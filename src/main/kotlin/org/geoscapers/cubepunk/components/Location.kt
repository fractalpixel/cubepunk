package org.geoscapers.cubepunk.components

import com.jogamp.opengl.math.Quaternion
import org.entityflakes.Entity
import org.entityflakes.ReusableComponentBase
import processing.core.PVector

/**
 * The position and rotation of some object in the world.
 */
class Location(initialPosition: PVector? = null,
               initialRotation: Quaternion? = null): ReusableComponentBase() {

    val position: PVector = PVector()
    val rotation: Quaternion = Quaternion()

    init {
        // Set initial values if provided
        if (initialPosition != null) position.set(initialPosition)
        if (initialRotation != null) rotation.set(initialRotation)
    }

    override fun doDispose() {
        // Nothing to do
    }

    override fun doInit(entity: Entity) {
        // Nothing to do
    }

    override fun doReset(oldEntity: Entity) {
        // Reset to default
        position.set(0f, 0f, 0f)
        rotation.setIdentity()
    }
}