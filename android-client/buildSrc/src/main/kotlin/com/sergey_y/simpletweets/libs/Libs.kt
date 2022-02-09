package com.sergey_y.simpletweets.libs

object Libs {
    const val kotlin_version = "1.6.10"

    object Plugin {

        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        const val kotlin_serialization = "org.jetbrains.kotlin:kotlin-serialization:$kotlin_version"
        const val agp = "com.android.tools.build:gradle:7.0.3"
    }

    const val compose_version = "1.1.0"
    const val accompanist_version = "0.22.1-rc"
    const val coil_version = "2.0.0-alpha08"
}