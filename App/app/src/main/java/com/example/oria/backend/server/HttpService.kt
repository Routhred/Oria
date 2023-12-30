package com.example.oria.backend.server

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLProtocol

/**
 * Class to use HTTP services and request the server
 * TODO The class has to be make (doesn't work yet)
 */
class HttpService private constructor() {
    companion object {

        @Volatile
        private var instance: HttpClient? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: HttpClient().also { instance = it }
            }
    }

    val client = HttpClient(Android) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    suspend fun Login(name: String, password: String): Int {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = HttpRoutes.HOST
                path(HttpRoutes.LOGIN)
                parameters.append("name", name)
                parameters.append("password", password)
            }
        }

        return 0
    }
}