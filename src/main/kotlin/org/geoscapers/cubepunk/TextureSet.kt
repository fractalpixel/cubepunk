package org.geoscapers.cubepunk

import org.geoscapers.basecode.DemoContext
import org.mistutils.random.RandomHash
import processing.core.PImage

/**
 * Set of related textures that can be automatically selected between based on a random seed.
 */
class TextureSet(val context: DemoContext,
                 val baseFileName: String,
                 val textureCount: Int,
                 val textureSetSeed: Long = 1) {

    private val randomHash = RandomHash.createDefault()
    private val images= ArrayList<PImage>()

    init {
        loadImages()
    }

    fun loadImages() {
        if (images.isEmpty()) {
            for (i in 1..textureCount) {
                images.add(context.loadImage("textures/${baseFileName}_$i.png"))
            }
        }
    }

    fun getTexture(seed: Long): PImage {
        val selectionSeed = randomHash.hash(textureSetSeed, seed)
        val textureNum = randomHash.hashInt(selectionSeed, 1, textureCount)
        return images[textureNum]
    }

    fun applyTexture(seed: Long, color: Int? = null) {
        if (color != null) context.tint(color)
        context.texture(getTexture(seed))
    }

}