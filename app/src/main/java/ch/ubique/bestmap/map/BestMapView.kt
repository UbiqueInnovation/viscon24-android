package ch.ubique.bestmap.map

import android.content.Context
import android.location.Location
import androidx.lifecycle.findViewTreeLifecycleOwner
import ch.ubique.bestmap.map.util.BestMapClickListener
import ch.ubique.bestmap.map.util.SimpleTouchListener
import ch.ubique.bestmap.map.util.VectorLayerState
import io.openmobilemaps.gps.providers.LocationUpdateListener
import io.openmobilemaps.mapscore.map.view.MapView
import io.openmobilemaps.mapscore.shared.graphics.common.Vec2F
import io.openmobilemaps.mapscore.shared.map.MapConfig
import io.openmobilemaps.mapscore.shared.map.coordinates.CoordinateSystemFactory
import io.openmobilemaps.mapscore.shared.map.coordinates.CoordinateSystemIdentifiers
import io.openmobilemaps.mapscore.shared.map.layers.icon.IconInfoInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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


	// TODO [ICON LAYER] create icon layer as field

	private val vectorLayerStateMutable = MutableStateFlow(VectorLayerState.UNINITIALIZED)
	val vectorLayerState = vectorLayerStateMutable.asStateFlow()

	/*
	TODO [VECTOR LAYER]
	private val density = resources.displayMetrics.density
	private val dataLoader = DataLoader(context, context.cacheDir, 16 * 1024 * 1024L)
	private val fontLoader = FontLoader(context, "map/fonts/", density)
	 */

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
		// TODO [STYLE JSON] change map style to base_map_style
		// TODO [VECTOR LAYER] load vector style.json
		// TODO [VECTOR LAYER] create vector layer
		val vectorLayer = Unit

		if (vectorLayer != null) {
			// TODO [VECTOR LAYER] add vector layer to map
			vectorLayerStateMutable.value = VectorLayerState.INITIALIZED
		} else {
			vectorLayerStateMutable.value = VectorLayerState.ERROR
		}
	}

	private fun addGpsLayer() {
		// TODO [GPS] add gps layer to map
	}

	private fun addIconLayer() {
		// TODO [ICON LAYER] customize icon layer to be clickable and add callback to handle clicks
		// TODO [ICON LAYER] add icon layer to map

		/*
		TODO [ICON LAYER] test your icon layer using this icon
		val testIcon = IconFactory.createIcon(
			identifier = "Test-Icon",
			coordinate = Coord(CoordinateSystemIdentifiers.EPSG4326(), 8.54378, 47.37568, 0.0),
			texture = BitmapTextureHolder(drawable = requireNotNull(ContextCompat.getDrawable(context, R.drawable.ic_star))),
			iconSize = Vec2F(84f, 84f),
			scaleType = IconType.INVARIANT,
			blendMode = BlendMode.NORMAL
		)
		iconLayer.add(testIcon)
		 */
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
		// TODO [ICON LAYER] set icons on icon layer
	}
}