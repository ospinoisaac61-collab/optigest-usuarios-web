# OptiGest ERP — Módulo Web de Usuarios (Servlets + JSP)

Evidencia **GA7-220501096-AA2-EV02** — Módulos de software codificados y probados.

Versión web del módulo de **Gestión de Usuarios** de OptiGest ERP, construida
con **Servlets** (Java EE / Jakarta Servlet 4.0) y **JSP**, persistiendo en la
misma base de datos **PostgreSQL** usada en la evidencia AA2-EV01 (módulo de
escritorio con JDBC).

## Requisitos cubiertos

| Requisito de la evidencia            | Dónde se cumple                                                   |
|---------------------------------------|--------------------------------------------------------------------|
| Formularios HTML con servlets         | `formulario.jsp` (formulario HTML) envía datos a `UsuarioServlet`   |
| Uso de métodos GET y POST             | `UsuarioServlet.doGet` (listar, mostrar formulario, eliminar) y `UsuarioServlet.doPost` (crear/actualizar) |
| Elementos de JSP                      | `index.jsp`, `WEB-INF/views/lista.jsp`, `WEB-INF/views/formulario.jsp` (directivas, scriptlets, expresiones) |
| Conexión a base de datos (JDBC)       | `ConexionBD` + `UsuarioDAOImpl` (heredado del módulo AA2-EV01)      |

## Rutas de la aplicación

| Método | Ruta                              | Acción                                  |
|--------|------------------------------------|------------------------------------------|
| GET    | `/usuarios`                        | Lista todos los usuarios                 |
| GET    | `/usuarios?accion=nuevo`           | Muestra el formulario vacío              |
| GET    | `/usuarios?accion=editar&id=N`     | Muestra el formulario con los datos de N |
| GET    | `/usuarios?accion=eliminar&id=N`   | Elimina el usuario N                     |
| POST   | `/usuarios`                        | Crea o actualiza (según si viene `id`)   |

## Arquitectura del proyecto

```
src/main/java/com/optigest/usuariosweb/
├── modelo/Usuario.java          # Entidad (POJO)
├── conexion/ConexionBD.java     # Conexion JDBC (lee db.properties)
├── dao/UsuarioDAO.java          # Contrato de acceso a datos (CRUD)
├── dao/UsuarioDAOImpl.java      # Implementacion JDBC (PreparedStatement)
├── util/Seguridad.java          # Hash SHA-256 de contraseñas
└── servlet/UsuarioServlet.java  # Servlet: doGet / doPost
src/main/webapp/
├── index.jsp                    # Redirige al listado
├── css/estilos.css
└── WEB-INF/
    ├── web.xml                  # welcome-file-list
    └── views/
        ├── lista.jsp            # Tabla de usuarios (JSP)
        └── formulario.jsp       # Formulario HTML (crear/editar)
src/main/resources/db.properties.example
sql/optigest_usuarios.sql
```

### Estándares de codificación aplicados

- **Paquetes**: minúsculas, dominio inverso (`com.optigest.usuariosweb.*`).
- **Clases**: `PascalCase` y sustantivos (`UsuarioServlet`, `ConexionBD`).
- **Métodos**: `camelCase` y verbos (`doGet`, `doPost`, `insertarUsuario`, `mostrarListado`).
- **Variables**: `camelCase`, nombres descriptivos en español.
- **Seguridad**: contraseñas con hash SHA-256, consultas con `PreparedStatement`
  (sin concatenar SQL), patrón Post/Redirect/Get para evitar reenvíos de formulario.

## Requisitos para ejecutar

- JDK 17 o superior.
- Maven 3.8+.
- PostgreSQL 12+ en ejecución local (la misma base `optigest_bd` de AA2-EV01).

## Puesta en marcha

1. **Base de datos.** Si ya hiciste la evidencia AA2-EV01 en esta misma máquina,
   la base `optigest_bd` y la tabla `usuario` ya existen: no necesitas hacer
   nada más. Si es la primera vez, ejecuta:

   ```bash
   psql -U postgres -f sql/optigest_usuarios.sql
   ```

2. **Configurar la conexión.** Copia `src/main/resources/db.properties.example`
   como `src/main/resources/db.properties` y coloca tu contraseña real:

   ```properties
   db.url=jdbc:postgresql://localhost:5432/optigest_bd
   db.usuario=postgres
   db.clave=tu_password
   ```

3. **Ejecutar el servidor web embebido (no requiere instalar Tomcat aparte):**

   ```bash
   mvn clean jetty:run
   ```

   Abre en el navegador:

   ```
   http://localhost:8080/optigest-usuarios-web/
   ```

   (Redirige automáticamente al listado de usuarios en `/usuarios`.)

4. Para detener el servidor, `Ctrl+C` en la terminal.

## Despliegue alternativo en Tomcat

Si prefieres usar un Tomcat instalado aparte:

```bash
mvn clean package
```

Copia el archivo `target/optigest-usuarios-web.war` a la carpeta
`webapps/` de tu Tomcat y arráncalo; la aplicación quedará disponible en
`http://localhost:8080/optigest-usuarios-web/`.

## Artefactos de referencia

Este módulo reutiliza el modelo de datos, el diagrama de clases y las
historias de usuario del módulo de Usuarios definidos en las evidencias
previas del proyecto OptiGest ERP, cambiando únicamente la capa de
presentación de escritorio (Swing) por una interfaz web (Servlets + JSP).

La tabla `usuario` y sus columnas (`id_usuario`, `nombre`, `email`,
`password`, `rol`, `estado`, `fecha_creacion`) siguen exactamente el diseño
oficial de base de datos del proyecto, definido en la evidencia
GA6-220501096-AA2-EV02/EV03 (diagrama entidad-relación y script físico en
MySQL). Este script está adaptado a PostgreSQL, pero conserva los mismos
nombres de tabla y columnas.
