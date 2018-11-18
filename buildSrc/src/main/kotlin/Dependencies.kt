/**
 * Represents a collection of dependency versions for this project.
 */
sealed class Versions {
    
    companion object {
        
        const val kotlin     = "1.3.10"
        const val kotlintest = "2.0.7"
        const val rxjava     = "2.2.3"
        const val rxkotlin   = "2.3.0"
    }
}

/**
 * Represents a collection of all fully-qualified dependencies with their versions
 * used in this project in Gradle-compatible format.
 */
sealed class Libraries {

    companion object {

        const val kotlin     =
            "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val kotlinref  =
            "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
        const val kotlintest =
            "io.kotlintest:kotlintest:${Versions.kotlintest}"
        const val rxjava     =
            "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
        const val rxkotlin   =
            "io.reactivex.rxjava2:rxkotlin:${Versions.rxkotlin}"
    }
}
