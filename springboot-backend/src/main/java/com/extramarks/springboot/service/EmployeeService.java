package com.extramarks.springboot.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.extramarks.springboot.model.Employee;
import com.extramarks.springboot.model.Skills;

@Service
public interface EmployeeService {	
	
	public List<Employee> getAllEMployees();
	
	public Employee getEmployeeById(String id);
	
	public ResponseEntity<Map<String, String>> createEmployee(Employee employee);
	
	public ResponseEntity<Employee> updateEmployee(String id, Employee employee);
	
	public ResponseEntity<Map<String,Boolean>> deleteEmployee(String id);
	
	public ResponseEntity<Employee> updateEmployeeStatus(String id);

	public void saveSkills(List<Skills> skills);
	
}
