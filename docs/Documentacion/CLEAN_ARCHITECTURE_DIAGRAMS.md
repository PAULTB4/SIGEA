# 📊 Diagramas Visuales - Clean Architecture SIGEA

## 🎯 **Diagrama de Capas - Estructura Correcta**

```
┌─────────────────────────────────────────────────────────────┐
│                    🌐 FRONTEND                              │
│                 (React, Angular, etc.)                     │
└─────────────────────────────────────────────────────────────┘
                              │ HTTP Requests
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                📱 PRESENTATION LAYER                       │
│  ┌─────────────────┐  ┌─────────────────────────────────┐   │
│  │ Controllers     │  │ DTOs / Models                   │   │
│  │ @RestController │  │ Request/Response Objects        │   │
│  └─────────────────┘  └─────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                              │ Calls Use Cases
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    🧠 CORE LAYER                           │
│  ┌─────────────────┐  ┌─────────────────────────────────┐   │
│  │ 🎯 USE CASES    │  │ 📊 Entities                    │   │
│  │ CrearActividad  │  │ Actividad                       │   │
│  │ BuscarActividad │  │ TipoActividad                   │   │
│  │ @Service        │  │ EstadoActividad                 │   │
│  └─────────────────┘  └─────────────────────────────────┘   │
│  ┌─────────────────┐  ┌─────────────────────────────────┐   │
│  │ Repositories    │  │ Value Objects                   │   │
│  │ (Interfaces)    │  │ Email, Fecha, etc.              │   │
│  └─────────────────┘  └─────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                              │ Uses Repository Interfaces
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                🔧 INFRASTRUCTURE LAYER                     │
│  ┌─────────────────┐  ┌─────────────────────────────────┐   │
│  │ Repository      │  │ JPA Entities                    │   │
│  │ Implementations │  │ @Entity                         │   │
│  │ @Repository     │  │ ActividadEntity                 │   │
│  └─────────────────┘  └─────────────────────────────────┘   │
│  ┌─────────────────┐  ┌─────────────────────────────────┐   │
│  │ Mappers         │  │ External APIs                   │   │
│  │ Domain ↔ JPA    │  │ Email, SMS, etc.                │   │
│  └─────────────────┘  └─────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    🗄️ DATABASE                             │
│                   (PostgreSQL, MySQL)                      │
└─────────────────────────────────────────────────────────────┘
```

---

## ❌ **Lo que NO debemos hacer**

```
❌ ESTRUCTURA INCORRECTA:

📁 module_actividad/
├── 📁 services/
│   └── 📁 usecases/          ← ❌ Use Cases aquí = INCORRECTO
│       ├── ActividadUseCase.java
│       └── TipoActividadUseCase.java
└── 📁 core/
    ├── 📁 entities/
    └── 📁 repositories/

🚨 PROBLEMAS:
- Viola Clean Architecture
- Mezcla capas de aplicación con servicios
- Confunde al equipo sobre dónde crear código
```

---

## ✅ **Estructura Correcta del Proyecto**

```
✅ ESTRUCTURA CORRECTA:

📁 src/main/java/com/zentry/sigea/
└── 📁 module_actividad/
    ├── 📁 presentation/              # 🎨 Capa de Presentación
    │   ├── 📁 api/
    │   │   ├── ActividadController.java
    │   │   └── TipoActividadController.java
    │   └── 📁 models/
    │       ├── ActividadRequest.java
    │       └── ActividadResponse.java
    │
    ├── 📁 core/                      # 🧠 Capa Central (Dominio + Aplicación)
    │   ├── 📁 entities/
    │   │   ├── Actividad.java
    │   │   └── TipoActividad.java
    │   │
    │   ├── 📁 usecases/              # ✅ USE CASES VAN AQUÍ
    │   │   ├── 📁 actividad/
    │   │   │   ├── CrearActividadUseCase.java
    │   │   │   ├── BuscarActividadUseCase.java
    │   │   │   └── ActualizarActividadUseCase.java
    │   │   └── 📁 tipo_actividad/
    │   │       ├── CrearTipoActividadUseCase.java
    │   │       └── ListarTiposActividadUseCase.java
    │   │
    │   ├── 📁 repositories/          # Interfaces de repositorios
    │   │   ├── ActividadRepository.java
    │   │   └── TipoActividadRepository.java
    │   │
    │   └── 📁 value_objects/         # Objetos de valor
    │       ├── Email.java
    │       └── FechaActividad.java
    │
    ├── 📁 services/                  # 🔄 Coordinación (OPCIONAL)
    │   └── 📁 interfaces/            # Solo interfaces, NO use cases
    │       └── NotificationService.java
    │
    └── 📁 infrastructure/            # 🔧 Capa de Infraestructura
        ├── 📁 database/
        │   ├── 📁 entities/
        │   │   ├── ActividadEntity.java
        │   │   └── TipoActividadEntity.java
        │   └── 📁 mappers/
        │       ├── ActividadMapper.java
        │       └── TipoActividadMapper.java
        └── 📁 repository/
            ├── ActividadRepositoryImpl.java
            └── TipoActividadRepositoryImpl.java
```

