package com.willoutwest.kalahari.math

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
     * @param array
     *        The array to copy from.
     */
    constructor(array: FloatArray) : this(array[0], array[1])

    /**
     * Constructor.
     *
     * @param tuple
     *        The tuple to copy from.
     */
    constructor(tuple: Tuple2?) : this(tuple!!.x, tuple.y)

    override fun clone(): TexCoord2 = TexCoord2(this)

    override fun set(x: Float, y: Float): TexCoord2 =
        super.set(x, y) as TexCoord2

    override fun set(array: FloatArray): TexCoord2 =
        super.set(array) as TexCoord2

    override fun set(tuple: Tuple2?): TexCoord2 = super.set(tuple) as TexCoord2
}