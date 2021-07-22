package com.extramarks.springboot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.extramarks.springboot.model.Skills;

@Repository
public interface SkillRepository extends MongoRepository<Skills, String> {

}
