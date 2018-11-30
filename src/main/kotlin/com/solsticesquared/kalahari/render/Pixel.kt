package com.solsticesquared.kalahari.render

/**
 * Represents a single, drawable RGB (red, green, and blue) color produced
 * from a ray tracing algorithm.
 *
 * Pixels represent the final stage of the rendering pipeline: visualization
 * via output.  They contain the finalized color that is created from all
 * individual processes such as sampling, lighting, post-processing, and gamma
 * correction.  Because of this, pixels are only suitable for output and may
 * not be used for any additional ray tracing computations.
 *
 * @property x
 *           The x-axis coordinate in pixels.
 * @property y
 *           The y-axis coordinate in pixels.
 * @property rgb
 *           The RGB color value.
 */
data class Pixel(val x: Int, val y: Int, val rgb: Int) : Cloneable
