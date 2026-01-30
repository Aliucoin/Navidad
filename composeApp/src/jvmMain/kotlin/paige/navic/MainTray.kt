package paige.navic

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.ApplicationScope
import com.kdroid.composetray.tray.api.Tray
import navic.composeapp.generated.resources.Res
import navic.composeapp.generated.resources.app_name
import navic.composeapp.generated.resources.exit
import navic.composeapp.generated.resources.play_arrow
import navic.composeapp.generated.resources.skip_next
import navic.composeapp.generated.resources.skip_previous
import navic.composeapp.generated.resources.tray_icon
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ApplicationScope.MainTray(window: ComposeWindow?) {
	Tray(
		icon = vectorResource(Res.drawable.tray_icon),
		tooltip = stringResource(Res.string.app_name),
		tint = if (isSystemInDarkTheme()) Color.White else Color.Black,
	) {
		Item("Previous", Res.drawable.skip_previous, isEnabled = false)
		Item("Play", Res.drawable.play_arrow, isEnabled = false)
		Item("Next", Res.drawable.skip_next, isEnabled = false)

		Divider()

		Item(label = "Open") {
			window?.isVisible = true
			window?.toFront()
		}
		Item(
			label = "Exit",
			icon = Res.drawable.exit
		) {
			(::exitApplication)()
		}
	}
}
