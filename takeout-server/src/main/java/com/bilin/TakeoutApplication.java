package com.bilin;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableCaching
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@MapperScan("com.bilin.mapper")
public class TakeoutApplication {
    public static void main(String[] args) {
        SpringApplication.run(TakeoutApplication.class, args);
        log.info("server started");
    }
}
