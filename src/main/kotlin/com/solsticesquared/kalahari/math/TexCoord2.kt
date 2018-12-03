package com.solsticesquared.kalahari.math

/**
 * Represents a pair of coordinates in two-dimensional texture space.
 *
 * @property u
 *           The u-axis texture component.
 * @property v
 *           The v-axis texture component.
 */
class TexCoord2(u: Float = 0.0f, v: Float = 0.0f) : Cloneable, Tuple2(u, v) {

    var u: Float
        get() = this.x
        set(value) { this.x = value }

    var v: Float
        get() = this.y
        set(value) { this.y = value }

    /**
     * Constructor.
     *
     * @param tuple
     *        The tuple to copy from.
     */
    constructor(tuple: Tuple2?) : this(tuple!!.x, tuple.y)

    override fun clone(): TexCoord2 = TexCoord2(this)

    override fun set(x: Float, y: Float): TexCoord2 {
        this.u = x
        this.v = y
        return this
    }

    override fun set(tuple: Tuple2?): TexCoord2 {
        this.u = tuple!!.x
        this.v = tuple.y
        return this
    }
}
