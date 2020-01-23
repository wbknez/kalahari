package com.willoutwest.kalahari.math.intersect

import com.willoutwest.kalahari.gen.smallFloats
import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.math.point3
import com.willoutwest.kalahari.math.vector3
import io.kotlintest.Matcher
import io.kotlintest.MatcherResult
import io.kotlintest.data.forall
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import io.kotlintest.tables.row
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class BoundingSphereGenerator : Gen<BoundingSphere> {

    override fun constants(): Iterable<BoundingSphere> = listOf(
        BoundingSphere()
    )

    override fun random(): Sequence<BoundingSphere> = generateSequence {
        BoundingSphere(Gen.numericFloats(0.5f, 1000f).random().first(),
                    Gen.point3().random().first())
    }
}

fun Gen.Companion.boundingSphere(): Gen<BoundingSphere> =
    BoundingSphereGenerator()

class InsideSphereGenerator(val sphere: BoundingSphere) : Gen<Point3> {

    override fun constants(): Iterable<Point3> = emptyList()

    override fun random(): Sequence<Point3> = generateSequence {
        val r  = Gen.numericFloats(0f, sphere.radius)
                     .filter { it < sphere.radius }
                     .random()
                     .first()
        val u  = Gen.smallFloats().random().first()
        val v  = Gen.smallFloats().random().first()

        val theta = u * MathUtils.TwoPi
        val phi   = acos(2f * v - 1f)

        val cosPhi   = cos(phi)
        val cosTheta = cos(theta)
        val sinPhi   = sin(phi)
        val sinTheta = sin(theta)

        val x = r * sinPhi * cosTheta
        val y = r * sinPhi * sinTheta
        val z = r * cosPhi

        Point3(sphere.center.x + x, sphere.center.y + y, sphere.center.z + z)
    }
}

fun Gen.Companion.insideSphere(sphere: BoundingSphere): Gen<Point3> =
    InsideSphereGenerator(sphere)

class OutsideSphereGenerator(val sphere: BoundingSphere,
                             val lowerBound: Float = -1000f,
                             val upperBound: Float = 1000f) : Gen<Point3> {

    override fun constants(): Iterable<Point3> = emptyList()

    override fun random(): Sequence<Point3> = generateSequence {
        val low  = this.sphere.min
        val high = this.sphere.max

        Point3(Gen.numericFloats(this.lowerBound, this.upperBound)
                   .filter { it < low.x || it > high.x }
                   .random()
                   .first(),
               Gen.numericFloats(this.lowerBound, this.upperBound)
                   .filter { it < low.y || it > high.y }
                   .random()
                   .first(),
               Gen.numericFloats(this.lowerBound, this.upperBound)
                   .filter { it < low.z || it > high.z }
                   .random()
                   .first())
    }
}

fun Gen.Companion.outsideSphere(sphere: BoundingSphere,
                                lowerBound: Float = -1000f,
                                upperBound: Float = 1000f): Gen<Point3> =
    OutsideSphereGenerator(sphere, lowerBound, upperBound)

private fun equalsTo(sphere: BoundingSphere) = object : Matcher<BoundingSphere> {

    override fun test(value: BoundingSphere): MatcherResult =
        MatcherResult(value == sphere,
                      "Bounding sphere $value should be $sphere",
                      "Bounding sphere $value should not be $sphere")
}

fun BoundingSphere.shouldBe(other: BoundingSphere) =
    this shouldBe equalsTo(other)

/**
 * Test suite for [BoundingSphere].
 */
class BoundingSphereTest : ShouldSpec() {
    
