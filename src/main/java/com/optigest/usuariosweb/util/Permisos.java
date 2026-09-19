package com.optigest.usuariosweb.util;

import java.util.Map;
import java.util.Set;

/**
 * Reglas de acceso por rol. Cada rol tiene una lista de modulos permitidos;
 * "inicio" (el dashboard) es accesible para cualquier usuario autenticado.
 */
public final class Permisos {

    public static final String USUARIOS = "usuarios";
    public static final String PRODUCTOS = "productos";
    public static final String PROVEEDORES = "proveedores";
    public static final String COMPRAS = "compras";

    private static final Map<String, Set<String>> MODULOS_POR_ROL = Map.of(
            "Administrador", Set.of(USUARIOS, PRODUCTOS, PROVEEDORES, COMPRAS),
            "Jefe de compras", Set.of(COMPRAS, PROVEEDORES),
            "Contador", Set.of(COMPRAS),
            "Bodega", Set.of(PRODUCTOS),
            "Vendedor", Set.of(PRODUCTOS),
            "Jefe de taller", Set.of(PRODUCTOS)
    );

    private Permisos() {
        // Clase de utilidad: no debe instanciarse.
    }

    public static boolean puedeAcceder(String rol, String modulo) {
        if ("inicio".equals(modulo)) {
            return true;
        }
        return MODULOS_POR_ROL.getOrDefault(rol, Set.of()).contains(modulo);
    }
}
