plugins {
    kotlin("jvm") version Versions.kotlin 
}

dependencies {
    implementation(Libraries.kotlin)
    implementation(Libraries.kotlinargp)
    implementation(Libraries.kotlinref)
    implementation(Libraries.luaj)
    implementation(Libraries.rxjava)
    implementation(Libraries.rxkotlin)

    testImplementation(Libraries.kotlintest)
}

repositories {
    jcenter()
}
