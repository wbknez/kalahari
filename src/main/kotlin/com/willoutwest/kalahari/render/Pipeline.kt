package com.willoutwest.kalahari.render

import com.willoutwest.kalahari.scene.Scene

/**
 * Represents a mechanism to apply a series of sequential transformations to
 * a stream of rays created from display coordinates.
 *
 * @property listeners
 *           The collection of objects to be notified of rendering events.
 * @property model
 *           The thread pool to use for parallelism (if any).
 */
class Pipeline(private val model: ThreadingModel) {

    private val listeners: MutableList<PipelineListener> = mutableListOf()

    /**
     * Adds the specified listener to this pipeline's collection of listeners.
     *
     * @param listener
     *        The listener to add.
     */
    fun addListener(listener: PipelineListener) {
        this.listeners.add(listener)
    }

    /**
     * Renders a full scene by iterating over all possible pixels in the
     * specified scene and obtaining a color value for each using the
     * specified ray tracing algorithm.
     *
     * @param scene
     *        The scene to trace.
     * @param tracer
     *        The ray tracing function to use.
     */
    fun submit(scene: Scene, tracer: Tracer) {
    }
}