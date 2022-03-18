package com.example.laptop;

import com.example.laptop.db.entity.User;
import com.example.laptop.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Optional;

@SpringBootApplication
@EnableSwagger2
public class LaptopApplication {
    public static void main(String[] args) {
        SpringApplication.run(LaptopApplication.class, args);
    }
}
