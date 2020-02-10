package com.willoutwest.kalahari.render.orders

import com.willoutwest.kalahari.math.RandomUtils
import com.willoutwest.kalahari.render.Bounds
import com.willoutwest.kalahari.render.Coords
import com.willoutwest.kalahari.render.DrawingOrder
import com.willoutwest.kalahari.util.fisherShuffle

/**
 * Represents an implementation of [DrawingOrder] that organizes pixel
 * coordinates randomly.
 */
class RandomDrawingOrder : DrawingOrder {

    override fun orderOf(bounds: Bounds): List<Coords> {
        val coords = MutableList(bounds.area) {
            Coords(bounds.x + (it % bounds.width),
                   bounds.y + (it / bounds.width))
        }

        coords.fisherShuffle(RandomUtils.localRandom)

        return coords
    }
}