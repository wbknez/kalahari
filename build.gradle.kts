plugins {
    kotlin("jvm") version Versions.kotlin 
}

dependencies {
    implementation(Libraries.kotlin)
    implementation(Libraries.kotlinref)
    implementation(Libraries.rxjava)
    implementation(Libraries.rxkotlin)

    testImplementation(Libraries.kotlintest)
}

repositories {
    jcenter()
}
