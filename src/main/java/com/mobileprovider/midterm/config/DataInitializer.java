package com.mobileprovider.midterm.config;

import com.mobileprovider.midterm.entity.AppUser;
import com.mobileprovider.midterm.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(AppUserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                AppUser user = new AppUser("admin", "1234"); // Şifre hash'lenmeden düz yazılıyor
                userRepository.save(user);
                System.out.println("Default user 'admin' created.");
            }
        };
    }
}
