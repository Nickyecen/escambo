package br.com.escambo.app.model;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.escambo.app.model.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

