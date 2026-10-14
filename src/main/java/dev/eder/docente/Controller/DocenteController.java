package dev.eder.docente.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.eder.docente.dto.DocenteRequest;
import dev.eder.docente.dto.DocenteResponse;
import dev.eder.docente.service.DocenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/docentes")
@RequiredArgsConstructor
public class DocenteController {

    private final DocenteService docenteService;

    @GetMapping
    public List<DocenteResponse> listar() {
        return docenteService.listar();
    }

    @GetMapping("/{id}")
    public DocenteResponse obtenerPorId(@PathVariable Long id) {
        return docenteService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DocenteResponse crear(@Valid @RequestBody DocenteRequest request) {
        return docenteService.crear(request);
    }

    @PutMapping("/{id}")
    public DocenteResponse actualizar(@PathVariable Long id, @Valid @RequestBody DocenteRequest request) {
        return docenteService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        docenteService.eliminar(id);
    }

}
