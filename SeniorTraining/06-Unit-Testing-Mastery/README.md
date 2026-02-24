# 🧪 Módulo 06 — Unit Testing Mastery

> **"Un Senior no entrega código sin tests. Un Senior ESCRIBE los tests primero."**

En el sector financiero, un bug en producción puede costar millones. Los tests no son opcionales — son tu seguro contra desastres. Las pruebas técnicas Senior frecuentemente evalúan si sabes escribir tests correctos, no solo código que funciona.

---

## 🎯 Lo que aprenderás

- **JUnit 5 Fundamentals**: `@Test`, Assertions avanzadas, Lifecycle hooks
- **Parameterized Tests**: `@ParameterizedTest`, `@CsvSource`, `@MethodSource`, `@ValueSource`
- **TDD Practice**: Test-Driven Development aplicado a problemas financieros reales

---

## 📁 Estructura

```
06-Unit-Testing-Mastery/
├── README.md
├── pom.xml                    ← Parent POM (Maven multi-módulo)
├── 01-JUnit5-Fundamentals/
│   ├── pom.xml
│   └── src/
│       ├── main/java/         ← Código a testear
│       └── test/java/         ← Tests
├── 02-Parameterized-Tests/
│   ├── pom.xml
│   └── src/
│       ├── main/java/
│       └── test/java/
└── 03-TDD-Practice/
    ├── pom.xml
    └── src/
        ├── main/java/         ← Implementa aquí (TODO stubs)
        └── test/java/         ← Tests ya escritos (TDD)
```

---

## 🔑 Conceptos Clave

### ¿Por qué Testing aparece en pruebas técnicas Senior?

Las plataformas como CodinGame y HackerRank ya incluyen tests predefinidos. Pero en pruebas propietarias del sector financiero, **te piden que tú escribas los tests**.

Lo que evalúan:

1. **¿Cubres edge cases?** — null, vacío, negativo, overflow, duplicados
2. **¿Usas assertions adecuadas?** — `assertAll()`, `assertThrows()`, no solo `assertEquals()`
3. **¿Tests parametrizados?** — Un Senior no escribe 10 tests idénticos con datos diferentes
4. **¿Estructura AAA?** — Arrange, Act, Assert en cada test

### Senior vs Mid en Testing

| Mid hace                                 | Senior hace                                      |
| ---------------------------------------- | ------------------------------------------------ |
| Un test que verifica el happy path       | Tests para happy path + edge cases + errores     |
| `assertEquals(expected, actual)`         | `assertAll()` agrupando múltiples verificaciones |
| 10 tests copy-paste para datos distintos | `@ParameterizedTest` con `@CsvSource`            |
| Tests después del código                 | TDD: tests primero, luego implementación         |
| Ignora excepciones esperadas             | `assertThrows()` con verificación del mensaje    |

---

## 🛠️ Cómo ejecutar

```bash
cd 06-Unit-Testing-Mastery/
mvn test                                      # Todos los tests
mvn test -pl 01-JUnit5-Fundamentals           # Solo un sub-módulo
mvn test -pl 03-TDD-Practice                  # Solo TDD (empezará en rojo)
mvn test -Dtest=AccountServiceTest            # Solo una clase de test
```

---

## 📋 Orden de Estudio

1. **01-JUnit5-Fundamentals** — Sintaxis esencial de JUnit 5 (assertions, lifecycle).
2. **02-Parameterized-Tests** — Tests parametrizados para eficiencia.
3. **03-TDD-Practice** — Tests ya escritos, TÚ implementas el código para que pasen.

---

## ⏱️ Tiempo estimado: 2–3 horas
