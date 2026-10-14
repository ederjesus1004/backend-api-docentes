package dev.eder.docente.dto;

import jakarta.validation.constraints.*;

public record DocenteRequest(

        @NotBlank(message = "El DNI es obligatorio") @Pattern(regexp = "^\\d{8}$", message = "El DNI debe tener 8 dígitos") String dni,

        @NotBlank(message = "Los nombres son obligatorios") @Size(max = 80, message = "Los nombres no pueden pasar de 80 caracteres") String nombres,

        @NotBlank(message = "Los apellidos son obligatorios") @Size(max = 80, message = "Los apellidos no pueden pasar de 80 caracteres") String apellidos,

        @NotBlank(message = "El email es obligatorio") @Email(message = "El email no tiene un formato válido") @Size(max = 100, message = "El email no puede pasar de 100 caracteres") String email,

        @NotBlank(message = "El curso es obligatorio") @Size(max = 50, message = "El curso no puede pasar de 50 caracteres") String curso,

        @NotNull(message = "Las horas semanales son obligatorias") @Min(value = 1, message = "Mínimo 1 hora semanal") @Max(value = 40, message = "Máximo 40 horas semanales") Integer horasSemanales) {
}