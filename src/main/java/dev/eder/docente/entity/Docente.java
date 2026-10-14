package dev.eder.docente.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "docentes")
@Data
@NoArgsConstructor
public class Docente {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    @Column(nullable = false, length = 80)
    private String nombres;

    @Column(nullable = false, length = 80)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 50)
    private String curso;

    @Column(name = "hora_semanales", nullable = false)
    private Integer horasSemanales;

    @Column(name = "fecha_registrado", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    void alCrear(){
        this.fechaRegistro=LocalDateTime.now();
    }
    
}
