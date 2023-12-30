package com.example.oria.viewModel.auth

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.oria.backend.data.storage.dataStore.PreferencesKey
import com.example.oria.backend.data.storage.dataStore.PreferencesManager
import com.example.oria.backend.location.launchGPS
import com.example.oria.backend.utils.DEBUG
import com.example.oria.backend.utils.TagDebug
import com.example.oria.ui.theme.ERROR_LOGIN
import com.example.oria.ui.theme.NO_ERROR

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
    fun login(context: Context): Int {

        // Send request to the server
        val token = requestLogin(context)

        if (token == "ERROR") {
            return ERROR_LOGIN
        }
        DEBUG(TagDebug.LOGIN, "Save Username to storage")
        // Save username in the storage
        preferencesManager.saveData(
            PreferencesKey.USERNAME,
            loginUiState.username
        )

        DEBUG(TagDebug.LOGIN, "Save Token to storage")
        // Save token in the storage
        preferencesManager.saveData(
            PreferencesKey.TOKEN,
            token
        )

        // Launch GPS Service
        launchGPS(context)

        return NO_ERROR

    }

    /**
     * Request Login to the server
     *
     * @param context
     * @return
     */
    fun requestLogin(context: Context): String {
        DEBUG(TagDebug.LOGIN, "Request Login")
        // TODO request server to login and return the token

        return "token"
    }
}


data class LoginUiState(
    val username: String = "",
    val password: String = ""
)