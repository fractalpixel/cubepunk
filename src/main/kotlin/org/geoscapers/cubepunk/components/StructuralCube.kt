package org.geoscapers.cubepunk.components

import org.entityflakes.ComponentBase
import org.entityflakes.Entity
import org.entityflakes.entitymanager.ComponentRef
import org.geoscapers.basecode.DemoContext
import org.geoscapers.cubepunk.TextureSet
import org.geoscapers.utils.ZeroVector
import org.mistutils.math.Tau
import processing.core.PVector
import processing.core.PConstants.QUADS
import processing.core.PImage
import java.lang.Math.abs
import java.lang.Math.pow


val locationRef = ComponentRef(Location::class)

class StructuralCubeRenderer(var radius: Float = 10f,
                             val textureSet: TextureSet? = null,
                             var seed: Long = System.nanoTime()): ComponentBase(), Renderer {

    override fun draw(context: DemoContext) {
        context.run {
            random.setSeed(seed)

            // TODO: Move positioning out to a renderer base class?
            // TODO: Technically color should probably be a parameter, instead of being randomized...
            pushMatrix()
            val pos: PVector = entity?.get(locationRef)?.position ?: ZeroVector

            val wavepos = random.nextGaussianFloat(0f, 1f)
            val wavespeed = 1f //random.nextGaussianFloat(1f, 0.001f)

            translate(pos.x, pos.y + pow(Math.sin((wavepos * context.world.time.secondsSinceStart) * Tau * 0.03 * wavespeed), 3.0).toFloat() * 100f, pos.z)
            val color = color(
                random.nextGaussianFloat(10f, 20f),
                random.nextGaussianFloat(10f, 20f),
                random.nextGaussianFloat(100f, 10f)
            )
            //fill(color)

            //box(radius)
            scale(radius * 0.5f)
            drawTexturableCube(context, color)

            popMatrix()
        }
    }

    override fun doInit(entity: Entity) {
    }

    override fun doDispose() {
    }


    fun drawTexturableCube(context: DemoContext, color: Int) {
        context.run {
            beginShape(QUADS)

            // Apply texture if specified
            val seed1 = random.nextLong()
            textureSet?.applyTexture(seed1, color)

            // +Z "front" face
            vertex(-1f, -1f, 1f, 0f, 0f)
            vertex(1f, -1f, 1f, 1f, 0f)
            vertex(1f, 1f, 1f, 1f, 1f)
            vertex(-1f, 1f, 1f, 0f, 1f)

            // -Z "back" face
            vertex(1f, -1f, -1f, 0f, 0f)
            vertex(-1f, -1f, -1f, 1f, 0f)
            vertex(-1f, 1f, -1f, 1f, 1f)
            vertex(1f, 1f, -1f, 0f, 1f)

            // +Y "bottom" face
            vertex(-1f, 1f, 1f, 0f, 0f)
            vertex(1f, 1f, 1f, 1f, 0f)
            vertex(1f, 1f, -1f, 1f, 1f)
            vertex(-1f, 1f, -1f, 0f, 1f)

            // -Y "top" face
            vertex(-1f, -1f, -1f, 0f, 0f)
            vertex(1f, -1f, -1f, 1f, 0f)
            vertex(1f, -1f, 1f, 1f, 1f)
            vertex(-1f, -1f, 1f, 0f, 1f)

            // +X "right" face
            vertex(1f, -1f, 1f, 0f, 0f)
            vertex(1f, -1f, -1f, 1f, 0f)
            vertex(1f, 1f, -1f, 1f, 1f)
            vertex(1f, 1f, 1f, 0f, 1f)

            // -X "left" face
            vertex(-1f, -1f, -1f, 0f, 0f)
            vertex(-1f, -1f, 1f, 1f, 0f)
            vertex(-1f, 1f, 1f, 1f, 1f)
            vertex(-1f, 1f, -1f, 0f, 1f)

            endShape()
        }
    }
}

