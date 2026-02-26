# 🏆 SeniorTraining — Java Senior Interview Prep

Curso práctico para dominar los métodos y patrones exóticos de Java que aparecen en entrevistas técnicas Senior del sector financiero. Formato CodinGame / HackerRank: **lee la teoría → practica los ejercicios → valida con las soluciones**.

---

## 🗺️ Roadmap

| #   | Módulo                                               | Tiempo Estimado     | Prioridad  |
| --- | ---------------------------------------------------- | ------------------- | ---------- |
| 01  | [Java Core APIs](01-Java-Core-APIs/)                 | 3–4 horas           | 🔴 Crítica |
| 02  | [Algorithms](02-Algorithms/)                         | 4–6 horas           | 🔴 Crítica |
| 03  | [Functional Programming](03-Functional-Programming/) | 3–4 horas           | 🔴 Crítica |
| 04  | [Data Analysis](04-Data-Analysis/)                   | 2–3 horas           | 🔴 Crítica |
| 05  | [OOP Patterns](05-OOP-Patterns/)                     | 2–3 horas           | 🟡 Alta    |
| 06  | [Unit Testing Mastery](06-Unit-Testing-Mastery/)     | 2–3 horas           | 🟡 Alta    |
| 07  | [Java 17 Features](07-Java17-Features/)              | 1–2 horas           | 🟢 Media   |
| 08  | [Mock Exams](08-Mock-Exams/)                         | 4.5 horas (3×90min) | 🔴 Crítica |

**Tiempo total estimado:** 22–30 horas de estudio enfocado

---

## 📁 Estructura de cada módulo

```
módulo/
├── src/
│   ├── Main.java        ← Teoría ejecutable con ejemplos anotados
│   ├── Exercises.java   ← Ejercicios con TODO (tú implementas)
│   └── Solutions.java   ← Soluciones anotadas (mira DESPUÉS)
└── README.md            ← Contexto y orden de estudio
```

> Los módulos 04, 06 y 08 usan **Maven + JUnit 5** para tests automatizados.

---

## 🎯 ¿Por dónde empezar?

### Si tienes **1 semana**:

1. → Módulo 01 (día 1)
2. → Módulo 02 (días 2–3)
3. → Módulo 03 (día 4)
4. → Módulo 04 (día 5)
5. → Módulo 08 Exam-01 (día 6, cronometrado)
6. → Repaso de errores + Exam-02 (día 7)

### Si tienes **3 días**:

1. → CHEATSHEET.md completo (30min)
2. → Módulo 01 rápido (2h)
3. → Módulo 02: solo 01-Binary-Search y 02-Two-Pointers (2h)
4. → Módulo 03: solo 01-Lambdas y 02-Streams (2h)
5. → Módulo 04 completo (2h) — este es el más parecido a las pruebas reales
6. → Módulo 08 Exam-01 cronometrado

### Si tienes **1 día** (emergencia):

1. → CHEATSHEET.md (30min)
2. → EDGE_CASES_CHECKLIST.md (10min)
3. → TIME_STRATEGY.md (10min)
4. → Módulo 01: Arrays Utility + BigDecimal (1h)
5. → Módulo 04 completo (2h)
6. → Módulo 08 Exam-01 (90min)

---

## 🛠️ Setup del Entorno

### Requisitos

- **Java 17** (LTS estándar en el sector financiero)
- **Maven 3.8+** (para módulos con JUnit 5)
- IntelliJ IDEA o VS Code con Java Extension Pack

### Verificar instalación

```bash
java -version    # debe mostrar openjdk 17.x.x o similar
mvn -version     # debe mostrar Apache Maven 3.x.x
```

### Ejecutar módulos simples (sin Maven)

```bash
cd 01-Java-Core-APIs/01-Arrays-Utility/
javac src/*.java -d bin/
java -cp bin/ Main
```

### Ejecutar módulos con Maven

```bash
cd 04-Data-Analysis/
mvn test                              # corre todos los tests
mvn test -pl 01-Threshold-Detection   # solo un sub-módulo
mvn test -Dtest=ThresholdTest         # solo una clase de test
```

---

## 📋 Archivos de Referencia Rápida

| Archivo                                            | Descripción                                          |
| -------------------------------------------------- | ---------------------------------------------------- |
| [CHEATSHEET.md](CHEATSHEET.md)                     | APIs Java más usadas en pruebas, con sintaxis exacta |
| [EDGE_CASES_CHECKLIST.md](EDGE_CASES_CHECKLIST.md) | Lista de edge cases que SIEMPRE debes verificar      |
| [TIME_STRATEGY.md](TIME_STRATEGY.md)               | Estrategia de 90 minutos para no quedarte sin tiempo |

---

## 🏦 ¿Por qué Java 17 para Finanzas?

- LTS hasta **2029** — estándar actual en bancos, aseguradoras y fintechs
- **Records** para DTOs inmutables (thread-safe sin boilerplate)
- **Sealed Classes** para modelar jerarquías de productos financieros
- **BigDecimal** obligatorio para cálculos monetarios (nunca `double`)
- Spring Boot 3.x y Quarkus requieren Java 17 mínimo

---

## 🔑 Conceptos que distinguen a un Senior de un Mid

| Mid sabe                  | Senior también sabe                                  |
| ------------------------- | ---------------------------------------------------- |
| `for` loop sobre arrays   | `Arrays.binarySearch()`, complejidad O(log n)        |
| `ArrayList`, `HashMap`    | `TreeMap`, `PriorityQueue`, `Deque`, `LinkedHashMap` |
| `if (obj != null)`        | `Optional<T>`, `orElseThrow()`                       |
| `for` loops de filtrado   | `Stream.filter().map().collect()`                    |
| `new BigDecimal(3.14)` ⚠️ | `new BigDecimal("3.14")` ✅                          |
| Tests básicos con `@Test` | `@ParameterizedTest`, `assertAll()`, edge cases      |
| Herencia simple           | SOLID, Strategy, Builder patterns                    |
| Implementar funcionalidad | Implementar + anticipar edge cases + escribir tests  |

---

_Java 17 · Maven · JUnit 5 · Formato: CodinGame / HackerRank_
