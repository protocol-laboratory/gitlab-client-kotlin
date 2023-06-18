package io.github.protocol.gitlab.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessLevel(
    val id: Int,
    @SerialName("access_level")
    val accessLevel: Int,
    @SerialName("access_level_description")
    val accessLevelDescription: String,
    @SerialName("user_id")
    val userId: Int?,
    @SerialName("group_id")
    val groupId: Int?,
)
