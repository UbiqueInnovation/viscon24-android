package ch.ubique.ubdiag.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import ch.ubique.bestmap.MainActivity
import ch.ubique.bestmap.extension.requireActivityContext

@Composable
@ReadOnlyComposable
fun mainActivity() = LocalContext.current.requireActivityContext() as MainActivity