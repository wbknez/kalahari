package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.util.hash

/**
 * Represents a vector-like mathematical object in three dimensional space.
 *
 * @property x
 *           The x-axis component.
 * @property y
 *           The y-axis component.
 * @property z
 *           The z-axis component.
 */
open class Tuple3(@JvmField var x: Float = 0.0f,
                  @JvmField var y: Float = 0.0f,
                  @JvmField var z: Float = 0.0f) : Cloneable {

    /**
     * Constructor.
     *
     * @param array
     *        The array to copy from.
     */
    constructor(array: FloatArray)
        : this(array[0], array[1], array[2])

    /**
     * Constructor.
     *
     * @param tuple
     *        The tuple to copy from.
     */
    constructor(tuple: Tuple3?) : this(tuple!!.x, tuple.y, tuple.z)

    public override fun clone(): Tuple3 = Tuple3(this)

    operator fun component1(): Float = this.x
    operator fun component2(): Float = this.y
    operator fun component3(): Float = this.z

    override fun equals(other: Any?): Boolean =
        when(other) {
            is Tuple3 -> this.x == other.x && this.y == other.y &&
                         this.z == other.z
            else -> false
        }

    operator fun get(index: Int): Float =
        when(index) {
            0 -> this.x
            1 -> this.y
            2 -> this.z
            else -> throw IndexOutOfBoundsException(
                    "Invalid tuple index: ${index}."
            )
        }

    override fun hashCode(): Int = hash(this.x, this.y, this.z)

    operator fun set(index: Int, value: Float) {
        when(index) {
            0 -> this.x = value
            1 -> this.y = value
            2 -> this.z = value
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
     * @return A reference to this tuple for easy chaining.
     */
    open fun set(x: Float, y: Float, z: Float): Tuple3 {
        this.x = x
        this.y = y
        this.z = z

        return this
    }

    /**
     * Sets the components of this tuple to those of the specified array.
     *
     * @param array
     *        The array to copy from.
     * @return A reference to this tuple for easy chaining.
     */
    open fun set(array: FloatArray): Tuple3 =
        this.set(array[0], array[1], array[2])

    /**
     * Sets the components of this tuple to those of the specified one.
     *
     * @param tuple
     *        The tuple to copy from.
     * @return A reference to this tuple for easy chaining.
     */
    open fun set(tuple: Tuple3?): Tuple3 =
        this.set(tuple!!.x, tuple.y, tuple.z)

    /**
     * Converts this tuple to a three element floating-point array.
     *
     * @return A tuple as an array.
     */
    fun toArray(): FloatArray = floatArrayOf(this.x, this.y, this.z)

    override fun toString(): String = "(${this.x}, ${this.y}, ${this.z})"
}
