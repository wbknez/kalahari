package com.solsticesquared.kalahari.render.order

import com.solsticesquared.kalahari.math.Bounds
import com.solsticesquared.kalahari.math.Coords

/**
 * Represents an implementation of [DrawingOrder] that organizes pixel
 * coordinates in naturally increasing order, first along the x- and then the
 * y-axes.
 */
class NaturalDrawingOrder : DrawingOrder {

    override fun create(bounds: Bounds): List<Coords> = List(bounds.area) {
        Coords(bounds.x + (it % bounds.width),
               bounds.y + (it / bounds.width))
    }
}
