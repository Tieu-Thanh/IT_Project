package com.example.loginui.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Mail
import androidx.compose.material.icons.rounded.Person
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.loginui.R
import com.example.loginui.ui.theme.TextColor1
import com.example.loginui.ui.theme.WhiteColor

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun SignUp(navController: NavHostController) {
    Column(
        Modifier
            .background(color = WhiteColor)
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                Text(text = "Username", color = TextColor1)
            },
            placeholder = {
                Text(text = "Username", color = TextColor1)
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
                Text(text = "E-mail", color = TextColor1)
            },
            placeholder = {
                Text(text = "E-mail", color = TextColor1)
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
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            label = {
                Text(text = "Password", color = TextColor1)
            },
            placeholder = {
                Text(text = "Password", color = TextColor1)
            },
        )

        OutlinedTextField(
            value = repass, { text -> repass = text },

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
            label = {
                Text(text = "Retype", color = TextColor1)
            },
            placeholder = {
                Text(text = "Retype your password", color = TextColor1)
            },
        )
        Button(
            onClick = {navController.navigate("SignIn")}, Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(TextColor1),
            shape = RoundedCornerShape(50)

        ) {
            Text(
                text = "Sign Up",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

        }
        Row {
            Text(
                text = "Already have account ?",
                Modifier.padding(top = 16.dp, bottom = 16.dp),
                fontSize = 14.sp,
                color = Color.Black,
            )
            TextButton(
                onClick = { navController.navigate("SignIn") },

            ) {
                Text(

                    text = "click here !",
                    Modifier.padding(top = 8.dp, bottom = 8.dp),
                    fontSize = 14.sp,
                    color = TextColor1,
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