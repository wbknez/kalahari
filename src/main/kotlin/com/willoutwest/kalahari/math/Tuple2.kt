package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.util.hash

/**
 * Represents a vector-like mathematical object in two dimensional space.
 *
 * @property x
 *           The x-axis component.
 * @property y
 *           The y-axis component.
 */
open class Tuple2(@JvmField var x: Float = 0.0f,
                  @JvmField var y: Float = 0.0f) : Cloneable {

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
                "Invalid tuple index: ${index}."
            )
        }

    override fun hashCode(): Int = hash(this.x, this.y)

    operator fun set(index: Int, value: Float) {
        when(index) {
            0 -> this.x = value
            1 -> this.y = value
            else -> throw IndexOutOfBoundsException(
                "Invalid tuple index: ${index}."
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
     * Sets the components of this tuple to those of the specified array.
     *
     * @param array
     *        The array to copy from.
     * @return A reference to this tuple for easy chaining.
     */
    open fun set(array: FloatArray): Tuple2 {
        this.x = array[0]
        this.y = array[1]

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

    /**
     * Converts this tuple to a two element floating-point array.
     *
     * @return A tuple as an array.
     */
    fun toArray(): FloatArray = floatArrayOf(this.x, this.y)

    override fun toString(): String = "(${this.x}, ${this.y})"
}