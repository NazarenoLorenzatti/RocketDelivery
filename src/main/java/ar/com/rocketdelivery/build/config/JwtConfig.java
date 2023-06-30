package ar.com.rocketdelivery.build.config;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;


@Configuration
public class JwtConfig {
	private static final Logger log = LoggerFactory.getLogger(JwtConfig.class);	
	private JksProperties jksProperties;

	public JwtConfig(JksProperties jksProperties) {
		this.jksProperties = jksProperties;
	}
	
	
	@Bean
	public KeyStore keyStore() {
		try {
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(this.jksProperties.keystorePath());
			keyStore.load(resourceAsStream, this.jksProperties.keystorePassword().toCharArray());
			return keyStore;
		} catch (IOException e) {
			log.error("Error al cargar keystore: " + e.toString());
		} catch(CertificateException e) {
			log.error("Error al cargar keystore: " + e.toString());
		} catch(NoSuchAlgorithmException e) {
			log.error("Error al cargar keystore: " + e.toString());
		} catch(KeyStoreException e) {
			log.error("Error al cargar keystore: " + e.toString());
		}
		
		throw new IllegalArgumentException("No se pudo cargar el keystore");
	}
	
	
	@Bean
	public RSAPublicKey jwtValidationKey(KeyStore keyStore) {
		try {
			Certificate certificate = keyStore.getCertificate(this.jksProperties.keystoreKeyAlias());
			PublicKey publicKey = certificate.getPublicKey();
			
			if (publicKey instanceof RSAPublicKey) {
				return (RSAPublicKey) publicKey;
			}
		} catch (KeyStoreException e) {
			log.error("No se pudo recuperar la clave privada del keystore");
		}
		
		throw new IllegalArgumentException("No se pudo cargar la clave publica");
	}	

	
	@Bean
	public RSAPrivateKey jwtSigningKey(KeyStore keyStore) {
		try {
			Key key = keyStore.getKey(this.jksProperties.keystoreKeyAlias(), this.jksProperties.keystorePrivateKeyPassphrase().toCharArray());
			
			if (key instanceof RSAPrivateKey) {
				return (RSAPrivateKey) key;
			}
		} catch (UnrecoverableKeyException e) {
			log.error("No se pudo recuperar la clave privada del keystore: " + e.toString());
		} catch (NoSuchAlgorithmException e) {
			log.error("No se pudo recuperar la clave privada del keystore: " + e.toString());
		} catch (KeyStoreException e) {
			log.error("No se pudo recuperar la clave privada del keystore: " + e.toString());
		}
		
		throw new IllegalArgumentException("No se pudo cargar la clave privada");
	}	
	
	
	@Bean
	public JwtDecoder jwtDecoder(RSAPublicKey rsaPublicKey) {
		return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
	}

}