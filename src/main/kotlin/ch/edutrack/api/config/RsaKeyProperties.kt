package ch.edutrack.api.config

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "rsa")
data class RsaKeyProperties (
    val publicKeyPath: String,
    val privateKeyPath: String
)