package com.sergey_y.simpletweets.libs

object Libs {
    const val kotlin_version = "1.7.0"

    object Plugin {

        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        const val kotlin_serialization = "org.jetbrains.kotlin:kotlin-serialization:$kotlin_version"
        const val agp = "com.android.tools.build:gradle:7.2.1"
    }

    const val compose_version = "1.2.0-rc03"
    const val accompanist_version = "0.24.13-rc"
    const val coil_version = "2.1.0"
}