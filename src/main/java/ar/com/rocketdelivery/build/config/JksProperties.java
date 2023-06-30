package ar.com.rocketdelivery.build.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jks")
public record JksProperties(String keystorePath, String keystorePassword, String keystoreKeyAlias, String keystorePrivateKeyPassphrase) {

}
