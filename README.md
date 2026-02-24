# 🎮 Java PlayGround

Espacio personal de experimentación y aprendizaje continuo en el ecosistema de Java. Este repositorio centraliza diversos proyectos, ejercicios y pruebas de concepto enfocadas en el dominio del lenguaje, el framework Spring y la persistencia de datos.

## 🎯 Propósito del Repositorio

Este playground sirve como bitácora técnica del proceso de aprendizaje práctico, abarcando desde los fundamentos del lenguaje (**Java SE**) hasta el desarrollo de aplicaciones empresariales robustas con **Spring Boot** y **JPA/Hibernate**.

## 📁 Estructura del Proyecto

El contenido está categorizado por fuentes de aprendizaje y especialización tecnológica:

### 🎓 Platzi (Ruta Backend Java)

Proyectos enfocados en el desarrollo profesional con Spring:

- **[Java Spring](Platzi/Java%20Spring/)**:
  - **[Java Spring Framework](Platzi/Java%20Spring/Java%20Spring%20Framework/)**: Conjunto de implementaciones modulares que exploran:
    - Gestión de horarios (`control_horario`).
    - Persistencia con Hibernate (`hybernate_jpa`).
    - Manejo de tipos de excepciones (`spring-excepciones`).
    - Interceptores y lógica transaccional.
  - **[platzi-market](Platzi/Java%20Spring/platzi-market/)**: API REST completa para gestión de inventarios utilizando Clean Architecture y MapStruct.
- **[Spring Data JPA](Platzi/Spring%20Data%20JPA/)**:
  - **[platzi.pizzeria](Platzi/Spring%20Data%20JPA/platzi.pizzeria/)**: Proyecto dedicado a relaciones complejas, consultas avanzadas (Query Methods) y optimización de base de datos.

### 📺 TodoCode (Java SE Roadmap)

Guía exhaustiva de fundamentos y niveles avanzados del lenguaje:

- **[Java SE](TodoCodeYT/Java%20SE/)**: Ruta de aprendizaje dividida en 22 módulos:
  - **Fundamentos**: Sintaxis básica, ciclo de vida del programa y control de flujo.
  - **OOP**: Herencia, polimorfismo, abstracción y encapsulamiento.
  - **Manejo de Datos**: Colecciones (List, Set, Map), Genéricos e I/O Operations.
  - **Avanzado**: Lambda Expressions, Stream API, Optionals, Concurrencia y Networking.
  - **Ecosistema**: Build Tools (Maven/Gradle), Testing (JUnit) y Web Frameworks.

### 🏆 SeniorTraining (Preparación para Pruebas Técnicas Senior)

Tutorial completo para superar pruebas técnicas de nivel Senior en Java (CodinGame, HackerRank, entrevistas financieras).

- **[SeniorTraining/](SeniorTraining/)** — 8 módulos + 3 exámenes simulacro con cronómetro:

| Módulo | Tema                   | Contenido                                                                  |
| ------ | ---------------------- | -------------------------------------------------------------------------- |
| 01     | Java Core APIs         | Arrays.binarySearch, Collections, BigDecimal, Strings                      |
| 02     | Algoritmos             | Binary Search, Two Pointers, Sliding Window                                |
| 03     | Programación Funcional | Lambdas, Stream API, Optional                                              |
| 04     | Análisis de Datos      | RadiationAnalyzer (22 tests), Temperature, TransactionValidator (20 tests) |
| 07     | Java 17 Features       | Records, Sealed Classes, Switch Expressions, Text Blocks                   |
| 08     | Exámenes Simulacro     | 3 exámenes × 90 min con ~40 tests en total                                 |

**Archivos de apoyo**: `CHEATSHEET.md`, `TIME_STRATEGY.md`, `EDGE_CASES_CHECKLIST.md`

**Ejecutar tests de cualquier módulo Maven**:

```bash
cd SeniorTraining/04-Data-Analysis && mvn test
cd SeniorTraining/08-Mock-Exams/Exam-01 && mvn test
```

## 🚀 Tecnologías Destacadas

- **Java 17/21**: Records, Sealed Classes y Text Blocks.
- **Spring Boot 3.x**: Desarrollo de microservicios y APIs RESTful.
- **Spring Data JPA / Hibernate**: Gestión de persistencia y modelado ORM.
- **Maven & Gradle**: Automatización de construcción y dependencias.

---

_Este repositorio es una muestra dinámica del crecimiento técnico y la experimentación dentro del ecosistema Java._
