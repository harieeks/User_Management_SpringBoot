package com.example.angular_jwt.Service;

import com.example.angular_jwt.Config.Auth.RegisterRequest;
import com.example.angular_jwt.user.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AuthService {
     User RegisterUser(RegisterRequest request);
     Optional<User> findUser(String email);
     List<User> getAllUsers();
     void deleteUser(Long id);
     List<User> editUser(Long id,User request);
     User getUserByEmail(String email);
     User getUser(Long id);
     User saveUserImage(MultipartFile file,User user);
}
