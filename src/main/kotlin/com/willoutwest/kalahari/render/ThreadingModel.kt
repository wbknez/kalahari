package com.willoutwest.kalahari.render

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Represents a mechanism for providing for all of this project's
 * parallelization needs (both Java and RXKotlin) with a single shared work
 * pool of threads.
 *
 * @param numThreads
 *        The number of threads to use.
 * @property pool
 *           A shared thread pool of finite size that uses work-stealing to
 *           manage tasks.
 * @property scheduler
 *           The RxJava compatible scheduler backed by a shared thread pool.
 */
class ThreadingModel(numThreads: Int) : AutoCloseable {

    private val pool: ExecutorService =
        Executors.newWorkStealingPool(numThreads)

    val scheduler: Scheduler = Schedulers.from(this.pool)

    init {
        require(numThreads >= 1) {
            "The number of threads must be positive."
        }
    }

    override fun close() {
        this.pool.shutdownNow()
    }
}