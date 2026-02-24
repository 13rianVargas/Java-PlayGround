# 🔑 SOLUCIONES — Exam-01

> ⛔ **NO LEAS ESTO HASTA COMPLETAR EL EXAMEN**

---

## Solución: Problema 1

```java
public static boolean exists(int[] ids, int searchId) {
    return Arrays.binarySearch(ids, searchId) >= 0;
}
```

**Por qué es la respuesta Senior:**

- `Arrays.binarySearch` ya está implementado en la JDK de forma óptima → O(log n)
- Un Senior **no reinventa** lo que ya existe en la librería estándar
- La clave: el retorno es `>= 0` cuando encuentra el elemento, negativo cuando no lo encuentra
- Un Mid escribiría un for loop → O(n) → no escala con 1M elementos

**Trampa frecuente:** `Arrays.binarySearch(ids, searchId) != -1` es INCORRECTO.
El valor de retorno puede ser cualquier negativo (no solo -1).

---

## Solución: Problema 2

```java
// 2a.
public static List<String> flaggedIds(List<Transaction> txns) {
    return txns.stream()
        .filter(Transaction::flagged)
        .map(Transaction::id)
        .sorted()
        .collect(Collectors.toList());
}

// 2b.
public static Map<String, Double> totalByCategory(List<Transaction> txns) {
    return txns.stream()
        .filter(t -> !t.flagged())
        .collect(Collectors.groupingBy(
            Transaction::category,
            Collectors.summingDouble(Transaction::amount)
        ));
}

// 2c.
public static Optional<Transaction> highestRisk(List<Transaction> txns) {
    return txns.stream()
        .filter(Transaction::flagged)
        .max(Comparator.comparingDouble(Transaction::amount));
}

// 2d.
public static boolean hasExcessiveAmount(List<Transaction> txns, double threshold) {
    return txns.stream().anyMatch(t -> t.amount() > threshold);
}
```

**Patrones Senior usados:**

- `groupingBy` + `summingDouble` → acumulador por categoría en un paso
- `max(Comparator.comparingDouble(...))` → retorna Optional automáticamente
- `anyMatch` → cortocircuito, no evalúa toda la lista si encuentra uno

---

## Solución: Problema 3

```java
public static List<String> classifyReadings(int[] readings, int threshold) {
    // Inputs inválidos → lista vacía
    if (readings == null || readings.length == 0 || threshold < 0) {
        return Collections.emptyList();
    }

    List<String> result = new ArrayList<>(readings.length);
    result.add("NORMAL"); // La primera lectura siempre es NORMAL

    for (int i = 1; i < readings.length; i++) {
        // ⚠️ Cast a long ANTES de restar para evitar Integer overflow
        long diff = (long) readings[i] - (long) readings[i - 1];

        if (diff > threshold) {
            result.add("HIGH");
        } else if (-diff > threshold) {
            result.add("LOW");
        } else {
            result.add("NORMAL");
        }
    }

    return result;
}
```

**Decisiones Senior:**

- `long diff = (long) readings[i] - (long) readings[i-1]` evita overflow con MIN/MAX_VALUE
- `-diff > threshold` en vez de `Math.abs(diff)` evita un segundo cast y es idiomático
- `Collections.emptyList()` (inmutable, eficiente) en vez de `new ArrayList<>()`
- `new ArrayList<>(readings.length)` pre-aloca capacidad → evita resize internal
- La validación de `threshold < 0` está antes del loop (fail-fast)

**El 30% de candidatos FALLA en T13/T14 por no usar `long`**.

---

## Puntuación Auto-evaluación

| Tests pasados (P1) | pts | Tests pasados (P2) | pts | Tests pasados (P3) | pts |
| ------------------ | --- | ------------------ | --- | ------------------ | --- |
| 11/11              | 30  | 14/14              | 40  | 18/18              | 30  |
| 8–10               | 20  | 10–13              | 25  | 14–17              | 20  |
| 5–7                | 10  | 6–9                | 15  | 8–13               | 10  |
| < 5                | 0   | < 6                | 0   | < 8                | 0   |
