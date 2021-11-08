package com.sergey_y.simpletweets.benchmark

import com.sergey_y.simpletweets.benchmark.model.BenchmarkReport
import com.sergey_y.simpletweets.benchmark.model.DeviceBenchmarkSummary
import space.kscience.dataforge.values.Value
import space.kscience.plotly.*
import space.kscience.plotly.models.*
import java.io.File
import space.kscience.plotly.palettes.Xkcd
import space.kscience.plotly.palettes.T10
import kotlin.math.abs
import kotlin.math.floor

class GraphPrinter {

    @OptIn(UnstablePlotlyAPI::class, ExperimentalStdlibApi::class)
    fun printReport(
        composeVersion: String,
        report: BenchmarkReport,
        dir: File
    ) {
        val benchmarks = mutableMapOf<String, MutableList<DeviceBenchmarkSummary>>()

        for (benchmark in report.benchmarks) {
            if (benchmark.name.contains("particle", ignoreCase = true)) {
                benchmarks.getOrPut("animation", ::mutableListOf).add(benchmark)
            } else {
                benchmarks[benchmark.name] = mutableListOf(benchmark)
            }
        }

        benchmarks.values.forEach {
            val benchmarkName =
                if (it.size == 1) it.first().name else "500particlesAnimation"
            val xAxisName = if (it.size == 1) "Frame" else "Particle Animation"

            val plot = Plotly.plot {

                if (it.size == 1) {
                    layout.showlegend = false

                    val benchmark = it.first()
                    val frameCpuTimeMs = benchmark.sampledMetrics.frameCpuTimeMs
                    val runs = frameCpuTimeMs.frames.flatten()
                    val maxFrames = runs.size
                    frameBars(0, runs)

                    fpsLine30(maxFrames)
                    fpsLine60(maxFrames)
                    fpsLine120(maxFrames)
                } else {
                    layout.showlegend = true
                    val sorted = it.sortedBy { it.sampledMetrics.frameCpuTimeMs.P90 }
                    barPercentileTrace(
                        "P50",
                        sorted.map { summary -> summary.name },
                        sorted.map { summary -> summary.sampledMetrics.frameCpuTimeMs.P50 },
                        barColor = palette[0]
                    )
                    barPercentileTrace(
                        "P90",
                        sorted.map { summary -> summary.name },
                        sorted.map { summary -> summary.sampledMetrics.frameCpuTimeMs.P90 },
                        barColor = palette[1]
                    )
                }


                layout {
                    val plotName =
                        "${report.context.build.displayableDeviceName()}. Benchmark <b>${benchmarkName}</b>. Compose $composeVersion"
                    title {
                        text = plotName
                        font {
                            size = 10
                        }
                    }
                    height = PLOT_HEIGHT
                    width = PLOT_WIDTH
                    xaxis {
                        automargin = true
                        tickmode = TickMode.auto
                        title = xAxisName
                        showgrid = false
                    }
                    yaxis {
                        automargin = true
                        tickmode = TickMode.auto
                        title = "Frametime, ms"
                        range(Value.of(0.0f), Value.of(60.0f))
                    }
                }
            }
            val deviceName = report.context.build.deviceName()
            val fileName =
                "compose_${composeVersion}_${benchmarkName}_benchmark.svg"
            val svgDir = File(dir, "reports${SEP}$deviceName${SEP}benchmark")
            if (svgDir.exists().not()) {
                svgDir.mkdirs()
            }
            val reportSvgPath = File(svgDir, fileName).toPath()

            plot.export(reportSvgPath) // Exports SVG, Orca must be installed, see function doc.
        }
    }

