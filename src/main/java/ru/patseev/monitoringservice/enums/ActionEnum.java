package ru.patseev.monitoringservice.enums;

/**
 * The ActionEnum enum represents different user actions for auditing purposes.
 */
public enum ActionEnum {
	REGISTRATION,
	AUTHORIZATION,
	GET_LATEST_METER_DATA,
	SAVE_METER_DATA,
	GET_METER_DATA_FOR_SPECIFIED_MONTH,
	GET_METER_DATA_FOR_USER,
	GET_USERS_ACTION,
	GET_AVAILABLE_METER_TYPE,
	ADD_NEW_METER_TYPE,
	GET_DATA_FROM_ALL_METER_USER
}