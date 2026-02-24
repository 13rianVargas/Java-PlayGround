# 🔍 Arrays Utility — El arma secreta de las pruebas técnicas

## ¿Por qué importa?

En entornos financieros y de alta demanda, los arrays de datos vienen **pre-ordenados** (registros bancarios por fecha, precios en orden cronológico, IDs secuenciales). Un Senior sabe que cuando el array está ordenado, ya no necesita buscar con un `for` loop.

---

## 📚 Teoría: `Arrays.binarySearch`

### La firma del método

```java
int Arrays.binarySearch(int[] a, int key)
int Arrays.binarySearch(int[] a, int fromIndex, int toIndex, int key)
```

### ¿Qué retorna?

| Resultado | Significado                                                |
| --------- | ---------------------------------------------------------- |
| `>= 0`    | Encontrado. El valor es el **índice** de `key` en el array |
| `< 0`     | No encontrado. El valor es `-(insertion point) - 1`        |

```java
int[] arr = {-9, 14, 37, 102};
Arrays.binarySearch(arr, 37);   // → 2 ✅ (encontrado en índice 2)
Arrays.binarySearch(arr, 36);   // → -3  (no está; posición de inserción sería 2, retorna -2-1 = -3)
```

### Regla mnemotécnica:

```
¿Encontrado? índice >= 0
¿No hallado? valor < 0  →  para saber DÓNDE insertar: -(resultado) - 1
```

### ⚠️ PRECONDICIÓN CRÍTICA

```java
// ✅ CORRECTO: array ordenado antes de binarySearch
int[] arr = {5, 3, 8, 1};
Arrays.sort(arr);                        // {1, 3, 5, 8}
int idx = Arrays.binarySearch(arr, 5);   // → 2

// ❌ INCORRECTO: binarySearch en array NO ordenado → resultado indefinido
int[] arr = {5, 3, 8, 1};
int idx = Arrays.binarySearch(arr, 5);   // comportamiento INDEFINIDO
```

---

## 📚 Teoría: Otros métodos de `Arrays`

```java
// sort — O(n log n). Modifica el array original (in-place)
int[] arr = {3, 1, 4, 1, 5};
Arrays.sort(arr);                            // [1, 1, 3, 4, 5]
Arrays.sort(arr, 1, 4);                      // ordena solo [1..4) → [1, 1, 3, 4, 5] parcial

// copyOf — O(n). Crea un nuevo array
int[] original = {1, 2, 3};
int[] longer = Arrays.copyOf(original, 5);   // [1, 2, 3, 0, 0] — padding con 0
int[] shorter = Arrays.copyOf(original, 2);  // [1, 2]

// copyOfRange — O(m). Slice del array — rango [from, to)
int[] slice = Arrays.copyOfRange(original, 1, 3);  // [2, 3]

// fill — O(n). Llenar con un valor
int[] zeros = new int[5];
Arrays.fill(zeros, 7);                       // [7, 7, 7, 7, 7]

// equals — O(n). Comparar contenido
Arrays.equals(new int[]{1, 2}, new int[]{1, 2});  // true
Arrays.equals(new int[]{1, 2}, new int[]{1, 3});  // false

// stream — convierte a IntStream/Stream para operaciones funcionales
int sum = Arrays.stream(arr).sum();
int max = Arrays.stream(arr).max().getAsInt();
int[] filtrado = Arrays.stream(arr).filter(n -> n > 3).toArray();

// toString — para debugging
System.out.println(Arrays.toString(arr));          // [1, 2, 3, 4, 5]
System.out.println(Arrays.deepToString(matrix));   // [[1,2],[3,4]] para 2D
```

---

## 🆚 Comparación de Complejidades: La razón por la que importa

| Algoritmo                      | Complejidad           | Para 1,000,000 elementos |
| ------------------------------ | --------------------- | ------------------------ |
| For loop (búsqueda lineal)     | O(n)                  | ~1,000,000 comparaciones |
| `Arrays.binarySearch`          | O(log n)              | ~20 comparaciones        |
| `Arrays.sort` + `binarySearch` | O(n log n) + O(log n) | ~20M ops (una sola vez)  |

**Conclusión:** Si el array ya viene ordenado (como sucede frecuentemente en finanzas: datos por fecha, IDs crecientes), usa `binarySearch`. Si necesitas ordenar primero, solo vale la pena si vas a hacer múltiples búsquedas.

---

## 📋 Ejercicios

Ver [`Exercises.java`](src/Exercises.java)

Ejercicio exacto del tipo de pregunta que viste en la prueba (imagen del enunciado):

> **"The aim of this exercise is to check the presence of a number in an array. Items are integers in ascending order. Implement `boolean A.exists(int[] ints, int k)` that returns `true` if `k` belongs to `ints`."**
