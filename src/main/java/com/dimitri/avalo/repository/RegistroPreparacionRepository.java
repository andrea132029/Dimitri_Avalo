package com.dimitri.avalo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dimitri.avalo.entity.Receta;
import com.dimitri.avalo.entity.RegistroPreparacion;
@Repository
public interface RegistroPreparacionRepository extends JpaRepository<RegistroPreparacion, Long> {
	

	 List<RegistroPreparacion> findByReceta(Receta receta);
	 
	
}