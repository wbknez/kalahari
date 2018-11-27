package com.solsticesquared.kalahari.math

/**
 * Represents a vector in three dimensional Cartesian space.
 *
 * @property magnitude
 *           The length of this vector.
 * @property magnitudeSquared
 *           The length of this vector squared.
 */
class Vector3(x: Float = 0.0f, y: Float = 0.0f, z: Float = 0.0f)
    : Cloneable, Tuple3(x, y, z) {

    companion object {

        /**
         * The generalized unit vector.
         */
        val Unit = Vector3(1f, 1f, 1f)

        /**
         * The x-axis unit vector.
         */
        val X    = Vector3(1f, 0f, 0f)

        /**
         * The y-axis unit vector.
         */
        val Y    = Vector3(0f, 1f, 0f)

        /**
         * The z-axis unit vector.
         */
        val Z    = Vector3(0f, 0f, 1f)

        /**
         * The zero vector.
         */
        val Zero = Vector3(0f, 0f, 0f)
    }

    val magnitude: Float
        get() = MathUtils.sqrt(this.magnitudeSquared)

    val magnitudeSquared: Float
        get() = this.x * this.x + this.y * this.y + this.z * this.z

    /**
     * Constructor.
     *
     * @param vec
     *        The vector to copy from.
     */
    constructor(vec: Vector3?) : this(vec!!.x, vec.y, vec.z)

    override fun clone(): Vector3 = Vector3(this)

    /**
     * Computes the cross product of this vector with the specified one.
     *
     * @param vec
     *        The vector to cross with.
     * @return The cross product.
     */
    fun cross(vec: Vector3): Vector3 =
        Vector3(
            this.y * vec.z - this.z * vec.y,
            this.z * vec.x - this.x * vec.z,
            this.x * vec.y - this.y * vec.x
        )

    /**
     * Computes the cross product of this vector with the specified one and
     * also modifies this vector as a result.
     *
     * @param vec
     *        The vector to cross with.
     * @return A reference to this vector for easy chaining.
     */
    fun crossSelf(vec: Vector3): Vector3 {
        val cX = this.y * vec.z - this.z * vec.y
        val cY = this.z * vec.x - this.x * vec.z
        val cZ = this.x * vec.y - this.y * vec.x

        this.x = cX
        this.y = cY
        this.z = cZ
        return this
    }

    /**
     * Computes the distance between this vector and the specified one.
     *
     * @param vec
     *        The vector to use.
     * @return The distance between two vectors.
     */
    fun distanceTo(vec: Vector3): Float {
        val dX = this.x - vec.x
        val dY = this.y - vec.y
        val dZ = this.z - vec.z
        return MathUtils.sqrt(dX * dX + dY * dY + dZ * dZ)
    }

    /**
     * Computes the squared distance between this vector and the specified one.
     *
     * @param vec
     *        The vector to use.
     * @return The squared distance.
     */
    fun distanceSquaredTo(vec: Vector3): Float {
        val dX = this.x - vec.x
        val dY = this.y - vec.y
        val dZ = this.z - vec.z
        return dX * dX + dY * dY + dZ * dZ
    }

    operator fun div(scalar: Float): Vector3 {
        val inv = 1f / scalar
        return Vector3(this.x * inv, this.y * inv, this.z * inv)
    }

    /**
     * Divides the components of this vector by the specified scalar and also
     * modifies this vector as a result.
     *
     * @param scalar
     *        The scalar to divide by.
     * @return A reference to this vector for easy chaining.
     */
    fun divSelf(scalar: Float): Vector3 {
        val inv = 1f / scalar
        this.x *= inv; this.y *= inv; this.z *= inv
        return this
    }

    /**
     * Computes the dot product between this vector and the specified one.
     *
     * @param vec
     *        The vector use.
     * @return The dot product.
     */
    fun dot(vec: Vector3): Float =
        this.x * vec.x + this.y * vec.y + this.z * vec.z

    /**
     * Computes the inverse of this vector.
     *
     * @return An inverted vector.
     */
    fun invert(): Vector3 =
        Vector3(
            MathUtils.safeInverse(this.x),
            MathUtils.safeInverse(this.y),
            MathUtils.safeInverse(this.z)
        )

    /**
     * Computes the inverse of this vector and also modifies this vector as a
     * result.
     *
     * @return A reference to this vector for easy chaining.
     */
    fun invertSelf(): Vector3 {
        this.x = MathUtils.safeInverse(this.x)
        this.y = MathUtils.safeInverse(this.y)
        this.z = MathUtils.safeInverse(this.z)
        return this
    }

    operator fun minus(vec: Vector3): Vector3 =
        Vector3(this.x - vec.x, this.y - vec.y, this.z - vec.z)

    /**
     * Subtracts this vector from the specified vector and also modifies this
     * vector as a result.
     *
     * @param vec
     *        The vector to subtract.
     * @return A reference to this vector for easy chaining.
     */
    fun minusSelf(vec: Vector3): Vector3 {
        this.x -= vec.x
        this.y -= vec.y
        this.z -= vec.z
        return this
    }

    /**
     * Negates this vector and also modifies this vector as a result.
     *
     * @return A negated vector.
     */
    fun negateSelf(): Vector3 {
        this.x = -this.x
        this.y = -this.y
        this.z = -this.z
        return this
    }

    /**
     * Computes the normalization of this vector by dividing each component
     * by the magnitude, yielding a new vector with a magnitude of one.
     *
     * @return A normalized vector.
     */
    fun normalize(): Vector3 {
        val mag = this.magnitude
        val vec = Vector3(this)

        if(mag != 0f) {
            val inv = 1f / mag

            vec.x *= inv
            vec.y *= inv
            vec.z *= inv
        }

        return vec
    }

    /**
     * Computes the normalization of this vector by dividing each component
     * by the magnitude, yielding a new vector with a magnitude of one and
     * also modifies this vector as a result.
     *
     * @return A reference to this vector for easy chaining.
     */
    fun normalizeSelf(): Vector3 {
        val mag = this.magnitude

        if(mag != 0f) {
            val inv = 1f / mag

            this.x *= inv
            this.y *= inv
            this.z *= inv
        }

        return this
    }

    operator fun plus(vec: Vector3): Vector3 =
        Vector3(this.x + vec.x, this.y + vec.y, this.z + vec.z)

    /**
     * Adds this vector with the specified one and also modifies this vector
     * as a result.
     *
     * @param vec
     *        The vector to add.
     * @return A reference to this vector for easy chaining.
     */
    fun plusSelf(vec: Vector3): Vector3 {
        this.x += vec.x
        this.y += vec.y
        this.z += vec.z
        return this
    }

    override fun set(x: Float, y: Float, z: Float): Vector3 {
        this.x = x
        this.y = y
        this.z = z
        return this
    }

    override fun set(tuple: Tuple3?): Vector3 {
        this.x = tuple!!.x
        this.y = tuple.y
        this.z = tuple.z
        return this
    }

    operator fun times(scalar: Float): Vector3 =
        Vector3(this.x * scalar, this.y * scalar, this.z * scalar)

    /**
     * Multiplies this vector by the specified scalar and also modifies this
     * vector as a result.
     *
     * @param scalar
     *        The value to use.
     * @return A reference to this vector for easy chaining.
     */
    fun timesSelf(scalar: Float): Vector3 {
        this.x *= scalar
        this.y *= scalar
        this.z *= scalar
        return this
    }

    operator fun unaryMinus(): Vector3 =
        Vector3(-this.x, -this.y, -this.z)
}
