package ch.ubique.bestmap.map.util

import io.openmobilemaps.mapscore.shared.map.coordinates.Coord

interface BestMapClickListener {
    fun onLongPressBaseMap(coordinate: Coord)
    fun onClickIcons(icons: List<String>)
}