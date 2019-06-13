package org.geoscapers.cubepunk

import org.ode4j.ode.internal.OdeFactoryImpl
import processing.core.PApplet
import processing.core.PVector

fun main() {
    // Start with processing
    PApplet.main(CubePunk::class.java)
}

/**
 *
 */
class CubePunk: CubePunkContext(false) {

    val balls = ArrayList<PhysicalBall>()

    override fun init() {
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
    }

    override fun update() {
        // Red
        fill(0f, 50f, 60f)

        // Cube!
        box(10f)

        // Balls
        for (ball in balls) {
            ball.draw()
        }
    }
}