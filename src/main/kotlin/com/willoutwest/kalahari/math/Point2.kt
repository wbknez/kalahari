package com.willoutwest.kalahari.math

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
        val X    = Point2(1f, 0f)

        /**
         * The y-axis unit point.
         */
        val Y    = Point2(0f, 1f)

        /**
         * The origin point.
         */
        val Zero = Point2(0f, 0f)
    }

    /**
     * Constructor.
     *
     * @param tuple
     *        The tuple to copy from.
     */
    constructor(tuple: Tuple2?) : this(tuple!!.x, tuple.y)

    override fun clone(): Point2 = Point2(this)

    /**
     * Computes the distance between this point and the specified x- and
     * y-axis components.
     *
     * @param x
     *        The x-axis component to use.
     * @param y
     *        The y-axis component to use.
     * @return The distance between a point and two components.
     */
    fun distanceTo(x: Float, y: Float): Float =
        MathUtils.sqrt(this.distanceSquaredTo(x, y))

    /**
     * Computes the distance between this point and the specified one.
     *
     * @param point
     *        The point to use.
     * @return The distance between two points.
     */
    fun distanceTo(point: Point2): Float =
        this.distanceTo(point.x, point.y)

    /**
     * Computes the squared distance between this point and the specified x-
     * and y-axis components.
     *
     * @param x
     *        The x-axis component to use.
     * @param y
     *        The y-axis component to use.
     * @return The squared distance between a point and two components.
     */
    fun distanceSquaredTo(x: Float, y: Float): Float {
        val dX = this.x - x
        val dY = this.y - y

        return dX * dX + dY * dY
    }

    /**
     * Computes the squared distance between this point and the specified one.
     *
     * @param point
     *        The point to use.
     * @return The squared distance between two points.
     */
    fun distanceSquaredTo(point: Point2): Float =
        this.distanceSquaredTo(point.x, point.y)

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

    /**
     * Subtracts this point from the specified x- and y-axis components.
     *
     * @param x
     *        The x-axis component to subtract.
     * @param y
     *        The y-axis component to subtract.
     * @return The difference between a point and two components.
     */
    fun minus(x: Float, y: Float): Point2 =
        Point2(this.x - x, this.y - y)

    operator fun minus(point: Point2): Point2 = this.minus(point.x, point.y)

    /**
     * Subtracts this point from the specified x- and y-axis components and
     * also modifies this point as a result.
     *
     * @param x
     *        The x-axis component to subtract.
     * @param y
     *        The y-axis component to subtract.
     * @return A reference to this point for easy chaining.
     */
    fun minusSelf(x: Float, y: Float): Point2 {
        this.x -= x
        this.y -= y

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
    fun minusSelf(point: Point2): Point2 = this.minusSelf(point.x, point.y)

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

    /**
     * Adds this point to the specified x- and y-axis components.
     *
     * @param x
     *        The x-axis component to add.
     * @param y
     *        The y-axis component to add.
     * @return The sum of a point and two components.
     */
    fun plus(x: Float, y: Float): Point2 =
        Point2(this.x + x, this.y + y)

    operator fun plus(point: Point2): Point2 = this.plus(point.x, point.y)

    /**
     * Adds this point from the specified x- and y-axis components and
     * modifies this point as a result.
     *
     * @param x
     *        The x-axis component to add.
     * @param y
     *        The y-axis component to add.
     * @return A reference to this point for easy chaining.
     */
    fun plusSelf(x: Float, y: Float): Point2 {
        this.x += x
        this.y += y

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
    fun plusSelf(point: Point2): Point2 = this.plusSelf(point.x, point.y)

    override fun set(x: Float, y: Float): Point2 = super.set(x, y) as Point2

    override fun set(array: FloatArray): Point2 = super.set(array) as Point2

    override fun set(tuple: Tuple2?): Point2 = super.set(tuple) as Point2

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

    operator fun unaryMinus(): Point2 =
        Point2(-this.x, -this.y)
}