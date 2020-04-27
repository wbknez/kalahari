package com.willoutwest.kalahari.script.libraries

import com.willoutwest.kalahari.texture.Texture
import com.willoutwest.kalahari.material.Material
import com.willoutwest.kalahari.material.Shader
import com.willoutwest.kalahari.material.shaders.MatteShader
import com.willoutwest.kalahari.script.ResourceLibrary

/**
 * Represents a mechanism for creating and working with [Material] objects
 * in a Lua scripting environment.
 */
class MaterialLibrary : ResourceLibrary {

    /**
     * Represents the type of materials this project supports by default.
     */
    enum class Type : Material.Type {

        /**
         * Represents a material with diffuse shading only.
         */
        Matte
    }

    companion object {

        /**
         * Represents a mapping of materials to the shaders used to render
         * them.
         *
         * @return A mapping of material types to shaders.
         */
        fun defaultShaders(): Map<Material.Type, Shader> = mapOf(
            Type.Matte to MatteShader()
        )
    }

    /**
     * Creates a new matte material with the specified properties.
     *
     * @param name
     *        The material name to use.
     * @param cD
     *        The ambient and diffuse reflection color to use.
     * @param kA
     *        The ambient reflection coefficient to use.
     * @param kD
     *        The diffuse reflection coefficient to use.
     * @return A matte material.
     */
    fun matte(name: String, cD: Texture, kA: Float, kD: Float): Material {
        val mat = Material(name, Type.Matte)

        mat.cD = cD
        mat.kA = kA
        mat.kD = kD

        return mat
    }
}