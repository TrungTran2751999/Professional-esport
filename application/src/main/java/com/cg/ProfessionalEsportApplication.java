package com.cg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProfessionalEsportApplication {

    private static final Logger logger = LoggerFactory.getLogger(ProfessionalEsportApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ProfessionalEsportApplication.class, args);
        logger.info("Professional Esport Application Started.......");
    }

}
