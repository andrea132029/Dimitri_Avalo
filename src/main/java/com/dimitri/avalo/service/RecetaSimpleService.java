package com.dimitri.avalo.service;

import java.util.List;
import com.dimitri.avalo.dto.RecetaSimpleDTO;
import com.dimitri.avalo.dto.RecetaSimpleRequestDTO;

public interface RecetaSimpleService {

    RecetaSimpleDTO crear(RecetaSimpleRequestDTO dto);
    RecetaSimpleDTO obtener(Long id);
    List<RecetaSimpleDTO> listar();
    RecetaSimpleDTO modificar(Long id, RecetaSimpleRequestDTO dto);
    void eliminar(Long id);
}

