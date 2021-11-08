package com.sergey_y.simpletweets.ui.animation

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import kotlin.random.Random

private val rnd: Random = Random(2)
private val shape = RoundedCornerShape(3.dp)

private const val MAX_LEVEL = 4

private data class Node<T>(
    val value: T,
    val children: List<Node<T>>
)

@Immutable
private data class MosaicTile(
    val color: Color,
    val width: Dp,
    val height: Dp,
    val offset: Offset
)

@OptIn(ExperimentalStdlibApi::class)
private fun generateTree(
    levels: Int,
    width: Dp,
    height: Dp,
    offset: Offset,
    density: Density
): Node<MosaicTile> {
    return Node(
        value = MosaicTile(
            color = Color(
                red = rnd.nextFloat(),
                green = rnd.nextFloat(),
                blue = rnd.nextFloat()
            ),
            width = width,
            height = height,
            offset = offset
        ),
        children = buildList {
            if (levels > 0) {
                val subLevels = if (rnd.nextBoolean()) levels - 1 else 0
                val subWidth = width / 2
                val subHeight = height / 2
                val subWidthPx = with(density) { subWidth.roundToPx() }
                val subHeightPx = with(density) { subHeight.roundToPx() }
                for (row in 0 until 2) {
                    for (col in 0 until 2) {
                        add(
                            generateTree(
                                levels = subLevels,
                                width = subWidth,
                                height = subHeight,
                                offset = Offset(
                                    x = offset.x + (subWidthPx * col),
                                    y = offset.y + (subHeightPx * row)
                                ),
                                density = density
                            )
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun MosaicLayout() {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val largestTileWidth = maxWidth / 3
        val largestTileHeight = if (maxWidth == maxHeight) largestTileWidth else maxHeight / 6

        val largestTileWidthPx = with(LocalDensity.current) { largestTileWidth.roundToPx() }
        val largestTileHeightPx = with(LocalDensity.current) { largestTileHeight.roundToPx() }

        val density = LocalDensity.current

        val forest: List<MosaicTile> by produceState(initialValue = emptyList()) {
            val forest = with(Dispatchers.Default) {
                val rows = (maxHeight / largestTileHeight).toInt()
                val cols = (maxWidth / largestTileWidth).toInt()
                val forest = mutableListOf<Node<MosaicTile>>()
                for (row in 0 until rows) {
                    for (col in 0 until cols) {
                        val levels = rnd.nextInt(0, MAX_LEVEL)
                        val tree = generateTree(
                            levels = levels,
                            width = largestTileWidth,
                            height = largestTileHeight,
                            offset = Offset(
                                x = (largestTileWidthPx * col).toFloat(),
                                y = (largestTileHeightPx * row).toFloat()
                            ),
                            density = density
                        )
                        forest.add(tree)
                    }
                }
                forest.traverse()
            }
            value = forest
        }

        if (forest.isNotEmpty()) {
            Layout(
                content = {
                    forest.forEach { mosaicTile ->
                        Tile(tile = mosaicTile)
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .semantics { contentDescription = "mosaic_layout" }
            ) { measurables, constraints ->
                val tiles = measurables.mapIndexed { idx, measurable ->
                    val tile = forest[idx]
                    measurable.measure(
                        Constraints.fixed(
                            tile.width.roundToPx(),
                            tile.height.roundToPx()
                        )
                    )
                }
                layout(constraints.maxWidth, constraints.maxHeight) {
                    tiles.forEachIndexed { idx, tile ->
                        val xy = forest[idx].offset
                        tile.place(
                            x = xy.x.toInt(),
                            y = xy.y.toInt()
                        )
                    }
                }
            }
        }

    }
}

private enum class TileFlip { BACK, FRONT }

@Composable
private fun Tile(tile: MosaicTile) {
    val backColor = remember {
        Color(red = rnd.nextFloat(), green = rnd.nextFloat(), blue = rnd.nextFloat())
    }
    var currentState by remember {
        mutableStateOf(TileFlip.FRONT)
    }
    val transition = updateTransition(currentState, "flip_transition")

    val rotation by transition.animateFloat(
        label = "flip_angle",
        transitionSpec = { tween(1000) }
    ) {
        when (it) {
            TileFlip.BACK -> 0.0f
            TileFlip.FRONT -> 180.0f
        }
    }
    val color = if (rotation < 90.0f) backColor else tile.color
    Box(
        modifier = Modifier
            .graphicsLayer {
                val distance = max(tile.height, tile.width)
                cameraDistance = distance.toPx()
                rotationX = rotation
            }
            .padding(2.dp)
            .background(color, shape)
    )

    LaunchedEffect(Unit) {
        withTimeout(ANIMATION_TIMEOUT) {
            while (true) {
                delay(rnd.nextInt(1000, 2000).toLong())
                currentState = when (currentState) {
                    TileFlip.BACK -> TileFlip.FRONT
                    TileFlip.FRONT -> TileFlip.BACK
                }
            }

        }
    }
}

private fun List<Node<MosaicTile>>.traverse(): List<MosaicTile> {
    val list = mutableListOf<MosaicTile>()
    for (node in this) {
        if (node.children.isNotEmpty()) {
            list.addAll(node.children.traverse())
        } else {
            list.add(node.value)
        }
    }
    return list
}