package com.bsmtech.bsm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bsmtech.bsm.model.Student;
import com.bsmtech.bsm.service.IStudentService;
import com.bsmtech.bsm.util.EmailUtil;

@Controller
@RequestMapping("/student")
public class StudentConrtroller {
	
	@Autowired
	private IStudentService service;
	
	@Autowired
	private EmailUtil emailUtil;
	
	
	
	//0.HomePage
	
			@GetMapping("/home")
			public String showHome(Model model) {
				
				//from backing object
			model.addAttribute("student", new Student());
				return "studentHome";
			}
	
	//1. Show Register page
		@GetMapping("/register")
		public String showReg(Model model) {
			
			//from backing object
			model.addAttribute("student", new Student());
			return "StudentRegister";
		}
	
	
	//2. Save Student
		@PostMapping("/save")
		public String save(@ModelAttribute Student student,Model model) {
			
			Integer id = service.saveStudent(student);
			
			String message = "Student ' "+id+" ' saved";
			
			//send email on save
			if(id!=0) {
				boolean flag = emailUtil.send(student.getStdEmail(), "Welcome", 
						"Hello :"+student.getStdName()+"You Are Successfully Registered");
			if(flag) message+=", Email Also Sent";
			else message+=",Email is not sent";
			}
			//Send Message To UI
			model.addAttribute("message", message );
			//clear the form
			model.addAttribute("student" , new Student());
			 return "StudentRegister";
			
			
			
		}
	
	
	//3. display all students
		@GetMapping("/all")
		public String showAll(Model model) {
			List<Student> list = service.getAllStudent();
			model.addAttribute("list" , list);
			return "StudentData";
		}
		
	
	//4. Delete one Student
		
		@GetMapping("/delete")
		public String delete(@RequestParam Integer id,Model model) {
			
			String message = null;
			if(service.isStudentExist(id)) {
				service.deleteStudent(id);
				message = "Student '"+id+"' Deleted ";
			}else {
				message = "Student '"+id+"' Not Exist ";
			}
			model.addAttribute("message", message);
			
			//Fetch All Latest Data
			List<Student> list = service.getAllStudent();
			model.addAttribute("list" , list);
			//send to Ui
			return "StudentData";
		
		}
	
	//5. Show Edit Page
		/** 
		 * If given input id exist in database 
		 * then goto to edit page, else come to same page
		 * back to data (all)
		 */
		@GetMapping("/edit")
		public String showEdit(@RequestParam Integer id, Model model) {
			
			String page = null;
			Optional<Student> opt = service.getOneStudent(id);
			
			if(opt.isPresent()) {
				//If exist -- goto edit page
				model.addAttribute("student",opt.get());
				page = "StudentEdit";
			}else {
				//given id not exist in DB
				page ="redirect:all";
			}
			return page;
		}
		
	//6. Do Update
		@PostMapping("/update")
		public String update(@ModelAttribute Student student) {
			
			service.updateStudent(student);
			return "redirect:all";
			
		}
	
}
