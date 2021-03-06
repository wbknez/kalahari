package com.willoutwest.kalahari.math

/**
 * Represents a single point in three dimensional Cartesian space.
 */
class Point3(x: Float = 0f, y: Float = 0f, z: Float = 0f)
    : Cloneable, Tuple3(x, y, z) {

    companion object {

        /**
         * The generalized unit point.
         */
        val Unit = Point3(1f, 1f, 1f)

        /**
         * The x-axis unit point.
         */
        val X    = Point3(1f, 0f, 0f)

        /**
         * The y-axis unit point.
         */
        val Y    = Point3(0f, 1f, 0f)

        /**
         * The z-axis unit point.
         */
        val Z    = Point3(0f, 0f, 1f)

        /**
         * The origin point.
         */
        val Zero = Point3(0f, 0f, 0f)
    }

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

    override fun clone(): Point3 = Point3(this)

    /**
     * Computes the distance between this point and the specified x-, y-,
     * and z-axis components.
     *
     * @param x
     *        The x-axis component to use.
     * @param y
     *        The y-axis component to use.
     * @param z
     *        The z-axis component to use.
     * @return The distance between a point and three components.
     */
    fun distanceTo(x: Float, y: Float, z: Float): Float =
        MathUtils.sqrt(this.distanceSquaredTo(x, y, z))

    /**
     * Computes the distance between this point and the specified one.
     *
     * @param point
     *        The point to use.
     * @return The distance between two points.
     */
    fun distanceTo(point: Point3): Float =
        this.distanceTo(point.x, point.y, point.z)

    /**
     * Computes the squared distance between this point and the specified x-,
     * y-, and z-axis components.
     *
     * @param x
     *        The x-axis component to use.
     * @param y
     *        The y-axis component to use.
     * @param z
     *        The z-axis component to use.
     * @return The squared distance between a point and three components.
     */
    fun distanceSquaredTo(x: Float, y: Float, z: Float): Float {
        val dX = this.x - x
        val dY = this.y - y
        val dZ = this.z - z

        return dX * dX + dY * dY + dZ * dZ
    }

    /**
     * Computes the squared distance between this point and the specified one.
     *
     * @param point
     *        The point to use.
     * @return The distance between two points.
     */
    fun distanceSquaredTo(point: Point3): Float =
        this.distanceSquaredTo(point.x, point.y, point.z)

    operator fun div(scalar: Float): Point3 {
        val inv = 1f / scalar

        return Point3(this.x * inv, this.y * inv, this.z * inv)
    }

    /**
     * Divides this point by the specified scalar and also modifies this
     * point as a result.
     *
     * @param scalar
     *        The value to divide.
     * @return A reference to this point for easy chaining.
     */
    fun divSelf(scalar: Float): Point3 {
        val inv = 1f / scalar

        this.x *= inv
        this.y *= inv
        this.z *= inv

        return this
    }

    /**
     * Subtracts this point from the specified x-, y-, and z-axis components.
     *
     * @param x
     *        The x-axis component to subtract.
     * @param y
     *        The y-axis component to subtract.
     * @param z
     *        The z-axis component to subtract.
     * @return The difference between a point and three components.
     */
    fun minus(x: Float, y: Float, z: Float): Point3 =
        Point3(this.x - x, this.y - y, this.z - z)

    operator fun minus(point: Point3): Point3 =
        this.minus(point.x, point.y, point.z)

    /**
     * Subtracts this point from the specified x-, y-, and z-axis components
     * and modifies this point as a result.
     *
     * @param x
     *        The x-axis component to subtract.
     * @param y
     *        The y-axis component to subtract.
     * @param z
     *        The z-axis component to subtract.
     * @return A reference to this point for easy chaining.
     */
    fun minusSelf(x: Float, y: Float, z: Float): Point3 {
        this.x -= x
        this.y -= y
        this.z -= z

        return this
    }

    /**
     * Subtracts this point from the specified one and also modifies this
     * point as a result.
     *
     * @param point
     *        The point to subtract.
     * @return A reference to this point for easy chaining.
     */
    fun minusSelf(point: Point3): Point3 =
        this.minusSelf(point.x, point.y, point.z)

    /**
     * Negates this point and also modifies this point as a result.
     *
     * @return A reference to this point for easy chaining.
     */
    fun negateSelf(): Point3 {
        this.x = -this.x
        this.y = -this.y
        this.z = -this.z

        return this
    }

    /**
     * Adds this point to the specified x-, y-, and z-axis components.
     *
     * @param x
     *        The x-axis component to add.
     * @param y
     *        The y-axis component to add.
     * @param z
     *        The z-axis component to add.
     * @return The sum of a point and three components.
     */
    fun plus(x: Float, y: Float, z: Float): Point3 =
        Point3(this.x + x, this.y + y, this.z + z)

    operator fun plus(point: Point3): Point3 =
        this.plus(point.x, point.y, point.z)

    /**
     * Adds this point to the specified x-, y-, and z-axis components and
     * modifies this point as a result.
     *
     * @param x
     *        The x-axis component to add.
     * @param y
     *        The y-axis component to add.
     * @param z
     *        The z-axis component to add.
     * @return A reference to this point for easy chaining.
     */
    fun plusSelf(x: Float, y: Float, z: Float): Point3 {
        this.x += x
        this.y += y
        this.z += z

        return this
    }

    /**
     * Adds this point to the specified one and also modifies this point as a
     * result.
     *
     * @param point
     *        The point to add.
     * @return A reference to this point for easy chaining.
     */
    fun plusSelf(point: Point3): Point3 =
        this.plusSelf(point.x, point.y, point.z)

    override fun set(x: Float, y: Float, z: Float): Point3 =
        super.set(x, y, z) as Point3

    override fun set(array: FloatArray): Point3 = super.set(array) as Point3

    override fun set(tuple: Tuple3?): Point3 = super.set(tuple) as Point3

    operator fun times(scalar: Float): Point3 =
        Point3(this.x * scalar, this.y * scalar, this.z * scalar)

    /**
     * Multiplies this point with the specified scalar and also modifies this
     * point as a result.
     *
     * @param scalar
     *        The value to multiply.
     * @return A reference to this point for easy chaining.
     */
    fun timesSelf(scalar: Float): Point3 {
        this.x *= scalar
        this.y *= scalar
        this.z *= scalar

        return this
    }

    /**
     * Transforms this point using the specified matrix.
     *
     * @param mat
     *        The transformation matrix to use.
     * @return A transformed point.
     */
    fun transform(mat: Matrix4): Point3 =
        Point3(
            this.x * mat.t00 + this.y * mat.t01 + this.z * mat.t02 + mat.t03,
            this.x * mat.t10 + this.y * mat.t11 + this.z * mat.t12 + mat.t13,
            this.x * mat.t20 + this.y * mat.t21 + this.z * mat.t22 + mat.t23
        )

    /**
     * Transforms this point using the specified matrix and modifies this
     * point as a result.
     *
     * @param mat
     *        The transformation matrix to use.
     * @return A reference to this point for easy chaining.
     */
    fun transformSelf(mat: Matrix4): Point3 {
        val vX = this.x
        val vY = this.y
        val vZ = this.z

        this.x = vX * mat.t00 + vY * mat.t01 + vZ * mat.t02 + mat.t03
        this.y = vX * mat.t10 + vY * mat.t11 + vZ * mat.t12 + mat.t13
        this.z = vX * mat.t20 + vY * mat.t21 + vZ * mat.t22 + mat.t23

        return this
    }

    operator fun unaryMinus(): Point3 =
        Point3(-this.x, -this.y, -this.z)
}