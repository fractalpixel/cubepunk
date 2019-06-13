package org.geoscapers.basecode

import org.geoscapers.utils.camera
import org.mistutils.random.RandomSequence
import org.ode4j.ode.*
import org.ode4j.ode.internal.OdeFactoryImpl
import org.ode4j.ode.internal.OdeInit
import org.ode4j.ode.internal.joints.OdeJointsFactoryImpl
import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector



/**
 *
 */
abstract class CubePunkContext @JvmOverloads constructor(
    val releaseBuild: Boolean = false,
    val randomSeed: Long = 42,
    val targetFrameRate: Float = 60f,
    val initialGravity: PVector = PVector(0f, -9.81f, 0f)): PApplet() {

    /**
     * Random number source with better randomness and utility methods than java random.
     */
    val random: RandomSequence = RandomSequence.createDefault(randomSeed)

    /**
     * Bit mask for checking whether a geometry category bit vector marks the geometry as static.
     * By deault, if the first bit of the geometry category is set, it is static (not movable by physics).
     */
    var staticGeometryMask: Long = 0x01

    lateinit var physicsWorld: DWorld
        private set
    lateinit var physicsSpace: DSpace
        private set

    private val maxCollisionContacts = 8
    private val collisionContacts = DContactBuffer(maxCollisionContacts)
    private val collisionContactGeometries = DContactGeomBuffer(collisionContacts)
    private val collisionJointGroup = OdeJointsFactoryImpl.createJointGroup()


    override fun settings() {
        if (releaseBuild) {
            fullScreen(P3D)
        }
        else {
            size(800, 600, P3D)
        }
    }

    final override fun setup() {
        preInit()
        init()
    }

    final override fun draw() {
        preUpdate()
        update()
    }

    /**
     * Default actions done before other initialization.
     */
    open fun preInit() {
        // Hide mouse
        noCursor()

        // Semi-sane color space
        initColorSpace()

        frameRate(targetFrameRate)

        // Create physics engine
        OdeInit.dInitODE()
        physicsWorld = OdeFactoryImpl.createWorld()
        physicsSpace = OdeFactoryImpl.createHashSpace()
        //physicsSpace = OdeFactoryImpl.createBHVSpace(staticGeometryMask)

        // Setup world
        physicsWorld.setGravity(
            initialGravity.x.toDouble(),
            initialGravity.y.toDouble(),
            initialGravity.z.toDouble())

        // More iterations gives stabler physics but lower performance
        physicsWorld.quickStepNumIterations = 100
    }

    open fun initColorSpace() {
        colorMode(PConstants.HSB, 360f, 100f, 100f, 100f)
    }

    var nearClippingPlane: Float = 0.1f
    var farClippingPlane: Float = 1000f

    /**
     * Eye position
     */
    var cameraPosition: PVector = PVector(30f, 20f, -30f)

    /**
     * Position being looked at
     */
    var cameraFocus: PVector = PVector(0f, 0f, 0f)

    /**
     * Up direction for camera (note that y is considered up).
     */
    var cameraUp: PVector = PVector(0f, 1f, 0f)

    /**
     * Field of view for the camera in degrees.
     */
    var fieldOfViewDeg: Float
        get() = fieldOfViewRad * 360f / TAU
        set(value) {
            fieldOfViewRad = value / 360f * TAU
        }

    /**
     * Field of view of the camera in radians.
     */
    var fieldOfViewRad: Float = TAU / 6f

    /**
     * Color to fill background with.  Specify with color method.
     */
    var backgroundColor: Int = color(0f)

    /**
     * Default actions done at the start of each update loop.
     */
    open fun preUpdate() {
        initColorSpace()

        // Setup camera
        val aspectRatio = 1f * width / height
        perspective(fieldOfViewRad, aspectRatio, nearClippingPlane, farClippingPlane)

        camera(cameraPosition, cameraFocus, cameraUp)

        // Scale to resolution independent units.
        // Convention: One unit == 1 meter
        scale(width / 1000f)

        // Don't draw lines
        noStroke()

        // Clear background
        background(backgroundColor)

        // Simulate physics (first parameter is a generic data object that is passed in to handler, not used.)
        physicsSpace.collide(null) { _, o1: DGeom?, o2: DGeom? ->
            if (o1 != null && o2 != null) handlePotentialCollision(o1, o2)
        }

        physicsWorld.quickStep(1.0 / targetFrameRate)

        // Remove previous collision joints
        collisionJointGroup.clear()

        // Default lights
        defaultLights()
    }

    /**
     * Handles collision of two objects in the physics engine
     */
    open fun handlePotentialCollision(geometryA: DGeom, geometryB: DGeom) {
        //collisionContacts.

        // Check if they actually collide at some points
        val contactCount = OdeJointsFactoryImpl.collide(geometryA, geometryB, maxCollisionContacts, collisionContactGeometries)
        for (i in 0 until contactCount) {
            val collisionContact = collisionContacts.get(i)

            // Contact parameters?
            collisionContact.surface.mode = OdeConstants.dContactApprox1
            collisionContact.surface.mu = 5.0

            // Create a contact between the two colliding objects
            val contactJoint = OdeHelper.createContactJoint(physicsWorld, collisionJointGroup, collisionContact)
            contactJoint.attach(geometryA.body, geometryB.body)
        }

    }

    open fun defaultLights() {
        directionalLight(60f, 20f, 80f, -0.25f, -0.5f, 0.15f) // Clear skylight
        directionalLight(0f, 50f, 30f, 0.1f, 1f, -0.2f) // Diabolical counter light
        ambient(200f, 30f, 100f) // Bluish ambient light
    }

    /**
     * Do general initialization at start.
     */
    abstract fun init()

    /**
     * Update and draw scene here.
     */
    abstract fun update()


    fun shutdown() {
        doShutdown()
        shutdownPhysics()
    }

    fun shutdownPhysics() {
        collisionJointGroup.destroy()
        physicsWorld.destroy()
        physicsSpace.destroy()
        OdeHelper.closeODE()
    }

    open fun doShutdown() {}

}