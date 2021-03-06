package org.geoscapers.cubepunk

import org.entityflakes.DefaultWorld
import org.geoscapers.basecode.DemoContext
import org.geoscapers.cubepunk.components.Renderer
import org.geoscapers.cubepunk.components.StructuralCubeFactory
import org.mistutils.symbol.Symbol
import processing.core.PApplet
import processing.core.PConstants

fun main() {
    // Start with processing
    PApplet.main(CubePunk::class.java)
}

/**
 *
 */
class CubePunk: DemoContext(true) {

    val balls = ArrayList<PhysicalBall>()

    override fun init() {
        /*
        val ballCenter = PVector(10f, 20f, 10f)
        val pos = PVector()
        for (i in 1..100) {
            pos.randomizeGaussian(random, 40f, ballCenter)
            val color = color(
                random.nextGaussianFloat(220f, 20f),
                random.nextGaussianFloat(60f, 20f),
                random.nextGaussianFloat(80f, 10f)
            )
            val radius = random.nextDouble(2.0, 8.0)
            val density = 1000.0
            balls.add(PhysicalBall(this, pos, radius, density, color))
        }

        // Create physics ground - but assign no body for it, so it is only used in collision -> unmovable
        OdeFactoryImpl.createPlane(physicsSpace, 0.0, 1.0, 0.0, 0.0)
        */


        textureMode(PConstants.NORMAL)

        // Setup textures
        val dirtyBoxTexture = TextureSet(this, "dirty_cube", 2)

        // Setup world
        world.registerEntityFactory(Symbol["StructuralCube"], StructuralCubeFactory(dirtyBoxTexture))
        val drawingRef = world.getComponentRef(Renderer::class)
        world.addEntityProcessor(listOf(drawingRef), { entity, time ->
            // Draw each drawable object every frame
            entity[drawingRef]?.draw(this)
        })

        // Create some stuff
        for (i in 1..1000) {
            val seed = i.toLong()
            world.createEntity(Symbol["StructuralCube"], seed)
        }
    }

    override fun update() {
        lightFalloff(0.5f, 0.0f, 0.0001f)
        pointLight(0f, 0f, 100f, -30f, 20f, 20f)

        // Update world
        world.step()

        // Red cube!
        fill(0f, 50f, 60f)
        box(10f)

        /*
        // Balls
        for (ball in balls) {
            ball.draw()
        }
        */
    }
}