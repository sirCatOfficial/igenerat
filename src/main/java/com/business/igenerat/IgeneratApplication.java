package com.business.igenerat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.business.igenerat.dao")
public class IgeneratApplication {

    public static void main(String[] args) {
        SpringApplication.run(IgeneratApplication.class, args);
    }

}
