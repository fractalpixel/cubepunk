package org.geoscapers.cubepunk

import org.entityflakes.DefaultWorld
import org.geoscapers.basecode.DemoContext
import org.geoscapers.cubepunk.components.Renderer
import org.geoscapers.cubepunk.components.StructuralCubeFactory
import org.mistutils.symbol.Symbol
import processing.core.PApplet

fun main() {
    // Start with processing
    PApplet.main(CubePunk::class.java)
}

/**
 *
 */
class CubePunk: DemoContext(false) {

    val balls = ArrayList<PhysicalBall>()

    val world = DefaultWorld()

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

        // Setup world
        world.init()
        world.registerEntityFactory(Symbol["StructuralCube"], StructuralCubeFactory())
        val drawingRef = world.getComponentRef(Renderer::class)
        world.addEntityProcessor(listOf(drawingRef), { entity, time ->
            // Draw each drawable object every frame
            entity[drawingRef]?.draw(this)
        })

        // Create some stuff
        for (i in 1..1000) {
            world.createEntity(Symbol["StructuralCube"])
        }
    }

    override fun update() {
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