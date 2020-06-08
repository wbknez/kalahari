package com.willoutwest.kalahari.script.libraries

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.script.ScriptingLibrary
import com.willoutwest.kalahari.texture.FillTexture
import com.willoutwest.kalahari.texture.Image
import com.willoutwest.kalahari.texture.ImageMapper
import com.willoutwest.kalahari.texture.ImageTexture
import com.willoutwest.kalahari.texture.Texture
import com.willoutwest.kalahari.texture.mappers.CylindricalMapper
import com.willoutwest.kalahari.texture.mappers.LightProbeMapper
import com.willoutwest.kalahari.texture.mappers.RectangularMapper
import com.willoutwest.kalahari.texture.mappers.SphericalMapper
import com.willoutwest.kalahari.texture.patterns.CheckerTexture2
import com.willoutwest.kalahari.texture.patterns.CheckerTexture3

/**
 * Represents a mechanism for creating and working with [Texture] objects
 * in a Lua scripting environment.
 */
class TextureLibrary : ScriptingLibrary {

    @JvmField
    val Cylindrical = CylindricalMapper()

    @JvmField
    val LightProbe = LightProbeMapper(LightProbeMapper.MirrorMode.Light)

    @JvmField
    val Panoramic = LightProbeMapper(LightProbeMapper.MirrorMode.Panoramic)

    @JvmField
    val Rectangular = RectangularMapper()

    @JvmField
    val Spherical = SphericalMapper()

    /**
     * Creates a new 2D checker texture using the specified parameters.
     *
     * @param evenColor
     *        The first checker color to use.
     * @param oddColor
     *        The second checker color to use.
     * @param outlineColor
     *        The outline color to use.
     * @return A 2D checker texture.
     */
    fun checker2(evenColor: Texture, oddColor: Texture,
                 outlineColor: Texture): Texture =
        checker2(evenColor, oddColor, 1f, outlineColor, 0f)

    /**
     * Creates a new 2D checker texture using the specified parameters.
     *
     * @param evenColor
     *        The first checker color to use.
     * @param oddColor
     *        The second checker color to use.
     * @param scale
     *        The checker scale to use.
     * @param outlineColor
     *        The outline color to use.
     * @param outlineWidth
     *        The outline width to use.
     * @return A 2D checker texture.
     */
    fun checker2(evenColor: Texture, oddColor: Texture, scale: Float,
                 outlineColor: Texture, outlineWidth: Float): Texture =
        CheckerTexture2(evenColor, oddColor, scale, outlineColor,
                        outlineWidth)

    /**
     * Creates a new 3D checker texture using the specified parameters.
     *
     * @param evenColor
     *        The first checker color to use.
     * @param oddColor
     *        The second checker color to use.
     * @return A 3D checker texture.
     */
    fun checker3(evenColor: Texture, oddColor: Texture) =
        checker3(evenColor, oddColor, 1f)

    /**
     * Creates a new 3D checker texture using the specified parameters.
     *
     * @param evenColor
     *        The first checker color to use.
     * @param oddColor
     *        The second checker color to use.
     * @param scale
     *        The checker scale to use.
     * @return A 3D checker texture.
     */
    fun checker3(evenColor: Texture, oddColor: Texture, scale: Float):
        Texture =
        CheckerTexture3(evenColor, oddColor, scale)

    /**
     * Creates a new filled texture using the specified color components.
     *
     * @param red
     *        A red color component to use.
     * @param green
     *        The green color component to use.
     * @param blue
     *        The blue color component to use.
     * @return A filled texture.
     */
    fun fill(red: Float, green: Float, blue: Float): Texture =
        FillTexture(red, green, blue)

    /**
     * Creates a new filled texture with the specified color.
     *
     * @param color
     *        The fill color to use.
     * @return A filled texture.
     */
    fun fill(color: Color3): Texture = FillTexture(color)

    /**
     *
     *
     * @param img
     *
     * @param mapper
     *
     * @return
     */
    fun image(img: Image, mapper: ImageMapper): Texture =
        ImageTexture(img, mapper)

    /* *** Fill Texture Library *** */

    @JvmField
    val AliceBlue = FillTexture.fromRgb(240, 248, 255)

    @JvmField
    val AntiqueWhite = FillTexture.fromRgb(250, 235, 215)

    @JvmField
    val Aqua = FillTexture.fromRgb(0, 255, 255)

    @JvmField
    val Aquamarine = FillTexture.fromRgb(127, 255, 212)

