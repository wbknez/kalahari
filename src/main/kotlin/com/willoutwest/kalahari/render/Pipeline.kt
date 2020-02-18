package com.willoutwest.kalahari.render

import com.willoutwest.kalahari.scene.Scene
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Represents a mechanism to apply a series of sequential transformations to
 * a stream of rays created from display coordinates.
 *
 * @property listeners
 *           The collection of objects to be notified of rendering events.
 * @property model
 *           The thread pool to use for parallelism (if any).
 */
class Pipeline(numThreads: Int) : AutoCloseable {

    companion object {

        /**
         * The logging utility.
         */
        private val logger = Logger.getLogger(Pipeline::class.java.name)
    }

    private val listeners: MutableList<PipelineListener> = mutableListOf()

    private val model: ThreadingModel = ThreadingModel(numThreads)

    /**
     * Adds the specified listener to this pipeline's collection of listeners.
     *
     * @param listener
     *        The listener to add.
     */
    fun addListener(listener: PipelineListener) {
        this.listeners.add(listener)
    }

    override fun close() {
        this.model.close()
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
        tracer.drawOrder.orderOf(scene.viewport.bounds).toObservable()
            .doOnNext{ logger.log(Level.INFO, "Tracing pixel: {}.", it) }
            .doOnComplete{
                logger.log(Level.INFO, "Traced {} pixels.",
                           scene.viewport.bounds.area)
                logger.log(Level.INFO, "Rendering complete!")
            }
            .flatMap{
                Observable.just(Pixel(it.x, it.y, tracer.trace(it, scene).rgb))
            }
            .doOnNext{
                logger.log(Level.INFO, "Pixel traced: {}.", it)
            }
            .subscribe(
                { pixel: Pixel -> this.listeners.forEach{ it.onEmit(pixel) } },
                {
                    t: Throwable -> throw PipelineException(
                    "Caught in the main pipeline during rendering.", t
                    )
                },
                { this.listeners.forEach{ it.onComplete() } },
                { this.listeners.forEach{ it.onStart(scene.viewport.bounds) } }
            )
    }
}