package com.student.service;


import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import com.student.core.Student;

public interface StudentService {
	@Inject
	Map<String,Object> get(long id);
	Collection<Student> getAllStudents();
	Collection<Student> getAllStudentsInDepartment(String department, String lastNamelike);
	Student add(Student student);
	void delete(long id);
	void update(Student student);

}
