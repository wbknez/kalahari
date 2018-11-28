package com.solsticesquared.kalahari.math

import com.solsticesquared.kalahari.util.hash

/**
 * Represents a vector-like mathematical object in two dimensional space.
 *
 * @property x
 *           The x-axis component.
 * @property y
 *           The y-axis component.
 */
open class Tuple2(var x: Float = 0.0f, var y: Float = 0.0f) : Cloneable {

    /**
     * Constructor.
     *
     * @param tuple
     *        The tuple to copy from.
     */
    constructor(tuple: Tuple2?) : this(tuple!!.x, tuple.y)

    public override fun clone(): Tuple2 = Tuple2(this)

    operator fun component1(): Float = this.x
    operator fun component2(): Float = this.y

    override fun equals(other: Any?): Boolean =
        when(other) {
            is Tuple2 -> this.x == other.x && this.y == other.y
            else -> false
        }

    operator fun get(index: Int): Float =
        when(index) {
            0 -> this.x
            1 -> this.y
            else -> throw IndexOutOfBoundsException(
                "Invalid tuple index: $index"
                                                   )
        }

    override fun hashCode(): Int = hash(this.x, this.y)

    operator fun set(index: Int, value: Float) {
        when(index) {
            0 -> this.x = value
            1 -> this.y = value
            else -> throw IndexOutOfBoundsException(
                "Invalid tuple index: $index"
                                                   )
        }
    }

    /**
     * Sets the components of this tuple to the specified values.
     *
     * @param x
     *        The x-axis value to use.
     * @param y
     *        The y-axis value to use.
     * @return A reference to this tuple for easy chaining.
     */
    open fun set(x: Float, y: Float): Tuple2 {
        this.x = x
        this.y = y
        return this
    }

    /**
     * Sets the components of this tuple to those of the specified one.
     *
     * @param tuple
     *        The tuple to copy from.
     * @return A reference to this tuple for easy chaining.
     */
    open fun set(tuple: Tuple2?): Tuple2 {
        this.x = tuple!!.x
        this.y = tuple.y
        return this
    }

    override fun toString(): String = "(${this.x}, ${this.y})"
}
