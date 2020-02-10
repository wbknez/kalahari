package com.willoutwest.kalahari.render.orders

import com.willoutwest.kalahari.render.Bounds
import com.willoutwest.kalahari.render.Coords
import com.willoutwest.kalahari.render.orders.InterleavedDrawingOrder.Parity
import io.kotlintest.data.forall
import io.kotlintest.properties.Gen
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import io.kotlintest.tables.row

/**
 * Test suite for [InterleavedDrawingOrder].
 */
class InterleavedDrawingOrderTest : ShouldSpec() {

    companion object {

        fun basicOrderOf(width: Int, height: Int): List<Coords> =
            List(width * height) { Coords(it % width, it / width) }

        fun evenOrderOfX(width: Int, height: Int): List<Coords> {
            val coords = mutableListOf<Coords>()

            for(y in 0 until height step 2) {
                for(x in 0 until width step 2) {
                    coords.add(Coords(x, y))
                }
            }

            for(y in 0 until height step 2) {
                for(x in 1 until width step 2) {
                    coords.add(Coords(x, y))
                }
            }

            for(y in 1 until height step 2) {
                for(x in 0 until width step 2) {
                    coords.add(Coords(x, y))
                }
            }

            for(y in 1 until height step 2) {
                for(x in 1 until width step 2) {
                    coords.add(Coords(x, y))
                }
            }

            return coords
        }

        fun oddOrderOfX(width: Int, height: Int): List<Coords> {
            val coords = mutableListOf<Coords>()

            for(y in 0 until height step 2) {
                for(x in 1 until width step 2) {
                    coords.add(Coords(x, y))
                }
            }

            for(y in 1 until height step 2) {
                for(x in 0 until width step 2) {
                    coords.add(Coords(x, y))
                }
            }

            for(y in 1 until height step 2) {
                for(x in 1 until width step 2) {
                    coords.add(Coords(x, y))
                }
            }

            for(y in 0 until height step 2) {
                for(x in 0 until width step 2) {
                    coords.add(Coords(x, y))
                }
            }

            return coords
        }

        fun evenOrderOfY(width: Int, height: Int): List<Coords> {
            val coords = mutableListOf<Coords>()

            for(y in 0 until height step 2) {
                for(x in 0 until width step 2) {
                    coords.add(Coords(x, y))
                }
            }

            for(y in 1 until height step 2) {
                for(x in 0 until width step 2) {
                    coords.add(Coords(x, y))
                }
            }

            for(y in 0 until height step 2) {
                for(x in 1 until width step 2) {
                    coords.add(Coords(x, y))
                }
            }

            for(y in 1 until height step 2) {
                for(x in 1 until width step 2) {
                    coords.add(Coords(x, y))
                }
            }

            return coords
        }

        fun oddOrderOfY(width: Int, height: Int): List<Coords> {
            val coords = mutableListOf<Coords>()

            for(y in 1 until height step 2) {
                for(x in 0 until width step 2) {
                    coords.add(Coords(x, y))
                }
            }

            for(y in 0 until height step 2) {
                for(x in 1 until width step 2) {
                    coords.add(Coords(x, y))
                }
            }

            for(y in 1 until height step 2) {
                for(x in 1 until width step 2) {
                    coords.add(Coords(x, y))
                }
            }

            for(y in 0 until height step 2) {
                for(x in 0 until width step 2) {
                    coords.add(Coords(x, y))
                }
            }

            return coords
        }
    }

    init {

        "Ordering a bounds of interleaved pixels" {
            should("produce the correct result based on the parity") {
//                val width = Gen.choose(1, 20).random().first()
  //              val height = Gen.choose(1, 20).random().first()
                val width = 3
                val height = 3
                val bounds = Bounds(width, height)

                forall(
                    row(Parity.None, Parity.None, basicOrderOf(width, height)),
                    row(Parity.Any,  Parity.Any,  basicOrderOf(width, height)),
                    row(Parity.Even, Parity.Any,  evenOrderOfX(width, height)),
                    row(Parity.Odd,  Parity.Any,  oddOrderOfX(width, height)),
                    row(Parity.Any,  Parity.Even, evenOrderOfY(width, height)),
                    row(Parity.Any,  Parity.Odd,  oddOrderOfY(width, height)))
                {
                    xParity: Parity, yParity: Parity, coords: List<Coords> ->

                    val order    = InterleavedDrawingOrder(xParity, yParity)
                    val ordering = order.orderOf(bounds)

                    ordering.size.shouldBe(bounds.area)
                    ordering.toSet().size.shouldBe(bounds.area)
                }
            }
        }
    }
}