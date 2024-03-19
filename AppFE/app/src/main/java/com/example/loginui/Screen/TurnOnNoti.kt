package com.example.loginui.Screen

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.loginui.ui.theme.Milk
import com.example.loginui.ui.theme.TextColor1
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable

fun PermissionDemo(navController: NavHostController) {


    val notificationPermission = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,

    ) {
        Column (
        ){
            Button(onClick = {
                if (!notificationPermission.status.isGranted) {
                    notificationPermission.launchPermissionRequest()

                } else {
                    Toast.makeText(context, "Permission Given Already", Toast.LENGTH_SHORT).show()
                    navController.navigate("SignIn")
                }
            }, Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(TextColor1),
                shape = RoundedCornerShape(50)
            ) {
                Row (){
                    Text(text = "Turn on your notification", fontSize = 17.sp, color = Milk )

                    Icon(imageVector = Icons.Rounded.NotificationsActive , contentDescription ="Noti")
                }
            }

        }
    }


}