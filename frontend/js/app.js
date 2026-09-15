/* ===================================
   OptiGest ERP — app.js
   Navegación SPA-lite, UI helpers,
   datos de demostración
   =================================== */

'use strict';

// ---- Estado de la aplicación ----
const App = {
  user: { name: 'Isaac Ospino', role: 'Administrador', initials: 'IO' },
  currentPage: 'dashboard',

  // Datos de demostración
  data: {
    usuarios: [
      { id: 1, nombre: 'Laura Martínez', usuario: 'lmartinez', correo: 'lmartinez@optigest.co', rol: 'Vendedor',        estado: 'Activo'    },
      { id: 2, nombre: 'Carlos Herrera', usuario: 'cherrera',  correo: 'cherrera@optigest.co',  rol: 'Jefe de taller',  estado: 'Activo'    },
      { id: 3, nombre: 'Diana Ruiz',     usuario: 'druiz',     correo: 'druiz@optigest.co',     rol: 'Bodega',          estado: 'Activo'    },
      { id: 4, nombre: 'Pedro Salcedo',  usuario: 'psalcedo',  correo: 'psalcedo@optigest.co',  rol: 'Jefe de compras', estado: 'Inactivo'  },
      { id: 5, nombre: 'Ana Gómez',      usuario: 'agomez',    correo: 'agomez@optigest.co',    rol: 'Contador',        estado: 'Activo'    },
    ],
    ventas: [
      { id: 'V-001', fecha: '2026-06-01', cliente: 'Constructora Caribe S.A.',   total: '$4.200.000',  estado: 'Completada' },
      { id: 'V-002', fecha: '2026-06-03', cliente: 'Vidriería del Norte Ltda.',  total: '$1.850.000',  estado: 'Completada' },
      { id: 'V-003', fecha: '2026-06-05', cliente: 'Inmobiliaria Palms S.A.S.',  total: '$7.630.000',  estado: 'Pendiente'  },
      { id: 'V-004', fecha: '2026-06-08', cliente: 'Arq. Marcos Villalba',       total: '$980.000',    estado: 'Completada' },
      { id: 'V-005', fecha: '2026-06-10', cliente: 'Edificios Platinum S.A.',    total: '$12.400.000', estado: 'Pendiente'  },
    ],
    compras: [
      { id: 'C-001', fecha: '2026-06-02', proveedor: 'Aluminios del Caribe S.A.',  productos: 'Perfil 10 × 5 cm, Perfil 8 × 3 cm', total: '$3.100.000',  estado: 'Completada' },
      { id: 'C-002', fecha: '2026-06-04', proveedor: 'Vidrex Colombia Ltda.',       productos: 'Vidrio templado 6 mm',               total: '$2.460.000',  estado: 'Completada' },
      { id: 'C-003', fecha: '2026-06-07', proveedor: 'Herrajes & Accesorios SAS',   productos: 'Bisagras, manijas, silicón',         total: '$890.000',    estado: 'Pendiente'  },
    ],
    produccion: [
      { semana: 'Semana 22 (26 may – 1 jun)', meta: 120, producido: 118, pct: 98  },
      { semana: 'Semana 23 (2 – 8 jun)',       meta: 120, producido: 103, pct: 86  },
      { semana: 'Semana 24 (9 – 15 jun)',      meta: 130, producido: 97,  pct: 75  },
    ],
    proveedores: [
      { id: 1, nombre: 'Aluminios del Caribe S.A.',  contacto: 'Jorge Torres',    telefono: '3016789234', email: 'jtorres@alucaribe.co',  estado: 'Activo' },
      { id: 2, nombre: 'Vidrex Colombia Ltda.',       contacto: 'Sandra Cano',     telefono: '3124567890', email: 'scano@vidrex.co',       estado: 'Activo' },
      { id: 3, nombre: 'Herrajes & Accesorios SAS',   contacto: 'Mario Rincón',    telefono: '3209876543', email: 'mricon@herrajes.co',    estado: 'Activo' },
      { id: 4, nombre: 'Termovid S.A.',               contacto: 'Patricia Leal',   telefono: '3153456789', email: 'pleal@termovid.co',     estado: 'Inactivo'},
    ],
    kpis: {
      ventas_mes: '$26.060.000', ventas_delta: '+12%',
      pedidos_pendientes: 3,
      meta_pct: 75,
      usuarios_activos: 4,
    }
  }
};

// ---- Toast notifications ----
function toast(msg, type = 'info') {
  const icons = {
    success: `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="20 6 9 17 4 12"/></svg>`,
    error:   `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><line x1="15" y1="9" x2="9" y2="15"/><line x1="9" y1="9" x2="15" y2="15"/></svg>`,
    warning: `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"/><line x1="12" y1="9" x2="12" y2="13"/><line x1="12" y1="17" x2="12.01" y2="17"/></svg>`,
    info:    `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><line x1="12" y1="16" x2="12" y2="12"/><line x1="12" y1="8" x2="12.01" y2="8"/></svg>`,
  };
  const container = document.getElementById('toast-container');
  const el = document.createElement('div');
  el.className = `toast ${type}`;
  el.innerHTML = icons[type] + `<span>${msg}</span>`;
  container.appendChild(el);
  setTimeout(() => { el.style.opacity = '0'; el.style.transform = 'translateX(24px)'; el.style.transition = 'all .3s'; setTimeout(() => el.remove(), 320); }, 3200);
}

