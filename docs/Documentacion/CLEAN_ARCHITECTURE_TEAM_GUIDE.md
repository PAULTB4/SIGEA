# 🏗️ Guía de Clean Architecture para el Equipo SIGEA

## 🎯 **Objetivo de Este Documento**

Aclarar **dónde van los Use Cases** en nuestra arquitectura y **por qué** es importante seguir esta estructura.

---

## ❌ **PROBLEMA IDENTIFICADO**

Actualmente tenemos **Use Cases duplicados** en dos ubicaciones diferentes:

```
📁 src/main/java/com/zentry/sigea/
├── 📁 module_actividad/
│   ├── 📁 core/
│   │   └── 📁 usecases/           ← ✅ UBICACIÓN CORRECTA
│   └── 📁 services/
│       └── 📁 usecases/           ← ❌ UBICACIÓN INCORRECTA
└── 📁 infrastructure/
```

**Esta duplicación está causando:**

- ❌ Confusión sobre dónde crear nuevos Use Cases
- ❌ Inconsistencia en el código del equipo
- ❌ Violación de los principios de Clean Architecture

---

## ✅ **SOLUCIÓN: Estructura Correcta**

### 🗂️ **Ubicación de Use Cases según Clean Architecture**

```
📁 src/main/java/com/zentry/sigea/
└── 📁 module_actividad/           # Módulo de Actividades
    ├── 📁 presentation/           # 🎨 Capa de Presentación
    │   ├── 📁 api/               # Controladores REST
    │   └── 📁 models/            # DTOs de Request/Response
    │
    ├── 📁 core/                  # 🧠 CAPA PRINCIPAL (Dominio + Aplicación)
    │   ├── 📁 entities/          # Entidades de dominio
    │   ├── 📁 usecases/          # ✅ USE CASES VAN AQUÍ
    │   │   ├── 📁 actividad/     # Use cases de actividades
    │   │   ├── 📁 tipo_actividad/ # Use cases de tipos de actividad
    │   │   └── 📁 estado_actividad/ # Use cases de estados
    │   ├── 📁 repositories/      # Interfaces de repositorios
    │   └── 📁 value_objects/     # Objetos de valor
    │
    ├── 📁 services/              # 🔄 Capa de Coordinación (OPCIONAL)
    │   └── 📁 interfaces/        # Solo interfaces, NO use cases
    │
    └── 📁 infrastructure/        # 🔧 Capa de Infraestructura
        ├── 📁 database/
        └── 📁 repository/
```

---

## 📝 **Ejemplos Prácticos con Nuestro Código**

### ✅ **CORRECTO: Use Case en core/usecases/**

```java
// 📁 core/usecases/actividad/CrearActividadUseCase.java
package com.zentry.sigea.module_actividad.core.usecases.actividad;

@Service
public class CrearActividadUseCase {

    private final ActividadRepository actividadRepository;

    public CrearActividadUseCase(ActividadRepository actividadRepository) {
        this.actividadRepository = actividadRepository;
    }

    @Transactional
    public ActividadResponse ejecutar(ActividadRequest request) {
        // 1. Validaciones de negocio
        validateBusinessRules(request);

        // 2. Crear entidad de dominio
        Actividad actividad = Actividad.create(
            request.getTitulo(),
            request.getDescripcion(),
            request.getFechaInicio(),
            request.getFechaFin()
        );

        // 3. Persistir usando repositorio
        Actividad saved = actividadRepository.save(actividad);

        // 4. Retornar respuesta
        return ActividadResponse.from(saved);
    }

    private void validateBusinessRules(ActividadRequest request) {
        // Lógica de validación específica del negocio
    }
}
```

### ❌ **INCORRECTO: Use Case en services/usecases/**

```java
// ❌ NO HACER ESTO - services/usecases/ActividadUseCase.java
// Esta ubicación viola Clean Architecture
```

---

## 🔄 **Flujo de Datos Correcto**

