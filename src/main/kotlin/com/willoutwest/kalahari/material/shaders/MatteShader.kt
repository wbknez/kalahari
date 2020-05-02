@file:Suppress("LocalVariableName")

package com.willoutwest.kalahari.material.shaders

import com.willoutwest.kalahari.material.Shader
import com.willoutwest.kalahari.material.brdfs.LambertianBrdf
import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.ComputeUtils
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.render.Tracer
import com.willoutwest.kalahari.scene.Geometric
import com.willoutwest.kalahari.scene.Scene

/**
 * Represents an implementation of [Shader] that computes the reflectance
 * color using ambient and diffuse reflection only.
 *
 * @property ambient
 *           The ambient reflectance equation.
 * @property diffuse
 *           The diffuse reflectance equation.
 */
class MatteShader : Shader {

    private val ambient: LambertianBrdf = LambertianBrdf()

    private val diffuse: LambertianBrdf = LambertianBrdf()

    override fun shade(scene: Scene, tracer: Tracer, record: Intersection,
                       store: Color3): Color3 {
        val cache    = ComputeUtils.localCache

        val lD       = cache.colors.borrow()
        val L        = cache.colors.borrow()

        val omegaI   = cache.vectors.borrow()
        val omegaNot = cache.vectors.borrow()

        val geom     = record.obj as Geometric
        val material = geom.material!!

        omegaNot.set(record.ray.dir)
        store.set(Color3.Black)

        if(!record.reversed) {
            omegaNot.negateSelf()
        }

        scene.ambient?.let {
            this.ambient.rho(material, record, omegaNot, lD)
            tracer.bulbs[it].L(it, scene.root, record, L)

            store.set(L)
                .timesSelf(lD)
        }

        scene.lights.forEach {
            val bulb  = tracer.bulbs[it]
            val nDotI = bulb.illuminate(it, record, omegaI).dot(record.normal)

            if(nDotI > 0f) {
                this.diffuse.f(material, record, omegaI, omegaNot, lD)
                bulb.L(it, scene.root, record, L)

                L.timesSelf(lD)
                    .timesSelf(nDotI)

                store.plusSelf(L)
            }
        }

        cache.colors.reuse(lD, L)
        cache.vectors.reuse(omegaI, omegaNot)

        return store
    }
}