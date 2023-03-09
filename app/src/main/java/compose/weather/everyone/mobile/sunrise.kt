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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import compose.weather.everyone.R

@Composable
fun Card2(translation2:Float) {

    var check by remember {
        mutableStateOf(false)
    }
    check = translation2 == 0f

    val color  = animateColorAsState(targetValue = if (check) Color(0xFFFFEA00)
    else Color.LightGray)
    val float = animateFloatAsState(
        targetValue = if (check) 90f else 0f,
        animationSpec = tween(
            durationMillis = 2500,
            delayMillis = 700,
            easing = LinearOutSlowInEasing
        )
    )
    val width = LocalDensity.current.run {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }
    val height = LocalDensity.current.run {
        LocalConfiguration.current.screenHeightDp.dp.toPx()
    }
    println("canvas width = $width height = $height")
    Card(
        modifier = Modifier
            .padding(start = 29.dp, top = 5.dp, bottom = 5.dp, end = 29.dp)

            .graphicsLayer {
                cameraDistance = 8 * density
                translationX = translation2

                println("Translate = $translationX")
            }

            .fillMaxWidth()
            .fillMaxHeight(0.96f),
        shape = RoundedCornerShape(15.dp),
        elevation = 30.dp
    ) {
        BgImage()

            Canvas(modifier = Modifier
                .size(300.dp)
                .fillMaxHeight(0.5f)
            ) {


                drawArc(
                    startAngle = 180f,
                    color = Color.LightGray,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(
                        x = this.size.width.times(0.075).toFloat(),
                        y = this.size.height.times(0.075).toFloat()
                    ),
                    size = Size(
                        width = (this.size.width.times(0.85f)),
                        height = this.size.height.times(0.85f)
                    ),
                    style = Stroke(
                        width = 15f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(100f, 0f)),
                        cap = StrokeCap.Round
                    )
                )
                drawArc(
                    startAngle = 180f,
                    color = color.value,
                    sweepAngle = float.value,
                    useCenter = false,
                    topLeft = Offset(
                        x = this.size.width.times(0.075).toFloat(),
                        y = this.size.height.times(0.075).toFloat()),
                    size = Size(
                        width = (this.size.width.times(0.85f)),
                        height = this.size.height.times(0.85f)
                    ),
                    style = Stroke(
                        width = 15f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 0f)),
                        cap = StrokeCap.Round,
                    )
                )
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x = 0f, y = this.size.height.times(0.51f)),
                    end = Offset(x = this.size.width, y = this.size.height.times(0.51f)),
                    strokeWidth = 6f
                )
                drawRoundRect(
                    color = Color(0xFFFFEA00),
                    topLeft = Offset(
                        x = this.size.width.times(0.025).toFloat(),
                        y = this.size.height.times(0.5).toFloat()),
                    size = Size(
                        width = 120f,
                        height = 10f
                    ),
                    cornerRadius = CornerRadius(x = 10f, y = 10f)
                )
                drawRoundRect(
                    color = Color(0xFFFFEA00),
                    topLeft = Offset(
                        x = (this.size.width - this.size.width.times(0.13).toFloat()),
                        y = this.size.height.times(0.5).toFloat()),
                    size = Size(
                        width = 130f,
                        height = 10f
                    ),
                    cornerRadius = CornerRadius(x = 10f, y = 10f)
                )


            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "Sunrise",
                    size.width.times(0.035).toFloat(),
                    size.height.times(0.60).toFloat(),
                    android.graphics.Paint().apply {
                        textSize = 35f
                        this.color = Color(0xFFFFEA00).toArgb()
                    }
                )
                drawText(
                    "05:50 AM",
                    size.width.times(0.035).toFloat(),
                    size.height.times(0.72).toFloat(),
                    android.graphics.Paint().apply {
                        textSize = 50f
                        this.color = Color(0xFFFFFFFF).toArgb()
                    }
                )
                drawText(
                    "Sunset",
                    (size.width - size.width.times(0.17).toFloat()),
                    size.height.times(0.60).toFloat(),
                    android.graphics.Paint().apply {
                        textSize = 35f
                        this.color = Color(0xFFFFEA00).toArgb()
                    }
                )
                drawText(
                    "06:30 PM",
                    (size.width - size.width.times(0.27).toFloat()),
                    size.height.times(0.72).toFloat(),
                    android.graphics.Paint().apply {
                        textSize = 50f
                        this.color = Color(0xFFFFFFFF).toArgb()
                    }
                )
                drawText(
                    "06 ",
                    size.width.times(0.36).toFloat(),
                    size.height.times(0.30).toFloat(),
                    android.graphics.Paint().apply {
                        textSize = 85f
                        this.color = Color(0xFFFFFFFF).toArgb()
                    }
                )
                drawText(
                    " : ",
                    size.width.times(0.49).toFloat(),
                    size.height.times(0.30).toFloat(),
                    android.graphics.Paint().apply {
                        textSize = 85f
                        this.color = Color(0xFFFFFFFF).toArgb()
                    }
                )
                drawText(
                    "Hours",
                    size.width.times(0.38).toFloat(),
                    size.height.times(0.35).toFloat(),
                    android.graphics.Paint().apply {
                        textSize = 25f
                        this.color = Color(0xFFFFFFFF).toArgb()
                    }
                )
                drawText(
                    "30",
                    size.width.times(0.58).toFloat(),
                    size.height.times(0.30).toFloat(),
                    android.graphics.Paint().apply {
                        textSize = 85f
                        this.color = Color(0xFFFFFFFF).toArgb()
                    }
                )
                drawText(
                    "Minutes",
                    size.width.times(0.58).toFloat(),
                    size.height.times(0.35).toFloat(),
                    android.graphics.Paint().apply {
                        textSize = 25f
                        this.color = Color(0xFFFFFFFF).toArgb()
                    }
                )
                drawText(
                    "Left",
                    size.width.times(0.49).toFloat(),
                    size.height.times(0.44).toFloat(),
                    android.graphics.Paint().apply {
                        textSize = 35f
                        this.color = Color(0xFFFFFFFF).toArgb()
                    }
                )

            }
            }

        }




        }
