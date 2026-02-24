# 🏆 Módulo 08 — Mock Exams

> **"La única forma de prepararse para una prueba es... hacer pruebas."**

Tres exámenes cronometrados de 90 minutos que replican el formato de CodinGame y HackerRank. Cada examen tiene 3 problemas de dificultad progresiva.

---

## 📋 Instrucciones Generales

### Antes de empezar

1. Lee [TIME_STRATEGY.md](../TIME_STRATEGY.md) — 10 minutos
2. Ten abierto [CHEATSHEET.md](../CHEATSHEET.md) — solo consultarlo, no memorizarlo
3. Cronómetro en 90:00
4. Sin mirar soluciones ni módulos anteriores

### Durante el examen

- Sigue exactamente el framework de 90 minutos del TIME_STRATEGY
- Implementa el método, no cambies la firma
- Primero haz que funcione, luego optimiza

### Después del examen

- Ejecuta los tests: `mvn test`
- Para los que fallen: mira la solución y entiende el error
- Anota los patterns que fallaste para repasar

---

## 📁 Estructura

```
08-Mock-Exams/
├── README.md
├── Exam-01/              ← Binario + Streams + Análisis de Datos
│   ├── ENUNCIADOS.md
│   ├── pom.xml
│   └── src/
│       ├── main/java/    ejercicios a implementar
│       └── test/java/    tests pre-escritos (no modificar)
├── Exam-02/              ← Collections + Sliding Window + Validación
│   ├── ENUNCIADOS.md
│   ├── pom.xml
│   └── src/...
└── Exam-03/              ← Sort + Recursión + Sistema completo
    ├── ENUNCIADOS.md
    ├── pom.xml
    └── src/...
```

---

## 🧮 Distribución de Puntos por Examen

| Problema   | Dificultad | Tiempo sugerido | Puntos      |
| ---------- | ---------- | --------------- | ----------- |
| Problema 1 | Fácil      | 25 min          | 30 pts      |
| Problema 2 | Medio      | 35 min          | 40 pts      |
| Problema 3 | Difícil    | 30 min          | 30 pts      |
| **Total**  |            | **90 min**      | **100 pts** |

**Puntaje recomendado para pasar:**

- ≥ 70 pts: Listo para la prueba real (semana 1)
- 50–69 pts: Repasa los módulos débiles, intenta de nuevo
- < 50 pts: Repasa desde el Módulo 01 antes del siguiente examen

---

## 📅 Orden Recomendado

1. **Exam-01** → Lo más parecido a las pruebas que ya viste
2. Repasa errores → (1 día de estudio)
3. **Exam-02** → Patrones diferentes
4. Repasa errores → (1 día)
5. **Exam-03** → El más difícil, nivel Senior real
