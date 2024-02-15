package ru.patseev.monitoringservice.in.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.patseev.monitoringservice.aspect.annotation.Audit;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.dto.MeterTypeDto;
import ru.patseev.monitoringservice.exception.DataMeterNotFoundException;
import ru.patseev.monitoringservice.exception.MeterDataWasSubmittedException;
import ru.patseev.monitoringservice.exception.MeterTypeExistException;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.jwt.JwtService;
import ru.patseev.monitoringservice.in.validator.Validator;
import ru.patseev.monitoringservice.service.MeterService;

import java.util.List;
import java.util.Map;

/**
 * The MeterController class serves as a controller for managing data meter operations.
 */
@RestController
@RequestMapping("/meters")
public class MeterController {

	/**
	 * The service responsible for data meter-related business logic.
	 */
	private final MeterService meterService;

	/**
	 * The JWT service responsible for user authentication and token management.
	 */
	private final JwtService jwtService;

	/**
	 * The ResponseGenerator used by the controller to generate responses.
	 */
	private final ResponseGenerator responseGenerator;

	/**
	 * Validator for DataMeterDto objects.
	 */
	private final Validator<DataMeterDto> dataMeterDtoValidator;

	/**
	 * Validator for MeterTypeDto objects.
	 */
	private final Validator<MeterTypeDto> meterTypeDtoValidator;

	/**
	 * Constructs a new MeterController with the specified dependencies.
	 *
	 * @param meterService          The service for meter operations.
	 * @param jwtService            The service for JWT operations.
	 * @param responseGenerator     The generator for HTTP responses.
	 * @param dataMeterDtoValidator Validator for DataMeterDto objects.
	 * @param meterTypeDtoValidator Validator for MeterTypeDto objects.
	 */
	@Autowired
	public MeterController(MeterService meterService,
						   JwtService jwtService,
						   ResponseGenerator responseGenerator,
						   Validator<DataMeterDto> dataMeterDtoValidator,
						   Validator<MeterTypeDto> meterTypeDtoValidator) {
		this.meterService = meterService;
		this.jwtService = jwtService;
		this.responseGenerator = responseGenerator;
		this.dataMeterDtoValidator = dataMeterDtoValidator;
		this.meterTypeDtoValidator = meterTypeDtoValidator;
	}

