package com.willoutwest.kalahari.math

/**
 * Represents a vector in three dimensional Cartesian space.
 *
 * @property magnitude
 *           The length of this vector.
 * @property magnitudeSquared
 *           The length of this vector squared.
 */
open class Vector3(x: Float = 0.0f, y: Float = 0.0f, z: Float = 0.0f)
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
     * @param array
     *        The array to copy from.
     */
    constructor(array: FloatArray) : this(array[0], array[1], array[2])

    /**
     * Constructor.
     *
     * @param tuple
     *        The tuple to copy from.
     */
    constructor(tuple: Tuple3?) : this(tuple!!.x, tuple.y, tuple.z)

    override fun clone(): Vector3 = Vector3(this)

    /**
     * Computes the cross product of this vector with the specified x-, y-,
     * and z-axis components.
     *
     * @param x
     *        The x-axis component to cross with.
     * @param y
     *        The y-axis component to cross with.
     * @param z
     *        The z-axis component to cross with.
     * @return The cross product.
     */
    open fun cross(x: Float, y: Float, z: Float): Vector3 =
        Vector3(this.y * z - this.z * y,
                this.z * x - this.x * z,
                this.x * y - this.y * x)

    /**
     * Computes the cross product of this vector with the specified one.
     *
     * @param vec
     *        The vector to cross with.
     * @return The cross product.
     */
    open fun cross(vec: Vector3): Vector3 =
        this.cross(vec.x, vec.y, vec.z)

    /**
     * Computes the cross product of this vector with the specified x-, y-,
     * and z-axis components and modifies this vector as a result.
     *
     * @param x
     *        The x-axis component to cross with.
     * @param y
     *        The y-axis component to cross with.
     * @param z
     *        The z-axis component to cross with.
     * @return A reference to this vector for easy chaining.
     */
    open fun crossSelf(x: Float, y: Float, z: Float): Vector3 {
        val cX = this.y * z - this.z * y
        val cY = this.z * x - this.x * z
        val cZ = this.x * y - this.y * x

        this.x = cX
        this.y = cY
        this.z = cZ

        return this
    }

    /**
     * Computes the cross product of this vector with the specified one and
     * also modifies this vector as a result.
     *
     * @param vec
     *        The vector to cross with.
     * @return A reference to this vector for easy chaining.
     */
    open fun crossSelf(vec: Vector3): Vector3 =
        this.crossSelf(vec.x, vec.y, vec.z)

    /**
     * Computes the distance between this vector and the specified x-, y-,
     * and z-axis components.
     *
     * @param x
     *        The x-axis component to use.
     * @param y
     *        The y-axis component to use.
     * @param z
     *        The z-axis component to use.
     * @return The distance between a vector and three components.
     */
    fun distanceTo(x: Float, y: Float, z: Float): Float =
        MathUtils.sqrt(this.distanceSquaredTo(x, y, z))

    /**
     * Computes the distance between this vector and the specified one.
     *
     * @param vec
     *        The vector to use.
     * @return The distance between two vectors.
     */
    fun distanceTo(vec: Vector3): Float = this.distanceTo(vec.x, vec.y, vec.z)

    /**
     * Computes the squared distance between this vector and the specified x-,
     * y-, and z-axis components.
     *
     * @param x
     *        The x-axis component to use.
     * @param y
     *        The y-axis component to use.
     * @param z
     *        The z-axis component to use.
     * @return The squared distance between a vector and three components.
     */
    fun distanceSquaredTo(x: Float, y: Float, z: Float): Float {
        val dX = this.x - x
        val dY = this.y - y
        val dZ = this.z - z

        return dX * dX + dY * dY + dZ * dZ
    }

    /**
     * Computes the squared distance between this vector and the specified one.
     *
     * @param vec
     *        The vector to use.
     * @return The squared distance.
     */
    fun distanceSquaredTo(vec: Vector3): Float =
        this.distanceSquaredTo(vec.x, vec.y, vec.z)

    open operator fun div(scalar: Float): Vector3 {
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
    open fun divSelf(scalar: Float): Vector3 {
        val inv = 1f / scalar

        this.x *= inv
        this.y *= inv
        this.z *= inv

        return this
    }

    /**
     * Computes the dot product between this vector and the specified
     * components.
     *
     * @param x
     *        The x-axis component to use.
     * @param y
     *        The y-axis component to use.
     * @param z
     *        The z-axis component to use.
     */
    fun dot(x: Float, y: Float, z: Float): Float =
        this.x * x + this.y * y + this.z * z

    /**
     * Computes the dot product between this vector and the specified one.
     *
     * @param vec
     *        The vector use.
     * @return The dot product.
     */
    fun dot(vec: Vector3): Float = this.dot(vec.x, vec.y, vec.z)

    /**
     * Computes the inverse of this vector.
     *
     * @return An inverted vector.
     */
    open fun invert(): Vector3 =
        Vector3(MathUtils.safeInverse(this.x),
                MathUtils.safeInverse(this.y),
                MathUtils.safeInverse(this.z))

    /**
     * Computes the inverse of this vector and also modifies this vector as a
     * result.
     *
     * @return A reference to this vector for easy chaining.
     */
    open fun invertSelf(): Vector3 {
        this.x = MathUtils.safeInverse(this.x)
        this.y = MathUtils.safeInverse(this.y)
        this.z = MathUtils.safeInverse(this.z)

        return this
    }

    /**
     * Subtracts this vector from the specified x-, y-, and z-axis components.
     *
     * @param x
     *        The x-axis component to subtract.
     * @param y
     *        The y-axis component to subtract.
     * @param z
     *        The z-axis component to subtract.
     * @return The difference between a vector and three components.
     */
    open fun minus(x: Float, y: Float, z: Float): Vector3 =
        Vector3(this.x - x, this.y - y, this.z - z)

    open operator fun minus(vec: Vector3): Vector3 =
        this.minus(vec.x, vec.y, vec.z)

    /**
     * Subtracts this vector from the specified x-, y-, and z-axis components
     * and modifies this vector as a result.
     *
     * @param x
     *        The x-axis component to subtract.
     * @param y
     *        The y-axis component to subtract.
     * @param z
     *        The z-axis component to subtract.
     * @return A reference to this vector for easy chaining.
     */
    open fun minusSelf(x: Float, y: Float, z: Float): Vector3 {
        this.x -= x
        this.y -= y
        this.z -= z

        return this
    }

    /**
     * Subtracts this vector from the specified vector and also modifies this
     * vector as a result.
     *
     * @param vec
     *        The vector to subtract.
     * @return A reference to this vector for easy chaining.
     */
    open fun minusSelf(vec: Vector3): Vector3 =
        this.minusSelf(vec.x, vec.y, vec.z)

    /**
     * Negates this vector and also modifies this vector as a result.
     *
     * @return A negated vector.
     */
    open fun negateSelf(): Vector3 {
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
    open fun normalize(): Vector3 {
        val mag = this.magnitude
        val vec = Vector3(this)

        if(mag != 0f && mag != 1f) {
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
    open fun normalizeSelf(): Vector3 {
        val mag = this.magnitude

        if(mag != 0f && mag != 1f) {
            val inv = 1f / mag

            this.x *= inv
            this.y *= inv
            this.z *= inv
        }

        return this
    }

    /**
     * Adds this vector to the specified x-, y-, and z-axis components.
     *
     * @param x
     *        The x-axis component to add.
     * @param y
     *        The y-axis component to add.
     * @param z
     *        The z-axis component to add.
     * @return The sum of a vector and three components.
     */
    open fun plus(x: Float, y: Float, z: Float): Vector3 =
        Vector3(this.x + x, this.y + y, this.z + z)

    open operator fun plus(vec: Vector3): Vector3 =
        this.plus(vec.x, vec.y, vec.z)

    /**
     * Adds this vector to the specified x-, y-, and z-axis components and
     * modifies this vector as a result.
     *
     * @param x
     *        The x-axis component to add.
     * @param y
     *        The y-axis component to add.
     * @param z
     *        The z-axis component to add.
     * @return A reference to this vector for easy chaining.
     */
    open fun plusSelf(x: Float, y: Float, z: Float): Vector3 {
        this.x += x
        this.y += y
        this.z += z

        return this
    }

    /**
     * Adds this vector with the specified one and also modifies this vector
     * as a result.
     *
     * @param vec
     *        The vector to add.
     * @return A reference to this vector for easy chaining.
     */
    open fun plusSelf(vec: Vector3): Vector3 =
        this.plusSelf(vec.x, vec.y, vec.z)

    override fun set(x: Float, y: Float, z: Float): Vector3 =
        super.set(x, y, z) as Vector3

    override fun set(array: FloatArray): Vector3 = super.set(array) as Vector3

    override fun set(tuple: Tuple3?): Vector3 = super.set(tuple) as Vector3

    open operator fun times(scalar: Float): Vector3 =
        Vector3(this.x * scalar, this.y * scalar, this.z * scalar)

    /**
     * Multiplies this vector by the specified scalar and also modifies this
     * vector as a result.
     *
     * @param scalar
     *        The value to use.
     * @return A reference to this vector for easy chaining.
     */
    open fun timesSelf(scalar: Float): Vector3 {
        this.x *= scalar
        this.y *= scalar
        this.z *= scalar

        return this
    }

    /**
     * Converts this vector to a scaling matrix.
     *
     * @return A scaling matrix.
     */
    fun toScalingMatrix(): Matrix4 = this.toScalingMatrix(Matrix4())

    /**
     * Converts this vector to a scaling matrix.
     *
     * @param store
     *        The matrix to store the result in.
     * @return A reference to [store] for easy chaining.
     */
    fun toScalingMatrix(store: Matrix4): Matrix4 =
        store.set(this.x, 0f, 0f, 0f,
                  0f, this.y, 0f, 0f,
                  0f, 0f, this.z, 0f,
                  0f, 0f, 0f, 1f)

    /**
     * Converts this vector to a translation matrix.
     *
     * @return A translation matrix.
     */
    fun toTranslationMatrix(): Matrix4 = this.toTranslationMatrix(Matrix4())

    /**
     * Converts this vector to a translation matrix.
     *
     * @param store
     *        The matrix to store the result in.
     * @return A reference to [store] for easy chaining.
     */
    fun toTranslationMatrix(store: Matrix4): Matrix4 =
        store.set(1f, 0f, 0f, this.x,
                  0f, 1f, 0f, this.y,
                  0f, 0f, 1f, this.z,
                  0f, 0f, 0f, 1f)

    /**
     * Transforms this vector using the specified matrix.
     *
     * @param mat
     *        The transformation matrix to use.
     * @return A transformed vector.
     */
    open fun transform(mat: Matrix4): Vector3 =
        Vector3(
            this.x * mat.t00 + this.y * mat.t01 + this.z * mat.t02,
            this.x * mat.t10 + this.y * mat.t11 + this.z * mat.t12,
            this.x * mat.t20 + this.y * mat.t21 + this.z * mat.t22
        )

    /**
     * Transforms this vector using the specified matrix and modifies this
     * vector as a result.
     *
     * @param mat
     *        The transformation matrix to use.
     * @return A reference to this vector for easy chaining.
     */
    open fun transformSelf(mat: Matrix4): Vector3 {
        val vX = this.x
        val vY = this.y
        val vZ = this.z

        this.x = vX * mat.t00 + vY * mat.t01 + vZ * mat.t02
        this.y = vX * mat.t10 + vY * mat.t11 + vZ * mat.t12
        this.z = vX * mat.t20 + vY * mat.t21 + vZ * mat.t22

        return this
    }

    open operator fun unaryMinus(): Vector3 =
        Vector3(-this.x, -this.y, -this.z)
}