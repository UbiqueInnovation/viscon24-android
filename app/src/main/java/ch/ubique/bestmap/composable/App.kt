package ch.ubique.bestmap.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.isVisible
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.ubique.bestmap.MainViewModel
import ch.ubique.bestmap.map.BestMapView
import ch.ubique.bestmap.map.util.BestMapClickListener
import ch.ubique.bestmap.map.util.VectorLayerState
import ch.ubique.ubdiag.composables.mainActivity
import io.openmobilemaps.mapscore.map.view.MapViewState
import io.openmobilemaps.mapscore.shared.map.coordinates.Coord
import io.openmobilemaps.mapscore.shared.map.coordinates.CoordinateSystemIdentifiers

@Composable
fun App(innerPadding: PaddingValues) {
	val mapView = remember { mutableStateOf<BestMapView?>(null) }
	val mapViewState = remember(mapView.value) { mapView.value?.mapViewState }?.collectAsState()
	val cameraPositionInitialized = remember { mutableStateOf(false) }
	val mainViewModel: MainViewModel = viewModel<MainViewModel>(mainActivity())

	if (mapViewState?.value == MapViewState.RESUMED && !cameraPositionInitialized.value) {
		LaunchedEffect(Unit) {
			mapView.value?.mapInterface?.getCamera()?.moveToCenterPositionZoom(
				centerPosition = Coord(
					systemIdentifier = CoordinateSystemIdentifiers.EPSG4326(),
					x = 8.5404656,
					y = 47.377858,
					z = 0.0
				), zoom = 30000.0, animated = false
			)
			cameraPositionInitialized.value = true
		}
	}

	val icons = mainViewModel.icons.collectAsState(emptyList())
	mapView.value?.setIcons(icons.value)

	AndroidView(
		modifier = Modifier.fillMaxSize(),
		factory = { context ->
			BestMapView(context).also {
				mapView.value = it
				it.setClickListener(object : BestMapClickListener {
					override fun onLongPressBaseMap(coordinate: Coord) {
						mainViewModel.clearClickedIcons()
						mainViewModel.onLongPress(coordinate)
					}

					override fun onClickIcons(icons: List<String>) {
						mainViewModel.clearLongPressCoordinates()
						mainViewModel.onClickIcons(icons)
					}
				})
			}
		}
	)

	val mapVectorLayerState = mapView.value?.vectorLayerState?.collectAsState()
	val hasVectorLayerError = mapVectorLayerState?.value == VectorLayerState.ERROR
	mapView.value?.isVisible = !hasVectorLayerError
	if (hasVectorLayerError) {
		VectorLayerError() { mapView.value?.addVectorLayer() }
	} else {
		MapOverlay(
			modifier = Modifier
				.fillMaxSize(),
			mapView = mapView.value,
			innerPadding = innerPadding
		)
	}
}