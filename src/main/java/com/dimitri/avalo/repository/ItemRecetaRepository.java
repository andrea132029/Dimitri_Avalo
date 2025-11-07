package com.dimitri.avalo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dimitri.avalo.entity.ItemReceta;
@Repository
public interface ItemRecetaRepository extends JpaRepository<ItemReceta, Long> {

}