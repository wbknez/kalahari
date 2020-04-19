package com.willoutwest.kalahari.scene.camera

import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.math.sample.Sampler2
import com.willoutwest.kalahari.util.ParameterMap
import com.willoutwest.kalahari.util.Typeable
import com.willoutwest.kalahari.util.hash

/**
 * Represents a collection of viewing parameters that control how pairs of
 * pixel coordinates are transformed into rays that trace the boundaries of a
 * scene.
 *
 * @param name
 *        The camera name to use.
 * @property beta
 *           The stereo separation angle.
 * @property distance
 *           The distance to the viewing plane.
 * @property exposure
 *           The amount of time to accumulate light.
 * @property eye
 *           The observer (camera) location.
 * @property focus
 *           The distance to the focus plane.
 * @property gap
 *           The amount of space between images (in pixels).
 * @property lambdaMax
 *           The maximum field of view in the azimuth direction.
 * @property left
 *           The left camera in a stereoscopic view.
 * @property lookAt
 *           The target location.
 * @property mode
 *           The stereoscopic viewing mode.
 * @property offSet
 *           The number of pixels to offset by along the y-axis when
 *           rendering to an output; only used for stereoscopic rendering.
 * @property psiMax
 *           The maximum field of view in the polar direction.
 * @property radius
 *           The lens radius.
 * @property right
 *           The right camera in a stereoscopic view.
 * @property sampler
 *           The lens sampler.
 * @property up
 *           The "up" direction.
 * @property uvw
 *           The orthonormal viewing basis.
 * @property xShift
 *           The amount to shift by along the x-axis; only used for
 *           stereoscopic rendering.
 * @property zoom
 *           The amount of per-pixel magnification.
 */
class Camera(name: String) :
    Cloneable, ParameterMap(name), Typeable<Camera.Type> {

    /**
     * Represents a unique identifier that denotes the type of lens that
     * should be used to produce rays from a camera.
     */
    interface Type

    var beta: Float by this.params

    var distance: Float by this.params

    var exposure: Float = 1f

    var eye: Point3 = Point3(0f, 0f, 500f)

    var focus: Float by this.params

    var gap: Int by this.params

    var lambdaMax: Float by this.params

    var left: Camera by this.params

    var lookAt: Point3 = Point3.Zero.clone()

    var mode: StereoMode by this.params

    var offSet: Int = 0

    var radius: Float by this.params

    var sampler: Sampler2 by this.params

    var psiMax: Float by this.params

    var right: Camera by this.params

    var up: Vector3 = Vector3.Y.clone()

    val uvw: Uvw = Uvw()

    override var type: Type by this.params

    var xShift: Float = 0f

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

    override fun equals(other: Any?): Boolean =
        when(other) {
            is Camera -> super.equals(other) &&
                         this.exposure == other.exposure &&
                         this.eye == other.eye &&
                         this.lookAt == other.lookAt &&
                         this.type == other.type &&
                         this.up == other.up &&
                         this.uvw == other.uvw
            else      -> false
        }

    override fun hashCode(): Int =
        hash(super.hashCode(), this.exposure, this.eye, this.lookAt, this.type,
             this.up, this.uvw)

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