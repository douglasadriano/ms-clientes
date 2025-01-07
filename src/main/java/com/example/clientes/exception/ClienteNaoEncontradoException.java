package com.example.clientes.exception;

/**
 * Exceção personalizada para casos onde um cliente não é encontrado.
 */
public class ClienteNaoEncontradoException extends RuntimeException {

    /**
     * Construtor que aceita uma mensagem personalizada.
     * @param mensagem Mensagem descritiva do erro.
     */
    public ClienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
