package kreasi.karya.solusi.crud_backend_java.controller;

import kreasi.karya.solusi.crud_backend_java.dto.*;
import kreasi.karya.solusi.crud_backend_java.entity.User;
import kreasi.karya.solusi.crud_backend_java.service.UserService;
import kreasi.karya.solusi.crud_backend_java.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPassword());

        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String token = jwtUtil.generateToken(request.getEmail());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activate(@RequestBody ActivationRequest request) {
        userService.activateUser(request.getEmail(), request.getToken());
        return ResponseEntity.ok("User activated");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> requestReset(@RequestBody PasswordDto request) {
        userService.requestPasswordReset(request.getEmail());
        return ResponseEntity.ok("Reset token generated (check logs)");
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<?> confirmReset(@RequestBody PasswordDto request) {
        userService.resetPassword(request.getEmail(), request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Password reset successfully");
    }
}
