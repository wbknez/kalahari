package com.willoutwest.kalahari.texture.patterns

import com.willoutwest.kalahari.texture.Texture

/**
 * Represents a basic collection of properties for creating a checker pattern.
 *
 * @property evenColor
 *           The first checker color.
 * @property oddColor
 *           The second checker color.
 * @property scale
 *           The scaling factor for each checker.
 */
abstract class AbstractCheckerTexture(val evenColor: Texture,
                                      val oddColor: Texture,
                                      val scale: Float) : Texture {
    init {
        require(this.scale > 0f) {
            "Checker scale must be positive."
        }
    }
}
