package com.solsticesquared.kalahari.util.storage

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.ShouldSpec
import java.util.Random
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

/**
 * Test suite for [ThreadLocalStorage].
 */
class ThreadLocalStorageTest : ShouldSpec() {

    init {

        "Thread local storage" {

            should("be accessed by a single thread only.") {
                val random = Random(System.currentTimeMillis())
                val numThreads = random.nextInt(5) + 2
                val storage =
                    ThreadLocalStorage({ AtomicInteger(0) })
                val weights = IntArray(numThreads){ random.nextInt(100) + 1 }

                val ths = Array(numThreads){
                    thread {
                        for(i in 1..weights[it]) {
                            storage.get().incrementAndGet()
                        }
                    }
                }

                ths.forEach{ it.join() }

                for(i in 0..(numThreads - 1)) {
                    storage[ths[i].id]!!.get() shouldBe weights[i]
                }
            }
        }
    }
}
