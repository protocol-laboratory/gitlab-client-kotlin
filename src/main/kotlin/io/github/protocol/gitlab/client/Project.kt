package io.github.protocol.gitlab.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Project(
    val id: Int,
    val name: String,
    val description: String?,
    @SerialName("default_branch")
    val defaultBranch: String,
)
