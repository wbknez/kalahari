package com.willoutwest.kalahari.math.noise

import com.willoutwest.kalahari.math.Point3

/**
 * Represents a mechanism for creating coherent noise.
 */
interface Speaker : Cloneable {

    public override fun clone(): Speaker

    /**
     * Computes a single noise-based value using the specified position
     * components.
     *
     * @param x
     *        The x-axis component to use.
     * @param y
     *        The y-axis component to use.
     * @param z
     *        The z-axis component to use.
     * @return A single noise-based value.
     */
    fun output(x: Float, y: Float, z: Float): Float

    /**
     * Computes a single noise-based value using the specified point.
     *
     * @param point
     *        The point to use.
     * @return A single noise-based value.
     */
    fun output(point: Point3): Float = this.output(point.x, point.y, point.z)
}