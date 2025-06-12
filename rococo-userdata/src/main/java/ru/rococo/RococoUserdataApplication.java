package ru.rococo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.rococo.service.PropertiesLogger;

@SpringBootApplication
public class RococoUserdataApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(RococoUserdataApplication.class);
        springApplication.addListeners(new PropertiesLogger());
        springApplication.run(args);
    }

}
