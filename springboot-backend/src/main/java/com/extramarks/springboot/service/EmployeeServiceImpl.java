package com.extramarks.springboot.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import com.extramarks.springboot.exception.ResourceNotFoundException;
import com.extramarks.springboot.model.Employee;
import com.extramarks.springboot.model.Skills;
import com.extramarks.springboot.repository.EmployeeRepository;
import com.extramarks.springboot.repository.SkillRepository;

@Component
public class EmployeeServiceImpl implements EmployeeService{
	
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/webapp/imagedata";
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private SkillRepository skillRepository;
	
	@Override
	public List<Employee> getAllEMployees() {
		return employeeRepository.findByStatus(1L);
	}

	@Override
	public Employee getEmployeeById(String id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee doesn't exist with id: " + id));
		
		return employee;
	}

	@Override
	public ResponseEntity<Map<String, String>> createEmployee(Employee employee) {
		saveSkills(employee.getSkills());
		employee.setStatus(1L);
		
		Employee emp1 = employeeRepository.findByEmailId(employee.getEmailId());
		Employee emp2 = employeeRepository.findByMobile(employee.getMobile());
		
		if(emp1 != null) {
			Map<String, String> response = new HashMap<>();		
			response.put("response", "false1");		
			return ResponseEntity.ok(response);
		}
		
		if(emp2 != null) {
			Map<String, String> response = new HashMap<>();		
			response.put("response", "false2");		
			return ResponseEntity.ok(response);
		}
		
		employeeRepository.save(employee);
		Map<String, String> response = new HashMap<>();		
		response.put("response", "true");
		response.put("id", employee.getId());
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<Employee> updateEmployee(String id, Employee employee) {
		Employee existingEmp = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee doesn't exist with id: " + id));
		
		List<Skills> newSkills = employee.getSkills();
		
		List<Skills> oldSkills = existingEmp.getSkills();
		
		for(Skills skill: oldSkills) {
			if(!(newSkills.contains(skill))) {
				skillRepository.deleteById(skill.getId());
			}
		}
		
		for(Skills skill: newSkills) {
			if(!(oldSkills.contains(skill))) {
				skillRepository.save(skill);
			}
		}
		
		existingEmp.setSkills(newSkills);					
		existingEmp.setEmailId(employee.getEmailId());
		existingEmp.setFirstName(employee.getFirstName());
		existingEmp.setLastName(employee.getLastName());
		existingEmp.setGender(employee.getGender());
		existingEmp.setState(employee.getState());
		existingEmp.setMobile(employee.getMobile());
		existingEmp.setImage(employee.getImage());
		
		Employee updatedEmp = employeeRepository.save(existingEmp);
		
		return ResponseEntity.ok(updatedEmp);
	}

	@Override
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(String id) {
		Employee existingEmp = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee doesn't exist with id: " + id));
		
		List<Skills> skills = existingEmp.getSkills();
		
		for(Skills skill : skills) {
			skillRepository.deleteById(skill.getId());
		}
		
		employeeRepository.delete(existingEmp);		
		Map<String, Boolean> response = new HashMap<>();		
		response.put("response", Boolean.TRUE);
		
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<Employee> updateEmployeeStatus(String id) {
		Employee existingEmp = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee doesn't exist with id: " + id));
		
		existingEmp.setStatus(5L);		
		Employee updatedEmp = employeeRepository.save(existingEmp);		
		return ResponseEntity.ok(updatedEmp);
	}

	@Override
	public void saveSkills(List<Skills> skills) {
		for(Skills skill: skills) {
			skillRepository.save(skill);
		}		
	}
	private Map<String, Object> validation(Employee userData) {
        Map<String, Object> mapvalidate = new HashMap<>();
        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        try {
            //Email
            Pattern emailpattern = Pattern.compile(emailRegex);
            Matcher emailmatcher = emailpattern.matcher(userData.getEmailId());
            if (!emailmatcher.matches()) {
                mapvalidate.put("Email", false);
                mapvalidate.put("Valid", false);
                return mapvalidate;
            }

            //PhoneNo
            Pattern phone = Pattern.compile("^\\d{10}$");
            Matcher phoneMatcher = phone.matcher(userData.getMobile());
            if (!phoneMatcher.matches()) {
                mapvalidate.put("Mobile", false);
                mapvalidate.put("Valid", false);
                return mapvalidate;
            }
            mapvalidate.put("Valid", true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            mapvalidate.put("status", "Error");
        }
        return mapvalidate;
	}
}
