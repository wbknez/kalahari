package com.solsticesquared.kalahari.math

/**
 * Represents a single point in three dimensional Cartesian space.
 */
class Point3(x: Float = 0.0f, y: Float = 0.0f, z: Float = 0.0f)
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
     * @param point
     *        The three-dimensional point to copy from.
     */
    constructor(point: Point3?) : this(point!!.x, point.y, point.z)

    override fun clone(): Point3 = Point3(this)

    /**
     * Computes the distance between this point and the specified one.
     *
     * @param point
     *        The point to use.
     * @return The distance between two points.
     */
    fun distanceTo(point: Point3): Float {
        val dX = this.x - point.x
        val dY = this.y - point.y
        val dZ = this.z - point.z
        return MathUtils.sqrt(dX * dX + dY * dY + dZ * dZ)
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

    operator fun minus(point: Point3): Point3 =
        Point3(this.x - point.x, this.y - point.y, this.z - point.z)

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

    operator fun plus(point: Point3): Point3 =
        Point3(this.x + point.x, this.y + point.y, this.z + point.z)

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

    override fun set(x: Float, y: Float, z: Float): Point3 {
        this.x = x
        this.y = y
        this.z = z
        return this
    }

    override fun set(tuple: Tuple3?): Point3 {
        this.x = tuple!!.x
        this.y = tuple.y
        this.z = tuple.z
        return this
    }

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
