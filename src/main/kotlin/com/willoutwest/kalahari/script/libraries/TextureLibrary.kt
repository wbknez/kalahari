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
}