---

## 🔄 **Flujo de Ejecución - Crear Actividad**

```
1. 🌐 Frontend → POST /api/actividades
   {
     "titulo": "Capacitación Java",
     "descripcion": "Curso de Spring Boot",
     "fechaInicio": "2024-12-01T09:00:00",
     "fechaFin": "2024-12-01T17:00:00"
   }
                    ↓

2. 📱 ActividadController.crearActividad()
   - Valida formato de request
   - Convierte a ActividadRequest
                    ↓

3. 🧠 CrearActividadUseCase.ejecutar()
   - Valida reglas de negocio
   - Verifica conflictos de fechas
   - Crea entidad Actividad
                    ↓

4. 📊 ActividadRepository.save()
   - Interface del core
   - Implementada en infrastructure
                    ↓

5. 🔧 ActividadRepositoryImpl.save()
   - Usa ActividadMapper
   - Convierte Actividad → ActividadEntity
   - Persiste en BD
                    ↓

6. 🗄️ Database
   - Guarda registro en tabla actividades
                    ↓

7. 🔄 Respuesta
   - Entity → Domain → Response
   - Retorna al frontend
```

---

## 👥 **Asignación de Responsabilidades por Capa**

### 📱 **Presentation Layer**

```java
@RestController
@RequestMapping("/api/actividades")
public class ActividadController {

    private final CrearActividadUseCase crearActividadUseCase;

    @PostMapping
    public ResponseEntity<ActividadResponse> crear(
        @Valid @RequestBody ActividadRequest request) {

        // ✅ SOLO delega al Use Case
        ActividadResponse response = crearActividadUseCase.ejecutar(request);
        return ResponseEntity.ok(response);
    }
}
```

### 🧠 **Core Layer - Use Case**

```java
@Service
public class CrearActividadUseCase {

    private final ActividadRepository repository;

    @Transactional
    public ActividadResponse ejecutar(ActividadRequest request) {
        // ✅ Contiene TODA la lógica de aplicación

        // 1. Validaciones de negocio
        validateBusinessRules(request);

        // 2. Crear entidad de dominio
        Actividad actividad = Actividad.create(
            request.getTitulo(),
            request.getDescripcion(),
            request.getFechaInicio(),
            request.getFechaFin()
        );

        // 3. Verificar conflictos de fechas
        if (repository.existsConflictingDates(
            actividad.getFechaInicio(),
            actividad.getFechaFin())) {
            throw new ConflictoFechasException("Fechas en conflicto");
        }

        // 4. Persistir
        Actividad saved = repository.save(actividad);

        // 5. Retornar respuesta
        return ActividadResponse.from(saved);
    }
}
```

### 🔧 **Infrastructure Layer**

```java
@Repository
public class ActividadRepositoryImpl implements ActividadRepository {

    private final JpaActividadRepository jpaRepository;
    private final ActividadMapper mapper;

    @Override
    public Actividad save(Actividad actividad) {
        // ✅ SOLO maneja persistencia

        // 1. Convertir dominio → JPA
        ActividadEntity entity = mapper.toEntity(actividad);

        // 2. Persistir
        ActividadEntity saved = jpaRepository.save(entity);

        // 3. Convertir JPA → dominio
        return mapper.toDomain(saved);
    }
}
```

---

## 🎯 **Checklist para el Equipo**

### ✅ **Al crear un nuevo Use Case:**

- [ ] ¿Está en `core/usecases/[modulo]/`?
- [ ] ¿Tiene nombre descriptivo? (`CrearActividadUseCase`)
- [ ] ¿Usa `@Service`?
- [ ] ¿Usa `@Transactional` si modifica datos?
- [ ] ¿Solo inyecta interfaces del core?
- [ ] ¿Contiene validaciones de negocio?

### ✅ **Al hacer Code Review:**

- [ ] ¿Use Case está en ubicación correcta?
- [ ] ¿No hay lógica de negocio en el Controller?
- [ ] ¿No hay código de infrastructure en Use Case?
- [ ] ¿Imports apuntan a `core.usecases`?

---

## 🚨 **Errores Comunes a Evitar**

| ❌ Error                               | ✅ Solución                                   |
| -------------------------------------- | --------------------------------------------- |
| Use Case en `services/usecases/`       | Mover a `core/usecases/[modulo]/`             |
| Lógica de negocio en Controller        | Mover toda la lógica al Use Case              |
| Use Case inyecta Entity JPA            | Usar interfaces del core                      |
| Múltiples Use Cases en un archivo      | Un Use Case por archivo                       |
| Nombres genéricos (`ActividadService`) | Nombres específicos (`CrearActividadUseCase`) |

---

**🎯 Recuerda:** La ubicación correcta de los Use Cases es fundamental para mantener una arquitectura limpia y comprensible para todo el equipo.
