package com.example.loginui.Screen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.loginui.data.ImageList
import com.example.loginui.navigation.user


val TAG = "ModelInfo"
val usersImages = mutableListOf<ImageList?>()

@Preview
@SuppressLint("MutableCollectionMutableState", "InvalidColorHexValue")
@Composable
fun ModelInfo(){
    var loading by remember {
        mutableStateOf(false)
    }
    var uploadComplete by  remember {
        mutableStateOf("Upload more image to improve the accuracy")
    }
    var isVisible by remember { mutableStateOf(true) }
    val bitmapList by remember { mutableStateOf(mutableStateListOf<Bitmap?>()) }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var selectedImage by remember { mutableStateOf<Bitmap?>(null)}
    val takePicturePreviewLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            imageBitmap = bitmap
        }
    )
    val context = LocalContext.current
    var sizeOfDefaultDataset by remember { mutableStateOf("0") }
    createUsersImages()
    selectedImage?.let {
        if (isVisible){
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .zIndex(1f)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .absoluteOffset(0.dp, 50.dp)
                        .padding(16.dp)
                        .zIndex(2f)
                )
                {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Image",
                        modifier = Modifier
                            .size(400.dp)
                            .shadow(300.dp, ambientColor = Color.Black)
                            .clickable { isVisible = false },
                    )
                    LazyRow {
                        items(itemList) { item ->
                            Button(onClick = {
                                addImageIntoClass(it, item)
                            }) {
                                Text(text = item)
                            }
                        }
                    }
                }
            }
        }
    }
    Column {
        Text(text ="Model Info", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        Text(text = "Email: $user")
        Text(text = "Model: Yolov8")
        Text(text = "Accuracy: ...")
        LazyRow {
            items(itemList){
                    item ->
                Button(onClick = {
                    val classChoose = usersImages.filter {
                        it?.className == item
                    }[0]
                    classChoose.let {
                        val totalImage = it?.imageList?.size?.plus(sizeOfDefaultDataset.toInt())!!
                        if (totalImage > 0){    
                            val totalImageString = totalImage.toString()
                            Toast.makeText(context, "$item's size: $totalImageString", Toast.LENGTH_LONG).show()
                        }
                        else{
                            Toast.makeText(context, "No image in $item", Toast.LENGTH_LONG).show()
                        }
                    }
                }) {
                    Text(text = item)
                }
            }
        }
        OutlinedTextField(
            value = sizeOfDefaultDataset,
            onValueChange = { newText ->
                if (newText.all { it.isDigit() }) {
                    sizeOfDefaultDataset = newText
                }
            },
            label = { Text("Size") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(0.2f)
        )

        Button(onClick = {
            takePicturePreviewLauncher.launch(null)
        }) {
            Icon(imageVector = Icons.Default.Camera, contentDescription = "Camera")
        }
        if (imageBitmap != null){
            bitmapList.add(imageBitmap)
            imageBitmap = null
        }
        Row(modifier = Modifier.size(200.dp)){
            bitmapList.forEach {
                it?.asImageBitmap()?.let { bitmap ->
                    Image(bitmap = bitmap, contentDescription = "Image"
                        ,modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                selectedImage = bitmap.asAndroidBitmap()
                                isVisible = true
                            }
                    )
                }
            }
        }

        Button(onClick = {
            loading = true
        }
        ) {
            Text(text = "Upload")
        }
        Text(text =uploadComplete, color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        if (loading){
            Box(modifier = Modifier.size(200.dp), contentAlignment = Alignment.Center){
                ComposableProcessBar(
                    percentage = 1.0f, number = 100,
                    onAnimationEnd = {
                        if(!it){
                            loading = false
                            uploadComplete = "Upload complete"
                        }
                    }
                )
            }
        }
        Button(
            onClick = { //TODO: Start training model
                },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text("Start Training Your Model")
        }
    }

}
fun createUsersImages(){
    itemList.forEach{
        usersImages.add(ImageList(it, mutableListOf()))
    }
}
fun addImageIntoClass(bitmap: Bitmap,className: String){
    usersImages.filter {
        it?.className == className
    }[0].let { it?.imageList?.add(bitmap) }
}

fun uploadFunction(){

}
    
@Composable
fun ComposableProcessBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 8.dp,
    animationDuration: Int = 1000,
    animDelay: Int = 0,
    onAnimationEnd: (Boolean) -> Unit = {}
){
    var animationPlayed by remember { mutableStateOf(false) }
    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animDelay
        ), label = ""
    )
    LaunchedEffect(currentPercentage.value){
        animationPlayed = true
        if (currentPercentage.value == percentage){
            animationPlayed = false
        }
        onAnimationEnd(animationPlayed)
    }
    Box(
        modifier = Modifier.size(radius * 2f),
    ){
        Canvas(modifier = Modifier.size(radius*2f) ){
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(),cap = StrokeCap.Round)
            )
        }
        Text(text = (currentPercentage.value * number).toInt().toString(),
            fontSize = fontSize,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}