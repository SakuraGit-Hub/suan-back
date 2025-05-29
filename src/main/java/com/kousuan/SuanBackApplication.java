package com.kousuan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SuanBackApplication {

    public static void main(String[] args) {
        // 禁用重启类加载器
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(SuanBackApplication.class, args);
    }

}
