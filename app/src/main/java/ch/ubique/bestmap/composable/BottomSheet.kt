package ch.ubique.bestmap.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
	sheetState: SheetState,
	innerPadding: PaddingValues,
	onDismissRequest: () -> Unit,
	onBottomSheetExpanded: (Float) -> Unit,
	skimColor: Color = Color.Transparent,
	content: @Composable BoxScope.() -> Unit
) {
	val screenHeight = with(LocalDensity.current) { LocalConfiguration.current.screenHeightDp.dp.toPx() }

	// Calculate the height of the bottom sheet based on its fraction
	LaunchedEffect(sheetState.currentValue) {
		if (sheetState.currentValue == SheetValue.Expanded) {
			onBottomSheetExpanded(screenHeight - sheetState.requireOffset())
		}
	}
	ModalBottomSheet(
		onDismissRequest = onDismissRequest,
		sheetState = sheetState,
		scrimColor = skimColor,
		sheetMaxWidth = 400.dp,
		containerColor = Color.White,
		contentWindowInsets = { WindowInsets(0, 0, 0, 0) }
	) {
		Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
			content()
		}
	}
}