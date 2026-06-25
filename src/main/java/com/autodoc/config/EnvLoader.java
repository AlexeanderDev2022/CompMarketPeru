package com.autodoc.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class EnvLoader {

    @PostConstruct
    public void init() {

        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        String bd = dotenv.get("BD");

        if (bd != null && !bd.isBlank()) {
            System.setProperty("BD", bd);
        } else {
            System.out.println("⚠️ BD no está definida en .env ni en variables de entorno");
        }
    }
}