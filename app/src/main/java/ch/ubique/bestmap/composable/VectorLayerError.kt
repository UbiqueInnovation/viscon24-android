package ch.ubique.bestmap.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.ubique.bestmap.R


@Composable
fun VectorLayerError(onRetryClicked: () -> Unit) {
	Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
		Text(
			text = stringResource(id = R.string.error_vector_layer),
			fontSize = 20.sp,
			color = colorResource(id = R.color.dark_red),
			modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
		)
		IconButton(
			onClick = onRetryClicked,
			modifier = Modifier
				.padding(top = 16.dp)
				.border(width = 1.dp, color = colorResource(R.color.dark_red), shape = CircleShape)
				.background(color = Color.White, shape = CircleShape)
				.align(alignment = Alignment.CenterHorizontally)
		) {
			Icon(
				painter = painterResource(id = R.drawable.ic_retry),
				tint = colorResource(R.color.dark_red),
				contentDescription = null
			)
		}
	}
}

@Preview
@Composable
fun VectorLayerErrorPreview() {
	VectorLayerError({})
}