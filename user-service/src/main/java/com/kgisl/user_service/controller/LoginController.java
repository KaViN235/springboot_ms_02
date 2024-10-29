package com.kgisl.user_service.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kgisl.user_service.model.User;
import com.kgisl.user_service.service.UserService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    private UserService userService;

    // Endpoint to handle login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        Optional<User> user = userService.authenticate(username, password);

        if (user.isPresent()) {
            User authenticatedUser = user.get();
            Map<String, Object> response = new HashMap<>();
            response.put("role", authenticatedUser.getRole());

            if ("student".equals(authenticatedUser.getRole())) {
                response.put("studentId", authenticatedUser.getStudentId()); // Return studentId for students
            }

            // You can add a token generation here for future requests (optional)
            // response.put("token", "some-jwt-token");

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}