	/**
	 * Retrieves the current data meter reading for the specified user.
	 *
	 * @param jwtToken The JWT token for user authentication.
	 * @return The current data meter reading as a DataMeterDto object.
	 */
	//@Audit
	@Operation(summary = "Retrieve the current data meter reading for the specified user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved current data meter reading"),
			@ApiResponse(responseCode = "404", description = "Data meter reading not found")
	})
	@GetMapping("/last_data")
	public ResponseEntity<?> getLatestMeterData(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
		try {
			int userId = jwtService.extractPlayerId(jwtToken);
			DataMeterDto currentDataMeter = meterService.getCurrentDataMeter(userId);
			return responseGenerator.generateResponse(HttpStatus.OK, currentDataMeter);
		} catch (DataMeterNotFoundException e) {
			return responseGenerator.generateResponse(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	/**
	 * Saves the data meter reading for the specified user.
	 *
	 * @param jwtToken     The JWT token for user authentication.
	 * @param dataMeterDto The data meter reading to be saved.
	 */
	//@Audit
	@Operation(summary = "Save the data meter reading for the specified user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Meter reading data saved successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid data provided"),
			@ApiResponse(responseCode = "409", description = "Meter data was already submitted")
	})
	@PostMapping("/save_data")
	public ResponseEntity<?> saveMeterData(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken,
										   @RequestBody DataMeterDto dataMeterDto) {
		try {
			if (dataMeterDtoValidator.validate(dataMeterDto)) {
				return responseGenerator.generateResponse(HttpStatus.BAD_REQUEST, "Invalid data");
			}
			int userId = jwtService.extractPlayerId(jwtToken);
			meterService.saveDataMeter(userId, dataMeterDto);
			return responseGenerator.generateResponse(HttpStatus.OK, "Meter reading data sent");
		} catch (MeterDataWasSubmittedException e) {
			return responseGenerator.generateResponse(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	/**
	 * Retrieves the data meter reading for the specified user and month.
	 *
	 * @param jwtToken The JWT token for user authentication.
	 * @param month    The month for which the data meter reading is requested.
	 * @return Returns a list of all data for the specified month or an empty list.
	 */
	//@Audit
	@Operation(summary = "Retrieve the data meter reading for the specified user and month")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved data meter readings for the specified month"),
			@ApiResponse(responseCode = "404", description = "User not found or no data available for the specified month")
	})
	@GetMapping("/specified_month")
	public ResponseEntity<?> getMeterDataForSpecifiedMonth(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken,
														   @RequestParam("month") String month) {
		int userId = jwtService.extractPlayerId(jwtToken);
		int monthNumber = Integer.parseInt(month);
		List<DataMeterDto> meterDataForSpecifiedMonth = meterService.getMeterDataForSpecifiedMonth(userId, monthNumber);
		return responseGenerator.generateResponse(HttpStatus.OK, meterDataForSpecifiedMonth);
	}

	/**
	 * Retrieves all data meter readings for the specified user.
	 *
	 * @param jwtToken The JWT token for user authentication.
	 * @return A list of data meter readings as DataMeterDto objects.
	 */
	//@Audit
	@Operation(summary = "Retrieve all data meter readings for the specified user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved all data meter readings"),
	})
	@GetMapping("/data")
	public ResponseEntity<?> getMeterDataForUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
		int userId = jwtService.extractPlayerId(jwtToken);
		List<DataMeterDto> userMeterData = meterService.getUserMeterData(userId);
		return responseGenerator.generateResponse(HttpStatus.OK, userMeterData);
	}

	/**
	 * Retrieves data from all meter users and logs the corresponding user action.
	 *
	 * @param jwtToken The JWT token for user authentication.
	 * @return A map containing username as the key and DataMeterDto as the value.
	 */
	//@Audit
	@Operation(summary = "Retrieve data from all meter users")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved data from all meter users")
	})
	@GetMapping("/all_data")
	public ResponseEntity<?> getDataFromAllMeterUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
		Map<String, List<DataMeterDto>> dataFromAllMeterUsers = meterService.getDataFromAllMeterUsers();
		return responseGenerator.generateResponse(HttpStatus.OK, dataFromAllMeterUsers);
	}

	/**
	 * Retrieves a list of available meter types.
	 *
	 * @param jwtToken The JWT token for user authentication.
	 * @return A list of MeterTypeDto representing available meter types.
	 */
	//@Audit
	@Operation(summary = "Retrieve a list of available meter types")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved available meter types")
	})
	@GetMapping("/meter_types")
	public ResponseEntity<?> getAvailableMeterType(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
		List<MeterTypeDto> availableMeterType = meterService.getAvailableMeterType();
		return responseGenerator.generateResponse(HttpStatus.OK, availableMeterType);
	}

	/**
	 * Save a new type meter.
	 *
	 * @param jwtToken     The JWT token for user authentication.
	 * @param meterTypeDto An object containing the data of the new meter.
	 */
	//@Audit
	@Operation(summary = "Save a new type meter")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "New meter type saved successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid data provided"),
			@ApiResponse(responseCode = "409", description = "Meter type already exists")
	})
	@PostMapping("/save_meter")
	public ResponseEntity<?> addNewMeterType(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken,
											 @RequestBody MeterTypeDto meterTypeDto) {
		try {
			if (meterTypeDtoValidator.validate(meterTypeDto)) {
				return responseGenerator.generateResponse(HttpStatus.BAD_REQUEST, "Invalid data");
			}

			meterService.saveMeterType(meterTypeDto);
			return responseGenerator.generateResponse(HttpStatus.OK, "New meter type saved");
		} catch (MeterTypeExistException e) {
			return responseGenerator.generateResponse(HttpStatus.CONFLICT, e.getMessage());
		}
	}
}