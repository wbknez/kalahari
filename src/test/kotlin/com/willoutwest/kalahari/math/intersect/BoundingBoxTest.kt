package com.willoutwest.kalahari.math.intersect

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
import io.kotlintest.shouldBe
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.specs.ShouldSpec
import io.kotlintest.tables.row

class BoundingBoxGenerator : Gen<BoundingBox> {

    override fun constants(): Iterable<BoundingBox> = listOf(
        BoundingBox()
    )

    override fun random(): Sequence<BoundingBox> = generateSequence {
        BoundingBox(Gen.numericFloats(0.5f, 1000f).random().first(),
                    Gen.numericFloats(0.5f, 1000f).random().first(),
                    Gen.numericFloats(0.5f, 1000f).random().first(),
                    Gen.point3().random().first())
    }
}

fun Gen.Companion.boundingBox(): Gen<BoundingBox> = BoundingBoxGenerator()

class InsideBoxGenerator(val box: BoundingBox) : Gen<Point3> {

    override fun constants(): Iterable<Point3> = emptyList()

    override fun random(): Sequence<Point3> = generateSequence {
        val low  = this.box.min
        val high = this.box.max

        Point3(Gen.numericFloats(low.x, high.x)
                   .filter { it > low.x && it < high.x }
                   .random()
                   .first(),
               Gen.numericFloats(low.y, high.y)
                   .filter { it > low.y && it < high.y }
                   .random()
                   .first(),
               Gen.numericFloats(low.z, high.z)
                   .filter { it > low.z && it < high.z }
                   .random()
                   .first())
    }
}

fun Gen.Companion.insideBox(box: BoundingBox): Gen<Point3> =
    InsideBoxGenerator(box)

class OutsideBoxGenerator(val box: BoundingBox,
                          val lowerBound: Float = -1000f,
                          val upperBound: Float = 1000f) : Gen<Point3> {

    override fun constants(): Iterable<Point3> = emptyList()

    override fun random(): Sequence<Point3> = generateSequence {
        val low  = this.box.min
        val high = this.box.max

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


fun Gen.Companion.outsideBox(box: BoundingBox,
                             lowerBound: Float = -1000f,
                             upperBound: Float = 1000f): Gen<Point3> =
    OutsideBoxGenerator(box, lowerBound, upperBound)

private fun equalsTo(box: BoundingBox) = object : Matcher<BoundingBox> {

    override fun test(value: BoundingBox): MatcherResult =
        MatcherResult(value == box,
                      "Bounding box $value should be $box",
                      "Bounding box $value should not be $box")
}

fun BoundingBox.shouldBe(other: BoundingBox) =
    this shouldBe equalsTo(other)

/**
 * Test suite for [BoundingBox].
 */
class BoundingBoxTest : ShouldSpec() {

    init {

        "Checking if a bounding box contains a point" {
            should("succeed if the point is completely inside.") {
                assertAll(Gen.boundingBox()) { box: BoundingBox ->
                    val point = Gen.insideBox(box).random().first()

                    box.contains(point).shouldBeTrue()
                }
            }

            should("fail if the point is completely outside.") {
                assertAll(Gen.boundingBox()) { box: BoundingBox ->
                    val point = Gen.outsideBox(box).random().first()

                    box.contains(point).shouldBeFalse()
                }
            }

            should("fail if the point is on the boundary.") {
                assertAll(Gen.boundingBox()) { box: BoundingBox ->
                    box.contains(box.max).shouldBeFalse()
                    box.contains(box.min).shouldBeFalse()
                }
            }
        }

        "Checking if a bounding box intersects another box" {
            should("fail if the distance between each box is too great, " +
                   "otherwise succeed") {
                assertAll(Gen.boundingBox(), Gen.boundingBox()) {
                    a: BoundingBox, b: BoundingBox ->

                    val dist = (a.center.distanceTo(a.max) +
                                b.center.distanceTo(b.max))

                    when(a.center.distanceTo(b.center) <= dist) {
                        false -> a.intersects(b).shouldBeFalse()
                        true  -> a.intersects(b).shouldBeTrue()
                    }
                }
            }

            should("succeed if the intersection surface is a plane.") {
                forall(
                    row(BoundingBox(1f, 1f, 1f, Point3(2f, 0f, 0f))),
                    row(BoundingBox(1f, 1f, 1f, Point3(-2f, 0f, 0f))),
                    row(BoundingBox(1f, 1f, 1f, Point3(0f, 2f, 0f))),
                    row(BoundingBox(1f, 1f, 1f, Point3(0f, -2f, 0f))),
                    row(BoundingBox(1f, 1f, 1f, Point3(0f, 0f, 2f))),
                    row(BoundingBox(1f, 1f, 1f, Point3(0f, 0f, -2f)))
                ) { box: BoundingBox ->

                    box.intersects(BoundingBox(1f, 1f, 1f, Point3.Zero))
                        .shouldBeTrue()
                }
            }

            should("succeed if the intersection surface is a point.") {
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

                    box.intersects(BoundingBox(1f, 1f, 1f, Point3.Zero))
                        .shouldBeTrue()
                }
            }
        }

        "Checking if a bounding box intersects a ray" {
            should("succeed if the ray's origin is inside.") {
                assertAll(Gen.boundingBox(), Gen.vector3()) {
                    box: BoundingBox, dir: Vector3 ->

                    val ray = Ray3(dir, Gen.insideBox(box).random().first())
                                    .normalizeSelf()

                    box.intersects(ray).shouldBeTrue()
                }
            }

            should("succeed if the ray's origin is outside the box but " +
                       "points towards it") {
                assertAll(Gen.boundingBox()) {box: BoundingBox ->
                    val origin = Gen.outsideBox(box).random().first()
                    val ray    = Ray3(Vector3(box.center - origin), origin)
                                    .normalizeSelf()

                    box.intersects(ray).shouldBeTrue()

                }
            }

            should("fail if the ray's origin is outside the box and points " +
                   "away") {
                assertAll(Gen.boundingBox()) { box: BoundingBox ->
                    val origin = Gen.outsideBox(box).random().first()
                    val ray    = Ray3(Vector3(origin - box.center), origin)
                                    .normalizeSelf()

                    box.intersects(ray).shouldBeFalse()
                }
            }
        }

        "Checking if a bounding box intersects a bounding sphere" {
            should("fail if the distance between each volume is too great, " +
                   "otherwise succeed") {
                assertAll(Gen.boundingBox(), Gen.boundingSphere()) {
                    a: BoundingBox, b: BoundingSphere ->

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

                    box.intersects(BoundingSphere(1f, Point3.Zero))
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

                    box.intersects(BoundingSphere(1f, Point3.Zero))
                        .shouldBeFalse()
                }
            }
        }
    }
}