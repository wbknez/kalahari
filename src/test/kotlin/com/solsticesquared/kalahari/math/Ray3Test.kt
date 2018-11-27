package com.solsticesquared.kalahari.math

import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

/**
 * Represents an implementation of [Gen] that creates [Ray3] objects with
 * random components.
 */
class Ray3Generator : Gen<Ray3> {

    override fun generate(): Ray3 =
        Ray3(Vector3(Gen.float().generate(), Gen.float().generate(),
                     Gen.float().generate()),
             Point3(Gen.float().generate(), Gen.float().generate(),
                    Gen.float().generate()))
}

/**
 * Test suite for [Ray3].
 */
class Ray3Test : ShouldSpec() {

    companion object {

        /**
         * The utility to create randomized [Ray3] instances.
         */
        val Ray3Gen = Ray3Generator()
    }

    init {

        "Projecting a point along a ray" {
            should("be the origin plus direction times time.") {
                forAll(Ray3Gen, Gen.float()) { ray: Ray3, t: Float ->
                    val expected = Point3(
                        ray.origin.x + t * ray.dir.x,
                        ray.origin.y + t * ray.dir.y,
                        ray.origin.z + t * ray.dir.z
                                         )
                    ray.projectAlong(t) == expected
                }
            }
        }

        "Projecting a point along a ray and storing it" {
            should("be the origin plus direction times time.") {
                forAll(Ray3Gen, Gen.float()) { ray: Ray3, t: Float ->
                    val expected = Point3(
                        ray.origin.x + t * ray.dir.x,
                        ray.origin.y + t * ray.dir.y,
                        ray.origin.z + t * ray.dir.z
                                         )
                    val store = Point3()
                    ray.projectAlong(t, store) == expected
                }
            }
        }
    }
}
