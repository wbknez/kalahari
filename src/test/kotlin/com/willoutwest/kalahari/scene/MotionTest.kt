package com.willoutwest.kalahari.scene

import com.willoutwest.kalahari.math.quaternion
import com.willoutwest.kalahari.math.shouldBe
import com.willoutwest.kalahari.math.vector3
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.specs.ShouldSpec

class MotionGenerator : Gen<Motion> {

    override fun constants(): Iterable<Motion> = emptyList()

    override fun random(): Sequence<Motion> = generateSequence {
        Motion(Gen.quaternion().random().first(),
               Gen.vector3().random().first(),
               Gen.vector3().random().first())
    }
}

fun Gen.Companion.motion(): Gen<Motion> = MotionGenerator()

/**
 * Test suite for [Motion].
 */
class MotionTest : ShouldSpec() {

    init {

        "Converting a motion to a transformation matrix" {
            should("multiply the scale, rotation, and translation matrices " +
                   "in reverse order") {
                assertAll(Gen.motion()) { motion: Motion ->
                    val rot   = motion.rotation.toMatrix()
                    val scale = motion.scale.toScalingMatrix()
                    val trans = motion.translation.toTranslationMatrix()

                    motion.toMatrix().shouldBe(trans * rot * scale)
                }
            }
        }
    }
}