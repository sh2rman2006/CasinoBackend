package logtest.casinobackend.controller;

import logtest.casinobackend.dtos.LoginRequest;
import logtest.casinobackend.dtos.RegisterRequest;
import logtest.casinobackend.model.AuthUser;
import logtest.casinobackend.model.CustomRole;
import logtest.casinobackend.repository.AuthUserRepository;
import logtest.casinobackend.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private AuthUserRepository authUserRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            if (authUserRepository.existsByUsername(registerRequest.getUsername())
                    || authUserRepository.existsByEmail(registerRequest.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username or email already exists");
            }
            Set<CustomRole> roles = Set.of(CustomRole.USER);
            AuthUser authUser = new AuthUser(
                    registerRequest.getUsername(),
                    registerRequest.getEmail(),
                    passwordEncoder.encode(registerRequest.getPassword()),
                    roles
            );
            authUser.setBalance(new BigDecimal("10000.00"));
            authUser.setAccountNonLocked(true);
            authUserRepository.save(authUser);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("token", jwtService.generateToken(authUser.getUsername())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(Map.of("token", jwtService.generateToken(loginRequest.getUsername())));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authentication);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
