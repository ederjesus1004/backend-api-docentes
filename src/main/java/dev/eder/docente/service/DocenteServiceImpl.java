package dev.eder.docente.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.eder.docente.dto.DocenteRequest;
import dev.eder.docente.dto.DocenteResponse;
import dev.eder.docente.entity.Docente;
import dev.eder.docente.exception.RecursoDuplicadoException;
import dev.eder.docente.exception.RecursoNoEncontradoException;
import dev.eder.docente.mapper.DocenteMapper;
import dev.eder.docente.repository.DocenteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocenteServiceImpl implements DocenteService {

    private final DocenteRepository docenteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DocenteResponse> listar() {
        return docenteRepository.findAll()
                .stream()
                .map(DocenteMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DocenteResponse obtenerPorId(Long id) {
        return DocenteMapper.toResponse(buscarDocente(id));
    }

    // RECETA crear: Normalizar · Reglas · Mapear · Guardar
    @Override
    @Transactional
    public DocenteResponse crear(DocenteRequest request) {
        // 1. Normalizar
        String email = DocenteMapper.normalizarEmail(request.email());

        // 2. Reglas (una por campo único) → Duplicado = 409
        if (docenteRepository.existsByDni(request.dni())) {
            throw new RecursoDuplicadoException("Ya existe un docente con el DNI " + request.dni());
        }
        if (docenteRepository.existsByEmail(email)) {
            throw new RecursoDuplicadoException("Ya existe un docente con el email " + email);
        }

        // 3. Mapear
        Docente docente = DocenteMapper.toEntity(request);

        // 4. Guardar y responder
        return DocenteMapper.toResponse(docenteRepository.save(docente));
    }

    // RECETA actualizar: Buscar + crear con 3 cambios (AndIdNot, actualizarEntidad,
    // sin new)
    @Override
    @Transactional
    public DocenteResponse actualizar(Long id, DocenteRequest request) {
        // 0. Buscar (404 si no existe)
        Docente docente = buscarDocente(id);
        // 1. Normalizar
        String email = DocenteMapper.normalizarEmail(request.email());

        // 2. Reglas contra OTRO docente → AndIdNot + id → sigue siendo Duplicado = 409
        if (docenteRepository.existsByDniAndIdNot(request.dni(), id)) {
            throw new RecursoDuplicadoException("Ya existe un docente con el DNI " + request.dni());
        }
        if (docenteRepository.existsByEmailAndIdNot(email, id)) {
            throw new RecursoDuplicadoException("Ya existe un docente con el email " + email);
        }

        // 3. Mapear sobre el que ya existe
        DocenteMapper.actualizarEntidad(request, docente);

        // 4. Guardar y responder
        return DocenteMapper.toResponse(docenteRepository.save(docente));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Docente docente = buscarDocente(id);
        docenteRepository.delete(docente);
    }

    private Docente buscarDocente(Long id) {
        return docenteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el docente con id " + id));
    }

}
