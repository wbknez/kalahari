package com.solsticesquared.kalahari.render

/**
 * Represents a mechanism for being notified of rendering events from a
 * [Pipeline].
 */
interface PipelineListener {

    /**
     * Called whenever a single [Pixel] has been fully traced and is ready to
     * be visualized or serialized in some way.
     *
     * @param pixel
     *        The pixel that was emitted.
     */
    fun onEmit(pixel: Pixel)
}
