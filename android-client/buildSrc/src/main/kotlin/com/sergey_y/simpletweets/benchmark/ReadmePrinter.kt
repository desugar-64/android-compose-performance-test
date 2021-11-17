package com.sergey_y.simpletweets.benchmark

import com.sergey_y.simpletweets.benchmark.model.BenchmarkReport
import java.io.File
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import java.text.StringCharacterIterator

class ReadmePrinter(
    private val descriptionDirectory: File,
    private val reportDirectory: File
) {

    private val df = DecimalFormat(".#").apply {
        roundingMode = RoundingMode.FLOOR
    }

    fun printDescription(
        deviceFolderName: String,
        allReports: List<Pair<String/*Compose version*/, BenchmarkReport>>
    ) {
        val deviceReportDir = File(File(reportDirectory, "reports"), deviceFolderName)
        println(deviceReportDir.path)
        if (deviceReportDir.exists()) {
            val summaryReportDir = File(deviceReportDir, "summary")
            val summaryFiles = summaryReportDir.listFiles().orEmpty().toList()

            generateReadme(
                benchmarkReports = allReports,
                deviceReportDir = deviceReportDir,
                summaryFiles = summaryFiles,
                benchmarkReportSvgFiles = File(deviceReportDir, "benchmark")
                    .listFiles()
                    .orEmpty()
                    .toList()
            )
        } else {
            println("No reports for $deviceFolderName were found.")
        }
    }

    private fun generateReadme(
        benchmarkReports: List<Pair<String, BenchmarkReport>>,
        deviceReportDir: File,
        summaryFiles: List<File>,
        benchmarkReportSvgFiles: List<File>
    ) {
        val readmeFile = File(deviceReportDir, "Readme.md")
        val imageDir = File(deviceReportDir, "image")
        if (imageDir.exists().not()) {
            imageDir.mkdir()
        }

        val composeVersions = benchmarkReports.map { it.first }
        val (composeVersion, benchmarkReport) = benchmarkReports.find { it.second.context.build.deviceName() == deviceReportDir.name }!!
        val device = benchmarkReport.context

        val content = buildString {
            val displayableDeviceName = device.build.displayableDeviceName().capitalize()

            append("# Jetpack Compose performance test summary")
            dnl()
            append("## Device")
            dnl()
            append("**Model:** $displayableDeviceName")
            dnl()
            append("**Android API:** ${device.build.version.sdk}")
            dnl()
            append("**CPU cores:** ${device.cpuCoreCount}")
            dnl()
            val coreClock = device.cpuMaxFreqHz / 1000.0 / 1000.0 / 1000.0
            append("**Core clock:** ${String.format("%.1f", coreClock)}Gz")
            dnl()
            append("**RAM:** ${humanReadableByteCountSI(device.memTotalBytes)}")
            dnl()
            append("<br/>")
            dnl()

            append("![$displayableDeviceName](compose_dynamics.svg)")
            dnl()
            append("# Comparison table")
            nl()
            append("Test name / Compose version (P50/P90, values in ms)")
            dnl()
            append("Test | " + composeVersions.joinToString(separator = " | "))
            nl()
            append("--- | " + composeVersions.joinToString(separator = " | ") { "---:" })
            nl()

            summaryFiles.forEach { file ->
                val benchmarkName = file.name.split("_")[0]
                val valuesByVersions =
                    composeVersions
                        .map { compose ->
                            val benchmarksOfVersion =
                                benchmarkReports.find { it.first == compose }!!
                            benchmarksOfVersion
                                .second
                                .benchmarks
                                .find { deviceBenchmarkSummary -> deviceBenchmarkSummary.name == benchmarkName }!!
                        }.map { benchmarkSummary ->
                            val frameCpuTimeMs = benchmarkSummary.sampledMetrics.frameCpuTimeMs
                            val p50 = frameCpuTimeMs.P50
                            val p90 = frameCpuTimeMs.P90
                            p50 to p90
                        }


                val tableColumns = valuesByVersions.joinToString(separator = " | ") { (p50, p90) ->
                    "`${df.format(p50)}` / `${df.format(p90)}`"
                }
                append("$benchmarkName | $tableColumns")
                nl()
            }

            for (summary in summaryFiles) {
                val benchmarkName = summary.name.split("_")[0]
//                val test = benchmarkReport.benchmarks.find { it.name == benchmarkName }!!

                val description = findDescription(benchmarkName)
                if (description != null) {
                    val localDescriptionImage = File(imageDir, description.webpImage.name)
                    description.webpImage.copyTo(localDescriptionImage, overwrite = true)
                    val headerImageRelativePath =
                        localDescriptionImage.toRelativeString(deviceReportDir).replace("\\", "/")
                    val summaryImageRelativePath =
                        summary.toRelativeString(deviceReportDir).replace("\\", "/")

                    append("<br/>")
                    dnl()
                    append("# $benchmarkName")
                    dnl()
                    append("Preview | Description")
                    nl()
                    append("----- | -----")
                    nl()
                    append("| ![${benchmarkName}](${headerImageRelativePath}) | ${description.description} ${description.procedure} |")
                    dnl()
                    append("![summary](${summaryImageRelativePath})")
                    dnl()
                } else println("No description for $benchmarkName was found.")
            }
            dnl()
            append("<br/>")
            dnl()
            append("## Benchmarks")
            dnl()
            benchmarkReportSvgFiles.groupBy { it.name.split("_")[2] }.forEach { (name, files) ->
                append("### $name")
                nl()
                files.sortedBy { it.name }.forEach { image ->
                    val relativePath = image.toRelativeString(deviceReportDir).replace("\\", "/")
                    append(("![${image.name}](${relativePath})"))
                    nl()
                }
                nl()
                append("---")
                nl()
            }
//            benchmarkReports.forEach { image ->
//                val relativePath = image.toRelativeString(deviceReportDir).replace("\\", "/")
//                append(("![${image.name}](${relativePath})"))
//                nl()
//            }
        }
        if (readmeFile.exists().not()) {
            readmeFile.createNewFile()
        }
        readmeFile.writeText(content)
    }

    private fun findDescription(benchmarkName: String): BenchmarkDescription? {
        val files =
            descriptionDirectory.listFiles().orEmpty().filter { it.name.startsWith(benchmarkName) }
        if (files.isEmpty()) {
            return null
        }
        val props = files.find { it.extension == "properties" }!!
        val webpImage = files.find { it.extension == "webp" }!!
        val gifImage = files.find { it.extension == "gif" }!!

        val prop = Properties()
        prop.load(props.inputStream())
        return BenchmarkDescription(
            description = prop["description"].toString(),
            procedure = prop["procedure"].toString(),
            webpImage = webpImage,
            gifImage = gifImage
        )
    }

    private fun StringBuilder.nl() = append("\n")
    private fun StringBuilder.dnl() = nl().nl()

    private fun humanReadableByteCountSI(bytes: Long): String? {
        var bytes = bytes
        if (-1000 < bytes && bytes < 1000) {
            return "$bytes B"
        }
        val ci = StringCharacterIterator("kMGTPE")
        while (bytes <= -999950 || bytes >= 999950) {
            bytes /= 1000
            ci.next()
        }
        return java.lang.String.format(Locale.US, "%.1f %cB", bytes / 1000.0, ci.current())
    }

}

data class BenchmarkDescription(
    val description: String,
    val procedure: String,
    val webpImage: File,
    val gifImage: File
)