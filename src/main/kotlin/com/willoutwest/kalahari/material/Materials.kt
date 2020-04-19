package com.willoutwest.kalahari.material

import com.willoutwest.kalahari.material.shaders.MatteShader

/**
 * Represents a collection of utility methods for creating and working with
 * [Material] objects.
 */
sealed class Materials {

    /**
     * Represents the type of materials this project supports by default.
     */
    enum class Type : Material.Type {

        /**
         *
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
        fun matte(name: String, cD: ColorSource, kA: Float, kD: Float):
            Material {
            val mat = Material(name, Type.Matte)

            mat.cD = cD
            mat.kA = kA
            mat.kD = kD

            return mat
        }
    }
}