package com.sergey_y.simpletweets.benchmark

import java.io.File

object BenchmarkConfig {
    const val RAW_DATA_DIR_NAME = "macrobench"
    const val ON_DEVICE_DATA_DIR_PATH = "/sdcard/$RAW_DATA_DIR_NAME"

    const val LOCAL_REPORT_DIR_NAME = "benchmark_reports"
    const val LOCAL_BENCHMARK_VERSIONS_DIR_NAME = "benchmarked_compose_versions"
    const val LOCAL_BENCHMARK_DESCRIPTION_DIR_NAME = "benchmark_description"

    fun localRawDataDirPath(projectDir: File): File {
        return File(projectDir, RAW_DATA_DIR_NAME)
    }

    fun localReportsDirPath(projectDir: File): File {
        return File(projectDir, LOCAL_REPORT_DIR_NAME)
    }

    fun localBenchmarkedComposeDirPath(projectDir: File): File {
        return File(File(projectDir, LOCAL_REPORT_DIR_NAME), LOCAL_BENCHMARK_VERSIONS_DIR_NAME)
    }

    fun localBenchmarkDescriptionDirPath(projectDir: File): File {
        return File(projectDir, LOCAL_BENCHMARK_DESCRIPTION_DIR_NAME)
    }
}