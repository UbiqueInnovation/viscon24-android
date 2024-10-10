package ch.ubique.bestmap.map

import android.content.Context
import android.location.Location
import androidx.lifecycle.findViewTreeLifecycleOwner
import ch.ubique.bestmap.extension.create
import ch.ubique.bestmap.map.util.BestMapClickListener
import ch.ubique.bestmap.map.util.SimpleTouchListener
import ch.ubique.bestmap.map.util.VectorLayerState
import io.openmobilemaps.gps.providers.LocationUpdateListener
import io.openmobilemaps.mapscore.map.loader.DataLoader
import io.openmobilemaps.mapscore.map.loader.FontLoader
import io.openmobilemaps.mapscore.map.view.MapView
import io.openmobilemaps.mapscore.shared.graphics.common.Vec2F
import io.openmobilemaps.mapscore.shared.map.MapConfig
import io.openmobilemaps.mapscore.shared.map.coordinates.CoordinateSystemFactory
import io.openmobilemaps.mapscore.shared.map.coordinates.CoordinateSystemIdentifiers
import io.openmobilemaps.mapscore.shared.map.layers.icon.IconInfoInterface
import io.openmobilemaps.mapscore.shared.map.layers.icon.IconLayerCallbackInterface
import io.openmobilemaps.mapscore.shared.map.layers.icon.IconLayerInterface
import io.openmobilemaps.mapscore.shared.map.layers.tiled.vector.Tiled2dMapVectorLayerInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.nio.charset.StandardCharsets

class BestMapView(context: Context) : MapView(context), LocationUpdateListener {

	companion object {
		private const val BASE_LAYER_INDEX = 0
		private const val GPS_LAYER_INDEX = 10
		private const val ICON_LAYER_INDEX = 20
	}

	private var clickListener: BestMapClickListener? = null

	/*
	TODO [GPS]
	private val locationProvider = GpsProviderType.GOOGLE_FUSED.getProvider(context)
	private val gpsLayer = GpsLayer(context, GpsStyleInfoFactory.createDefaultStyle(context), locationProvider).apply {
		setMode(GpsMode.STANDARD)
		setHeadingEnabled(false)
	}
	 */
	var currentLocation: Location? = null
		private set

	private val iconLayer = IconLayerInterface.create()

	private val vectorLayerStateMutable = MutableStateFlow(VectorLayerState.UNINITIALIZED)
	val vectorLayerState = vectorLayerStateMutable.asStateFlow()

	private val density = resources.displayMetrics.density
	private val dataLoader = DataLoader(context, context.cacheDir, 16 * 1024 * 1024L)
	private val fontLoader = FontLoader(context, "map/fonts/", density)

	init {
		setupMap(MapConfig(CoordinateSystemFactory.getEpsg2056System()))

		addVectorLayer()
		addGpsLayer()
		addIconLayer()

		addTouchListener()
	}

	private fun addTouchListener() {
		requireMapInterface().getTouchHandler().addListener(object : SimpleTouchListener() {
			override fun onLongPress(posScreen: Vec2F): Boolean {
				// TODO [ADD NEW POI] get the coordinate of the screen position from the map camera
				// TODO [ADD NEW POI] call onLongPress with the resulting coordinate
				return false
			}
		})
	}

	fun addVectorLayer() {
		val styleJson = context.assets.open("map/styles/base_map_style.json")
			.bufferedReader(StandardCharsets.UTF_8).use { it.readText() }
		val vectorLayer: Tiled2dMapVectorLayerInterface? = Tiled2dMapVectorLayerInterface.create(
			layerName = "SwisstopoBaseMap",
			styleJson = styleJson,
			dataLoader = dataLoader,
			fontLoader = fontLoader
		)
		if (vectorLayer != null) {
			insertLayerAt(vectorLayer.asLayerInterface(), BASE_LAYER_INDEX)
			vectorLayerStateMutable.value = VectorLayerState.INITIALIZED
		} else {
			vectorLayerStateMutable.value = VectorLayerState.ERROR
		}
	}

	private fun addGpsLayer() {
		// TODO [GPS] add gps layer to map
	}

	private fun addIconLayer() {
		iconLayer.apply {
			setLayerClickable(true)
			setCallbackHandler(object : IconLayerCallbackInterface() {
				override fun onClickConfirmed(icons: ArrayList<IconInfoInterface>): Boolean {
					clickListener?.onClickIcons(icons.map { it.getIdentifier() })
					return true
				}

				override fun onLongPress(icons: ArrayList<IconInfoInterface>): Boolean {
					// not used
					return false
				}

			})
		}
		insertLayerAt(iconLayer.asLayerInterface(), ICON_LAYER_INDEX)
	}

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()
		val lifecycleOwner = requireNotNull(findViewTreeLifecycleOwner())
		registerLifecycle(lifecycleOwner.lifecycle)
		// TODO [GPS] register lifecycle in gps layer
		// TODO [GPS] locationProvider.registerLocationUpdateListener(this)
	}

	override fun onDetachedFromWindow() {
		super.onDetachedFromWindow()
		// TODO [GPS] locationProvider.unregisterLocationUpdateListener(this)
	}

	override fun onLocationUpdate(newLocation: Location) {
		currentLocation = newLocation
	}

	fun notifyLocationPermissionGranted() {
		// TODO [GPS] locationProvider.notifyLocationPermissionGranted()
	}

	fun setClickListener(clickListener: BestMapClickListener?) {
		this.clickListener = clickListener
	}

	fun setIcons(icons: List<IconInfoInterface>) {
		iconLayer.setIcons(icons.toCollection(ArrayList()))
	}
}