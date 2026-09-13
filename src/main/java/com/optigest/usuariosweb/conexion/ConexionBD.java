package com.optigest.usuariosweb.conexion;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase responsable de abrir conexiones JDBC hacia la base de datos PostgreSQL.
 * Los parametros de conexion se leen de src/main/resources/db.properties
 * para no dejar credenciales escritas directamente en el codigo fuente.
 */
public final class ConexionBD {

    private static final String ARCHIVO_CONFIGURACION = "db.properties";
    private static final Properties PROPIEDADES = cargarPropiedades();

    private ConexionBD() {
        // Clase de utilidad: no debe instanciarse.
    }

    private static Properties cargarPropiedades() {
        Properties propiedades = new Properties();
        try (InputStream entrada = ConexionBD.class.getClassLoader()
                .getResourceAsStream(ARCHIVO_CONFIGURACION)) {
            if (entrada == null) {
                throw new IllegalStateException(
                        "No se encontro el archivo " + ARCHIVO_CONFIGURACION + " en el classpath.");
            }
            propiedades.load(entrada);
        } catch (IOException excepcion) {
            throw new IllegalStateException(
                    "Error leyendo " + ARCHIVO_CONFIGURACION, excepcion);
        }
        return propiedades;
    }

    /**
     * Abre y retorna una nueva conexion JDBC hacia la base de datos.
     * Quien invoque este metodo es responsable de cerrar la conexion
     * (idealmente usando try-with-resources).
     */
    public static Connection obtenerConexion() throws SQLException {
        String url = PROPIEDADES.getProperty("db.url");
        String usuario = PROPIEDADES.getProperty("db.usuario");
        String clave = PROPIEDADES.getProperty("db.clave");
        return DriverManager.getConnection(url, usuario, clave);
    }
}
