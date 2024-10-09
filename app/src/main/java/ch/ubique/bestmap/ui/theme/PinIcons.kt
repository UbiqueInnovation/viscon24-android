package ch.ubique.bestmap.ui.theme

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import androidx.annotation.DrawableRes
import ch.ubique.bestmap.R
import kotlin.math.min
import kotlin.math.roundToInt

val pinIcons = listOf(
	PinIcon("favorite", R.drawable.ic_favorite),
	PinIcon("star", R.drawable.ic_star),
	PinIcon("account_circle", R.drawable.ic_account_circle),
	PinIcon("face", R.drawable.ic_face),
	PinIcon("home", R.drawable.ic_home),
	PinIcon("build", R.drawable.ic_build),
	PinIcon("call", R.drawable.ic_call),
	PinIcon("notification", R.drawable.ic_notification),
	PinIcon("info", R.drawable.ic_info),
	PinIcon("error", R.drawable.ic_error)
)

data class PinIcon(val name: String, @DrawableRes val drawableId: Int) {
	fun drawToPinBitmap(context: Context, canvas: Canvas, fillWidthPc: Float = 0.55f) {
		val targetWidth = canvas.width * fillWidthPc
		val padding = ((canvas.width - targetWidth) * 0.5f).roundToInt()
		context.getDrawable(drawableId)?.apply {
			val scalingRatio = min(canvas.width / intrinsicWidth.toFloat(), canvas.height / intrinsicHeight.toFloat())
			setBounds(
				padding,
				padding,
				(scalingRatio * intrinsicWidth).roundToInt() - padding,
				(scalingRatio * intrinsicHeight).roundToInt() - padding
			)
			setTint(Color.WHITE)
			draw(canvas)
		}
	}

	companion object {
		fun getPinForName(iconName: String?): PinIcon? {
			return pinIcons.firstOrNull { iconName == it.name }
		}
	}
}