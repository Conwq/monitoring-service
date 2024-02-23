package ru.patseev.auditstarter.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.function.Function;

/**
 * The JwtService class provides methods for JWT token generation, extraction, and validation.
 */
public class JwtAuditService {

	/**
	 * The secret key used for JWT token generation and validation.
	 */
	@Value("${secret.key}")
	private String secretKey;

	/**
	 * Generates a SecretKey based on the secret key stored in Base64 format.
	 *
	 * @return The generated SecretKey.
	 */
	private SecretKey generateKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * Extracts the player ID from the JWT token.
	 *
	 * @param jwt The JWT token from which to extractor the player ID.
	 * @return The extracted player ID.
	 */
	public int extractPlayerId(String jwt) {
		return extractClaim(jwt, claims -> claims.get("userId", Integer.class));
	}

	/**
	 * Extracts a specific claim from the provided JWT token.
	 *
	 * @param jwt            The JWT token from which to extractor the claim.
	 * @param claimsResolver The function to resolve the desired claim from the JWT claims.
	 * @param <T>            The type of the extracted claim.
	 * @return The extracted claim.
	 */
	private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(jwt);
		return claimsResolver.apply(claims);
	}

	/**
	 * Extracts all claims from the provided JWT token.
	 *
	 * @param jwt The JWT token from which to extractor all claims.
	 * @return The claims extracted from the JWT token.
	 */
	private Claims extractAllClaims(String jwt) {
		return Jwts.parser()
				.verifyWith(generateKey())
				.build()
				.parseSignedClaims(jwt)
				.getPayload();
	}
}