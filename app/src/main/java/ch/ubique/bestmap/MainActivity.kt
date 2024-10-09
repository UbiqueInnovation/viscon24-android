package ch.ubique.bestmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ch.ubique.bestmap.composable.App
import ch.ubique.bestmap.ui.theme.BestMapTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			BestMapTheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					App(innerPadding)
				}
			}
		}
	}
}