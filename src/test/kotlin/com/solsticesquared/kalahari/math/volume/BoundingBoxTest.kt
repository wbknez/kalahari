package com.solsticesquared.kalahari.math.volume

import com.solsticesquared.kalahari.math.Point3
import com.solsticesquared.kalahari.math.Ray3
import com.solsticesquared.kalahari.math.Vector3
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.ShouldSpec

/**
 * Test suite for [BoundingBox].
 */
class BoundingBoxTest : ShouldSpec() {

    init {

        "A bounding box containing a point" {
            should("be true when that point is completely inside the box.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val point = Point3(0.5f, 0.5f, -0.5f)
                box.contains(point) shouldBe true
            }

            should("be false when that point is completely outside the box.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val point = Point3(1.5f, 1.5f, -1.5f)
                box.contains(point) shouldBe false
            }

            should("be false when that point is on the boundary.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val point = Point3(1f, 1f, 1f)
                box.contains(point) shouldBe false
            }
        }

        "A bounding box intersecting another box" {
            should("be true when one box contains another.") {
                val other = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val box   = BoundingBox(0.5f, 0.5f, 0.5f, Point3.Zero)
                box.intersects(other) shouldBe true
            }

            should("be true when one box contains another and is also offset" +
                   ".") {
                val other = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val box   = BoundingBox(0.5f, 0.5f, 0.5f,
                                        Point3(0.3f, 0.1f, 0.2f))
                box.intersects(other) shouldBe true
            }

            should("be false when one box is too far away.") {
                val other = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val box   = BoundingBox(1.0f, 1.0f, 1.0f,
                                        Point3(3.5f, 2.5f, 2.75f))
                box.intersects(other) shouldBe false
            }

            should("be true when on the positive x-axis boundary.") {
                val other = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val box   = BoundingBox(1f, 1f, 1f,
                                        Point3(2f, 0f, 0f))
                box.intersects(other) shouldBe true
            }

            should("be true when on the negative x-axis boundary.") {
                val other = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val box   = BoundingBox(1f, 1f, 1f,
                                        Point3(-2f, 0f, 0f))
                box.intersects(other) shouldBe true
            }

            should("be true when on the positive y-axis boundary.") {
                val other = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val box   = BoundingBox(1f, 1f, 1f,
                                        Point3(0f, 2f, 0f))
                box.intersects(other) shouldBe true
            }

            should("be true when on the negative y-axis boundary.") {
                val other = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val box   = BoundingBox(1f, 1f, 1f,
                                        Point3(0f, -2f, 0f))
                box.intersects(other) shouldBe true
            }

            should("be true when on the positive z-axis boundary.") {
                val other = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val box   = BoundingBox(1f, 1f, 1f,
                                        Point3(0f, 0f, 2f))
                box.intersects(other) shouldBe true
            }

            should("be true when on the negative z-axis boundary.") {
                val other = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val box   = BoundingBox(1f, 1f, 1f,
                                        Point3(0f, 0f, -2f))
                box.intersects(other) shouldBe true
            }
        }

        "A bounding box intersecting a ray" {
            should("be true when the ray starts inside the box.") {
                val box = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val ray = Ray3(Vector3(0.2f, -0.3f, 0.45f),
                               Point3(0.1f, 0.4f, 0.2f))
                box.intersects(ray) shouldBe true
            }

            should("be true when the ray starts outside but enters the " +
                   "box.") {
                val box = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val ray = Ray3(Vector3(-0.5f, -1.3f, -0.45f),
                               Point3(1.1f, 1.4f, 1.2f))
                box.intersects(ray) shouldBe true
            }

            should("be false when the ray stays outside the box.") {
                val box = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val ray = Ray3(Vector3(1.2f, 1.3f, 4.2f).normalizeSelf(),
                               Point3(2.1f, -2.4f, -2.2f))
                box.intersects(ray) shouldBe false
            }

            should("be true when the ray hits the boundary.") {
                val box = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val ray = Ray3(Vector3(0f, 1f, 0f), Point3(1f, -10f, 0f))
                box.intersects(ray) shouldBe true
            }
        }

        "A bounding box intersecting a sphere" {
            should("be true when the box is contained by the sphere.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(0.5f, Point3.Zero)
                box.intersects(sphere) shouldBe true
            }

            should("be true when the box is contained by the sphere but " +
                   "offset.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(0.5f, Point3(0.3f, 0.3f, 0.3f))
                box.intersects(sphere) shouldBe true
            }

            should("be true when the box contains the box.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(1.5f, Point3.Zero)
                box.intersects(sphere) shouldBe true
            }

            should("be false when the box is too far away.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(3f, 4f, 5f))
                box.intersects(sphere) shouldBe false
            }

            should("be false when the box is too far away.") {
                val box    = BoundingBox(1f, 1f, 1f,
                                         Point3(2f, 3f, 4f))
                val sphere = BoundingSphere(1f, Point3.Zero)
                box.intersects(sphere) shouldBe false
            }

            should("be true when on the positive x-axis boundary.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(2f, 0f, 0f))
                box.intersects(sphere) shouldBe true
            }

            should("be true when on the negative x-axis boundary.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(-2f, 0f, 0f))
                box.intersects(sphere) shouldBe true
            }

            should("be true when on the positive y-axis boundary.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(0f, 2f, 0f))
                box.intersects(sphere) shouldBe true
            }

            should("be true when on the negative y-axis boundary.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(0f, -2f, 0f))
                box.intersects(sphere) shouldBe true
            }

            should("be true when on the positive x-axis boundary.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(0f, 0f, 2f))
                box.intersects(sphere) shouldBe true
            }

            should("be true when on the negative x-axis boundary.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(0f, 0f, -2f))
                box.intersects(sphere) shouldBe true
            }
        }
    }
}
