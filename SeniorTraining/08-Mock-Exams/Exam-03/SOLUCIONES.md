# 🔑 SOLUCIONES — Exam-03

> ⛔ **NO LEAS ESTO HASTA COMPLETAR EL EXAMEN**

---

## Solución: Problema 1 — Combinations DP

```java
public static int countCombinations(int amount, int[] denominations) {
    if (amount == 0) return 0; // según enunciado

    int[] dp = new int[amount + 1];
    dp[0] = 1; // 1 forma de hacer 0: no usar nada

    for (int denom : denominations) {
        for (int i = denom; i <= amount; i++) {
            dp[i] += dp[i - denom];
        }
    }

    return dp[amount];
}
```

**¿Por qué el orden de los loops importa?**

- El loop externo es **por denominación** → asegura que cada denominación se considere sin duplicar combinaciones (el orden no importa = "Coin Change 2")
- Si inviertes los loops, cuentas **permutaciones** (orden SÍ importa), lo cual sería una respuesta incorrecta

**Complejidad:** O(amount × denominations.length) tiempo, O(amount) espacio.

---

## Solución: Problema 2 — Payroll

```java
// 2a.
public static Map<String, List<Employee>> byDepartment(List<Employee> employees) {
    return employees.stream()
        .filter(Employee::active)
        .collect(Collectors.groupingBy(Employee::department));
}

// 2b.
public static Map<String, Double> totalPayrollByDept(List<Employee> employees) {
    return employees.stream()
        .filter(Employee::active)
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.summingDouble(Employee::effectiveSalary)));
}

// 2c.
public static List<Employee> topEarners(List<Employee> employees, int n) {
    return employees.stream()
        .filter(Employee::active)
        .sorted(Comparator.comparingDouble(Employee::effectiveSalary).reversed())
        .limit(n)
        .collect(Collectors.toList());
}

// 2d.
public static Optional<String> mostExpensiveDepartment(List<Employee> employees) {
    return employees.stream()
        .filter(Employee::active)
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.summingDouble(Employee::effectiveSalary)))
        .entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey);
}
```

**Puntos Senior:**

- `effectiveSalary()` encapsulado en el record → computed property, no duplicación
- `Optional.map()` en 2d → transformación elegante sin null check
- `Comparator.reversed()` en 2c → en vez de negación manual

---

## Solución: Problema 3 — LRU Cache

```java
public class LRUCache {

    private final int capacity;
    private final LinkedHashMap<Integer, Integer> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        // accessOrder=true: mueve el elemento al final en cada get/put
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > LRUCache.this.capacity;
            }
        };
    }

    public int get(int key) {
        return cache.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        cache.put(key, value);
    }
}
```

**Por qué LinkedHashMap:**

- `accessOrder=true` → mantiene orden por último acceso (get y put)
- `removeEldestEntry()` es un hook que Java llama automáticamente después de cada `put`
- Todo el trabajo de O(1) get/put lo hace el LinkedHashMap internamente

**Alternativa sin LinkedHashMap (más código, mismo concepto):**

- `HashMap<Integer, Node>` + doubly-linked list manual
- Mucho más código pero mismo comportamiento
- El Senior reconoce que LinkedHashMap ya implementa LRU

**Esta es la respuesta que distingue a un Senior:** conocer la librería estándar profundamente.
