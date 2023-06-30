package ar.com.rocketdelivery.build;

import ar.com.rocketdelivery.build.config.JksProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JksProperties.class)
public class RocketDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(RocketDeliveryApplication.class, args);
	}

}
