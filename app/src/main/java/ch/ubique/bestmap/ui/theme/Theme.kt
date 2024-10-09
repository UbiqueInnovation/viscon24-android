package ch.ubique.bestmap.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
	primary = DarkBlue,
	secondary = DarkRed,
	tertiary = LightBlue,
	background = Color.White
)

@Composable
fun BestMapTheme(
	content: @Composable () -> Unit
) {
	MaterialTheme(
		colorScheme = LightColorScheme,
		typography = Typography,
		content = content
	)
}