package br.com.escambo.app.model;

import br.com.escambo.app.model.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUsernameOrderByTimestampDesc(String username);
}