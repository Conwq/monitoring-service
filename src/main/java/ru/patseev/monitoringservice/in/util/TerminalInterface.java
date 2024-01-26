package ru.patseev.monitoringservice.in.util;

public class TerminalInterface {
	public static final String mainMenuInterface = """
   
			1. Регистрация.
			2. Вход.
			3. Выход.
			
			""";

	public static final String authUserInterface = """
   
			1. Получить актуальные показания счетчика.
			2. Подать показания. (только один раз в месяц) (они считаются актуальными)
			3. Просмотр показаний за конкретный месяц.
			4. Просмотр истории подачи показаний.
			6. Выйти.
			
			""";

	public static final String readingDataOutputText = """
			    
			Дата: %s
			Отопление: %s
			Холодная вода: %s
			Горячая вода: %s
							
			""";
}
