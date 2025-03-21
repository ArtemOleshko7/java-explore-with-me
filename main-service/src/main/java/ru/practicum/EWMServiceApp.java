package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EWMServiceApp {

    public static void main(String[] args) {
        // Запуск Spring Boot приложения с указанным классом и аргументами командной строки
        SpringApplication.run(EWMServiceApp.class, args);
    }
}