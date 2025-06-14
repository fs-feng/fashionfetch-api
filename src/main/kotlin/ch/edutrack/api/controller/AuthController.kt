package ch.edutrack.api.controller

import ch.edutrack.api.dto.LoginRequest
import ch.edutrack.api.service.TokenService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController (
    private val tokenService: TokenService,
    private val authenticationManager: AuthenticationManager,
) {

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(AuthController::class.java)
    }

    @PostMapping("/token")
    fun token(@RequestBody userLogin: LoginRequest): String {
        val auth: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                userLogin.username,
                userLogin.password
        ))
        LOG.info("User '{}' authenticated with roles: {}", auth.name, auth.authorities)

        return tokenService.generateToken(auth)

    }
}