package com.example.oria.backend.server

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import java.nio.charset.Charset

class Connection(
    context: Context,
    ip: String = "127.0.0.1",
    port: String = "3000",
    protocole: String = "http",
) {

    private var queue: RequestQueue
    private var ip: String
    private var port: String
    private var url: String
    private var protocole: String
    private var id: String = ""

    private val requestQueue: RequestQueue by lazy {

        Volley.newRequestQueue(context.applicationContext)
    }
    init {
        this.queue = Volley.newRequestQueue(context)
        this.ip = ip
        this.port = port
        this.protocole = protocole
        this.url = "$protocole://$ip:$port"
        Log.v("Connector created", this.toString())
    }
    companion object {
        @Volatile
        private var INSTANCE: Connection? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Connection(context).also {
                    INSTANCE = it
                }
            }
    }
    private fun sendRequest(
        request: String,
        type: Int = Request.Method.POST,
        params: MutableMap<String, String> = HashMap(),
    ): GeneralResponse? {
        var request_body = url + request
        var general_response: GeneralResponse? = null

        val string_request: StringRequest = object : StringRequest(
            type,
            request_body,
            Response.Listener {
                    response ->
                general_response = Gson().fromJson(response, GeneralResponse::class.java)
                Log.i("Response : ", general_response.toString())
            },
            Response.ErrorListener {
                    error ->
                Log.e("Error : ", error.message.toString())
                val response = error.networkResponse
                val errorMsg = ""
                if (response?.data != null) {
                    val errorString = response.data.toString()
                    Log.e("log error", errorString)
                }
            },
        ) {
            override fun getParams(): Map<String, String> { return params }
            override fun getBody(): ByteArray {
                return request_body.toByteArray(Charset.defaultCharset())
            }
        }

        Log.v("Request", string_request.toString())

        queue.add(string_request)
        return general_response
    }
    fun loginRequest(email: String, password: String) {
        val params: MutableMap<String, String> = HashMap()
        params["email"] = email
        params["password"] = password

        val response: GeneralResponse? = sendRequest(SERVER_SIGNIN, params = params)

        if (response != null) {
            this.id = response.message
            Log.i("Response : ", response.message)
        } else {
            Log.i("Response : ", "Pas de réponse")
        }
    }
    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
    override fun toString(): String {
        return "this.queue = " + this.queue.toString() +
            "\nthis.ip = " + this.ip +
            "\nthis.port = " + this.port +
            "\nthis.protocole = " + this.protocole +
            "\nthis.url = " + this.url
    }
}
