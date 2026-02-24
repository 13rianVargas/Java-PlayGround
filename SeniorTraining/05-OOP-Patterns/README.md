# 🏗️ Módulo 05 — OOP Patterns

> **"Un Senior no solo escribe código que funciona. Escribe código que se puede mantener durante 5 años."**

En el sector financiero, donde cambios regulatorios modifican la lógica de negocio cada trimestre, los patrones OOP son la diferencia entre refactorizar un método vs reescribir un sistema.

---

## 🎯 Lo que aprenderás

- **SOLID Principles**: Los 5 principios que todo Senior debe aplicar sin pensarlo
- **Design Patterns clave**: Strategy, Builder, Factory — los 3 más evaluados
- **Inmutabilidad**: Records, clases inmutables, por qué importa en finanzas (thread-safety + auditoría)

---

## 📁 Estructura

```
05-OOP-Patterns/
├── README.md
├── 01-SOLID-Principles/
│   └── src/ Main.java · Exercises.java · Solutions.java
├── 02-Design-Patterns/
│   └── src/ Main.java · Exercises.java · Solutions.java
└── 03-Immutability/
    └── src/ Main.java · Exercises.java · Solutions.java
```

---

## 🔑 Conceptos Clave

### ¿Por qué OOP en una prueba técnica?

Las pruebas Senior reales no solo evalúan si tu código **funciona**. Evalúan:

1. **Organización** — ¿Está bien separado? ¿Cada clase tiene una responsabilidad clara?
2. **Extensibilidad** — ¿Se puede agregar un nuevo tipo de cuenta sin romper lo existente?
3. **Inmutabilidad** — ¿Los objetos de dominio (Account, Transaction) son seguros para multihilo?
4. **Legibilidad** — ¿Se entiende la intención del código sin comentarios excesivos?

### Senior vs Mid en OOP

| Mid hace                            | Senior hace                                         |
| ----------------------------------- | --------------------------------------------------- |
| Una clase God con 500 líneas        | Clases pequeñas con SRP (Single Responsibility)     |
| `if/else` para cada tipo de cálculo | Strategy pattern para lógica intercambiable         |
| Constructor con 8 parámetros        | Builder pattern para crear objetos complejos        |
| `new ConcreteClass()` hardcodeado   | Factory para desacoplar la creación                 |
| Clases mutables con setters         | Records / clases inmutables (especialmente en DTOs) |
| Herencia profunda (3+ niveles)      | Composición + interfaces (flat hierarchy)           |

---

## 📋 Orden de Estudio

1. **01-SOLID-Principles** — Empieza aquí. Es la base de todo lo demás.
2. **02-Design-Patterns** — Strategy, Builder, Factory aplicados a banca.
3. **03-Immutability** — Cierra con el concepto más importante en finanzas: datos inmutables.

---

## ⏱️ Tiempo estimado: 2–3 horas
