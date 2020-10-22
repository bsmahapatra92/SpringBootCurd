package com.bsmtech.bsm.service;

import java.util.List;
import java.util.Optional;

import com.bsmtech.bsm.model.Student;

public interface IStudentService {
	
	public Integer saveStudent(Student s);
	public void updateStudent(Student s);
	public void deleteStudent(Integer id);
	public Optional<Student> getOneStudent(Integer id);
	public List<Student> 	 getAllStudent();
	public boolean isStudentExist(Integer id);

}
