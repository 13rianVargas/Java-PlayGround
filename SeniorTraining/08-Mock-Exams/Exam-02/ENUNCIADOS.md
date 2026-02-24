# 📝 EXAM-02 — Enunciados

> ⏱️ **90 MINUTOS. Empieza el cronómetro AHORA.**

---

## PROBLEMA 1 — Ventana Deslizante (30 pts) ⏱️ 25 min

### Contexto

Un sistema de monitoreo de fraude analiza transacciones consecutivas. Necesitas encontrar la subsecuencia de `k` transacciones seguidas con el mayor monto total.

### Especificación

```java
/**
 * Dado un array de amounts y un tamaño de ventana k,
 * retorna el monto total máximo entre todas las ventanas de tamaño k.
 *
 * @param amounts  array de montos (nunca null, siempre length >= k)
 * @param k        tamaño de la ventana, 1 <= k <= amounts.length
 * @return         suma máxima de k elementos consecutivos
 */
static long maxWindowSum(int[] amounts, int k)
```

### Ejemplos

```
amounts = [100, 200, 150, 300, 250], k = 2
windows: [100+200=300, 200+150=350, 150+300=450, 300+250=550]
resultado: 550

amounts = [5, 5, 5, 5, 5], k = 3
resultado: 15

amounts = [1000], k = 1
resultado: 1000
```

**Restricción de rendimiento:** O(n). Está prohibido recalcular la suma desde cero en cada ventana.

---

## PROBLEMA 2 — Procesamiento de Texto (40 pts) ⏱️ 35 min

### Contexto

El sistema de atención al cliente necesita analizar comentarios de usuarios.

### Especificación

```java
record Comment(String userId, String text, int rating)
```

Implementa:

**2a.** `Map<Integer, Long> countByRating(List<Comment> comments)`  
Cuenta cuántos comentarios hay por cada rating (1-5).

**2b.** `List<String> topWords(List<Comment> comments, int n, Set<String> stopWords)`  
Retorna las `n` palabras más frecuentes (ignorando stopWords y mayúsculas/minúsculas), ordenadas por frecuencia descendente. En caso de empate, orden alfabético.

**2c.** `OptionalDouble averageRatingForUser(List<Comment> comments, String userId)`  
Promedio de ratings del usuario indicado. `OptionalDouble.empty()` si no tiene comentarios.

**2d.** `Map<String, Double> avgRatingPerUser(List<Comment> comments)`  
Mapa `userId → promedio rating`, solo para usuarios con al menos 2 comentarios.

---

## PROBLEMA 3 — Validador de Transacciones (30 pts) ⏱️ 30 min

### Contexto

El sistema de pagos debe validar que una secuencia de transacciones no viole ninguna regla de negocio.

### Especificación

```java
/**
 * @param amounts    montos de las transacciones (puede ser null)
 * @param dailyLimit límite máximo por transacción individual (> 0)
 * @param maxDaily   límite de la suma total diaria (> 0)
 * @return           ValidationResult con isValid, totalAmount y violationCount
 */
static ValidationResult validate(int[] amounts, int dailyLimit, int maxDaily)
```

```java
record ValidationResult(boolean isValid, long totalAmount, int violationCount) {}
```

**Reglas:**

- Una transacción viola la regla si `amount > dailyLimit`
- El día viola la regla si `sum(amounts) > maxDaily`
- `isValid = true` solo si `violationCount == 0` Y `totalAmount <= maxDaily`
- Si `amounts` es null o vacío: `isValid=true`, `totalAmount=0`, `violationCount=0`
- Si `dailyLimit <= 0` o `maxDaily <= 0`: lanza `IllegalArgumentException`

### Ejemplos

```
amounts=[100, 200, 50], dailyLimit=150, maxDaily=500
→ ValidationResult(isValid=false, totalAmount=350, violationCount=1)
  (TX de 200 viola dailyLimit=150)

amounts=[50, 50, 50], dailyLimit=100, maxDaily=100
→ ValidationResult(isValid=false, totalAmount=150, violationCount=0)
  (ninguna tx individual viola, pero suma 150 > maxDaily=100)
```

---

## 🏁 Para ejecutar los tests:

```bash
cd 08-Mock-Exams/Exam-02/
mvn test
```
