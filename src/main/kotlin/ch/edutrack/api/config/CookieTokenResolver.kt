package ch.edutrack.api.config

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver

class CookieTokenResolver : BearerTokenResolver {
    override fun resolve(request: HttpServletRequest): String? {
        val cookies = request.cookies ?: return null
        return cookies.find { it.name == "jwtToken" }?.value
    }
}
