package io.github.protocol.gitlab.client

class GitLabException : Exception {
    private val statusCode: Int

    constructor(t: Throwable) : super(t) {
        statusCode = DEFAULT_STATUS_CODE
    }

    constructor(message: String) : this(message, DEFAULT_STATUS_CODE)

    constructor(message: String, statusCode: Int) : super(message) {
        this.statusCode = statusCode
    }
}
