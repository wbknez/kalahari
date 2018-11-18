package com.solsticesquared.kalahari.math.color

import com.solsticesquared.kalahari.math.MathUtils
import io.kotlintest.matchers.shouldBe
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec
import java.awt.Color

/**
 * Represents an implementation of [Gen] that creates [Color3] objects with
 * random components.
 */
class Color3Generator : Gen<Color3> {

    override fun generate(): Color3 =
        Color3(Gen.float().generate(), Gen.float().generate(),
               Gen.float().generate())
}

/**
 * Test suite for [Color3].
 */
class Color3Test : ShouldSpec() {

    companion object {

        /**
         * The utility to create randomized [Color3] instances.
         */
        val Col3Gen = Color3Generator()
    }

    init {

        "Converting a float component to an integer" {
            should("transform 1f to 255.") {
                componentToInt(1f) shouldBe 255
            }

            should("transform 0f to 0.") {
                forAll(Gen.int()) { _: Int ->
                    componentToInt(0f) == 0
                }
            }

            should("transform all intermediate values correctly.") {
                forAll(Gen.float()) { comp: Float ->
                    val expected = when(comp != 1f) {
                        false -> 255
                        true  -> (comp * 256f).toInt()
                    }

                    componentToInt(comp) == expected
                }
            }
        }

        "Converting an integer value to a float component" {
            should("transform 255 to 1f.") {
                intToComponent(255) shouldBe 1f
            }

            should("transform 0 to 0f.") {
                intToComponent(0) shouldBe 0f
            }

            should("transform all intermediate values correctly.") {
                forAll(Gen.float()) { f: Float ->
                    val value    = componentToInt(f)
                    val expected = when(value != 255) {
                        false -> 1f
                        true  -> value / 256f
                    }

                    intToComponent(value) == expected
                }
            }
        }

        "Dividing a color by a scalar" {
            should("inversely scale each component by that scalar.") {
                forAll(Gen.float(), Col3Gen) { s: Float, color: Color3 ->
                    val ss = when(s == 0f) { false -> s; true -> s + 0.001f }
                    val ds = 1f / ss

                    val expected = Color3(color.red * ds, color.green * ds,
                            color.blue * ds)
                    (color / ss) == expected
                }
            }
        }

        "A color dividing itself by a scalar" {
            should("inversely scale each of its components.") {
                forAll(Gen.float(), Col3Gen) { s: Float, color: Color3 ->
                    val ss = when(s == 0f) { false -> s; true -> s + 0.001f }
                    val ds = 1f / ss

                    val expected = Color3(color.red * ds, color.green * ds,
                            color.blue * ds)
                    color.divSelf(ss) == expected
                }
            }
        }

        "Linearly interpolating from a start color (A) to an end (B) at (t)" {
            should("be A when t is zero.") {
                forAll(Col3Gen, Col3Gen) { a: Color3, b: Color3 ->
                    a.lerp(b, 0f) == a
                }
            }

            should("be B when t is one.") {
                forAll(Col3Gen, Col3Gen) { a: Color3, b: Color3 ->
                    a.lerp(b, 1f) == b
                }
            }

            should("be somewhere in between when t is in (0, 1).") {
                forAll(Col3Gen, Col3Gen, Gen.float()) {
                    a: Color3, b: Color3, t: Float ->

                    val expected = Color3(
                            a.red + (b.red - a.red) * t,
                            a.green + (b.green - a.green) * t,
                            a.blue + (b.blue - a.blue) * t
                    )
                    a.lerp(b, t) == expected
                }
            }
        }

        "A color linearly interpolating itself (A) to an end (B) at (t)" {
            should("be A when t is zero.") {
                forAll(Col3Gen, Col3Gen) { a: Color3, b: Color3 ->
                    a.lerpSelf(b, 0f) == a
                }
            }

            should("be B when t is one.") {
                forAll(Col3Gen, Col3Gen) { a: Color3, b: Color3 ->
                    a.lerpSelf(b, 1f) == b
                }
            }

            should("be somewhere in between when t is in (0, 1).") {
                forAll(Col3Gen, Col3Gen, Gen.float()) { a: Color3, b: Color3, t: Float ->

                    val expected = Color3(
                            a.red + (b.red - a.red) * t,
                            a.green + (b.green - a.green) * t,
                            a.blue + (b.blue - a.blue) * t
                    )
                    a.lerpSelf(b, t) == expected
                }
            }
        }

        "Subtracting one color from another" {
            should("subtract each color's components.") {
                forAll(Col3Gen, Col3Gen) { a: Color3, b: Color3 ->
                    val expected = Color3(
                            a.red - b.red,
                            a.green - b.green,
                            a.blue - b.blue
                    )
                    (a - b) == expected
                }
            }
        }

        "A color subtracting another color from itself" {
            should("subtract each of its components.") {
                forAll(Col3Gen, Col3Gen) { a: Color3, b: Color3 ->
                    val expected = Color3(
                            a.red - b.red,
                            a.green - b.green,
                            a.blue - b.blue
                    )
                    a.minusSelf(b) == expected
                }
            }
        }

        "Adding one color from another" {
            should("add each color's components.") {
                forAll(Col3Gen, Col3Gen) { a: Color3, b: Color3 ->
                    val expected = Color3(
                            a.red + b.red,
                            a.green + b.green,
                            a.blue + b.blue
                    )
                    (a + b) == expected
                }
            }
        }

        "A color adding another color from itself" {
            should("add each of its components.") {
                forAll(Col3Gen, Col3Gen) { a: Color3, b: Color3 ->
                    val expected = Color3(
                            a.red + b.red,
                            a.green + b.green,
                            a.blue + b.blue
                    )
                    a.plusSelf(b) == expected
                }
            }
        }

        "Raising a color to a power" {
            should("raise each component to that power.") {
                forAll(Gen.float(), Col3Gen) { exp: Float, color: Color3 ->
                    val expected = Color3(
                            MathUtils.pow(color.red, exp),
                            MathUtils.pow(color.green, exp),
                            MathUtils.pow(color.blue, exp)
                    )
                    color.pow(exp) == expected
                }
            }
        }

        "A color raising itself to a power" {
            should("raise each of its component to that power.") {
                forAll(Gen.float(), Col3Gen) { exp: Float, color: Color3 ->
                    val expected = Color3(
                            MathUtils.pow(color.red, exp),
                            MathUtils.pow(color.green, exp),
                            MathUtils.pow(color.blue, exp)
                    )
                    color.powSelf(exp) == expected
                }
            }
        }

        "Multiplying a color by a scalar" {
            should("multiply each component by that scalar.") {
                forAll(Gen.float(), Col3Gen) { s: Float, color: Color3 ->
                    val expected = Color3(
                            color.red * s, color.green * s, color.blue * s
                    )
                    (color * s) == expected
                }
            }
        }

        "A color multiplying itself by a scalar" {
            should("multiply each of its components by that scalar.") {
                forAll(Gen.float(), Col3Gen) { s: Float, color: Color3 ->
                    val expected = Color3(
                            color.red * s, color.green * s, color.blue * s
                    )
                    color.timesSelf(s) == expected
                }
            }
        }

        "Multiplying a color by another color" {
            should("multiply each component by the other.") {
                forAll(Col3Gen, Col3Gen) { a: Color3, b: Color3 ->
                    val expected = Color3(
                            a.red * b.red, a.green * b.green, a.blue * b.blue
                    )
                    (a * b) == expected
                }
            }
        }

        "A color multiplying itself by another" {
            should("multiply each of its components by the others as well.") {
                forAll(Col3Gen, Col3Gen) { a: Color3, b: Color3 ->
                    val expected = Color3(
                            a.red * b.red, a.green * b.green, a.blue * b.blue
                    )
                    a.timesSelf(b) == expected
                }
            }
        }

        "Converting a color to a single packed integer" {
            should("match the same operation from AWT's Color class.") {
                forAll(Col3Gen) { color: Color3 ->
                    val redInt   = componentToInt(color.red)
                    val greenInt = componentToInt(color.green)
                    val blueInt  = componentToInt(color.blue)

                    val redMax   = Math.min(redInt + 1, 255)
                    val greenMax = Math.min(greenInt + 1, 255)
                    val blueMax  = Math.min(blueInt + 1, 255)

                    val redMin   = Math.max(redInt - 1, 0)
                    val greenMin = Math.max(greenInt - 1, 0)
                    val blueMin  = Math.max(blueInt - 1, 0)

                    val awtMax = Color(redMax, greenMax, blueMax).rgb
                    val awtMin = Color(redMin, greenMin, blueMin).rgb

                    color.rgb in awtMin..awtMax
                }
            }
        }
    }
}
