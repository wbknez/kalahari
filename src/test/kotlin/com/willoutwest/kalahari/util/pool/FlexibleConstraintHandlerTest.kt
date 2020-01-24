package com.willoutwest.kalahari.util.pool

import com.willoutwest.kalahari.gen.smallFloats
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.ShouldSpec
import java.lang.IllegalArgumentException

class FlexibleConstraintHandlerGenerator(val increment: Int,
                                         val scale: Float,
                                         val chooseIncrement: Boolean,
                                         val chooseScale: Boolean)
    : Gen<FlexibleConstraintHandler> {

    override fun constants(): Iterable<FlexibleConstraintHandler> = emptyList()

    override fun random(): Sequence<FlexibleConstraintHandler> =
        generateSequence {
            val chosenIncrement = when(this.chooseIncrement) {
                false -> this.increment
                true  -> Gen.positiveIntegers().random().first()
            }
            val chosenScale     = when(this.chooseScale) {
                false -> this.scale
                true  -> Gen.smallFloats().filter{ it > 0.1f }.random().first()
            }

            FlexibleConstraintHandler(chosenIncrement, chosenScale)
        }
}

fun Gen.Companion.flexibleConstraintHandler(): Gen<FlexibleConstraintHandler> =
    FlexibleConstraintHandlerGenerator(0, 0f, true, true)

fun Gen.Companion.flexibleConstraintHandler(increment: Int)
    :Gen<FlexibleConstraintHandler> =
    FlexibleConstraintHandlerGenerator(increment, 0f, false, true)

fun Gen.Companion.flexibleConstraintHandler(scale: Float)
    :Gen<FlexibleConstraintHandler> =
    FlexibleConstraintHandlerGenerator(0, scale, true, false)

/**
 * Test suite for [FlexibleConstraintHandler].
 */
class FlexibleConstraintHandlerTest : ShouldSpec() {

    init {

        "Creating a flexible handler with an increment less than zero" {
            should("throw an exception") {
                shouldThrow<IllegalArgumentException> {
                    FlexibleConstraintHandler(-1, 0f)
                }
            }
        }

        "Creating a flexible handler with a scale less than zero" {
            should("throw an exception") {
                shouldThrow<IllegalArgumentException> {
                    FlexibleConstraintHandler(1, -0.25f)
                }
            }
        }

        "Creating a flexible handler with both an increment and scale of " +
        "zero" {
            should("throw an exception") {
                shouldThrow<IllegalArgumentException> {
                    FlexibleConstraintHandler(0, 0f)
                }
            }
        }

        "Handling a violation by linearly modifying the pool size only" {
            should("increase the pool capacity only by that amount") {
                assertAll(Gen.flexibleConstraintHandler(0f),
                          Gen.choose(0, 50)) {
                    handler: FlexibleConstraintHandler, capacity: Int ->

                    val pool = QueueObjectPool(capacity, handler, { 0 })

                    pool.dump(capacity + 1)

                    pool.capacity shouldBe (capacity + handler.increment)
                }
            }
        }

        "Handling a violation by scaling the pool size only" {
            should("increase the pool capacity by a factor of that amount") {
                assertAll(Gen.flexibleConstraintHandler(0),
                          Gen.choose(0, 50)) {
                    handler: FlexibleConstraintHandler, capacity: Int ->

                    val pool = QueueObjectPool(capacity, handler, { 0 })
                    val computed = pool.capacity +
                                   (pool.capacity * handler.scale).toInt()

                    pool.dump(capacity + 1)

                    when(computed == capacity) {
                        false -> pool.capacity shouldBe computed
                        true  -> pool.capacity shouldBe (capacity + 1)
                    }
                }
            }
        }

        "Handling a violation by both scaling and linearly modifying the " +
        "pool size" {
            should("increase the pool capacity by both that amount and a " +
                   "factor of it") {
                assertAll(Gen.flexibleConstraintHandler(),
                          Gen.choose(0, 50)) {
                    handler: FlexibleConstraintHandler, capacity: Int ->

                    val pool = QueueObjectPool(capacity, handler, { 0 })
                    val computed = pool.capacity + handler.increment +
                                   (pool.capacity * handler.scale).toInt()

                    pool.dump(capacity + 1)

                    when(computed == capacity) {
                        false -> pool.capacity shouldBe computed
                        true  -> pool.capacity shouldBe (capacity + 1)
                    }
                }
            }
        }
    }
}