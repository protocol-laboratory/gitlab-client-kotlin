package io.github.protocol.gitlab.client

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class InnerHttpClient(private val configuration: Configuration) {

    private val client: OkHttpClient
    private val json = Json { ignoreUnknownKeys = true }

    init {
        val builder = OkHttpClient.Builder()

        if (configuration.useTls) {
            val sslContext = SslContextUtil(configuration)
            builder.sslSocketFactory(sslContext.getSslContext().socketFactory, sslContext.getX509TrustManager())
        }

        if (configuration.disableHostnameVerification) {
            builder.hostnameVerifier { _, _ -> true }
        }

        client = builder.build()
    }

    fun <T> get(path: String, deserializationStrategy: DeserializationStrategy<T>): T {
        val request = Request.Builder()
            .url(buildUrl(path))
            .header("Authorization", "Bearer ${configuration.token}")
            .build()

        return execute(request, deserializationStrategy)
    }

    fun <T> post(path: String, requestBody: String, deserializationStrategy: DeserializationStrategy<T>): T {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = requestBody.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(buildUrl(path))
            .header("Authorization", "Bearer ${configuration.token}")
            .post(body)
            .build()

        return execute(request, deserializationStrategy)
    }

    fun <T> put(path: String, requestBody: String, deserializationStrategy: DeserializationStrategy<T>): T {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = requestBody.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(buildUrl(path))
            .header("Authorization", "Bearer ${configuration.token}")
            .put(body)
            .build()

        return execute(request, deserializationStrategy)
    }

    fun <T> delete(path: String, deserializationStrategy: DeserializationStrategy<T>): T {
        val request = Request.Builder()
            .url(buildUrl(path))
            .header("Authorization", "Bearer ${configuration.token}")
            .delete()
            .build()

        return execute(request, deserializationStrategy)
    }

    private fun buildUrl(path: String): String {
        val protocol = if (configuration.useTls) "https" else "http"
        return "$protocol://${configuration.host}:${configuration.port}$path"
    }

    private fun <T> execute(request: Request, deserializationStrategy: DeserializationStrategy<T>): T {
        val response = client.newCall(request).execute()

        if (!response.isSuccessful) {
            throw GitLabException("Unexpected code ${response.code}")
        }

        val body = response.body?.string() ?: throw GitLabException("Empty response")

        try {
            return json.decodeFromString(deserializationStrategy, body)
        } catch (e: Exception) {
            throw GitLabException(e)
        }
    }
}
