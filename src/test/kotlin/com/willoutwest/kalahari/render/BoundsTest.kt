package com.willoutwest.kalahari.render

import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec

class BoundsGenerator(val xMin: Int, val xMax: Int,
                      val yMin: Int, val yMax: Int,
                      val widthMin: Int, val widthMax: Int,
                      val heightMin: Int, val heightMax: Int) : Gen<Bounds> {

    override fun constants(): Iterable<Bounds> = emptyList()

    override fun random(): Sequence<Bounds> = generateSequence {
        Bounds(Gen.choose(this.xMin, this.xMax).random().first(),
               Gen.choose(this.yMin, this.yMax).random().first(),
               Gen.choose(this.widthMin, this.widthMax).random().first(),
               Gen.choose(this.heightMin, this.heightMax).random().first())
    }
}

fun Gen.Companion.bounds(xMax: Int = 1000, yMax: Int = 1000,
                         widthMax: Int = 1000,
                         heightMax: Int = 1000): Gen<Bounds> =
    BoundsGenerator(0, xMax, 0, yMax, 1, widthMax, 1, heightMax)

fun Gen.Companion.bounds(xMin: Int = 0, xMax: Int = 1000,
                         yMin: Int = 0, yMax: Int = 1000,
                         widthMin: Int = 1, widthMax: Int = 1000,
                         heightMin: Int = 1, heightMax: Int = 1000):
    Gen<Bounds> =
    BoundsGenerator(xMin, xMax, yMin, yMax, widthMin, widthMax, heightMin,
                    heightMax)

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