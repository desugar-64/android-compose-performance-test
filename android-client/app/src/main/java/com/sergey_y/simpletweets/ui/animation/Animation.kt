package com.sergey_y.simpletweets.ui.animation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.util.*
import kotlin.math.roundToInt
import kotlin.random.Random

const val ANIMATION_TIMEOUT = 7500L

@Stable
private class Particle(
    id: String,
    offset: Offset,
    vector: Offset,
    color: Color
) {
    val id: String = id
    var offset: Offset by mutableStateOf(offset)
    var vector: Offset = vector
    var color: Color = color

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Particle

        if (offset != other.offset) return false
        if (vector != other.vector) return false
        if (color != other.color) return false

        return true
    }

    override fun hashCode(): Int {
        var result = offset.hashCode()
        result = 31 * result + vector.hashCode()
        result = 31 * result + color.hashCode()
        return result
    }
}

enum class AnimationType {
    CANVAS, LAYOUT_OFFSET, LAYOUT_GRAPHIC_LAYER, CUSTOM_LAYOUT
}

private val particleSize = 24.dp
private val movementSpeed = 1.dp

@Composable
fun BaseAnimation(particleCount: Int, animationType: AnimationType) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val widthPx = with(LocalDensity.current) { maxWidth.roundToPx() }
        val heightPx = with(LocalDensity.current) { maxHeight.roundToPx() }
        val particleSizePx = with(LocalDensity.current) { particleSize.roundToPx() }
        val speedPx = with(LocalDensity.current) { movementSpeed.roundToPx() }

        val particles by produceState<List<Particle>>(initialValue = emptyList()) {
            val p = withContext(Dispatchers.Default) {
                val rnd = { Random(System.nanoTime()) }
                generateSequence {
                    val sign = { if (rnd().nextBoolean()) 1f else -1f }
                    Particle(
                        id = UUID.randomUUID().toString(),
                        offset = Offset(
                            x = rnd().nextInt(1, widthPx - particleSizePx - 1).toFloat(),
                            y = rnd().nextInt(1, heightPx - particleSizePx - 1).toFloat()
                        ),
                        vector = Offset(
                            x = speedPx * sign(),
                            y = speedPx * sign()
                        ),
                        color = Color(
                            red = rnd().nextFloat(),
                            green = rnd().nextFloat(),
                            blue = rnd().nextFloat(),
                        )
                    )
                }.take(particleCount).toList()
            }
            value = p
        }

        if (particles.isNotEmpty()) {
            LaunchedEffect(Unit) {
                withTimeout(ANIMATION_TIMEOUT) {
                    while (true) {
                        withFrameNanos {
                            for (particle in particles) {
                                val x = particle.offset.x
                                val y = particle.offset.y
                                val dx = particle.vector.x
                                val dy = particle.vector.y
                                val newDx =
                                    if (x < 0 || x + particleSizePx > widthPx) {
                                        -dx
                                    } else {
                                        dx
                                    }
                                val newDy =
                                    if (y < 0 || y + particleSizePx > heightPx) {
                                        -dy
                                    } else {
                                        dy
                                    }

                                particle.vector = Offset(newDx, newDy)
                                particle.offset += particle.vector
                            }
                        }

                    }

                }
            }

            when (animationType) {
                AnimationType.CANVAS -> ParticleCanvasAnimation(
                    particles = particles,
                    particleSizePx = particleSizePx,
                    canvasWidth = maxWidth,
                    canvasHeight = maxHeight
                )
                AnimationType.LAYOUT_OFFSET -> ParticleOffsetAnimation(
                    particles = particles,
                    particleSize = particleSize,
                    width = maxWidth,
                    height = maxHeight
                )
                AnimationType.LAYOUT_GRAPHIC_LAYER -> ParticleLayoutLayerAnimation(
                    particles = particles,
                    particleSize = particleSize,
                    width = maxWidth,
                    height = maxHeight
                )
                AnimationType.CUSTOM_LAYOUT -> ParticleCustomLayout(
                    particles = particles,
                    particleSize = particleSizePx,
                    width = maxWidth,
                    height = maxHeight
                )
            }
        }
    }
}

@Composable
private fun ParticleCustomLayout(
    particles: List<Particle>,
    particleSize: Int,
    width: Dp,
    height: Dp
) {
    Layout(
        content = {
            particles.forEach {
                key(it.id) {
                    Box(Modifier.background(it.color, CircleShape))
                }
            }
        },
        modifier = Modifier
            .size(width, height)
            .semantics {
                contentDescription = "particle_custom_layout"
            }
    ) { measurables, constraints ->
        val placeables =
            measurables.map { it.measure(Constraints.fixed(particleSize, particleSize)) }

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEachIndexed { idx, placeable ->
                val xy = particles[idx].offset
                placeable.place(
                    x = xy.x.toInt(),
                    y = xy.y.toInt()
                )
            }
        }
    }
}

@Composable
private fun ParticleCanvasAnimation(
    particles: List<Particle>,
    particleSizePx: Int,
    canvasWidth: Dp,
    canvasHeight: Dp
) {
    Canvas(modifier = Modifier
        .size(canvasWidth, canvasHeight)
        .semantics {
            contentDescription = "canvas"
        }) {
        val particleRadius = particleSizePx / 2.0f
        particles.forEach { particle ->
            drawCircle(
                color = particle.color,
                center = particle.offset + Offset(particleRadius, particleRadius),
                radius = particleRadius
            )
        }
    }
}

@Composable
private fun ParticleOffsetAnimation(
    particles: List<Particle>,
    particleSize: Dp,
    width: Dp,
    height: Dp
) {

    Box(modifier = Modifier
        .size(width, height)
        .semantics {
            contentDescription = "layout_offset"
        }) {
        particles.forEach { particle ->
            key(particle.id) {
                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                particle.offset.x.roundToInt(),
                                particle.offset.y.roundToInt()
                            )
                        }
                        .size(particleSize)
                        .background(particle.color, CircleShape)
                )
            }
        }
    }
}

@Composable
private fun ParticleLayoutLayerAnimation(
    particles: List<Particle>,
    particleSize: Dp,
    width: Dp,
    height: Dp
) {

    Box(modifier = Modifier
        .size(width, height)
        .semantics {
            contentDescription = "layout_layer"
        }) {
        particles.forEach { particle ->
            key(particle.id) {
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            translationX = particle.offset.x
                            translationY = particle.offset.y
                        }
                        .size(particleSize)
                        .background(particle.color, CircleShape)
                )
            }
        }
    }
}