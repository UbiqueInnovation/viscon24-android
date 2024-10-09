package ch.ubique.bestmap

import android.app.Application
import android.graphics.Bitmap
import androidx.core.graphics.applyCanvas
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ch.ubique.bestmap.repository.PinsRepository
import ch.ubique.bestmap.repository.model.Pin
import ch.ubique.bestmap.ui.theme.PinIcon
import io.openmobilemaps.mapscore.graphics.BitmapTextureHolder
import io.openmobilemaps.mapscore.shared.graphics.common.Vec2F
import io.openmobilemaps.mapscore.shared.graphics.shader.BlendMode
import io.openmobilemaps.mapscore.shared.map.coordinates.Coord
import io.openmobilemaps.mapscore.shared.map.coordinates.CoordinateSystemIdentifiers
import io.openmobilemaps.mapscore.shared.map.layers.icon.IconFactory
import io.openmobilemaps.mapscore.shared.map.layers.icon.IconInfoInterface
import io.openmobilemaps.mapscore.shared.map.layers.icon.IconType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

	private val pinsRepository = PinsRepository(getApplication())
	private val pinDrawable = requireNotNull(application.getDrawable(R.drawable.ic_pin))
	private val pinSelectedDrawable = requireNotNull(application.getDrawable(R.drawable.ic_pin_selected))

	private val clickedIconsMutable = MutableStateFlow<List<String>>(emptyList())

	private val longPressCoordinatesMutable = MutableStateFlow<Coord?>(null)
	val longPressCoordinates = longPressCoordinatesMutable.asStateFlow()

	private val newPinMutable = MutableStateFlow<Pin?>(null)

	val pins = pinsRepository.getAllPins().map {
		it.map { pin ->
			Pin(
				id = pin.id.toString(), title = pin.title, iconName = pin.icon, coord = Coord(
					systemIdentifier = CoordinateSystemIdentifiers.EPSG4326(),
					x = pin.longitude,
					y = pin.latitude,
					z = 0.0
				)
			)
		}
	}

	val selectedPin = combine(pins, clickedIconsMutable) { pins, clickedIcons ->
		return@combine clickedIcons.mapNotNull { clickedIconId -> pins.firstOrNull { pin -> clickedIconId == pin.id } }
			.minByOrNull { it.coord.y }
	}

	val icons = combine(newPinMutable, pins, selectedPin) { newPin, pins, selectedPin ->
		return@combine buildList<IconInfoInterface> {
			addAll(
				(pins + listOf(newPin))
					.filterNotNull()
				.map { pin ->
					IconFactory.createIconWithAnchor(
						identifier = pin.id,
						coordinate = pin.coord,
						texture = BitmapTextureHolder(Bitmap.createBitmap(
							pinDrawable.intrinsicWidth,
							pinDrawable.intrinsicHeight,
							Bitmap.Config.ARGB_8888
						)
							.applyCanvas {
								val drawable =
									if (pin.id == selectedPin?.id || pin.id == newPin?.id) pinSelectedDrawable else pinDrawable
								drawable.apply {
									setBounds(0, 0, width, height)
									draw(this@applyCanvas)
								}
								PinIcon.getPinForName(pin.iconName)?.drawToPinBitmap(application, this)
							}),
						iconSize = Vec2F(pinDrawable.intrinsicWidth.toFloat(), pinDrawable.intrinsicHeight.toFloat()),
						scaleType = IconType.INVARIANT,
						blendMode = BlendMode.NORMAL,
						iconAnchor = Vec2F(0.5f, 1.0f)
					)
				}.sortedByDescending { it.getCoordinate().y })
		}
	}

	fun onClickIcons(icons: List<String>) {
		viewModelScope.launch {
			clickedIconsMutable.emit(icons)
		}
	}

	fun clearClickedIcons() {
		viewModelScope.launch {
			clickedIconsMutable.emit(emptyList())
		}
	}

	fun onLongPress(clickCoordinate: Coord) {
		viewModelScope.launch {
			longPressCoordinatesMutable.emit(clickCoordinate)
		}
	}

	fun clearLongPressCoordinates() {
		longPressCoordinatesMutable.value = null
	}

	fun setNewIconProperties(pin: Pin) {
		viewModelScope.launch {
			newPinMutable.emit(pin)
		}
	}

	fun clearNewPin() {
		newPinMutable.value = null
	}

	fun addPin(title: String, icon: String?, coord: Coord): String {
		return pinsRepository.addPin(title, icon, coord).toString()
	}

	fun removePin(id: String) {
		id.toLongOrNull()?.let { pinsRepository.removePin(it) }
	}
}