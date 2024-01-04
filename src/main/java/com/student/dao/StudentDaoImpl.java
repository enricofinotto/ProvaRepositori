package com.student.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.student.core.College;
import com.student.core.Student;
import com.student.database.StudentCRUD;

@Named
public class StudentDaoImpl implements StudentDao {
	@Autowired
	private JdbcTemplate jdbc = new JdbcTemplate();
	
	private Map<Long, Student> students;
	private NavigableMap<Long, College> colleges;
	{
		students = new HashMap<>();
		students.put(1L, new Student(1L, "Eric", "Colbert", "English Literature", 145.00));
		students.put(2L, new Student(2L, "Mary", "Contrary", "Physics", 155.00));
		students.put(3L, new Student(3L, "Jason", "Stewart", "English Literature", 145.00));
		students.put(4L, new Student(4L, "Ester", "Freeman", "English Literature", 145.00));
		students.put(5L, new Student(5L, "Ann", "Mouvier", "French", 125.00));

		colleges = new TreeMap<Long, College>();
		colleges.put(2L, new College("Texas State University", "601 University Dr", "San Marcos", "Texas"));
		colleges.put(4L, new College("University of South Florida", "4202 E Fowler Ave", "Tampa", "Florida"));
		colleges.put(6L, new College("Boston College", "140 Commonwealth Avenue", "Chestnut Hill", "Massachusetts"));
		colleges.put(Long.MAX_VALUE, new College("Tulane", "6823 St Charles Ave", "New Orleans", "Louisiana"));
	}

	@Override
	public Student getOne(long id) {
		Student student = students.get(id);
		student.setCollege(colleges.ceilingEntry(student.getId()).getValue());
		return student;
	}

	@Override
	public Collection<Student> getAll() {
		Collection<Student> studentList = students.values().stream().map(p -> {
			p.setCollege(colleges.ceilingEntry(p.getId()).getValue());
			return p;
		}).collect(Collectors.toList());
		return studentList;
	}

	@Override
	public Student add(Student student) {
		String nome = student.getFirstName();
		String cognome = student.getSurname();
		boolean presenti = false;
		long newId = -1;

		for (Student student1 : students.values()) {
			String valorenome = student1.getFirstName();
			String valorecognome = student1.getSurname();

			if (nome.equals(valorenome) && cognome.equals(valorecognome)) {
				System.out.println("Nome e Cognome già presenti nell'elenco");
				presenti = true;
				break;
			}
		}
		if (presenti == false) {
			for (Student st : students.values()) {
				newId = st.getId();
			}
			newId++;
			student.setId(newId);
			students.put(newId, student);
			
		}
		String query = "INSERT INTO student (id, name, surname, department, fees) VALUES"
				+ "('"+student.getId()+"','"+student.getFirstName()+"', '"+student.getSurname()+"', '"+student.getDept()+"', "+student.getFees()+");";
		jdbc.update(query);
		return students.get(newId);
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		// id = students.keySet().stream().count();
		boolean presente = true;
		if (id > students.size() || !students.containsKey(id)) {
			System.out.println("Id non presente!");
			presente = false;
		}
		if (presente == true) {
			students.remove(id);
		}
	}

	
	@Override
	public void update(Student updatedStudent) {
		if(!students.containsKey(updatedStudent.getId())) {
			System.out.println("id inesistente");
		}else {
			students.replace(updatedStudent.getId(), updatedStudent);
		}
	}

}
