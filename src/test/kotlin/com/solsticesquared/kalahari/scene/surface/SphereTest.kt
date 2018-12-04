package com.solsticesquared.kalahari.scene.surface

import com.solsticesquared.kalahari.math.EpsilonTable
import com.solsticesquared.kalahari.math.Point3
import com.solsticesquared.kalahari.math.Ray3
import com.solsticesquared.kalahari.math.Vector3
import com.solsticesquared.kalahari.math.Vector3Generator
import com.solsticesquared.kalahari.math.intersect.Intersection
import com.solsticesquared.kalahari.util.container.FloatContainer
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

/**
 * Represents an implementation of [Gen] that creates [Sphere] objects with
 * random components.
 */
class SphereGenerator : Gen<Sphere> {

    override fun generate(): Sphere =
        Sphere(Gen.choose(1, 1000).generate().toFloat(),
               Point3(Gen.choose(1, 1000).generate().toFloat(),
                      Gen.choose(1, 1000).generate().toFloat(),
                      Gen.choose(1, 1000).generate().toFloat()))
}

/**
 * Test suite for [Sphere].
 */
class SphereTest : ShouldSpec() {

    companion object {

        /**
         * The utility to create randomized [Sphere] instances.
         */
        val SphGen = SphereGenerator()

        /**
         * The utility to create randomized [Vector3] instances.
         */
        val Vec3Gen = Vector3Generator()
    }

    init {

        "Hit rays that start inside a sphere" {
            val hEps   = EpsilonTable(0.001f,
                                      mutableMapOf(Sphere.ID to Sphere.hEps))
            val record = Intersection()
            val tMin   = FloatContainer(0f)

            should("always intersect if the direction is negative.") {
                forAll(SphGen, Vec3Gen) {sphere: Sphere, vec: Vector3 ->
                    val ray = Ray3(-vec, sphere.center)
                    sphere.intersect(ray, tMin, record, hEps)
                }
            }

            should("always intersect if the direction is positive.") {
                forAll(SphGen, Vec3Gen) {sphere: Sphere, vec: Vector3 ->
                    val ray = Ray3(vec, sphere.center)
                    sphere.intersect(ray, tMin, record, hEps)
                }
            }
        }

        "Hit rays that start outside a sphere" {
            val hEps   = EpsilonTable(0.001f,
                                      mutableMapOf(Sphere.ID to Sphere.hEps))
            val record = Intersection()
            val tMin   = FloatContainer(0f)

            should("always intersect if the direction is towards the sphere.") {
                forAll(SphGen) {sphere: Sphere ->
                    val offSet = sphere.radius + 1f
                    val origin = sphere.center + Point3(offSet, offSet, offSet)
                    val ray = Ray3(Vector3(sphere.center - origin), origin)

                    sphere.intersect(ray, tMin, record, hEps)
                }
            }

            should("never intersect if the direction is away from the " +
                   "sphere.") {
                forAll(SphGen) {sphere: Sphere ->
                    val offSet = sphere.radius + 1f
                    val origin = sphere.center + Point3(offSet, offSet, offSet)
                    val ray = Ray3(Vector3(origin - sphere.center), origin)

                    !sphere.intersect(ray, tMin, record, hEps)
                }
            }
        }

        "Hit rays that start on a sphere" {
            val hEps   = EpsilonTable(0.001f,
                                      mutableMapOf(Sphere.ID to Sphere.hEps))
            val record = Intersection()
            val tMin   = FloatContainer(0f)

            should("never intersect if the direction is towards the sphere.") {
                forAll(SphGen) {sphere: Sphere ->
                    val origin = sphere.center + Point3(sphere.radius,
                                                        sphere.radius,
                                                        sphere.radius)
                    val ray = Ray3(Vector3(origin - sphere.center), origin)

                    !sphere.intersect(ray, tMin, record, hEps)
                }
            }

            should("always intersect if the direction is away from the " +
                   "sphere (far side).") {
                forAll(SphGen) {sphere: Sphere ->
                    val origin = sphere.center + Point3(sphere.radius,
                                                        sphere.radius,
                                                        sphere.radius)
                    val ray = Ray3(Vector3(sphere.center - origin).normalize(),
                                   origin)

                    sphere.intersect(ray, tMin, record, hEps)
                }
            }
        }
    }
}
