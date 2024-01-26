package ru.patseev.monitoringservice.in.session.operation.impl.admin;

import lombok.RequiredArgsConstructor;
import ru.patseev.monitoringservice.data_meter_service.controller.DataMeterController;
import ru.patseev.monitoringservice.data_meter_service.dto.DataMeterDto;
import ru.patseev.monitoringservice.in.session.operation.Operation;
import ru.patseev.monitoringservice.in.util.TerminalInterface;
import ru.patseev.monitoringservice.user_service.dto.UserDto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ShowAllUsersMeterDataOperation implements Operation {
	private final DataMeterController dataMeterController;

	@Override
	public void execute(UserDto userDto) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM");
		Map<String, List<DataMeterDto>> dataFromAllMeterUsers =
				dataMeterController.getDataFromAllMeterUsers(userDto);

		for (Map.Entry<String, List<DataMeterDto>> entry : dataFromAllMeterUsers.entrySet()) {
			System.out.printf("****** %s ******", entry.getKey());

			List<DataMeterDto> listDataMeter = entry.getValue();

			listDataMeter.forEach(dataMeterDto ->
					System.out.printf(TerminalInterface.METER_DATA_OUTPUT_TEXT,
							dataMeterDto.date().format(format),
							dataMeterDto.heatingData(),
							dataMeterDto.coldWaterData(),
							dataMeterDto.hotWaterData())
			);
			System.out.println("+------------+");
		}

	}
}
