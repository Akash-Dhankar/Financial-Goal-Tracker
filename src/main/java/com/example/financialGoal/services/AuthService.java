package com.example.financialGoal.services;

import com.example.financialGoal.auth.AuthRequest;
import com.example.financialGoal.auth.AuthResponse;
import com.example.financialGoal.models.Role;
import com.example.financialGoal.models.User;
import com.example.financialGoal.repositories.RoleRepository;
import com.example.financialGoal.repositories.UserRepository;
import com.example.financialGoal.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Collections;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String register(AuthRequest request) {
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        String requestedRole = request.getRole();
        if (requestedRole == null || (!requestedRole.equals("ROLE_USER") && !requestedRole.equals("ROLE_ADMIN"))) {
            throw new RuntimeException("Invalid role");
        }

        Role userRole = roleRepo.findByName(requestedRole);
        user.setRoles(Collections.singleton(userRole));


        userRepo.save(user);
        return "User registered successfully!";
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .toList()
        );
        String token = jwtUtils.generateTokenWithAuthorities(userDetails);
        return new AuthResponse(token);
    }
}
