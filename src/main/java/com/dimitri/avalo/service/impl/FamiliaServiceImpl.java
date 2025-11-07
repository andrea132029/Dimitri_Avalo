package com.dimitri.avalo.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dimitri.avalo.dto.FamiliaDTO;
import com.dimitri.avalo.dto.FamiliaRequestDTO;
import com.dimitri.avalo.entity.Familia;
import com.dimitri.avalo.entity.Integrante;
import com.dimitri.avalo.exception.Excepcion;
import com.dimitri.avalo.repository.FamiliaRepository;
import com.dimitri.avalo.repository.IntegranteRepository;
import com.dimitri.avalo.service.FamiliaService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void eliminar(Long id) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int contarIntegrantesActivos(Long id) {
			// TODO Auto-generated method stub
			return 0;
		}
}