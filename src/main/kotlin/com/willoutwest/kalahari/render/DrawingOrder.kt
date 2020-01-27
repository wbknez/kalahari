package com.willoutwest.kalahari.render

/**
 * Represents a mechanism for choosing the order in which to draw pixels in a
 * scene.
 */
@FunctionalInterface
interface DrawingOrder {

    /**
     * Creates a new collection of pixel coordinates with arbitrary ordering.
     *
     * @param bounds
     *        The total area to cover.
     * @return A list of ordered pixel coordinates.
     */
    fun order(bounds: Bounds): List<Coords>
}
