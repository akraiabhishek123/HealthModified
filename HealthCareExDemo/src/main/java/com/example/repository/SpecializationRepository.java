package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Specialization;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

@Query("SELECT COUNT(specCode) FROM Specialization WHERE specCode=:specCode")
Integer getSpecCodeCount(String specCode);

@Query("SELECT COUNT(specCode) FROM Specialization WHERE specCode=:specCode AND id!=:id")
Integer getSpecCodeCountForEdit(String specCode,Long id); 
}
  