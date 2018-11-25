package com.solsticesquared.kalahari.render

import com.solsticesquared.kalahari.math.Bounds
import com.solsticesquared.kalahari.render.order.DrawingOrder
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable

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

    private val listeners: MutableList<PipelineListener> = mutableListOf()

    /**
     *
     *
     * @param listener
     *        The listener to add.
     */
    fun addListener(listener: PipelineListener) {
        this.listeners.add(listener)
    }

    /**
     *
     */
    fun run(tracer: Tracer, bounds: Bounds, order: DrawingOrder) {
        val renderables = order.create(bounds).toObservable()

        renderables
            .flatMap{ Observable.just(Pixel(it.x, it.y, tracer.trace(it).rgb)) }
            .subscribe(
                { pixel: Pixel -> this.listeners.forEach{ it.onEmit(pixel) } },
                {
                    t: Throwable -> throw PipelineException(
                        "Caught in the pipeline during rendering.", t
                    )
                },
                { this.listeners.forEach{ it.onComplete() } },
                { this.listeners.forEach{ it.onStart(bounds) } }
            )
    }
}
