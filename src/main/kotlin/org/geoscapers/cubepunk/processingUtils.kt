package org.geoscapers.cubepunk

import org.mistutils.random.RandomSequence
import processing.core.PApplet
import processing.core.PVector

/*
 * Various utilities and extension methods making working with processing from Kotlin easier.
 */

/**
 * Note, assumes positive y is upwards, as opposed to Processing.
 */
fun PApplet.camera(position: PVector, focus: PVector = ZeroVector, up: PVector = PositiveY) {
    // Flip y in up vector, so that up 1 means y is upwards, as would be expected.
    this.camera(
        position.x, position.y, position.z,
        focus.x, focus.y, focus.z,
        up.x, -up.y, up.z)
}

fun PVector.randomize(random: RandomSequence, radius: Float = 1f, center: PVector = ZeroVector) {
    x = random.nextFloat(center.x - radius, center.x + radius)
    y = random.nextFloat(center.y - radius, center.y + radius)
    z = random.nextFloat(center.z - radius, center.z + radius)
}

fun PVector.randomizeGaussian(random: RandomSequence, standardDeviation: Float = 1f, center: PVector = ZeroVector) {
    x = random.nextGaussianFloat(center.x, standardDeviation)
    y = random.nextGaussianFloat(center.y, standardDeviation)
    z = random.nextGaussianFloat(center.z, standardDeviation)
}


// Constants for directions.  Do not change these.
val PositiveX: PVector = PVector(1f, 0f, 0f)
val NegativeX: PVector = PVector(-1f, 0f, 0f)
val PositiveY: PVector = PVector(0f, 1f, 0f)
val NegativeY: PVector = PVector(0f, -1f, 0f)
val PositiveZ: PVector = PVector(0f, 0f, 1f)
val NegativeZ: PVector = PVector(0f, 0f, -1f)
val ZeroVector: PVector = PVector(0f, 0f, 0f)
