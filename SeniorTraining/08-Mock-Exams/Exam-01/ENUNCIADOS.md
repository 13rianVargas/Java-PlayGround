# 📝 EXAM-01 — Enunciados

> ⏱️ **90 MINUTOS. EMPIEZA EL CRONÓMETRO AHORA.**
> No mires las soluciones hasta terminar.

---

## PROBLEMA 1 — Búsqueda Eficiente (30 pts) ⏱️ 25 min

### Contexto

En un sistema bancario, los IDs de transacciones se almacenan en orden ascendente. El equipo de fraude necesita verificar rápidamente si ciertos IDs están registrados.

### Especificación

El array puede tener hasta **1 millón de elementos**.  
El array siempre está en **orden ascendente**.  
El array **nunca es null**.

Implementa el método:

```java
static boolean exists(int[] ids, int searchId)
```

Que retorna `true` si `searchId` está en `ids`, `false` en caso contrario.

**Nota importante:** El tiempo de ejecución importa. Usa el algoritmo más eficiente posible.

### Ejemplos

```
ids = [-9, 14, 37, 102]
exists(ids, 102)  →  true
exists(ids, 36)   →  false
exists(ids, -9)   →  true
exists(ids, 0)    →  false
```

### Archivo a implementar

`src/main/java/Problem1.java` — método `exists(int[], int)`

---

## PROBLEMA 2 — Análisis de Transacciones (40 pts) ⏱️ 35 min

### Contexto

El departamento de riesgo necesita un reporte automatizado de las transacciones del mes. Te dan una lista de transacciones y necesitas procesar la información con Streams.

### Especificación

```java
record Transaction(String id, String category, double amount, boolean flagged)
```

Implementa los siguientes métodos:

**2a.** `List<String> flaggedIds(List<Transaction> txns)`  
Retorna los IDs de transacciones con `flagged = true`, ordenados alfabéticamente.

**2b.** `Map<String, Double> totalByCategory(List<Transaction> txns)`  
Retorna la suma de montos por categoría (solo transacciones NO flagged).

**2c.** `Optional<Transaction> highestRisk(List<Transaction> txns)`  
Retorna la transacción flagged con mayor monto. Si no hay ninguna, `Optional.empty()`.

**2d.** `boolean hasExcessiveAmount(List<Transaction> txns, double threshold)`  
Retorna `true` si **alguna** transacción tiene monto > threshold.

### Archivo a implementar

`src/main/java/Problem2.java`

---

## PROBLEMA 3 — Detector de Anomalías (30 pts) ⏱️ 30 min

### Contexto

Un sistema de monitoreo recibe lecturas de sensores en tiempo real. Debes clasificar cada lectura como NORMAL, HIGH o LOW según su variación respecto a la lectura anterior.

### Especificación

```java
/**
 * @param readings  array de lecturas del sensor (puede ser null)
 * @param threshold variación máxima permitida entre lecturas consecutivas (>= 0)
 * @return          List<String> con "NORMAL", "HIGH" o "LOW" por cada lectura
 *                  La primera lectura siempre es "NORMAL"
 *                  Si readings es null o vacío, retorna lista vacía
 *                  Si threshold < 0, retorna lista vacía
 */
static List<String> classifyReadings(int[] readings, int threshold)
```

**Reglas:**

- Primera lectura → siempre "NORMAL"
- Si `readings[i] - readings[i-1] > threshold` → "HIGH"
- Si `readings[i-1] - readings[i] > threshold` → "LOW"
- Si diferencia ≤ threshold → "NORMAL"

### Ejemplos

```
readings  = [10, 12, 8, 15, 14], threshold = 3
resultado = ["NORMAL", "NORMAL", "LOW", "HIGH", "NORMAL"]

readings  = [], threshold = 5
resultado = []

readings  = [100], threshold = 0
resultado = ["NORMAL"]
```

### Archivo a implementar

`src/main/java/Problem3.java`

⚠️ **Este problema tiene 18 tests automatizados. Para pasar todos necesitas manejar correctamente los edge cases.**

---

## 🏁 Cuando termines

```bash
cd 08-Mock-Exams/Exam-01/
mvn test
```

Cuenta los tests que pasaron y calcula tu puntaje.
