package com.solsticesquared.kalahari.math

/**
 * Represents a single point in two dimensional Cartesian space.
 */
class Point2(x: Float = 0f, y: Float = 0f) : Cloneable, Tuple2(x, y) {

    companion object {

        /**
         * The generalized unit point.
         */
        val Unit = Point2(1f, 1f)

        /**
         * The x-axis unit point.
         */
        val X = Point2(1f, 0f)

        /**
         * The y-axis unit point.
         */
        val Y = Point2(0f, 1f)

        /**
         * The zero point.
         */
        val Zero = Point2(0f, 0f)
    }

    /**
     * Constructor.
     *
     * @param point
     *        The point to copy from.
     */
    constructor(point: Point2?) : this(point!!.x, point.y)

    override fun clone(): Point2 = Point2(this)

    /**
     * Computes the distance between this point and the specified one.
     *
     * @param point
     *        The point to use.
     * @return The distance between two points.
     */
    fun distanceTo(point: Point2): Float {
        val dX = this.x - point.x
        val dY = this.y - point.y
        return MathUtils.sqrt(dX * dX + dY * dY)
    }

    /**
     * Computes the squared distance between this point and the specified one.
     *
     * @param point
     *        The point to use.
     * @return The distance between two points.
     */
    fun distanceSquaredTo(point: Point2): Float {
        val dX = this.x - point.x
        val dY = this.y - point.y
        return dX * dX + dY * dY
    }

    operator fun div(scalar: Float): Point2 {
        val inv = 1f / scalar
        return Point2(this.x * inv, this.y * inv)
    }

    /**
     * Divides this point by the specified scalar and also modifies this
     * point as a result.
     *
     * @param scalar
     *        The value to divide.
     * @return A reference to this point for easy chaining.
     */
    fun divSelf(scalar: Float): Point2 {
        val inv = 1f / scalar

        this.x *= inv
        this.y *= inv
        return this
    }

    operator fun minus(point: Point2): Point2 =
        Point2(this.x - point.x, this.y - point.y)

    /**
     * Subtracts this point from the specified one and also modifies this
     * point as a result.
     *
     * @param point
     *        The point to subtract.
     * @return A reference to this point for easy chaining.
     */
    fun minusSelf(point: Point2): Point2 {
        this.x -= point.x
        this.y -= point.y
        return this
    }

    /**
     * Negates this point and also modifies this point as a result.
     *
     * @return A reference to this point for easy chaining.
     */
    fun negateSelf(): Point2 {
        this.x = -this.x
        this.y = -this.y
        return this
    }

    operator fun plus(point: Point2): Point2 =
        Point2(this.x + point.x, this.y + point.y)

    /**
     * Adds this point to the specified one and also modifies this point as a
     * result.
     *
     * @param point
     *        The point to add.
     * @return A reference to this point for easy chaining.
     */
    fun plusSelf(point: Point2): Point2 {
        this.x += point.x
        this.y += point.y
        return this
    }

    override fun set(x: Float, y: Float): Point2 {
        this.x = x; this.y = y
        return this
    }

    override fun set(tuple: Tuple2?): Point2 {
        this.x = tuple!!.x; this.y = tuple.y
        return this
    }

    operator fun times(scalar: Float): Point2 =
        Point2(this.x * scalar, this.y * scalar)

    /**
     * Multiplies this point with the specified scalar and also modifies this
     * point as a result.
     *
     * @param scalar
     *        The value to multiply.
     * @return A reference to this point for easy chaining.
     */
    fun timesSelf(scalar: Float): Point2 {
        this.x *= scalar
        this.y *= scalar
        return this
    }

    operator fun unaryMinus(): Point2 = Point2(-this.x, -this.y)
}
