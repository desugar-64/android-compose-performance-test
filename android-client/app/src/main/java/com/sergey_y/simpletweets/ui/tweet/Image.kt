package com.sergey_y.simpletweets.ui.tweet

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.PixelSize
import com.sergey_y.simpletweets.R
import com.sergey_y.simpletweets.ui.theme.SimpleTweetsTheme

const val LOW_IMAGE_QUALITY = 0.5f
const val HIGH_IMAGE_QUALITY = 0.8f

private val imageCorners = RoundedCornerShape(4.dp)
private val startCorners = RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)
private val endCorners = RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)
private val topStartCorner = RoundedCornerShape(topStart = 4.dp)
private val topEndCorner = RoundedCornerShape(topEnd = 4.dp)
private val bottomStartCorner = RoundedCornerShape(bottomStart = 4.dp)
private val bottomEndCorner = RoundedCornerShape(bottomEnd = 4.dp)

/*
Mono image layout:
--------
|      |
|  A   |
|      |
--------
*/
@ExperimentalCoilApi
@Composable
fun MonoImage(imageUrl: String, imageHeight: Dp, compactMode: Boolean) {
    var imgSize by remember { mutableStateOf(IntSize.Zero) }
    SimpleTweetImage(
        modifier = Modifier
            .height(imageHeight)
            .fillMaxWidth()
            .onGloballyPositioned { imgSize = it.size },
        clipShape = imageCorners,
        imgUrl = imageUrl,
        imgSize = imgSize,
        compactMode = compactMode
    )
}

/*
Duo image layout:
--------
|   |   |
| A | B |
|   |   |
--------
*/
@ExperimentalCoilApi
@Composable
fun DuoImage(
    imgA: String,
    imgB: String,
    imageHeight: Dp,
    compactMode: Boolean
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        var imgSize by remember {
            mutableStateOf(IntSize.Zero)
        }
        // Image A

        SimpleTweetImage(
            modifier = Modifier
                .height(imageHeight)
                .weight(1.0f)
                .onGloballyPositioned { imgSize = it.size },
            clipShape = startCorners,
            imgUrl = imgA,
            imgSize = imgSize,
            compactMode = compactMode
        )
        Spacer(modifier = Modifier.width(4.dp))
        // Image B
        SimpleTweetImage(
            modifier = Modifier
                .height(imageHeight)
                .weight(1.0f),
            clipShape = endCorners,
            imgUrl = imgB,
            imgSize = imgSize,
            compactMode = compactMode
        )
    }
}

/*
Triple image layout:
--------
|   | B |
| A |-- |
|   | C |
--------
*/
@ExperimentalCoilApi
@Composable
fun TripleImage(imageHeight: Dp, imgA: String, imgB: String, imgC: String, compactMode: Boolean) {
    Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        var imgASize by remember { mutableStateOf(IntSize.Zero) }
        // Image A
        SimpleTweetImage(
            modifier = Modifier
                .height(imageHeight)
                .weight(1.0f)
                .onGloballyPositioned { imgASize = it.size },
            clipShape = startCorners,
            imgUrl = imgA,
            imgSize = imgASize,
            compactMode = compactMode
        )
        Spacer(modifier = Modifier.width(4.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1.0f)
        ) {
            var imgBCSize by remember { mutableStateOf(IntSize.Zero) }
            // Image B
            SimpleTweetImage(
                modifier = Modifier
                    .height((imageHeight / 2) - 2.dp)
                    .fillMaxWidth()
                    .onGloballyPositioned { imgBCSize = it.size },
                clipShape = topEndCorner,
                imgUrl = imgB,
                imgSize = imgBCSize,
                compactMode = compactMode
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Image C
            SimpleTweetImage(
                modifier = Modifier
                    .height((imageHeight / 2) - 2.dp)
                    .fillMaxWidth(),
                clipShape = bottomEndCorner,
                imgUrl = imgC,
                imgSize = imgBCSize,
                compactMode = compactMode
            )
        }

    }
}

