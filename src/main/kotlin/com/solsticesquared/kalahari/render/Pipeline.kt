package com.solsticesquared.kalahari.render

import com.solsticesquared.kalahari.math.Bounds
import com.solsticesquared.kalahari.render.order.DrawingOrder
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import org.slf4j.LoggerFactory

/**
 * Represents an implementation of [RuntimeException] that is thrown when a
 * [Pipeline] encounters an error during processing.
 *
 * @param msg
 *        The message describing the error.
 * @param t
 *        The exception representing the error.
 */
class PipelineException(msg: String?, t: Throwable?) : RuntimeException(msg, t)

/**
 * Represents a mechanism that produces a visualization of a scene by applying a
 * series of sequential transformational operations to a stream of display
 * coordinates.
 *
 * @property listeners
 *           The collection of objects that are notified of rendering events.
 */
class Pipeline {

    companion object {

        /**
         * The logging utility.
         */
        private val logger = LoggerFactory.getLogger(Pipeline::class.java)
    }

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
     * specified bounds (with the specified drawing order) and obtaining a
     * color value for each using the specified ray tracing algorithm.
     *
     * @param tracer
     *        The ray tracing algorithm to use.
     * @param bounds
     *        The scene bounds to use.
     * @param order
     *        The pixel drawing order to use.
     * @throws PipelineException
     *         If there was a problem rendering the scene.
     */
    fun run(tracer: Tracer, bounds: Bounds, order: DrawingOrder) {
        val renderables = order.create(bounds).toObservable()

        renderables
            .doOnNext{ logger.info("Tracing pixel: {}.", it) }
            .doOnComplete{
                logger.info("Traced {} pixels.", bounds.area)
                logger.info("Rendering complete!")
            }
            .flatMap{ Observable.just(Pixel(it.x, it.y, tracer.trace(it).rgb)) }
            .doOnNext{ logger.info("Pixel traced: {}.", it) }
            .subscribe(
                { pixel: Pixel -> this.listeners.forEach{ it.onEmit(pixel) } },
                {
                    t: Throwable -> throw PipelineException(
                        "Caught in the main pipeline during rendering.", t
                    )
                },
                { this.listeners.forEach{ it.onComplete() } },
                { this.listeners.forEach{ it.onStart(bounds) } }
            )
    }
}
