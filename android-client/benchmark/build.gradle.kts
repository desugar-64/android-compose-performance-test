import com.sergey_y.simpletweets.benchmark.BenchmarkConfig
import com.sergey_y.simpletweets.benchmark.BenchmarkParser
import com.sergey_y.simpletweets.benchmark.GraphPrinter
import com.sergey_y.simpletweets.benchmark.ReadmePrinter
import com.sergey_y.simpletweets.benchmark.model.BenchmarkReport
import com.sergey_y.simpletweets.libs.Libs
import org.jetbrains.kotlin.util.capitalizeDecapitalize.toLowerCaseAsciiOnly


plugins {
    id("com.android.test")
    id("kotlin-android")
}

android {
    compileSdk = 31
    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = "LOW-BATTERY"
        testInstrumentationRunnerArguments["additionalTestOutputDir"] =
            BenchmarkConfig.ON_DEVICE_DATA_DIR_PATH
        testInstrumentationRunnerArguments["no-isolated-storage"] = "1"

    }

    targetProjectPath = ":app"
    experimentalProperties["android.experimental.self-instrumenting"] = true

    signingConfigs {
        create("release") {
            storeFile = file("../test.jks")
            storePassword = "12345678"
            keyAlias = "Test"
            keyPassword = "12345678"
        }
    }

    buildTypes {
        create("benchmark") {
            // duplicate any release build type settings for measurement accuracy,
            // such as "minifyEnabled" and "proguardFiles" in this block
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }

        create("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}
androidComponents {
    beforeVariants(selector().all()) {
        // Enable only the benchmark buildType, since we only want to measure
        // release-like build performance (should match app buildType)
        it.enabled = it.buildType == "benchmark"

    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Libs.kotlin_version}")
    implementation("androidx.test.ext:junit:1.1.3")
    implementation("androidx.test.espresso:espresso-core:3.4.0")
    implementation("androidx.test.uiautomator:uiautomator:2.2.0")
    implementation("androidx.benchmark:benchmark-macro-junit4:1.1.0-alpha12")

    compileOnly("space.kscience:plotlykt-server:0.5.0")
}

val startBenchmarkTask = tasks.named("connectedCheck")

val cleanRemoteBenchmarkDataTask = task("cleanBenchmarkData", Exec::class) {
    dependsOn("clean")
    try {
        commandLine("adb", "shell", "rm -rf", BenchmarkConfig.ON_DEVICE_DATA_DIR_PATH)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
    doLast {
        println("${BenchmarkConfig.RAW_DATA_DIR_NAME} has been deleted!")
    }
}

val pullDataTask = task("pullData", Exec::class) {
    mustRunAfter(startBenchmarkTask)
    commandLine("adb", "pull", BenchmarkConfig.ON_DEVICE_DATA_DIR_PATH)
    doLast {
        println("The report was pulled from the device!")
    }
}

val deleteLocalBenchmarkDataTask = task("deleteLocalBenchmarkData", Delete::class) {
    dependsOn(cleanRemoteBenchmarkDataTask)
    delete(BenchmarkConfig.localRawDataDirPath(projectDir))
}

val buildGraphTask = task("buildBenchmarkGraph") {
    mustRunAfter(pullDataTask)
    doLast {
        val jsonReportDir = BenchmarkConfig.localRawDataDirPath(projectDir)
        jsonReportDir
            .listFiles()
            ?.filter { it.extension == "json" }
            ?.forEach { printBenchmarkChart(Libs.compose_version, it, true) }
    }
}

val updateBenchmarkGraphs = task("updateBenchmarkGraphs") {
    doLast {
        val jsonReportDir = BenchmarkConfig.localBenchmarkedComposeDirPath(projectDir)
        jsonReportDir
            .listFiles()
            ?.filter { it.extension == "json" }
            ?.forEach {
                val composeVersion = it.name.split("_")[1]
                printBenchmarkChart(composeVersion, it, false)
            }
    }
}

val report = task("report") {
    dependsOn(startBenchmarkTask, pullDataTask, buildGraphTask)
    doLast {
        println("Reports saved to ${BenchmarkConfig.localReportsDirPath(projectDir).path}")
        println("Cleaning up the old benchmark data...")
    }
    finalizedBy(deleteLocalBenchmarkDataTask)
}

task("go") {
//    dependsOn(report)
    doLast {
        val benchmarksDir = BenchmarkConfig.localBenchmarkedComposeDirPath(projectDir)
        val files: Array<File> = benchmarksDir.listFiles() ?: emptyArray()

        val jsonFiles = files.filter { it.extension == "json" }

        if (jsonFiles.isEmpty()) {
            error("No benchmark records were found. Nothing to compare!")
        }

        // name pattern compose_x.x.x_device_model_benchmark
        val getVersion: (String) -> String = { it.split("_")[1] }
        val getDevice: (String) -> String = {
            it.split("_").let { tokens ->
                val model = tokens.drop(2).dropLast(1).joinToString("_")
                model
            }
        }

        val reportsByDevice: Map<String/* device name */, List<Pair<String/* compose version */, BenchmarkReport>>> =
            files
                .sortedBy { file -> getVersion(file.name) }
                .groupBy { file -> getDevice(file.name) }
                .mapValues { (_, value) ->
                    value
                        .sortedBy { getVersion(it.name) }
                        .map { getVersion(it.name) to BenchmarkParser(it.readText()).parse() }
                }

        val graphPrinter = GraphPrinter()

        reportsByDevice.forEach { (deviceName: String, value: List<Pair<String, BenchmarkReport>>) ->
            if (value.size > 1) {
                graphPrinter.printComposeHistory(
                    reports = value,
                    saveDir = BenchmarkConfig.localReportsDirPath(projectDir)
                )

                ReadmePrinter(
                    descriptionDirectory = BenchmarkConfig.localBenchmarkDescriptionDirPath(
                        projectDir
                    ),
                    reportDirectory = BenchmarkConfig.localReportsDirPath(projectDir)
                ).printDescription(deviceName, value)

                val displayableDeviceName =
                    value.first().second.context.build.displayableDeviceName().capitalize()
                graphPrinter.printDynamics(
                    saveDir = File(
                        File(BenchmarkConfig.localReportsDirPath(projectDir), BenchmarkConfig.DEVICE_REPORTS_DIR_NAME),
                        deviceName
                    ),
                    allDeviceBenchmarkReports = mapOf(deviceName to value),
                    graphTitle = "Jetpack Compose dynamics. $displayableDeviceName"
                )

                println("Benchmarks compared for the device ${deviceName}!")
            } else {
                println("Two or more benchmark reports are required for comparison.")
            }
        }

        // Print Jetpack Compose performance dynamics across all benchmarked devices
        graphPrinter.printDynamics(
            saveDir = File(BenchmarkConfig.localReportsDirPath(projectDir), BenchmarkConfig.DEVICE_REPORTS_DIR_NAME),
            allDeviceBenchmarkReports = reportsByDevice
        )
    }


}

fun printBenchmarkChart(composeVersion: String, jsonReport: File, backupFile: Boolean) {
    println("benchmark file ${jsonReport.name} opened")
    val report = BenchmarkParser(jsonReport.readText()).parse()
    val printer = GraphPrinter()
    val saveDir = BenchmarkConfig.localReportsDirPath(projectDir)
    if (saveDir.exists().not()) {
        saveDir.mkdir()
    }
    printer.printReport(composeVersion, report, saveDir)

    if (backupFile) {
        // backup old benchmark json file
        val backupDir = BenchmarkConfig.localBenchmarkedComposeDirPath(projectDir)
        if (backupDir.exists().not()) {
            backupDir.mkdir()
        }
        val deviceName =
            report.context.build.deviceName().toLowerCaseAsciiOnly().replace(" ", "")

        val backupFileName = "compose_${Libs.compose_version}_${deviceName}_benchmark.json"
        val backupBenchmarkFile = File(backupDir, backupFileName)
        jsonReport.copyTo(backupBenchmarkFile, overwrite = true)
    }
}