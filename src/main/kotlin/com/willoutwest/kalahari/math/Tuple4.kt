package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.util.hash

/**
 * Represents a vector-like mathematical object in four dimensional space.
 *
 * @property x
 *           The x-axis component.
 * @property y
 *           The y-axis component.
 * @property z
 *           The z-axis component.
 * @property w
 *           The w-axis component.
 */
open class Tuple4(var x: Float = 0.0f,
                  var y: Float = 0.0f,
                  var z: Float = 0.0f,
                  var w: Float = 0.0f) : Cloneable {

    /**
     * Constructor.
     *
     * @param array
     *        The array to copy from.
     */
    constructor(array: FloatArray)
        : this(array[0], array[1], array[2], array[3])

    /**
     * Constructor.
     *
     * @param tuple
     *        The tuple to copy from.
     */
    constructor(tuple: Tuple4?) : this(tuple!!.x, tuple.y, tuple.z, tuple.w)

    public override fun clone(): Tuple4 = Tuple4(this)

    operator fun component1(): Float = this.x
    operator fun component2(): Float = this.y
    operator fun component3(): Float = this.z
    operator fun component4(): Float = this.w

    override fun equals(other: Any?): Boolean =
        when(other) {
            is Tuple4 -> this.x == other.x && this.y == other.y &&
                         this.z == other.z && this.w == other.w
            else -> false
        }

    operator fun get(index: Int): Float =
        when(index) {
            0 -> this.x
            1 -> this.y
            2 -> this.z
            3 -> this.w
            else -> throw IndexOutOfBoundsException(
                "Invalid tuple index: ${index}."
            )
        }

    override fun hashCode(): Int = hash(this.x, this.y, this.z, this.w)

    operator fun set(index: Int, value: Float) {
        when(index) {
            0 -> this.x = value
            1 -> this.y = value
            2 -> this.z = value
            3 -> this.w = value
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
     * @param z
     *        The z-axis value to use.
     * @param w
     *        The w-axis value to use.
     * @return A reference to this tuple for easy chaining.
     */
    open fun set(x: Float, y: Float, z: Float, w: Float): Tuple4 {
        this.x = x
        this.y = y
        this.z = z
        this.w = w

        return this
    }

    /**
     * Sets the components of this tuple to those of the specified array.
     *
     * @param array
     *        The array to copy from.
     * @return A reference to this tuple for easy chaining.
     */
    open fun set(array: FloatArray): Tuple4 {
        this.x = array[0]
        this.y = array[1]
        this.z = array[2]
        this.w = array[3]

        return this
    }

    /**
     * Sets the components of this tuple to those of the specified one.
     *
     * @param tuple
     *        The tuple to copy from.
     * @return A reference to this tuple for easy chaining.
     */
    open fun set(tuple: Tuple4?): Tuple4 {
        this.x = tuple!!.x
        this.y = tuple.y
        this.z = tuple.z
        this.w = tuple.w

        return this
    }

    /**
     * Converts this tuple to a four element floating-point array.
     *
     * @return A tuple as an array.
     */
    fun toArray(): FloatArray = floatArrayOf(this.x, this.y, this.z, this.w)

    override fun toString(): String = "(${this.x}, ${this.y}, ${this.z}, " +
                                      "${this.w})"
}