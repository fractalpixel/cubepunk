package org.geoscapers.cubepunk.components

import org.entityflakes.ComponentBase
import org.entityflakes.Entity
import org.entityflakes.entitymanager.ComponentRef
import org.geoscapers.basecode.DemoContext
import org.geoscapers.utils.ZeroVector
import processing.core.PVector

val locationRef = ComponentRef(Location::class)

class StructuralCubeRenderer(var radius: Float = 10f,
                             var seed: Long = System.nanoTime()): ComponentBase(), Renderer {

    override fun draw(context: DemoContext) {
        context.run {
            random.setSeed(seed)

            // TODO: Move positioning out to a renderer base class?
            // TODO: Technically color should probably be a parameter, instead of being randomized...
            pushMatrix()
            val pos: PVector = entity?.get(locationRef)?.position ?: ZeroVector
            translate(pos.x, pos.y, pos.z)
            fill(color(random.nextGaussianFloat(210f, 20f),
                       random.nextGaussianFloat(70f, 20f),
                       random.nextGaussianFloat(50f, 10f)))
            box(radius)
            popMatrix()
        }
    }

    override fun doInit(entity: Entity) {
    }

    override fun doDispose() {
    }
}

