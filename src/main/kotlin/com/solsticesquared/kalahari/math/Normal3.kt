package com.solsticesquared.kalahari.math

import com.solsticesquared.kalahari.util.hash

/**
 * Represents an implementation of [Vector3] whose magnitude is always
 * guaranteed to be one.
 */
class Normal3(x: Float = 0.0f, y: Float = 1.0f, z: Float = 0.0f,
              normalize: Boolean = true)
    : Cloneable, Vector3(x, y, z) {

    /**
     * Constructor.
     *
     * @param x
     *        The x-axis coordinate to use.
     * @param y
     *        The y-axis coordinate to use.
     * @param z
     *        The z-axis coordinate to use.
     */
    constructor(x: Float = 0.0f, y: Float = 0.0f, z: Float = 0.0f)
        : this(x, y, z, true)

    /**
     * Constructor.
     *
     * @param normal
     *        The normal vector to copy from.
     */
    constructor(normal: Normal3?) : this(normal!!.x, normal.y, normal.z)

    init {
        if(normalize) {
            this.normalizeSelf()
        }
    }

    override fun clone(): Normal3 = Normal3(this.x, this.y, this.z, false)

    override operator fun div(scalar: Float): Normal3 {
        val inv = 1f / scalar
        return Normal3(this.x * inv, this.y * inv, this.z * inv)
    }

    /**
     * Divides the components of this normal by the specified scalar and also
     * modifies this normal as a result.
     *
     * @param scalar
     *        The scalar to divide by.
     * @return A reference to this normal for easy chaining.
     */
    override fun divSelf(scalar: Float): Normal3 {
        val inv = 1f / scalar

        this.x *= inv
        this.y *= inv
        this.z *= inv
        return this.normalizeSelf()
    }

    override fun equals(other: Any?): Boolean =
        when(other) {
            is Normal3 -> this.x == other.x && this.y == other.y
            else       -> false
        }

    override fun hashCode(): Int = hash(this.x, this.y, this.z)

    /**
     * Negates this normal and also modifies this normal as a result.
     *
     * @return A negated normal.
     */
    override fun negateSelf(): Normal3 {
        this.x = -this.x
        this.y = -this.y
        this.z = -this.z
        return this
    }

    /**
     * Computes the normalization of this normal by dividing each component
     * by the magnitude, yielding a new normal with a magnitude of one and
     * also modifies this normal as a result.
     *
     * @return A reference to this normal for easy chaining.
     */
    override fun normalizeSelf(): Normal3 {
        val mag = this.magnitude

        if(mag != 0f && mag != 1f) {
            val inv = 1f / mag

            this.x *= inv
            this.y *= inv
            this.z *= inv
        }

        return this
    }

    operator fun plus(normal: Normal3): Normal3 =
        Normal3(this.x + normal.x, this.y + normal.y, this.z + normal.z)

    /**
     * Adds this normal with the specified one and also modifies this normal
     * as a result.
     *
     * @param normal
     *        The normal to add.
     * @return A reference to this normal for easy chaining.
     */
    fun plusSelf(normal: Normal3): Normal3 {
        this.x += normal.x
        this.y += normal.y
        this.z += normal.z
        return this.normalizeSelf()
    }

    override fun set(x: Float, y: Float, z: Float): Normal3 {
        this.x = x
        this.y = y
        this.z = z
        return this.normalizeSelf()
    }

    override fun set(tuple: Tuple3?): Normal3 {
        this.x = tuple!!.x
        this.y = tuple.y
        this.z = tuple.z
        return this.normalizeSelf()
    }

    override operator fun times(scalar: Float): Normal3 =
        Normal3(this.x * scalar, this.y * scalar, this.z * scalar)

    /**
     * Multiplies this normal by the specified scalar and also modifies this
     * normal as a result.
     *
     * @param scalar
     *        The value to use.
     * @return A reference to this normal for easy chaining.
     */
    override fun timesSelf(scalar: Float): Normal3 {
        this.x *= scalar
        this.y *= scalar
        this.z *= scalar
        return this.normalizeSelf()
    }

    override operator fun unaryMinus(): Normal3 =
        Normal3(-this.x, -this.y, -this.z, false)
}
