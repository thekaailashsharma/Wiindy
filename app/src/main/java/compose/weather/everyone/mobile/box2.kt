package compose.weather.everyone.mobile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.translationMatrix
import compose.weather.everyone.R

@Composable
fun Box2(rotated:Boolean) {
    val days = mutableListOf(
        "Sun",
        "Mon",
        "Tue",
        "Wed",
        "Thu",
        "Fri",
        "Sat"
    )
    val date = mutableListOf(
        "25th July",
        "26th July",
        "27th July",
        "28th July",
        "29th July",
        "30th July",
        "31st July",
    )
    Box {
        val width = LocalDensity.current.run {
            LocalConfiguration.current.screenWidthDp.dp.toPx()
        }

        val rotation by animateFloatAsState(
            targetValue = if (rotated) 180f else 0f,
            animationSpec = tween(500)
        )
        val translation by animateFloatAsState(
            targetValue = if (rotated) (width) else 0f,
            animationSpec = tween(850)
        )
        val translation2 by animateFloatAsState(
            targetValue = if (rotated) (0f) else width,
            animationSpec = tween(850)
        )



        Card(
            modifier = Modifier
                .padding(start = 29.dp, top = 5.dp, bottom = 5.dp, end = 29.dp)

                .graphicsLayer {
                    cameraDistance = 8 * density
//                    translationX = translation

                    println("Translate = $translationX")
                }
                .fillMaxHeight(0.96f),
            shape = RoundedCornerShape(15.dp),
            elevation = 30.dp
        ) {
            BgImage()

            val listState = rememberLazyListState(
                Int.MAX_VALUE / 2
            )

            LazyColumn(content = {
                items(count = Int.MAX_VALUE, itemContent = {
                    val index = it % days.size
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Icon(
                            painter = painterResource(
                                id = compose.weather.everyone.R.drawable.rainy),
                            contentDescription = "cloudy",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .customPadding()
                                .padding(top = 15.dp)
                                .size(50.dp)
                        )
                        Text(
                            text = buildAnnotatedString {
                                append("28")
                                withStyle(
                                    style = SpanStyle(
                                        baselineShift = BaselineShift.Superscript,
                                        fontSize = 14.sp,
                                    )
                                ) {
                                    append("o")
                                    withStyle(
                                        style = SpanStyle(
                                            baselineShift = BaselineShift.Subscript,
                                            fontSize = 19.sp,
                                        )
                                    ) {
                                        append("C")
                                    }
                                }

                            },
                            fontSize = 25.sp,
                            color = Color.White,
                            modifier = Modifier
                                .customPadding()
                                .padding(top = 15.dp)
                        )
                        Text(
                            text = date[index],
                            color = Color.White,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .customPadding()
                                .padding(top = 25.dp)
                        )

                        Text(
                            text = days[index],
                            color = Color.White,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .customPadding()
                                .padding(top = 25.dp)
                        )
                    }
                })

            }, state = listState)
            BoxWithConstraints(
                contentAlignment = Alignment.BottomCenter,
            ) {
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(25.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(0.2f),
                    shape = RoundedCornerShape(15.dp),
                    elevation = 25.dp,
                    backgroundColor = Color(0xFF26282E)
                ) {

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.rainy),
                            contentDescription = "cloudy",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .customPadding()
                                .padding(top = 15.dp)
                                .size(50.dp)
                        )
                        Column(
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Text(
                                text = "Tomorrow",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.SansSerif,
                                modifier = Modifier
                                    .padding(top = 7.dp)
                            )
                            Text(
                                text = buildAnnotatedString {
                                    append("28")
                                    withStyle(
                                        style = SpanStyle(
                                            baselineShift = BaselineShift.Superscript,
                                            fontSize = 14.sp,
                                        )
                                    ) {
                                        append("o")
                                        withStyle(
                                            style = SpanStyle(
                                                baselineShift = BaselineShift.Subscript,
                                                fontSize = 17.sp,
                                            )
                                        ) {
                                            append("C")
                                        }
                                    }

                                },
                                fontSize = 20.sp,
                                color = Color.White,
                                modifier = Modifier
                            )
                        }
                        Text(
                            text = "Thunder Storm !!",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontFamily = FontFamily.SansSerif,
                            modifier = Modifier
                                .customPadding()
                                .padding(top = 17.dp)
                        )


                    }

                }

            }
            }
        Card2(translation2 = translation2)



    }
}

fun Modifier.customPadding(): Modifier =
    padding(
        start = 16.dp,
        end = 10.dp,
        bottom = 15.dp
    )

