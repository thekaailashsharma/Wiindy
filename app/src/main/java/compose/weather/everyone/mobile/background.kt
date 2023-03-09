package compose.weather.everyone.mobile

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import compose.weather.everyone.mobile.retrofit.NetworkResult
import compose.weather.everyone.mobile.retrofit.WeatherViewModel

@Composable
fun HomePage(viewModel: WeatherViewModel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF000000))) {

        Column(modifier = Modifier
            .fillMaxHeight(0.90f)
            .background(Color(0xFF000000))) {
            Box1(viewModel)
            var sunriseTranslate by remember { mutableStateOf(false) }
            var dropDownDownRequest by remember {
                mutableStateOf(false)
            }
            var play by remember {
                mutableStateOf(true)
            }
            val dropDownContent = listOf("7 days Forecast", "Sunrise & Sunset", "Today's Highlight")
            var currentDropDownText by remember {
                mutableStateOf(dropDownContent[0])
            }
            val context = LocalContext.current
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = currentDropDownText,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 0.dp, top = 5.dp, bottom = 5.dp)
                        .clickable {
                            viewModel.getWeather()
                            viewModel.weatherResponse.observe(
                                context as LifecycleOwner
                            ){ response ->
                                when(response){
                                    is NetworkResult.Success<*> -> {
                                        response.data?.let {
//                                            var city = "default"
//                                            val list = it.features
//                                            for (i in list){
//                                                 city = i.properties.address_line1
//                                            }
                                            Toast.makeText(
                                                context,
                                                it.current_weather.windspeed.toString(),
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                            println("latitude = ${it.latitude}, longitude = ${it.longitude}")


                                        }


                                    }

                                }
                            }

                        },
                    fontSize = 20.sp
                )
                Card(backgroundColor = Color.Black, elevation = 5.dp) {
                    IconButton(onClick = {
                        play = !play
                        dropDownDownRequest = true
                    }) {
                        val compnotify by rememberLottieComposition(
                            spec = LottieCompositionSpec.Asset("dropdown.json"))
                        val progress by animateLottieCompositionAsState(compnotify)
                        LottieAnimation(
                            composition = compnotify,
                            iterations = 1,
                            isPlaying = play,
                            contentScale = ContentScale.Crop,
                            speed = 1.45f,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(end = 10.dp, top = 0.dp, start = 10.dp)
                        )
                        if (progress == 1.0f) play = true
                    }


                    DropdownMenu(
                        expanded = dropDownDownRequest,
                        onDismissRequest = { dropDownDownRequest = false },
                        modifier = Modifier
                            .background(Color.Black),
                    ) {


                        dropDownContent.forEach {
                            DropdownMenuItem(onClick = {
                                currentDropDownText = it
                                dropDownDownRequest = false
                                if (it == "Sunrise & Sunset") sunriseTranslate = true
                                else if (it == "7 days Forecast") sunriseTranslate = false
                            }) {
                                Card(modifier = Modifier.fillMaxWidth(),
                                    backgroundColor = Color.Black,
                                    elevation = 20.dp
                                ) {
                                    Text(
                                        text = it,
                                        color = Color.White,
                                        modifier = Modifier,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }

                    }
                }
            }

            Box2(rotated = sunriseTranslate)



        }

            Box(contentAlignment = Alignment.BottomCenter) {
                Card(
                    modifier = Modifier
                        .padding(start = 29.dp, top = 5.dp, bottom = 5.dp, end = 29.dp)
                        .fillMaxHeight(1f),
                    shape = RoundedCornerShape(15.dp),
                    elevation = 30.dp
                ) {
                    BgImage()

                }
        }

    }
}