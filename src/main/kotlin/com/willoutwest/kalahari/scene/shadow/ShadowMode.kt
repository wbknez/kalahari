package com.willoutwest.kalahari.scene.shadow

/**
 * Represents a mechanism for specifying how physical objects in a
 * scene respond to shadowing operations.
 */
enum class ShadowMode {

    /**
     * Represents an object that is not affected by a specific shadowing
     * operation.
     */
    Disable,

    /**
     * Represents an object that is affected by a specific shadowing operation.
     */
    Enable
}
