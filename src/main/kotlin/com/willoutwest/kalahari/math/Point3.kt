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
        MathUtils.sqrt(this.distanceSquaredTo(point))

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
    fun distanceSquaredTo(point: Point3): Float {
        val dX = this.x - point.x
        val dY = this.y - point.y
        val dZ = this.z - point.z

        return dX * dX + dY * dY + dZ * dZ
    }

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
        Point3(this.x - point.x, this.y - point.y, this.z - point.z)

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
    fun minusSelf(point: Point3): Point3 {
        this.x -= point.x
        this.y -= point.y
        this.z -= point.z

        return this
    }

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
        Point3(this.x + point.x, this.y + point.y, this.z + point.z)

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
    fun plusSelf(point: Point3): Point3 {
        this.x += point.x
        this.y += point.y
        this.z += point.z

        return this
    }

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

    operator fun unaryMinus(): Point3 =
        Point3(-this.x, -this.y, -this.z)
}