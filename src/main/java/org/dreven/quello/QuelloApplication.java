package org.dreven.quello;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"org.dreven.quello.**.mapper"})
public class QuelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuelloApplication.class, args);
    }

}
