package io.github.protocol.gitlab.client

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val username: String,
    val name: String,
    val email: String,
)
