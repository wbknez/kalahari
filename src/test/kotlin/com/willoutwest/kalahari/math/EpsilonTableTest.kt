package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.gen.smallFloats
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec

class EpsilonTableGenerator(private val numEntries: Int = 1,
                            private val defaultEpsilon: Float = 0.0001f)
    : Gen<EpsilonTable> {

    override fun constants(): Iterable<EpsilonTable> = emptyList()

    override fun random(): Sequence<EpsilonTable> = generateSequence {
        val pairs = (1..this.numEntries).map {
            randomName() to randomEpsilon()
        }

        EpsilonTable(this.defaultEpsilon, pairs.toMap().toMutableMap())
    }

    private fun randomEpsilon(): Float = Gen.smallFloats().random().first()

    private fun randomName(): String = Gen.string(10).random().first()
}

fun Gen.Companion.epsilonTable(numEntries: Int = 1,
                               defaultEpsilon: Float = 0.0001f):
    Gen<EpsilonTable> =
    EpsilonTableGenerator(numEntries, defaultEpsilon)

/**
 * Test suite for [EpsilonTable].
 */
class EpsilonTableTest : ShouldSpec() {

    init {

        "Requesting an epsilon for a name that does not exist" {
            should("produce the default epsilon value instead.") {
                assertAll(Gen.epsilonTable(20, 3.0f)) {
                    table: EpsilonTable ->

                    table["unique_str_name"].shouldBe(table.defaultEpsilon)
                }
            }
        }
    }
}
