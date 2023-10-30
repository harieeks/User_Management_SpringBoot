package com.example.angular_jwt.Repository;

import com.example.angular_jwt.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    void deleteById(Long id);
    User getUserById(Long id);

    User getUsersByEmail(String email);
}
