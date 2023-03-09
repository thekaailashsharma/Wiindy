package compose.weather.everyone.mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import compose.weather.everyone.R

@Composable
fun BgImage(id: Int = R.drawable.background) {
    Image(
        painter = painterResource(id = id),
        contentDescription = "background",
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.80f),
        contentScale = ContentScale.FillBounds
    )
}
