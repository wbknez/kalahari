package com.willoutwest.kalahari.math

import java.awt.Color

/**
 * Converts the specified color component, given as a floating-point value
 * between zero and one, to its near equivalent on the range zero to
 * two-hundred and fifty five.
 *
 * @param comp
 *        The color component to convert.
 * @return A floating-point color component as an integer.
 */
fun componentToInt(comp: Float): Int {
    require(comp in 0f..1f) {
        "The color component is out of range: $comp."
    }

    return when(comp) {
        1f   -> 255
        else -> (comp * 256f).toInt()
    }
}

/**
 * Converts the specified integer value, given as an integer between zero and
 * two-hundred and fifty-five, to its near equivalent on the range zero to one.
 *
 * @param value
 *        The integer value to convert.
 * @return An integer as a floating point color component.
 */
fun intToComponent(value: Int): Float {
    require(value in 0..255) {
        "The color value is out of range: $value."
    }

    return when(value) {
        255  -> 1f
        else -> value / 256f
    }
}

/**
 * Represents a trio of components in the RGB (red, green, and blue) color
 * space using single-precision floating-point numbers.
 *
 * @property red
 *           The red color component.
 * @property green
 *           The green color component.
 * @property blue
 *           The blue color component.
 * @property rgb
 *           The components of this color transformed to the range of 0 to
 *           255 and packed into a single integer.
 * @property awt
 *           This color converted to an AWT representation.  This is
 *           generally only used for unit testing; the rgb property should be
 *           preferred for direct translation to a pixelated surface.
 */
