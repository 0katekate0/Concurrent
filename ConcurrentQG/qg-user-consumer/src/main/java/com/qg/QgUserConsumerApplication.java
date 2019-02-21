package com.qg;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration
public class QgUserConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(QgUserConsumerApplication.class, args);
	}

}

