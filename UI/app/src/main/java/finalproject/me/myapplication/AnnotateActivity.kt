package finalproject.me.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateRotation
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import finalproject.me.myapplication.ui.theme.MyApplicationTheme
import kotlin.math.absoluteValue


class AnnotateActivity : ComponentActivity() {
    private val TAG = "AnnotateActivity"
    private val imageResource = R.drawable.people
    private var isZoomable = true
    private val maxScale = 1f
    private val minScale = 3f
    private val isRotation = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Annotate()
                }
            }
        }
    }
    @SuppressLint("MutableCollectionMutableState", "SuspiciousIndentation",
        "UnrememberedMutableState"
    )
    @OptIn(ExperimentalFoundationApi::class)
    @Preview
    @Composable
    fun Annotate() {
        // Replace R.drawable.your_image_resource with the actual resource ID of your image
        val rotationState = remember { mutableStateOf(1f) }
        val scale = remember { mutableStateOf(1f) }
        val offsetX = remember { mutableStateOf(1f) }
        val offsetY = remember { mutableStateOf(1f) }
        val rectangle = remember {
            mutableStateOf(Rectangle(Offset(0f, 0f), Offset(0f, 0f)))
        }
        var point1 = Offset(0f, 0f)
        var point2: Offset
        var click = false
        var imageWidth = 0.dp
        var imageHeight = 0.dp
        val rectangleList = remember{
            mutableStateListOf<Rectangle>()
        }
        val density = LocalDensity.current
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)

                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { /* NADA :) */ },
                    onDoubleClick = {
                        if (scale.value >= 2f) {
                            scale.value = 1f
                            offsetX.value = 1f
                            offsetY.value = 1f
                        } else scale.value = 3f
                    },
                )
                .pointerInput(Unit) {
                    awaitEachGesture {
                        awaitFirstDown()
                        do {
                            val event = awaitPointerEvent()
                            if (isZoomable) {
                                scale.value *= event.calculateZoom()
                                if (scale.value > 1) {
                                    val offset = event.calculatePan()
                                    offsetX.value += offset.x
                                    offsetY.value += offset.y
                                    rotationState.value += event.calculateRotation()
                                } else {
                                    scale.value = 1f
                                    offsetX.value = 1f
                                    offsetY.value = 1f
                                }
                            } else {
                                if (!click) {
                                    point1 = event.changes.first().position
                                    click = true
                                } else {
                                    point2 = event.changes.first().position
                                    rectangle.value = Rectangle(point1, point2)
                                    if (event.type == PointerEventType.Release) {
                                        rectangleList.add(rectangle.value)
                                        click = false
                                    }
                                }
                            }
                        } while (event.changes.any { it.pressed })
                    }
                }
        ) {
            // Use the Image composable to display the image
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null, // Content description for accessibility (can be null if decorative)
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f) // Displaying the image on half of the screen height
                    .clip(shape = MaterialTheme.shapes.medium) // Optional: Clip the image with a rounded shape
                    .align(Alignment.Center)
                    .onGloballyPositioned { coordinates ->
                        imageWidth = with(density) {
                            coordinates.size.width.toDp()
                        }
                        imageHeight = with(density) {
                            coordinates.size.height.toDp()
                        }
                    }
                    .graphicsLayer {
                        if (isZoomable) {
                            scaleX = maxOf(maxScale, minOf(minScale, scale.value))
                            scaleY = maxOf(maxScale, minOf(minScale, scale.value))
                            if (isRotation) {
                                rotationZ = rotationState.value
                            }
                            if (offsetX.value > 0) {
                                offsetX.value = minOf(offsetX.value, scaleX * imageWidth.value)
                            } else {
                                offsetX.value = maxOf(offsetX.value, -scaleX * imageWidth.value)
                            }
                            if (offsetY.value > 0) {
                                offsetY.value = minOf(offsetY.value, scaleY * imageHeight.value)
                            } else {
                                offsetY.value = maxOf(offsetY.value, -scaleY * imageHeight.value)
                            }
                            translationX = offsetX.value
                            translationY = offsetY.value
                        }
                    }
            )
            Button(
                onClick = {
                    isZoomable = !isZoomable
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Icon(imageVector = Icons.Default.AddCircle, contentDescription = null)
                Text("Annotate")
            }
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
            ) {
                drawBoundingBoxes(
                    color = Color.Red,
                    rectangles = rectangleList)
                drawBoundingBox(
                    color = Color.Red,
                    rectangle = rectangle.value
                )
            }
        }
    }
    private fun DrawScope.drawBoundingBox(
        color: Color,
        rectangle: Rectangle
    ) {
        drawRect(
            color = color,
            topLeft = rectangle.topLeft,
            style = Stroke(width = 5.dp.toPx()),
            size = Size(
                width = (rectangle.bottomRight.x - rectangle.topLeft.x),
                height = (rectangle.bottomRight.y - rectangle.topLeft.y),
            )
        )
    }
    private fun DrawScope.drawBoundingBoxes(
        color: Color,
        rectangles: List<Rectangle>
    ) {
        rectangles.forEach { rectangle ->
            drawBoundingBox(
                color = color,
                rectangle = rectangle
            )
        }
    }
}


data class Rectangle(val topLeft: Offset, val bottomRight: Offset)