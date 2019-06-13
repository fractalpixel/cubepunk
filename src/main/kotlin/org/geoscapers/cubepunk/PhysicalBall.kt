package org.geoscapers.cubepunk

import org.geoscapers.basecode.DemoContext
import org.geoscapers.utils.ZeroVector
import org.geoscapers.utils.x
import org.geoscapers.utils.y
import org.geoscapers.utils.z
import org.ode4j.ode.DBody
import org.ode4j.ode.DSphere
import org.ode4j.ode.internal.DxMass
import org.ode4j.ode.internal.OdeFactoryImpl
import org.ode4j.ode.internal.joints.OdeJointsFactoryImpl
import processing.core.PVector

/**
 * Ball object, backed by physics.
 */
class PhysicalBall(val context: DemoContext,
                   initialPosition: PVector = ZeroVector,
                   initialRadius: Double = 0.5,
                   initialDensity: Double = DEFAULT_DENSITY,
                   var color: Int = context.color(0f, 60f, 80f)) {

    /**
     * Physics body object.
     */
    lateinit var body: DBody
        private set

    /**
     * Physics geometry object.
     */
    lateinit var physicsGeometry: DSphere
        private set

    private val massDistribution = DxMass()

    /**
     * Radius of the sphere, in meters.
     */
    var radius: Double = initialRadius
        set(value) {
            field = value
            physicsGeometry.radius = value
            updateMass()
        }

    /**
     * Density of the object, in kg / m^3
     */
    var density: Double = initialDensity
        set(value) {
            field = value
            updateMass()
        }

    /**
     * Calculates and returns the volume of this object.
     */
    val volume: Double get() = 4.0 / 3.0 * Math.PI * radius*radius*radius

    /**
     * The mass in kg for this object.
     * If you set this, the density will be changed.
     */
    var mass: Double
        get() = massDistribution.mass
        set(newMass) {
            val v = volume
            density = if (v <= 0.0) DEFAULT_DENSITY else newMass / v
        }

    init {
        // Create body
        body = OdeFactoryImpl.createBody(context.physicsWorld)
        massDistribution.setSphere(initialDensity, initialRadius)
        body.mass = massDistribution
        body.setPosition(
            initialPosition.x.toDouble(),
            initialPosition.y.toDouble(),
            initialPosition.z.toDouble())
        updateMass()

        // Create geometry
        physicsGeometry = OdeJointsFactoryImpl.createSphere(context.physicsSpace, initialRadius)
        physicsGeometry.body = body
    }

    /**
     * Recalculates physical mass (including inertia constants) based on density and radius.
     */
    private fun updateMass() {
        massDistribution.dMassSetSphere(density, radius)
        body.mass = massDistribution
    }

    /**
     * Draws this object.
     */
    fun draw() {
        context.run {
            pushMatrix()

            // Update position based on physics
            val pos = body.position
            translate(pos.x, pos.y, pos.z)

            // Draw
            fill(color)
            sphere(radius.toFloat())

            popMatrix()
        }
    }

    companion object {
        val DEFAULT_DENSITY = 1000.0 // Approximate density of water.
    }

}