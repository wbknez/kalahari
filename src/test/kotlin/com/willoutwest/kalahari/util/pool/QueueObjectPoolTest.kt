package com.willoutwest.kalahari.util.pool

import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.ShouldSpec

class QueueObjectPoolGenerator<T>(val maxCapacity: Int,
                                  val handler: ConstraintHandler,
                                  val supplier: () -> T,
                                  val preallocate: Boolean)
    : Gen<QueueObjectPool<T>> {

    override fun constants(): Iterable<QueueObjectPool<T>> = emptyList()

    override fun random(): Sequence<QueueObjectPool<T>> = generateSequence {
        QueueObjectPool(Gen.choose(1, this.maxCapacity).random().first(),
                        this.handler, this.supplier, this.preallocate)
    }
}

fun <T> Gen.Companion.queuePool(maxCapacity: Int = 10,
                                handler: ConstraintHandler =
                                    FixedConstraintHandler(),
                                preallocate: Boolean = false,
                                supplier: () -> T):
    Gen<QueueObjectPool<T>> =
    QueueObjectPoolGenerator(maxCapacity, handler, supplier, preallocate)

inline fun <reified T> QueueObjectPool<T>.dump(amount: Int): Array<T> =
    Array(amount) { this.borrow() }

/**
 * Test suite for [QueueObjectPool].
 */
class QueueObjectPoolTest : ShouldSpec() {

    init {

        "Borrowing an object form an empty queue pool" {
            should("construct an object from a supplier") {
                assertAll(Gen.queuePool { 20 }) { pool: QueueObjectPool<Int> ->
                    repeat((0 until pool.capacity).count()) {
                        pool.borrow() shouldBe 20
                    }
                }
            }
        }

        "Borrowing an object from a non-empty pool" {
            should("re-use any existing objects") {
                assertAll(Gen.queuePool { 20 }) { pool: QueueObjectPool<Int> ->
                    val objs = pool.dump(Gen.choose(0, pool.capacity)
                                             .random()
                                             .first())

                    repeat((objs.indices).count()) {
                        objs[it] = Gen.int().filter{ it != 20 }
                                        .random()
                                        .first()
                    }

                    pool.reuse(*objs)

                    objs.forEach { pool.borrow() shouldBe it }
                }
            }
        }

        "Clearing a queue pool" {
            should("remove all created objects") {
                assertAll(Gen.queuePool { 0 }) { pool: QueueObjectPool<Int> ->

                    val objs = pool.dump(pool.capacity)

                    pool.available shouldBe 0
                    pool.borrowed shouldBe pool.capacity

                    repeat((objs.indices).count()) {
                        objs[it] = Gen.choose(1, 100).random().first()
                    }
                    pool.reuse(*objs)

                    pool.available shouldBe pool.capacity
                    pool.borrowed shouldBe 0

                    pool.borrow() shouldNotBe 0
                    pool.reuse(Gen.choose(1, 100).random().first())

                    pool.clear()

                    repeat((0 until pool.capacity).count()) {
                        pool.borrow() shouldBe 0
                    }
                }
            }
        }

        "Submitting a single object for re-use to a queue pool" {
            should("place the object at the head of the pool") {
                assertAll(Gen.queuePool { 20 }) { pool: QueueObjectPool<Int> ->
                    val objs = pool.dump(Gen.choose(0, pool.capacity)
                                             .random()
                                             .first())

                    repeat((objs.indices).count()) {
                        objs[it] = Gen.int().filter{ it != 20 }
                            .random()
                            .first()
                    }

                    objs.forEach {
                        pool.reuse(it)
                        pool.borrow() shouldBe it
                    }
                }
            }
        }

        "Submitting a single object for re-use to a full queue pool" {
            should("throw an exception") {
                assertAll(Gen.queuePool { 20 }) { pool: QueueObjectPool<Int> ->
                    shouldThrow<FullPoolException> {
                        pool.reuse(Gen.int().filter{ it != 20 }
                                       .random()
                                       .first())
                    }
                }
            }
        }
    }
}