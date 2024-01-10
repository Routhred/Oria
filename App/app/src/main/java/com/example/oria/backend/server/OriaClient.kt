package com.example.oria.backend.server

import com.example.oria.backend.utils.DEBUG
import com.example.oria.backend.utils.ERROR
import com.example.oria.backend.utils.TagDebug
import com.example.oria.ui.theme.CONNECTION_TIMEOUT
import com.example.oria.ui.theme.ERROR_REQUEST
import com.example.oria.ui.theme.NO_ERROR
import com.example.oria.ui.theme.REQUEST_TIMEOUT
import com.example.oria.ui.theme.URL_BASE
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.util.InternalAPI
import kotlinx.serialization.Serializable

/**
 * Class for client http
 *
 */
class OriaClient {

    companion object {

        @Volatile
        private var instance: OriaClient? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: OriaClient().also { instance = it }
            }
    }

    val client = HttpClient(CIO) {

        install(HttpTimeout) {
            requestTimeoutMillis = REQUEST_TIMEOUT
            connectTimeoutMillis = CONNECTION_TIMEOUT
        }
        install(ResponseObserver) {
            onResponse { response ->
                DEBUG(TagDebug.SERVER, "${response.status.value}")
            }
        }
    }

    /**
     * Test function
     *
     */
    @OptIn(InternalAPI::class)
    suspend fun getToTest(): String {
        DEBUG(TagDebug.SERVER, "Test : GET $URL_BASE")
        try {
            val response = client.get(URL_BASE)
            DEBUG(
                TagDebug.SERVER,
                "Response Status : ${response.status}"
                        + "Response content : ${response.content}"

            )
            return response.status.toString()
        } catch (e: Exception) {
            ERROR(TagDebug.SERVER, e.message.toString())

        }

        return ERROR_REQUEST.toString()


    }

    /**
     * Function to send login request to the server
     *
     * @param username
     * @param password
     * @return
     */
    suspend fun login(username: String, password: String): Int {
        DEBUG(TagDebug.SERVER, "Test : GET $URL_BASE")
        try {
            // val token =  getCsrf()
            val response: HttpResponse = client.post("$URL_BASE/signin") {
                // cookie(name = "X-CSRFToken", value = token.CSRFToken)
                setBody(MultiPartFormDataContent(
                    formData {
                        append("username", username)
                        append("password", password)
                    }
                ))
            }
            val body: String = response.body()
            DEBUG(TagDebug.SERVER, "Response Status : ${response.status}")
            DEBUG(TagDebug.SERVER, "Response Body : ${body.toString()}")
            return NO_ERROR
        } catch (e: Exception) {
            ERROR(TagDebug.SERVER, e.message.toString())
        }
        return ERROR_REQUEST
    }

    private suspend fun getCsrf(): Csrf_response {
        return client.post("${URL_BASE}/get_csrf").body()
    }

}

@Serializable
data class Csrf_response(
    val detail: String,
    val CSRFToken: String,
)