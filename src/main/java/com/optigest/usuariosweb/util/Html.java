package com.optigest.usuariosweb.util;

/** Utilidad para escapar texto antes de mostrarlo en HTML (evita inyeccion de codigo). */
public final class Html {

    private Html() {
        // Clase de utilidad: no debe instanciarse.
    }

    public static String escapar(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
