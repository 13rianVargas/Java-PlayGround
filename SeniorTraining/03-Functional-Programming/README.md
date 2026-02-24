# 🔗 Módulo 03 — Functional Programming

> **"El gap más grande entre un Mid y un Senior en Java hoy mismo."**

La programación funcional en Java existe desde Java 8 (2014). En 2026, en el sector financiero, es **obligación**. Las pruebas Senior generalmente incluyen al menos un problema que se resuelve elegantemente con Streams en 3 líneas, vs 20 líneas imperativas.

---

## 🎯 Lo que aprenderás

- **Lambdas** y la syntaxis correcta
- **Interfaces funcionales**: `Predicate`, `Function`, `Consumer`, `Supplier`, `BiFunction`
- **Stream API**: `filter`, `map`, `flatMap`, `reduce`, `collect`, `groupingBy`
- **Optional**: el reemplazo correcto del null check
- **Method References**: `Class::method`, `instance::method`, `Class::new`

---

## 📁 Estructura

```
03-Functional-Programming/
├── README.md
├── 01-Lambdas/
│   └── src/ Main.java · Exercises.java · Solutions.java
├── 02-Stream-API/
│   └── src/ Main.java · Exercises.java · Solutions.java
├── 03-Optional/
│   └── src/ Main.java · Exercises.java · Solutions.java
└── 04-Method-References/
    └── src/ Main.java · Exercises.java · Solutions.java
```

---

## 🔑 La Transformación Mental

El código imperativo Y funcional hacen lo MISMO. La diferencia es legibilidad y expresividad.

**Mid (imperativo):**

```java
List<Integer> result = new ArrayList<>();
for (Transaction t : transactions) {
    if (t.getAmount() > 1000) {
        result.add(t.getAmount() * 2);
    }
}
Collections.sort(result);
```

**Senior (funcional):**

```java
List<Integer> result = transactions.stream()
    .filter(t -> t.getAmount() > 1000)
    .map(t -> t.getAmount() * 2)
    .sorted()
    .collect(Collectors.toList());
```

Mismo resultado. El Senior escribe código que se **lee como el enunciado del problema**.

---

## ⏱️ Tiempo estimado: 3–4 horas
