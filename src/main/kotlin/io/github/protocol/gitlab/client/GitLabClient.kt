package io.github.protocol.gitlab.client

class GitLabClient(configuration: Configuration) {
    private val httpClient = InnerHttpClient(configuration)

    val projects = Projects(httpClient)
    val protectedBranches = ProtectedBranches(httpClient)
    val users = Users(httpClient)
}
