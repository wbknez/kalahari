package com.willoutwest.kalahari.script.libraries

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.script.ScriptingLibrary
import com.willoutwest.kalahari.texture.FillTexture
import com.willoutwest.kalahari.texture.Texture

/**
 * Represents a mechanism for creating and working with [Texture] objects
 * in a Lua scripting environment.
 */
class TextureLibrary : ScriptingLibrary {

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
}