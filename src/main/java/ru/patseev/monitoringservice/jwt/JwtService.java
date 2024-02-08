package ru.patseev.monitoringservice.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.manager.ResourceManager;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * The JwtService class provides methods for JWT token generation, extraction, and validation.
 */
public class JwtService {

	/**
	 * The secret key used for JWT token generation and validation.
	 */
	private final String secretKey;

	/**
	 * The expiration time for JWT tokens, specified in milliseconds.
	 */
	private final Long expirationData;

	/**
	 * Constructs a JwtService instance.
	 *
	 * @param resourceManager The resource manager for accessing configuration properties.
	 */
	public JwtService(ResourceManager resourceManager) {
		secretKey = resourceManager.getValue("secret.key");
		expirationData = Long.parseLong(resourceManager.getValue("jwt.expiration"));
	}

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
				.expiration(new Date(System.currentTimeMillis() + expirationData))
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
	 * Extracts the username from the JWT token.
	 *
	 * @param jwt The JWT token from which to extract the username.
	 * @return The extracted username.
	 */
	public String extractUsername(String jwt) {
		return extractClaim(jwt, Claims::getSubject);
	}

	/**
	 * Extracts the player ID from the JWT token.
	 *
	 * @param jwt The JWT token from which to extract the player ID.
	 * @return The extracted player ID.
	 */
	public int extractPlayerId(String jwt) {
		return extractClaim(jwt, claims -> claims.get("userId", Integer.class));
	}

	/**
	 * Extracts the role from the JWT token.
	 *
	 * @param jwt The JWT token from which to extract the role.
	 * @return The extracted role.
	 */
	public String extractRole(String jwt) {
		return extractClaim(jwt, claims -> claims.get("role", String.class));
	}

	/**
	 * Extracts a specific claim from the provided JWT token.
	 *
	 * @param jwt           The JWT token from which to extract the claim.
	 * @param claimsResolver The function to resolve the desired claim from the JWT claims.
	 * @param <T>           The type of the extracted claim.
	 * @return The extracted claim.
	 */
	private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(jwt);
		return claimsResolver.apply(claims);
	}

	/**
	 * Extracts all claims from the provided JWT token.
	 *
	 * @param jwt The JWT token from which to extract all claims.
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