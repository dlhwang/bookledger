package com.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ComponentScan
@Slf4j
public class Application {
    public static void main(String[] args) {
        log.trace("############### BEGIN BookLedger API Application ###################");
        log.debug("############### BEGIN BookLedger API Application ###################");
        log.info("############### BEGIN BookLedger API Application ###################");

        try {
            SpringApplication.run(Application.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}