/*
Triple image layout:
--------
| A | B |
| --|-- |
| C | D |
--------
*/
@ExperimentalCoilApi
@Composable
fun QuadImage(
    imageHeight: Dp,
    imgA: String,
    imgB: String,
    imgC: String,
    imgD: String,
    compactMode: Boolean
) {
    // static 2x2 grid
    val cols = 2
    var imgSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    Layout(
        modifier = Modifier
            .height(imageHeight)
            .fillMaxWidth(),
        content = {
            SimpleTweetImage(
                modifier = Modifier.padding(end = 2.dp, bottom = 2.dp),
                clipShape = topStartCorner,
                imgUrl = imgA,
                imgSize = imgSize,
                compactMode = compactMode
            )
            SimpleTweetImage(
                modifier = Modifier.padding(start = 2.dp, bottom = 2.dp),
                clipShape = topEndCorner,
                imgUrl = imgB,
                imgSize = imgSize,
                compactMode = compactMode
            )
            SimpleTweetImage(
                modifier = Modifier.padding(top = 2.dp, end = 2.dp),
                clipShape = bottomStartCorner,
                imgUrl = imgC,
                imgSize = imgSize,
                compactMode = compactMode
            )
            SimpleTweetImage(
                modifier = Modifier.padding(top = 2.dp, start = 2.dp),
                clipShape = bottomEndCorner,
                imgUrl = imgD,
                imgSize = imgSize,
                compactMode = compactMode
            )
        }
    ) { measurables, constraints ->
        val rows = (measurables.size / cols) + measurables.size % cols
        val childWidth = constraints.maxWidth / cols
        val childHeight = constraints.maxHeight / rows

        imgSize = IntSize(childWidth, childHeight)

        val children = measurables.map { child ->
            child.measure(
                constraints.copy(
                    minWidth = childWidth,
                    minHeight = childHeight,
                    maxWidth = childWidth,
                    maxHeight = childHeight
                )
            )
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            children.forEachIndexed { idx, child ->
                val row = idx / cols
                val col = idx - (row * cols)

                val offset = IntOffset(
                    x = col * child.measuredWidth,
                    y = row * child.measuredHeight
                )
                child.placeRelative(offset)
            }
        }
    }
}


@ExperimentalCoilApi
@Composable
private fun SimpleTweetImage(
    modifier: Modifier,
    clipShape: Shape,
    imgUrl: String,
    imgSize: IntSize,
    compactMode: Boolean
) {
    val isImgSizeKnown = imgSize != IntSize.Zero && imgSize.width > 0 && imgSize.height > 0
    Image(
        modifier = modifier.clip(clipShape),
        painter = rememberImagePainter(imgUrl.takeIf { isImgSizeKnown }) {
            if (isImgSizeKnown) {
                setImageSize(imgSize.width, imgSize.height, compactMode)
            }
        },
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

/*
Image row layout:
-------------------
| A | B | C | ... |
-------------------
*/
@ExperimentalCoilApi
@Composable
fun ImageRow(
    imageHeight: Dp,
    images: List<String>,
    compactMode: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(state = rememberScrollState())
    ) {
        val imageSizePx = with(LocalDensity.current) {
            imageHeight.roundToPx()
        }
        images.forEachIndexed { idx, image ->
            val clipper = when(idx) {
                0 -> startCorners
                images.lastIndex -> endCorners
                else -> RectangleShape
            }
            SimpleTweetImage(
                modifier = Modifier.size(imageHeight),
                clipShape = clipper,
                imgUrl = image,
                imgSize = IntSize(imageSizePx, imageSizePx),
                compactMode = compactMode
            )
            if (idx < images.lastIndex) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}


private fun ImageRequest.Builder.setImageSize(width: Int, height: Int, compactMode: Boolean) {
    val qualityReducer = if (compactMode) LOW_IMAGE_QUALITY else HIGH_IMAGE_QUALITY
    size(
        PixelSize(
            (width * qualityReducer).toInt(),
            (height * qualityReducer).toInt()
        )
    )
}

@OptIn(ExperimentalCoilApi::class)
@Preview
@Composable
private fun MonoPreview() {
    SimpleTweetsTheme {
        MonoImage(imageUrl = "", imageHeight = 150.dp, compactMode = false)
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview
@Composable
private fun DuoPreview() {
    SimpleTweetsTheme {
        DuoImage(imgA = "", imgB = "", imageHeight = 150.dp, compactMode = false)
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview
@Composable
private fun TriplePreview() {
    SimpleTweetsTheme {
        TripleImage(imgA = "", imgB = "",  imgC = "", imageHeight = 150.dp, compactMode = false)
    }
}







