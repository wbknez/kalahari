package com.willoutwest.kalahari.gen

import io.kotlintest.properties.Gen
import kotlin.math.abs

/**
 * Produces a randomized sequence of negative numeric <code>float</code>
 * values.
 */
fun Gen.Companion.negativeFloats(): Gen<Float> = numericFloats().map {
    abs(it) * -1f
}

/**
 * Produces a randomized sequence of numeric <code>float</code> values
 * between one and zero, inclusive.
 */
fun Gen.Companion.nonzeroFloats(): Gen<Float> = numericFloats().filter {
    it  != 0f
}

/**
 * Produces a randomized sequence of positive numeric <code>float</code>
 * values.
 */
fun Gen.Companion.positiveFloats(): Gen<Float> = numericFloats().map {
    abs(it)
}

/**
 * Produces a randomized sequence of numeric <code>float</code> values
 * between one and zero, inclusive.
 */
fun Gen.Companion.smallFloats(): Gen<Float> = numericFloats(0f, 1f)
