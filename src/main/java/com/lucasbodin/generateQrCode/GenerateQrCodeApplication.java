package com.lucasbodin.generateqrcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.lucasbodin.generateqrcode"})
public class GenerateQrCodeApplication {
	public static void main(String[] args) {
		SpringApplication.run(GenerateQrCodeApplication.class, args);
	}
}

