plugins {
    kotlin("jvm") version "1.5.30"
    kotlin("plugin.serialization") version "1.5.30"
}
//
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}

dependencies {
    implementation ("space.kscience:plotlykt-server:0.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
}


repositories {
    jcenter()
}