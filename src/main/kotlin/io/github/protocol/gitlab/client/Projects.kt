package io.github.protocol.gitlab.client

import kotlinx.serialization.builtins.ListSerializer

class Projects(private val httpClient: InnerHttpClient) {
    fun projects(): List<Project> {
        return httpClient.get(URL_PROJECTS, ListSerializer(Project.serializer()))
    }

    fun members(projectId: Int): List<Member> {
        return httpClient.get("$URL_PROJECTS/$projectId/members", ListSerializer(Member.serializer()))
    }

    fun member(projectId: Int, userId: Int): Member {
        return httpClient.get("$URL_PROJECTS/$projectId/members/$userId", Member.serializer())
    }
}
