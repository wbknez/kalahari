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
    implementation(Libraries.snakeyaml)

    testImplementation(Libraries.kotlintest)
}

repositories {
    jcenter()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = application.mainClassName
    }

    from(configurations["runtimeClasspath"].map {
        if (it.isDirectory) it else zipTree(it)
    })
}

tasks.withType<Test> {
    useJUnitPlatform()
}
