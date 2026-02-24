# 🛡️ EDGE CASES CHECKLIST

> Antes de entregar CUALQUIER solución, recorre esta lista mentalmente (2 minutos).
> Las pruebas senior tienen edge cases diseñados para eliminar candidatos.

---

## 📌 Arrays / Colecciones

- [ ] **Array vacío** (`arr.length == 0`) → ¿tu método lanza excepción o retorna valor por defecto?
- [ ] **Array con un solo elemento** → ¿funciona la lógica de "comparar con vecinos"?
- [ ] **Array con todos los elementos iguales** → ¿bucles infinitos?, ¿resultado correcto?
- [ ] **Array ordenado inversamente** → ¿falla sort si asumes orden ascendente?
- [ ] **Array ya ordenado** → ¿es correcto y eficiente?
- [ ] **`null` como argumento** → la firma puede garantizarlo ("the array is never null") — léela bien
- [ ] **Índices fuera de rango** → ¿el caller puede pasar `from > to` o `index >= array.length`?
- [ ] **Colección `null`** vs **colección vacía** — son distintos problemas

---

## 🔢 Números

- [ ] **Integer overflow** — si sumas dos `int` grandes: usa `long` o `Math.addExact(a, b)`
  ```java
  int a = Integer.MAX_VALUE;
  int b = 1;
  long sum = (long) a + b;  // ✅  no: a + b → overflow silencioso
  ```
- [ ] **División por cero** — verifica denominador distinto de 0
- [ ] **Números negativos** — ¿tu módulo `%` funciona? En Java `%` puede ser negativo: `-5 % 3 = -2`
  ```java
  Math.floorMod(-5, 3);  // → 1 ✅  resultado siempre positivo
  ```
- [ ] **`int` para índices, `long` para sumas grandes** — suma de 1M enteros de `int` puede overflowear si son grandes
- [ ] **Comparar `double`/`float`** — nunca `==`, usa `Math.abs(a - b) < 1e-9`
- [ ] **`BigDecimal` comparación** — usa `.compareTo()`, NUNCA `.equals()` (distingue escala: `2.0 != 2.00`)

---

## 🔡 Strings

- [ ] **String vacío** (`""`) — distinto de `null`
- [ ] **String con un solo carácter**
- [ ] **Todos mayúsculas / todos minúsculas** — ¿tu lógica de casing es correcta?
- [ ] **Espacios en blanco al inicio/fin** — ¿debes hacer `trim()`?
- [ ] **Caracteres especiales** (@, #, ñ, ü) — si aplica regex
- [ ] **String palindromo** de longitud par vs impar
- [ ] **Case sensitivity** — ¿la comparación debe ser case-insensitive?

---

## 📊 Análisis de Datos / Umbrales

(Especialmente relevante para ejercicios tipo radioactividad)

- [ ] **Exactamente en el umbral** — si el límite es `>= 2`, ¿qué pasa cuando la diferencia es exactamente `2`? ¿Es HIGH o NORMAL?  
      Leer el enunciado con cuidado: `>` vs `>=`
- [ ] **Primera y última medición** — si comparas con vecinos, ¿qué pasa en los extremos?
- [ ] **Dataset con un solo punto** — ¿tiene "anterior" o "siguiente"?
- [ ] **Valores negativos en el dataset** — ¿la diferencia absoluta funciona? `Math.abs(a - b)`
- [ ] **Todos los valores iguales** — diferencia es 0, ¿clasificación correcta?
- [ ] **Picos aislados** (un valor alto entre valores normales)
- [ ] **Tendencia constante** (valores siempre subiendo o bajando)

---

## 🔍 Búsqueda

- [ ] **Elemento no existe** — `Arrays.binarySearch` retorna negativo, ¿manejas ese caso?
- [ ] **Elemento duplicado** — `Arrays.binarySearch` no garantiza cuál instancia retorna
- [ ] **Primer/último elemento del array** — ¿los índices de inicio/fin funcionan correctamente?
- [ ] **Buscar en array de un elemento**
- [ ] **Buscar el valor mínimo / máximo** del array

---

## 🔄 Recursión

- [ ] **Caso base definido** — ¿siempre se alcanza?
- [ ] **Stack overflow** — para n > 10,000 considera iteración con stack explícito
- [ ] **Memoización** — ¿computas el mismo subproblema múltiples veces? → cache con `Map`

---

## 🧵 Tipos de Retorno

- [ ] Si el método retorna `int`, ¿qué retorna cuando no encuentra nada? (`-1`, `0`, `Integer.MIN_VALUE`?) — el enunciado lo especifica
- [ ] Si retorna `List`, ¿puede ser lista vacía? ¿puede ser `null`? Preferir lista vacía: `Collections.emptyList()`
- [ ] Si retorna `Optional`, nunca retornes `Optional.of(null)`, usa `Optional.empty()`
- [ ] Si retorna `boolean`, los casos `true` Y `false` deben estar cubiertos

---

## 🧪 Antes de Entregar

```
✅ ¿Compilé el código mentalmente con el ejemplo del enunciado?
✅ ¿Probé con array/string vacío?
✅ ¿Probé con un solo elemento?
✅ ¿Probé con valores negativos?
✅ ¿Está el caso "exactamente en el límite" correcto?
✅ ¿Hay posible NullPointerException?
✅ ¿Hay posible ArrayIndexOutOfBoundsException?
✅ ¿Es la complejidad razonable? (para 1M elementos, O(n²) falla)
```

---

## ⚡ Tabla de Complejidades de Referencia

| Operación                       | Complejidad                  |
| ------------------------------- | ---------------------------- |
| `Arrays.binarySearch(arr, key)` | O(log n)                     |
| `Arrays.sort(arr)`              | O(n log n)                   |
| `HashMap.get/put/contains`      | O(1) amortizado              |
| `TreeMap.get/put/contains`      | O(log n)                     |
| `ArrayList.get(i)`              | O(1)                         |
| `ArrayList.add(i, val)`         | O(n)                         |
| `LinkedList.add(i, val)`        | O(1) si tienes la referencia |
| `PriorityQueue.offer/poll`      | O(log n)                     |
| `String.contains(sub)`          | O(n·m)                       |
| Iterar `List` con for-each      | O(n)                         |
| `stream().filter().collect()`   | O(n)                         |

**Regla práctica para 1M elementos (10⁶):**

- O(n): ✅ ~1ms
- O(n log n): ✅ ~20ms
- O(n²): ❌ ~1000s → **FALLA**
- O(n³): ❌ **IMPOSIBLE**

---

_Revisa esta lista en los últimos 5 minutos de cualquier prueba._
