package com.sergey_y.simpletweets.libs

object Libs {
    const val kotlin_version = "1.5.30"

    object Plugin {

        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        const val kotlin_serialization = "org.jetbrains.kotlin:kotlin-serialization:$kotlin_version"
        const val agp = "com.android.tools.build:gradle:7.0.3"
    }

    const val compose_version = "1.0.3"
    const val accompanist_version = "0.19.0"
    const val coil_version = "1.4.0"
}