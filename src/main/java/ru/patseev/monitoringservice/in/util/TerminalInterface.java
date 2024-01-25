package ru.patseev.monitoringservice.in.util;

public class TerminalInterface {
	public static final String mainMenuInterface = """
   
			1. Регистрация.
			2. Вход.
			3. Выход.
			
			""";

	public static final String authUserInterface = """
   
			1. Получить актуальные показания счетчика. (последние показание считаются актуальными)
			2. Подать показания. (только один раз в месяц) (они считаются актуальными)
			3. Просмотр показаний за конкретный месяц.
			4. Просмотр истории подачи показаний. (текущий пользователь)
			5. Просмотр показаний пользователя. (По username) (отобразить все username, после чего предоставить возможность админу выбрать любой из предоставленных)
			6. Выйти.
			""";

	public static final String readingDataOutputText = """
			    
			Дата: %s
			Отопление: %s
			Холодная вода: %s
			Горячая вода: %s
							
			""";
}
