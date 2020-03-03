package com.willoutwest.kalahari.scene.camera

import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.math.point3
import com.willoutwest.kalahari.math.vector3
import io.kotlintest.Matcher
import io.kotlintest.MatcherResult
import io.kotlintest.shouldBe
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.specs.ShouldSpec

class UvwGenerator : Gen<Uvw> {

    override fun constants(): Iterable<Uvw> = listOf(Uvw())

    override fun random(): Sequence<Uvw> = generateSequence {
        Uvw(Gen.vector3().random().first(),
            Gen.vector3().random().first(),
            Gen.vector3().random().first())
    }
}

fun Gen.Companion.uvw(): Gen<Uvw> = UvwGenerator()

private fun equalsTo(uvw: Uvw) = object : Matcher<Uvw> {

    override fun test(value: Uvw): MatcherResult =
        MatcherResult(value == uvw,
                      "Orthonormal basis $value should be $uvw",
                      "Orthonormal basis $value should not be $uvw")
}

fun Uvw.shouldBe(other: Uvw) = this shouldBe equalsTo(other)

/**
 * Test suite for [Uvw].
 */
class UvwTest : ShouldSpec() {

    init {

        "Updating an orthonormal basis" {
            should("be zero when the basis is zero regardless of up") {
                assertAll(Gen.vector3()) { up: Vector3 ->
                    Uvw().updateBasis(Point3.Zero, Point3.Zero, up).shouldBe(
                        Uvw(Vector3.Zero, Vector3.Zero, Vector3.Zero)
                    )
                }
            }

            should("update each component as necessary") {
                assertAll(Gen.point3(), Gen.point3(), Gen.vector3()) {
                    eye: Point3, lookAt: Point3, up: Vector3 ->

                    if(eye.x == lookAt.x && eye.y != lookAt.y &&
                       eye.z == lookAt.z) {
                        eye.y = lookAt.y
                    }

                    val w = Vector3(eye)
                        .minusSelf(lookAt.x, lookAt.y, lookAt.z)
                        .normalizeSelf()
                    val u = Vector3(up)
                        .crossSelf(w)
                        .normalizeSelf()
                    val v = Vector3(w)
                        .crossSelf(u)

                    Uvw().updateBasis(eye, lookAt, up).shouldBe(Uvw(u, v, w))
                }
            }
        }

        "Removing the singularity" {
            should("occur when looking up the y-axis") {
                assertAll(Gen.uvw()) { uvw: Uvw ->
                    val eye    = Point3(35f, 93f, 55f)
                    val lookAt = Point3(35f, 42f, 55f)

                    uvw.removeSingularity(eye, lookAt).shouldBe(Uvw(
                        Vector3.Z, Vector3.X, Vector3.Y
                    ))
                }
            }

            should("occur when looking down the y-axis") {
                assertAll(Gen.uvw()) { uvw: Uvw ->
                    val eye    = Point3(35f, 12f, 55f)
                    val lookAt = Point3(35f, 142f, 55f)

                    uvw.removeSingularity(eye, lookAt).shouldBe(Uvw(
                        Vector3.X, Vector3.Z, -Vector3.Y
                    ))
                }
            }
        }
    }
}