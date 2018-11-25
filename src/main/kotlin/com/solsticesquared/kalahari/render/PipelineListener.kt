package com.solsticesquared.kalahari.render

import com.solsticesquared.kalahari.math.Bounds

/**
 * Represents a mechanism for being notified of rendering events from a
 * [Pipeline].
 */
interface PipelineListener {

    /**
     * Called whenever a [Pipeline] finishes rendering a scene.
     */
    fun onComplete()

    /**
     * Called whenever a single [Pixel] has been fully traced and is ready to
     * be visualized or serialized in some way.
     *
     * @param pixel
     *        The pixel that was emitted.
     */
    fun onEmit(pixel: Pixel)

    /**
     * Called before a [Pipeline] begins rendering a scene in order to ensure
     * all listeners are aware of the shape of the rendering window (which also
     * defines the total number of pixels to be drawn).
     *
     * @param bounds
     *        The bounds of the rendering window to use.
     */
    fun onStart(bounds: Bounds)
}
