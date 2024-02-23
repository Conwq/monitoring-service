package ru.patseev.auditstarter.manager;

import ru.patseev.auditstarter.manager.enums.ActionEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages actions associated with method names.
 */
public class ActionManager {

	/**
	 * A map containing method names mapped to corresponding ActionEnum values.
	 */
	private final Map<String, ActionEnum> actions;

	/**
	 * Constructs an ActionManager and initializes the action map
	 */
	public ActionManager() {
		actions = initMap();
	}

	/**
	 * Retrieves the action associated with the given method name
	 *
	 * @param methodName The name of the method
	 * @return The action associated with the method name
	 */
	public ActionEnum getActionByMethodName(String methodName) {
		return actions.get(methodName);
	}

	/**
	 * Initialize the action map
	 *
	 * @return Returns a map with values
	 */
	private Map<String, ActionEnum> initMap() {
		return new HashMap<>() {{
			put("getLatestMeterData", ActionEnum.GET_LATEST_METER_DATA);
			put("saveMeterData", ActionEnum.SAVE_METER_DATA);
			put("getMeterDataForSpecifiedMonth", ActionEnum.GET_METER_DATA_FOR_SPECIFIED_MONTH);
			put("getMeterDataForUser", ActionEnum.GET_METER_DATA_FOR_USER);
			put("getDataFromAllMeterUsers", ActionEnum.GET_DATA_FROM_ALL_METER_USER);
			put("getAvailableMeterType", ActionEnum.GET_AVAILABLE_METER_TYPE);
			put("addNewMeterType", ActionEnum.ADD_NEW_METER_TYPE);

			put("saveUser", ActionEnum.REGISTRATION);
			put("authUser", ActionEnum.AUTHORIZATION);

			put("getListOfUserActions", ActionEnum.GET_USERS_ACTION);
		}};
	}
}
