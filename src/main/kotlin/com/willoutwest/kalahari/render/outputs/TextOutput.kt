package com.willoutwest.kalahari.render.outputs

import com.willoutwest.kalahari.render.Bounds
import com.willoutwest.kalahari.render.PipelineListener
import com.willoutwest.kalahari.render.Pixel
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Represents an implementation of [PipelineListener] that displays
 * rendering progress as text to the standard output.
 * 
 * @property barWidth
 *           The progress bar width in characters.
 * @property counter
 *           The current number of rendered pixels.
 * @property emptyChar
 *           The character to display for un-completed progress.
 * @property endLine
 *           The string to use 
 * @property fillChar
 *           The character to display for completed progress.
 * @property lock
 *           The stream write lock.
 * @property precision
 *           The number of percent complete digits to display.
 * @property prefix
 *           The string to display before the progress bar.
 * @property suffix
 *           The string to display after the progress bar.
 * @property total
 *           The total number of pixels to render (for progress calculations).
 */
class TextOutput(val prefix: String = "", val suffix: String = "",
                 val precision: Int = 2, val barWidth: Int = 50,
                 val fillChar: String = "█", val emptyChar: String = "-",
                 val endLine: String = "\r") :
    PipelineListener {

    private var counter: Int = 0

    private val lock: Lock = ReentrantLock()

    private var total: Int = 0

    override fun onComplete() {
        println()
    }

    override fun onEmit(pixel: Pixel) {
        this.lock.withLock {
            val current = ++this.counter

            val percent = "%.${this.precision}f".format(
                100 * current.toDouble() / this.total
            )
            val fillAmount = this.barWidth * current / this.total
            val progressBar = this.fillChar.repeat(fillAmount) +
                              this.emptyChar.repeat(this.barWidth - fillAmount)

            print("\r ${this.prefix} |$progressBar| $percent%")
        }
    }

    override fun onStart(bounds: Bounds) {
        this.counter = 0
        this.total   = bounds.area
    }
}