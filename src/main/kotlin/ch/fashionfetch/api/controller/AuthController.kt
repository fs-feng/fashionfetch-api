package ch.fashionfetch.api.controller

import ch.fashionfetch.api.dto.LoginRequest
import ch.fashionfetch.api.service.TokenService
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController (
    private val tokenService: TokenService,
    private val authenticationManager: AuthenticationManager,
) {

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(AuthController::class.java)
    }
    /**
    @GetMapping("/me")
    fun me(authentication: Authentication?): ResponseEntity<*> {
        if (authentication == null || !authentication.isAuthenticated) {
            return ResponseEntity.status(401).body(mapOf("error" to "Unauthorized"))
        }

        return ResponseEntity.ok(
            mapOf(
                "username" to authentication.name,
                "roles" to authentication.authorities.map { it.authority }
            )
        )
    }

    @GetMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Map<String, String>> {
        response.addHeader(
            "Set-Cookie",
            "jwtToken=; Path=/; Max-Age=0; HttpOnly; SameSite=Lax; Secure"
        )
        return ResponseEntity.ok(mapOf("message" to "Logged out successfully"))
    }

    @PostMapping("/login")
    fun token(@RequestBody userLogin: LoginRequest,  response: HttpServletResponse): ResponseEntity<Map<String, String>> {
        val auth: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(userLogin.username, userLogin.password)
        )

        val jwtToken = tokenService.generateToken(auth)

        response.addHeader("Set-Cookie", "jwtToken=$jwtToken; HttpOnly; Secure; Path=/; Max-Age=86400; SameSite=Strict")

        return ResponseEntity.ok(mapOf("message" to "Login successful"))
    }
    **/

}