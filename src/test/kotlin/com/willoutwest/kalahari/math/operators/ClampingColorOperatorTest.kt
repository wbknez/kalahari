package com.willoutwest.kalahari.math.operators

import com.willoutwest.kalahari.gen.smallFloats
import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.MathUtils.Companion.clamp
import com.willoutwest.kalahari.math.MathUtils.Companion.max
import com.willoutwest.kalahari.math.MathUtils.Companion.min
import com.willoutwest.kalahari.math.color3
import com.willoutwest.kalahari.math.shouldBe
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.specs.ShouldSpec

class ClampingColorOperatorGenerator(val redMin: Float, val redMax: Float,
                                     val greenMin: Float, val greenMax: Float,
                                     val blueMin: Float, val blueMax: Float) :
    Gen<ClampingColorOperator> {

    override fun constants(): Iterable<ClampingColorOperator> = emptyList()

    override fun random(): Sequence<ClampingColorOperator> = generateSequence {
        ClampingColorOperator(Color3(this.redMin, this.greenMin, this.blueMin),
                              Color3(this.redMax, this.greenMax, this.blueMax))
    }
}

fun Gen.Companion.clampingColorOperator(): Gen<ClampingColorOperator> {
    val extrema = FloatArray(6) {
        Gen.smallFloats().random().first()
    }

    return ClampingColorOperatorGenerator(min(extrema[0], extrema[1]),
                                          max(extrema[0], extrema[1]),
                                          min(extrema[2], extrema[3]),
                                          max(extrema[2], extrema[3]),
                                          min(extrema[4], extrema[5]),
                                          max(extrema[4], extrema[5]))
}

fun Gen.Companion.clampingColorOperator(redMin: Float, redMax: Float,
                                        greenMin: Float, greenMax: Float,
                                        blueMin: Float, blueMax: Float):
    Gen<ClampingColorOperator> =
    ClampingColorOperatorGenerator(redMin, redMax, greenMin, greenMax,
                                   blueMin, blueMax)

/**
 * Test suite for [ClampingColorOperator].
 */
class ClampingColorOperatorTest : ShouldSpec() {

    init {

        "Applying a clamping operator to a color" {
            should("clamp each component that is outside a specific range") {
                assertAll(Gen.clampingColorOperator(), Gen.color3()) {
                    operator: ClampingColorOperator, color: Color3 ->

                    val opMin = operator.min
                    val opMax = operator.max

                    operator.operate(color, Color3()).shouldBe(
                        when(color.red !in opMin.red..opMax.red) {
                            false -> color.red
                            true  -> clamp(color.red, opMin.red, opMax.red)
                        },
                        when(color.green !in opMin.green..opMax.green) {
                            false -> color.green
                            true  -> clamp(color.green, opMin.green,
                                           opMax.green)
                        },
                        when(color.blue !in opMin.blue..opMax.blue) {
                            false -> color.blue
                            true  -> clamp(color.blue, opMin.blue, opMax.blue)
                        }
                    )
                }
            }

            should("not modify components that are outside a specific range") {
                assertAll(Gen.clampingColorOperator(-1f, 2f, -1f, 2f, -1f, 2f),
                          Gen.color3()) {
                    operator: ClampingColorOperator, color: Color3 ->

                    operator.operate(color, Color3()).shouldBe(color)
                }
            }
        }
    }
}