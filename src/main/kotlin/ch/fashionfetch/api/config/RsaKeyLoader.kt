package ch.fashionfetch.api.config

import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

@Component
class RsaKeyLoader(private val rsaProperties: RsaKeyProperties) {

    fun getPublicKey(): RSAPublicKey {
        val keyBytes = getKeyFromClasspath(rsaProperties.publicKeyPath)
        return convertToPublicKey(keyBytes)
    }

    fun getPrivateKey(): RSAPrivateKey {
        val keyBytes = getKeyFromClasspath(rsaProperties.privateKeyPath)
        return convertToPrivateKey(keyBytes)
    }

    private fun getKeyFromClasspath(path: String): ByteArray {
        val resource = ClassPathResource(path.replace("classpath:", ""))
        return resource.inputStream.readBytes()
    }

    private fun stripPemHeader(pemBytes: ByteArray): ByteArray {
        val pemContent = String(pemBytes)
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replace("\n", "")
            .trim()

        return java.util.Base64.getDecoder().decode(pemContent)
    }

    private fun convertToPublicKey(keyBytes: ByteArray): RSAPublicKey {
        val keyFactory = KeyFactory.getInstance("RSA")
        val spec = X509EncodedKeySpec(stripPemHeader(keyBytes))
        return keyFactory.generatePublic(spec) as RSAPublicKey
    }

    private fun convertToPrivateKey(keyBytes: ByteArray): RSAPrivateKey {
        val keyFactory = KeyFactory.getInstance("RSA")
        val spec = PKCS8EncodedKeySpec(stripPemHeader(keyBytes))
        return keyFactory.generatePrivate(spec) as RSAPrivateKey
    }
}
