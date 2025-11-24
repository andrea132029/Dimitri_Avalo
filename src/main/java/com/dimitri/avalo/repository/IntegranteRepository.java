package com.dimitri.avalo.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dimitri.avalo.entity.Integrante;
@Repository
public interface IntegranteRepository extends JpaRepository<Integrante, Long> {
    
    // Método para verificar si existe un DNI
    boolean existsByDni(Long dni);
    
    // Método para buscar por DNI
    Optional<Integrante> findByDni(Long dni);
    
    // Método para listar integrantes activos de una familia
    List<Integrante> findByFamiliaIdAndActivoTrue(Long familiaId);
}