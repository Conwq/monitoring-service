package ru.patseev.monitoringservice.in.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.patseev.monitoringservice.aspect.annotation.Audit;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.exception.UserAlreadyExistException;
import ru.patseev.monitoringservice.exception.UserNotFoundException;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.jwt.JwtService;
import ru.patseev.monitoringservice.in.validator.Validator;
import ru.patseev.monitoringservice.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller class for handling user-related operations.
 */
@RestController
@RequestMapping("/users")
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
	 * Response generator for handling responses.
	 */
	private final ResponseGenerator responseGenerator;

	/**
	 * Validator for UserDto objects.
	 */
	private final Validator<UserDto> userDtoValidator;

	/**
	 * Constructs a new UserController with the specified dependencies.
	 *
	 * @param userService       The service for user-related operations.
	 * @param jwtService        The service for JWT operations.
	 * @param responseGenerator The response generator for handling responses.
	 * @param userDtoValidator  Validator for UserDto objects.
	 */
	@Autowired
	public UserController(UserService userService,
						  JwtService jwtService,
						  ResponseGenerator responseGenerator,
						  Validator<UserDto> userDtoValidator) {
		this.userService = userService;
		this.jwtService = jwtService;
		this.responseGenerator = responseGenerator;
		this.userDtoValidator = userDtoValidator;
	}

	/**
	 * Saves user data and generates a JWT token based on the saved user data.
	 *
	 * @param userDto The user data to be saved.
	 * @return The JWT token generated based on the saved user data.
	 */
	@Audit
	@PostMapping("/register")
	public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) {
		try {
			if (userDtoValidator.validate(userDto)) {
				return responseGenerator.generateResponse(HttpStatus.BAD_REQUEST, "The data is not valid");
			}
			UserDto savedUserData = userService.saveUser(userDto);
			String jwtToken = jwtService.generateToken(createExtraClaims(savedUserData), savedUserData);
			return responseGenerator.generateResponse(HttpStatus.OK, jwtToken);
		} catch (UserAlreadyExistException e) {
			return responseGenerator.generateResponse(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Authenticates a user using the UserService and generates a JWT token with extra claims.
	 *
	 * @param userDto The data transfer object containing user authentication information.
	 * @return A JWT token containing extra claims such as user role and user ID.
	 */
	@Audit
	@PostMapping("/auth")
	public ResponseEntity<?> authUser(@RequestBody UserDto userDto) {
		try {
			UserDto userData = userService.authUser(userDto);
			String jwtToken = jwtService.generateToken(createExtraClaims(userData), userDto);
			if (jwtToken == null || jwtToken.isEmpty()) {
				return responseGenerator.generateResponse(HttpStatus.UNAUTHORIZED, "Unauthorized");
			}
			return responseGenerator.generateResponse(HttpStatus.OK, jwtToken);
		} catch (UserNotFoundException e) {
			return responseGenerator.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	/**
	 * Retrieves a user DTO by the specified username.
	 *
	 * @param username The username of the user to retrieve.
	 * @return A UserDto object representing the user with the specified username, or null if the user is not found.
	 */
	@GetMapping
	public UserDto getUser(@RequestParam("username") String username) {
		return userService.getUser(username);
	}

	/**
	 * Creates extra claims for JWT token based on user data.
	 *
	 * @param userData The user data.
	 * @return A map containing extra claims such as user role and user ID.
	 */
	private Map<String, Object> createExtraClaims(UserDto userData) {
		return new HashMap<>() {{
			put("role", userData.role());
			put("userId", userData.userId());
		}};
	}
}
