package ch.ubique.bestmap.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.ubique.bestmap.R
import ch.ubique.bestmap.ui.theme.BestMapTheme
import ch.ubique.bestmap.ui.theme.pinIcons

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreatePoiBottomSheetContent(
	onNewIconChanged: (iconName: String?) -> Unit,
	onSaveClicked: (title: String, icon: String?) -> Unit,
) {
	Column(horizontalAlignment = Alignment.CenterHorizontally) {
		var text by rememberSaveable { mutableStateOf("") }

		TextField(
			value = text,
			onValueChange = {
				text = it
			},
			textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
			label = { Text(stringResource(R.string.create_poi_name_label)) },
			colors = TextFieldDefaults.colors(
				unfocusedContainerColor = colorResource(id = R.color.black_08),
				focusedContainerColor = colorResource(id = R.color.black_08)
			),
			modifier = Modifier
				.fillMaxWidth()
				.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
		)

		var selectedIconChip by rememberSaveable { mutableStateOf<String?>(null) }

		LaunchedEffect(selectedIconChip) {
			onNewIconChanged(selectedIconChip)
		}

		FlowRow(
			modifier = Modifier
				.padding(horizontal = 12.dp)
				.fillMaxWidth(), horizontalArrangement = Arrangement.Center
		) {
			pinIcons.forEach {
				ElevatedFilterChip(
					selected = selectedIconChip == it.name,
					onClick = {
						selectedIconChip = it.name
					},
					label = { Icon(painterResource(id = it.drawableId), contentDescription = null) },
					colors = FilterChipDefaults.filterChipColors(
						containerColor = colorResource(id = R.color.black_04),
						selectedContainerColor = colorResource(id = R.color.black_16)
					),
					modifier = Modifier.padding(horizontal = 4.dp)
				)
			}
		}

		Button(
			onClick = {
				onSaveClicked(text, selectedIconChip)
			},
			enabled = text.isNotEmpty(),
			modifier = Modifier
				.padding(16.dp)
				.fillMaxWidth()
		) {
			Text(stringResource(R.string.save_button))
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CreatePoiBottomSheetContentPreview() {
	BestMapTheme {
		BottomSheet(
			sheetState = SheetState(true, Density(LocalContext.current), initialValue = SheetValue.Expanded),
			PaddingValues(),
			{},
			{},
			Color.LightGray
		) {
			CreatePoiBottomSheetContent({}, { _, _ -> })
		}
	}
}