```
🌐 Frontend Request
    ↓
📱 Controller (presentation/api/)
    ↓
🧠 Use Case (core/usecases/)     ← Aquí está la lógica de aplicación
    ↓
📊 Repository Interface (core/repositories/)
    ↓
🔧 Repository Implementation (infrastructure/repository/)
    ↓
🗄️ Database
```

---

## 🎯 **Acciones para el Equipo**

### 🚀 **Tareas Inmediatas**

1. **✅ MIGRAR Use Cases existentes:**

   ```
   DE: services/usecases/
   A:  core/usecases/[modulo]/
   ```

2. **✅ ACTUALIZAR imports en controladores:**

   ```java
   // Cambiar esto:
   import com.zentry.sigea.module_actividad.services.usecases.ActividadUseCase;

   // Por esto:
   import com.zentry.sigea.module_actividad.core.usecases.actividad.CrearActividadUseCase;
   ```

3. **✅ ELIMINAR carpeta services/usecases/**
   - Una vez migrados todos los Use Cases

### 📋 **Reglas para Nuevos Use Cases**

| ✅ HACER                                    | ❌ NO HACER                           |
| ------------------------------------------- | ------------------------------------- |
| Crear en `core/usecases/[modulo]/`          | Crear en `services/usecases/`         |
| Un Use Case por archivo                     | Múltiples Use Cases en un archivo     |
| Nombre descriptivo: `CrearActividadUseCase` | Nombres genéricos: `ActividadService` |
| Inyectar solo interfaces del core           | Inyectar clases de infrastructure     |

---

## 🧪 **Convenciones de Naming**

### 📁 **Estructura de Carpetas**

```
core/usecases/
├── actividad/
│   ├── CrearActividadUseCase.java
│   ├── ActualizarActividadUseCase.java
│   ├── EliminarActividadUseCase.java
│   └── BuscarActividadUseCase.java
├── tipo_actividad/
│   ├── CrearTipoActividadUseCase.java
│   └── ListarTiposActividadUseCase.java
└── estado_actividad/
    └── CambiarEstadoActividadUseCase.java
```

### 🏷️ **Nombres de Clases**

- ✅ `CrearActividadUseCase`
- ✅ `BuscarParticipantesPorActividadUseCase`
- ❌ `ActividadService`
- ❌ `ActividadUseCase` (muy genérico)

---

## 🤝 **Coordinación del Equipo**

### 👥 **Responsabilidades**

| Rol               | Responsabilidad                                  |
| ----------------- | ------------------------------------------------ |
| **Desarrollador** | Crear Use Cases en `core/usecases/[modulo]/`     |
| **Code Reviewer** | Verificar ubicación correcta antes de aprobar PR |
| **Team Lead**     | Supervisar migración y aplicación de estándares  |

### 🔄 **Workflow de Desarrollo**

1. **Antes de crear un Use Case:**

   - ✅ Verificar que no existe en `services/usecases/`
   - ✅ Crear en `core/usecases/[modulo]/`

2. **En Code Review:**
   - ✅ Verificar ubicación correcta
   - ✅ Verificar naming conventions
   - ✅ Verificar que no hay duplicados

---

## 🎓 **Por Qué Es Importante**

### 🏗️ **Clean Architecture Benefits**

1. **📦 Separación de Responsabilidades:**

   - Core = Lógica de negocio
   - Infrastructure = Detalles técnicos

2. **🧪 Testabilidad:**

   - Use Cases fáciles de probar unitariamente
   - Sin dependencias de frameworks

3. **🔄 Mantenibilidad:**

   - Código organizado y predecible
   - Fácil localización de funcionalidades

4. **👥 Trabajo en Equipo:**
   - Estándares claros para todos
   - Menos conflictos en el código

---

## 📞 **¿Dudas?**

Si tienes preguntas sobre dónde ubicar un Use Case o cómo implementarlo:

1. 📋 Consulta este documento
2. 💬 Pregunta en el canal del equipo
3. 🔍 Revisa ejemplos en `core/usecases/actividad/`

---

**🎯 Recuerda:** Los Use Cases son el **corazón de la aplicación** y deben estar en el **core**, no en services.
