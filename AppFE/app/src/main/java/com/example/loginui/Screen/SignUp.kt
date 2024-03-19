package com.example.loginui.Screen

import android.Manifest
import android.os.Build
import androidx.compose.ui.text.input.VisualTransformation
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Mail
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.example.loginui.MainActivity
import com.example.loginui.R
import com.example.loginui.data.User
import com.example.loginui.navigation.repo
import com.example.loginui.ui.theme.TextColor1
import com.example.loginui.ui.theme.WhiteColor
import com.example.loginui.ui.theme.interFontFamily
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun SignUp(navController: NavHostController) {
    val context = LocalContext.current
    Column(
        Modifier
            .background(color = WhiteColor)
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val passwordVisibility = remember { mutableStateOf(false) }
        val retypeVisibility = remember { mutableStateOf(false) }


        Image(
            painter = painterResource(id = R.drawable.top_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.height(150.dp)
        )
        Text(
            "Sign Up",
            fontFamily = interFontFamily,
            fontSize = 30.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            color = TextColor1,
        )
        var user by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var pass by remember { mutableStateOf("") }
        var repass by remember { mutableStateOf("") }

        OutlinedTextField(
            value = user, { text -> user = text },

            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),

            textStyle = TextStyle(
                textAlign = TextAlign.Start,
                color = Color.Black,
                fontSize = 14.sp
            ),
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = TextColor1,
                unfocusedBorderColor = TextColor1,
                disabledBorderColor = TextColor1,
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            label = {
                Text(text = "Username", color = TextColor1, fontFamily = interFontFamily)
            },
            placeholder = {
                Text(text = "Username", color = TextColor1, fontFamily = interFontFamily)
            },
        )

        OutlinedTextField(
            value = email, { text -> email = text },

            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),

            textStyle = TextStyle(
                textAlign = TextAlign.Start,
                color = Color.Black,
                fontSize = 14.sp
            ),
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = TextColor1,
                unfocusedBorderColor = TextColor1,
                disabledBorderColor = TextColor1,
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Mail,
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            label = {
                Text(text = "E-mail", color = TextColor1, fontFamily = interFontFamily)
            },
            placeholder = {
                Text(text = "E-mail", color = TextColor1, fontFamily = interFontFamily)
            },
        )

        OutlinedTextField(
            value = pass, { text -> pass = text },

            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),

            textStyle = TextStyle(
                textAlign = TextAlign.Start,
                color = Color.Black,
                fontSize = 14.sp
            ),
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = TextColor1,
                unfocusedBorderColor = TextColor1,
                disabledBorderColor = TextColor1,
            ),
            trailingIcon = {
                val image = if (passwordVisibility.value)
                    Icons.Rounded.Visibility
                else
                    Icons.Rounded.VisibilityOff

                Icon(
                    imageVector = image,
                    contentDescription = "Toggle Password Visibility",
                    tint = Color.Black,
                    modifier = Modifier.clickable { passwordVisibility.value = !passwordVisibility.value }
                )
            },
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            label = {
                Text(text = "Password", color = TextColor1, fontFamily = interFontFamily)
            },
            placeholder = {
                Text(text = "Password", color = TextColor1, fontFamily = interFontFamily)
            },

        )

        OutlinedTextField(

            value = repass, { text -> repass = text },
            trailingIcon = {
                val image = if (retypeVisibility.value)
                    Icons.Rounded.Visibility
                else
                    Icons.Rounded.VisibilityOff

                Icon(
                    imageVector = image,
                    contentDescription = "Toggle Password Visibility",
                    tint = Color.Black,
                    modifier = Modifier.clickable { retypeVisibility.value = !retypeVisibility.value }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),

            textStyle = TextStyle(
                textAlign = TextAlign.Start,
                color = Color.Black,
                fontSize = 14.sp
            ),
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = TextColor1,
                unfocusedBorderColor = TextColor1,
                disabledBorderColor = TextColor1,
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            visualTransformation = if (retypeVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = {
                Text(text = "Retype", color = TextColor1, fontFamily = interFontFamily)
            },
            placeholder = {
                Text(text = "Retype your password", color = TextColor1, fontFamily = interFontFamily)
            },
        )

        Button(
            onClick = {
                    repo.signup(email, pass) {code, idToken ->
                        when (code){
                            200 -> {
                                Toast.makeText(context, "Sign Up Success", Toast.LENGTH_SHORT).show()
                                repo.createStorage(User(idToken)) {
                                    if (it) {
                                        repo.updateCurrentUser(idToken)
                                        navController.navigate("HomeScreen")
                                    }
                                }
                            }
                            400 -> {
                                Toast.makeText(context, "Email already exists", Toast.LENGTH_SHORT).show()
                            }
                            405 -> {
                                Toast.makeText(context, "Storage Create Failed", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                Toast.makeText(context, "Sign Up Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } }, Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(TextColor1),
            shape = RoundedCornerShape(50)

        ) {
            Text(
                text = "Sign Up",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = interFontFamily
            )
        }
        Row {
            Text(
                text = "Already have account ?",
                Modifier.padding(top = 16.dp, bottom = 16.dp),
                fontSize = 14.sp,
                color = Color.Black,
                fontFamily = interFontFamily
            )
            TextButton(
                onClick = { navController.navigate("SignIn") },

            ) {
                Text(
                    text = "click here !",
                    Modifier.padding(top = 8.dp, bottom = 8.dp),
                    fontSize = 14.sp,
                    color = TextColor1,
                    fontFamily = interFontFamily
                )

            }
        }
        Image(
            painter = painterResource(id = R.drawable.bottom_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }
}