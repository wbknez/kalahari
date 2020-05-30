package com.willoutwest.kalahari.scene.surfaces

/**
 * A collection of methods for working with surface-specific epsilons.
 */
sealed class EpsilonUtils {
    companion object {

        /**
         * Creates and returns a mapping of all surfaces (in this project)
         * to their specific epsilon quantities that determine valid ray
         * intersection(s).
         *
         * @return A collection of hit epsilons.
         */
        fun hitEpsilons(): MutableMap<String, Float> = mutableMapOf(
            Box.ID       to Box.HitEps,
            Cylinder.ID  to Cylinder.HitEps,
            Disk.ID      to Disk.HitEps,
            Plane.ID     to Plane.HitEps,
            Rectangle.ID to Rectangle.HitEps,
            Sphere.ID    to Sphere.HitEps,
            Torus.ID     to Torus.HitEps,
            Triangle.ID  to Triangle.HitEps
        )

        /**
         * Creates and returns a mapping of all surfaces (in this project)
         * to their specific epsilon quantities that determine valid shadow
         * ray intersection(s).
         *
         * @return A collection of shadowing epsilons.
         */
        fun shadowEpsilons(): MutableMap<String, Float> = mutableMapOf(
            Box.ID       to Box.ShadowEps,
            Cylinder.ID  to Cylinder.ShadowEps,
            Disk.ID      to Disk.ShadowEps,
            Plane.ID     to Plane.ShadowEps,
            Rectangle.ID to Rectangle.ShadowEps,
            Sphere.ID    to Sphere.ShadowEps,
            Torus.ID     to Torus.ShadowEps,
            Triangle.ID  to Triangle.ShadowEps
        )
    }
}