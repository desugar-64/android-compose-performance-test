package com.sergey_y.simpletweets.benchmark.model

import kotlinx.serialization.Serializable

@Serializable
data class BenchmarkReport(
    val context: DeviceContext,
    val benchmarks: List<DeviceBenchmarkSummary>
)