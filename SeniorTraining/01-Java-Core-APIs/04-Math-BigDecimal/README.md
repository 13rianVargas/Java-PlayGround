# 💰 04 — Math & BigDecimal

> **"En finanzas, `0.1 + 0.2 != 0.3` con `double`. BigDecimal existe por esto."**

---

## 🎯 Objetivo

Manejo preciso de operaciones numéricas para el sector financiero:

- **BigDecimal** — creación correcta, operaciones, comparación
- **RoundingMode** — HALF_UP (bancario), HALF_EVEN (estadístico)
- **Errores clásicos** — `new BigDecimal(0.1)` vs `new BigDecimal("0.1")`
- **Math utilities** — Math.abs, Math.max, Math.min, Integer overflow

---

## 📁 Archivos

| Archivo              | Descripción                                                  |
| -------------------- | ------------------------------------------------------------ |
| `src/Main.java`      | Demostración del error de `double` y solución con BigDecimal |
| `src/Exercises.java` | Ejercicios financieros: interés compuesto, descuentos, IVA   |
| `src/Solutions.java` | Soluciones con mejores prácticas del sector financiero       |

---

## 🔑 Reglas de Oro

```java
// ✅ CORRECTO — siempre String constructor
BigDecimal price = new BigDecimal("19.99");

// ❌ INCORRECTO — imprecisión de punto flotante
BigDecimal price = new BigDecimal(19.99); // = 19.9899999999...

// ✅ Comparar con compareTo (equals considera escala)
price.compareTo(BigDecimal.ZERO) > 0  // true si positivo

// ✅ División con escala y redondeo
total.divide(count, 2, RoundingMode.HALF_UP);
```

---

## 🛠️ Cómo ejecutar

```bash
javac src/*.java -d bin/
java -cp bin/ Main
```
