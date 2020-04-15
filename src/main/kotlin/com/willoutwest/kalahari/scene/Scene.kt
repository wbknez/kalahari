package com.willoutwest.kalahari.scene

import com.willoutwest.kalahari.scene.camera.Camera
import com.willoutwest.kalahari.scene.camera.Viewport
import com.willoutwest.kalahari.scene.light.Light

/**
 * Represents a ray traceable scene.
 *
 * @property ambient
 *           The ambient light, if any.
 * @property camera
 *           The camera.
 * @property lights
 *           The collection of active lights.
 * @property root
 *           The root element of the scenegraph.
 * @property viewport
 *           The viewing plane.
 */
class Scene {

    @JvmField
    var ambient: Light? = null

    @JvmField
    var camera: Camera = Camera("camera")

    @JvmField
    var lights: MutableList<Light> = mutableListOf()

    @JvmField
    var root: Group = Group("root")

    @JvmField
    val viewport: Viewport = Viewport()
}