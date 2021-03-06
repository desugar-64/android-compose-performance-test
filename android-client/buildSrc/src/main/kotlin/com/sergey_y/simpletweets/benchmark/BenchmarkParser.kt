package com.sergey_y.simpletweets.benchmark

import com.sergey_y.simpletweets.benchmark.model.BenchmarkReport
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class BenchmarkParser(private val benchmarkReport: String) {
    val json = Json {
        ignoreUnknownKeys = true
        useAlternativeNames = true
    }
    fun parse(): BenchmarkReport {
        return json.decodeFromString(benchmarkReport)
    }
}