package ru.patseev.monitoringservice.display;

/**
 * The TerminalInterface class provides static constants for the textual user interfaces
 * and output formats used in the monitoring service application.
 */
public class TerminalInterface {

	/**
	 * The main menu interface for user registration, sign-in, and exiting the application.
	 */
	public static final String MAIN_MENU_INTERFACE = """
			       
			1. Регистрация.
			2. Вход.
			3. Выход.
			""";

	/**
	 * The user interface for authenticated users, including various operations and an exit option.
	 */
	public static final String AUTH_USER_INTERFACE = """
			       
			1. Получить актуальные показания счетчика.
			2. Подать показания.
			3. Просмотр показаний за конкретный месяц.
			4. Просмотр истории подачи показаний.
			5. Выйти.
			""";

	/**
	 * The admin interface with options to view user meter data history, user actions, and exit.
	 */
	public static final String ADMIN_INTERFACE = """
			       
			1. Просмотр истории подачи показаний пользователей.
			2. Просмотр действий пользователей.
			3. Добавление нового типа счетчиков.
			4. Выйти.
			""";

	/**
	 * The output text format for displaying meter data, including date, meter type, and value.
	 */
	public static final String METER_DATA_OUTPUT_TEXT = """
			            
			Дата: %s
			Тип показаний: %s
			Данные: %s
			""";

	/**
	 * The output text format for displaying user actions, including date and action.
	 */
	public static final String ACTION_OUTPUT_TEXT = """
			       
			Дата: %s
			Действие: %s
			""";
}