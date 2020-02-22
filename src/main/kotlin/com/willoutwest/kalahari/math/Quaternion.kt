package com.willoutwest.kalahari.math

/**
 * Represents a rotation in three dimensional Cartesian space using
 * hyper-complex numbers.
 *
 * @property magnitude
 *           The length of this quaternion.
 * @property magnitudeSquared
 *           The length squared of this quaternion.
 */
class Quaternion(x: Float = 0.0f,
                 y: Float = 0.0f,
                 z: Float = 0.0f,
                 w: Float = 1.0f) : Cloneable, Tuple4(x, y, z, w) {

    companion object {

        /**
         * Represents the identity quaternion.
         */
        val Identity = Quaternion(0f, 0f, 0f, 1f)

        /**
         * Represents the zero quaternion.
         */
        val Zero = Quaternion(0f, 0f, 0f, 0f)
    }

    val magnitude: Float
        get() = MathUtils.sqrt(this.magnitudeSquared)

    val magnitudeSquared: Float
        get() = this.x * this.x + this.y * this.y + this.z * this.z +
                this.w * this.w

    /**
     * Constructor.
     *
     * @param angle
     *        The amount to rotate by in radians.
     * @param axis
     *        The axis to rotate around.
     */
    constructor(angle: Float, axis: Vector3) : this() {
        this.setFromAxis(angle, axis)
    }

    /**
     * Constructor.
     *
     * @param pitch
     *        The angle of rotation in radians around the y-axis.
     * @param roll
     *        The angle of rotation in radians around the x-axis.
     * @param yaw
     *        The angle of rotation in radians around the z-axis.
     */
    constructor(pitch: Float, roll: Float, yaw: Float) : this() {
        this.setFromEuler(pitch, roll, yaw)
    }

    /**
     * Constructor.
     *
     * @param array
     *        The array to copy from.
     */
    constructor(array: FloatArray)
        : this(array[0], array[1], array[2], array[3])

    /**
     * Constructor.
     *
     * @param tuple
     *        The tuple to copy from.
     */
    constructor(tuple: Tuple4?) : this(tuple!!.x, tuple.y, tuple.z, tuple.w)

    override fun clone(): Quaternion = Quaternion(this)

    /**
     * Computes the conjugate of this quaternion.
     *
     * @return The conjugate.
     */
    fun conjugate(): Quaternion =
        Quaternion(-this.x, -this.y, -this.z, this.w)

    /**
     * Computes the conjugate of this quaternion and also modifies this
     * quaternion as a result.
     *
     * @return A reference to this quaternion for easy chaining.
     */
    fun conjugateSelf(): Quaternion {
        this.x = -this.x
        this.y = -this.y
        this.z = -this.z

        return this
    }

    operator fun div(scalar: Float): Quaternion {
        val inv = 1f / scalar

        return Quaternion(this.x * inv, this.y * inv,
                          this.z * inv, this.w * inv)
    }

    /**
     * Divides the components of this quaternion by the specified scalar
     * and also modifies this quaternion as a result.
     *
     * @param scalar
     *        The value to use.
     * @return A reference to this quaternion for easy chaining.
     */
    fun divSelf(scalar: Float): Quaternion {
        val inv = 1f / scalar

        this.x *= inv
        this.y *= inv
        this.z *= inv
        this.w *= inv

        return this
    }

    /**
     * Computes the inverse of this quaternion.
     *
     * @return The inverse.
     */
    fun invert(): Quaternion {
        val invMagSq   = MathUtils.safeInverse(this.magnitudeSquared)

        return Quaternion(
            -this.x * invMagSq, -this.y * invMagSq,
            -this.z * invMagSq, this.w * invMagSq
        )
    }

    /**
     * Computes the inverse of this quaternion and also modifies this
     * quaternion as a result.
     *
     * @return A reference to this quaternion for easy chaining.
     */
    fun invertSelf(): Quaternion {
        val invMagSq   = MathUtils.safeInverse(this.magnitudeSquared)

        this.x *= -invMagSq
        this.y *= -invMagSq
        this.z *= -invMagSq
        this.w *= invMagSq

        return this
    }

    operator fun minus(quat: Quaternion): Quaternion =
        Quaternion(this.x - quat.x, this.y - quat.y,
                   this.z - quat.z, this.w - quat.w)

    /**
     * Subtracts this quaternion from the specified one and also modifies
     * this quaternion as a result.
     *
     * @param quat
     *        The quaternion to subtract by.
     * @return A reference to this quaternion for easy chaining.
     */
    fun minusSelf(quat: Quaternion): Quaternion {
        this.x -= quat.x
        this.y -= quat.y
        this.z -= quat.z
        this.w -= quat.w

        return this
    }

    /**
     * Normalizes this quaternion.
     *
     * @return A normalized quaternion.
     */
    fun normalize(): Quaternion {
        val mag  = this.magnitude
        val quat = Quaternion(this)

        if(mag != 0f) {
            val inv = 1f / mag
            quat.x *= inv
            quat.y *= inv
            quat.z *= inv
            quat.w *= inv
        }

        return quat
    }

    /**
     * Normalizes this quaternion and also modifies this quaternion as a result.
     *
     * @return A reference to this quaternion for easy chaining.
     */
    fun normalizeSelf(): Quaternion {
        val mag = this.magnitude

        if(mag != 0f) {
            val inv = 1f / mag
            this.x *= inv
            this.y *= inv
            this.z *= inv
            this.w *= inv
        }

        return this
    }

    operator fun plus(quat: Quaternion): Quaternion =
        Quaternion(this.x + quat.x, this.y + quat.y,
                   this.z + quat.z, this.w + quat.w)

    /**
     * Adds this quaternion to the specified one and also modifies this
     * quaternion as a result.
     *
     * @param quat
     *        The quaternion to add with.
     * @return A reference to this quaternion for easy chaining.
     */
    fun plusSelf(quat: Quaternion): Quaternion {
        this.x += quat.x
        this.y += quat.y
        this.z += quat.z
        this.w += quat.w

        return this
    }

    override fun set(x: Float, y: Float, z: Float, w: Float): Quaternion =
        super.set(x, y, z, w) as Quaternion

    override fun set(array: FloatArray): Quaternion =
        super.set(array) as Quaternion

    override fun set(tuple: Tuple4?): Quaternion =
        super.set(tuple) as Quaternion

    /**
     * Composes this quaternion as a rotation of the specified amount in
     * radians around the specified axis.
     *
     * @param angle
     *        The amount to rotate by in radians.
     * @param axis
     *        The axis to rotate around.
     * @return A reference to this quaternion for easy chaining.
     */
    fun setFromAxis(angle: Float, axis: Vector3): Quaternion {
        val halfAngle = angle / 2f
        val sin = MathUtils.sin(halfAngle)

        this.x = axis.x * sin
        this.y = axis.y * sin
        this.z = axis.z * sin
        this.w = MathUtils.cos(halfAngle)

        return this
    }

    /**
     * Composes this quaternion using the specified angles of rotation around
     * the pitch, roll, and yaw axes, respectively.
     *
     * This method follows the "body 3-2-1" sequence of composition by first
     * applying yaw, then pitch, and finally roll.
     *
     * @param pitch
     *        The angle of rotation in radians around the y-axis.
     * @param roll
     *        The angle of rotation in radians around the x-axis.
     * @param yaw
     *        The angle of rotation in radians around the z-axis.
     * @return A reference to this quaternion for easy chaining.
     */
    fun setFromEuler(pitch: Float, roll: Float, yaw: Float): Quaternion {
        val cosP = MathUtils.cos(pitch * 0.5f)
        val sinP = MathUtils.sin(pitch * 0.5f)
        val cosR = MathUtils.cos(roll * 0.5f)
        val sinR = MathUtils.sin(roll * 0.5f)
        val cosY = MathUtils.cos(yaw * 0.5f)
        val sinY = MathUtils.sin(yaw * 0.5f)

        this.x = cosY * sinR * cosP - sinY * cosR * sinP
        this.y = cosY * cosR * sinP + sinY * sinR * cosP
        this.z = sinY * cosR * cosP - cosY * sinR * sinP
        this.w = cosY * cosR * cosP + sinY * sinR * sinP

        return this
    }

    operator fun times(scalar: Float): Quaternion =
        Quaternion(this.x * scalar, this.y * scalar,
                   this.z * scalar, this.w * scalar)

    operator fun times(quat: Quaternion): Quaternion =
        Quaternion(
            this.w * quat.x + this.x * quat.w +
            this.y * quat.z - this.z * quat.y,
            this.w * quat.y - this.x * quat.z +
            this.y * quat.w + this.z * quat.x,
            this.w * quat.z + this.x * quat.y -
            this.y * quat.x + this.z * quat.w,
            this.w * quat.w - this.x * quat.x -
            this.y * quat.y - this.z * quat.z
        )

    /**
     * Multiplies the components of this quaternion by the specified scalar
     * and also modifies this quaternion as a result.
     *
     * @param scalar
     *        The value to use.
     * @return A reference to this quaternion for easy chaining.
     */
    fun timesSelf(scalar: Float): Quaternion {
        this.x *= scalar
        this.y *= scalar
        this.z *= scalar
        this.w *= scalar

        return this
    }

    /**
     * Multiplies this quaternion by the specified one and also modifies this
     * quaternion as a result.
     *
     * Quaternion multiplication is also called the Hamilton product.
     *
     * @param quat
     *        The quaternion to multiply with.
     * @return A reference to this quaternion for easy chaining.
     */
    fun timesSelf(quat: Quaternion): Quaternion {
        val oldW = this.w
        val oldX = this.x
        val oldY = this.y
        val oldZ = this.z

        this.x = oldW * quat.x + oldX * quat.w +
                 oldY * quat.z - oldZ * quat.y
        this.y = oldW * quat.y - oldX * quat.z +
                 oldY * quat.w + oldZ * quat.x
        this.z = oldW * quat.z + oldX * quat.y -
                 oldY * quat.x + oldZ * quat.w
        this.w = oldW * quat.w - oldX * quat.x -
                 oldY * quat.y - oldZ * quat.z

        return this
    }

    /**
     * Converts this quaternion to a rotation matrix.
     *
     * @return A rotation matrix.
     */
    fun toMatrix(): Matrix4 = toMatrix(Matrix4())

    /**
     * Converts this quaternion to a rotation matrix and stores the result in
     * the specified matrix.
     *
     * @param store
     *        The matrix to store the result in.
     * @return A reference to store for easy chaining.
     */
    fun toMatrix(store: Matrix4): Matrix4 {
        val mag = this.magnitude
        val scalar = when(mag == 1f) {
            false -> 2f / mag
            true  -> 2f
        }

        val xs = this.x * scalar
        val ys = this.y * scalar
        val zs = this.z * scalar
        val ws = this.w * scalar

        val xx = this.x * xs
        val xy = this.x * ys
        val xz = this.x * zs
        val xw = this.x * ws
        val yy = this.y * ys
        val yz = this.y * zs
        val yw = this.y * ws
        val zz = this.z * zs
        val zw = this.z * ws

        store.t00 = 1.0f - (yy + zz)
        store.t01 = (xy - zw)
        store.t02 = (xz + yw)
        store.t10 = (xy + zw)
        store.t11 = 1.0f - (xx + zz)
        store.t12 = (yz - xw)
        store.t20 = (xz - yw)
        store.t21 = (yz + xw)
        store.t22 = 1.0f - (xx + yy)

        return store
    }
}