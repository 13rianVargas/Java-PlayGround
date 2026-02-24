# 🔑 SOLUCIONES — Exam-02

> ⛔ **NO LEAS ESTO HASTA COMPLETAR EL EXAMEN**

---

## Solución: Problema 1 — Sliding Window

```java
public static long maxWindowSum(int[] amounts, int k) {
    // Calcula la primera ventana
    long windowSum = 0;
    for (int i = 0; i < k; i++) {
        windowSum += amounts[i];
    }

    long maxSum = windowSum;

    // Desliza la ventana: agrega el nuevo, elimina el viejo
    for (int i = k; i < amounts.length; i++) {
        windowSum += amounts[i] - amounts[i - k];
        maxSum = Math.max(maxSum, windowSum);
    }

    return maxSum;
}
```

**Por qué `long`:** Con arrays de int, la suma de k=1M elementos puede desbordar int.  
**O(n):** Solo recorremos el array una vez. Sin Sliding Window sería O(n\*k).

---

## Solución: Problema 2

```java
// 2a.
public static Map<Integer, Long> countByRating(List<Comment> comments) {
    return comments.stream()
        .collect(Collectors.groupingBy(Comment::rating, Collectors.counting()));
}

// 2b.
public static List<String> topWords(List<Comment> comments, int n, Set<String> stopWords) {
    Map<String, Long> freq = comments.stream()
        .flatMap(c -> Arrays.stream(c.text().toLowerCase().split("\\s+")))
        .filter(w -> !w.isBlank() && !stopWords.contains(w))
        .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

    return freq.entrySet().stream()
        .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
            .thenComparing(Map.Entry.comparingByKey()))
        .limit(n)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
}

// 2c.
public static OptionalDouble averageRatingForUser(List<Comment> comments, String userId) {
    return comments.stream()
        .filter(c -> c.userId().equals(userId))
        .mapToInt(Comment::rating)
        .average();
}

// 2d.
public static Map<String, Double> avgRatingPerUser(List<Comment> comments) {
    return comments.stream()
        .collect(Collectors.groupingBy(Comment::userId,
            Collectors.averagingInt(Comment::rating)))
        .entrySet().stream()
        // Filtra solo usuarios con >= 2 comentarios
        .filter(e -> comments.stream()
            .filter(c -> c.userId().equals(e.getKey())).count() >= 2)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
}
```

**Alternativa más eficiente para 2d (un solo paso):**

```java
public static Map<String, Double> avgRatingPerUser(List<Comment> comments) {
    // Paso 1: mapas intermedios con count y sum
    Map<String, long[]> acc = new HashMap<>();
    comments.forEach(c -> acc.merge(c.userId(),
        new long[]{1, c.rating()},
        (a, b) -> new long[]{a[0]+1, a[1]+b[1]}));

    // Paso 2: filtra >= 2 y calcula promedio
    return acc.entrySet().stream()
        .filter(e -> e.getValue()[0] >= 2)
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            e -> (double) e.getValue()[1] / e.getValue()[0]));
}
```

---

## Solución: Problema 3

```java
public static ValidationResult validate(int[] amounts, int dailyLimit, int maxDaily) {
    if (dailyLimit <= 0 || maxDaily <= 0) {
        throw new IllegalArgumentException(
            "dailyLimit y maxDaily deben ser > 0");
    }
    if (amounts == null || amounts.length == 0) {
        return new ValidationResult(true, 0L, 0);
    }

    long totalAmount = 0;
    int violationCount = 0;

    for (int amount : amounts) {
        totalAmount += amount;
        if (amount > dailyLimit) {
            violationCount++;
        }
    }

    boolean isValid = violationCount == 0 && totalAmount <= maxDaily;
    return new ValidationResult(isValid, totalAmount, violationCount);
}
```

**Puntos Senior:**

- Validación de argumentos ANTES de procesar datos (fail-fast)
- `long totalAmount` para evitar overflow
- Record como tipo de retorno → inmutable, legible, sin getters manuales
- Una sola pasada por el array O(n)
