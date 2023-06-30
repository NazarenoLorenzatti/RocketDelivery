package ar.com.rocketdelivery.build.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class JwtUtil {
	private RSAPrivateKey privateKey;
	private RSAPublicKey publicKey;

	public JwtUtil(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}


	public String encode(String subject) {
		return JWT.create()
				.withSubject(subject)
				.withExpiresAt(null)
				.sign(Algorithm.RSA256(publicKey, privateKey) );
	}

}