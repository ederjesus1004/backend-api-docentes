package dev.eder.docente.service;

import java.util.List;

import dev.eder.docente.dto.DocenteRequest;
import dev.eder.docente.dto.DocenteResponse;

public interface DocenteService {

    List<DocenteResponse> listar();

    DocenteResponse obtenerPorId(Long id);

    DocenteResponse crear (DocenteRequest request);

    DocenteResponse actualizar(Long id, DocenteRequest request);

    void eliminar(Long id);
}
