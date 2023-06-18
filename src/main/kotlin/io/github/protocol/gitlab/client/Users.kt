package io.github.protocol.gitlab.client

class Users(private val httpClient: InnerHttpClient) {
    fun user(): User {
        return httpClient.get(URL_USER, User.serializer())
    }
}
