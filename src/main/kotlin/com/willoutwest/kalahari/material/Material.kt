package com.willoutwest.kalahari.material

import com.willoutwest.kalahari.math.sample.Sampler3
import com.willoutwest.kalahari.scene.shadow.ShadowMode
import com.willoutwest.kalahari.texture.Texture
import com.willoutwest.kalahari.util.ParameterMap
import com.willoutwest.kalahari.util.Typeable
import com.willoutwest.kalahari.util.hash

/**
 * Represents a collection of rendering parameters that controls the
 * colorization of reflected light from an arbitrary geometric surface.
 *
 * @param name
 *        The material name to use.
 * @property cD
 *           The ambient and diffuse reflection color.
 * @property cE
 *           The emissive color.
 * @property cR
 *           The perfect reflection color.
 * @property cS
 *           The specular reflection color.
 * @property exp
 *           The specular reflection exponent.
 * @property expS
 *           The glossy specular reflection exponent.
 * @property kA
 *           The ambient reflection coefficient.
 * @property kD
 *           The diffuse reflection coefficient.
 * @property kE
 *           The emissive coefficient.
 * @property kR
 *           The perfect reflection coefficient.
 * @property kS
 *           The specular reflection coefficient.
 * @property receivesShadows
 *           Controls whether or not shadows may be received.
 * @property sampler
 *           The material sampler.
 */
class Material(name: String) :
    Cloneable, ParameterMap(name), Typeable<Material.Type> {

    /**
     * Represents a unique identifier that denotes the type of shader that
     * should be used to determine the color of light reflected from a
     * material.
     */
    interface Type

    var cD: Texture by this.params

    var cE: Texture by this.params

    var cR: Texture by this.params

    var cS: Texture by this.params

    var exp: Float by this.params

    var expS: Float by this.params

    var kA: Float by this.params

    var kD: Float by this.params

    var kE: Float by this.params

    var kR: Float by this.params

    var kS: Float by this.params

    var receivesShadows: ShadowMode = ShadowMode.Enable

    var sampler: Sampler3 by this.params

    override var type: Type by this.params

    /**
     * Constructor.
     *
     * @param name
     *        The material name to use.
     * @param type
     *        The material type to use.
     */
    constructor(name: String, type: Type) : this(name) {
        this.type = type
    }

    /**
     * Constructor.
     *
     * @param material
     *        The material to copy from.
     */
    constructor(material: Material?) : this(material!!.id) {
        this.params.putAll(material.params)
    }

    override fun clone(): Material = Material(this)

    override fun equals(other: Any?): Boolean =
        when(other) {
            is Material -> super.equals(other) &&
                           this.type == other.type
            else        -> false
        }

    override fun hashCode(): Int = hash(super.hashCode(), this.type)

    /**
     * Returns whether or not this shadowable may receive shadows from
     * surrounding, or nearby, objects.
     *
     * @return Whether or not shadows may be received.
     */
    fun isReceivingShadows(): Boolean =
        this.receivesShadows == ShadowMode.Enable

    /**
     * Updates the inverse transformation matrices of all textures in this
     * material.
     */
    fun updateTextureTransforms() {
        this.params.values.forEach {
            when(it is Texture) {
                false -> Unit
                true  -> it.updateTransform()
            }
        }
    }
}