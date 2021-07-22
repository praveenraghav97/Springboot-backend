package com.extramarks.springboot.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.extramarks.springboot.model.Employee;
import com.extramarks.springboot.service.EmployeeService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {	
	
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/webapp/imagedata";
	
	//Dependency Injection of Employee
	@Autowired
	private EmployeeService employeeService;
	
	//Getting all the Active Employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEMployees();
	}
	
	//Creating a new Employee in the System
	@PostMapping("/employees")
	public ResponseEntity<Map<String, String>> createEmployee(@RequestBody  Employee employee) {		
		return employeeService.createEmployee(employee);
	}
	
	//Getting a particular Employee based on ID of Employee
	@GetMapping("/employees/{id}")
	public Employee getEmployeeById(@PathVariable("id") String id) {		
		return employeeService.getEmployeeById(id);
	}
	
	//Updating the details of a particular Employee based on ID
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") String id,@RequestBody Employee employee) {
		return employeeService.updateEmployee(id, employee);
	}
	
	
	//Deleting specific Employee based on ID
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable String id) {
		return employeeService.deleteEmployee(id);
	}
	
	
	//Updating the status of Employee from active to inactive
	@GetMapping("/employees/status/{id}")
	public ResponseEntity<Employee> updateEmployeeStatus(@PathVariable("id") String id) {
		return employeeService.updateEmployeeStatus(id);
	}
	
	@PostMapping("employees/image-upload")
	public ResponseEntity<Map<String, String>> updateImage(@RequestParam("id") String id,
			 @RequestParam("image") MultipartFile file, ModelMap model) throws IOException {
		
		Employee employee = employeeService.getEmployeeById(id);		
		
		String fileName = employee.getId()+file.getOriginalFilename();
		 
		 String fileExtension = fileName.substring(fileName.length()- 4);
		 if(!(fileExtension.equals("jpeg") || fileExtension.equals(".jpg") || fileExtension.equals(".png"))) {
			 model.addAttribute("message", "File format not supported for 'Profile Picture', please upload jpeg, jpg or png only");
			 Map<String, String> response = new HashMap<>();
			 response.put("status", "notOk");
			 return ResponseEntity.ok(response);
		 }		 
		 		 
		 Path fileNameAndPath = Paths.get(uploadDirectory, fileName);	     
		 Files.write(fileNameAndPath, file.getBytes());		 
		 employee.setImage(fileName);
		 
		 employeeService.updateEmployee(id, employee);
		 
		 Map<String, String> response = new HashMap<>();
		 response.put("status", "Ok");
		 return ResponseEntity.ok(response);
	}
	
    

}
