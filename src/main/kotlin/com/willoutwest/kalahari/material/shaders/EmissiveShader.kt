package com.willoutwest.kalahari.material.shaders

import com.willoutwest.kalahari.material.Shader
import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.render.Tracer
import com.willoutwest.kalahari.scene.Geometric
import com.willoutwest.kalahari.scene.Scene

/**
 * Represents an implementation of [Shader] that computes emittance rather
 * than reflectance.
 */
class EmissiveShader : Shader {

    override fun shade(scene: Scene, tracer: Tracer, record: Intersection,
                       store: Color3): Color3 {
        val mod = when(record.reversed) {
            false -> -1f
            true  -> 1f
        }
        val nDotN = record.normal.x * record.ray.dir.x * mod +
                    record.normal.y * record.ray.dir.y * mod +
                    record.normal.z * record.ray.dir.z * mod

        return when(nDotN > 0f) {
            false -> store.set(Color3.Black)
            true  -> {
                val geom     = record.obj as Geometric
                val material = geom.material!!

                material.cE.getColor(record, store)
                    .timesSelf(material.kE)
            }
        }
    }
}