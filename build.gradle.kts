plugins {
    kotlin("jvm") version Versions.kotlin 
}

dependencies {
    implementation(Libraries.kotlin)
    implementation(Libraries.rxjava)
    implementation(Libraries.rxkotlin)
}

repositories {
    jcenter()
}
