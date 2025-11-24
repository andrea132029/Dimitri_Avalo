package com.dimitri.avalo.controller;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dimitri.avalo.dto.PreparacionDTO;
import com.dimitri.avalo.dto.RecetaDTO;
import com.dimitri.avalo.dto.RecetaRequestDTO;
import com.dimitri.avalo.entity.Receta;
import com.dimitri.avalo.service.RecetaService;

@RestController
@RequestMapping("/api/recetas")
@CrossOrigin(origins = "*")
public class RecetaController {
                              
    @Autowired
    private RecetaService recetaService;

    //enpoint
    
    @PostMapping
    public ResponseEntity<RecetaDTO> crearReceta(@RequestBody RecetaRequestDTO dto) {
        RecetaDTO resultado = recetaService.crearDesdeDTO(dto);
        return ResponseEntity.ok(resultado);
    }
    @PostMapping("/{id}/preparar")
    public ResponseEntity<PreparacionDTO> preparar(
            @PathVariable Long id,
            @RequestParam int raciones) {
        return ResponseEntity.ok(recetaService.prepararReceta(id, raciones));
    }


  @DeleteMapping("/{id}")
  public void eliminarReceta(@PathVariable Long id) {
      recetaService.eliminarReceta(id);
  }


    @PutMapping("/{id}")
    public Receta actualizarReceta(@PathVariable Long id, @RequestBody Receta receta) {
        return recetaService.modificarReceta(id, receta);
    }

   

    @GetMapping
    public List<Receta> filtrar(@RequestParam(required = false) String nombre,
            @RequestParam(required = false) Float minCalorias,
            @RequestParam(required = false) Float maxCalorias) {
return recetaService.filtrar(nombre, minCalorias, maxCalorias);
}

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecetaById(@PathVariable Long id) {
        var receta = recetaService.buscarPorId(id);

        if (receta == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(receta);
    }


    }

