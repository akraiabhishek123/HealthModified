   package com.example.service;

import java.util.List;

import com.example.entity.Specialization;

public interface ISpecialization {
	public Long saveSpecialization(Specialization spec);
	public List<Specialization> getAllSpecializations();
	public void removeSpecialization(Long id);
	public Specialization getoneSpecialization(Long id);
	public void updateSpecialization(Specialization spec);
	
	public boolean isSpecCodeExist(String specCode);
	public boolean isSpecCodeExistForEdit(String specCode,Long id);
	//public boolean isSpecCodeExistForEdit(String code, Long id);
}
