package compose.weather.everyone.mobile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import compose.weather.everyone.R
import compose.weather.everyone.mobile.retrofit.NetworkResult
import compose.weather.everyone.mobile.retrofit.WeatherViewModel

@Composable
fun Box1(viewModel: WeatherViewModel) {
    var play by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(Unit) {
        viewModel.getGeo()
    }
    var currentLocation by remember {
        mutableStateOf("Not able to Fetch")
    }
    val context = LocalContext.current
    viewModel.geoResponse.observe(
        context as LifecycleOwner
    ) { response ->
        when (response) {
            is NetworkResult.Success<*> -> {
                response.data?.let {
                    var city = "default"
                    val list = it.features
                    for (i in list) {
                        city = i.properties.city + i.properties.address_line2
                    }
                    currentLocation = city

                }


            }

        }
    }

    Box {
        Card(
            modifier = Modifier
                .padding(21.dp)
                .fillMaxHeight(0.4f),
            shape = RoundedCornerShape(15.dp),
            elevation = 30.dp
        ) {

            BgImage()
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.rainy),
                        contentDescription = "Rainy",
                        modifier = Modifier
                            .size(115.dp)
                            .padding(start = 10.dp, top = 30.dp),
                        tint = Color.Unspecified
                    )
                    val compnotify by rememberLottieComposition(
                        spec = LottieCompositionSpec.Asset("search.json"))
                    val progress by animateLottieCompositionAsState(compnotify)
                    LottieAnimation(
                        composition = compnotify,
                        iterations = 1,
                        isPlaying = play,
                        contentScale = ContentScale.Crop,
                        speed = 1.45f,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(end = 10.dp, top = 30.dp)
                    )
                    if (progress == 1.0f) play = true
                }
                Text(
                    text = buildAnnotatedString {
                        append("28")
                        withStyle(
                            style = SpanStyle(
                                baselineShift = BaselineShift.Superscript,
                                fontSize = 20.sp,
                            )
                        ) {
                            append("o")
                            withStyle(
                                style = SpanStyle(
                                    baselineShift = BaselineShift.Subscript,
                                    fontSize = 28.sp,
                                )
                            ) {
                                append("C")
                            }
                        }

                    },
                    color = Color.White,
                    fontSize = 45.sp,
                    modifier = Modifier.padding(start = 28.dp, top = 15.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, top = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        Icons.Outlined.LocationOn,
                        contentDescription = "Location",
                        tint = Color.White
                    )
                    Text(
                        text = currentLocation,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, top = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        Icons.Outlined.CalendarMonth,
                        contentDescription = "Location",
                        tint = Color.White
                    )
                    Text(
                        text = "24th July 2022 5:01 AM",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }

    }
}