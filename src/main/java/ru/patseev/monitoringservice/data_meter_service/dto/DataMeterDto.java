package ru.patseev.monitoringservice.data_meter_service.dto;

import java.time.LocalDate;

public record DataMeterDto (LocalDate date, Long heatingData, Long coldWaterData, Long hotWaterData){
}
