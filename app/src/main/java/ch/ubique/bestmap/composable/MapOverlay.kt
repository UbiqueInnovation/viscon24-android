package ch.ubique.bestmap.composable

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.ubique.bestmap.MainViewModel
import ch.ubique.bestmap.R
import ch.ubique.bestmap.extension.hasLocationPermission
import ch.ubique.bestmap.map.BestMapView
import ch.ubique.ubdiag.composables.mainActivity
import io.openmobilemaps.mapscore.shared.map.coordinates.Coord

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapOverlay(
	modifier: Modifier,
	mapView: BestMapView?,
	innerPadding: PaddingValues,
) {
	val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

	/*
	TODO [GPS]
	Box(
		modifier = modifier.padding(
			PaddingValues(0.dp, innerPadding.calculateTopPadding(), 0.dp, innerPadding.calculateBottomPadding())
		)
	) {
		GpsButton(mapView)
	}
	 */

	val onBottomSheetExpanded: (Coord, Float) -> Unit = { coordinates, bottomPadding ->
		if (bottomPadding > 0f) {
			mapView?.getCamera()?.setPaddingBottom(bottomPadding)
			coordinates.let {
				mapView?.mapInterface?.getCamera()?.moveToCenterPosition(
					centerPosition = it,
					animated = true
				)
			}
		}
	}

	val mainViewModel: MainViewModel = viewModel<MainViewModel>(mainActivity())
	val longPressCoordinate = mainViewModel.longPressCoordinates.collectAsState()

	longPressCoordinate.value?.let { pinCoordinate ->
		// TODO [ADD NEW POI] clear long press coordinates and save pin without icon to database
		/*
		TODO [NEW POI SHEET]
		BottomSheet(
			sheetState,
			innerPadding,
			{
				mainViewModel.clearLongPressCoordinates()
				mainViewModel.clearNewPin()
			},
			{ f -> onBottomSheetExpanded(pinCoordinate, f) }) {
			CreatePoiBottomSheetContent(
				{ tempIcon ->
					// TODO [NEW POI SHEET] propagate modified icon details
				},
				{ title, icon ->
					// TODO [NEW POI SHEET] clear long press coordinates, save pin to database and clear temporary pin state
				})
		}
		 */
	}

	val selectedPin = mainViewModel.selectedPin.collectAsState(null)
	selectedPin.value?.let { pin ->
		BottomSheet(sheetState,
			innerPadding,
			{ mainViewModel.onClickIcons(emptyList()) },
			{ f -> onBottomSheetExpanded(pin.coord, f) }) {
			PoiBottomSheetContent(pin) {
				// TODO [POI CONTENT] remove pin from database
			}
		}
	}
}

@Composable
private fun BoxScope.GpsButton(mapView: BestMapView?) {
	val context = LocalContext.current
	val launcher = rememberLauncherForActivityResult(
		ActivityResultContracts.RequestPermission()
	) { isGranted: Boolean ->
		if (isGranted) {
			mapView?.notifyLocationPermissionGranted()
		}
	}

	FloatingActionButton(
		onClick = {
			if (context.hasLocationPermission()) {
				// TODO [GPS] move camera to current location
			} else {
				launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
			}
		},
		containerColor = Color.White,
		modifier = Modifier
			.align(Alignment.TopEnd)
			.padding(16.dp)
	) {
		Icon(
			ImageVector.vectorResource(R.drawable.ic_gps_location),
			contentDescription = null
		)
	}
}

@Preview
@Composable
fun MapOverlayPreview() {
	MapOverlay(
		modifier = Modifier,
		mapView = null,
		innerPadding = PaddingValues(32.dp)
	)
}