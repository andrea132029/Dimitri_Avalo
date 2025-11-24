package com.dimitri.avalo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dimitri.avalo.dto.RecetaSimpleDTO;
import com.dimitri.avalo.dto.RecetaSimpleRequestDTO;
import com.dimitri.avalo.service.RecetaSimpleService;

@RestController
@RequestMapping("/api/recetas-simple")
@CrossOrigin(origins = "*")
public class RecetaSimpleController {

    @Autowired
    private RecetaSimpleService service;

   
    @PostMapping
    public ResponseEntity<RecetaSimpleDTO> crear(@RequestBody RecetaSimpleRequestDTO dto) {
        RecetaSimpleDTO creada = service.crear(dto);

        
        URI location = URI.create("/api/recetas-simple/" + creada.getId());

        return ResponseEntity.created(location).body(creada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaSimpleDTO> obtener(@PathVariable Long id) {
        RecetaSimpleDTO dto = service.obtener(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<RecetaSimpleDTO>> listar() {
        List<RecetaSimpleDTO> lista = service.listar();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetaSimpleDTO> actualizar(
            @PathVariable Long id,
            @RequestBody RecetaSimpleRequestDTO dto) {

        RecetaSimpleDTO actualizado = service.modificar(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
