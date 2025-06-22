package br.com.escambo.app.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.escambo.app.model.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findByEmail(String email);
}
