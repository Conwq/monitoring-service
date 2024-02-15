package ru.patseev.monitoringservice.enums;

/**
 * Enum representing various operations in the monitoring service.
 */
public enum OperationEnum {

	/**
	 * Authentication operation.
	 */
	AUTH,

	/**
	 * User registration operation.
	 */
	REGISTRATION,

	/**
	 * Retrieving current data operation.
	 */
	LAST_DATA,

	/**
	 * Retrieving data for a specified month operation.
	 */
	SPECIFIED_MONTH_DATA,

	/**
	 * Retrieving user data operation.
	 */
	USER_DATA,

	/**
	 * Retrieving all data operation.
	 */
	ALL_DATA,

	/**
	 * Retrieving meter type operation.
	 */
	ALL_METER_TYPES,

	/**
	 * Saving data operation.
	 */
	SAVE_DATA,

	/**
	 * Adding meter type operation.
	 */
	ADD_METER_TYPE,

	/**
	 * Getting audit operation.
	 */
	GET_AUDIT
}
