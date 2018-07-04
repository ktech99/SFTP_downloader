package com.mmt.sem.SEM_WORKFLOW_APP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.mmt.*"})
public class SemWorkflowAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SemWorkflowAppApplication.class, args);
	}
}
