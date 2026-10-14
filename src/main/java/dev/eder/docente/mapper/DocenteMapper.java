package dev.eder.docente.mapper;

import dev.eder.docente.dto.DocenteRequest;
import dev.eder.docente.dto.DocenteResponse;
import dev.eder.docente.entity.Docente;

// RECETA: 3 métodos + 1 ayudante
// "El Request entra con set, el Response sale con builder"
public final class DocenteMapper {

    private DocenteMapper() {
    }

    // 1. toEntity → new + actualizarEntidad
    public static Docente toEntity(DocenteRequest request) {
        Docente docente = new Docente();
        actualizarEntidad(request, docente);
        return docente;
    }

    // 2. actualizarEntidad → un set por cada campo del REQUEST
    // texto: trim · email: normalizarEmail · número: tal cual
    public static void actualizarEntidad(DocenteRequest request, Docente docente) {
        docente.setDni(request.dni().trim());
        docente.setNombres(request.nombres().trim());
        docente.setApellidos(request.apellidos().trim());
        docente.setEmail(normalizarEmail(request.email()));
        docente.setCurso(request.curso().trim());
        docente.setHorasSemanales(request.horasSemanales());
    }

    // 3. toResponse → un .campo() por cada campo del RESPONSE
    public static DocenteResponse toResponse(Docente docente) {
        return DocenteResponse.builder()
                .id(docente.getId())
                .dni(docente.getDni())
                .nombres(docente.getNombres())
                .apellidos(docente.getApellidos())
                // "Juan Pérez Soto - Matemática" ← ojo con el espacio
                .descripcion(docente.getNombres() + " " + docente.getApellidos() + " - " + docente.getCurso())
                .email(docente.getEmail())
                .curso(docente.getCurso())
                .horasSemanales(docente.getHorasSemanales())
                .fechaRegistro(docente.getFechaRegistro())
                .build();
    }

    // + ayudante → trim + toLowerCase
    public static String normalizarEmail(String email) {
        return email.trim().toLowerCase();
    }
}