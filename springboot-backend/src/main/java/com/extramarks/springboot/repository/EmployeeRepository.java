package com.extramarks.springboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.extramarks.springboot.model.Employee;



@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
	public List<Employee> findByStatus(Long status);

	@Query("{ 'id' : ?0 }")
    Optional<Employee> findById(String id);
	public Employee findByEmailId(String emailId);
	public Employee findByMobile(String mobile);
}
