package ru.patseev.monitoringservice.controller;

import ru.patseev.monitoringservice.aspect.annotation.Loggable;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.jwt.JwtService;
import ru.patseev.monitoringservice.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller class for handling user-related operations.
 */
public class UserController {

	/**
	 * The service responsible for user-related business logic.
	 */
	private final UserService userService;

	/**
	 * Service responsible for generating and parsing JSON Web Tokens (JWT).
	 */
	private final JwtService jwtService;

	/**
	 * Constructs a new UserController with the specified dependencies.
	 *
	 * @param userService The service for user-related operations
	 * @param jwtService  The service for JWT operations
	 */
	public UserController(UserService userService, JwtService jwtService) {
		this.userService = userService;
		this.jwtService = jwtService;
	}

	/**
	 * Saves user data and generates a JWT token based on the saved user data.
	 *
	 * @param userDto The user data to be saved.
	 * @return The JWT token generated based on the saved user data.
	 */
	@Loggable
	public String saveUser(UserDto userDto) {
		UserDto savedUserData = userService.saveUser(userDto);

		Map<String, Object> extraClaims = new HashMap<>() {{
			put("role", savedUserData.role());
			put("userId", savedUserData.userId());
		}};

		return jwtService.generateToken(extraClaims, savedUserData);
	}

	/**
	 * Authenticates a user using the UserService and generates a JWT token with extra claims.
	 *
	 * @param userDto The data transfer object containing user authentication information.
	 * @return A JWT token containing extra claims such as user role and user ID.
	 */
	@Loggable
	public String authUser(UserDto userDto) {
		UserDto userData = userService.authUser(userDto);

		Map<String, Object> extraClaims = new HashMap<>() {{
			put("role", userData.role());
			put("userId", userData.userId());
		}};

		return jwtService.generateToken(extraClaims, userDto);
	}

	/**
	 * Retrieves a user DTO by the specified username.
	 *
	 * @param username The username of the user to retrieve.
	 * @return A UserDto object representing the user with the specified username,or null if the user is not found.
	 */
	public UserDto getUser(String username) {
		return userService.getUser(username);
	}
}