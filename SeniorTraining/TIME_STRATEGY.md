# ⏱️ TIME STRATEGY — Prueba Senior de 90 Minutos

> La diferencia entre un Senior y un Mid no es solo el código. Es saber **administrar el tiempo bajo presión**.

---

## 🗓️ El Framework de 90 Minutos

### Para pruebas con **3 problemas** (formato CodinGame/HackerRank típico)

```
00:00 - 00:05  │ LECTURA INICIAL (5 min)
               │ Lee TODOS los problemas de corrido.
               │ Clasifica: fácil / medio / difícil.
               │ Empieza por el fácil.

00:05 - 00:30  │ PROBLEMA 1 - Fácil (25 min)
               │ 5 min: entender + diseñar en papel/mente
               │ 15 min: implementar
               │ 5 min: edge cases + submit

00:30 - 01:05  │ PROBLEMA 2 - Medio (35 min)
               │ 5 min: entender + identificar patrón (búsqueda, sliding window, etc.)
               │ 25 min: implementar
               │ 5 min: edge cases + submit

01:05 - 01:25  │ PROBLEMA 3 - Difícil (20 min)
               │ 5 min: entender + diseñar
               │ 12 min: implementar solución funcional (aunque no óptima)
               │ 3 min: submit aunque esté incompleto — puntos parciales

01:25 - 01:30  │ REVISIÓN FINAL (5 min)
               │ Verifica los edge cases del CHEATSHEET
               │ Revisa problemas 1 y 2 si algo está rojo
```

---

## 🚨 Señales de Alerta y Qué Hacer

### "Llevo 20 minutos en un problema y no arranco"

→ Para. Escribe una solución bruta aunque sea O(n²). Algo que funcione en los casos básicos.  
→ Luego optimiza si tienes tiempo.  
**Nunca te quedes en blanco — código parcial da puntos.**

### "No entiendo el enunciado"

→ Lee el ejemplo concreto y trabaja desde lo concreto hacia lo general.  
→ Input/output del ejemplo es más valioso que el texto del enunciado.

### "Mi solución pasa los casos básicos pero falla los tests"

→ Revela los test cases fallidos (si la plataforma lo permite).  
→ Aplica la EDGE_CASES_CHECKLIST: empty input, negatives, single element, exact boundary.

### "Me quedé sin tiempo en el problema 3"

→ Si tienes 5 minutos: escribe el esqueleto con comentarios que describan tu algoritmo.  
→ En CodinGame: incluso `return null` te da 0 pts, pero si tu método compila, sin runtime errors en algunos test cases → puntos parciales.

---

## 🧠 Patrón de Identificación de Algoritmos

Durante los **5 minutos de lectura inicial**, identifica el patrón:

| Señales en el enunciado                           | Algoritmo a usar                      |
| ------------------------------------------------- | ------------------------------------- |
| "array ordenado", "eficiente", hasta 1M elementos | Binary Search / `Arrays.binarySearch` |
| "suma de subarreglo", "ventana de tamaño K"       | Sliding Window                        |
| "par de elementos", "dos índices"                 | Two Pointers                          |
| "todas las combinaciones/permutaciones"           | Backtracking / Recursión              |
| "frecuencia", "cuántas veces aparece"             | HashMap counts                        |
| "máximo/mínimo del top-K"                         | PriorityQueue (heap)                  |
| "análisis de datos", "umbral", "anomalías"        | Streams + comparaciones simples       |
| "sin duplicados", "conjunto de"                   | HashSet                               |
| "por orden de inserción", "mantener orden"        | LinkedHashMap / LinkedHashSet         |
| "jerarquía de objetos", "comportamiento variable" | Strategy / Polimorfismo               |
| "validar", "transformar", "filtrar datos"         | Stream API                            |

---

## 📏 Reglas de Oro

1. **Compila antes de optimizar.** Una solución O(n²) que funciona vale más que O(log n) que no compila.

2. **Los tests que la plataforma muestra son simples.** Los que fallan son edge cases.  
   Antes de submit: prueba con `[]`, `[x]`, negativos, `Integer.MAX_VALUE`.

3. **Nombra tus variables claramente.** El código legible te ayuda a debuggear más rápido bajo presión.  
   `left` y `right` > `i` y `j` para dos pointers.

4. **Comenta el algoritmo antes de codificarlo** (2 líneas):

   ```java
   // Usar binary search porque el array está ordenado → O(log n)
   // Retornar -1 si no se encuentra
   ```

5. **No refactorices bajo tiempo.** Si funciona, no lo toques. Refactoring → nuevos bugs.

6. **Reutiliza métodos de Java.** No reinventes: `Collections.sort`, `Arrays.binarySearch`, `String.join`, Streams.  
   Un Senior sabe qué ya está resuelto en la JDK.

---

## 🎯 El Mindset Senior

> Un Mid **resuelve** el problema.  
> Un Senior **anticipa** los edge cases, **selecciona** el algoritmo correcto, y **demuestra** que piensa en escala.

Cuando terminas antes de tiempo:

- ¿Es mi solución O(n)? ¿Puede ser O(log n)?
- ¿Qué pasa con el caso edge del inicio/fin del array?
- ¿Puedo escribir un segundo test case mental que falle mi solución?

---

## 📋 Checklist de los Últimos 5 Minutos

```
[ ] ¿Todos los métodos tienen return en cada rama?
[ ] ¿Hay NullPointerException posible?
[ ] ¿Los índices pueden salirse del rango?
[ ] ¿Funciona con array vacío?
[ ] ¿Funciona con un solo elemento?
[ ] ¿Los tipos de datos son correctos (int vs long, double vs BigDecimal)?
[ ] ¿El resultado de binarySearch es negativo si no se encuentra?
```

---

_La prueba técnica Senior no es solo de código. Es de criterio, priorización y calma bajo presión._
