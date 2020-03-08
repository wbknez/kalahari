package com.willoutwest.kalahari.scene.camera

import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.util.ParameterMap
import com.willoutwest.kalahari.util.Typeable

/**
 * Represents a collection of viewing parameters that control how pairs of
 * pixel coordinates are transformed into rays that trace the boundaries of a
 * scene.
 *
 * @property distance
 *           The distance to the viewing plane.
 * @property exposure
 *           The amount of time to accumulate light.
 * @property eye
 *           The observer (camera) location.
 * @property lookAt
 *           The target location.
 * @property up
 *           The "up" direction.
 * @property uvw
 *           The orthonormal viewing basis.
 */
class Camera(name: String) :
    Cloneable, ParameterMap(name), Typeable<Camera.Type> {

    /**
     * Represents a unique identifier that denotes the type of lens that
     * should be used to produce rays from a camera.
     */
    interface Type

    var distance: Float by this.params

    var exposure: Float = 1f

    var eye: Point3 = Point3(0f, 0f, 500f)

    var lookAt: Point3 = Point3.Zero.clone()

    var up: Vector3 = Vector3.Y.clone()

    val uvw: Uvw = Uvw()

    override var type: Type by this.params

    var xShift: Float by this.params

    var zoom: Float by this.params

    /**
     * Constructor.
     *
     * @param camera
     *        The camera to copy from.
     */
    constructor(camera: Camera?) : this(camera!!.id) {
        this.params.putAll(camera.params)

        this.exposure = camera.exposure
        this.eye.set(camera.eye)
        this.lookAt.set(camera.lookAt)
        this.up.set(camera.up)
        this.uvw.u.set(camera.uvw.u)
        this.uvw.v.set(camera.uvw.v)
        this.uvw.w.set(camera.uvw.w)
    }

    override fun clone(): Camera = Camera(this)

    fun setEye(x: Float, y: Float, z: Float) {
        this.eye.set(x, y, z)
    }

    fun setLookAt(x: Float, y: Float, z: Float) {
        this.lookAt.set(x, y, z)
    }

    fun setUp(x: Float, y: Float, z: Float) {
        this.up.set(x, y, z)
    }
}