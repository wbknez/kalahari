package com.solsticesquared.kalahari.util.pool

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.specs.ShouldSpec

/**
 * Test suite for [QueueObjectPool].
 */
class QueueObjectPoolTest : ShouldSpec() {

    companion object {

        /**
         * Borrows the specified number of objects from the specified object
         * pool.
         *
         * This is primarily used to test boundary cases that may trigger
         * constraint violations.
         *
         * @param amount
         *        The number of objects to borrow.
         * @param pool
         *        The pool to borrow from.
         */
        fun dump(amount: Int, pool: ObjectPool<*>) {
            for(i in 0..(amount - 1)) {
                pool.borrow()
            }
        }
    }

    init {

        "Clearing an object pool" {
            should("remove all created objects from the pool.") {
                val pool = QueueObjectPool(10, ConstantConstraintHandler(),
                                           { 200 })

                dump(6, pool)
                pool.available shouldBe 4
                pool.borrowed shouldBe 6
                pool.capacity shouldBe 10

                pool.reuse(15, 30, 45)
                pool.available shouldBe 7
                pool.borrowed shouldBe 3
                pool.capacity shouldBe 10
                pool.borrow() shouldBe 15

                pool.clear()
                pool.available shouldBe 6
                pool.borrowed shouldBe 4
                pool.capacity shouldBe 10
                pool.borrow() shouldBe 200
            }
        }

        "Requesting an object from an empty pool" {
            should("construct a new object using a supplier.") {
                val pool = QueueObjectPool(10, ConstantConstraintHandler(),
                                           { 200 })

                pool.available shouldBe 10
                pool.borrowed shouldBe 0
                pool.capacity shouldBe 10

                pool.borrow() shouldBe 200

                pool.available shouldBe 9
                pool.borrowed shouldBe 1
                pool.capacity shouldBe 10
            }
        }

        "Requesting an object from a non-empty pool" {
            should("reuse an object that is already in the pool.") {
                val pool = QueueObjectPool(10, ConstantConstraintHandler(),
                                           { 200 })

                pool.borrow() shouldBe 200
                pool.reuse(32)
                pool.borrow() shouldBe 32
                pool.borrow() shouldBe 200
            }
        }

        "Reusing a single object" {
            should("throw if too many are returned.") {
                val pool = QueueObjectPool(10, ConstantConstraintHandler(),
                                           { 200 })

                shouldThrow<ObjectPoolException>{
                    pool.reuse(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
                }
            }
        }

        "Reusing an array of objects" {
            should("put them all back in the pool in order.") {
                val pool = QueueObjectPool(10, ConstantConstraintHandler(),
                                           { 200 })

                dump(4, pool)
                pool.available shouldBe 6
                pool.borrowed shouldBe 4
                pool.capacity shouldBe 10

                pool.reuse(10, 20, 30, 40)
                pool.available shouldBe 10
                pool.borrowed shouldBe 0
                pool.capacity shouldBe 10

                val objs = IntArray(4)

                for(i in 0..3) {
                    objs[i] = pool.borrow()
                }

                objs[0] shouldBe 10
                objs[1] shouldBe 20
                objs[2] shouldBe 30
                objs[3] shouldBe 40
            }

            should("throw if too many are returned.") {
                val pool = QueueObjectPool(10, ConstantConstraintHandler(),
                                           { 200 })

                shouldThrow<ObjectPoolException>{
                    pool.reuse(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)
                }
            }
        }
    }
}
