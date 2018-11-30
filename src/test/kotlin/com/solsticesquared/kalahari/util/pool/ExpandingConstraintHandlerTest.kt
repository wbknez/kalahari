package com.solsticesquared.kalahari.util.pool

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.ShouldSpec

/**
 * Test suite for [ExpandingConstraintHandler].
 */
class ExpandingConstraintHandlerTest : ShouldSpec() {

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

        "A constraint handler that only uses linear increments" {
            should("increase the capacity strictly by that amount.") {
                val handler = ExpandingConstraintHandler(10, 0f)
                val pool = QueueObjectPool(10, handler, { 0 })

                pool.capacity shouldBe 10
                dump(11, pool)
                pool.capacity shouldBe 20
            }
        }

        "A constraint handler that only uses scalar increments" {
            should("increase the capacity strictly by that amount.") {
                val handler = ExpandingConstraintHandler(0, 0.5f)
                val pool = QueueObjectPool(10, handler, { 0 })

                pool.capacity shouldBe 10
                dump(11, pool)
                pool.capacity shouldBe 15
            }
        }

        "A constraint handler that uses both linear and scalar increments" {
            should("increase the capacity using both amounts.") {
                val handler = ExpandingConstraintHandler(10, 0.5f)
                val pool = QueueObjectPool(10, handler, { 0 })

                pool.capacity shouldBe 10
                dump(11, pool)
                pool.capacity shouldBe 25
            }
        }
    }
}
