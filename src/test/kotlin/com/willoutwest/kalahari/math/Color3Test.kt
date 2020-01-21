package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.gen.nonzeroFloats
import com.willoutwest.kalahari.gen.smallFloats
import io.kotlintest.Matcher
import io.kotlintest.MatcherResult
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import java.awt.Color
import kotlin.math.pow

class Color3Generator : Gen<Color3> {

    override fun constants(): Iterable<Color3> = emptyList()

    override fun random(): Sequence<Color3> = generateSequence {
        Color3(Gen.smallFloats().random().first(),
               Gen.smallFloats().random().first(),
               Gen.smallFloats().random().first())
    }
}

fun Gen.Companion.color3(): Gen<Color3> = Color3Generator()

private fun equalsTo(color: Color3) = object : Matcher<Color3> {

    override fun test(value: Color3): MatcherResult =
        MatcherResult(value == color,
                      "Color $value should be $color",
                      "Color $value should not be $color")
}

fun Color3.shouldBe(other: Color3) =
    this shouldBe equalsTo(other)

fun Color3.shouldBe(red: Float, green: Float, blue: Float) =
    this shouldBe equalsTo(Color3(red, green, blue))

/**
 * Test suite for [Color3].
 */
class Color3Test : ShouldSpec() {

    init {
        "Converting a float component to an integer" {
            should("transform 1f to 255.") {
                componentToInt(1f) shouldBe 255
            }

            should("transform 0f to 0.") {
                componentToInt(0f) shouldBe 0
            }

            should("transform all intermediate values correctly.") {
                assertAll(Gen.smallFloats()) { comp: Float ->
                    val expected = when(comp != 1f) {
                        false -> 255
                        true  -> (comp * 256f).toInt()
                    }

                    componentToInt(comp) shouldBe expected
                }
            }
        }

        "Converting an integer value to a float component" {
            should("transform 255 to 1f.") {
                intToComponent(255).shouldBe(1f)
            }

            should("transform 0 to 0f.") {
                intToComponent(0).shouldBe(0f)
            }

            should("transform all intermediate values correctly.") {
                assertAll(Gen.choose(0, 255)) { value: Int ->
                    when(value != 255) {
                        false -> intToComponent(value).shouldBe(1f)
                        true  -> intToComponent(value).shouldBe(value / 256f)
                    }
                }
            }
        }

        "Dividing a color by a scalar" {
            should("inversely scale each component by that scalar.") {
                assertAll(Gen.color3(), Gen.nonzeroFloats()) {
                    color: Color3, scalar: Float ->

                    val ds = 1f / scalar

                    (color / scalar).shouldBe(color.red  * ds,
                                              color.green * ds,
                                              color.blue * ds)
                }
            }
        }

        "Dividing a color by a scalar in place" {
            should("inversely scale each of its components.") {
                assertAll(Gen.color3(), Gen.nonzeroFloats()) {
                    color: Color3, scalar: Float ->

                    val ds     = 1f / scalar

                    color.clone().divSelf(scalar).shouldBe(color.red * ds,
                                                           color.green * ds,
                                                           color.blue * ds)
                }
            }
        }

        "Linearly interpolating to two colors over an interval" {
            should("be the lower bound when t is zero.") {
                assertAll(Gen.color3(), Gen.color3()) { a: Color3, b: Color3 ->
                    a.lerp(b, 0f).shouldBe(a)
                }
            }

            should("be the upper bound when t is one.") {
                assertAll(Gen.color3(), Gen.color3()) { a: Color3, b: Color3 ->
                    a.lerp(b, 1f).shouldBe(a + (b - a) * 1f)
                }
            }

            should("be the percentage increment otherwise.") {
                assertAll(Gen.color3(), Gen.color3(), Gen.smallFloats()) {
                    a: Color3, b: Color3, t: Float ->

                    a.lerp(b, t).shouldBe(a + (b - a) * t)
                }
            }
        }

        "Linearly interpolating two colors over an interval in place" {
            should("be the lower bound when t is zero.") {
                assertAll(Gen.color3(), Gen.color3()) { a: Color3, b: Color3 ->
                    a.clone().lerpSelf(b, 0f).shouldBe(a)
                }
            }

            should("be the upper bound when t is one.") {
                assertAll(Gen.color3(), Gen.color3()) { a: Color3, b: Color3 ->
                    a.clone().lerpSelf(b, 1f).shouldBe(a + (b - a) * 1f)
                }
            }

            should("be the percentage increment otherwise.") {
                assertAll(Gen.color3(), Gen.color3(), Gen.smallFloats()) {
                    a: Color3, b: Color3, t: Float ->

                    a.clone().lerpSelf(b, t).shouldBe(a + (b - a) * t)
                }
            }
        }

        "Subtracting a color from another" {
            should("subtract each color's components.") {
                assertAll(Gen.color3(), Gen.color3()) { a: Color3, b: Color3 ->
                    (a - b).shouldBe(a.red - b.red,
                                     a.green - b.green,
                                     a.blue - b.blue)
                }
            }
        }

        "Subtracting a color from another in place" {
            should("subtract each of its components.") {
                assertAll(Gen.color3(), Gen.color3()) { a: Color3, b: Color3 ->
                    a.clone().minusSelf(b).shouldBe(a.red - b.red,
                                                    a.green - b.green,
                                                    a.blue - b.blue)
                }
            }
        }

        "Adding a color to another" {
            should("add each color's components.") {
                assertAll(Gen.color3(), Gen.color3()) { a: Color3, b: Color3 ->
                    (a + b).shouldBe(a.red + b.red,
                                     a.green + b.green,
                                     a.blue + b.blue)
                }
            }
        }

        "Adding a color to another in place" {
            should("add each of its components.") {
                assertAll(Gen.color3(), Gen.color3()) { a: Color3, b: Color3 ->
                    a.clone().plusSelf(b).shouldBe(a.red + b.red,
                                                   a.green + b.green,
                                                   a.blue + b.blue)
                }
            }
        }

        "Raising a color to a power" {
            should("raise each component to that power.") {
                assertAll(Gen.color3(), Gen.numericFloats()) {
                    color: Color3, a: Float ->

                    color.pow(a).shouldBe(color.red.pow(a),
                                          color.green.pow(a),
                                          color.blue.pow(a))
                }
            }
        }

        "Raising a color to a power in place" {
            should("raise each of its components to that power.") {
                assertAll(Gen.color3(), Gen.numericFloats()) {
                    color: Color3, a: Float ->


                    color.clone().powSelf(a).shouldBe(color.red.pow(a),
                                                      color.green.pow(a),
                                                      color.blue.pow(a))
                }
            }
        }

        "Multiplying a color with a scalar" {
            should("multiply each component by that scalar.") {
                assertAll(Gen.color3(), Gen.numericFloats()) {
                    color: Color3, scalar: Float ->

                    (color * scalar).shouldBe(color.red * scalar,
                                              color.green * scalar,
                                              color.blue * scalar)
                }
            }
        }

        "Multiplying a color with a scalar in place" {
            should("multiply each of its components by that scalar.") {
                assertAll(Gen.color3(), Gen.numericFloats()) {
                    color: Color3, scalar: Float ->

                    color.clone().timesSelf(scalar).shouldBe(
                        color.red * scalar,
                        color.green * scalar,
                        color.blue * scalar)
                }
            }
        }

        "Multiplying a color with another" {
            should("multiply each of their components in turn.") {
                assertAll(Gen.color3(), Gen.color3()) { a: Color3, b: Color3 ->
                    a.times(b).shouldBe(
                        a.red * b.red,
                        a.green * b.green,
                        a.blue * b.blue
                    )
                }
            }
        }

        "Multiplying a color with another in place" {
            should("multiply each of their components in turn.") {
                assertAll(Gen.color3(), Gen.color3()) { a: Color3, b: Color3 ->
                    a.clone().timesSelf(b).shouldBe(
                        a.red * b.red,
                        a.green * b.green,
                        a.blue * b.blue
                    )
                }
            }
        }

        "Converting a color to a single packed integer" {
            should("match the same operation from AWT's Color class.") {
                assertAll(Gen.color3()) { color: Color3 ->
                    val redInt   = componentToInt(color.red)
                    val greenInt = componentToInt(color.green)
                    val blueInt  = componentToInt(color.blue)

                    color.rgb shouldBe Color(redInt, greenInt, blueInt).rgb
                }
            }
        }
    }
}