// ---- Modal helpers ----
function openModal(id) {
  const m = document.getElementById(id);
  if (m) { m.classList.add('open'); m.querySelector('.modal')?.focus(); }
}

function closeModal(id) {
  const m = document.getElementById(id);
  if (m) m.classList.remove('open');
}

// close on backdrop click
document.addEventListener('click', e => {
  if (e.target.classList.contains('modal-overlay')) {
    e.target.classList.remove('open');
  }
});

// ---- Navegación entre páginas ----
function navigate(page) {
  App.currentPage = page;

  // Marca el nav-item activo
  document.querySelectorAll('.nav-item').forEach(el => {
    el.classList.toggle('active', el.dataset.page === page);
  });

  // Oculta todas las secciones
  document.querySelectorAll('.page-section').forEach(s => {
    s.style.display = 'none';
  });

  // Muestra la sección target
  const target = document.getElementById('page-' + page);
  if (target) target.style.display = 'block';

  // Actualiza el topbar
  const titles = {
    dashboard:   'Dashboard',
    usuarios:    'Gestión de Usuarios',
    ventas:      'Reportes de Ventas',
    produccion:  'Producción',
    compras:     'Compras',
    proveedores: 'Proveedores',
  };
  const breadcrumbs = {
    dashboard:   '',
    usuarios:    'Administración &rsaquo; <span>Usuarios</span>',
    ventas:      'Administración &rsaquo; <span>Ventas</span>',
    produccion:  'Administración &rsaquo; <span>Producción</span>',
    compras:     'Administración &rsaquo; <span>Compras</span>',
    proveedores: 'Administración &rsaquo; <span>Proveedores</span>',
  };
  document.getElementById('topbar-title').textContent    = titles[page] || page;
  document.getElementById('topbar-breadcrumb').innerHTML = breadcrumbs[page] || '';

  // Cierra sidebar en móvil
  document.querySelector('.sidebar')?.classList.remove('open');
}

// ---- Búsqueda en tablas ----
function filterTable(inputId, tableId) {
  const q = document.getElementById(inputId).value.toLowerCase();
  document.querySelectorAll(`#${tableId} tbody tr`).forEach(row => {
    row.style.display = row.textContent.toLowerCase().includes(q) ? '' : 'none';
  });
}

// ---- Formatear porcentaje en barra de progreso ----
function setPct(id, pct) {
  const el = document.getElementById(id);
  if (!el) return;
  el.style.width = pct + '%';
  el.style.background = pct >= 90 ? 'var(--teal)' : pct >= 70 ? 'var(--amber)' : 'var(--red)';
}

// ---- Validación de formularios ----
function validateForm(formId, rules) {
  let valid = true;
  rules.forEach(({ field, label, required, pattern }) => {
    const input = document.getElementById(field);
    const error = document.getElementById(field + '-error');
    if (!input) return;
    input.classList.remove('error');
    if (error) error.textContent = '';

    if (required && !input.value.trim()) {
      if (error) error.textContent = `${label} es requerido.`;
      input.classList.add('error');
      valid = false;
    } else if (pattern && input.value && !pattern.test(input.value)) {
      if (error) error.textContent = `${label} no tiene un formato válido.`;
      input.classList.add('error');
      valid = false;
    }
  });
  return valid;
}

// ---- Init ----
document.addEventListener('DOMContentLoaded', () => {
  // Sidebar toggle móvil
  document.getElementById('mobile-toggle')?.addEventListener('click', () => {
    document.querySelector('.sidebar').classList.toggle('open');
  });

  // Inicializar nav
  document.querySelectorAll('.nav-item[data-page]').forEach(el => {
    el.addEventListener('click', () => navigate(el.dataset.page));
  });

  // Usuario sidebar
  const avatarEl = document.getElementById('sidebar-avatar');
  const nameEl   = document.getElementById('sidebar-name');
  const roleEl   = document.getElementById('sidebar-role');
  if (avatarEl) avatarEl.textContent = App.user.initials;
  if (nameEl)   nameEl.textContent   = App.user.name;
  if (roleEl)   roleEl.textContent   = App.user.role;

  // Dibujar barras de producción
  App.data.produccion.forEach((p, i) => {
    setPct(`prod-bar-${i}`, p.pct);
  });

  // KPI: porcentaje de meta
  setPct('meta-pct-bar', App.data.kpis.meta_pct);

  // Página inicial
  navigate('dashboard');
});
