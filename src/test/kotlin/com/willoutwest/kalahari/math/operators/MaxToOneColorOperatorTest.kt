package com.willoutwest.kalahari.math.operators

import com.willoutwest.kalahari.gen.positiveFloats
import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.color3
import com.willoutwest.kalahari.math.shouldBe
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.specs.ShouldSpec
import kotlin.math.max

/**
 * Test suite for [MaxToOneColorOperator].
 */
class MaxToOneColorOperatorTest : ShouldSpec() {

    private val operator = MaxToOneColorOperator()

    init {

        "Applying a max-to-one operator to a color" {
            should("divide each component by the maximum component if any " +
                   "are greater than one") {
                assertAll(Gen.color3(), Gen.positiveFloats()) {
                    color: Color3, scalar: Float ->
                    color.timesSelf(scalar)

                    val maxComponent    = max(color.red,
                                              max(color.green, color.blue))
                    val invMaxComponent = 1f / maxComponent

                    operator.operate(color, Color3()).shouldBe(
                        when(maxComponent > 1f) {
                            false -> color.red
                            true  -> color.red * invMaxComponent
                        },
                        when(maxComponent > 1f) {
                            false -> color.green
                            true  -> color.green * invMaxComponent
                        },
                        when(maxComponent > 1f) {
                            false -> color.blue
                            true  -> color.blue * invMaxComponent
                        }
                    )
                }
            }

            should("not modify any components if the maximum component is " +
                   "less than or equal to one") {
                assertAll(Gen.color3()) { color: Color3 ->
                    operator.operate(color, Color3()).shouldBe(color)
                }
            }
        }
    }
}