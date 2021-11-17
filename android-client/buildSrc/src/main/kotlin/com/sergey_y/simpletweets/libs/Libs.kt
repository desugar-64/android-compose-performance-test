package com.sergey_y.simpletweets.libs

object Libs {
    const val kotlin_version = "1.5.31"

    object Plugin {

        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        const val kotlin_serialization = "org.jetbrains.kotlin:kotlin-serialization:$kotlin_version"
        const val agp = "com.android.tools.build:gradle:7.0.3"
    }

    const val compose_version = "1.1.0-beta03"
    const val accompanist_version = "0.21.2-beta"
    const val coil_version = "2.0.0-alpha03"
}