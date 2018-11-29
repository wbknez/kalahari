package com.solsticesquared.kalahari.math.volume

import com.solsticesquared.kalahari.math.Point3
import com.solsticesquared.kalahari.math.Ray3
import com.solsticesquared.kalahari.math.Vector3
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.ShouldSpec

/**
 * Test suite for [BoundingSphere].
 */
class BoundingSphereTest : ShouldSpec() {

    init {

        "A bounding sphere containing a point" {

            should("be true when that point is completely inside the sphere" +
                   ".") {
                val point = Point3(0.9f, 0.2f, 0.3f)
                val sphere = BoundingSphere(1f, Point3.Zero)
                sphere.contains(point) shouldBe true
            }

            should("be false when that point is completely outside the " +
                   "sphere.") {
                val point = Point3(1.9f, 1.2f, 1.3f)
                val sphere = BoundingSphere(1f, Point3.Zero)
                sphere.contains(point) shouldBe false
            }

            should("be false when that point lies along the boundary.") {
                val point = Point3(1f, 0f, 0f)
                val sphere = BoundingSphere(1f, Point3.Zero)
                sphere.contains(point) shouldBe false
            }
        }

        "A bounding sphere intersecting a box" {

            should("be true when the sphere is contained by the box.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(0.5f, Point3.Zero)
                sphere.intersects(box) shouldBe true
            }

            should("be true when the sphere is contained by the box but " +
                   "offset.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(0.5f, Point3(0.3f, 0.3f, 0.3f))
                sphere.intersects(box) shouldBe true
            }

            should("be true when the sphere contains the box.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(1.5f, Point3.Zero)
                sphere.intersects(box) shouldBe true
            }

            should("be false when the sphere is too far away.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(3f, 4f, 5f))
                sphere.intersects(box) shouldBe false
            }

            should("be false when the box is too far away.") {
                val box    = BoundingBox(1f, 1f, 1f,
                                         Point3(2f, 3f, 4f))
                val sphere = BoundingSphere(1f, Point3.Zero)
                sphere.intersects(box) shouldBe false
            }

            should("be true when on the positive x-axis boundary.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(2f, 0f, 0f))
                sphere.intersects(box) shouldBe true
            }

            should("be true when on the negative x-axis boundary.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(-2f, 0f, 0f))
                sphere.intersects(box) shouldBe true
            }

            should("be true when on the positive y-axis boundary.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(0f, 2f, 0f))
                sphere.intersects(box) shouldBe true
            }

            should("be true when on the negative y-axis boundary.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(0f, -2f, 0f))
                sphere.intersects(box) shouldBe true
            }

            should("be true when on the positive x-axis boundary.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(0f, 0f, 2f))
                sphere.intersects(box) shouldBe true
            }

            should("be true when on the negative x-axis boundary.") {
                val box    = BoundingBox(1f, 1f, 1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(0f, 0f, -2f))
                sphere.intersects(box) shouldBe true
            }
        }

        "A bounding sphere intersecting a ray" {
            should("be true when the ray starts inside the sphere.") {
                val ray = Ray3(Vector3(0.2f, -0.3f, 0.45f),
                               Point3(0.1f, 0.4f, 0.2f))
                val sphere = BoundingSphere(1f, Point3.Zero)
                sphere.intersects(ray) shouldBe true
            }

            should("be true when the ray starts outside but enters the " +
                   "sphere.") {
                val ray = Ray3(Vector3(-0.5f, -1.3f, -0.45f),
                               Point3(1.1f, 1.4f, 1.2f))
                val sphere = BoundingSphere(1f, Point3.Zero)
                sphere.intersects(ray) shouldBe true
            }

            should("be false when the ray stays outside the sphere.") {
                val ray = Ray3(Vector3(0.2f, -0.3f, 0.45f),
                               Point3(1.1f, 1.4f, 1.2f))
                val sphere = BoundingSphere(1f, Point3.Zero)
                sphere.intersects(ray) shouldBe false
            }
        }

        "A bounding sphere intersecting another sphere" {
            should("be true when one sphere contains the other.") {
                val other = BoundingSphere(1f, Point3.Zero)
                val sphere = BoundingSphere(0.5f, Point3.Zero)
                sphere.intersects(other) shouldBe true
            }

            should("be true when one sphere contains the other but is also " +
                   "offset" +
                   ".") {
                val other = BoundingSphere(1f, Point3.Zero)
                val sphere = BoundingSphere(0.5f, Point3(0.3f, 0.3f, 0.3f))
                sphere.intersects(other) shouldBe true
            }

            should("be false when the sphere is too far away.") {
                val other = BoundingSphere(1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(2f, 3f, 4f))
                sphere.intersects(other) shouldBe false
            }

            should("be true when on the positive x-axis boundary.") {
                val other = BoundingSphere(1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(2f, 0f, 0f))
                sphere.intersects(other) shouldBe true
            }

            should("be true when on the negative x-axis boundary.") {
                val other = BoundingSphere(1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(-2f, 0f, 0f))
                sphere.intersects(other) shouldBe true
            }

            should("be true when on the positive y-axis boundary.") {
                val other = BoundingSphere(1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(0f, 2f, 0f))
                sphere.intersects(other) shouldBe true
            }

            should("be true when on the negative y-axis boundary.") {
                val other = BoundingSphere(1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(0f, -2f, 0f))
                sphere.intersects(other) shouldBe true
            }

            should("be true when on the positive z-axis boundary.") {
                val other = BoundingSphere(1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(0f, 0f, 2f))
                sphere.intersects(other) shouldBe true
            }

            should("be true when on the negative z-axis boundary.") {
                val other = BoundingSphere(1f, Point3.Zero)
                val sphere = BoundingSphere(1f, Point3(0f, 0f, -2f))
                sphere.intersects(other) shouldBe true
            }
        }
    }
}
