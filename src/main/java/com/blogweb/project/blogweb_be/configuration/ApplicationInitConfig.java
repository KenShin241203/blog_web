package com.blogweb.project.blogweb_be.configuration;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blogweb.project.blogweb_be.entity.User;
import com.blogweb.project.blogweb_be.repository.RoleRepository;
import com.blogweb.project.blogweb_be.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {

        return args -> {
            // Initialize default admin user if not exists
            String adminUsername = "admin";
            if (userRepository.findByUsername(adminUsername).isEmpty()) {
                var adminUser = new User();
                adminUser.setUsername(adminUsername);
                adminUser.setPassword(passwordEncoder.encode("admin"));
                var roleUser = roleRepository.findById("ADMIN");
                adminUser.setRoles(new HashSet<>(roleUser.stream().toList()));
                userRepository.save(adminUser);
            }
        };
    }
}
