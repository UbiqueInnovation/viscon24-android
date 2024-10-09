package ch.ubique.bestmap

import android.app.Application
import io.openmobilemaps.gps.LayerGps

class BestMapApplication: Application() {

	override fun onCreate() {
		super.onCreate()
		LayerGps.initialize()
	}
}