    @JvmField
    val Azure = FillTexture.fromRgb(240, 255, 255)

    @JvmField
    val Beige = FillTexture.fromRgb(245, 245, 220)

    @JvmField
    val Bisque = FillTexture.fromRgb(255, 228, 196)

    @JvmField
    val Black = FillTexture.fromRgb(0, 0, 0)

    @JvmField
    val BlanchedAlmond = FillTexture.fromRgb(255, 235, 205)

    @JvmField
    val Blue = FillTexture.fromRgb(0, 0, 255)

    @JvmField
    val BlueViolet = FillTexture.fromRgb(138, 43, 226)

    @JvmField
    val Brown = FillTexture.fromRgb(165, 42, 42)

    @JvmField
    val BurlyWood = FillTexture.fromRgb(222, 184, 135)

    @JvmField
    val CadetBlue = FillTexture.fromRgb(95, 158, 160)

    @JvmField
    val Chartreuse = FillTexture.fromRgb(127, 255, 0)

    @JvmField
    val Chocolate = FillTexture.fromRgb(210, 105, 30)

    @JvmField
    val Coral = FillTexture.fromRgb(255, 127, 80)

    @JvmField
    val CornflowerBlue = FillTexture.fromRgb(100, 149, 237)

    @JvmField
    val Cornsilk = FillTexture.fromRgb(255, 248, 220)

    @JvmField
    val Crimson = FillTexture.fromRgb(220, 20, 60)

    @JvmField
    val Cyan = FillTexture.fromRgb(0, 255, 255)

    @JvmField
    val DarkBlue = FillTexture.fromRgb(0, 0, 139)

    @JvmField
    val DarkVyan = FillTexture.fromRgb(0, 139, 139)

    @JvmField
    val DarkGoldenrod = FillTexture.fromRgb(184, 134, 11)

    @JvmField
    val DarkGray = FillTexture.fromRgb(169, 169, 169)

    @JvmField
    val DarkGreen = FillTexture.fromRgb(0, 100, 0)

    @JvmField
    val DarkGrey = FillTexture.fromRgb(169, 169, 169)

    @JvmField
    val DarkKhaki = FillTexture.fromRgb(189, 183, 107)

    @JvmField
    val DarkMagenta = FillTexture.fromRgb(139, 0, 139)

    @JvmField
    val DarkOliveGreen = FillTexture.fromRgb(85, 107, 47)

    @JvmField
    val DarkOrange = FillTexture.fromRgb(255, 140, 0)

    @JvmField
    val DarkOrchid = FillTexture.fromRgb(153, 50, 204)

    @JvmField
    val DarkRed = FillTexture.fromRgb(139, 0, 0)

    @JvmField
    val DarkSalmon = FillTexture.fromRgb(233, 150, 122)

    @JvmField
    val DarkSeaGreen = FillTexture.fromRgb(143, 188, 143)

    @JvmField
    val DarkSlateBlue = FillTexture.fromRgb(72, 61, 139)

    @JvmField
    val DarkSlateGray = FillTexture.fromRgb(47, 79, 79)

    @JvmField
    val DarkSlateGrey = FillTexture.fromRgb(47, 79, 79)

    @JvmField
    val DarkTurquoise = FillTexture.fromRgb(0, 206, 209)

    @JvmField
    val DarkViolet = FillTexture.fromRgb(148, 0, 211)

    @JvmField
    val DeepInk = FillTexture.fromRgb(255, 20, 147)

    @JvmField
    val DeepSkyBlue = FillTexture.fromRgb(0, 191, 255)

    @JvmField
    val DimGray = FillTexture.fromRgb(105, 105, 105)

    @JvmField
    val DimGrey = FillTexture.fromRgb(105, 105, 105)

    @JvmField
    val DodgerBlue = FillTexture.fromRgb(30, 144, 255)

    @JvmField
    val FireBrick = FillTexture.fromRgb(178, 34, 34)

    @JvmField
    val FloralWhite = FillTexture.fromRgb(255, 250, 240)

    @JvmField
    val ForestGreen = FillTexture.fromRgb(34, 139, 34)

    @JvmField
    val Fuchsia = FillTexture.fromRgb(255, 0, 255)

    @JvmField
    val Gainsboro = FillTexture.fromRgb(220, 220, 220)

    @JvmField
    val GhostWhite = FillTexture.fromRgb(248, 248, 255)

