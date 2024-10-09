package ch.ubique.bestmap.extension

import io.openmobilemaps.mapscore.map.loader.DataLoader
import io.openmobilemaps.mapscore.map.loader.FontLoader
import io.openmobilemaps.mapscore.shared.map.layers.tiled.Tiled2dMapZoomInfo
import io.openmobilemaps.mapscore.shared.map.layers.tiled.vector.Tiled2dMapVectorLayerInterface
import kotlin.math.PI
import kotlin.math.cos

fun Tiled2dMapVectorLayerInterface.Companion.create(layerName: String, styleJson: String, dataLoader: DataLoader, fontLoader: FontLoader): Tiled2dMapVectorLayerInterface? = createExplicitly(
	layerName = layerName,
	styleJson = styleJson,
	localStyleJson = true,
	loaders = arrayListOf(dataLoader),
	fontLoader = fontLoader,
	localDataProvider = null,
	customZoomInfo = Tiled2dMapZoomInfo(
		zoomLevelScaleFactor = cos(46.8132 / 180 * PI).toFloat(),
		numDrawPreviousLayers = 0,
		numDrawPreviousOrLaterTLayers = 0,
		adaptScaleToScreen = false,
		maskTile = true,
		underzoom = false,
		overzoom = true
	),
	symbolDelegate = null,
	sourceUrlParams = null
)
