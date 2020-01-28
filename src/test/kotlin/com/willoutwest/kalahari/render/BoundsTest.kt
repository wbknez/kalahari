package com.willoutwest.kalahari.render

import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec

class BoundsGenerator(val xMax: Int, val yMax: Int,
                      val widthMax: Int, val heightMax: Int) : Gen<Bounds> {

    override fun constants(): Iterable<Bounds> = emptyList()

    override fun random(): Sequence<Bounds> = generateSequence {
        Bounds(Gen.choose(0, this.xMax).random().first(),
               Gen.choose(0, this.yMax).random().first(),
               Gen.choose(1, this.widthMax).random().first(),
               Gen.choose(1, this.heightMax).random().first())
    }
}

fun Gen.Companion.bounds(xMax: Int = 1000, yMax: Int = 1000,
                         widthMax: Int = 1000,
                         heightMax: Int = 1000): Gen<Bounds> =
    BoundsGenerator(xMax, yMax, widthMax, heightMax)

/**
 * Test suite for [Bounds].
 */
class BoundsTest : ShouldSpec() {

    init {

        "Computing the area of a bounds" {
            should("be the product of the width times the height") {
                assertAll(Gen.bounds()) { bounds: Bounds ->
                    bounds.area shouldBe (bounds.width * bounds.height)
                }
            }
        }
    }
}