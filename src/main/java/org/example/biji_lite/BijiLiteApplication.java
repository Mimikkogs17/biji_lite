package org.example.biji_lite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.biji_lite.mapper")
public class BijiLiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(BijiLiteApplication.class, args);
    }
}