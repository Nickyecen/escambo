package br.com.escambo.app;

import br.com.escambo.app.model.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Component
public class MaintenanceInterceptor implements HandlerInterceptor {

    @Autowired
    private MaintenanceService maintenanceService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String uri = request.getRequestURI();

        if (maintenanceService.isMaintenanceMode()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            boolean isAdmin = auth != null && auth.isAuthenticated() &&
                    auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

            if (!isAdmin) {
                response.sendRedirect("/maintenance");
                return false;
            }
        }

        return true;
    }
}
