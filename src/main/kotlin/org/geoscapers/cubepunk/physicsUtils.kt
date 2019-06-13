package org.geoscapers.cubepunk

import org.ode4j.math.DVector3
import org.ode4j.math.DVector3C
import processing.core.PVector

/*
 * Utilities for making working with Ode4J simpler with Kotlin.
 */

val DVector3C.x: Float get() = this.get0().toFloat()
val DVector3C.y: Float get() = this.get1().toFloat()
val DVector3C.z: Float get() = this.get2().toFloat()

var DVector3.x: Float
    get() = this.get0().toFloat()
    set(value) = this.set(0, value.toDouble())
var DVector3.y: Float
    get() = this.get1().toFloat()
    set(value) = this.set(1, value.toDouble())
var DVector3.z: Float
    get() = this.get2().toFloat()
    set(value) = this.set(2, value.toDouble())

/**
 * Converts an Ode Physics Ode4j vector to a processing PVector.
 * Pass in the processing vector to write to, or create a new one each call.
 */
fun DVector3C.toPVector(out: PVector = PVector()): PVector {
    out.x = this.x
    out.y = this.y
    out.z = this.z
    return out
}

fun DVector3.set(v: PVector = PVector()) {
    this.set(v.x.toDouble(), v.y.toDouble(), v.z.toDouble())
}