    @OptIn(UnstablePlotlyAPI::class, ExperimentalStdlibApi::class)
    fun printComposeHistory(reports: List<Pair<String, BenchmarkReport>>, saveDir: File) {
        val firstBenchmark = reports.first().second
        val displayableDeviceName = firstBenchmark.context.build.displayableDeviceName()
        val deviceNameForDir = firstBenchmark.context.build.deviceName()

        // Map<BenchmarkName, List<ComposeVersion, BenchmarkSummary>
        val dataset = reports
            .flatMap { (version, report) ->
                report.benchmarks.map { version to it }
            }.groupBy { it.second.name }

        dataset.forEach { (benchmarkName, summary) ->
            val composeVersions = summary.map { it.first }
            val benchmarks = summary.map { it.second }
            val reducePrecision = { value: Float -> floor(value * 10) / 10 }
//            val p95th = benchmarks.map { it.sampledMetrics.frameCpuTimeMs.P95 }.map(reducePrecision)
            val p90th = benchmarks.map { it.sampledMetrics.frameCpuTimeMs.P90 }.map(reducePrecision)
            val p50th = benchmarks.map { it.sampledMetrics.frameCpuTimeMs.P50 }.map(reducePrecision)
            val plot = Plotly.plot {
                barPercentileTrace(
                    name = "P50",
                    xs = composeVersions,
                    ys = p50th,
                    barColor = palette[0]
                )
                barPercentileTrace(
                    name = "P90",
                    xs = composeVersions,
                    ys = p90th,
                    barColor = palette[1]
                )
                linePercentileTrace(
                    name = "P50",
                    xs = composeVersions,
                    ys = p50th,
                    extendedTextInfo = false,
                    lineWidth = 2.0,
                    lineColor = palette[0],
                    drawLine = false,
                    lineTextPosition = TextPosition.`top left`
                )
                linePercentileTrace(
                    name = "P90",
                    xs = composeVersions,
                    ys = p90th,
                    extendedTextInfo = false,
                    lineWidth = 2.0,
                    lineColor = palette[1],
                    drawLine = false,
                    lineTextPosition = TextPosition.`top right`
                )
//                linePercentileTrace(name = "P95", xs = composeVersions, ys = p95th)

                layout {
                    val plotName =
                        "${displayableDeviceName}. Compose benchmark <b>${benchmarkName}</b>."
                    title = plotName
                    height = PLOT_HEIGHT
                    width = PLOT_WIDTH
                    xaxis {
                        title = "Compose version"
                        autosize = false
                        autotick = false
                    }
                    yaxis {
                        val maxY = requireNotNull(p90th.maxOrNull())
                        range(Value.of(0.0f), Value.of(maxY + maxY / 2))
                        title = "Frametime, ms"
                    }
                }
            }


            val fileName = "${benchmarkName}_summary.svg"
            val svgDir = File(saveDir, "reports${SEP}$deviceNameForDir${SEP}summary")

            if (svgDir.exists().not()) {
                svgDir.mkdirs()
            }
            val savePath = File(svgDir, fileName).toPath()
            plot.export(savePath)
        }
    }

    @OptIn(UnstablePlotlyAPI::class, ExperimentalStdlibApi::class)
    fun printDynamics(
        saveDir: File,
        allDeviceBenchmarkReports: Map<String/*device name*/, List<Pair<String, BenchmarkReport>>>
    ) {
        val reportsDir = File(saveDir, "reports")
        val svgReport = File(reportsDir, "compose_dynamics.svg")
        if (svgReport.exists().not()) {
            svgReport.createNewFile()
        }
        val composeVersions = allDeviceBenchmarkReports.values.map {
            it.map { (compose, _) -> compose }.toSortedSet()
        }.flatten().toSet().toList()

        val dataset = allDeviceBenchmarkReports.values.flatten()
            .groupBy { it.first }
            .mapValues { (_, values) -> values.map { it.second } }
            .mapValues { (_, report) ->
                val avg = report
                    .map { report -> report.benchmarks }
                    .flatten()
                    .map { summary ->
                        val p50 = summary.sampledMetrics.frameCpuTimeMs.P50
                        val p90 = summary.sampledMetrics.frameCpuTimeMs.P90
                        (p50 + p90) / 2.0
                    }
                    .average()
                (16/*ms per frame budget*/ / avg)
            }

        val plot = Plotly.plot {

            val xsRange = (0..composeVersions.lastIndex).toList()
            val xsText = composeVersions
            val ysRange = composeVersions.map { dataset[it]!! }

            // background
            performanceDynamicLine(
                xsRange = xsRange,
                xsText = xsText,
                ysRange = ysRange.map { 1.75 },
                fillColor = Xkcd.LIGHTBLUE,
                fillType = FillType.tozeroy,
                lineMode = ScatterMode.none,
                lineDash = Dash.solid,
                lineColor = Xkcd.BLACK
            )
            // values line
            performanceDynamicLine(
                xsRange = xsRange,
                xsText = xsText,
                ysRange = ysRange,
                fillColor = Xkcd.GREEN_TEAL,
                fillType = FillType.tozeroy,
                lineMode = ScatterMode.lines,
                lineDash = Dash.solid,
                lineColor = Xkcd.WHITE,
                lineWidth = 3.0
            )

            xsRange.forEachIndexed { idx, xVal ->
                scatter {
                    mode = ScatterMode.lines
                    line {
                        dash = Dash.dot
                        width = 0.75
                        color(Xkcd.DARK_GREY)
                    }
                    x.numbers = listOf(xVal, xVal)
                    x.strings = listOf(xsText[idx], xsText[idx])
                    y.numbers = listOf(ysRange[idx], 1.0f)
                }
            }
            // values dots
            performanceDynamicLine(
                xsRange = xsRange,
                xsText = xsText,
                ysRange = ysRange,
                fillColor = Xkcd.GREEN_TEAL,
                fillType = FillType.none,
                lineMode = ScatterMode.markers,
                lineDash = Dash.solid,
                lineColor = Xkcd.WHITE,
                lineWidth = 3.0
            )

            performanceDynamicLine(
                xsRange = xsRange,
                xsText = xsText,
                ysRange = ysRange.map { 1.0 },
                fillColor = "",
                fillType = FillType.none,
                ScatterMode.`lines+text`,
                lineDash = Dash.dot,
                lineColor = Xkcd.ALMOST_BLACK,
                lineWidth = 0.95,
                lineLabel = "Good"
            )


            layout {
                title = "Jetpack Compose dynamics"
                width = 1024
                height = 512
                showlegend = false

                xaxis {
                    title = "Compose version"
                    autotick = true
                    showgrid = false
                }
                yaxis {
                    autotick = true
                    automargin = false
                    showgrid = false
                    linewidth = 1
                    linecolor(Xkcd.BLACK)
                    val minY = 0.0f
                    val maxY = 1.75f
                    range(Value.of(minY), Value.of(maxY))
                    tickmode = TickMode.array
                    title = "Performance index"
                    ticktext = listOf(Value.of("Low"), Value.of(""), Value.of("High"))
                    tickvals = listOf(Value.of(0), Value.of(1), Value.of(1.75))
                }
            }
        }
        plot.export(svgReport.toPath())
    }

