package ru.patseev.monitoringservice.in.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.patseev.monitoringservice.aspect.annotation.Audit;
import ru.patseev.monitoringservice.dto.UserDto;
import ru.patseev.monitoringservice.exception.UserAlreadyExistException;
import ru.patseev.monitoringservice.exception.UserNotFoundException;
import ru.patseev.monitoringservice.in.jwt.JwtService;
import ru.patseev.monitoringservice.in.validator.ErrorValidationExtractor;
import ru.patseev.monitoringservice.in.validator.UserValidator;
import ru.patseev.monitoringservice.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Controller class for handling user-related operations.
 */
@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	/**
	 * The service responsible for user-related business logic.
	 */
	private final UserService userService;

	/**
	 * Service responsible for generating and parsing JSON Web Tokens (JWT).
	 */
	private final JwtService jwtService;

	private final UserValidator userValidator;

	private final ErrorValidationExtractor errorValidationExtractor;

	@InitBinder(value = "userDto")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}

	/**
	 * Saves user data and generates a JWT token based on the saved user data.
	 *
	 * @param userDto The user data to be saved.
	 * @return The JWT token generated based on the saved user data.
	 */
	@Audit
	@Operation(summary = "Register a new user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully registered user and generated JWT token"),
			@ApiResponse(responseCode = "400", description = "Invalid data provided for registration"),
			@ApiResponse(responseCode = "409", description = "User already exists")
	})
	@PostMapping("/register")
	public ResponseEntity<?> saveUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				Set<String> setErrors = errorValidationExtractor.getErrorsFromBindingResult(bindingResult);
				return ResponseEntity.badRequest().body(setErrors);
			}
			UserDto savedUserData = userService.saveUser(userDto);
			String jwtToken = jwtService.generateToken(createExtraClaims(savedUserData), savedUserData);
			return ResponseEntity.ok(jwtToken);
		} catch (UserAlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

	/**
	 * Authenticates a user using the UserService and generates a JWT token with extra claims.
	 *
	 * @param userDto The data transfer object containing user authentication information.
	 * @return A JWT token containing extra claims such as user role and user ID.
	 */
	@Audit
	@Operation(summary = "Authenticate user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully authenticated user and generated JWT token"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "User not found")
	})
	@PostMapping("/auth")
	public ResponseEntity<?> authUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				Set<String> setErrors = errorValidationExtractor.getErrorsFromBindingResult(bindingResult);
				return ResponseEntity.badRequest().body(setErrors);
			}
			UserDto userData = userService.authUser(userDto);
			String jwtToken = jwtService.generateToken(createExtraClaims(userData), userDto);
			if (jwtToken == null || jwtToken.isEmpty()) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
			}
			return ResponseEntity.ok(jwtToken);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	/**
	 * Retrieves a user DTO by the specified username.
	 *
	 * @param username The username of the user to retrieve.
	 * @return A UserDto object representing the user with the specified username, or null if the user is not found.
	 */
	@Operation(summary = "Get user by username")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved user",
					content = {@Content(mediaType = "application/json",
							schema = @Schema(implementation = UserDto.class))}),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content)
	})
	@GetMapping
	public UserDto getUser(@Parameter(description = "The username of the user to retrieve")
						   @RequestParam("username") String username) {
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
