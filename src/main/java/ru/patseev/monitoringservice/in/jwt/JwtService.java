package ru.patseev.monitoringservice.in.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.patseev.monitoringservice.dto.UserDto;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * The JwtService class provides methods for JWT token generation, extraction, and validation.
 */
@Service
public class JwtService {

	/**
	 * The secret key used for JWT token generation and validation.
	 */
	@Value("${secret.key}")
	private String secretKey;

	/**
	 * The expiration time for JWT tokens, specified in milliseconds.
	 */
	@Value("${jwt.expiration}")
	private String expirationData;

	/**
	 * Generates a JWT token with the specified extra claims and user information.
	 *
	 * @param extraClaims Additional claims to include in the JWT token.
	 * @param userDto     The user information to include in the token.
	 * @return The generated JWT token.
	 */
	public String generateToken(Map<String, Object> extraClaims, UserDto userDto) {
		return Jwts.builder()
				.claims(extraClaims)
				.subject(userDto.username())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + Long.parseLong(expirationData)))
				.signWith(generateKey(), Jwts.SIG.HS256)
				.compact();
	}

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
	 * Extracts the role from the JWT token.
	 *
	 * @param jwt The JWT token from which to extractor the role.
	 * @return The extracted role.
	 */
	public String extractRole(String jwt) {
		return extractClaim(jwt, claims -> claims.get("role", String.class));
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