package ch.fashionfetch.api.service

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors

@Component
class TokenService (
    private val encoder: JwtEncoder
) {

    fun generateToken(authentication: Authentication): String{
        val now: Instant = Instant.now()

        val roles: List<String> = authentication.authorities.map {
            if (it.authority.startsWith("ROLE_")) it.authority else "ROLE_${it.authority}"
        }
        
        val claims: JwtClaimsSet = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plus(1, ChronoUnit.DAYS))
            .subject(authentication.name)
            .claim("roles", roles)
            .build()

        println("Generated JWT claims: ")
        println("Roles: ${claims.claims["roles"]}")

        return encoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }

}