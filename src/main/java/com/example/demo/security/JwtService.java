package com.example.demo.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.provider.ResourceProvider;
//
////  making it a Spring-managed bean:
//@Component
//public class JwtService {
//
//	final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	// annotation to inject an instance of the ResourceProvider into the class,
//	// allowing access to properties related to our JWT settings:
//	@Autowired
//	ResourceProvider provider;
//
//	public String generateJwtToken(String username, long expiration) {
//
//		// JWT.create(): This creates a new JWT builder instance.
//		return JWT.create().withIssuer(this.provider.getJwtIssuer()) // This sets the issuer claim of the JWT to the
//																		// value retrieved from the provider object.
//				.withAudience(this.provider.getJwtAudience()) // This sets the audience's claim for the JWT to the value
//																// retrieved from the provider object.
//				.withIssuedAt(new Date()) // This sets the "issued at" claim for the JWT to the current date and time.
//				.withSubject(username) // This sets the subject claim of the JWT to the given username.
//				.withExpiresAt(new Date(System.currentTimeMillis() + expiration)) // This sets the expiration time of
//																					// the JWT to the current time plus
//																					// the given expiration time in
//																					// milliseconds.
//				.sign(HMAC512(this.provider.getJwtSecret())); // This signs the JWT using the HMAC512 algorithm with the
//																// secret key retrieved from the provider object.
//	}
//
//	// If the token is valid, a DecodedJWT object is returned, which contains the
//	// claims of the JWT.
//	public DecodedJWT verifyJwtToken(String token) {
//
//		return JWT.require(HMAC512(this.provider.getJwtSecret())) // method creates a verifier object that requires a
//																	// signature algorithm and a secret key to verify
//																	// the token.
//				.withIssuer(this.provider.getJwtIssuer()) // method sets the issuer claim of the JWT to the value
//															// retrieved from the provider object using the
//															// getJwtIssuer() method.
//				.build() // method builds the verifier object with the specified requirements.
//				.verify(token); // method of the verifier object is called with the token parameter to verify
//								// the JWT.
//	}
//
//	public String getSubject(String token) {
//
//		return JWT.require(HMAC512(this.provider.getJwtSecret())) // method creates a new verifier object that requires
//																	// a signature algorithm and a secret key to verify
//																	// the token.
//				.withIssuer(this.provider.getJwtIssuer()) // method sets the issuer claim of the JWT to the value
//															// retrieved from the provider object using the
//															// getJwtIssuer() method.
//				.build() // method builds the verifier object with the specified requirements.
//				.verify(token) // method of the verifier object is called with the token parameter to verify
//								// the JWT. If the token is valid, a DecodedJWT object is returned, which
//								// contains the claims of the JWT.
//				.getSubject(); // method of the DecodedJWT object is called to extract the subject claim from
//								// the token, which is then returned by the getSubject(String token) method.
//	}
//

@Component
public class JwtService {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ResourceProvider provider;

// create a new token
	public String generateJwtToken(String username, long expiration) {

		return JWT.create().withIssuer(this.provider.getJwtIssuer()).withAudience(this.provider.getJwtAudience())
				.withIssuedAt(new Date()).withSubject(username)
				.withExpiresAt(new Date(System.currentTimeMillis() + expiration))
				.sign(HMAC512(this.provider.getJwtSecret()));
	}

// verify the token to make sure the generated hash == signature
	public DecodedJWT verifyJwtToken(String token) {

		return JWT.require(HMAC512(this.provider.getJwtSecret())).withIssuer(this.provider.getJwtIssuer()).build()
				.verify(token);
	}

//Decode the token to get the user name
	public String getSubject(String token) {

		return JWT.require(HMAC512(this.provider.getJwtSecret())).withIssuer(this.provider.getJwtIssuer()).build()
				.verify(token).getSubject();
	}


}