package com.sergey_y.simpletweets.benchmark.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class DeviceContext(
    val build: DeviceBuild,
    val cpuCoreCount: Int,
    val cpuLocked: Boolean,
    val cpuMaxFreqHz: Long,
    val memTotalBytes: Long,
    val sustainedPerformanceModeEnabled: Boolean
)

@Serializable
data class DeviceBuild(
    val brand: String,
    val device: String,
    val fingerprint: String,
    val model: String,
    val version: SdkVersion
) {
    fun deviceName() = "${brand}_${model}".toLowerCase(Locale.ROOT).replace(" ", "_")
    fun displayableDeviceName() = "$brand $model"
}

@Serializable
data class SdkVersion(val sdk: Int)