package com.solsticesquared.kalahari.math

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.ShouldSpec

/**
 * Test suite for [EpsilonTable].
 */
class EpsilonTableTest : ShouldSpec() {

    init {

        "An epsilon table's copy constructor" {
            should("copy all of the other table's values correctly.") {
                val defaultEpsilon = 0.103f
                val table = mutableMapOf(
                    "circle" to 0.0001f,
                    "rect" to 0.01f,
                    "square" to 0.003f
                )

                val epsilons = EpsilonTable(defaultEpsilon, table)

                val expected = epsilons.clone()
                val result = EpsilonTable(epsilons)

                result shouldBe expected
            }
        }

        "A request for a mapping that does not exist" {
            should("return the default epsilon value.") {
                val defaultEpsilon = 0.103f
                val table = mutableMapOf(
                    "circle" to 0.0001f,
                    "rect" to 0.01f,
                    "square" to 0.003f
                )

                val epsilons = EpsilonTable(defaultEpsilon, table)

                epsilons["circle"] shouldBe 0.0001f
                epsilons["spline"] shouldBe defaultEpsilon
            }
        }
    }
}
