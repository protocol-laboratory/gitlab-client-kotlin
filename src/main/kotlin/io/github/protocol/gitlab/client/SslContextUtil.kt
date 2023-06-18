package io.github.protocol.gitlab.client

import java.io.File
import java.io.FileInputStream
import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class SslContextUtil(private val configuration: Configuration) {

    private var trustManager: X509TrustManager? = null

    fun getSslContext(): SSLContext {
        val keyStorePath = configuration.keyStorePath
            ?: throw IllegalStateException("KeyStore path must be provided in the configuration")
        val trustStorePath = configuration.trustStorePath
            ?: throw IllegalStateException("TrustStore path must be provided in the configuration")

        val keyStorePassword = configuration.keyStorePassword?.toCharArray()
        val trustStorePassword = configuration.trustStorePassword?.toCharArray()

        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        FileInputStream(File(keyStorePath)).use { fis ->
            keyStore.load(fis, keyStorePassword)
        }

        val keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
        keyManagerFactory.init(keyStore, keyStorePassword)

        val trustStore = KeyStore.getInstance(KeyStore.getDefaultType())
        FileInputStream(File(trustStorePath)).use { fis ->
            trustStore.load(fis, trustStorePassword)
        }

        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(trustStore)

        val trustManagers = trustManagerFactory.trustManagers
        trustManager = trustManagers.first { it is X509TrustManager } as X509TrustManager

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(keyManagerFactory.keyManagers, trustManagers, null)

        return sslContext
    }

    fun getX509TrustManager(): X509TrustManager {
        return trustManager ?: throw IllegalStateException("Trust manager is not initialized")
    }
}
