package com.willoutwest.kalahari.util.pool

import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldThrow
import io.kotlintest.specs.ShouldSpec

/**
 * Test suite for [FixedConstraintHandler].
 */
class FixedConstraintHandlerTest : ShouldSpec() {

    private val handler: ConstraintHandler = FixedConstraintHandler()

    init {

        "Handling a violation by throwing an exception" {
            should("throw an exception") {
                assertAll(Gen.choose(0, 50)) { capacity: Int ->
                    val pool = QueueObjectPool(capacity, handler, { 0 })

                    pool.dump(pool.capacity)

                    shouldThrow<EmptyPoolException> {
                        pool.borrow()
                    }
                }
            }
        }
    }
}
