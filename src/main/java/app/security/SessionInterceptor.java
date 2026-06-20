package app.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    private static final Set<String> UNAUTHENTICATED_ENDPOINTS = Set.of(
            "/", "/login", "/register", "/error", "/css", "/js", "/images"
    );

    // Endpoints that require MANAGER role (adjust as needed)
    private static final Set<String> MANAGER_ENDPOINTS = Set.of(
            "/users", "/reports", "/equipment/new", "/tasks/new", "/worktask/new"
    );

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String endpoint = request.getServletPath();

        if (UNAUTHENTICATED_ENDPOINTS.contains(endpoint) || endpoint.startsWith("/static") ) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/login");
            return false;
        }

        Object userId = session.getAttribute("userId");
        if (userId == null) {
            session.invalidate();
            response.sendRedirect("/login");
            return false;
        }

        Object roleObj = session.getAttribute("userRole");
        String role = roleObj != null ? roleObj.toString() : null;

        if (MANAGER_ENDPOINTS.contains(endpoint) && (role == null || !role.equals("MANAGER"))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Нямате права за достъп до този ресурс.");
            return false;
        }

        return true;
    }
}
