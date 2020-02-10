package com.willoutwest.kalahari.render.orders

import com.willoutwest.kalahari.render.Bounds
import com.willoutwest.kalahari.render.Coords
import com.willoutwest.kalahari.render.bounds
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec

/**
 * Test suite for [NaturalDrawingOrder].
 */
class NaturalDrawingOrderTest : ShouldSpec() {

    val order = NaturalDrawingOrder()

    init {

        "Ordering a bounds of pixels naturally" {
            should("produce coordinates in increasing, row-major order") {
                assertAll(Gen.bounds(1, 1, 100, 100)) { bounds: Bounds ->
                    val pixels = order.orderOf(bounds)
                    val naturalOrder = mutableListOf<Coords>()
                    var counter = 0

                    for(i in bounds.y until bounds.y + bounds.height) {
                        for(j in bounds.x until bounds.x + bounds.width) {
                            naturalOrder.add(Coords(j, i))

                            counter += 1
                        }
                    }

                    pixels.size shouldBe counter
                    pixels.shouldBe(naturalOrder)
                }
            }
        }
    }
}