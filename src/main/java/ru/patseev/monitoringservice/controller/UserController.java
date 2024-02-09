package ru.patseev.monitoringservice.controller;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.enums.ActionEnum;
import ru.patseev.monitoringservice.jwt.JwtService;
import ru.patseev.monitoringservice.service.AuditService;
import ru.patseev.monitoringservice.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller class for handling user-related operations.
 */
@RequiredArgsConstructor
public class UserController {

	/**
	 * The service responsible for user-related business logic.
	 */
	private final UserService userService;

	/**
	 * The service responsible for auditing user actions.
	 */
	private final AuditService auditService;

	/**
	 * Service responsible for generating and parsing JSON Web Tokens (JWT).
	 */
	private final JwtService jwtService;

	/**
	 * Saves user data and generates a JWT token based on the saved user data.
	 *
	 * @param userDto The user data to be saved.
	 * @return The JWT token generated based on the saved user data.
	 */
	public String saveUser(UserDto userDto) {
		UserDto savedUserData = userService.saveUser(userDto);
		auditService.saveUserAction(ActionEnum.REGISTRATION, savedUserData.userId());

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
	public String authUser(UserDto userDto) {
		UserDto userData = userService.authUser(userDto);

		auditService.saveUserAction(ActionEnum.AUTHORIZATION, userData.userId());

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