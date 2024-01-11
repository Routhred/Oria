package com.example.oria.viewModel.auth

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.oria.backend.data.storage.dataStore.PreferencesKey
import com.example.oria.backend.data.storage.dataStore.PreferencesManager
import com.example.oria.backend.location.launchGPS
import com.example.oria.backend.server.OriaClient
import com.example.oria.backend.utils.DEBUG
import com.example.oria.backend.utils.ERROR
import com.example.oria.backend.utils.TagDebug
import com.example.oria.ui.theme.ERROR_INCORRECT_PASSWORD_FORMAT
import com.example.oria.ui.theme.ERROR_INCORRECT_USERNAME_FORMAT
import com.example.oria.ui.theme.ERROR_LOGIN
import com.example.oria.ui.theme.ERROR_LOGIN_EMAIL
import com.example.oria.ui.theme.ERROR_LOGIN_ID
import com.example.oria.ui.theme.ERROR_SERVER
import com.example.oria.ui.theme.NO_ERROR
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * ViewModel for the login view
 *
 * @constructor
 *
 * @param preferencesManager
 */
class LoginViewModel(preferencesManager: PreferencesManager) : ViewModel() {

    var loginUiState by mutableStateOf(
        LoginUiState(
            username = preferencesManager.getData(PreferencesKey.USERNAME, "")
        )
    )
        private set

    private val preferencesManager = preferencesManager
    private lateinit var _navController: NavController
    private lateinit var _context: Context


    /**
     * Update current Ui State
     *
     * @param login
     * @param password
     */
    fun updateUiState(login: LoginUiState, password: String = "") {
        DEBUG(TagDebug.LOGIN, "Update Ui State")
        loginUiState = login
    }

    /**
     * Try to login
     *
     * @param context
     * @return ErrorCode
     */
    fun login(context: Context, navController: NavController) {
        _navController = navController
        _context = context
        viewModelScope.launch{
            callLogin()
        }
    }

    /**
     * function to terminate login
     *
     */
    private fun finishLogin(){

        DEBUG(TagDebug.BEGIN_FUNCTION, "finishLogin")
        val error_code: Int = loginUiState.error_code

        if (error_code != NO_ERROR) {
            when(error_code){
                ERROR_SERVER -> {
                    loginUiState = loginUiState.copy(error_field = "Server error")
                }
                ERROR_LOGIN_EMAIL -> {
                    loginUiState = loginUiState.copy(error_field = "Email not verified")
                }
                ERROR_LOGIN_ID -> {
                    loginUiState = loginUiState.copy(error_field = "Incorrect identifiers")
                }
                ERROR_INCORRECT_PASSWORD_FORMAT -> {
                    loginUiState = loginUiState.copy(error_field = "Incorrect password format")
                }
                ERROR_INCORRECT_USERNAME_FORMAT -> {
                    loginUiState = loginUiState.copy(error_field = "Incorrect username format")
                }
                else -> {
                    loginUiState = loginUiState.copy(error_field = "Bad request")
                }
            }
            ERROR(TagDebug.LOGIN, loginUiState.error_field)
            return
        }

        preferencesManager.saveData(
            PreferencesKey.USERNAME,
            loginUiState.username
        )

        preferencesManager.saveData(
            PreferencesKey.TOKEN,
            ERROR_LOGIN.toString()
        )

        launchGPS(_context)

        _navController.navigate(
            "main",
        ) {
            popUpTo("auth") {
                inclusive = true
            }
        }
    }

    /**
     * Suspended function to send Login resquest to server
     *
     */
    private suspend fun callLogin() {
        DEBUG(TagDebug.BEGIN_FUNCTION, "callLogin")
        val responseCode: Int = OriaClient.getInstance().login(
            loginUiState.username, loginUiState
                .password
        )
        loginUiState = loginUiState.copy(error_code = responseCode)
        finishLogin()
    }
}


data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val token: String = "",
    val error_code: Int = 1,
    val error_field: String = ""
)