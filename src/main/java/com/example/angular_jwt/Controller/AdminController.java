package com.example.angular_jwt.Controller;

import com.example.angular_jwt.Config.Auth.RegisterRequest;
import com.example.angular_jwt.Service.AuthService;
import com.example.angular_jwt.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AuthService authService;

    @GetMapping("/users")
//    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers(){
        return authService.getAllUsers();
    }

    @PostMapping("/add-user")
    public User addUser(@RequestBody RegisterRequest request){
        return authService.RegisterUser(request);
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id")Long id){
        try {
            authService.deleteUser(id);
            return ResponseEntity.ok("User deleted");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or unable to delete");
        }
    }

    @PutMapping("/edit-user/{id}")
    public List<User> editUser(@PathVariable("id") Long id,@RequestBody User request){
        return authService.editUser(id,request);
    }

    @GetMapping("/get-user/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return  authService.getUser(id);
    }

    @PostMapping("/upload-image")
    public User uploadImage(@RequestParam MultipartFile file, Principal principal){
        User user = null;
        if(principal != null){
             user=authService.getUserByEmail(principal.getName());
        }
        User newUser=authService.saveUserImage(file,user);
        return newUser;
    }

    @GetMapping("/get-user")
    public User getCurrentUser(Principal principal){
        User user=authService.getUserByEmail(principal.getName());
        return user;
    }

}
