package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.gen.negativeFloats
import com.willoutwest.kalahari.gen.nonzeroFloats
import com.willoutwest.kalahari.gen.positiveFloats
import com.willoutwest.kalahari.gen.smallFloats
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Test suite for [MathUtils].
 */
class MathUtilsTest : ShouldSpec() {

    init {

        "Taking the absolute of a number" {
            should("be positive if the value is negative.") {
                assertAll(Gen.negativeFloats()) { x: Float ->
                    MathUtils.abs(x).shouldBe(abs(x))
                }
            }

            should("be positive if the number is also positive.") {
                assertAll(Gen.positiveFloats()) { x: Float ->
                    MathUtils.abs(x).shouldBe(x)
                }
            }
        }

        "Clamping a number on a range" {
            should("force it to be a the minimum if less.") {
                assertAll(Gen.float(), Gen.float(), Gen.positiveFloats()) {
                    x0: Float, x1: Float, c: Float ->

                    val a = min(x0, x1)
                    val b = max(x0, x1)
                    val x = a - c

                    MathUtils.clamp(x, a, b).shouldBe(a)
                }
            }

            should("force it to be the maximum if greater.") {
                assertAll(Gen.float(), Gen.float(), Gen.positiveFloats()) {
                    x0: Float, x1: Float, c: Float ->

                    val a = min(x0, x1)
                    val b = max(x0, x1)
                    val x = b + c

                    MathUtils.clamp(x, a, b).shouldBe(b)
                }
            }

            should("do nothing if within bounds.") {
                assertAll(Gen.float(), Gen.float(), Gen.choose(2, 9)) {
                    x0: Float, x1: Float, c: Int ->

                    val a = min(x0, x1)
                    val b = max(x0, x1)
                    val x = a + (b - a) / c

                    MathUtils.clamp(x, a, b).shouldBe(x)
                }
            }
        }

        "Taking the floor of a number" {
            should("be equal to the integer cast.") {
                assertAll { x: Float ->
                    MathUtils.floor(x) shouldBe floor(x)
                }
            }

            should("be that number when the int cast is greater.") {
                assertAll { x: Int ->
                    val f = x.toFloat() + 0.1111f

                    MathUtils.floor(f) shouldBe floor(x.toFloat())
                }
            }
        }

        "Checking whether a number is in an exclusive interval" {
            should("never include the end points.") {
                assertAll(Gen.numericFloats(), Gen.numericFloats()) {
                    x0: Float, x1: Float ->

                    val a = min(x0, x1)
                    val b = max(x0, x1)

                    MathUtils.isExclusive(a, a, b).shouldBeFalse()
                    MathUtils.isExclusive(b, a, b).shouldBeFalse()
                }
            }

            should("always include the interval itself.") {
                assertAll(Gen.numericFloats(-1000f, 1000f),
                          Gen.numericFloats(-1000f, 1000f),
                          Gen.choose(2, 9)) {
                    x0: Float, x1: Float, c: Int ->

                    val a = min(x0, x1)
                    val b = max(x0, x1) + 1f
                    val x = a + (b - a) / c

                    MathUtils.isExclusive(x, a, b).shouldBeTrue()
                }
            }
        }

        "Checking whether a number is in an inclusive interval" {
            should("always include the end points.") {
                assertAll(Gen.numericFloats(), Gen.numericFloats()) {
                    x0: Float, x1: Float ->

                    val a = min(x0, x1)
                    val b = max(x0, x1)

                    MathUtils.isInclusive(a, a, b).shouldBeTrue()
                    MathUtils.isInclusive(b, a, b).shouldBeTrue()
                }
            }

            should("always include the interval itself.") {
                assertAll(Gen.numericFloats(-1000f, 1000f),
                          Gen.numericFloats(-1000f, 1000f),
                          Gen.choose(2, 9)) {
                    x0: Float, x1: Float, c: Int ->

                    val a = min(x0, x1)
                    val b = max(x0, x1)
                    val x = a + (b - a) / c

                    MathUtils.isInclusive(x, a, b).shouldBeTrue()
                }
            }

            should("never include the interval complement.") {
                assertAll(Gen.numericFloats(-1000f, 1000f),
                          Gen.numericFloats(-1000f, 1000f),
                          Gen.positiveIntegers()) {
                    x0: Float, x1: Float, c: Int ->

                    val a = min(x0, x1)
                    val b = max(x0, x1)

                    val x = a - c
                    val y = b + c

                    MathUtils.isInclusive(x, a, b).shouldBeFalse()
                    MathUtils.isInclusive(y, a, b).shouldBeFalse()
                }
            }
        }

        "Applying linear interpolation to a number over an interval" {
            should("be the lower bound when t is zero.") {
                assertAll(Gen.numericFloats(), Gen.numericFloats()) {
                    x0: Float, x1: Float ->

                    val a = min(x0, x1)
                    val b = max(x0, x1)

                    MathUtils.lerp(a, b, 0f).shouldBe(a)
                }
            }

            should("be the upper bound when t is one.") {
                assertAll(Gen.numericFloats(), Gen.numericFloats()) {
                    x0: Float, x1: Float ->

                    val a = min(x0, x1)
                    val b = max(x0, x1)

                    MathUtils.lerp(a, b, 1f).shouldBe(a + (b - a) * 1f)
                }
            }

            should("be the percentage increment otherwise.") {
                assertAll(Gen.numericFloats(), Gen.numericFloats(),
                          Gen.smallFloats()) {
                    x0: Float, x1: Float, t: Float ->

                    val a = min(x0, x1)
                    val b = max(x0, x1)

                    MathUtils.lerp(a, b, t).shouldBe(a + (b - a) * t)
                }
            }
        }

        "Taking the maximum of two numbers" {
            should("be independent of the argument order.") {
                assertAll { a: Float, b: Float ->
                    MathUtils.max(a, b) shouldBe MathUtils.max(b, a)
                }
            }

            should("result in the minimum being chosen.") {
                assertAll { a: Float, b: Float ->
                    MathUtils.max(a, b) shouldBe max(a, b)
                }
            }
        }

        "Taking the minimum of two numbers" {
            should("be independent of the argument order.") {
                assertAll { a: Float, b: Float ->
                    MathUtils.min(a, b) shouldBe MathUtils.min(b, a)
                }
            }

            should("result in the minimum being chosen.") {
                assertAll { a: Float, b: Float ->
                    MathUtils.min(a, b) shouldBe min(a, b)
                }
            }
        }

        "Raising a number to a power" {
            should("result in that number being multiplied power times.") {
                assertAll(Gen.numericFloats(), Gen.numericFloats()) {
                    x: Float, a: Float ->

                    MathUtils.pow(x, a).shouldBe(x.pow(a))
                }
            }
        }

        "Inverting a number safely" {
            should("produce a number when not zero.") {
                assertAll(Gen.nonzeroFloats()) { x: Float ->
                    MathUtils.safeInverse(x).shouldBe(1f / x)
                }
            }

            should("produce negative infinity if a positive zero.") {
                MathUtils.safeInverse(-0f).shouldBe(Float.NEGATIVE_INFINITY)
            }

            should("produce positive infinity if a positive zero.") {
                MathUtils.safeInverse(0f).shouldBe(Float.POSITIVE_INFINITY)
            }
        }

        "Taking the square root of a number" {
            should("produce the square root.") {
                assertAll(Gen.numericFloats()) { x: Float ->
                    MathUtils.sqrt(x).shouldBe(sqrt(x))
                }
            }
        }
    }
}