    private fun Plot.performanceDynamicLine(
        xsRange: List<Int>,
        xsText: List<String>,
        ysRange: List<Double>,
        fillColor: String,
        fillType: FillType,
        lineMode: ScatterMode,
        lineDash: Dash,
        lineColor: String,
        lineWidth: Double = 1.0,
        lineLabel: String = ""
    ) {
        scatter {
            mode = lineMode
            x.numbers = xsRange
            x.strings = xsText
            y.numbers = ysRange

            if (fillType != FillType.none) {
                fill = fillType
                fillcolor(fillColor)
            }

            text(lineLabel)
            textposition = TextPosition.`top right`

            line {
                color(lineColor)
                dash = lineDash
                shape = LineShape.spline
                width = lineWidth
            }
            marker {
                symbol = Symbol.circle
                size = 10
                color(lineColor)
            }
            showlegend = true
            name = "P90"
        }
    }

    private fun Plot.barPercentileTrace(
        name: String,
        xs: List<String>,
        ys: List<Float>,
        barWidth: Int = -1,
        barColor: String? = null
    ) {
        val trace = Bar {
            x.numbers = xs.mapIndexed { idx, _ -> idx }
            x.strings = xs
            y.numbers = ys
            text.strings = ys.map { String.format("%.1f", it) }
            textposition = TextPosition.auto
            hoverinfo = "none"
            showlegend = true
            this.name = name

            if (barWidth != -1) {
                width = barWidth
            }
            if (barColor != null) {
                marker {
                    color(barColor)
                }
            }
        }
        addTrace(trace)
    }

    private fun Plot.linePercentileTrace(
        name: String,
        xs: List<String>,
        ys: List<Float>,
        lineTextPosition: TextPosition,
        extendedTextInfo: Boolean = true,
        lineColor: String? = null,
        lineWidth: Double = 3.0,
        drawLine: Boolean = true,
        showLegend: Boolean = false
    ) {
        val fpFormatter: (Float) -> String = { it.toInt().toString() }
        val perfIncrease = ys.mapIndexed { idx: Int, y: Float ->
            if (idx < 1) {
                0.0f
            } else {
                val prevY = ys[idx - 1]
                var perfInc = 0.0f
                if (abs(y - prevY) >= 3.0f /*ms*/) {
                    // performance increase formula (old-new)/new x 100%
                    perfInc = (prevY - y) / y * 100.0f

                }
                perfInc
            }
        }

        val texts = ys.mapIndexed { idx, y ->
            val perfInc = perfIncrease[idx]
            if (idx < 1 || perfInc == 0.0f) {
                if (extendedTextInfo) fpFormatter(y) else ""
            } else {
                val symbol = if (perfInc < 0.0f) "↗+" else "↘-"
                if (extendedTextInfo) {
                    "${fpFormatter(y)}($symbol${fpFormatter(abs(perfInc))}%)"
                } else {
                    "$symbol${fpFormatter(abs(perfInc))}%"
                }
            }
        }

        val textColors = perfIncrease.map { value ->
            when {
                value > 0.0f -> Xkcd.GREEN
                value < 0.0f -> Xkcd.RED
                else -> Xkcd.BLACK
            }
        }

        val trace = Scatter {
            val style = if (drawLine) ScatterMode.`lines+markers+text` else ScatterMode.text
            mode = style
            x.numbers = xs.mapIndexed { idx, _ -> idx }
            x.strings = xs
            y.numbers = ys
            text.strings = texts
            textposition = lineTextPosition
            hoverinfo = "none"
            showlegend = showLegend
            textfont {
                size = 13
                colors(textColors)
            }
            if (lineColor != null) {
                fillcolor(lineColor)
            }
            line {
                width = lineWidth
                if (lineColor != null) {
                    color(lineColor)
                }
                dash = Dash.dash
            }
            this.name = name
            marker {
                size = 8.0f
                symbol = Symbol.`square-cross`
            }
        }
        addTrace(trace)
    }

