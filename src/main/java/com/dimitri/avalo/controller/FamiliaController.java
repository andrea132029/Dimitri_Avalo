package com.dimitri.avalo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dimitri.avalo.dto.FamiliaDTO;
import com.dimitri.avalo.dto.FamiliaRequestDTO;
import com.dimitri.avalo.service.FamiliaService;

@RestController
@RequestMapping("/api/familias")
@CrossOrigin(origins = "*")
public class FamiliaController {

    @Autowired
    private FamiliaService service;

    @GetMapping
    public List<FamiliaDTO> listar() {
        return service.listarActivas();
    }

    @PostMapping
    public ResponseEntity<FamiliaDTO> crear(@RequestBody FamiliaRequestDTO dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FamiliaDTO> modificar(@PathVariable Long id, @RequestBody FamiliaRequestDTO dto) {
        return ResponseEntity.ok(service.modificar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
