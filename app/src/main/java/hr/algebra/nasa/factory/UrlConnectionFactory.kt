package hr.algebra.nasa.factory

import java.net.HttpURLConnection
import java.net.URL

const val TIMEOUT = 10000
const val REQUEST_METHOD = "GET"
const val USER_AGENT = "User-Agent"
const val MOZILLA = "Mozilla/5.0"

fun createGetHttpGetConnection(path: String): HttpURLConnection {
    val url = URL(path)
    return (url.openConnection() as HttpURLConnection).apply {
        connectTimeout = TIMEOUT
        readTimeout = TIMEOUT
        requestMethod = REQUEST_METHOD
        addRequestProperty(USER_AGENT, MOZILLA)
    }
}