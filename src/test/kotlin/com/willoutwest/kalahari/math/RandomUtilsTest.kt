package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.gen.positiveFloats
import com.willoutwest.kalahari.gen.smallDoubles
import com.willoutwest.kalahari.gen.smallFloats
import io.kotlintest.assertSoftly
import io.kotlintest.matchers.doubles.shouldBeGreaterThanOrEqual
import io.kotlintest.matchers.doubles.shouldBeLessThan
import io.kotlintest.matchers.floats.shouldBeGreaterThanOrEqual
import io.kotlintest.matchers.floats.shouldBeLessThan
import io.kotlintest.matchers.shouldBeInRange
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.specs.ShouldSpec
import kotlin.math.max
import kotlin.math.min

class RandomDoubleGenerator: Gen<Double> {

    override fun constants(): Iterable<Double> = emptyList()

    override fun random(): Sequence<Double> = generateSequence {
        RandomUtils.nextDouble()
    }
}

fun Gen.Companion.randomDouble(): Gen<Double> = RandomDoubleGenerator()

class RandomFloatGenerator: Gen<Float> {

    override fun constants(): Iterable<Float> = emptyList()

    override fun random(): Sequence<Float> = generateSequence {
        RandomUtils.nextFloat()
    }
}

fun Gen.Companion.randomFloat(): Gen<Float> = RandomFloatGenerator()

/**
 * Test suite for [RandomUtils].
 */
class RandomUtilsTest : ShouldSpec() {

    init {

        "Generating a random double" {
            should("be less than one") {
                assertAll(Gen.randomDouble()) { x: Double ->
                    assertSoftly {
                        x.shouldBeGreaterThanOrEqual(0.0)
                        x.shouldBeLessThan(1.0)
                    }
                }
            }
        }

        "Generating a random double with a single bound" {
            should("be less than that bound and greater than or equal to " +
                   "zero") {
                assertAll(Gen.positiveDoubles()) { n: Double ->
                    val x = RandomUtils.nextDouble(n)

                    assertSoftly {
                        x.shouldBeGreaterThanOrEqual(0.0)
                        x.shouldBeLessThan(n)
                    }
                }
            }
        }

        "Generating a random double with an upper and lower bound" {
            should("be less than the upper bound and greater than or equal " +
                   "to the lower") {
                assertAll(Gen.smallDoubles(), Gen.smallDoubles()) {
                    x0: Double, x1: Double ->

                    val a = when (x0 != x1) {
                        false -> min(x0, x1 + 1)
                        true  -> min(x0, x1)
                    }
                    val b = when (x0 != x1) {
                        false -> max(x0, x1 + 1)
                        true  -> max(x0, x1)
                    }
                    val x = RandomUtils.nextDouble(a, b)

                    assertSoftly {
                        x.shouldBeGreaterThanOrEqual(a)
                        x.shouldBeLessThan(b)
                    }
                }
            }
        }

        "Generating a random float" {
            should("be less than one") {
                assertAll(Gen.randomFloat()) { x: Float ->
                    assertSoftly {
                        x.shouldBeGreaterThanOrEqual(0f)
                        x.shouldBeLessThan(1f)
                    }
                }
            }
        }

        "Generating a random float with a single bound" {
            should("be less than that bound and greater than or equal to " +
                   "zero") {
                assertAll(Gen.positiveFloats()) { n: Float ->
                    val x = RandomUtils.nextFloat(n)

                    assertSoftly {
                        x.shouldBeGreaterThanOrEqual(0f)
                        x.shouldBeLessThan(n)
                    }
                }
            }
        }

        "Generating a random float with an upper and lower bound" {
            should("be less than the upper bound and greater than or equal " +
                   "to the lower") {
                assertAll(Gen.smallFloats(), Gen.smallFloats()) {
                    x0: Float, x1: Float ->

                    val a = when (x0 != x1) {
                        false -> min(x0, x1 + 1f)
                        true  -> min(x0, x1)
                    }
                    val b = when (x0 != x1) {
                        false -> max(x0, x1 + 1f)
                        true  -> max(x0, x1)
                    }
                    val x = RandomUtils.nextFloat(a, b)

                    assertSoftly {
                        x.shouldBeGreaterThanOrEqual(a)
                        x.shouldBeLessThan(b)
                    }
                }
            }
        }

        "Generating a random integer with a single bound" {
            should("be less than that bound and greater than or equal to " +
                   "zero") {
                assertAll(Gen.positiveIntegers()) { n: Int ->
                    RandomUtils.nextInt(n) shouldBeInRange (0 until n)
                }
            }
        }

        "Generating a random integer with an upper and lower bound" {
            should("be less than the upper bound and be greater than or " +
                   "equal to the lower") {
                assertAll(Gen.int(), Gen.int()) { x0: Int, x1: Int ->
                    val a = when(x0 != x1) {
                        false -> min(x0, x1 + 1)
                        true  -> min(x0, x1)
                    }
                    val b = when(x0 != x1) {
                        false -> max(x0, x1 + 1)
                        true  -> max(x0, x1)
                    }
                    
                    RandomUtils.nextInt(a, b) shouldBeInRange (a until b)
                }
            }
        }
    }
}