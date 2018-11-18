package com.solsticesquared.kalahari.math

import io.kotlintest.matchers.shouldBe
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

class MathUtilsTest : ShouldSpec() {

    init {

        "The absolute value" {
            should("be positive if x is negative.") {
                forAll(Gen.float()) { x: Float ->
                    val xx = when (x > 0) {
                        false -> x
                        true  -> -x
                    }
                    val expected = when (xx != 0f) {
                        false -> 0f
                        true  -> -xx
                    }
                    val result = MathUtils.abs(xx)

                    result == expected
                }
            }

            should("be positive if x is positive.") {
                forAll(Gen.float()) { x: Float ->
                    val xx = when(x < 0) {
                        false -> x
                        true  -> -x
                    }
                    val expected = when(xx != 0f) {
                        false -> 0f
                        true  -> xx
                    }
                    val result = MathUtils.abs(xx)

                    result == expected
                }
            }
        }

        "Clamping a number on a range" {
            should("clamp if below the minimum.") {
                MathUtils.clamp(-1f, 0f, 1f) shouldBe 0f
            }

            should("clamp if above the maximum.") {
                MathUtils.clamp(2f, 0f, 1f) shouldBe 1f
            }

            should("do nothing if within bounds.") {
                MathUtils.clamp(200f, 123f, 345f) shouldBe 200f
            }
        }

        "The floor of a value" {
            should("be that number when the int cast is equivalent.") {
                MathUtils.floor(0.00001f) shouldBe
                    Math.floor(0.0001).toFloat()
            }

            should("be that number when the int cast is greater.") {
                MathUtils.floor(0.911111f) shouldBe
                    Math.floor(0.00001).toFloat()
            }
        }

        "A number that lies on an exclusive interval" {
            should("not be equal to either end point.") {
                MathUtils.isExclusive(0f, 0f, 1f) shouldBe false
                MathUtils.isExclusive(1f, 0f, 1f) shouldBe false
            }

            should("be strictly inside the interval.") {
                MathUtils.isExclusive(0.5f, 0f, 1f) shouldBe true
            }
        }

        "A number that lies on an inclusive interval" {
            should("be allowed to be equal to either end point.") {
                MathUtils.isInclusive(0f, 0f,1f) shouldBe true
                MathUtils.isInclusive(1f, 0f, 1f) shouldBe true
            }

            should("be allowed to be inside the interval.") {
                MathUtils.isInclusive(0.5f, 0f, 1f) shouldBe true
            }

            should("not be equal strictly outside the interval.") {
                MathUtils.isInclusive(-0.1f, 0f, 1f) shouldBe false
                MathUtils.isInclusive(1.1f, 0f, 1f) shouldBe false
            }
        }

        "Applying linear interpolation to a value over an interval" {
            should("be (a) when (t) is zero.") {
                MathUtils.lerp(1f, 2f, 0f) shouldBe 1f
            }

            should("be (b) when (t) is one.") {
                MathUtils.lerp(1f, 2f, 1f) shouldBe 2f
            }

            should("be an interpolation otherwise.") {
                MathUtils.lerp(1f, 2f, 0.5f) shouldBe 1.5f
            }
        }

        "The maximum of two numbers" {
            should("be independent of the argument order.") {
                MathUtils.max(33f, 42f) shouldBe MathUtils.max(42f, 33f)
            }

            should("choose the maximum.") {
                MathUtils.max(33f, 42f) shouldBe 42f
            }
        }

        "The minimum of two numbers" {
            should("be independent of the argument order.") {
                MathUtils.min(33f, 42f) shouldBe MathUtils.min(42f, 33f)
            }

            should("choose the minimum.") {
                MathUtils.min(33f, 42f) shouldBe 33f
            }
        }

        "A number raised to a power" {
            should("be that number times itself for each power.") {
                MathUtils.pow(2f, 6f) shouldBe 64f
            }
        }

        "The safe inverse of a number" {
            should("be positive infinity when the sign of zero is (+).") {
                MathUtils.safeInverse(0f) shouldBe Float.POSITIVE_INFINITY
            }

            should("be negative infinity when the sign of zero is (-).") {
                MathUtils.safeInverse(-0f) shouldBe Float.NEGATIVE_INFINITY
            }

            should("otherwise be the direct inverse of that number.") {
                MathUtils.safeInverse(2f) shouldBe 0.5f
            }
        }

        "The square root of a number" {
            should("be computed correctly.") {
                MathUtils.sqrt(121f) shouldBe 11f
            }
        }
    }
}
