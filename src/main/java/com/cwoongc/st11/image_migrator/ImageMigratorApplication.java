package com.cwoongc.st11.image_migrator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.cwoongc.st11.de_closing")
public class ImageMigratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageMigratorApplication.class, args);
	}

	@Bean
	public Gson gson() {
		return new GsonBuilder().serializeNulls().create();
	}
}
