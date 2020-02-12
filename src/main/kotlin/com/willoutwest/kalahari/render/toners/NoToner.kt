package com.willoutwest.kalahari.render.toners

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.render.Toner
import com.willoutwest.kalahari.scene.camera.Camera
import com.willoutwest.kalahari.scene.camera.Viewport

/**
 * Represents an implementation of [Toner] that does not apply any
 * tone-mapping at all.
 */
class NoToner : Toner {

    override fun tone(camera: Camera, viewport: Viewport, input: Color3,
                      store: Color3): Color3 =
        store.set(input)
}