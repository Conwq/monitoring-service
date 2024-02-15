package ru.patseev.monitoringservice.in.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.patseev.monitoringservice.controller.MeterController;
import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.dto.MeterTypeDto;
import ru.patseev.monitoringservice.in.extractor.ObjectExtractor;
import ru.patseev.monitoringservice.in.generator.ResponseGenerator;
import ru.patseev.monitoringservice.in.operation.manager.OperationManager;
import ru.patseev.monitoringservice.in.operation.manager.impl.MeterOperationManager;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

class MeterServletTest {

	static final String JWT_TOKEN = "jwtToken";
	static final String AUTH_HEADER = "Authorization";
	static final String OPERATION_PARAM = "operation";

	HttpServletRequest req;
	HttpServletResponse resp;
	ResponseGenerator responseGenerator;
	MeterController meterController;
	ObjectExtractor objectExtractor;
	MeterServlet meterServlet;
	MeterTypeDto meterTypeDto;
	DataMeterDto dataMeterDto;

	@BeforeEach
	void setUp() {
		req = mock(HttpServletRequest.class);
		resp = mock(HttpServletResponse.class);
		meterController = mock(MeterController.class);
		responseGenerator = mock(ResponseGenerator.class);
		objectExtractor = mock(ObjectExtractor.class);

		OperationManager operationManager
				= new MeterOperationManager(meterController, responseGenerator, objectExtractor);
		meterServlet = new MeterServlet(operationManager);

		meterTypeDto = new MeterTypeDto(1, "electricity");
		dataMeterDto
				= new DataMeterDto(Timestamp.from(Instant.now()), 100L, meterTypeDto.meterTypeId(), meterTypeDto.typeName());
	}

	@Test
	@DisplayName("should save the new meter type")
	public void doPost_shouldSaveMeterType() {
		String operationName = "add_meter_type";

		when(req.getParameter(OPERATION_PARAM))
				.thenReturn(operationName);
		when(req.getHeader(AUTH_HEADER))
				.thenReturn(JWT_TOKEN);
		when(objectExtractor.extractObject(req, MeterTypeDto.class))
				.thenReturn(meterTypeDto);

		meterServlet.doPost(req, resp);

		verify(meterController)
				.addNewMeterType(JWT_TOKEN, meterTypeDto);
		verify(responseGenerator)
				.generateResponse(resp, HttpServletResponse.SC_OK, "New meter type saved");
	}

	@Test
	@DisplayName("should save the meter data")
	public void doPost_shouldSaveMeterData() {
		String operationName = "save_data";

		when(req.getParameter(OPERATION_PARAM))
				.thenReturn(operationName);
		when(req.getHeader(AUTH_HEADER))
				.thenReturn(JWT_TOKEN);
		when(objectExtractor.extractObject(req, DataMeterDto.class))
				.thenReturn(dataMeterDto);

		meterServlet.doPost(req, resp);

		verify(meterController)
				.saveMeterData(JWT_TOKEN, dataMeterDto);
		verify(responseGenerator)
				.generateResponse(resp, HttpServletResponse.SC_OK, "Meter reading data sent");
	}

	@Test
	@DisplayName("should return available meter types")
	public void doGet_shouldReturnMeterTypes() {
		String operationName = "all_meter_types";
		List<MeterTypeDto> availableMeterTypes = List.of(meterTypeDto);

		when(req.getParameter(OPERATION_PARAM))
				.thenReturn(operationName);
		when(req.getHeader(AUTH_HEADER))
				.thenReturn(JWT_TOKEN);
		when(meterController.getAvailableMeterType(JWT_TOKEN))
				.thenReturn(availableMeterTypes);

		meterServlet.doGet(req, resp);

		verify(responseGenerator)
				.generateResponse(resp, HttpServletResponse.SC_OK, availableMeterTypes);
	}

	@Test
	@DisplayName("should return all meter data")
	public void doGet_shouldReturnAllMeterData() {
		String operationName = "all_data";
		Map<String, List<DataMeterDto>> meterDataForUser = Map.of("username", List.of(dataMeterDto));

		when(req.getParameter(OPERATION_PARAM))
				.thenReturn(operationName);
		when(req.getHeader(AUTH_HEADER))
				.thenReturn(JWT_TOKEN);
		when(meterController.getDataFromAllMeterUsers(JWT_TOKEN))
				.thenReturn(meterDataForUser);

		meterServlet.doGet(req, resp);

		verify(responseGenerator)
				.generateResponse(resp, HttpServletResponse.SC_OK, meterDataForUser);
	}

	@Test
	@DisplayName("should return user meter data")
	public void doGet_shouldReturnUserMeterData() {
		String operationName = "user_data";
		List<DataMeterDto> userDataMeter = List.of(dataMeterDto);

		when(req.getParameter(OPERATION_PARAM))
				.thenReturn(operationName);
		when(req.getHeader(AUTH_HEADER))
				.thenReturn(JWT_TOKEN);
		when(meterController.getMeterDataForUser(JWT_TOKEN))
				.thenReturn(userDataMeter);

		meterServlet.doGet(req, resp);

		verify(responseGenerator)
				.generateResponse(resp, HttpServletResponse.SC_OK, userDataMeter);
	}

	@Test
	@DisplayName("should return meter reading data for the specified month")
	void doGet_shouldReturnUserMeterDataForSpecifiedMonth() {
		String operationName = "specified_month_data";
		List<DataMeterDto> meterDataForSpecifiedMonth = List.of(dataMeterDto);

		when(req.getParameter(OPERATION_PARAM))
				.thenReturn(operationName);
		when(req.getHeader(AUTH_HEADER))
				.thenReturn(JWT_TOKEN);
		when(req.getParameter("month"))
				.thenReturn("1");
		when(meterController.getMeterDataForSpecifiedMonth(JWT_TOKEN, "1"))
				.thenReturn(meterDataForSpecifiedMonth);

		meterServlet.doGet(req, resp);

		verify(responseGenerator)
				.generateResponse(resp, HttpServletResponse.SC_OK, meterDataForSpecifiedMonth);
	}

	@Test
	@DisplayName("should return the last transmitted meter readings")
	void doGet_shouldReturnLastMeterData() {
		String operationName = "last_data";

		when(req.getParameter(OPERATION_PARAM))
				.thenReturn(operationName);
		when(req.getHeader(AUTH_HEADER))
				.thenReturn(JWT_TOKEN);
		when(meterController.getLatestMeterData(JWT_TOKEN))
				.thenReturn(dataMeterDto);

		meterServlet.doGet(req, resp);

		verify(responseGenerator)
				.generateResponse(resp, HttpServletResponse.SC_OK, dataMeterDto);
	}
}