package com.kgisl.user_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kgisl.user_service.model.User;
import com.kgisl.user_service.repository.UserRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            // Add admin user
            repository.save(new User("admin", "adminpassword", "admin", null));

            // Add a student user with studentId = 1
            repository.save(new User("student", "studentpassword", "student", 1L));
        };
    }
}

