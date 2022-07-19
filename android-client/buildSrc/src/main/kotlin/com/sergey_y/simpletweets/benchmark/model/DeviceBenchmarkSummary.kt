package com.sergey_y.simpletweets.benchmark.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonNames

@Serializable
data class DeviceBenchmarkSummary(
    val name: String,
    val params: BenchmarkParams,
    val className: String,
    val totalRunTimeNs: Long,
    val metrics: Metric,
    val sampledMetrics: BenchmarkMetrics,
    val warmupIterations: Int,
    val repeatIterations: Int,
    val thermalThrottleSleepSeconds: Int
)

@Serializable
class BenchmarkParams() // todo
@Serializable
class Metric

@Serializable
data class BenchmarkMetrics(
    @JsonNames("frameCpuTimeMs", "frameDurationCpuMs")
    val frameCpuTimeMs: BenchmarkMetric
)

@Serializable
data class BenchmarkMetric(
    val P50: Float,
    val P90: Float,
    val P95: Float,
    val P99: Float,
    @SerialName("runs")
    val frames: List<List<Float>>
)