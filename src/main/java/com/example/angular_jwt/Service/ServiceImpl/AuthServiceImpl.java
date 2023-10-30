package com.example.angular_jwt.Service.ServiceImpl;

import com.example.angular_jwt.Config.Auth.RegisterRequest;
import com.example.angular_jwt.Repository.UserRepository;
import com.example.angular_jwt.Service.AuthService;
import com.example.angular_jwt.user.Role;
import com.example.angular_jwt.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  UserRepository repository;
    @Override
    public User RegisterUser(RegisterRequest request) {
        var user=User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        return  repository.save(user);
    }

    @Override
    public Optional<User> findUser(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<User> editUser(Long id, User request) {
        System.out.println(request.getId()+ "This is my id ");
        User user=repository.getUserById(request.getId());
        user.setEmail(request.getEmail());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        repository.save(user);
        return repository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return repository.getUsersByEmail(email);
    }

    @Override
    public User getUser(Long id) {
        return repository.getUserById(id);
    }

    @Override
    public User saveUserImage(MultipartFile file, User user) {
        user.setImage(file.getOriginalFilename());
        upload(file);
        return repository.save(user);
    }
    String UPLOAD_FOLDER="C:\\Users\\hp\\Desktop\\Angular_JWT\\src\\main\\resources\\static\\images";
    public boolean upload(MultipartFile imageProduct){
        boolean isUpload=false;
        try {
            Files.copy(imageProduct.getInputStream(),
                    Paths.get(UPLOAD_FOLDER+ File.separator,
                            imageProduct.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            isUpload=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpload;
    }
}
