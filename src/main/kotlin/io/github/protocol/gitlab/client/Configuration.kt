package io.github.protocol.gitlab.client

data class Configuration(
    var host: String = "localhost",
    var port: Int = 80,
    var token: String,
    var useTls: Boolean = false,
    var keyStorePath: String? = null,
    var keyStorePassword: String? = null,
    var trustStorePath: String? = null,
    var trustStorePassword: String? = null,
    var disableSslVerify: Boolean = false,
    var disableHostnameVerification: Boolean = false,
    var tlsProtocols: List<String>? = null,
    var tlsCiphers: List<String>? = null,
)
