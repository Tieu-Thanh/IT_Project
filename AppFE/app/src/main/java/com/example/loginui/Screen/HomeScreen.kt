package com.example.loginui.Screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Groups2
import androidx.compose.material.icons.rounded.Mail
import androidx.compose.material.icons.rounded.Memory
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.loginui.FunctionSection
import com.example.loginui.ui.theme.DarkSpecStart
import com.example.loginui.ui.theme.Milk
import com.example.loginui.ui.theme.PurpleEnd
import com.example.loginui.ui.theme.PurpleSpecEnd
import com.example.loginui.ui.theme.PurpleSpecStart
import com.example.loginui.ui.theme.PurpleStart
import com.example.loginui.ui.theme.interFontFamily


@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {


        }
    ) { padding ->
        Box(modifier = Modifier.padding())
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintLayout {
            val (topImg, profile) = createRefs()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(245.dp)
                    .constrainAs(topImg) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .background(
                        brush = getGradient(PurpleStart, PurpleEnd),
                        shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)
                    ),
            )
            Row(
                modifier = Modifier
                    .padding(top = 48.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth()

            ) {
                Column(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(start = 14.dp)
                        .weight(0.7f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "HOME",
                        fontFamily = interFontFamily,
                        fontWeight = FontWeight.Bold,

                        color = Milk,
                        fontSize = 30.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "ROLE MODEL",
                        fontFamily = interFontFamily,
                        fontSize = 35.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        color = Milk,

                        )
                }
                Icon(
                    modifier = Modifier
                        .size(100.dp),
                    imageVector = Icons.Rounded.Memory,
                    contentDescription = "Logo",
                    tint = DarkSpecStart

                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                    .shadow(3.dp, shape = RoundedCornerShape(20.dp))
                    .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                    .constrainAs(profile) {
                        top.linkTo(topImg.bottom)
                        bottom.linkTo(topImg.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Column(
                    modifier = Modifier
                        .clickable {
                            navController.navigate("News")
                        }
                        .padding(top = 12.dp, bottom = 12.dp, end = 8.dp, start = 8.dp)
                        .height(90.dp)
                        .width(90.dp)
                        .background(
                            brush = getGradient(PurpleSpecStart, PurpleSpecEnd),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {
                    Icon(
                        imageVector = Icons.Rounded.Newspaper,
                        contentDescription = "What's new",
                        modifier = Modifier
                            .size(50.dp),

                        tint = Milk
                    )
                    Text(
                        text = "What's new",
                        color = Milk,
                        fontWeight = FontWeight.SemiBold,
                        )
                }
                Column(
                    modifier = Modifier
                        .clickable {
                            navController.navigate("Your model")
                        }
                        .padding(top = 12.dp, bottom = 12.dp, end = 8.dp, start = 8.dp)
                        .height(90.dp)
                        .width(90.dp)
                        .background(
                            brush = getGradient(PurpleSpecStart, PurpleSpecEnd),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {
                    Icon(
                        imageVector = Icons.Rounded.Mail,
                        contentDescription = "E-mail us",
                        modifier = Modifier
                            .size(50.dp),

                        tint = Milk
                    )
                    Text(
                        text = "Contact us",
                        color = Milk,
                        fontWeight = FontWeight.SemiBold,


                        )

                }
                Column(
                    modifier = Modifier
                        .clickable {
                            navController.navigate("Your model")
                        }
                        .padding(top = 12.dp, bottom = 12.dp, end = 8.dp, start = 8.dp)
                        .height(90.dp)
                        .width(90.dp)
                        .background(
                            brush = getGradient(PurpleSpecStart, PurpleSpecEnd),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {
                    Icon(
                        imageVector = Icons.Rounded.Groups2,
                        contentDescription = "About us",
                        modifier = Modifier
                            .size(50.dp),

                        tint = Milk
                    )
                    Text(
                        text = "About us",
                        color = Milk,
                        fontWeight = FontWeight.SemiBold,
                        )
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 8.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()

        ) {
            Column(
                modifier = Modifier
                    .padding(start = 14.dp)
                    .weight(0.7f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "The function",
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 30.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

            }

        }
        Row(
            modifier = Modifier
                .padding(top = 8.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()

        ) {
            Column(
                modifier = Modifier
                    .padding(start = 14.dp)
                    .weight(0.7f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {


                Spacer(modifier = Modifier.height(8.dp))

            }

        }
        FunctionSection(navController)

    }
}
