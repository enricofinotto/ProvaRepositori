package com.student.database;

import com.student.core.Student;

public class QueryList {

	static public String getOneStudent(long id) {
		return "SELECT * FROM student WHERE id="+id;
	}
	
	static public String getOneCollege(long id) {
		return "SELECT * FROM college WHERE id="+id;
	}
	
	static public String getAllStudents() {
		return "SELECT * FROM student INNER JOIN college ON student.id_college=college.id;";
	}
	
	static public String addStudent(Student student) {
		return "INSERT INTO student (id, name, surname, department, fees, id_college)"
				+ " VALUES"
				+ "('"+student.getId()+"','"+student.getFirstName()+"', '"
				+ student.getSurname()+"', '"+student.getDept()+"', "+student.getFees()+", "
				+ student.getCollege().getId()+");";
	}
	
	static public String deleteStudent(long id) {
		return "DELETE FROM student WHERE id = "+id+";";
	}
	
	static public String updateStudent(Student student) {
		return "UPDATE student SET name='"+student.getFirstName()
		+"', surname='"+student.getSurname()
		+"', department='"+student.getDept()
		+"', fees="+student.getFees()
		+", id_college="+student.getId_college()
		+" WHERE id = "+student.getId()+";";
	}
	
}
