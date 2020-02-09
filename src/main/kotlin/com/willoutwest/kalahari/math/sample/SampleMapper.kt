package com.willoutwest.kalahari.math.sample

import com.willoutwest.kalahari.math.Point2

/**
 * Represents a mechanism for mapping sample points from a unit square, or
 * grid, to other geometries.
 */
interface SampleMapper<T> {

    /**
     * Converts the specified collection of samples points on a unit square, or
     * grid, to points on an another geometry.
     *
     * @param points
     *        The collection of sample points to map.
     * @return A collection of mapped sample points.
     */
    fun map(points: List<Point2>): List<T>
}