package com.willoutwest.kalahari.script.libraries

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.script.ScriptingLibrary

/**
 * Represents a collection of [Color3] objects for use in a Lua scripting
 * environment.
 *
 * These colors are courtesy of Matplotlib, a plotting library for Python 3.
 */
class ColorLibrary : ScriptingLibrary {

    @JvmField
    val AliceBlue = Color3.fromRgb(240, 248, 255)

    @JvmField
    val AntiqueWhite = Color3.fromRgb(250, 235, 215)

    @JvmField
    val Aqua = Color3.fromRgb(0, 255, 255)

    @JvmField
    val Aquamarine = Color3.fromRgb(127, 255, 212)

    @JvmField
    val Azure = Color3.fromRgb(240, 255, 255)

    @JvmField
    val Beige = Color3.fromRgb(245, 245, 220)

    @JvmField
    val Bisque = Color3.fromRgb(255, 228, 196)

    @JvmField
    val Black = Color3.fromRgb(0, 0, 0)

    @JvmField
    val BlanchedAlmond = Color3.fromRgb(255, 235, 205)

    @JvmField
    val Blue = Color3.fromRgb(0, 0, 255)

    @JvmField
    val BlueViolet = Color3.fromRgb(138, 43, 226)

    @JvmField
    val Brown = Color3.fromRgb(165, 42, 42)

    @JvmField
    val BurlyWood = Color3.fromRgb(222, 184, 135)

    @JvmField
    val CadetBlue = Color3.fromRgb(95, 158, 160)

    @JvmField
    val Chartreuse = Color3.fromRgb(127, 255, 0)

    @JvmField
    val Chocolate = Color3.fromRgb(210, 105, 30)

    @JvmField
    val Coral = Color3.fromRgb(255, 127, 80)

    @JvmField
    val CornflowerBlue = Color3.fromRgb(100, 149, 237)

    @JvmField
    val Cornsilk = Color3.fromRgb(255, 248, 220)

    @JvmField
    val Crimson = Color3.fromRgb(220, 20, 60)

    @JvmField
    val Cyan = Color3.fromRgb(0, 255, 255)

    @JvmField
    val DarkBlue = Color3.fromRgb(0, 0, 139)

    @JvmField
    val DarkVyan = Color3.fromRgb(0, 139, 139)

    @JvmField
    val DarkGoldenrod = Color3.fromRgb(184, 134, 11)

    @JvmField
    val DarkGray = Color3.fromRgb(169, 169, 169)

    @JvmField
    val DarkGreen = Color3.fromRgb(0, 100, 0)

    @JvmField
    val DarkGrey = Color3.fromRgb(169, 169, 169)

    @JvmField
    val DarkKhaki = Color3.fromRgb(189, 183, 107)

    @JvmField
    val DarkMagenta = Color3.fromRgb(139, 0, 139)

    @JvmField
    val DarkOliveGreen = Color3.fromRgb(85, 107, 47)

    @JvmField
    val DarkOrange = Color3.fromRgb(255, 140, 0)

    @JvmField
    val DarkOrchid = Color3.fromRgb(153, 50, 204)

    @JvmField
    val DarkRed = Color3.fromRgb(139, 0, 0)

    @JvmField
    val DarkSalmon = Color3.fromRgb(233, 150, 122)

    @JvmField
    val DarkSeaGreen = Color3.fromRgb(143, 188, 143)

    @JvmField
    val DarkSlateBlue = Color3.fromRgb(72, 61, 139)

    @JvmField
    val DarkSlateGray = Color3.fromRgb(47, 79, 79)

    @JvmField
    val DarkSlateGrey = Color3.fromRgb(47, 79, 79)

    @JvmField
    val DarkTurquoise = Color3.fromRgb(0, 206, 209)

    @JvmField
    val DarkViolet = Color3.fromRgb(148, 0, 211)

    @JvmField
    val DeepInk = Color3.fromRgb(255, 20, 147)

    @JvmField
    val DeepSkyBlue = Color3.fromRgb(0, 191, 255)

    @JvmField
    val DimGray = Color3.fromRgb(105, 105, 105)

    @JvmField
    val DimGrey = Color3.fromRgb(105, 105, 105)

    @JvmField
    val DodgerBlue = Color3.fromRgb(30, 144, 255)

    @JvmField
    val FireBrick = Color3.fromRgb(178, 34, 34)

