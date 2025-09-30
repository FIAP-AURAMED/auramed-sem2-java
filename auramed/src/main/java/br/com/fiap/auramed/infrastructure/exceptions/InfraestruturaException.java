package br.com.fiap.auramed.infrastructure.exceptions;

public class InfraestruturaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InfraestruturaException(String message) {
        super(message);
    }

    public InfraestruturaException(String message, Throwable cause) {
        super(message, cause);
    }
}