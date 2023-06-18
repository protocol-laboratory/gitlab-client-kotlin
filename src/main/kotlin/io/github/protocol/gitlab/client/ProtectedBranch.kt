package io.github.protocol.gitlab.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProtectedBranch(
    val id: Int,
    val name: String,
    @SerialName("push_access_levels")
    val pushAccessLevels: List<AccessLevel>,
    @SerialName("merge_access_levels")
    val mergeAccessLevels: List<AccessLevel>,
    @SerialName("allow_force_push")
    val allowForcePush: Boolean,
    @SerialName("unprotect_access_levels")
    val unprotectAccessLevels: List<AccessLevel>,
    @SerialName("code_owner_approval_required")
    val codeOwnerApprovalRequired: Boolean,
    val inherited: Boolean,
)
