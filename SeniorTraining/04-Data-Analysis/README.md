# Módulo 04 — Data Analysis con Streams

## Objetivo

Aplicar **Streams + Collectors + BigDecimal** a datasets reales.  
Cada sub-proyecto es un mini-laboratorio Maven con tests JUnit 5 ya escritos.

## Estructura

```
04-Data-Analysis/
├── pom.xml                          ← Parent POM (Maven multi-module)
├── README.md
├── 01-Threshold-Detection/
│   ├── pom.xml
│   └── src/
│       ├── main/java/
│       │   ├── RadiationAnalyzer.java          ← TODO: Implementa
│       │   └── RadiationAnalyzerSolution.java  ← Solución de referencia
│       └── test/java/
│           └── RadiationAnalyzerTest.java      ← Tests pre-escritos
├── 02-Temperature-Analysis/
│   ├── pom.xml
│   └── src/
│       ├── main/java/
│       │   ├── TemperatureAnalyzer.java          ← TODO: Implementa
│       │   └── TemperatureAnalyzerSolution.java  ← Solución de referencia
│       └── test/java/
│           └── TemperatureAnalyzerTest.java      ← Tests pre-escritos
└── 03-Transaction-Validator/
    ├── pom.xml
    └── src/
        ├── main/java/
        │   ├── TransactionValidator.java          ← TODO: Implementa
        │   └── TransactionValidatorSolution.java  ← Solución de referencia
        └── test/java/
            └── TransactionValidatorTest.java      ← Tests pre-escritos
```

## Cómo usar

```bash
# Compilar y ejecutar todos los tests
cd 04-Data-Analysis
mvn clean test

# Solo un sub-proyecto
mvn test -pl 01-Threshold-Detection
```

## Sub-módulos

| #   | Sub-módulo                | Enfoque                                                  |
| --- | ------------------------- | -------------------------------------------------------- |
| 01  | **Threshold-Detection**   | Streams + filter + reduce sobre datos de radiación       |
| 02  | **Temperature-Analysis**  | Collectors.groupingBy + estadísticas sobre temperaturas  |
| 03  | **Transaction-Validator** | Stream pipelines + BigDecimal para validar transacciones |

## Senior vs Mid

| Tema       | Mid-Level                      | Senior                                 |
| ---------- | ------------------------------ | -------------------------------------- |
| Streams    | `for` loop + acumulador manual | `stream().filter().map().collect()`    |
| Agrupación | `HashMap` manual               | `Collectors.groupingBy()` + downstream |
| Precisión  | `double`                       | `BigDecimal` con `RoundingMode`        |
| Tests      | "Funciona en mi máquina"       | Tests parametrizados con edge cases    |

## Orden de estudio

1. **01-Threshold-Detection** — Filter + reduce básico
2. **02-Temperature-Analysis** — GroupingBy + estadísticas
3. **03-Transaction-Validator** — Pipeline completo con validación

> **Tip:** Cada `*Analyzer.java` tiene métodos con `// TODO`. Los tests ya están escritos.  
> Implementa hasta que `mvn test` pase al 100%.
