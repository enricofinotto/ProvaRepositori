package com.student.controller;

 
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.student.StudentProperties;
import com.student.core.College;
import com.student.core.Student;
import com.student.service.StudentService;
 
@RestController
@RequestMapping(value="/student")
@CrossOrigin
public class StudentController {
	
	@Inject
	private StudentProperties studentProperties;
	@Inject
	private StudentService studentService;
	
	@GetMapping("/msg")
	public String getMessage(@RequestHeader("user-agent") String userAgent) {
		return studentProperties.getGreeting()+" "+userAgent;
	}
	@GetMapping
	public Collection<Student> getAll() {
		return studentService.getAllStudents();
	}

	@GetMapping("/{id}")
	public Student getStudent(@PathVariable("id") long id) {
		return studentService.get(id);
	}
	
	@GetMapping(path="/single",
		produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Student> getSingleStudent(@RequestParam("id") Optional<Long> optional) {
		return ResponseEntity.ok(studentService.get(optional.orElse(1l)));
	}

	
	@GetMapping("/search/{department}")
	public Collection<Student> getStudentsPerDepartment(@PathVariable("department") String department, @RequestParam("name")  Optional<String> optional) {
		return studentService.getAllStudentsInDepartment(department, optional.orElse(""));
	}
	
	@PostMapping("/addStudent")
	public Student addStudent(@RequestBody Student student){
		studentService.add(student);
		return getStudent(student.getId());
	}
	
	/*@PostMapping("/updateStudentSimple")
	public String updateStudentSimple(@RequestBody Student student) {
		if(studentService.get(student.getId())==null)
			return "Error: non-existent student";
		studentService.delete(student.getId());
		addStudent(student);
		return "update completed successfully";
	}*/
	
	@PutMapping("/updateStudent")
	public void updateStudent(@RequestBody Student student) {
		studentService.update(student);
	}
	
}
