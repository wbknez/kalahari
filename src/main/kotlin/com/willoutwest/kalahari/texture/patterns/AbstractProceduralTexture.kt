package com.willoutwest.kalahari.texture.patterns

import com.willoutwest.kalahari.math.noise.Speaker
import com.willoutwest.kalahari.texture.AbstractTexture
import com.willoutwest.kalahari.texture.Texture

/**
 * Represents an implementation of [Texture] that provides the
 * infrastructure to create procedural textures using one or more noise
 * outputs.
 *
 * @property speaker
 *           The speaker to use for noise output.
 */
abstract class AbstractProceduralTexture(val speaker: Speaker) :
    AbstractTexture(), Texture