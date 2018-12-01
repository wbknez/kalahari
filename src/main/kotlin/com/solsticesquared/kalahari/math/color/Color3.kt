package com.solsticesquared.kalahari.math.color

import com.solsticesquared.kalahari.math.MathUtils
import com.solsticesquared.kalahari.math.Tuple3
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

    /**
     * Represents a collection of RGB colors.
     *
     * Colors are provided courtesy of Matplotlib, a plotting library for
     * Python 3.
     */
    companion object {

        val AliceBlue = Color3(240, 248, 255)
        val AntiqueWhite = Color3(250, 235, 215)
        val Aqua = Color3(0, 255, 255)
        val Aquamarine = Color3(127, 255, 212)
        val Azure = Color3(240, 255, 255)
        val Beige = Color3(245, 245, 220)
        val Bisque = Color3(255, 228, 196)
        val Black = Color3(0, 0, 0)
        val BlanchedAlmond = Color3(255, 235, 205)
        val Blue = Color3(0, 0, 255)
        val BlueViolet = Color3(138, 43, 226)
        val Brown = Color3(165, 42, 42)
        val BurlyWood = Color3(222, 184, 135)
        val CadetBlue = Color3(95, 158, 160)
        val Chartreuse = Color3(127, 255, 0)
        val Chocolate = Color3(210, 105, 30)
        val Coral = Color3(255, 127, 80)
        val CornflowerBlue = Color3(100, 149, 237)
        val Cornsilk = Color3(255, 248, 220)
        val Crimson = Color3(220, 20, 60)
        val Cyan = Color3(0, 255, 255)
        val DarkBlue = Color3(0, 0, 139)
        val DarkVyan = Color3(0, 139, 139)
        val DarkGoldenrod = Color3(184, 134, 11)
        val DarkGray = Color3(169, 169, 169)
        val DarkGreen = Color3(0, 100, 0)
        val DarkGrey = Color3(169, 169, 169)
        val DarkKhaki = Color3(189, 183, 107)
        val DarkMagenta = Color3(139, 0, 139)
        val DarkOliveGreen = Color3(85, 107, 47)
        val DarkOrange = Color3(255, 140, 0)
        val DarkOrchid = Color3(153, 50, 204)
        val DarkRed = Color3(139, 0, 0)
        val DarkSalmon = Color3(233, 150, 122)
        val DarkSeaGreen = Color3(143, 188, 143)
        val DarkSlateBlue = Color3(72, 61, 139)
        val DarkSlateGray = Color3(47, 79, 79)
        val DarkSlateGrey = Color3(47, 79, 79)
        val DarkTurquoise = Color3(0, 206, 209)
        val DarkViolet = Color3(148, 0, 211)
        val DeepInk = Color3(255, 20, 147)
        val DeepSkyBlue = Color3(0, 191, 255)
        val DimGray = Color3(105, 105, 105)
        val DimGrey = Color3(105, 105, 105)
        val DodgerBlue = Color3(30, 144, 255)
        val FireBrick = Color3(178, 34, 34)
        val FloralWhite = Color3(255, 250, 240)
        val ForestGreen = Color3(34, 139, 34)
        val Fuchsia = Color3(255, 0, 255)
        val Gainsboro = Color3(220, 220, 220)
        val GhostWhite = Color3(248, 248, 255)
        val Gold = Color3(255, 215, 0)
        val Goldenrod = Color3(218, 165, 32)
        val Gray = Color3(128, 128, 128)
        val Green = Color3(0, 128, 0)
        val GreenYellow = Color3(173, 255, 47)
        val Grey = Color3(128, 128, 128)
        val Honeydew = Color3(240, 255, 240)
        val HotPink = Color3(255, 105, 180)
        val IndianRed = Color3(205, 92, 92)
        val Indigo = Color3(75, 0, 130)
        val Ivory = Color3(255, 255, 240)
        val Khaki = Color3(240, 230, 140)
        val Lavender = Color3(230, 230, 250)
        val LavenderBlush = Color3(255, 240, 245)
        val LawnGreen = Color3(124, 252, 0)
        val LemonChiffon = Color3(255, 250, 205)
        val LightBlue = Color3(173, 216, 230)
        val LightCoral = Color3(240, 128, 128)
        val LightCyan = Color3(224, 255, 255)
        val LightGoldenrodYellow = Color3(250, 250, 210)
        val LightGray = Color3(211, 211, 211)
        val LightGreen = Color3(144, 238, 144)
        val LightGrey = Color3(211, 211, 211)
        val LightPink = Color3(255, 182, 193)
        val LightSalmon = Color3(255, 160, 122)
        val LightSeaGreen = Color3(32, 178, 170)
        val LightSkyBlue = Color3(135, 206, 250)
        val LightSlateGray = Color3(119, 136, 153)
        val LightSlateGrey = Color3(119, 136, 153)
        val LightSteelBlue = Color3(176, 196, 222)
        val LightYellow = Color3(255, 255, 224)
        val Lime = Color3(0, 255, 0)
        val LimeGreen = Color3(50, 205, 50)
        val Linen = Color3(250, 240, 230)
        val Magenta = Color3(255, 0, 255)
        val Maroon = Color3(128, 0, 0)
        val MediumAquamarine = Color3(102, 205, 170)
        val MediumBlue = Color3(0, 0, 205)
        val MediumOrchid = Color3(186, 85, 211)
        val MediumPurple = Color3(147, 112, 219)
        val MediumSeaGreen = Color3(60, 179, 113)
        val MediumSlateBlue = Color3(123, 104, 238)
        val MediumSpringGreen = Color3(0, 250, 154)
        val MediumTurquoise = Color3(72, 209, 204)
        val MediumVioletRed = Color3(199, 21, 133)
        val MidnightBlue = Color3(25, 25, 112)
        val MintCream = Color3(245, 255, 250)
        val MistyRose = Color3(255, 228, 225)
        val Moccasin = Color3(255, 228, 181)
        val NavajoWhite = Color3(255, 222, 173)
        val Navy = Color3(0, 0, 128)
        val OldLace = Color3(253, 245, 230)
        val Olive = Color3(128, 128, 0)
        val OliveDrab = Color3(107, 142, 35)
        val Orange = Color3(255, 165, 0)
        val OrangeRed = Color3(255, 69, 0)
        val Orchid = Color3(218, 112, 214)
        val PaleGoldenrod = Color3(238, 232, 170)
        val PaleGreen = Color3(152, 251, 152)
        val PaleTurquoise = Color3(175, 238, 238)
        val PaleVioletRed = Color3(219, 112, 147)
        val PapayaWhip = Color3(255, 239, 213)
        val PeachPuff = Color3(255, 218, 185)
        val Peru = Color3(205, 133, 63)
        val Pink = Color3(255, 192, 203)
        val Plum = Color3(221, 160, 221)
        val PowderBlue = Color3(176, 224, 230)
        val Purple = Color3(128, 0, 128)
        val RebeccaPurple = Color3(102, 51, 153)
        val Red = Color3(255, 0, 0)
        val RosyBrown = Color3(188, 143, 143)
        val RoyalBlue = Color3(65, 105, 225)
        val SaddleBrown = Color3(139, 69, 19)
        val Salmon = Color3(250, 128, 114)
        val SandyBrown = Color3(244, 164, 96)
        val SeaGreen = Color3(46, 139, 87)
        val SeaShell = Color3(255, 245, 238)
        val Sienna = Color3(160, 82, 45)
        val Silver = Color3(192, 192, 192)
        val SkyBlue = Color3(135, 206, 235)
        val SlateBlue = Color3(106, 90, 205)
        val SlateGray = Color3(112, 128, 144)
        val SlateGrey = Color3(112, 128, 144)
        val Snow = Color3(255, 250, 250)
        val SpringGreen = Color3(0, 255, 127)
        val SteelBlue = Color3(70, 130, 180)
        val Tan = Color3(210, 180, 140)
        val Teal = Color3(0, 128, 128)
        val Thistle = Color3(216, 191, 216)
        val Tomato = Color3(255, 99, 71)
        val Turquoise = Color3(64, 224, 208)
        val Violet = Color3(238, 130, 238)
        val Wheat = Color3(245, 222, 179)
        val White = Color3(255, 255, 255)
        val WhiteSmoke = Color3(245, 245, 245)
        val Yellow = Color3(255, 255, 0)
        val YellowGreen = Color3(154, 205, 50)
    }

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
     * @param red
     *        The red color component to use.
     * @param green
     *        The green color component to use.
     * @param blue
     *        The blue color component to use.
     */
    constructor(red: Int = 0, green: Int = 0, blue: Int = 0)
        : this(intToComponent(red), intToComponent(green), intToComponent(blue))

    /**
     * Constructor.
     *
     * @param color
     *        The color to copy from.
     */
    constructor(color: Color3?) : this(color!!.red, color.green, color.blue)

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
        Color3(this.red + (end.red - this.red) * t,
               this.green + (end.green - this.green) * t,
               this.blue + (end.blue - this.blue) * t)

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
        this.red   = this.red + (end.red - this.red) * t
        this.green = this.green + (end.green - this.green) * t
        this.blue  = this.blue + (end.blue - this.blue) * t
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

    override fun set(x: Float, y: Float, z: Float): Color3 {
        this.red   = x
        this.green = y
        this.blue  = z
        return this
    }

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
    fun set(red: Int, green: Int, blue: Int): Color3 {
        this.red   = intToComponent(red)
        this.green = intToComponent(green)
        this.blue  = intToComponent(blue)
        return this
    }

    override fun set(tuple: Tuple3?): Color3 {
        this.red = tuple!!.x
        this.green = tuple.y
        this.blue = tuple.z
        return this
    }

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
     * comptaible with AWT, Swing, and JavaFX drawing methods.
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

        return (((255 and 0xFF) shl 24) or
               ((redInt and 0xFF) shl 16) or
               ((greenInt and 0xFF) shl 8) or
               ((blueInt and 0xFF) shl 0))
    }
}
