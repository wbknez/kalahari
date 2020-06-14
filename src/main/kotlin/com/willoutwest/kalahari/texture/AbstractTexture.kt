package com.willoutwest.kalahari.texture

import com.willoutwest.kalahari.scene.AbstractMovable
import com.willoutwest.kalahari.scene.Movable

/**
 * Represents an implementation of [Texture] that adds transformation
 * capabilities for deriving classes.
 */
abstract class AbstractTexture : AbstractMovable(), Movable, Texture