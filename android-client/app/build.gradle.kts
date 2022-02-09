import com.sergey_y.simpletweets.libs.Libs

plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("plugin.serialization")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.sergey_y.simpletweets"
        minSdk = 23
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file("../test.jks")
            storePassword = "12345678"
            keyAlias = "Test"
            keyPassword = "12345678"
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        create("benchmark") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = false
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Libs.compose_version
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Libs.kotlin_version}")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.compose.ui:ui:${Libs.compose_version}")
    implementation("androidx.compose.material:material:${Libs.compose_version}")
    implementation("androidx.compose.material:material-icons-extended:${Libs.compose_version}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Libs.compose_version}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("io.coil-kt:coil-compose:${Libs.coil_version}")
    implementation("com.google.accompanist:accompanist-insets:${Libs.accompanist_version}")
    implementation("com.google.accompanist:accompanist-systemuicontroller:${Libs.accompanist_version}")
    implementation("com.google.accompanist:accompanist-navigation-animation:${Libs.accompanist_version}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Libs.compose_version}")
    debugImplementation("androidx.compose.ui:ui-tooling:${Libs.compose_version}")
}