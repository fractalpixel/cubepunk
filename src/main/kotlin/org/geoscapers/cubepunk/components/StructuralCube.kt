package org.geoscapers.cubepunk.components

import org.entityflakes.ComponentBase
import org.entityflakes.Entity
import org.entityflakes.entitymanager.ComponentRef
import org.geoscapers.basecode.DemoContext
import org.geoscapers.utils.ZeroVector
import processing.core.PVector

val locationRef = ComponentRef(Location::class)

class StructuralCubeRenderer(var radius: Float = 10f): ComponentBase(), Renderer {

    override fun draw(context: DemoContext) {
        context.run {
            // TODO: Move position drawing out to a renderable base class?
            pushMatrix()
            val pos: PVector = entity?.get(locationRef)?.position ?: ZeroVector
            translate(pos.x, pos.y, pos.z)
            fill(color(210f, 50f, 50f))
            box(radius)
            popMatrix()
        }
    }

    override fun doInit(entity: Entity) {
    }

    override fun doDispose() {
    }
}

