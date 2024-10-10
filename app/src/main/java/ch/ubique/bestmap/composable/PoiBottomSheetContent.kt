package ch.ubique.bestmap.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import ch.ubique.bestmap.repository.model.Pin
import ch.ubique.bestmap.ui.theme.BestMapTheme
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

	// TODO [POI CONTENT] Show content of the pin and add a button to delete the pin
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