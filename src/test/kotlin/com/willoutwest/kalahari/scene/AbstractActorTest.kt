package com.willoutwest.kalahari.scene

import com.willoutwest.kalahari.gen.smallFloats
import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.Quaternion
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.math.quaternion
import com.willoutwest.kalahari.math.shouldBe
import com.willoutwest.kalahari.math.vector3
import com.willoutwest.kalahari.util.FloatContainer
import com.willoutwest.kalahari.util.ObjectContainer
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.specs.ShouldSpec

class TestActor(name: String) : AbstractActor(name), Cloneable {

    constructor(ta: TestActor?) : this(ta!!.name)

    override fun clone(): TestActor = TestActor(this)

    override fun intersects(ray: Ray3, tMin: FloatContainer,
                            record: Intersection,
                            eps: EpsilonTable): Boolean = false

    override fun shadows(ray: Ray3, tMin: FloatContainer, obj: ObjectContainer,
                         eps: EpsilonTable, tMax: Float): Boolean = false
}

class TestActorGenerator(val maxChars: Int = 20) : Gen<TestActor> {

    override fun constants(): Iterable<TestActor> = emptyList()

    override fun random(): Sequence<TestActor> = generateSequence {
        TestActor(Gen.string(this.maxChars).random().first())
    }
}

fun Gen.Companion.testActor(maxChars: Int = 20): Gen<TestActor> =
    TestActorGenerator(maxChars)

class AbstractActorTest : ShouldSpec() {

    init {

        "Moving an actor by individual components" {
            should("add each component to the translation") {
                assertAll(Gen.testActor(), Gen.numericFloats(-1000f, 1000f),
                          Gen.numericFloats(-1000f, 1000f),
                          Gen.numericFloats(-1000f, 1000f)) {
                    actor: TestActor, x: Float, y: Float, z: Float ->

                    actor.move(x, y, z)
                    actor.motion.translation.shouldBe(x, y, z)

                    actor.move(x, y, z)
                    actor.motion.translation.shouldBe(x * 2f, y * 2f, z * 2f)
                }
            }
        }

        "Moving an actor by a vector" {
            should("add each vector component to the translation") {
                assertAll(Gen.testActor(), Gen.vector3()) {
                    actor: TestActor, vec: Vector3 ->

                    actor.move(vec)
                    actor.motion.translation.shouldBe(vec)

                    actor.move(vec)
                    actor.motion.translation.shouldBe(vec * 2f)
                }
            }
        }

        "Rotating an actor using axis angles" {
            should("rotate around the axes via multiplication") {
                assertAll(Gen.testActor(), Gen.smallFloats(), Gen.vector3()) {
                    actor: TestActor, angle: Float, axis: Vector3 ->
                    val quat0 = Quaternion(angle, axis)
                    val quat1 = Quaternion(angle / 2f, axis / 2f)

                    actor.rotate(angle, axis)
                    actor.motion.rotation.shouldBe(quat0)

                    actor.rotate(quat1)
                    actor.motion.rotation.shouldBe(quat0 * quat1)
                }
            }
        }

        "Rotating an actor using Euler angles" {
            should("rotate each axis in turn via multiplication") {
                assertAll(Gen.testActor(), Gen.smallFloats(),
                          Gen.smallFloats(), Gen.smallFloats()) {
                    actor: TestActor, roll: Float, pitch: Float, yaw: Float ->
                    val quat0 = Quaternion(roll, pitch, yaw)
                    val quat1 = Quaternion(roll / 2f, pitch  / 2f, yaw / 2f)

                    actor.rotate(roll, pitch, yaw)
                    actor.motion.rotation.shouldBe(quat0)

                    actor.rotate(roll / 2f, pitch / 2f, yaw / 2f)
                    actor.motion.rotation.shouldBe(quat0 * quat1)
                }
            }
        }

        "Rotating an actor using quaternions" {
            should("rotate via multiplication") {
                assertAll(Gen.testActor(), Gen.quaternion()) {
                    actor: TestActor, quat: Quaternion ->
                    val quat1 = quat / 2f

                    actor.rotate(quat)
                    actor.motion.rotation.shouldBe(quat)

                    actor.rotate(quat1)
                    actor.motion.rotation.shouldBe(quat * quat1)
                }
            }
        }

        "Scaling an actor by individual components" {
            should("multiply each component by the scale") {
                assertAll(Gen.testActor(), Gen.numericFloats(-1000f, 1000f),
                          Gen.numericFloats(-1000f, 1000f),
                          Gen.numericFloats(-1000f, 1000f)) {
                    actor: TestActor, x: Float, y: Float, z: Float ->

                    actor.scale(x, y, z)
                    actor.motion.scale.shouldBe(x, y, z)

                    actor.scale(x, y, z)
                    actor.motion.scale.shouldBe(x * x, y * y, z * z)
                }
            }
        }

        "Scaling an actor by a vector" {
            should("multiply each vector component by the scale") {
                assertAll(Gen.testActor(), Gen.vector3()) {
                    actor: TestActor, vec: Vector3 ->

                    actor.scale(vec)
                    actor.motion.scale.shouldBe(vec)

                    actor.scale(vec)
                    actor.motion.scale.shouldBe(vec.x * vec.x,
                                                vec.y * vec.y,
                                                vec.z * vec.z)
                }
            }
        }
    }
}