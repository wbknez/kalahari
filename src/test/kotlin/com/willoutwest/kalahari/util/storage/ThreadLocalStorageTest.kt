package com.willoutwest.kalahari.util.storage

import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.random.Random

/**
 * Test suite for [ThreadLocalStorage].
 */
class ThreadLocalStorageTest : ShouldSpec() {

    init {

        "Modifying values in a thread local storage" {
            should("automatically discern unique thread identifiers for use " +
                   "as keys.") {
                assertAll(Gen.choose(1, 10)) { numThreads: Int ->
                    val random = Random(System.currentTimeMillis())
                    val storage = ThreadLocalStorage({ AtomicInteger(0) })
                    val weights = IntArray(numThreads) {
                        random.nextInt(100) + 1
                    }
                    val threads = Array(numThreads) {
                        thread {
                            repeat((0 until weights[it]).count()) {
                                storage.get().incrementAndGet()
                            }
                        }
                    }

                    threads.forEach{ it.join() }

                    repeat((0 until numThreads).count()) {
                        storage[threads[it].id]!!.get() shouldBe weights[it]
                    }
                }
            }
        }
    }
}