package io.github.protocol.gitlab.client

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ProtectedBranches(private val httpClient: InnerHttpClient) {
    fun protectedBranches(projectId: Int): List<ProtectedBranch> {
        return httpClient.get(
            "$URL_PROJECTS/$projectId/protected_branches",
            ListSerializer(ProtectedBranch.serializer()),
        )
    }

    fun addProtectedBranch(projectId: Int, req: ProtectedBranchAddReq): ProtectedBranch {
        val requestBody = Json.encodeToString(req)
        return httpClient.post(
            "$URL_PROJECTS/$projectId/protected_branches",
            requestBody,
            ProtectedBranch.serializer(),
        )
    }
}
