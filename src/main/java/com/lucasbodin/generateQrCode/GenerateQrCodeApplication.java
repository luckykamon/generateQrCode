package com.lucasbodin.generateQrCode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.lucasbodin.generateQrCode"})
public class GenerateQrCodeApplication {
	public static void main(String[] args) {
		SpringApplication.run(GenerateQrCodeApplication.class, args);
	}
}

