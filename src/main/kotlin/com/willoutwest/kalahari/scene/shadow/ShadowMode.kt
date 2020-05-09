package com.willoutwest.kalahari.scene.shadow

/**
 * Represents a mechanism for specifying how physical objects  in a
 * scene may cast or receive shadows onto or from others.
 */
enum class ShadowMode {

    /**
     * Represents an object that may both cast shadows on and receive shadows
     * from other objects.
     *
     * This is the default shadow mode for most actors and other objects.
     */
    CastAndReceive,

    /**
     * Represents an object that may only cast shadows on other objects;
     * shadow rays that intersect its geometry will be ignored and not visible.
     */
    Cast,

    /**
     * Represents an object that may neither cast on other objects nor be
     * affected by those shadows that fall upon it.
     */
    Disable,

    /**
     * Represents an object that may only receive shadows from other objects;
     * any kind of shadow that originates from its own geometry will not be
     * visible.
     */
    Receive,
}
