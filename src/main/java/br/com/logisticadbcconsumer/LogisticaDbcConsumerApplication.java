package br.com.logisticadbcconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class LogisticaDbcConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticaDbcConsumerApplication.class, args);
	}

}
