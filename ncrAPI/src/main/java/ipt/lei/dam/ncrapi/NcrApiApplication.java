package ipt.lei.dam.ncrapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NcrApiApplication extends ServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(NcrApiApplication.class, args);
    }

}
