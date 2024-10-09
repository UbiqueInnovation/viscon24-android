package ch.ubique.bestmap.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.ubique.bestmap.R
import ch.ubique.bestmap.extension.roundToDecimals
import ch.ubique.bestmap.repository.model.Pin
import ch.ubique.bestmap.ui.theme.BestMapTheme
import ch.ubique.bestmap.ui.theme.PinIcon
import io.openmobilemaps.mapscore.shared.map.coordinates.Coord
import io.openmobilemaps.mapscore.shared.map.coordinates.CoordinateConversionHelperInterface
import io.openmobilemaps.mapscore.shared.map.coordinates.CoordinateSystemIdentifiers

@Composable
fun PoiBottomSheetContent(pin: Pin, onDeleteButtonClick: () -> Unit) {
	var latLonCoord = pin.coord
	if (!LocalInspectionMode.current) {
		val conversionHelper = CoordinateConversionHelperInterface.independentInstance()
		latLonCoord = conversionHelper.convert(CoordinateSystemIdentifiers.EPSG4326(), pin.coord)
	}
	Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.Center,
			modifier = Modifier.fillMaxWidth()
		) {
			PinIcon.getPinForName(pin.iconName)?.let {
				Icon(
					ImageVector.vectorResource(it.drawableId),
					contentDescription = null
				)
			}
			Text(
				text = pin.title,
				fontSize = 20.sp,
				fontWeight = FontWeight.Bold,
				modifier = Modifier.padding(start = 8.dp)
			)
		}
		Text(
			text = "${latLonCoord.y.roundToDecimals(3)}, ${latLonCoord.x.roundToDecimals(3)}",
			fontSize = 16.sp,
			modifier = Modifier
				.padding(top = 8.dp)
		)
		IconButton(
			onClick = onDeleteButtonClick,
			modifier = Modifier
				.padding(16.dp)
				.border(1.dp, colorResource(R.color.dark_blue), shape = CircleShape)
				.background(color = Color.White, shape = CircleShape)
				.align(Alignment.End)
		) {
			Icon(painterResource(R.drawable.ic_delete), tint = colorResource(R.color.dark_blue), contentDescription = null)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PoiBottomSheetContentPreview() {
	BestMapTheme {
		BottomSheet(
			sheetState = SheetState(true, Density(LocalContext.current), initialValue = SheetValue.Expanded),
			PaddingValues(),
			{},
			{},
			Color.LightGray
		) {
			PoiBottomSheetContent(Pin("-", "Test Title", "favorite", Coord(4326, 0.0, 0.0, 0.0)), {})
		}
	}
}