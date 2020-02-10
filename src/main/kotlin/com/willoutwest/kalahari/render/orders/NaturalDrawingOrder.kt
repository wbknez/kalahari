package com.willoutwest.kalahari.render.orders

import com.willoutwest.kalahari.render.Bounds
import com.willoutwest.kalahari.render.Coords
import com.willoutwest.kalahari.render.DrawingOrder

/**
 * Represents an implementation of [DrawingOrder] that organizes pixel
 * coordinates in naturally increasing order, first along the x- and then the
 * y-axes.
 */
class NaturalDrawingOrder : DrawingOrder {

    override fun orderOf(bounds: Bounds): List<Coords> = List(bounds.area) {
        Coords(bounds.x + (it % bounds.width),
               bounds.y + (it / bounds.width))
    }
}
