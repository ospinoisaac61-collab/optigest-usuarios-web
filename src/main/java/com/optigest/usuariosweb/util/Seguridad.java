package com.optigest.usuariosweb.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilidades de seguridad. Las contrasenias nunca se guardan en texto
 * plano en la base de datos: se aplica un hash SHA-256 antes de persistirlas.
 */
public final class Seguridad {

    private Seguridad() {
        // Clase de utilidad: no debe instanciarse.
    }

    public static String hashearClave(String claveEnTexto) {
        try {
            MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
            byte[] hash = algoritmo.digest(claveEnTexto.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexadecimal = new StringBuilder();
            for (byte b : hash) {
                hexadecimal.append(String.format("%02x", b));
            }
            return hexadecimal.toString();
        } catch (NoSuchAlgorithmException excepcion) {
            throw new IllegalStateException("Algoritmo SHA-256 no disponible.", excepcion);
        }
    }
}
