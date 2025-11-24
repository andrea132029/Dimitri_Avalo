package com.dimitri.avalo.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dimitri.avalo.dto.FamiliaDTO;
import com.dimitri.avalo.dto.FamiliaRequestDTO;
import com.dimitri.avalo.entity.Familia;
import com.dimitri.avalo.exception.Excepcion;
import com.dimitri.avalo.repository.FamiliaRepository;
import com.dimitri.avalo.service.FamiliaService;

@Service 
@Transactional
public class FamiliaServiceImpl implements FamiliaService {//implementa los métodos  la interfaz FamiliaService.
	    
	    @Autowired
	    private FamiliaRepository repo;

	    private FamiliaDTO toDTO(Familia f) {
	        return new FamiliaDTO(
	            f.getId(),
	            f.getNombre(),
	            (int) f.contarIntegrantesActivos(), //  cuenta activos
	            f.getFechaAlta(),
	            f.getDireccion()
	        );
	    }

	    @Override
	    public FamiliaDTO crear(FamiliaRequestDTO dto) {
	        Familia f = new Familia();
	        f.setNombre(dto.getNombre());
	        f.setDireccion(dto.getDireccion());
	        repo.save(f);
	        return toDTO(f);
	    }

	    @Override
	    public FamiliaDTO modificar(Long id, FamiliaRequestDTO dto) {
	        Familia f = repo.findById(id)
	                .orElseThrow(() -> new Excepcion("Familia no encontrada"));

	        f.setNombre(dto.getNombre());
	        f.setDireccion(dto.getDireccion());
	        repo.save(f);
	        return toDTO(f);
	    }

	    @Override
	    public List<FamiliaDTO> listarActivas() {
	        return repo.findAllByActivaTrue()
	                   .stream()
	                   .map(this::toDTO)
	                   .collect(Collectors.toList());
	    }

	    @Override
	    public void eliminar(Long id) {
	        Familia f = repo.findById(id)
	                .orElseThrow(() -> new Excepcion("Familia no encontrada"));
	        f.setActiva(false);
	        repo.save(f);
	    }

	    @Override
	    public int contarIntegrantesActivos(Long id) {
	        Familia f = repo.findById(id)
	                .orElseThrow(() -> new Excepcion("Familia no encontrada"));
	        return (int) f.contarIntegrantesActivos();
	    }

	    @Override
	    public Familia obtenerPorId(Long id) {
	        return repo.findById(id).orElse(null);
	    }
	}