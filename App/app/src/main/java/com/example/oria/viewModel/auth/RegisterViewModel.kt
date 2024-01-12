package com.example.oria.viewModel.auth

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.oria.backend.data.storage.dataStore.PreferencesKey
import com.example.oria.backend.data.storage.dataStore.PreferencesManager
import com.example.oria.backend.server.OriaClient
import com.example.oria.backend.server.RegisterResponse
import com.example.oria.backend.utils.DEBUG
import com.example.oria.backend.utils.ERROR
import com.example.oria.backend.utils.TagDebug
import com.example.oria.ui.theme.ERROR_INCORRECT_EMAIL_FORMAT
import com.example.oria.ui.theme.ERROR_INCORRECT_NAME_FORMAT
import com.example.oria.ui.theme.ERROR_INCORRECT_PASSWORD_FORMAT
import com.example.oria.ui.theme.ERROR_INCORRECT_USERNAME_FORMAT
import com.example.oria.ui.theme.ERROR_PASSWORD
import com.example.oria.ui.theme.ERROR_SERVER
import com.example.oria.ui.theme.NO_ERROR
import kotlinx.coroutines.launch

class RegisterViewModel(preferencesManager: PreferencesManager) : ViewModel() {

    var registerState by mutableStateOf(
        RegisterState()
    )
        private set

    private val preferencesManager = preferencesManager
    private lateinit var _navController: NavController
    private lateinit var _context: Context


    /**
     * Update current State
     *
     * @param RegisterState
     */
    fun updateUiState(new_state: RegisterState) {
        registerState = new_state
    }

    fun register(context: Context, navController: NavController){
        _navController = navController
        _context = context
        DEBUG(TagDebug.REGISTER, registerState.toString())
        viewModelScope.launch{
            callRegister()
        }
    }

    suspend fun callRegister(){
        val response: RegisterResponse = OriaClient.getInstance().register(registerState)
        registerState = registerState.copy(error_code = response.ERROR_CODE, token = response.TOKEN)
        finishRegister()
    }


    fun finishRegister(){

        val error_code: Int = registerState.error_code

        if(error_code != NO_ERROR){
            ERROR(TagDebug.REGISTER, error_code.toString())
            when(error_code){
                ERROR_SERVER ->
                    registerState = registerState.copy(error_field = "Server error")
                ERROR_INCORRECT_USERNAME_FORMAT ->
                    registerState = registerState.copy(
                        error_field = "Incorrect username (maybe already taken)")
                ERROR_INCORRECT_NAME_FORMAT ->
                    registerState = registerState.copy(
                        error_field = "Incorrect first/last name format"
                    )
                ERROR_INCORRECT_EMAIL_FORMAT ->
                    registerState = registerState.copy(error_field = "Incorrect email format")
                ERROR_INCORRECT_PASSWORD_FORMAT ->
                    registerState = registerState.copy(error_field = "Incorrect password format")
                ERROR_PASSWORD ->
                    registerState = registerState.copy(error_field = "Password are not the same")
                else ->
                    registerState = registerState.copy(error_field = "Bad Request")

            }
            return
        }
        preferencesManager.saveData(
            PreferencesKey.TOKEN,
            registerState.token
        )
        preferencesManager.saveData(
            PreferencesKey.USERNAME,
            registerState.username
        )

        _navController.navigate("login")

    }

}

data class RegisterState(
    val username: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val password: String = "",
    val confirmpwd: String = "",
    val email: String = "",
    val error_code: Int = 1,
    val error_field: String = "",
    val token: String = ""
){
    override fun toString():String{
        return "username : $username\n" +
                "firstname : $firstname\n" +
                "lastname : $lastname\n" +
                "password : $password\n" +
                "confirmpwd : $confirmpwd\n" +
                "error_code : $error_code\n" +
                "error_field : $error_field\n" +
                "token : $token\n"
    }
}