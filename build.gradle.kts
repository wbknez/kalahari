plugins {
    application
    kotlin("jvm") version Versions.kotlin 
}

application {
    mainClassName = "${project.group}.${project.name}.AppEntry"
}

dependencies {
    implementation(Libraries.kotlin)
    implementation(Libraries.kotlinargp)
    implementation(Libraries.kotlinref)
    implementation(Libraries.luaj)
    implementation(Libraries.rxjava)
    implementation(Libraries.rxkotlin)
    implementation(Libraries.slf4japi)

    runtimeOnly(Libraries.slf4jjdk)

    testImplementation(Libraries.kotlintest)
    testImplementation(Libraries.slf4jnop)
}

repositories {
    jcenter()
}
