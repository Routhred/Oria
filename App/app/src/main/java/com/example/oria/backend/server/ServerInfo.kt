package com.example.oria.backend.server

const val SERVER_IP = "192.168.1.19"
const val SERVER_PORT = "333"
const val SERVER_PROTOCOL = "http"
const val SERVER_ADDRESS = "$SERVER_PROTOCOL://$SERVER_IP:$SERVER_PORT"


const val SERVER_SIGNIN = "/auth/signin"
const val SERVER_SIGNUP= "/auth/signup"