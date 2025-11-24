package com.dimitri.avalo.controller;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dimitri.avalo.dto.PreparacionDTO;
import com.dimitri.avalo.dto.PreparacionRequestDTO;
import com.dimitri.avalo.entity.Preparacion;
import com.dimitri.avalo.entity.Receta;
import com.dimitri.avalo.service.PreparacionService;
import com.dimitri.avalo.service.RecetaService;

@RestController
@RequestMapping("/api/preparaciones")
@CrossOrigin(origins = "*")
public class PreparacionController {
/*
    @Autowired
    private PreparacionService preparacionService;

  
    @GetMapping
    public List<Preparacion> listar() {
        return preparacionService.listarPreparaciones();
    }

    @PostMapping
    public ResponseEntity<Preparacion> registrar(@RequestBody Preparacion preparacion) {
        return ResponseEntity.ok(preparacionService.registrarPreparacion(preparacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Preparacion> modificar(@PathVariable Long id, @RequestBody Preparacion p) {
        return ResponseEntity.ok(preparacionService.modificarFechaPreparacion(id, p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        preparacionService.eliminarPreparacion(id);
        return ResponseEntity.noContent().build();
    }
*/
	 @Autowired
	    private PreparacionService preparacionService;

	    //  POST — Registrar una nueva preparación 
	    @PostMapping
	    public ResponseEntity<PreparacionDTO> registrar(@RequestBody Preparacion preparacion) {
	        Preparacion guardada = preparacionService.registrarPreparacion(preparacion);

	        PreparacionDTO dto = new PreparacionDTO();
	        dto.setId(guardada.getId());
	        dto.setRecetaId(guardada.getReceta().getId());
	        dto.setRacionesGeneradas(guardada.getCantidadRaciones());
	        dto.setFechaPreparacion(guardada.getFechaPreparacion());

	        return ResponseEntity.ok(dto);
	    }

	    //  GET — Listar todas las preparaciones (historial)
	    @GetMapping
	    public ResponseEntity<List<PreparacionDTO>> listar() {
	        List<PreparacionDTO> lista = preparacionService.listarPreparaciones().stream()
	                .map(p -> {
	                    PreparacionDTO dto = new PreparacionDTO();
	                    dto.setId(p.getId());
	                    dto.setRecetaId(p.getReceta().getId());
	                    dto.setRacionesGeneradas(p.getCantidadRaciones());
	                    dto.setFechaPreparacion(p.getFechaPreparacion());
	                    return dto;
	                })
	                .collect(Collectors.toList());
	        return ResponseEntity.ok(lista);
	    }
	    
	    @GetMapping("/receta/{recetaId}")
	    public ResponseEntity<List<Preparacion>> listarPorReceta(@PathVariable Long recetaId) {
	        List<Preparacion> lista = preparacionService.listarPreparaciones()
	                .stream()
	                .filter(p -> p.getReceta().getId().equals(recetaId))
	                .toList();
	        return ResponseEntity.ok(lista);
	    }

	}