package br.com.escambo.app;

import br.com.escambo.app.model.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;

@Component
public class MaintenanceInterceptor implements HandlerInterceptor {

    @Autowired
    private MaintenanceService maintenanceService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String uri = request.getRequestURI();
        // Permite acesso à área administrativa mesmo em manutenção
        if (maintenanceService.isMaintenanceMode() && !uri.startsWith("/admin")) {
            response.sendRedirect("/maintenance");
            return false;
        }
        return true;
    }
}