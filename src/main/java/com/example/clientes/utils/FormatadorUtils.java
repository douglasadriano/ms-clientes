package com.example.clientes.utils;

/**
 * Classe utilitária para formatação de dados como e-mail e telefone.
 */
public class FormatadorUtils {

    /**
     * Formata e valida o e-mail para garantir que esteja no formato correto.
     * @param email E-mail a ser formatado.
     * @return E-mail formatado.
     * @throws IllegalArgumentException Se o e-mail tiver um formato inválido.
     */
    public static String formatarEmail(String email) {
        if (email != null) {
            email = email.trim().toLowerCase();
            if (!email.matches("^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,6}$")) {
                throw new IllegalArgumentException("Formato de e-mail inválido.");
            }
        }
        return email;
    }

    /**
     * Formata o telefone para o formato "(XX) XXXXX-XXXX".
     * @param telefone Telefone a ser formatado.
     * @return Telefone formatado.
     */
    public static String formatarTelefone(String telefone) {
        if (telefone != null) {
            if (telefone.matches("\\d{10}")) { // Telefone com 10 dígitos
                return String.format("(%s) %s-%s",
                        telefone.substring(0, 2),  // DDD
                        telefone.substring(2, 6), // Parte inicial do número
                        telefone.substring(6));   // Parte final do número
            } else if (telefone.matches("\\d{11}")) { // Telefone com 11 dígitos
                return String.format("(%s) %s-%s",
                        telefone.substring(0, 2),  // DDD
                        telefone.substring(2, 7), // Parte inicial do número
                        telefone.substring(7));   // Parte final do número
            }
        }
        throw new IllegalArgumentException("Número de telefone inválido. Deve conter 10 ou 11 dígitos.");
    }

}
