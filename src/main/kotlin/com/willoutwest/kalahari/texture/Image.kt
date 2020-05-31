package com.willoutwest.kalahari.texture

import com.willoutwest.kalahari.math.Color3

/**
 * Represents a collection of raw color data read from disk.
 *
 * @property data
 *           The collection of image data.  This uses a stride of three to
 *           denote the color components of each individual pixel.
 * @property height
 *           The image height in pixels.
 * @property width
 *           The image width in pixels.
 */
data class Image(val width: Int, val height: Int,
                 private val data: List<Color3>) {

    init {
        require(height >= 1) {
            "The image height must be positive."
        }

        require(width >= 1) {
            "The image width must be positive."
        }

        require(data.size == (width * height)) {
            "The amount of image data must equal the width times the height."
        }
    }

    operator fun get(x: Int, y: Int): Color3 =
        this.data[x + this.width * (this.height - y - 1)]
}