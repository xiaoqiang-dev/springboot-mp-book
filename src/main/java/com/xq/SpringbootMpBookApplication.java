package com.xq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.xq.mapper")
@EnableSwagger2
public class SpringbootMpBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMpBookApplication.class, args);
    }

}
