package com.student.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollectionOf;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.student.core.College;
import com.student.core.Student;
import com.student.database.QueryList;
import com.student.service.StudentServiceImpl;

class StudentDaoImplTest {

	@Mock
	StudentServiceImpl stService = Mockito.mock(StudentServiceImpl.class);
	
	@Mock
	JdbcTemplate jdbc = Mockito.mock(JdbcTemplate.class);
	
	StudentDaoImpl dao = new StudentDaoImpl();
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetOne() {
		dao.setJdbc(jdbc);
		Student student = new Student(1, "Susan", "Doubtfire", "French", 75.00, 1);
		Mockito.when(jdbc.queryForObject(ArgumentMatchers.any(String.class), ArgumentMatchers.any(RowMapper.class))).thenReturn(student);
		Student res = dao.getOne(1);
		System.out.println(res.toString());
	}
	
	@Test
	public void testGetAll() {
		dao.setJdbc(jdbc);
		
		
		List<Student> students = new ArrayList<Student>();
		students.add(new Student(1L, "Eric", "Colbert", "English Literature", 145.00, 1));
		students.add(new Student(2L, "Mary", "Contrary", "Physics", 155.00, 1));
		students.add(new Student(3L, "Jason", "Stewart", "English Literature", 145.00, 2));
		students.add(new Student(4L, "Ester", "Freeman", "English Literature", 145.00, 3));
		students.add(new Student(5L, "Ann", "Mouvier", "French", 125.00, 4));

		Mockito.when(jdbc.query(anyString(), ArgumentMatchers.any(RowMapper.class))).thenReturn(students);
		dao.getAll().toString();
	}
	
	@Test
	public void testAdd() {
		dao.setJdbc(jdbc);
		Student student = new Student(1, "Susan", "Doubtfire", "French", 75.00, 1);
		College college = new College(1, "Texas State University", "601 University Dr", "San Marcos", "Texas");
		student.setCollege(college);
		Mockito.when(jdbc.update(anyString())).thenReturn(1);
		dao.add(student);
		student = new Student(1, "Eric", "Colbert", "French", 75.00, 1);
		college = new College(1, "Texas State University", "601 University Dr", "San Marcos", "Texas");
		student.setCollege(college);
		dao.add(student);
	}
	
	@Test
	public void testDelete() {
		dao.setJdbc(jdbc);
		Mockito.when(jdbc.update(QueryList.deleteStudent(anyLong()))).thenReturn(1);
		dao.delete(1);
		dao.delete(100);
	}
	
	@Test
	public void testUpdate() {
		Student student = new Student(1, "Susan", "Doubtfire", "French", 75.00, 1);
		College college = new College(1, "Texas State University", "601 University Dr", "San Marcos", "Texas");
		student.setCollege(college);
		dao.setJdbc(jdbc);
		Mockito.when(jdbc.update(Mockito.anyString())).thenReturn(1);
		dao.update(student);
		student.setId(100);
		dao.update(student);
	}

}
