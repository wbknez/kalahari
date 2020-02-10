package com.willoutwest.kalahari.render.orders

import com.willoutwest.kalahari.render.Bounds
import com.willoutwest.kalahari.render.Coords
import com.willoutwest.kalahari.render.bounds
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.ShouldSpec

/**
 * Test suite for [RandomDrawingOrder].
 */
class RandomDrawingOrderTest : ShouldSpec() {

    val order = RandomDrawingOrder()

    init {

        "Ordering a bounds of pixels randomly" {
            should("produce random coordinates in increasing, row-major " +
                   "order") {
                assertAll(Gen.bounds(xMax=1, yMax=1, widthMin=19,
                                     widthMax=20, heightMin=19,
                                     heightMax= 20)) { bounds: Bounds ->
                    val coords       = order.orderOf(bounds)
                    val naturalOrder = mutableListOf<Coords>()
                    var counter      = 0

                    for(i in bounds.y until bounds.y + bounds.height) {
                        for(j in bounds.x until bounds.x + bounds.width) {
                            naturalOrder.add(Coords(j, i))

                            counter += 1
                        }
                    }

                    coords.size shouldBe counter
                    coords.shouldNotBe(naturalOrder)
                }
            }
        }
    }
}