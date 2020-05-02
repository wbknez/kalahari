package com.willoutwest.kalahari.script.libraries

import com.willoutwest.kalahari.texture.Texture
import com.willoutwest.kalahari.material.Material
import com.willoutwest.kalahari.material.Shader
import com.willoutwest.kalahari.material.shaders.MatteShader
import com.willoutwest.kalahari.material.shaders.PhongShader
import com.willoutwest.kalahari.script.ScriptingLibrary
import com.willoutwest.kalahari.texture.FillTexture

/**
 * Represents a mechanism for creating and working with [Material] objects
 * in a Lua scripting environment.
 */
class MaterialLibrary : ScriptingLibrary {

    /**
     * Represents the type of materials this project supports by default.
     */
    enum class Type : Material.Type {

        /**
         * Represents a material with diffuse shading only.
         */
        Matte,

        /**
         * Represents a material with both diffuse and specular shading.
         */
        Phong
    }

    companion object {

        /**
         * Represents a mapping of materials to the shaders used to render
         * them.
         *
         * @return A mapping of material types to shaders.
         */
        fun defaultShaders(): Map<Material.Type, Shader> = mapOf(
            Type.Matte to MatteShader(),
            Type.Phong to PhongShader()
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

    /**
     * Creates a new Phong material with the specified properties.
     *
     * This method sets the specular reflection color to white, resulting in
     * specular reflection that is a blend of the material diffuse and light
     * coloring only.
     *
     * @param name
     *        The material name to use.
     * @param cD
     *        The ambient and diffuse reflection color to use.
     * @param exp
     *        The specular exponent to use.
     * @param kA
     *        The ambient reflection coefficient to use.
     * @param kD
     *        The diffuse reflection coefficient to use.
     * @param kS
     *        The specular reflection coefficient to use.
     * @return A Phong material.
     */
    fun phong(name: String, cD: Texture, exp: Float, kA: Float, kD: Float,
              kS: Float): Material =
        phong(name, cD, FillTexture.White, exp, kA, kD, kS)

    /**
     * Creates a new Phong material with the specified properties.
     *
     * @param name
     *        The material name to use.
     * @param cD
     *        The ambient and diffuse reflection color to use.
     * @param cS
     *        The specular reflection color to use.
     * @param exp
     *        The specular exponent to use.
     * @param kA
     *        The ambient reflection coefficient to use.
     * @param kD
     *        The diffuse reflection coefficient to use.
     * @param kS
     *        The specular reflection coefficient to use.
     * @return A Phong material.
     */
    fun phong(name: String, cD: Texture, cS: Texture, exp: Float, kA: Float,
              kD: Float, kS: Float): Material {
        val mat = Material(name, Type.Phong)

        mat.cD  = cD
        mat.cS  = cS
        mat.exp = exp
        mat.kA  = kA
        mat.kD  = kD
        mat.kS  = kS

        return mat
    }
}