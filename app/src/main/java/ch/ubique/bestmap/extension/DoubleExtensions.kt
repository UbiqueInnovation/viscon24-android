package ch.ubique.bestmap.extension

import kotlin.math.round

fun Double.roundToDecimals(decimals: Int): Double {
	var multiplier = 1.0
	repeat(decimals) { multiplier *= 10 }
	return round(this * multiplier) / multiplier
}