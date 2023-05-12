package main.java.com.lucasbodin.generateQrCode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@SpringBootApplication(scanBasePackages={"main.java.com.lucasbodin.generateQrCode"})
public class GenerateQrCodeApplication {
	public static void main(String[] args) {
		SpringApplication.run(GenerateQrCodeApplication.class, args);
	}
	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
}

