package com.solsticesquared.kalahari.scene.surface

import com.solsticesquared.kalahari.math.EpsilonTable
import com.solsticesquared.kalahari.math.Normal3
import com.solsticesquared.kalahari.math.Point3
import com.solsticesquared.kalahari.math.Ray3
import com.solsticesquared.kalahari.math.intersect.Intersection
import com.solsticesquared.kalahari.util.container.FloatContainer
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

/**
 * Represents an implementation of [Gen] that creates [Plane] objects with
 * random components.
 */
class PlaneGenerator : Gen<Plane> {

    override fun generate(): Plane =
        Plane(Normal3(Gen.float().generate(), Gen.float().generate(),
                      Gen.float().generate()),
              Point3(Gen.float().generate(), Gen.float().generate(),
                     Gen.float().generate()))
}

/**
 * Test suite for [Plane].
 */
class PlaneTest : ShouldSpec() {

    companion object {

        /**
         * The utility to create randomized [Plane] instances.
         */
        val PlaGen = PlaneGenerator()
    }

    init {

        "Hit rays that start on the positive side of a plane" {
            val hEps   = EpsilonTable(0.001f,
                                      mutableMapOf(Plane.ID to Plane.hEps))
            val record = Intersection()
            val tMin   = FloatContainer(0f)

            should("always intersect if the ray points towards the plane.") {
                forAll(PlaGen) {plane: Plane ->
                    val ray = Ray3(-plane.normal,
                                   Point3(plane.normal) * 2f + plane.point)
                    plane.intersect(ray, tMin, record, hEps)
                }
            }

            should("never intersect if the ray points away from the plane.") {
                forAll(PlaGen) {plane: Plane ->
                    val ray = Ray3(plane.normal,
                                   Point3(plane.normal) * 2f + plane.point)
                    !plane.intersect(ray, tMin, record, hEps)
                }
            }
        }

        "Hit rays that start on the negative side of a plane" {
            val hEps   = EpsilonTable(0.001f,
                                      mutableMapOf(Plane.ID to Plane.hEps))
            val record = Intersection()
            val tMin   = FloatContainer(0f)

            should("always intersect if the ray points towards the plane.") {
                forAll(PlaGen) {plane: Plane ->
                    val ray = Ray3(plane.normal,
                                   Point3(-plane.normal) * 2f + plane.point)
                    plane.intersect(ray, tMin, record, hEps)
                }
            }

            should("never intersect if the ray points away from the plane.") {
                forAll(PlaGen) {plane: Plane ->
                    val ray = Ray3(-plane.normal,
                                   Point3(-plane.normal) * 2f + plane.point)
                    !plane.intersect(ray, tMin, record, hEps)
                }
            }
        }

        "Hit rays that start on the plane" {
            val hEps   = EpsilonTable(0.001f,
                                      mutableMapOf(Plane.ID to Plane.hEps))
            val record = Intersection()
            val tMin   = FloatContainer(0f)

            should("never intersect if the ray points towards the plane.") {
                forAll(PlaGen) {plane: Plane ->
                    val ray = Ray3(plane.normal, plane.point)
                    !plane.intersect(ray, tMin, record, hEps)
                }
            }

            should("never intersect if the ray points away from the plane.") {
                forAll(PlaGen) {plane: Plane ->
                    val ray = Ray3(-plane.normal, plane.point)
                    !plane.intersect(ray, tMin, record, hEps)
                }
            }
        }
    }
}
