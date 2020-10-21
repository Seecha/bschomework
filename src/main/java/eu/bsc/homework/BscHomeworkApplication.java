package eu.bsc.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BscHomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(BscHomeworkApplication.class, args);
    }

}