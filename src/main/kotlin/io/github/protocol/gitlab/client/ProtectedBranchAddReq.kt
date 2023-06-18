package io.github.protocol.gitlab.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProtectedBranchAddReq(
    val name: String,
    @SerialName("push_access_level")
    val pushAccessLevel: Int,
    @SerialName("merge_access_level")
    val mergeAccessLevel: Int,
)
