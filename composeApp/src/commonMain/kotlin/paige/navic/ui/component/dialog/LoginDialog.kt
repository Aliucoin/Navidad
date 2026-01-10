package paige.navic.ui.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kyant.capsule.ContinuousCapsule
import com.kyant.capsule.ContinuousRoundedRectangle
import dev.burnoo.compose.remembersetting.rememberStringSetting
import navic.composeapp.generated.resources.Res
import navic.composeapp.generated.resources.action_cancel
import navic.composeapp.generated.resources.action_log_in
import navic.composeapp.generated.resources.option_account_navidrome_instance
import navic.composeapp.generated.resources.option_account_password
import navic.composeapp.generated.resources.option_account_username
import org.jetbrains.compose.resources.stringResource
import paige.navic.LocalCtx
import paige.navic.ui.viewmodel.LoginViewModel
import paige.navic.util.LoginState

@Composable
fun LoginDialog(
	viewModel: LoginViewModel = viewModel { LoginViewModel() },
	onDismissRequest: () -> Unit
) {
	val ctx = LocalCtx.current
	val scrollState = rememberScrollState()

	var instanceUrl by rememberStringSetting("instanceUrl", "")
	var username by rememberStringSetting("username", "")
	var password by rememberStringSetting("password", "")

	val loginState by viewModel.loginState.collectAsState()

	AlertDialog(
		onDismissRequest = {
			if (loginState !is LoginState.Loading) {
				onDismissRequest()
			}
		},
		text = {
			Column(modifier = Modifier.verticalScroll(scrollState)) {
				(loginState as? LoginState.Error)?.let {
					Text(it.error.message ?: "$it")
				}
				OutlinedTextField(
					value = instanceUrl,
					onValueChange = { instanceUrl = it },
					label = { Text(stringResource(Res.string.option_account_navidrome_instance)) },
					placeholder = { Text("demo.navidrome.org") },
					maxLines = 1,
					keyboardOptions = KeyboardOptions(
						autoCorrectEnabled = false,
						keyboardType = KeyboardType.Uri
					)
				)
				OutlinedTextField(
					value = username,
					onValueChange = { username = it },
					label = { Text(stringResource(Res.string.option_account_username)) },
					maxLines = 1,
					modifier = Modifier.semantics {
						contentType = ContentType.Username
					},
					keyboardOptions = KeyboardOptions(
						autoCorrectEnabled = false
					)
				)
				OutlinedTextField(
					value = password,
					onValueChange = { password = it },
					label = { Text(stringResource(Res.string.option_account_password)) },
					visualTransformation = PasswordVisualTransformation(),
					maxLines = 1,
					modifier = Modifier.semantics {
						contentType = ContentType.Password
					},
					keyboardOptions = KeyboardOptions(
						autoCorrectEnabled = false,
						keyboardType = KeyboardType.Password
					)
				)
			}
		},
		confirmButton = {
			Button(
				shape = ContinuousCapsule,
				onClick = {
					viewModel.login(
						if (
							!instanceUrl.startsWith("https://")
							&& !instanceUrl.startsWith("http://")
						) "https://$instanceUrl" else instanceUrl,
						username,
						password
					)
				},
				enabled = loginState !is LoginState.Loading,
				content = {
					if (loginState !is LoginState.Loading) {
						Text(stringResource(Res.string.action_log_in))
					} else {
						CircularProgressIndicator(
							modifier = Modifier.size(20.dp)
						)
					}
				}
			)
		},
		dismissButton = {
			TextButton(
				onClick = {
					ctx.clickSound()
					onDismissRequest()
				},
				enabled = loginState !is LoginState.Loading,
				content = { Text(stringResource(Res.string.action_cancel)) }
			)
		},
		shape = ContinuousRoundedRectangle(42.dp)
	)
}