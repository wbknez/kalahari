plugins {
    application
    kotlin("jvm") version Versions.kotlin 
}

application {
    mainClassName = "${project.group}.${project.name}.AppEntry"
}

dependencies {
    implementation(Libraries.klaxon)
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

tasks.withType<Test> {
    useJUnitPlatform()
}