    init {

        "Checking if a bounding sphere contains a point" {
            should("succeed if the point is completely inside.") {
                assertAll(Gen.boundingSphere()) { sphere: BoundingSphere ->
                    val point = Gen.insideSphere(sphere).random().first()

                    sphere.contains(point).shouldBeTrue()
                }
            }

            should("fail if the point is completely outside.") {
                assertAll(Gen.boundingSphere()) { sphere: BoundingSphere ->
                    val point = Gen.outsideSphere(sphere).random().first()

                    sphere.contains(point).shouldBeFalse()
                }
            }

            should("fail if the point is on the boundary.") {
                forall(row(Point3.X),
                       row(-Point3.X),
                       row(Point3.Y),
                       row(-Point3.Y),
                       row(Point3.Z),
                       row(-Point3.Z)) { point: Point3 ->
                    BoundingSphere(1f, Point3.Zero).contains(point)
                        .shouldBeFalse()
                }
            }
        }

        "Checking if a bounding sphere intersects a bounding box" {
            should("fail if the distance between each volume is too great, " +
                   "otherwise succeed") {
                assertAll(Gen.boundingSphere(), Gen.boundingBox()) {
                    a: BoundingSphere, b: BoundingBox ->

                    val dist = (a.center.distanceTo(a.max) +
                                b.center.distanceTo(b.max))

                    when(a.center.distanceTo(b.center) <= dist) {
                        false -> a.intersects(b).shouldBeFalse()
                        true  -> a.intersects(b).shouldBeTrue()
                    }
                }
            }

            should("succeed if the intersection surface is on an axis.") {
                forall(
                    row(BoundingBox(1f, 1f, 1f, Point3(2f, 0f, 0f))),
                    row(BoundingBox(1f, 1f, 1f, Point3(-2f, 0f, 0f))),
                    row(BoundingBox(1f, 1f, 1f, Point3(0f, 2f, 0f))),
                    row(BoundingBox(1f, 1f, 1f, Point3(0f, -2f, 0f))),
                    row(BoundingBox(1f, 1f, 1f, Point3(0f, 0f, 2f))),
                    row(BoundingBox(1f, 1f, 1f, Point3(0f, 0f, -2f)))
                ) { box: BoundingBox ->

                    BoundingSphere(1f, Point3.Zero).intersects(box)
                        .shouldBeTrue()
                }
            }

            should("fail if the intersection surface is on a diagonal.") {
                forall(
                    row(BoundingBox(1f, 1f, 1f, Point3(2f, 2f, 2f))),
                    row(BoundingBox(1f, 1f, 1f, Point3(2f, 2f, -2f))),
                    row(BoundingBox(1f, 1f, 1f, Point3(2f, -2f, 2f))),
                    row(BoundingBox(1f, 1f, 1f, Point3(2f, -2f, -2f))),
                    row(BoundingBox(1f, 1f, 1f, Point3(-2f, 2f, 2f))),
                    row(BoundingBox(1f, 1f, 1f, Point3(-2f, 2f, -2f))),
                    row(BoundingBox(1f, 1f, 1f, Point3(-2f, -2f, 2f))),
                    row(BoundingBox(1f, 1f, 1f, Point3(-2f, -2f, -2f)))
                ) { box: BoundingBox ->

                    BoundingSphere(1f, Point3.Zero).intersects(box)
                        .shouldBeFalse()
                }
            }
        }

        "Checking if a bounding sphere intersects a ray" {
            should("succeed if the ray's origin is inside.") {
                assertAll(Gen.boundingSphere(), Gen.vector3()) {
                    sphere: BoundingSphere, dir: Vector3 ->

                    val ray = Ray3(dir,
                                   Gen.insideSphere(sphere).random().first())
                                .normalizeSelf()

                    sphere.intersects(ray).shouldBeTrue()
                }
            }

            should("succeed if the ray's origin is outside the sphere but " +
                   "points towards it") {
                assertAll(Gen.boundingSphere()) {sphere: BoundingSphere ->
                    val origin = Gen.outsideSphere(sphere).random().first()
                    val ray    = Ray3(Vector3(sphere.center - origin), origin)
                                    .normalizeSelf()

                    sphere.intersects(ray).shouldBeTrue()

                }
            }

            should("fail if the ray's origin is outside the sphere and points " +
                   "away") {
                assertAll(Gen.boundingSphere()) { sphere: BoundingSphere ->
                    val origin = Gen.outsideSphere(sphere).random().first()
                    val ray    = Ray3(Vector3(origin - sphere.center), origin)
                                    .normalizeSelf()

                    sphere.intersects(ray).shouldBeFalse()
                }
            }
        }
        
        "Checking if a bounding sphere intersects another sphere" {
            should("fail if the distance between each sphere is too great, " +
                   "otherwise succeed") {
                assertAll(Gen.boundingSphere(), Gen.boundingSphere()) {
                    a: BoundingSphere, b: BoundingSphere ->

                    val dist = (a.radius.pow(2) + b.radius.pow(2))

                    when(a.center.distanceTo(b.center) <= dist) {
                        false -> a.intersects(b).shouldBeFalse()
                        true  -> a.intersects(b).shouldBeTrue()
                    }
                }
            }

            should("succeed if the intersection surface is a point.") {
                forall(
                    row(BoundingSphere(1f, Point3(2f, 0f, 0f))),
                    row(BoundingSphere(1f, Point3(-2f, 0f, 0f))),
                    row(BoundingSphere(1f, Point3(0f, 2f, 0f))),
                    row(BoundingSphere(1f, Point3(0f, -2f, 0f))),
                    row(BoundingSphere(1f, Point3(0f, 0f, 2f))),
                    row(BoundingSphere(1f, Point3(0f, 0f, -2f)))
                ) { sphere: BoundingSphere ->

                    sphere.intersects(BoundingSphere(1f, Point3.Zero))
                        .shouldBeTrue()
                }
            }
        }
    }
}
