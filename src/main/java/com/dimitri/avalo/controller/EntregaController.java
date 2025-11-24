package com.dimitri.avalo.controller;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dimitri.avalo.entity.Entrega;
import com.dimitri.avalo.service.EntregaService;

@RestController
@RequestMapping("/api/entregas")
@CrossOrigin(origins = "*")
public class EntregaController {

	 @Autowired
	    private EntregaService entregaService;

	    @GetMapping
	    public ResponseEntity<List<Entrega>> listarEntregas(
	            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
	            @RequestParam(required = false) Long familiaId) {

	        if (fecha != null && familiaId != null)
	            return ResponseEntity.ok(entregaService.listarPorFechaYFamilia(fecha, familiaId));

	        if (fecha != null)
	            return ResponseEntity.ok(entregaService.listarPorFecha(fecha));

	        return ResponseEntity.ok(entregaService.listarPorFecha(LocalDate.now()));
	    }

	    @PostMapping
	    public ResponseEntity<Entrega> registrar(@RequestBody Entrega entrega) {
	        return ResponseEntity.ok(entregaService.registrar(entrega));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
	        entregaService.eliminar(id);
	        return ResponseEntity.noContent().build();
	    }
	}