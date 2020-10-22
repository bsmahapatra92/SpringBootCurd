package com.bsmtech.bsm.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bsmtech.bsm.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	

	
}