    @JvmField
    val FloralWhite = Color3.fromRgb(255, 250, 240)

    @JvmField
    val ForestGreen = Color3.fromRgb(34, 139, 34)

    @JvmField
    val Fuchsia = Color3.fromRgb(255, 0, 255)

    @JvmField
    val Gainsboro = Color3.fromRgb(220, 220, 220)

    @JvmField
    val GhostWhite = Color3.fromRgb(248, 248, 255)

    @JvmField
    val Gold = Color3.fromRgb(255, 215, 0)

    @JvmField
    val Goldenrod = Color3.fromRgb(218, 165, 32)

    @JvmField
    val Gray = Color3.fromRgb(128, 128, 128)

    @JvmField
    val Green = Color3.fromRgb(0, 128, 0)

    @JvmField
    val GreenYellow = Color3.fromRgb(173, 255, 47)

    @JvmField
    val Grey = Color3.fromRgb(128, 128, 128)

    @JvmField
    val Honeydew = Color3.fromRgb(240, 255, 240)

    @JvmField
    val HotPink = Color3.fromRgb(255, 105, 180)

    @JvmField
    val IndianRed = Color3.fromRgb(205, 92, 92)

    @JvmField
    val Indigo = Color3.fromRgb(75, 0, 130)

    @JvmField
    val Ivory = Color3.fromRgb(255, 255, 240)

    @JvmField
    val Khaki = Color3.fromRgb(240, 230, 140)

    @JvmField
    val Lavender = Color3.fromRgb(230, 230, 250)

    @JvmField
    val LavenderBlush = Color3.fromRgb(255, 240, 245)

    @JvmField
    val LawnGreen = Color3.fromRgb(124, 252, 0)

    @JvmField
    val LemonChiffon = Color3.fromRgb(255, 250, 205)

    @JvmField
    val LightBlue = Color3.fromRgb(173, 216, 230)

    @JvmField
    val LightCoral = Color3.fromRgb(240, 128, 128)

    @JvmField
    val LightCyan = Color3.fromRgb(224, 255, 255)

    @JvmField
    val LightGoldenrodYellow = Color3.fromRgb(250, 250, 210)

    @JvmField
    val LightGray = Color3.fromRgb(211, 211, 211)

    @JvmField
    val LightGreen = Color3.fromRgb(144, 238, 144)

    @JvmField
    val LightGrey = Color3.fromRgb(211, 211, 211)

    @JvmField
    val LightPink = Color3.fromRgb(255, 182, 193)

    @JvmField
    val LightSalmon = Color3.fromRgb(255, 160, 122)

    @JvmField
    val LightSeaGreen = Color3.fromRgb(32, 178, 170)

    @JvmField
    val LightSkyBlue = Color3.fromRgb(135, 206, 250)

    @JvmField
    val LightSlateGray = Color3.fromRgb(119, 136, 153)

    @JvmField
    val LightSlateGrey = Color3.fromRgb(119, 136, 153)

    @JvmField
    val LightSteelBlue = Color3.fromRgb(176, 196, 222)

    @JvmField
    val LightYellow = Color3.fromRgb(255, 255, 224)

    @JvmField
    val Lime = Color3.fromRgb(0, 255, 0)

    @JvmField
    val LimeGreen = Color3.fromRgb(50, 205, 50)

    @JvmField
    val Linen = Color3.fromRgb(250, 240, 230)

    @JvmField
    val Magenta = Color3.fromRgb(255, 0, 255)

    @JvmField
    val Maroon = Color3.fromRgb(128, 0, 0)

    @JvmField
    val MediumAquamarine = Color3.fromRgb(102, 205, 170)

    @JvmField
    val MediumBlue = Color3.fromRgb(0, 0, 205)

    @JvmField
    val MediumOrchid = Color3.fromRgb(186, 85, 211)

    @JvmField
    val MediumPurple = Color3.fromRgb(147, 112, 219)

    @JvmField
    val MediumSeaGreen = Color3.fromRgb(60, 179, 113)

    @JvmField
    val MediumSlateBlue = Color3.fromRgb(123, 104, 238)

    @JvmField
    val MediumSpringGreen = Color3.fromRgb(0, 250, 154)

    @JvmField
    val MediumTurquoise = Color3.fromRgb(72, 209, 204)

    @JvmField
    val MediumVioletRed = Color3.fromRgb(199, 21, 133)

    @JvmField
    val MidnightBlue = Color3.fromRgb(25, 25, 112)

