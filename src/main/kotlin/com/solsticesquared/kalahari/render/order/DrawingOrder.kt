package com.solsticesquared.kalahari.render.order

import com.solsticesquared.kalahari.math.Bounds
import com.solsticesquared.kalahari.math.Coords

/**
 * Represents a mechanism for choosing which pixels should be drawn in what
 * order in a scene.
 */
interface DrawingOrder {

    /**
     * Creates a new collection of pixel coordinates with arbitrary ordering.
     *
     * @param bounds
     *        The total area to cover.
     * @return A list of ordered pixel coordinates.
     */
    fun create(bounds: Bounds): List<Coords>
}
