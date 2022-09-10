package com.example.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Specialization;
import com.example.exception.SpecializationNotFoundException;
import com.example.repository.SpecializationRepository;
import com.example.service.ISpecialization;
@Service
public class SpecializationServiceImpl implements ISpecialization {
@Autowired
private SpecializationRepository repo;
	@Override
	public Long saveSpecialization(Specialization spec) {
		return repo.save(spec).getId();
	}

	@Override
	public List<Specialization> getAllSpecializations() {
		return repo.findAll();
	}

	@Override
	public void removeSpecialization(Long id) {
		//repo.deleteById(id);
		repo.delete(getoneSpecialization(id));
	}

	@Override
	public Specialization getoneSpecialization(Long id) {
		Optional<Specialization> optional = repo.findById(id);
		if(optional.isPresent()) {
		return optional.get() ;
	}
		else {
			throw new SpecializationNotFoundException(id+ " Not Found");
		}
		//return repo.findById(id).orElseThrow(()-> new SpecializationNotFoundException(id+ "Not Found"));
	}

	@Override
	public void updateSpecialization(Specialization spec) {
		repo.save(spec);
	}
	
	@Override
	public boolean isSpecCodeExist(String specCode) {
		/*
		 * Integer count = repo.getSpecCodeCount(specCode); boolean exist = count>0 ?
		 * true : false; return exist;
		 */ 
		return repo.getSpecCodeCount(specCode)>0;
	}
	@Override
	public boolean isSpecCodeExistForEdit(String specCode, Long id) {
		return repo.getSpecCodeCountForEdit(specCode,id)>0;
	}

}
