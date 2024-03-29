package com.student.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.student.core.Student;
import com.student.dao.StudentDao;
import com.student.dao.StudentDaoImpl;

@Named
public class StudentServiceImpl implements StudentService {
	@Inject
	private StudentDao studentDao = new StudentDaoImpl();

	@Override
	public Student get(long id) {
		return studentDao.getOne(id);
	}

	@Override
	public List<Student> getAllStudents() {
		return studentDao.getAll();
	}

	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	public Collection<Student> getAllStudentsInDepartment(String department, String lastNameLike) {
		return studentDao.getAll().stream().filter(p -> p.getDept().equals(department))
				.filter(p -> p.getSurname().contains(lastNameLike)).collect(Collectors.toList());
	}

	@Override
	public Student add(Student student) {
		if (student.getFirstName() != null && student.getSurname() != null && student.getDept() != null) {
			return studentDao.add(student);
		}
		System.out.println("nome o cognome o dipartimento non inseriti");
		return null;
	}
	
	
	public void update(Student student) {
		studentDao.update(student);
	}

	@Override
	public void delete(long id) {
		if (id > 0) {
			studentDao.delete(id);
		}
	}

}
