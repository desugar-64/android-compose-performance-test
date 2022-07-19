package com.sergey_y.benchmark

import android.content.Intent
import android.graphics.Point
import android.os.SystemClock
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TweetListBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    private lateinit var device: UiDevice

    @Before
    fun setUp() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        device = UiDevice.getInstance(instrumentation)
    }


    @Test
    fun lazyListScroll() = measureRepeated {
        awaitLayout("tweet_lazy_list")
        awaitComposeIdle()
        val lazyColumn = device.findObject(By.desc("tweet_lazy_list"))
        // Setting a gesture margin is important otherwise gesture nav is triggered.
        lazyColumn.setGestureMargin(device.displayWidth / 4)
        repeat(5) {
            lazyColumn.drag(Point(lazyColumn.visibleCenter.x, lazyColumn.visibleCenter.y / 3))
            awaitComposeIdle()
        }
    }

    @Test
    fun lazyListFling() = measureRepeated {
        val ctx = InstrumentationRegistry.getInstrumentation().context
        val res = ctx.resources
        awaitLayout("tweet_lazy_list")
        awaitComposeIdle()
        val lazyColumn = device.findObject(By.desc("tweet_lazy_list"))
        // Setting a gesture margin is important otherwise gesture nav is triggered.
        lazyColumn.setGestureMargin(device.displayWidth / 4)
        val speed = (DEFAULT_FLING_SPEED * res.displayMetrics.density).toInt()
        while (isNotReachedListBottom()) {
            lazyColumn.fling(Direction.DOWN, speed)
            awaitComposeIdle(5000)
        }

//        while (isNotReachedListTop()) {
//            lazyColumn.fling(Direction.UP, speed)
//            awaitComposeIdle(5000)
//        }
    }

    @Test
    fun itemRecomposition() = measureRepeated(
        setupBlock = { launchMainActivityAndWait(benchmark = "item") }
    ) {
        device.wait(Until.findObject(By.desc("single_item")), 5000)
        awaitComposeIdle()
        while (device.findObject(By.desc("last_item")) == null) {
            awaitComposeIdle(15000)
        }
    }

    @Test
    fun navigateToScreen() = measureRepeated(
        setupBlock = { launchMainActivityAndWait(benchmark = "navigation") }
    ) {
        awaitLayout("tweet_lazy_list")
        awaitComposeIdle()
        val getLazyList = { device.findObject(By.desc("tweet_lazy_list")) }
        val firstTwoItems = getLazyList().findObjects(By.textContains("@")).take(2).map { it.text }
        firstTwoItems.forEach { text ->
            repeat(3) {
                val item = getLazyList().findObject(By.text(text))
                item.setGestureMargin(100)
                item.click()
                awaitComposeIdle()
                device.pressBack()
            }
        }
    }

    private fun awaitLayout(layoutName: String, timeout: Long = 5000) {
        device.wait(Until.findObject(By.desc(layoutName)), timeout)
    }

    @Test
    fun particlesCanvas() = measureRepeated(
        setupBlock = { launchMainActivityAndWait(benchmark = "canvas_particles") }
    ) {
        awaitLayout("canvas")
        awaitComposeIdle(15000)
    }

    @Test
    fun particlesLayoutOffset() = measureRepeated(
        setupBlock = { launchMainActivityAndWait(benchmark = "layout_offset") }
    ) {
        awaitLayout("layout_offset")
        awaitComposeIdle(15000)
    }

    @Test
    fun particlesLayoutLayer() = measureRepeated(
        setupBlock = { launchMainActivityAndWait(benchmark = "layout_graphic_layer") }
    ) {
        awaitLayout("layout_layer")
        awaitComposeIdle(15000)
    }

    @Test
    fun particlesCustomLayout() = measureRepeated(
        setupBlock = { launchMainActivityAndWait(benchmark = "particle_custom_layout") }
    ) {
        awaitLayout("particle_custom_layout")
        awaitComposeIdle(15000)
    }

    @Test
    fun transitionAnimation() = measureRepeated(
        setupBlock = { launchMainActivityAndWait(benchmark = "mosaic_layout") }
    ) {
        awaitLayout("mosaic_layout")
        SystemClock.sleep(1100) // no worries, the benchmark instrumentation runs under a separate process and does not affect the performance metrics of the main process
        awaitComposeIdle(15000)
    }

    @Test
    fun addItemsToColumn() = measureRepeated(
        setupBlock = { launchMainActivityAndWait(benchmark = "add_items") }
    ) {
        awaitLayout("add_items_layout")
        awaitComposeIdle(15000)
    }

    private fun isNotReachedListTop() = device.findObject(By.desc("list_start")) == null

    private fun isNotReachedListBottom() = device.findObject(By.desc("list_end")) == null

    private fun measureRepeated(
        setupBlock: MacrobenchmarkScope.() -> Unit = { launchMainActivityAndWait() },
        measureBlock: MacrobenchmarkScope.() -> Unit
    ) {
        benchmarkRule.measureRepeated(
            packageName = PACKAGE_NAME,
            metrics = listOf(FrameTimingMetric()),
            iterations = ITERATIONS,
            startupMode = StartupMode.WARM,
            compilationMode = CompilationMode.Full(),
            setupBlock = setupBlock,
            measureBlock = measureBlock

        )
    }

    private fun awaitComposeIdle(timeout: Long = 3000) {
        device.wait(Until.findObject(By.desc("COMPOSE-IDLE")), timeout)
    }

    private fun MacrobenchmarkScope.launchMainActivityAndWait(benchmark: String? = null) {
        startActivityAndWait(mainActivityIntent(benchmark))
    }

    companion object {
        private const val PACKAGE_NAME = "com.sergey_y.simpletweets"
        private const val MAIN_ACTIVITY_CLASS = "com.sergey_y.simpletweets.MainActivity"

        private const val ITERATIONS = 2
        private const val WARMUPS = 2
        private const val DEFAULT_FLING_SPEED =
            4200 // 7500 default, see UiObject2#DEFAULT_FLING_SPEED. The default speed sometimes causes test failures in versions of JP Compose prior to 1.1.

        fun mainActivityIntent(benchmark: String?): Intent {

            return Intent().apply {
                if (benchmark == null) {
                    putExtra("benchmarkKey", "list")
                } else {
                    putExtra("benchmarkKey", benchmark)
                }
                setClassName(PACKAGE_NAME, MAIN_ACTIVITY_CLASS)
            }
        }
    }
}
