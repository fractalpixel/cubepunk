package org.geoscapers.cubepunk.components

import org.entityflakes.Entity
import org.entityflakes.entityfactory.EntityFactoryBase
import org.mistutils.random.RandomSequence
import org.mistutils.symbol.Symbol
import processing.core.PVector

class StructuralCubeFactory: EntityFactoryBase() {

    override fun doCreateEntity(entity: Entity, random: RandomSequence, parameters: Map<Symbol, Any>) {
        parameters.getOrDefault(Symbol["x"], 1)

        // Position
        val x = random.nextGaussianFloat(0f, 100f)
        val y = random.nextGaussianFloat(0f, 100f)
        val z = random.nextGaussianFloat(0f, 100f)
        entity.set(Location(PVector(x, y, z)))

        // Appearance
        val radius = random.nextFloat(0.1f, 8f)
        val renderingSeed = random.nextLong()
        entity.set(StructuralCubeRenderer(radius, renderingSeed))
    }
}