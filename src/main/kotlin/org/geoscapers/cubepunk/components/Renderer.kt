package org.geoscapers.cubepunk.components

import org.entityflakes.Component
import org.entityflakes.PolymorphicComponent
import org.geoscapers.basecode.DemoContext

/**
 * Something that can draw itself onto a 3D context.
 */
interface Renderer: PolymorphicComponent  {

    override val componentCategory: Class<out Component> get() = Renderer::class.java

    fun draw(context: DemoContext)

}