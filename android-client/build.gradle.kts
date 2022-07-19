
// Top-level build file where you can add configuration options common to all sub-projects/modules.

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

buildscript {
    dependencies {
        classpath (com.sergey_y.simpletweets.libs.Libs.Plugin.agp)
        classpath (com.sergey_y.simpletweets.libs.Libs.Plugin.kotlin)
        classpath (com.sergey_y.simpletweets.libs.Libs.Plugin.kotlin_serialization)
    }
    repositories {
        google()
        mavenCentral()
    }
}

allprojects {
    beforeEvaluate {
        tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
//            sourceCompatibility = "1.8"
//            targetCompatibility = "1.8"
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://repo.kotlin.link")
        }
    }
}
