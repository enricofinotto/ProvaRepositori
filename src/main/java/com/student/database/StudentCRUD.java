package com.student.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.student.core.Student;

@Component
public class StudentCRUD implements CommandLineRunner {
	
	@Autowired
	private JdbcTemplate jdbc = new JdbcTemplate();

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Inizializzazione tabella.");
		jdbc.execute("DROP TABLE IF EXISTS student;");
		jdbc.execute("CREATE TABLE public.student\r\n"
				+ "(\r\n"
				+ "    id bigint,\r\n"
				+ "    name text NOT NULL,\r\n"
				+ "    surname text NOT NULL,\r\n"
				+ "    department text NOT NULL,\r\n"
				+ "    fees double precision NOT NULL,\r\n"
				+ "    PRIMARY KEY (id)\r\n"
				+ ");\r\n"
				+ "\r\n"
				+ "ALTER TABLE IF EXISTS public.student\r\n"
				+ "    OWNER to postgres;");
		jdbc.update("INSERT INTO student (id, name, surname, department, fees) VALUES"
				+ "(1,'Eric', 'Colbert', 'English Literature', 145.00),"
				+ "(2,'Mary', 'Contrary', 'Physics', 155.00),"
				+ "(3,'Jason', 'Stewart', 'English Literature', 145.00),"
				+ "(4,'Ester', 'Freeman', 'English Literature', 145.00),"
				+ "(5,'ErAnnic', 'Mouvier', 'French', 125.00)"
				+";");
	}
}
