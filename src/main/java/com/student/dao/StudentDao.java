package com.student.dao;

import java.util.Collection;
import java.util.Map;

import com.student.core.Student;

public interface StudentDao {
	
	Student getOne(long id);
	Collection<Student> getAll();
	Student add(Student student);
	void delete(long id);
	void update(Student student);
}
