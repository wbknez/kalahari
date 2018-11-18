/**
 * Represents a collection of dependency versions for this project.
 */
sealed class Versions {
    
    companion object {
        
        val kotlin   = "1.3.10"
        val rxjava   = "2.2.3" 
        val rxkotlin = "2.3.0" 
    }
}

/**
 * Represents a collection of all fully-qualified dependencies with their versions
 * used in this project in Gradle-compatible format.
 */
sealed class Libraries {

    companion object {

        val kotlin   = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        val rxjava   = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
        val rxkotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxkotlin}"
    }
}