    private fun Plot.frameBars(run: Int, frames: List<Float>) {
        bar {
            name = "$run"
            x.set((0..frames.lastIndex).toList())
            y.set(frames)
            legendgroup = "run"
            showlegend = true
            offset = offset
            width = 1.1f
            opacity = 1.0f
            line {
                color(palette[1])
            }
            marker {
                color(palette[1])
                this.opacity = 1.0f
            }
        }
    }

    private fun Plot.fpsLine30(maxX: Int) = fpsLine("rgb(102,187,106)", 16.7f, maxX, "60fps")
    private fun Plot.fpsLine60(maxX: Int) = fpsLine("rgb(255,167,38)", 33.3f, maxX, "30fps")
    private fun Plot.fpsLine90(maxX: Int) = fpsLine("rgb(0,176,255)", 11.1f, maxX, "90fps")
    private fun Plot.fpsLine120(maxX: Int) = fpsLine("rgb(118,255,3)", 8.3f, maxX, "120fps")

    private fun Plot.fpsLine(rgb: String, frameTime: Float, maxFrames: Int, title: String) {
        scatter {
            mode = ScatterMode.`lines+text`
            name = title
            textposition = TextPosition.`top right`
            text("${frameTime}ms(${title})")

            x.set(listOf(0f, maxFrames))
            y.set(listOf(frameTime, frameTime))
            line {
                width = 2.7f
                color(rgb)
            }
            legendgroup = "fps"
            showlegend = false
        }
    }

    fun List<Double>.median() = this.sorted().let { (it[it.size / 2] + it[(it.size - 1) / 2]) / 2 }

    companion object {
        private const val PLOT_WIDTH = 1024
        private const val PLOT_HEIGHT = 600
        private val SEP = File.separator
        private val palette = listOf<String>(
            Xkcd.GREEN_TEAL,
            Xkcd.LIGHTBLUE,
            T10.GREEN,
            T10.RED,
            T10.PURPLE,
            T10.BROWN,
            T10.PINK,
            T10.GRAY,
            T10.OLIVE,
            T10.CYAN,
            Xkcd.CLOUDY_BLUE,
            Xkcd.DARK_PASTEL_GREEN,
            Xkcd.DUST,
            Xkcd.ELECTRIC_LIME,
            Xkcd.FRESH_GREEN,
            Xkcd.LIGHT_EGGPLANT,
            Xkcd.NASTY_GREEN,
            Xkcd.REALLY_LIGHT_BLUE,
            Xkcd.TEA,
            Xkcd.WARM_PURPLE,
            Xkcd.YELLOWISH_TAN,
            Xkcd.CEMENT,
            Xkcd.DARK_GRASS_GREEN,
            Xkcd.DUSTY_TEAL,
            Xkcd.GREY_TEAL,
            Xkcd.MACARONI_AND_CHEESE,
            Xkcd.PINKISH_TAN,
            Xkcd.SPRUCE,
            Xkcd.STRONG_BLUE,
            Xkcd.TOXIC_GREEN,
            Xkcd.WINDOWS_BLUE,
            Xkcd.BLUE_BLUE,
            Xkcd.BLUE_WITH_A_HINT_OF_PURPLE,
            Xkcd.BOOGER,
            Xkcd.BRIGHT_SEA_GREEN,
            Xkcd.DARK_GREEN_BLUE,
            Xkcd.DEEP_TURQUOISE,
            T10.BLUE
        )
    }
}