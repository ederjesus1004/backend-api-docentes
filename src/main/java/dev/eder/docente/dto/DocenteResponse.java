package dev.eder.docente.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record DocenteResponse(
    Long id,
    String dni,
    String nombres,
    String apellidos,
    String descripcion,
    String email,
    String curso,
    Integer horasSemanales,
    LocalDateTime fechaRegistro
) {

}
