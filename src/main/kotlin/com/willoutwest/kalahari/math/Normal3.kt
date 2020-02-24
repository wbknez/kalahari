package com.willoutwest.kalahari.math

/**
 * Represents an implementation of [Vector3] whose magnitude is always
 * (intended to be) guaranteed as one.
 */
class Normal3(x: Float = 0f, y: Float = 0f, z: Float = 0f)
    : Cloneable, Vector3(x, y, z) {

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

    override fun clone(): Normal3 = Normal3(this)

    override operator fun div(scalar: Float): Normal3 {
        val inv = 1f / scalar

        return Normal3(this.x * inv, this.y * inv, this.z * inv)
    }

    override fun divSelf(scalar: Float): Normal3 =
        super.divSelf(scalar) as Normal3

    override fun minus(x: Float, y: Float, z: Float): Normal3 =
        Normal3(this.x - x, this.y - y, this.z - z)

    override operator fun minus(vec: Vector3): Normal3 =
        Normal3(this.x - vec.x, this.y - vec.y, this.z - vec.z)

    override fun minusSelf(x: Float, y: Float, z: Float): Normal3 =
        super.minusSelf(x, y, z) as Normal3

    override fun minusSelf(vec: Vector3): Normal3 =
        super.minusSelf(vec) as Normal3

    override fun negateSelf(): Normal3 = super.negateSelf() as Normal3

    override fun normalize(): Normal3 {
        val mag    = this.magnitude
        val normal = Normal3(this)

        if(mag != 0f && mag != 1f) {
            val inv = 1f / mag

            normal.x *= inv
            normal.y *= inv
            normal.z *= inv
        }

        return normal
    }

    override fun normalizeSelf(): Normal3 = super.normalizeSelf() as Normal3

    override operator fun plus(vec: Vector3): Normal3 =
        Normal3(this.x + vec.x, this.y + vec.y, this.z + vec.z)

    override fun plusSelf(x: Float, y: Float, z: Float): Normal3 =
        super.plusSelf(x, y, z) as Normal3

    override fun plusSelf(vec: Vector3): Normal3 =
        super.plusSelf(vec) as Normal3

    override fun set(x: Float, y: Float, z: Float): Normal3 =
        super.set(x, y, z) as Normal3

    override fun set(array: FloatArray): Normal3 = super.set(array) as Normal3

    override fun set(tuple: Tuple3?): Normal3 = super.set(tuple) as Normal3

    override operator fun times(scalar: Float): Normal3 =
        Normal3(this.x * scalar, this.y * scalar, this.z * scalar)

    override fun timesSelf(scalar: Float): Normal3 =
        super.timesSelf(scalar) as Normal3

    override fun transform(mat: Matrix4): Normal3 =
        Normal3(
            this.x * mat.t00 + this.y * mat.t10 + this.z * mat.t20,
            this.x * mat.t01 + this.y * mat.t11 + this.z * mat.t21,
            this.x * mat.t02 + this.y * mat.t12 + this.z * mat.t22
        )

    override fun transformSelf(mat: Matrix4): Normal3 {
        val vX = this.x
        val vY = this.y
        val vZ = this.z

        this.x = vX * mat.t00 + vY * mat.t10 + vZ * mat.t20
        this.y = vX * mat.t01 + vY * mat.t11 + vZ * mat.t21
        this.z = vX * mat.t02 + vY * mat.t12 + vZ * mat.t22

        return this
    }

    override operator fun unaryMinus(): Normal3 =
        Normal3(-this.x, -this.y, -this.z)
}