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
		jdbc.execute("DROP TABLE IF EXISTS college;");
		
		jdbc.update("CREATE TABLE public.college\r\n"
				+ "(\r\n"
				+ "    id bigint NOT NULL,\r\n"
				+ "    college_name text NOT NULL,\r\n"
				+ "    street text NOT NULL,\r\n"
				+ "    city text NOT NULL,\r\n"
				+ "    state text NOT NULL,\r\n"
				+ "    PRIMARY KEY (id)\r\n"
				+ ");\r\n"
				+ "\r\n"
				+ "ALTER TABLE IF EXISTS public.college\r\n"
				+ "    OWNER to postgres;");
		jdbc.update("INSERT INTO college (id, college_name, street, city, state) VALUES"
				+ "(1,'Texas State University', '601 University Dr', 'San Marcos', 'Texas'),"
				+ "(2,'University of South Florida', '4202 E Fowler Ave', 'Tampa', 'Florida'),"
				+ "(3,'Boston College', '140 Commonwealth Avenue', 'Chestnut Hill', 'Massachusetts'),"
				+ "(4,'Tulane', '6823 St Charles Ave', 'New Orleans', 'Louisiana')"
				+";");
		
		jdbc.execute("CREATE TABLE public.student\r\n"
				+ "(\r\n"
				+ "    id bigint,\r\n"
				+ "    name text NOT NULL,\r\n"
				+ "    surname text NOT NULL,\r\n"
				+ "    department text NOT NULL,\r\n"
				+ "    fees double precision NOT NULL,\r\n"
				+ "    id_college bigint,\r\n"
				+ "    PRIMARY KEY (id),\r\n"
				+ "    FOREIGN KEY (id_college) REFERENCES college(id)\r\n"
				+ ");\r\n"
				+ "\r\n"
				+ "ALTER TABLE IF EXISTS public.student\r\n"
				+ "    OWNER to postgres;");
		jdbc.update("INSERT INTO student (id, name, surname, department, fees, id_college) VALUES"
				+ "(1,'Eric', 'Colbert', 'English Literature', 145.00, 1), "
				+ "(2,'Mary', 'Contrary', 'Physics', 155.00, 1),"
				+ "(3,'Jason', 'Stewart', 'English Literature', 145.00, 2),"
				+ "(4,'Ester', 'Freeman', 'English Literature', 145.00, 3),"
				+ "(5,'ErAnnic', 'Mouvier', 'French', 125.00, 4)"
				+";");
		
		
	}
}
