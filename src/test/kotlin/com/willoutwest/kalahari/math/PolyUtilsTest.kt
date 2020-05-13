package com.willoutwest.kalahari.math

import io.kotlintest.matchers.plusOrMinus
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec

/**
 * Test suite for [PolyUtils].
 */
class PolyUtilsTest : ShouldSpec() {

    init {

        "Obtaining roots for a cubic equation" {
            should("be correct for the following example.") {
                val result = PolyUtils.cubic(1f, -2f, -5f, 6f).sorted()

                result.size shouldBe 3
                result[0] shouldBe (-2f plusOrMinus 0.001f)
                result[1] shouldBe (1f plusOrMinus 0.001f)
                result[2] shouldBe (3f plusOrMinus 0.001f)
            }

            should("also be correct for the following example.") {
                val result = PolyUtils.cubic(6f, -5f, -17f, 6f).sorted()

                result.size shouldBe 3
                result[0] shouldBe (-1.5f plusOrMinus 0.001f)
                result[1] shouldBe ((1f/3f) plusOrMinus 0.001f)
                result[2] shouldBe (2f plusOrMinus 0.001f)
            }
        }

        "Obtaining roots for a quadtratic equation" {
            should("be correct for the following example.") {
                val result = PolyUtils.quadratic(5f, 6f, 1f).sorted()

                result.size shouldBe 2
                result[0] shouldBe (-1f plusOrMinus 0.001f)
                result[1] shouldBe (-0.2f plusOrMinus 0.001f)
            }
        }

        "Obtaining the roots to a quartic equation" {
            should("be correct for the following example.") {
                val result = PolyUtils.quartic(6f, 0f, -35f, 0f, 50f).sorted()

                result.size shouldBe 4
                result[0] shouldBe (-MathUtils.sqrt(10f/3f) plusOrMinus 0.001f)
                result[1] shouldBe (-MathUtils.sqrt(5f/2f) plusOrMinus 0.001f)
                result[2] shouldBe (MathUtils.sqrt(5f/2f) plusOrMinus 0.001f)
                result[3] shouldBe (MathUtils.sqrt(10f/3f) plusOrMinus 0.001f)
            }
        }
    }
}