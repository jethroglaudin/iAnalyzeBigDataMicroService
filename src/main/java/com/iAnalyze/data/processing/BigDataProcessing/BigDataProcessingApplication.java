package com.iAnalyze.data.processing.BigDataProcessing;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableEurekaClient
public class BigDataProcessingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigDataProcessingApplication.class, args);
	}

}
