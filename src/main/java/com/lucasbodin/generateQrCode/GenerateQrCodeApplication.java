package main.java.com.lucasbodin.generateQrCode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"main.java.com.lucasbodin.generateQrCode.controller"})
public class GenerateQrCodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenerateQrCodeApplication.class, args);
	}
}

