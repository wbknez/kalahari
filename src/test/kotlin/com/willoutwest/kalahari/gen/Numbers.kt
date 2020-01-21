package com.willoutwest.kalahari.gen

import io.kotlintest.properties.Gen
import kotlin.math.abs

/**
 * Produces a randomized sequence of positive numeric <code>float</code>
 * values.
 */
fun Gen.Companion.positiveFloats(): Gen<Float> = numericFloats().map {
    abs(it)
}

/**
 * Produces a randomized sequence of negative numeric <code>float</code>
 * values.
 */
fun Gen.Companion.negativeFloats(): Gen<Float> = numericFloats().map {
    abs(it) * -1f
}
