package com.dimitri.avalo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dimitri.avalo.entity.RecetaSimple;

public interface RecetaSimpleRepository extends JpaRepository<RecetaSimple, Long> {
    boolean existsByNombre(String nombre);
}
