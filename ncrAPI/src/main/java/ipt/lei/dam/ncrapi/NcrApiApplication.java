package ipt.lei.dam.ncrapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication
public class NcrApiApplication{

    public static void main(String[] args) {
        SpringApplication.run(NcrApiApplication.class, args);
    }

}
