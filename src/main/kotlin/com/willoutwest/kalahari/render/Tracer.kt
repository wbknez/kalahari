package com.willoutwest.kalahari.render

import com.willoutwest.kalahari.material.Material
import com.willoutwest.kalahari.script.libraries.MaterialLibrary
import com.willoutwest.kalahari.material.Shader
import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.ComputeUtils
import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.render.orders.NaturalDrawingOrder
import com.willoutwest.kalahari.render.toners.LinearToner
import com.willoutwest.kalahari.scene.Geometric
import com.willoutwest.kalahari.scene.Scene
import com.willoutwest.kalahari.scene.camera.Camera
import com.willoutwest.kalahari.script.libraries.CameraLibrary
import com.willoutwest.kalahari.scene.camera.Lens
import com.willoutwest.kalahari.scene.light.Bulb
import com.willoutwest.kalahari.scene.light.Light
import com.willoutwest.kalahari.script.libraries.LightLibrary
import com.willoutwest.kalahari.util.TypeAssociator

/**
 * Represents a mechanism for computing the reflected color of light based on
 * the path of a single ray cast into a scene.
 *
 * The sole tracing mechanism implemented in this project is the Whitted
 * tracer, which allows the eventual determiniation of the color of reflective
 * and transparent surfaces through the use of recursive ray generation.
 *
 * @property bulbs
 *           A mapping of light types to bulbs.
 * @property drawOrder
 *           The ordering of pixels for drawing.
 * @property hEps
 *           The collection of per-geometry epsilon values that denote the
 *           parametric minimum for a valid ray-to-surface intersection.
 * @property lenses
 *           A mapping of camera types to lenses.
 * @property sEps
 *           The collection of per-geometry epsilon values that denote the
 *           parametric minimum for a valid shadow ray-to-surface intersection.
 * @property toner
 *           The local (per-pixel) tone mapping operator.
 */
class Tracer {

    @JvmField
    var bulbs: TypeAssociator<Light.Type, Bulb> = TypeAssociator()

    @JvmField
    var drawOrder: DrawingOrder = NaturalDrawingOrder()

    @JvmField
    val hEps: EpsilonTable = EpsilonTable(0.0001f)

    @JvmField
    val lenses: TypeAssociator<Camera.Type, Lens> = TypeAssociator()

    @JvmField
    val sEps: EpsilonTable = EpsilonTable(0.0001f)

    @JvmField
    val shaders: TypeAssociator<Material.Type, Shader> = TypeAssociator()

    @JvmField
    var toner: Toner = LinearToner()

    init {
        this.bulbs.addAll(LightLibrary.defaultBulbs())
        this.lenses.addAll(CameraLibrary.defaultLenses())
        this.shaders.addAll(MaterialLibrary.defaultShaders())
    }

    /**
     * Computes the reflected color of light based on the path of the
     * specified ray cast into the specified scene.
     *
     * @param ray
     *        The ray to trace.
     * @param scene
     *        The scene to render.
     * @param depth
     *        The current recursion depth.
     * @return The reflected color.
     */
    fun trace(ray: Ray3, scene: Scene, depth: Int): Color3 =
        this.trace(ray, scene, depth, Color3())

    /**
     * Computes the reflected color of light based on the path of the
     * specified ray cast into the specified scene.
     *
     * @param ray
     *        The ray to trace.
     * @param scene
     *        The scene to render.
     * @param depth
     *        The current recursion depth.
     * @param store
     *        The color to store the result in.
     * @return A reference to [store] for easy chaining.
     */
    fun trace(ray: Ray3, scene: Scene, depth: Int, store: Color3): Color3 =
        when(depth < scene.viewport.maxDepth) {
            false -> store.set(Color3.Black)
            true  -> {
                val cache   = ComputeUtils.localCache
                val hRecord = cache.records.borrow()
                val tMin    = cache.tmins.borrow()

                when(scene.root.intersects(ray, tMin, hRecord, this.hEps)) {
                    false -> store.set(scene.viewport.bgColor)
                    true  -> {
                        hRecord.depth = depth
                        hRecord.ray.set(ray)

                        val geom     = hRecord.obj as Geometric
                        val material = geom.material!!
                        val shader   = this.shaders[material]

                        shader.shade(scene, this, hRecord, store)
                    }
                }

                cache.records.reuse(hRecord)
                cache.tmins.reuse(tMin)

                store
            }
        }
}