class Color3(red: Float = 0.0f, green: Float = 0.0f, blue: Float = 0.0f)
    : Cloneable, Tuple3(red, green, blue) {

    var red: Float
        get() = this.x
        set(value) { this.x = value }

    var green: Float
        get() = this.y
        set(value) { this.y = value }

    var blue: Float
        get() = this.z
        set(value) { this.z = value }

    val rgb: Int
        get() = this.toRgb()

    val awt: Color
        get() = this.toAwtColor()

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

    override fun clone(): Color3 = Color3(this)

    operator fun div(scalar: Float): Color3 {
        val inv = 1f / scalar

        return Color3(this.red * inv, this.green * inv, this.blue * inv)
    }

    /**
     * Divides this color by the specified scalar and also modifies this
     * color as a result.
     *
     * @param scalar
     *        The scalar to use.
     * @return A reference to this color for easy chaining.
     */
    fun divSelf(scalar: Float): Color3 {
        val inv = 1f / scalar

        this.red   *= inv
        this.green *= inv
        this.blue  *= inv

        return this
    }

    /**
     * Linearly interpolates between this color and the specified end color
     * at the specified amount of parametric time.
     *
     * @param end
     *        The final color to use.
     * @param t
     *        The parametric time to use.
     * @return A linearly interpolated color.
     */
    fun lerp(end: Color3, t: Float): Color3 =
        Color3(this.red   + (end.red   - this.red)  *  t,
               this.green + (end.green - this.green) * t,
               this.blue  + (end.blue  - this.blue)  * t)

    /**
     * Linearly interpolates between this color and the specified end color
     * at the specified amount of parametric time and modifies this color as
     * a result.
     *
     * @param end
     *        The final color to use.
     * @param t
     *        The parametric time to use.
     * @return A reference to this color for easy chaining.
     */
    fun lerpSelf(end: Color3, t: Float): Color3 {
        this.red   = this.red   + (end.red   - this.red)   * t
        this.green = this.green + (end.green - this.green) * t
        this.blue  = this.blue  + (end.blue  - this.blue)  * t

        return this
    }

    operator fun minus(color: Color3): Color3 =
        Color3(this.red - color.red, this.green - color.green,
               this.blue - color.blue)

    /**
     * Subtracts this color from the specified color and also modifies this
     * color as a result.
     *
     * @param color
     *        The color to subtract.
     * @return A reference to this color for easy chaining.
     */
    fun minusSelf(color: Color3): Color3 {
        this.red   -= color.red
        this.green -= color.green
        this.blue  -= color.blue

        return this
    }

    operator fun plus(color: Color3): Color3 =
        Color3(this.red + color.red, this.green + color.green,
               this.blue + color.blue)

    /**
     * Adds this color to the specified color and also modifies this color as
     * a result.
     *
     * @param color
     *        The color to add.
     * @return A reference to this color for easy chaining.
     */
    fun plusSelf(color: Color3): Color3 {
        this.red   += color.red
        this.green += color.green
        this.blue  += color.blue

        return this
    }

    /**
     * Raises each component of this color to the specified power.
     *
     * @param exp
     *        The exponent to use.
     * @return A color raised to a power.
     */
    fun pow(exp: Float): Color3 =
        Color3(MathUtils.pow(this.red, exp), MathUtils.pow(this.green, exp),
               MathUtils.pow(this.blue, exp))

    /**
     * Raises each component of this color to the specified power and also
     * modifies this color as a result.
     *
     * @param exp
     *        The exponent to use.
     * @return A reference to this color for easy chaining.
     */
    fun powSelf(exp: Float): Color3 {
        this.red   = MathUtils.pow(this.red, exp)
        this.green = MathUtils.pow(this.green, exp)
        this.blue  = MathUtils.pow(this.blue, exp)

        return this
    }

    override fun set(x: Float, y: Float, z: Float): Color3 =
        super.set(x, y, z) as Color3

    /**
     * Sets the components of this color to the specified values after they
     * have been converted from integer representation(s) to floating-point.
     *
     * @param red
     *        The red color component to use.
     * @param green
     *        The green color component to use.
     * @param blue
     *        The blue color component to use.
     * @return A reference to this color for easy chaining.
     */
    fun set(red: Int, green: Int, blue: Int): Color3 =
        super.set(intToComponent(red), intToComponent(green),
                  intToComponent(blue)) as Color3

    override fun set(array: FloatArray): Tuple3 = super.set(array) as Color3

    override fun set(tuple: Tuple3?): Color3 = super.set(tuple) as Color3

    operator fun times(color: Color3): Color3 =
        Color3(this.red * color.red, this.green * color.green,
               this.blue * color.blue)

    operator fun times(scalar: Float): Color3 =
        Color3(this.red * scalar, this.green * scalar, this.blue * scalar)

    /**
     * Multiplies this color by the specified color and also modifies this
     * color as a result.
     *
     * @param color
     *        The color to multiply.
     * @return A reference to this color for easy chaining.
     */
    fun timesSelf(color: Color3): Color3 {
        this.red   *= color.red
        this.green *= color.green
        this.blue  *= color.blue

        return this
    }

    /**
     * Multiplies this color by the specified scalar and also modifies this
     * color as a result.
     *
     * @param scalar
     *        The value to multiply.
     * @return A reference to this color for easy chaining.
     */
    fun timesSelf(scalar: Float): Color3 {
        this.red   *= scalar
        this.green *= scalar
        this.blue  *= scalar

        return this
    }

    /**
     * Multiplies this color by the specified components.
     *
     * @param red
     *        The red component to multiply by.
     * @param green
     *        The green component to multiply by.
     * @param blue
     *        The blue component to multiply by.
     * @return A reference to this color for easy chaining.
     */
    fun timesSelf(red: Float, green: Float, blue: Float): Color3 {
        this.red   *= red
        this.green *= green
        this.blue  *= blue

        return this
    }

    /**
     * Converts this color to an AWT-compatible object.
     *
     * This method is intended for use with unit-testing only.  Direct
     * conversion to a packed integer representation with pixel access should
     * be preferred for drawing.
     *
     * @return An AWT color.
     */
    private fun toAwtColor(): Color {
        return Color(this.red, this.green, this.blue)
    }

    /**
     * Converts this color to a packed RGB integer representation that is
     * compatible with AWT, Swing, and JavaFX drawing methods.
     *
     * Please note that although this class only supports three components -
     * RGB - the converted format is actually ARGB with an alpha value of one.
     *
     * @return A packed RGB integer.
     */
    private fun toRgb(): Int {
        val redInt   = componentToInt(this.red)
        val greenInt = componentToInt(this.green)
        val blueInt  = componentToInt(this.blue)

        return (((255  and 0xFF) shl 24) or
            ((redInt   and 0xFF) shl 16) or
            ((greenInt and 0xFF) shl 8) or
            ((blueInt  and 0xFF) shl 0))
    }
}