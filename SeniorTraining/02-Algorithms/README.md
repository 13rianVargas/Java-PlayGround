# ⚙️ Módulo 02 — Algorithms

> **"No te piden que inventes algoritmos. Te piden que reconozcas cuál usar en 5 minutos."**

Este es el módulo que más impacta en las pruebas Senior. El 80% de problemas en CodinGame/HackerRank se resuelve con uno de estos 6 patrones.

---

## 🎯 Los 6 Patrones que Debes Dominar

| #   | Patrón                | Cuándo usarlo                             | Complejidad   |
| --- | --------------------- | ----------------------------------------- | ------------- |
| 01  | Binary Search         | Array ordenado, buscar/encontrar límite   | O(log n)      |
| 02  | Two Pointers          | Par de elementos, palindromo, merge       | O(n)          |
| 03  | Sliding Window        | Subarray/substring de tamaño K            | O(n)          |
| 04  | Sorting + Comparators | Ordenar objetos, múltiples criterios      | O(n log n)    |
| 05  | Recursión             | Divide y vencerás, árboles, combinaciones | O(2ⁿ) típico  |
| 06  | Dynamic Programming   | Subproblemas repetidos, optimización      | O(n²) o mejor |

---

## 📁 Estructura

```
02-Algorithms/
├── README.md
├── 01-Binary-Search/
│   └── src/  Main.java · Exercises.java · Solutions.java
├── 02-Two-Pointers/
│   └── src/  Main.java · Exercises.java · Solutions.java
├── 03-Sliding-Window/
│   └── src/  Main.java · Exercises.java · Solutions.java
├── 04-Sorting/
│   └── src/  Main.java · Exercises.java · Solutions.java
├── 05-Recursion/
│   └── src/  Main.java · Exercises.java · Solutions.java
└── 06-Dynamic-Programming/
    └── src/  Main.java · Exercises.java · Solutions.java
```

---

## 🔑 Señales de Reconocimiento

### ¿Cuándo aplico Binary Search?

- El problema dice "array ordenado" + "eficiencia" + puede tener hasta 10⁶ elementos
- Buscas un valor exacto o una posición de inserción
- El problema dice "mínimo/máximo que satisface condición X" (binary search en respuesta)

### ¿Cuándo aplico Two Pointers?

- Trabajas con un SOLO array/string con dos índices
- Problema pide par de elementos (suma = target, etc.)
- "Palindromo", "merge de arrays ordenados", "eliminar duplicados"

### ¿Cuándo aplico Sliding Window?

- "Subarray de tamaño K", "ventana", "substring sin repeticiones"
- Trabajo en rango contiguous de elementos
- Suma/promedio de ventana que se mueve

### ¿Cuándo aplico Recursión/DP?

- "Todas las combinaciones/permutaciones" → Backtracking
- "Mínimo de operaciones", "máximo valor", "¿es posible llegar a X?" → DP
- El problema se divide en subproblemas IDÉNTICOS más pequeños

---

## 📋 Orden de Estudio

1. **01-Binary-Search** — Empieza aquí. Core de muchas pruebas Senior.
2. **02-Two-Pointers** — Segundo más frecuente.
3. **03-Sliding-Window** — Tercer lugar.
4. **04-Sorting** — Lo usas en combinación con los anteriores.
5. **05-Recursion** — Si tienes tiempo.
6. **06-Dynamic-Programming** — Solo ejemplos básicos para este nivel de prueba.
