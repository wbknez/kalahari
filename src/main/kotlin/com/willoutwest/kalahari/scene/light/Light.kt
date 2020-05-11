package com.willoutwest.kalahari.scene.light

import com.willoutwest.kalahari.texture.Texture
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.scene.shadow.ShadowMode
import com.willoutwest.kalahari.util.ParameterMap
import com.willoutwest.kalahari.util.Typeable
import com.willoutwest.kalahari.util.hash

/**
 * Represents a collection of lighting parameters that controls the emittance
 * of radiance throughout a scene.
 *
 * @param name
 *        The light name to use.
 * @property castsShadows
 *           Controls whether or not shadows may be cast.
 * @property cL
 *           The radiance color.
 * @property kL
 *           The radiance intensity coefficient.
 * @property location
 *           The location in three-dimensional space.
 */
class Light(name: String) :
    Cloneable, ParameterMap(name), Typeable<Light.Type> {

    /**
     * Represents a unique identifier that denotes the type of bulb that
     * should be used to determine how radiance is emitted.
     */
    interface Type

    var castsShadows: ShadowMode = ShadowMode.Enable

    var cL: Texture by this.params

    var kL: Float by this.params

    var location: Point3 by this.params

    override var type: Type by this.params

    /**
     * Constructor.
     *
     * @param name
     *        The light name to use.
     * @param type
     *        The light type to use.
     */
    constructor(name: String, type: Type) : this(name) {
        this.type = type
    }

    /**
     * Constructor.
     *
     * @param light
     *        The light to copy from.
     */
    constructor(light: Light?) : this(light!!.id) {
        this.params.putAll(light.params)
    }

    override fun clone(): Light = Light(this)

    override fun equals(other: Any?): Boolean =
        when(other) {
            is Light -> super.equals(other) &&
                        this.type == other.type
            else     -> false
        }

    override fun hashCode(): Int = hash(super.hashCode(), this.type)

    /**
     * Returns whether or not this shadowable may cast shadows onto other
     * objects in a scene.
     *
     * @return Whether or not shadows may be cast.
     */
    fun isCastingShadows(): Boolean = this.castsShadows == ShadowMode.Enable
}