    @JvmField
    val Gold = FillTexture.fromRgb(255, 215, 0)

    @JvmField
    val Goldenrod = FillTexture.fromRgb(218, 165, 32)

    @JvmField
    val Gray = FillTexture.fromRgb(128, 128, 128)

    @JvmField
    val Green = FillTexture.fromRgb(0, 128, 0)

    @JvmField
    val GreenYellow = FillTexture.fromRgb(173, 255, 47)

    @JvmField
    val Grey = FillTexture.fromRgb(128, 128, 128)

    @JvmField
    val Honeydew = FillTexture.fromRgb(240, 255, 240)

    @JvmField
    val HotPink = FillTexture.fromRgb(255, 105, 180)

    @JvmField
    val IndianRed = FillTexture.fromRgb(205, 92, 92)

    @JvmField
    val Indigo = FillTexture.fromRgb(75, 0, 130)

    @JvmField
    val Ivory = FillTexture.fromRgb(255, 255, 240)

    @JvmField
    val Khaki = FillTexture.fromRgb(240, 230, 140)

    @JvmField
    val Lavender = FillTexture.fromRgb(230, 230, 250)

    @JvmField
    val LavenderBlush = FillTexture.fromRgb(255, 240, 245)

    @JvmField
    val LawnGreen = FillTexture.fromRgb(124, 252, 0)

    @JvmField
    val LemonChiffon = FillTexture.fromRgb(255, 250, 205)

    @JvmField
    val LightBlue = FillTexture.fromRgb(173, 216, 230)

    @JvmField
    val LightCoral = FillTexture.fromRgb(240, 128, 128)

    @JvmField
    val LightCyan = FillTexture.fromRgb(224, 255, 255)

    @JvmField
    val LightGoldenrodYellow = FillTexture.fromRgb(250, 250, 210)

    @JvmField
    val LightGray = FillTexture.fromRgb(211, 211, 211)

    @JvmField
    val LightGreen = FillTexture.fromRgb(144, 238, 144)

    @JvmField
    val LightGrey = FillTexture.fromRgb(211, 211, 211)

    @JvmField
    val LightPink = FillTexture.fromRgb(255, 182, 193)

    @JvmField
    val LightSalmon = FillTexture.fromRgb(255, 160, 122)

    @JvmField
    val LightSeaGreen = FillTexture.fromRgb(32, 178, 170)

    @JvmField
    val LightSkyBlue = FillTexture.fromRgb(135, 206, 250)

    @JvmField
    val LightSlateGray = FillTexture.fromRgb(119, 136, 153)

    @JvmField
    val LightSlateGrey = FillTexture.fromRgb(119, 136, 153)

    @JvmField
    val LightSteelBlue = FillTexture.fromRgb(176, 196, 222)

    @JvmField
    val LightYellow = FillTexture.fromRgb(255, 255, 224)

    @JvmField
    val Lime = FillTexture.fromRgb(0, 255, 0)

    @JvmField
    val LimeGreen = FillTexture.fromRgb(50, 205, 50)

    @JvmField
    val Linen = FillTexture.fromRgb(250, 240, 230)

    @JvmField
    val Magenta = FillTexture.fromRgb(255, 0, 255)

    @JvmField
    val Maroon = FillTexture.fromRgb(128, 0, 0)

    @JvmField
    val MediumAquamarine = FillTexture.fromRgb(102, 205, 170)

    @JvmField
    val MediumBlue = FillTexture.fromRgb(0, 0, 205)

    @JvmField
    val MediumOrchid = FillTexture.fromRgb(186, 85, 211)

    @JvmField
    val MediumPurple = FillTexture.fromRgb(147, 112, 219)

    @JvmField
    val MediumSeaGreen = FillTexture.fromRgb(60, 179, 113)

    @JvmField
    val MediumSlateBlue = FillTexture.fromRgb(123, 104, 238)

    @JvmField
    val MediumSpringGreen = FillTexture.fromRgb(0, 250, 154)

    @JvmField
    val MediumTurquoise = FillTexture.fromRgb(72, 209, 204)

    @JvmField
    val MediumVioletRed = FillTexture.fromRgb(199, 21, 133)

    @JvmField
    val MidnightBlue = FillTexture.fromRgb(25, 25, 112)

    @JvmField
    val MintCream = FillTexture.fromRgb(245, 255, 250)

    @JvmField
    val MistyRose = FillTexture.fromRgb(255, 228, 225)

