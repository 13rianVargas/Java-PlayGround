# 📚 02 — Collections Deep Dive

> **"HashMap te consigue O(1), pero TreeMap te consigue O(log n) Y orden. El Senior sabe cuándo usar cada uno."**

---

## 🎯 Objetivo

Dominar las colecciones avanzadas de Java que aparecen en pruebas Senior:

- **HashMap** vs **TreeMap** vs **LinkedHashMap** — cuándo usar cada uno
- **Frequency Maps** — patrón #1 en pruebas técnicas
- **PriorityQueue (Heap)** — top-K, scheduling
- **Deque (ArrayDeque)** — stack + queue en una sola estructura

---

## 📁 Archivos

| Archivo | Descripción |
|---------|-------------|
| `src/Main.java` | Guía teórica con ejemplos ejecutables |
| `src/Exercises.java` | Ejercicios con `TODO` para que completes |
| `src/Solutions.java` | Soluciones anotadas con complejidad |

---

## 🔑 Patrones Clave

### Frequency Map (el más frecuente en pruebas)

```java
Map<Character, Integer> freq = new HashMap<>();
for (char c : str.toCharArray()) {
    freq.merge(c, 1, Integer::sum);
}
```

### Top-K con PriorityQueue

```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
for (int val : arr) {
    minHeap.offer(val);
    if (minHeap.size() > k) minHeap.poll();
}
// minHeap contiene los K elementos más grandes
```

---

## 🛠️ Cómo ejecutar

```bash
javac src/*.java -d bin/
java -cp bin/ Main
```
