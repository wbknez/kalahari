package com.willoutwest.kalahari.render

/**
 * Represents a two-dimensional space of finite area.
 *
 * @property area
 *           The total coverable area; for purposes of rendering, this is
 *           equivalent to the number of pixels necessary to cover a viewport
 *           of a given size.
 * @property height
 *           The extent along the y-axis.
 * @property width
 *           The extent along the x-axis.
 * @property x
 *           The origin on the x-axis.
 * @property y
 *           The origin on the y-axis.
 */
data class Bounds(val x: Int, val y: Int, val width: Int, val height: Int)
    : Cloneable {

    /**
     * Constructor.
     *
     * @param width
     *        The extent along the x-axis.
     * @param height
     *        The extent along the y-axis.
     */
    constructor(width: Int, height: Int) : this(0, 0, width, height)

    val area: Int
        get() = (this.width - this.x) * (this.height - this.y)
}
