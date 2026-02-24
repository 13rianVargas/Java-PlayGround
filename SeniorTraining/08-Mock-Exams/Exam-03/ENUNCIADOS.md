# 📝 EXAM-03 — Enunciados (Nivel Senior)

> ⏱️ **90 MINUTOS. El más difícil de los tres. Empieza el cronómetro AHORA.**

---

## PROBLEMA 1 — Recursión + Memoización (30 pts) ⏱️ 25 min

### Contexto

Un sistema financiero calcula el número de formas posibles de pagar una deuda usando billetes de denominaciones fijas.

### Especificación

```java
/**
 * Cuenta el número de combinaciones de billetes para alcanzar exactamente 'amount'.
 * El orden NO importa: [200, 100] == [100, 200] es la misma combinación.
 * Cada denominación puede usarse múltiples veces.
 *
 * @param amount        monto objetivo (0 <= amount <= 10_000)
 * @param denominations denominaciones disponibles, nunca null, sin duplicados, todos > 0
 * @return              número de combinaciones únicas, 0 si amount = 0
 */
static int countCombinations(int amount, int[] denominations)
```

### Ejemplos

```
amount=5, denominations=[1,2,5]
→ 4 combinaciones: [5], [2,2,1], [2,1,1,1], [1,1,1,1,1]

amount=3, denominations=[2]
→ 0 (imposible exactamente con solo billetes de 2)

amount=0, denominations=[1,2,5]
→ 0
```

**Nota:** Sin memoización, `amount=10_000` con denominations=[1,2,5] tardará décadas. Con DP → O(amount × denominations.length).

---

## PROBLEMA 2 — Diseño con Records y Streams (40 pts) ⏱️ 35 min

### Contexto

El sistema de gestión de empleados necesita un procesador de payroll (nómina).

### Especificación

```java
record Employee(String id, String name, String department,
                double baseSalary, double bonusPercent, boolean active)
```

Implementa:

**2a.** `Map<String, List<Employee>> byDepartment(List<Employee> employees)`  
Agrupa empleados activos por departamento.

**2b.** `Map<String, Double> totalPayrollByDept(List<Employee> employees)`  
Suma total de `baseSalary * (1 + bonusPercent/100)` por departamento (solo activos).

**2c.** `List<Employee> topEarners(List<Employee> employees, int n)`  
Los `n` empleados activos con mayor salario efectivo (`base * (1 + bonus/100)`). Si hay menos de `n`, retorna todos. Orden descendente por salario efectivo.

**2d.** `Optional<String> mostExpensiveDepartment(List<Employee> employees)`  
Nombre del departamento con mayor payroll total (solo activos). `Optional.empty()` si no hay activos.

---

## PROBLEMA 3 — Sistema Completo (30 pts) ⏱️ 30 min

### Contexto

Implementa un mini-sistema de caché LRU (Least Recently Used) para un sistema de consultas bancarias.

### Especificación

```java
/**
 * Caché LRU con capacidad fija.
 * - get(key): retorna el valor, o -1 si no existe. Marca como "usado recientemente".
 * - put(key, value): inserta o actualiza. Si la capacidad está llena,
 *                   elimina el elemento menos recientemente usado (LRU).
 */
class LRUCache {
    LRUCache(int capacity)
    int get(int key)
    void put(int key, int value)
}
```

### Ejemplos

```
LRUCache cache = new LRUCache(2);
cache.put(1, 10);
cache.put(2, 20);
cache.get(1);    →  10    (1 ahora es el más reciente)
cache.put(3, 30); // Expulsa 2 (el menos usado)
cache.get(2);    →  -1   (ya no existe)
cache.get(3);    →  30
```

**Pista:** Java tiene una clase en su librería estándar que implementa exactamente este comportamiento con una sola línea de configuración.

---

## 🏁 Para ejecutar los tests:

```bash
cd 08-Mock-Exams/Exam-03/
mvn test
```
