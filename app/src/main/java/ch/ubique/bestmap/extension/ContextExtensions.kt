package ch.ubique.bestmap.extension

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat.checkSelfPermission

fun Context.hasLocationPermission() =
	checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
			|| checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

fun Context.requireActivityContext(): Activity {
	return when (this) {
		is Activity -> this
		is ContextWrapper -> baseContext.requireActivityContext()
		else -> throw IllegalStateException("$this has to be an Activity instance.")
	}
}