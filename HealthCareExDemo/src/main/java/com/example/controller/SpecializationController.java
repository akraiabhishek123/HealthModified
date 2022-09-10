package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.Specialization;
import com.example.exception.SpecializationNotFoundException;
import com.example.service.ISpecialization;
import com.example.view.SpecializationExcelView;

@Controller
@RequestMapping("/spec")
public class SpecializationController {

	@Autowired
	private ISpecialization service;

	/**
	 * 1. display all Specialization
	 */

	@GetMapping("/register")
	public String showRegisterPage() {
		return "specializationRegister";
	}

	@PostMapping("/save")
	public String addSpecialization(@ModelAttribute Specialization specialization, Model model) {
		Long id = service.saveSpecialization(specialization);
		model.addAttribute("msg", "specializatio with   (" + id + " )created");
		return "specializationRegister";

	}

	@GetMapping("/all")
	public String viewAll(Model model, @RequestParam(value = "message", required = false) String message) {
		List<Specialization> list = service.getAllSpecializations();
		model.addAttribute("list", list);
		model.addAttribute("message", message);
		return "SpecializationData";
	}

	@GetMapping("/delete")
	public String reomoveSpecialization(@RequestParam Long id, RedirectAttributes attributes) {
		try {
			service.removeSpecialization(id);
			attributes.addAttribute("message", "specialization deleted with (" + id + " )");
			
		} catch (SpecializationNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());

		}
		return "redirect:all";

	}
	/***
	 * 5. Fetch Data into Edit page
	 *  End user clicks on Link, may enter ID manually.
	 *  If entered id is present in DB
	 *    > Load Row as Object
	 *    > Send to Edit page
	 *    > Fill in Form
	 *  Else 
	 *     > Redirect to all (Data page)
	 *     > Show Error message (Not found)
	 */

	@GetMapping("/edit")
	public String showEdit(@RequestParam Long id, Model model,RedirectAttributes attributes) {
		String page = null;
		try {
			Specialization specialization = service.getoneSpecialization(id);
			model.addAttribute("specialization", specialization);
			page = "specializationEdit";
		} catch(SpecializationNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
			page = "redirect:all";
		}
		return page;
	}

	@PostMapping("/update")
	public String updateSpec(@ModelAttribute Specialization specialization, RedirectAttributes attributes) {
		service.updateSpecialization(specialization);
		attributes.addAttribute("message", "specialization updated with (" + specialization.getId() + " updated");
		return "redirect:all";
	}
	
	/***
	 * 7. Read code and check with service 
	 * Return message back to UI
	 */
	@GetMapping("/checkCode")
	@ResponseBody
	public  String validationSpecCode(@RequestParam String code,@RequestParam Long id) {
		String message = "";
		if(id==0 && service.isSpecCodeExist(code)) { // register check
			message = code + ", already exist";
		} else if(id!=0 && service.isSpecCodeExistForEdit(code,id)) { //edit check
			message = code + ", already exist";
	}
	return message;  //this is not viewName(it is message)
}
	
	/***
	 * 8. export data to excel file 
	 */
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		ModelAndView m = new ModelAndView();
		m.setView(new SpecializationExcelView());
		//read data from DB
		List<Specialization> list = service.getAllSpecializations();
		//send to Excel Impl class
		m.addObject("list",list);
		return m;
	}
}
