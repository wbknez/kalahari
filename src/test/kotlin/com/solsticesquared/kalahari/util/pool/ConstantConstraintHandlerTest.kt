package com.solsticesquared.kalahari.util.pool

import io.kotlintest.matchers.shouldThrow
import io.kotlintest.specs.ShouldSpec

/**
 * Test suite for [ConstantConstraintHandler].
 */
class ConstantConstraintHandlerTest : ShouldSpec() {

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

        "An object pool that overflows with a constant constraint handler" {
            should("throw an exception to indicate the overflow.") {
                val handler = ConstantConstraintHandler()
                val pool = QueueObjectPool(10, handler, { 0 })

                shouldThrow<ObjectPoolException> { dump(11, pool) }
            }
        }
    }
}
