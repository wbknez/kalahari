package com.willoutwest.kalahari.scene.shadow

/**
 * Represents an object that may both cast and receive shadows on and from
 * other objects (including light sources) in a scene.
 *
 * @property shadowMode
 *           Controls whether or not shadows may be cast, received, neither,
 *           or both.
 */
interface Shadowable : ShadowCaster {

    var shadowMode: ShadowMode

    /**
     * Returns whether or not this shadowable may cast shadows onto other
     * objects in a scene.
     *
     * @return Whether or not shadows may be cast.
     */
    fun isCastingShadows(): Boolean =
        this.shadowMode == ShadowMode.Cast ||
        this.shadowMode == ShadowMode.CastAndReceive

    /**
     * Returns whether or not this shadowable may receive shadows from
     * surrounding, or nearby, objects.
     *
     * @return Whether or not shadows may be received.
     */
    fun isReceivingShadows(): Boolean =
        this.shadowMode == ShadowMode.CastAndReceive ||
        this.shadowMode == ShadowMode.Receive
}