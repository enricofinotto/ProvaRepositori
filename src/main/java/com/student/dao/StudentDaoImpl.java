package com.student.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.student.core.College;
import com.student.core.Student;
import com.student.database.QueryList;
import com.student.database.StudentCRUD;

@Named
public class StudentDaoImpl implements StudentDao {
	@Autowired
	private JdbcTemplate jdbc = new JdbcTemplate();
	
	private Map<Long, Student> students;
	private NavigableMap<Long, College> colleges;
	{
		students = new HashMap<>();
		students.put(1L, new Student(1L, "Eric", "Colbert", "English Literature", 145.00, 1));
		students.put(2L, new Student(2L, "Mary", "Contrary", "Physics", 155.00, 1));
		students.put(3L, new Student(3L, "Jason", "Stewart", "English Literature", 145.00, 2));
		students.put(4L, new Student(4L, "Ester", "Freeman", "English Literature", 145.00, 3));
		students.put(5L, new Student(5L, "Ann", "Mouvier", "French", 125.00, 4));

		colleges = new TreeMap<Long, College>();
		colleges.put(2L, new College(1, "Texas State University", "601 University Dr", "San Marcos", "Texas"));
		colleges.put(4L, new College(2, "University of South Florida", "4202 E Fowler Ave", "Tampa", "Florida"));
		colleges.put(6L, new College(3, "Boston College", "140 Commonwealth Avenue", "Chestnut Hill", "Massachusetts"));
		colleges.put(Long.MAX_VALUE, new College(4, "Tulane", "6823 St Charles Ave", "New Orleans", "Louisiana"));
	}
	
	@Override
	public Student getOne(long id) {
		Student student = jdbc.query(QueryList.getOneStudent(id), new RowMapper<Student>() {
			@Override  
		    public Student mapRow(ResultSet rs, int rownumber) throws SQLException {  
				Student st = new Student();  
		        st.setId(rs.getInt(1));  
		        st.setFirstName(rs.getString(2));  
		        st.setSurname(rs.getString(3));
		        st.setDept(rs.getString(4));
		        st.setFees(rs.getDouble(5));
		        st.setId_college(rs.getLong(6));
		        return st;  
		    }  
		}).get(0);
		College college = jdbc.query(QueryList.getOneCollege(student.getId_college()), new RowMapper<College>() {
			@Override  
		    public College mapRow(ResultSet rs, int rownumber) throws SQLException {  
				College cl = new College();  
				cl.setId(rs.getInt(1));
				cl.setName(rs.getString(2));  
				cl.setStreet(rs.getString(3));
				cl.setCity(rs.getString(4));
				cl.setState(rs.getString(5));
		        return cl;  
		    }  
		}).get(0);
		student.setCollege(college);
		return student;
	}

	@Override
	public List<Student> getAll() {
		/*Collection<Student> studentList = students.values().stream().map(p -> {
			p.setCollege(colleges.ceilingEntry(p.getId()).getValue());
			return p;
		}).collect(Collectors.toList());*/
		
		List<Student> mp = jdbc.query(QueryList.getAllStudents(), new RowMapper<Student>() {
			@Override  
		    public Student mapRow(ResultSet rs, int rownumber) throws SQLException {
				College cl = new College();
				Student st = new Student();
				
				cl.setId(rs.getInt(7));
				cl.setName(rs.getString(8));  
				cl.setStreet(rs.getString(9));
				cl.setCity(rs.getString(10));
				cl.setState(rs.getString(11));
				
				st.setId(rs.getInt(1));  
		        st.setFirstName(rs.getString(2));  
		        st.setSurname(rs.getString(3));
		        st.setDept(rs.getString(4));
		        st.setFees(rs.getDouble(5));
		        st.setId_college(rs.getLong(6));
				
		        st.setCollege(cl);
		        return st;  
		    }  
		});
		return mp;
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
			jdbc.update(QueryList.addStudent(student));
		}
		return students.get(newId);
	}

	@Override
	public void delete(long id) {
		boolean presente = true;
		if (id > students.size() || !students.containsKey(id)) {
			System.out.println("Id non presente!");
			presente = false;
		}
		if (presente == true) {
			students.remove(id);
			jdbc.update(QueryList.deleteStudent(id));
		}
	}

	
	@Override
	public void update(Student updatedStudent) {
		if(!students.containsKey(updatedStudent.getId())) {
			System.out.println("id inesistente");
		}else {
			students.replace(updatedStudent.getId(), updatedStudent);
			jdbc.update(QueryList.updateStudent(updatedStudent));
		}
	}

}
