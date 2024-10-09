package ch.ubique.bestmap.repository.model

import io.openmobilemaps.mapscore.shared.map.coordinates.Coord

data class Pin(val id: String, val title: String, val iconName: String?, val coord: Coord)