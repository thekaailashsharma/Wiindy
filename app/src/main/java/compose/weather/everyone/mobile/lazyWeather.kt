package compose.weather.everyone.mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import compose.weather.everyone.R
import compose.weather.everyone.ui.theme.White
import compose.weather.everyone.ui.theme.lightBackground
import kotlinx.coroutines.launch

@Composable
fun LazyWeather() {
    val lazyState = rememberLazyListState(initialFirstVisibleItemIndex = 3)
    var curren by remember {
        mutableStateOf(true)
    }
    val coroutineScope = rememberCoroutineScope()
//    LaunchedEffect(Unit) {
//        lazyState.animateScrollBy(1000f)
//    }
    LazyRow(content = {
        items(count = 7) {
    val height = LocalConfiguration.current.screenHeightDp
    Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(vertical = 30.dp)) {

        Card(
            modifier = Modifier
                .padding(15.dp)
                .width(260.dp)
                .height((height / 3).dp)
                .align(Alignment.Center),
            colors = CardDefaults.cardColors(Color(0xFFFB7AFF)),
            elevation = CardDefaults.outlinedCardElevation(15.dp),
            shape = RoundedCornerShape(75.dp)
        ) {

            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = compose.weather.everyone.R.drawable.backk),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    alpha = 0.5f
                )

//                    Column {
//                        Text(
//                            text = "Hello World",
//                            fontSize = 35.sp,
//                            textAlign = TextAlign.Center,
//                        )
//                    }
            }


        }
        Box(contentAlignment = Alignment.TopCenter, modifier = Modifier
            .fillMaxWidth()
            .offset(y = -((height / 3) / 2 + 2).dp)) {
            Card(
                colors = CardDefaults.cardColors(Color(0xFFfffdff)),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier.alpha(0.85f)
            ) {
                Text(
                    text = " Day $it ",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }
        }
        Box(contentAlignment = Alignment.TopCenter, modifier = Modifier
            .fillMaxWidth()
            .offset(y = ((height / 3) / 2 + 2).dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rainy),
                    contentDescription = "",
                    modifier = Modifier.size(160.dp)
                )
            }
        }

    }
        }
    },  state = lazyState)

}