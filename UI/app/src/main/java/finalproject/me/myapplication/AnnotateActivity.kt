package finalproject.me.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
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
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import finalproject.me.myapplication.ui.theme.MyApplicationTheme


class AnnotateActivity : ComponentActivity() {
    private val TAG = "AnnotateActivity"
    private val imageResource = R.drawable.people
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
    @Preview
    @Composable
    fun Annotate() {
        // Replace R.drawable.your_image_resource with the actual resource ID of your image
        val rectangle = remember {
            mutableStateOf(Rectangle(Offset(0f, 0f), Offset(0f, 0f)))
        }
        var click = false
        var point1 = Offset(0f, 0f)
        var point2:Offset
        val rectangleList = remember {
            mutableStateListOf<Rectangle>()
        }
        val clearRect = remember {
            mutableStateOf(false)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .pointerInput(Unit) {
                    awaitEachGesture {
                        awaitFirstDown()
                        do {
                            val event = awaitPointerEvent()
                            if (!clearRect.value) {
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
                                    else {
                                        rectangleList.add(rectangle.value)
                                        rectangleList.removeAt(rectangleList.size-1)
                                    }
                                }
                            } else {
                                if (event.type == PointerEventType.Release) {
                                    rectangleList
                                        .toList()
                                        .filter {
                                            it.topLeft.x < event.changes.first().position.x && it.topLeft.y < event.changes.first().position.y && it.bottomRight.x > event.changes.first().position.x && it.bottomRight.y > event.changes.first().position.y
                                        }
                                        .forEach {
                                            rectangleList.remove(it)
                                        }
                                    if (rectangle.value.topLeft.x < event.changes.first().position.x && rectangle.value.topLeft.y < event.changes.first().position.y && rectangle.value.bottomRight.x > event.changes.first().position.x && rectangle.value.bottomRight.y > event.changes.first().position.y) {
                                        rectangle.value = Rectangle(Offset(0f, 0f), Offset(0f, 0f))
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
            )
            Button(
                onClick = {
                          clearRect.value = false
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Icon(imageVector = Icons.Default.AddCircle, contentDescription = null)
                Text("Annotate")
            }
            Button(
                onClick = {
                    rectangleList.clear()
                    rectangle.value = Rectangle(Offset(0f, 0f), Offset(0f, 0f))
                },
                modifier = Modifier.align(Alignment.BottomStart)
            ) {
                Icon(imageVector = Icons.Default.AddCircle, contentDescription = null)
                Text("Clear")
            }
            Button(
                onClick = {
                    clearRect.value = true
                },
                modifier = Modifier.align(Alignment.BottomEnd)
            ){
                Icon(imageVector = Icons.Default.AddCircle, contentDescription = null)
                Text("Del")
            }
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
            ) {
                drawBoundingBox(
                    color = Color.Red,
                    rectangle = rectangle.value
                )
                drawBoundingBoxes(
                    color = Color.Red,
                    rectangles = rectangleList)
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