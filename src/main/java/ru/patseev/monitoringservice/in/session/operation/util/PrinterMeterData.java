package ru.patseev.monitoringservice.in.session.operation.util;

import ru.patseev.monitoringservice.dto.DataMeterDto;
import ru.patseev.monitoringservice.display.TerminalInterface;

import java.time.format.DateTimeFormatter;

/**
 * The PrinterMeterData class is responsible for printing data meter information to the console.
 */
public class PrinterMeterData {

	/**
	 * Prints the provided data meter information to the console.
	 *
	 * @param dataMeterDto The DataMeterDto containing information about the data meter reading.
	 */
	public void printData(DataMeterDto dataMeterDto) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM");

		System.out.printf(TerminalInterface.METER_DATA_OUTPUT_TEXT,
				dataMeterDto.date().format(format),
				dataMeterDto.meterTypeName(),
				dataMeterDto.value()
		);
	}
}