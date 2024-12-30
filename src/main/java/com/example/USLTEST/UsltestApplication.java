package com.example.USLTEST;

import com.example.USLTEST.domain.entity.Role;
import com.example.USLTEST.domain.entity.UserEntity;
import com.example.USLTEST.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class UsltestApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsltestApplication.class, args);

	}
	@Bean
	public CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			if (userRepository.findByEmail("admin-ticket-booking@gmail.com").isEmpty()) {
				UserEntity admin = new UserEntity();
				admin.setEmail("admin-ticket-booking@gmail.com");
				admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
				admin.setConfirmPassword(new BCryptPasswordEncoder().encode("admin"));
				admin.setRole(Role.ADMIN);
				userRepository.save(admin);
			}
		};
	}
}

