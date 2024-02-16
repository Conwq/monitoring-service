package ru.patseev.monitoringservice.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.dto.MeterTypeDto;
import ru.patseev.monitoringservice.exception.DataMeterNotFoundException;
import ru.patseev.monitoringservice.exception.MeterDataConflictException;
import ru.patseev.monitoringservice.exception.MeterTypeExistException;
import ru.patseev.monitoringservice.in.controller.MeterController;
import ru.patseev.monitoringservice.in.jwt.JwtService;
import ru.patseev.monitoringservice.in.validator.ValidationErrorExtractor;
import ru.patseev.monitoringservice.in.validator.MeterDataValidator;
import ru.patseev.monitoringservice.in.validator.MeterTypeValidator;
import ru.patseev.monitoringservice.service.MeterService;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MeterControllerTest {

	String jwtToken = "jwtToken";
	String electricity = "electricity";
	String hotWater = "hot water";
	int userId = 1;
	int month;
	Timestamp now;
	MeterTypeDto electricityType = new MeterTypeDto(1, electricity);
	MeterTypeDto hotWaterType = new MeterTypeDto(2, hotWater);
	DataMeterDto electricityData = new DataMeterDto(now, 120L, electricityType.meterTypeId(), electricityType.typeName());
	DataMeterDto hotWaterData = new DataMeterDto(now, 182L, hotWaterType.meterTypeId(), hotWaterType.typeName());
	MockMvc mockMvc;
	MeterService meterService;
	JwtService jwtService;

	@BeforeEach
	void setUp() {
		now = Timestamp.from(Instant.now());
		month = LocalDate.now().getMonth().getValue();

		meterService = mock(MeterService.class);
		jwtService = mock(JwtService.class);
		MeterTypeValidator meterTypeValidator = new MeterTypeValidator();
		MeterDataValidator meterDataValidator = new MeterDataValidator();
		ValidationErrorExtractor extractor = new ValidationErrorExtractor();
		MeterController meterController = new MeterController(meterService, jwtService, extractor, meterTypeValidator, meterDataValidator);

		mockMvc = MockMvcBuilders.standaloneSetup(meterController).build();
	}

	@Test
	@DisplayName("Test successful retrieval of latest meter data")
	void getLatestMeterData_successful() throws Exception {
		when(jwtService.extractPlayerId(jwtToken))
				.thenReturn(userId);
		when(meterService.getCurrentDataMeter(userId))
				.thenReturn(electricityData);

		mockMvc.perform(createGetRequest("/meters/last_data"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.meterTypeName").value(electricity));
	}

	@Test
	@DisplayName("Test handling of 'DataMeterNotFoundException' when attempting to retrieve latest meter data")
	void getLatestMeterData_notFound() throws Exception {
		when(jwtService.extractPlayerId(jwtToken))
				.thenReturn(userId);
		when(meterService.getCurrentDataMeter(userId))
				.thenThrow(DataMeterNotFoundException.class);

		mockMvc.perform(createGetRequest("/meters/last_data"))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Test successful saving of meter data")
	void saveData_successful() throws Exception {
		when(jwtService.extractPlayerId(jwtToken))
				.thenReturn(userId);

		mockMvc.perform(createPostRequest("/meters/save_data", electricityData))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").value("Meter reading data sent"));
	}

	@Test
	@DisplayName("Test handling of 'MeterDataWasSubmittedException' when saving meter data")
	void saveData_conflict() throws Exception {
		when(jwtService.extractPlayerId(jwtToken)).thenReturn(userId);
		doThrow(MeterDataConflictException.class)
				.when(meterService).saveDataMeter(userId, electricityData);

		mockMvc.perform(createPostRequest("/meters/save_data", electricityData))
				.andExpect(status().isConflict());
	}

	@Test
	@DisplayName("Saving invalid data should return HTTP 400 Bad Request with the message 'Invalid data'")
	void saveData_badRequest() throws Exception {
		DataMeterDto invalidData = new DataMeterDto(now, -1L, 1, electricity);

		mockMvc.perform(createPostRequest("/meters/save_data", invalidData))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$").value("The supplied value is too small"));
	}

	@Test
	@DisplayName("Test successful retrieval of meter data for specified month")
	void getMeterDataForSpecifiedMonth_successful() throws Exception {
		List<DataMeterDto> userData = new ArrayList<>();
		userData.add(electricityData);
		userData.add(hotWaterData);

		when(jwtService.extractPlayerId(jwtToken))
				.thenReturn(userId);
		when(meterService.getMeterDataForSpecifiedMonth(userId, month))
				.thenReturn(userData);

		MockHttpServletRequestBuilder request = createGetRequest("/meters/specified_month")
				.param("month", String.valueOf(month));

		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(userData.size()))
				.andExpect(jsonPath("$[0].meterTypeName").value(electricityData.meterTypeName()))
				.andExpect(jsonPath("$[1].meterTypeName").value(hotWater));
	}

	@Test
	@DisplayName("Test successful retrieval of all meter data for user")
	void getMeterDataForUser_successful() throws Exception {
		List<DataMeterDto> userData = new ArrayList<>();
		userData.add(electricityData);
		userData.add(hotWaterData);

		when(jwtService.extractPlayerId(jwtToken))
				.thenReturn(userId);
		when(meterService.getUserMeterData(userId))
				.thenReturn(userData);

		mockMvc.perform(createGetRequest("/meters/data"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(userData.size()))
				.andExpect(jsonPath("$[0].meterTypeName").value(electricityData.meterTypeName()))
				.andExpect(jsonPath("$[1].meterTypeName").value(hotWater));
	}

	@Test
	@DisplayName("Test successful retrieval of data from all meter users")
	void getDataFromAllMeterUsers_successful() throws Exception {
		List<DataMeterDto> userData = new ArrayList<>();
		userData.add(electricityData);
		userData.add(hotWaterData);
		Map<String, List<DataMeterDto>> dataFromAllMeterUsers = new HashMap<>();
		dataFromAllMeterUsers.put("1", userData);

		when(meterService.getDataFromAllMeterUsers())
				.thenReturn(dataFromAllMeterUsers);

		mockMvc.perform(createGetRequest("/meters/all_data"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(dataFromAllMeterUsers.size()));
	}

	@Test
	@DisplayName("Test successful retrieval of available meter types")
	void getAvailableMeterType_successful() throws Exception {
		List<MeterTypeDto> meterTypes = new ArrayList<>();
		meterTypes.add(electricityType);
		meterTypes.add(hotWaterType);

		when(meterService.getAvailableMeterType())
				.thenReturn(meterTypes);

		mockMvc.perform(createGetRequest("/meters/meter_types"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(meterTypes.size()));
	}

	@Test
	@DisplayName("Test successful addition of new meter type")
	void addNewMeterType_successful() throws Exception {
		mockMvc.perform(createPostRequest("/meters/save_meter", hotWaterType))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$").value("New meter type saved"));

		verify(meterService)
				.saveMeterType(hotWaterType);
	}

	@Test
	@DisplayName("Test handling of 'MeterTypeExistException' when adding new meter type")
	void addNewMeterType_conflict() throws Exception {
		doThrow(MeterTypeExistException.class)
				.when(meterService).saveMeterType(hotWaterType);

		mockMvc.perform(createPostRequest("/meters/save_meter", hotWaterType))
				.andExpect(status().isConflict());
	}

	@Test
	@DisplayName("Adding a new counter type with invalid data should return an HTTP 400 Bad Request with the message 'Invalid data'")
	void addNewMeterType_badRequest() throws Exception {
		MeterTypeDto invalidData = new MeterTypeDto(null, "...");

		mockMvc.perform(createPostRequest("/meters/save_meter", invalidData))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$").value("Invalid data"));
	}

	private MockHttpServletRequestBuilder createGetRequest(String uri) {
		return get(uri)
				.header(HttpHeaders.AUTHORIZATION, jwtToken)
				.accept(MediaType.APPLICATION_JSON);
	}

	private MockHttpServletRequestBuilder createPostRequest(String uri, Object object) {
		try {
			return post(uri)
					.header(HttpHeaders.AUTHORIZATION, jwtToken)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(object));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}