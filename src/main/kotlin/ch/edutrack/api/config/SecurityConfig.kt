package ch.edutrack.api.config

import ch.edutrack.api.service.CustomUserDetailService
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.web.SecurityFilterChain
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val userDetailService: CustomUserDetailService,
    private val rsaKeyLoader: RsaKeyLoader
) {

    @Bean
    fun rsaPublicKey(): RSAPublicKey = rsaKeyLoader.getPublicKey()

    @Bean
    fun rsaPrivateKey(): RSAPrivateKey = rsaKeyLoader.getPrivateKey()

    @Bean
    fun authManager(userDetailsService: CustomUserDetailService): AuthenticationManager {
        val authProvider: DaoAuthenticationProvider = DaoAuthenticationProvider(userDetailsService).apply {
            setPasswordEncoder(passwordEncoder())
        }
        return ProviderManager(authProvider)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf{ csrf -> csrf.disable() }
            .authorizeHttpRequests { auth -> auth
                .requestMatchers("/token").permitAll()
                .anyRequest().authenticated()
            }
            .oauth2ResourceServer { auth -> auth.jwt { it } }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .userDetailsService(userDetailService)
            .build();

    @Bean
    fun jwtDecoder(): JwtDecoder =
        NimbusJwtDecoder.withPublicKey(rsaPublicKey()).build()

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk: JWK = RSAKey.Builder(rsaPublicKey()).privateKey(rsaPrivateKey()).build()
        val jwks: JWKSource<SecurityContext> = ImmutableJWKSet(JWKSet(jwk))

        return NimbusJwtEncoder(jwks)
    }


    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}