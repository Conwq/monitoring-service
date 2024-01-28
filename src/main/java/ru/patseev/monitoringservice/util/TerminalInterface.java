package ru.patseev.monitoringservice.util;

public class TerminalInterface {
	public static final String MAIN_MENU_INTERFACE = """
   
			1. Регистрация.
			2. Вход.
			3. Выход.
			""";

	public static final String AUTH_USER_INTERFACE = """
   
			1. Получить актуальные показания счетчика.
			2. Подать показания.
			3. Просмотр показаний за конкретный месяц.
			4. Просмотр истории подачи показаний.
			5. Выйти.
			""";
	public static final String ADMIN_INTERFACE = """
   
			1. Просмотр истории подачи показаний пользователей.
			2. Просмотр действий пользователей.
			3. Выйти.
			""";

	public static final String METER_DATA_OUTPUT_TEXT = """
			    
			Дата: %s
			Тип показаний: %s
			Данные: %s
			""";

	public static final String ACTION_OUTPUT_TEXT = """
   
			Дата: %s
			Действие: %s
			""";
}