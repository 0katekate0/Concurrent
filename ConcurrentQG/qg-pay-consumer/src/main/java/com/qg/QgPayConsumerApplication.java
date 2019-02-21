package com.qg;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration
public class QgPayConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(QgPayConsumerApplication.class, args);
	}

}

