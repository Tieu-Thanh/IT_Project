package com.example.loginui.Screen


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.loginui.R
import com.example.loginui.data.authen.SignInRequest
import com.example.loginui.data.authen.SignInResponse
import com.example.loginui.navigation.repo
import com.example.loginui.navigation.user
import com.example.loginui.ui.theme.Milk
import com.example.loginui.ui.theme.TextColor1
import com.example.loginui.ui.theme.WhiteColor
import com.example.loginui.ui.theme.interFontFamily
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignIn(navController: NavHostController) {
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
            "Sign In",
            fontFamily = interFontFamily,
            fontSize = 30.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            color = TextColor1,
        )
        var email by remember { mutableStateOf("test.user@gmail.com") }
        var password by remember { mutableStateOf("021002ht") }
        var passwordVisible by rememberSaveable() { mutableStateOf(false) }
        val context = LocalContext.current

        OutlinedTextField(
            value = email, { text -> email = text },

            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),

//                shape = RoundedCornerShape(50),
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
                Text(text = "E-mail", color = TextColor1, )
            },
            placeholder = {
                Text(text = "E-mail", color = TextColor1,)

            },
        )
        OutlinedTextField(
            value = password, { text -> password = text },
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),

            shape = RoundedCornerShape(50),
            textStyle = TextStyle(

                textAlign = TextAlign.Start,
                color = Color.Black,
                fontSize = 14.sp
            ),
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
                Text(text = "Password", color = TextColor1, fontFamily = interFontFamily)
            },
            placeholder = {
                Text(text = "", color = TextColor1)
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {

                val image =
                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }

            }
        )
        Button(
            onClick = {
                repo.signIn(email, password) {
                    if (it) {
                        user = email
                        repo.updateCurrentUser(user)
                        navController.navigate("HomeScreen")
                        Toast.makeText(context, "Login Success", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show()
                    }
                }
            }, Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(TextColor1),
            shape = RoundedCornerShape(50)

        ) {
            Text(
                text = "Sign In",
                fontFamily = interFontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Milk
            )
        }
        Row {


            Text(
                text = "Don't have an account ? ",
                Modifier.padding(top = 16.dp, bottom = 16.dp),
                fontSize = 14.sp,
                color = Color.Black,
                fontFamily = interFontFamily
            )
            TextButton(onClick = { navController.navigate("SignUp") }) {
                Text(
                    text = "click here !",
                    Modifier.padding(top = 8.dp, bottom = 8.dp),
                    fontSize = 14.sp,
                    color = TextColor1,
                    fontFamily = interFontFamily
                )
            }
        }
        Row(Modifier.padding(top = 16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "",
                Modifier.padding(8.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.twitter),
                contentDescription = "",
                Modifier.padding(8.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.facebook),
                contentDescription = "",
                Modifier.padding(8.dp)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.bottom_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds

        )

    }
}
