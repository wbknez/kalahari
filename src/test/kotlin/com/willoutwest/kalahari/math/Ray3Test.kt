package com.willoutwest.kalahari.math

import io.kotlintest.matchers.types.shouldBeSameInstanceAs
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec

class Ray3Generator : Gen<Ray3> {

    override fun constants(): Iterable<Ray3> = emptyList()

    override fun random(): Sequence<Ray3> = generateSequence {
        Ray3(Gen.vector3().random().first(),
             Gen.point3().random().first())
    }
}

fun Gen.Companion.ray3(): Gen<Ray3> = Ray3Generator()

/**
 * Test suite for [Ray3].
 */
class Ray3Test : ShouldSpec() {

    init {

        "Normalizing a ray" {
            should("normalize both the direction and its inverse") {
                assertAll(Gen.ray3()) { ray: Ray3 ->
                    val norm = ray.normalize()

                    norm.dir.shouldBe(ray.dir.normalize())
                    norm.invDir.shouldBe(ray.invDir.normalize())
                    norm.origin.shouldBe(ray.origin)
                }
            }
        }

        "Normalizing a ray in place" {
            should("normalize both its direction and inverse") {
                assertAll(Gen.ray3()) { ray: Ray3 ->
                    val norm = ray.clone().normalizeSelf()

                    norm.dir.shouldBe(ray.dir.normalize(), 0.000001f)
                    norm.invDir.shouldBe(ray.invDir.normalize(), 0.000001f)
                    norm.origin.shouldBe(ray.origin)
                }
            }
        }

        "Projecting a point along a ray" {
            should("be the sum of the origin and product of the direction " +
                   "and time") {
                assertAll(Gen.ray3(), Gen.numericFloats()) {
                    ray: Ray3, t: Float ->

                    val dir = Point3(ray.dir)

                    ray.projectAlong(t).shouldBe(ray.origin + dir * t)
                }
            }
        }

        "Projecting a point along a ray into another point" {
            should("be the sum of the origin and product of the direction " +
                   "and time") {
                assertAll(Gen.ray3(), Gen.numericFloats()) {
                    ray: Ray3, t: Float ->

                    val dir   = Point3(ray.dir)
                    val store = Point3()

                    ray.projectAlong(t, store)
                        .shouldBe(ray.origin + dir * t)
                    ray.projectAlong(t, store)
                        .shouldBeSameInstanceAs(store)
                }
            }
        }
    }
}