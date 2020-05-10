package com.willoutwest.kalahari.scene.shadow

/**
 * Represents an object that may both cast and receive shadows on and from
 * other objects (including light sources) in a scene.
 *
 * @property castsShadows
 *           Controls whether or not shadows may be cast.
 * @property receivesShadows
 *           Controls whether or not shadows may be received.
 */
interface Shadowable : ShadowCaster {

    var castsShadows: ShadowMode

    var receivesShadows: ShadowMode

    /**
     * Returns whether or not this shadowable may cast shadows onto other
     * objects in a scene.
     *
     * @return Whether or not shadows may be cast.
     */
    fun isCastingShadows(): Boolean = this.castsShadows == ShadowMode.Enable

    /**
     * Returns whether or not this shadowable may receive shadows from
     * surrounding, or nearby, objects.
     *
     * @return Whether or not shadows may be received.
     */
    fun isReceivingShadows(): Boolean =
        this.receivesShadows == ShadowMode.Enable
}