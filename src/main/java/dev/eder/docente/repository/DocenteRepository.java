package dev.eder.docente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.eder.docente.entity.Docente;

public interface DocenteRepository extends JpaRepository<Docente, Long>{

    // Por cada campo único: uno para crear y uno para actualizar
    boolean existsByDni(String dni);
    boolean existsByDniAndIdNot(String dni, Long id);

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email,Long id);
}
