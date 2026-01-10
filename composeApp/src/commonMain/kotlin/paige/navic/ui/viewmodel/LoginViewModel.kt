package paige.navic.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import paige.navic.data.model.User
import paige.navic.data.session.SessionManager
import paige.navic.util.LoginState

class LoginViewModel : ViewModel() {
	private val _loginState = MutableStateFlow<LoginState<User?>>(LoginState.LoggedOut)
	val loginState: StateFlow<LoginState<User?>> = _loginState.asStateFlow()

	init {
		loadUser()
	}

	fun loadUser() {
		viewModelScope.launch {
			val user = SessionManager.currentUser
			if (user != null) {
				_loginState.value = LoginState.Success(user)
			} else {
				_loginState.value = LoginState.LoggedOut
			}
		}
	}

	fun login(
		instanceUrl: String,
		username: String,
		password: String
	) {
		viewModelScope.launch {
			_loginState.value = LoginState.Loading
			_loginState.value = try {
				SessionManager.login(instanceUrl, username, password)
				if (SessionManager.currentUser != null) {
					LoginState.Success(SessionManager.currentUser)
				} else {
					throw Exception("currentUser is null")
				}
			} catch (e: Exception) {
				LoginState.Error(e)
			}
		}
	}

	fun logout() {
		viewModelScope.launch {
			SessionManager.logout()
			_loginState.value = LoginState.LoggedOut
		}
	}
}