package ru.patseev.monitoringservice.data_meter_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataMeter {
	private LocalDate date;
	private Long heatingData;
	private Long coldWaterData;
	private Long hotWaterData;
	private String username;
}