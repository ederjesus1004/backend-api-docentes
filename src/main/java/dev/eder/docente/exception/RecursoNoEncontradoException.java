package dev.eder.docente.exception;

public class RecursoNoEncontradoException extends RuntimeException{

    public RecursoNoEncontradoException (String mensaje){
        super(mensaje);
    }
}
