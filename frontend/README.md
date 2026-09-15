# OptiGest ERP — Diseño Front-End
## GA6-220501096-AA4-EV03

**Aprendiz:** Isaac Daniel Ospino Utria  
**Ficha:** 3186674  
**Programa:** Análisis y Desarrollo de Software — SENA  
**Instructor:** Andrés Felipe Parra Martínez  
**Fecha:** Junio 2026  

---

## Descripción

Diseño front-end del Sistema ERP OptiGest para una empresa del sector de aluminio y vidrio. Aplica HTML, CSS y JavaScript puros (sin frameworks), cumpliendo los requisitos funcionales documentados en las evidencias anteriores.

## Estructura del proyecto

```
optigest/
├── login.html          → Pantalla de inicio de sesión (RF-01)
├── index.html          → SPA principal con todos los módulos
├── css/
│   └── main.css        → Sistema de diseño: variables, layout, componentes
├── js/
│   └── app.js          → Navegación SPA, datos demo, helpers (toast, modal, validación)
└── README.md
```

## Módulos implementados

| Módulo        | Requisitos cubiertos | Funcionalidades                                         |
|---------------|----------------------|---------------------------------------------------------|
| Login         | RF-01                | Validación, alerta de error, accesos demo               |
| Dashboard     | —                    | KPIs, gráficos (Chart.js), tabla de últimas ventas      |
| Usuarios      | RF-02 a RF-05        | Tabla con búsqueda, modal crear, confirmar eliminar      |
| Ventas        | RF-06                | Filtro por fechas, totalizador, tabla, exportar PDF/Excel|
| Producción    | RF-07, RF-09         | Barras de cumplimiento semanal, gráfico mensual         |
| Compras       | RF-08, RF-11         | Tabla historial, modal registrar compra con validación  |
| Proveedores   | RF-10                | Tabla con búsqueda y acciones                           |

## Cómo ejecutar

Abre `login.html` en cualquier navegador moderno. No requiere servidor ni instalación.

- Usuario demo: `admin` / contraseña: `admin123`
- O usar los accesos rápidos en la pantalla de login.

## Principios de usabilidad aplicados

- Control de roles y acceso restringido (RNF-01, RNF-02)
- Validación de formularios en tiempo real con mensajes de error claros
- Notificaciones toast no bloqueantes para feedback de acciones
- Confirmación explícita antes de eliminar (RNF prevención de errores)
- Diseño responsivo (desktop, tablet, móvil)
- Accesibilidad: atributos `aria-label`, `role`, foco de teclado visible
- Reducción de movimiento respetada (`prefers-reduced-motion`)