    @JvmField
    val Moccasin = FillTexture.fromRgb(255, 228, 181)

    @JvmField
    val NavajoWhite = FillTexture.fromRgb(255, 222, 173)

    @JvmField
    val Navy = FillTexture.fromRgb(0, 0, 128)

    @JvmField
    val OldLace = FillTexture.fromRgb(253, 245, 230)

    @JvmField
    val Olive = FillTexture.fromRgb(128, 128, 0)

    @JvmField
    val OliveDrab = FillTexture.fromRgb(107, 142, 35)

    @JvmField
    val Orange = FillTexture.fromRgb(255, 165, 0)

    @JvmField
    val OrangeRed = FillTexture.fromRgb(255, 69, 0)

    @JvmField
    val Orchid = FillTexture.fromRgb(218, 112, 214)

    @JvmField
    val PaleGoldenrod = FillTexture.fromRgb(238, 232, 170)

    @JvmField
    val PaleGreen = FillTexture.fromRgb(152, 251, 152)

    @JvmField
    val PaleTurquoise = FillTexture.fromRgb(175, 238, 238)

    @JvmField
    val PaleVioletRed = FillTexture.fromRgb(219, 112, 147)

    @JvmField
    val PapayaWhip = FillTexture.fromRgb(255, 239, 213)

    @JvmField
    val PeachPuff = FillTexture.fromRgb(255, 218, 185)

    @JvmField
    val Peru = FillTexture.fromRgb(205, 133, 63)

    @JvmField
    val Pink = FillTexture.fromRgb(255, 192, 203)

    @JvmField
    val Plum = FillTexture.fromRgb(221, 160, 221)

    @JvmField
    val PowderBlue = FillTexture.fromRgb(176, 224, 230)

    @JvmField
    val Purple = FillTexture.fromRgb(128, 0, 128)

    @JvmField
    val RebeccaPurple = FillTexture.fromRgb(102, 51, 153)

    @JvmField
    val Red = FillTexture.fromRgb(255, 0, 0)

    @JvmField
    val RosyBrown = FillTexture.fromRgb(188, 143, 143)

    @JvmField
    val RoyalBlue = FillTexture.fromRgb(65, 105, 225)

    @JvmField
    val SaddleBrown = FillTexture.fromRgb(139, 69, 19)

    @JvmField
    val Salmon = FillTexture.fromRgb(250, 128, 114)

    @JvmField
    val SandyBrown = FillTexture.fromRgb(244, 164, 96)

    @JvmField
    val SeaGreen = FillTexture.fromRgb(46, 139, 87)

    @JvmField
    val SeaShell = FillTexture.fromRgb(255, 245, 238)

    @JvmField
    val Sienna = FillTexture.fromRgb(160, 82, 45)

    @JvmField
    val Silver = FillTexture.fromRgb(192, 192, 192)

    @JvmField
    val SkyBlue = FillTexture.fromRgb(135, 206, 235)

    @JvmField
    val SlateBlue = FillTexture.fromRgb(106, 90, 205)

    @JvmField
    val SlateGray = FillTexture.fromRgb(112, 128, 144)

    @JvmField
    val SlateGrey = FillTexture.fromRgb(112, 128, 144)

    @JvmField
    val Snow = FillTexture.fromRgb(255, 250, 250)

    @JvmField
    val SpringGreen = FillTexture.fromRgb(0, 255, 127)

    @JvmField
    val SteelBlue = FillTexture.fromRgb(70, 130, 180)

    @JvmField
    val Tan = FillTexture.fromRgb(210, 180, 140)

    @JvmField
    val Teal = FillTexture.fromRgb(0, 128, 128)

    @JvmField
    val Thistle = FillTexture.fromRgb(216, 191, 216)

    @JvmField
    val Tomato = FillTexture.fromRgb(255, 99, 71)

    @JvmField
    val Turquoise = FillTexture.fromRgb(64, 224, 208)

    @JvmField
    val Violet = FillTexture.fromRgb(238, 130, 238)

    @JvmField
    val Wheat = FillTexture.fromRgb(245, 222, 179)

    @JvmField
    val White = FillTexture.fromRgb(255, 255, 255)

    @JvmField
    val WhiteSmoke = FillTexture.fromRgb(245, 245, 245)

    @JvmField
    val Yellow = FillTexture.fromRgb(255, 255, 0)

    @JvmField
    val YellowGreen = FillTexture.fromRgb(154, 205, 50)
}