package com.student.controller;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.student.StudentProperties;
import com.student.core.College;
import com.student.core.Student;
import com.student.service.StudentService;

@RestController
@RequestMapping(value = "/student")
@CrossOrigin
public class StudentController {

	@Inject
	private StudentProperties studentProperties;
	@Inject
	private StudentService studentService;

	@GetMapping("/msg")
	public String getMessage(@RequestHeader("user-agent") String userAgent) {
		return studentProperties.getGreeting() + " " + userAgent;
	}

	@GetMapping
	public Collection<Student> getAll() {
		return studentService.getAllStudents();
	}

	@GetMapping("/{id}")
	public Map<String,Object> getStudent(@PathVariable("id") long id) {
		return studentService.get(id);
	}

	@GetMapping("/search/{department}")
	public Collection<Student> getStudentsPerDepartment(@PathVariable("department") String department,
			@RequestParam("name") Optional<String> optional) {
		return studentService.getAllStudentsInDepartment(department, optional.orElse(""));
	}

	@PostMapping("/add")
	public Student addStudent(@RequestBody Student student) {
		return studentService.add(student);
	}

	/*
	 * Mattia: 
	 * 
	 * - delete con id --fatto
	 * - nel metodo add se aggiungo uno studente con lo
	 * stesso nome e cognome mi deve dare errore per record duplicato
	 * 
	 * Prova 1:
	 * 
	 * @PostMapping("/remove") public ResponseEntity<String>
	 * removeStudent(@RequestBody int id, Student student) { if (student.getId() ==
	 * id && student.getId() > 0) {
	 * 
	 * studentService.delete(student); URI uri = URI.create("/college/student/" +
	 * student.getId()); return ResponseEntity.accepted().location(uri).build(); }
	 * else { return ResponseEntity.badRequest().build(); }
	 * 
	 * }
	 */

	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public String removeStudent(@PathVariable("id") long id) {
		if (id > 0) {
				studentService.delete(id);
			return "Studente: " + id + " eliminato";
		} else {
			return "Id non adeguato!";
		}
	}
	
	@PutMapping("/updateStudent")
	public void updateStudent(@RequestBody Student student) {
		if(student.getFirstName()==null || student.getSurname()==null) {
			System.out.println("nome o cognome inesistente");
		}else {
			studentService.update(student);
		}
	}
	
}