    @JvmField
    val MintCream = Color3.fromRgb(245, 255, 250)

    @JvmField
    val MistyRose = Color3.fromRgb(255, 228, 225)

    @JvmField
    val Moccasin = Color3.fromRgb(255, 228, 181)

    @JvmField
    val NavajoWhite = Color3.fromRgb(255, 222, 173)

    @JvmField
    val Navy = Color3.fromRgb(0, 0, 128)

    @JvmField
    val OldLace = Color3.fromRgb(253, 245, 230)

    @JvmField
    val Olive = Color3.fromRgb(128, 128, 0)

    @JvmField
    val OliveDrab = Color3.fromRgb(107, 142, 35)

    @JvmField
    val Orange = Color3.fromRgb(255, 165, 0)

    @JvmField
    val OrangeRed = Color3.fromRgb(255, 69, 0)

    @JvmField
    val Orchid = Color3.fromRgb(218, 112, 214)

    @JvmField
    val PaleGoldenrod = Color3.fromRgb(238, 232, 170)

    @JvmField
    val PaleGreen = Color3.fromRgb(152, 251, 152)

    @JvmField
    val PaleTurquoise = Color3.fromRgb(175, 238, 238)

    @JvmField
    val PaleVioletRed = Color3.fromRgb(219, 112, 147)

    @JvmField
    val PapayaWhip = Color3.fromRgb(255, 239, 213)

    @JvmField
    val PeachPuff = Color3.fromRgb(255, 218, 185)

    @JvmField
    val Peru = Color3.fromRgb(205, 133, 63)

    @JvmField
    val Pink = Color3.fromRgb(255, 192, 203)

    @JvmField
    val Plum = Color3.fromRgb(221, 160, 221)

    @JvmField
    val PowderBlue = Color3.fromRgb(176, 224, 230)

    @JvmField
    val Purple = Color3.fromRgb(128, 0, 128)

    @JvmField
    val RebeccaPurple = Color3.fromRgb(102, 51, 153)

    @JvmField
    val Red = Color3.fromRgb(255, 0, 0)

    @JvmField
    val RosyBrown = Color3.fromRgb(188, 143, 143)

    @JvmField
    val RoyalBlue = Color3.fromRgb(65, 105, 225)

    @JvmField
    val SaddleBrown = Color3.fromRgb(139, 69, 19)

    @JvmField
    val Salmon = Color3.fromRgb(250, 128, 114)

    @JvmField
    val SandyBrown = Color3.fromRgb(244, 164, 96)

    @JvmField
    val SeaGreen = Color3.fromRgb(46, 139, 87)

    @JvmField
    val SeaShell = Color3.fromRgb(255, 245, 238)

    @JvmField
    val Sienna = Color3.fromRgb(160, 82, 45)

    @JvmField
    val Silver = Color3.fromRgb(192, 192, 192)

    @JvmField
    val SkyBlue = Color3.fromRgb(135, 206, 235)

    @JvmField
    val SlateBlue = Color3.fromRgb(106, 90, 205)

    @JvmField
    val SlateGray = Color3.fromRgb(112, 128, 144)

    @JvmField
    val SlateGrey = Color3.fromRgb(112, 128, 144)

    @JvmField
    val Snow = Color3.fromRgb(255, 250, 250)

    @JvmField
    val SpringGreen = Color3.fromRgb(0, 255, 127)

    @JvmField
    val SteelBlue = Color3.fromRgb(70, 130, 180)

    @JvmField
    val Tan = Color3.fromRgb(210, 180, 140)

    @JvmField
    val Teal = Color3.fromRgb(0, 128, 128)

    @JvmField
    val Thistle = Color3.fromRgb(216, 191, 216)

    @JvmField
    val Tomato = Color3.fromRgb(255, 99, 71)

    @JvmField
    val Turquoise = Color3.fromRgb(64, 224, 208)

    @JvmField
    val Violet = Color3.fromRgb(238, 130, 238)

    @JvmField
    val Wheat = Color3.fromRgb(245, 222, 179)

    @JvmField
    val White = Color3.fromRgb(255, 255, 255)

    @JvmField
    val WhiteSmoke = Color3.fromRgb(245, 245, 245)

    @JvmField
    val Yellow = Color3.fromRgb(255, 255, 0)

    @JvmField
    val YellowGreen = Color3.fromRgb(154, 205, 50)
}
