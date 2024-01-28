package ru.patseev.monitoringservice.audit_service.enums;

/**
 * The ActionEnum enum represents different user actions for auditing purposes.
 */
public enum ActionEnum {
	REGISTRATION,
	LOG_IN,
	GET_CURRENT_METER_DATA,
	SEND_METER_DATA,
	GET_METER_DATA_FOR_SPECIFIED_MONTH,
	GET_METER_DATA_FOR_USER,
	GET_USERS_ACTION,
	GET_ACTUAL_METER_TYPE,
	GET_ALL_METER_DATA
}