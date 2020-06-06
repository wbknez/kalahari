package com.willoutwest.kalahari.texture

/**
 * Represents how texture coordinates should be transformed during rendering.
 */
enum class TextureMode {

    /**
     * Represents a texture whose coordinates are transformed into a
     * geometric object's local space.
     *
     * This results in a texture whose transformations mirror those of the
     * geometric surface onto which it is displayed.
     */
    Local,

    /**
     * Represents a texture whose coordinates are not transformed into a
     * geometric object's local space.
     *
     * This results in a texture whose transformations ignore those of the
     * geometric surface onto which it is displayed.
     */
    World
}