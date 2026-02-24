# ⚡ CHEATSHEET — Java Senior Technical Tests

> Consulta este archivo los 30 minutos antes de cualquier prueba. Memoriza las firmas de métodos clave.

---

## 🔢 Arrays

```java
// Ordenar — O(n log n)
Arrays.sort(arr);
Arrays.sort(arr, fromIndex, toIndex);          // rango parcial

// Buscar en array ORDENADO — O(log n)
int idx = Arrays.binarySearch(arr, key);
// idx >= 0  → encontrado en esa posición
// idx < 0   → NO está; inserción sería en -(idx+1)
// ⚠️ El array DEBE estar ordenado antes de llamar binarySearch

// Copiar
int[] copy = Arrays.copyOf(arr, newLength);
int[] slice = Arrays.copyOfRange(arr, from, to); // [from, to)

// Llenar
Arrays.fill(arr, value);
Arrays.fill(arr, fromIndex, toIndex, value);

// Comparar
Arrays.equals(arr1, arr2);                      // true si mismo contenido

// Convertir a Stream
Arrays.stream(arr).filter(...).toArray();
Arrays.stream(arr).sum();                       // int[], long[], double[]
Arrays.stream(arr).max().getAsInt();

// Imprimir (debug)
System.out.println(Arrays.toString(arr));
System.out.println(Arrays.deepToString(matrix)); // 2D
```

---

## 📚 Collections

### List

```java
List<Integer> list = new ArrayList<>();
List<Integer> list = new LinkedList<>();        // mejor para insertar al inicio/medio
List<Integer> fixed = List.of(1, 2, 3);        // inmutable, Java 9+
List<Integer> copy = new ArrayList<>(List.of(1, 2, 3)); // mutable

list.add(val);
list.add(index, val);
list.remove(Integer.valueOf(val));              // ⚠️ remove(Object) vs remove(int index)
list.set(index, val);
list.get(index);
list.size();
list.contains(val);
list.indexOf(val);
Collections.sort(list);
Collections.sort(list, Comparator.reverseOrder());
Collections.reverse(list);
Collections.max(list);
Collections.min(list);
Collections.frequency(list, val);
Collections.unmodifiableList(list);             // vista inmutable
```

### Map

```java
Map<String, Integer> map = new HashMap<>();
Map<String, Integer> sorted = new TreeMap<>();  // ordenado por clave
Map<String, Integer> ordered = new LinkedHashMap<>(); // orden de inserción

map.put(key, value);
map.get(key);                                   // null si no existe
map.getOrDefault(key, defaultValue);            // ✅ uso recomendado
map.containsKey(key);
map.containsValue(value);
map.remove(key);
map.size();
map.isEmpty();
map.keySet();                                   // Set<K>
map.values();                                   // Collection<V>
map.entrySet();                                 // Set<Map.Entry<K,V>>

// Patrón frecuente: contar ocurrencias
map.merge(key, 1, Integer::sum);
map.put(key, map.getOrDefault(key, 0) + 1);    // equivalente

// Computar solo si ausente
map.computeIfAbsent(key, k -> new ArrayList<>()).add(val);

// Iterar
for (Map.Entry<String, Integer> e : map.entrySet()) {
    System.out.println(e.getKey() + " → " + e.getValue());
}
```

### Set

```java
Set<Integer> set = new HashSet<>();             // O(1) add/contains
Set<Integer> sorted = new TreeSet<>();          // O(log n), ordenado
Set<Integer> ordered = new LinkedHashSet<>();   // O(1), mantiene inserción

set.add(val);
set.remove(val);
set.contains(val);
set.size();
// Intersección
set1.retainAll(set2);
// Unión
set1.addAll(set2);
// Diferencia
set1.removeAll(set2);
```

### PriorityQueue (Heap)

```java
// Min-heap por defecto
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// Max-heap
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

// Top-K más grandes: min-heap de tamaño K
PriorityQueue<Integer> topK = new PriorityQueue<>();
for (int n : nums) {
    topK.offer(n);
    if (topK.size() > k) topK.poll(); // elimina el más pequeño
}

minHeap.offer(val);                             // insertar
minHeap.poll();                                 // extraer mínimo (y eliminate)
minHeap.peek();                                 // ver mínimo (sin eliminar)
```

### Deque (Cola doble / Stack)

```java
Deque<Integer> deque = new ArrayDeque<>();
deque.offerFirst(val);  deque.offerLast(val);   // insertar
deque.pollFirst();      deque.pollLast();        // extraer
deque.peekFirst();      deque.peekLast();        // ver sin extraer

// Usarlo como Stack (LIFO)
deque.push(val);  // = offerFirst
deque.pop();      // = pollFirst
deque.peek();     // = peekFirst
```

---

## 🔡 Strings

```java
String s = "Hello World";
s.length();
s.charAt(i);
s.substring(from);              // [from, end)
s.substring(from, to);          // [from, to)
s.indexOf("lo");                // -1 si no existe
s.lastIndexOf("l");
s.contains("World");
s.startsWith("He");
s.endsWith("ld");
s.toLowerCase();  s.toUpperCase();
s.trim();          s.strip();   // strip() maneja Unicode mejor (Java 11+)
s.replace("l", "L");
s.replaceAll("[aeiou]", "*");   // con regex
s.split(" ");                   // String[]
s.split(",", -1);               // -1 preserva trailing empty strings
String.join("-", "a", "b", "c");         // "a-b-c"
String.join(", ", list);                 // desde Collection
String.format("Val: %d, Str: %s", n, s);
s.toCharArray();                          // char[]
String.valueOf(42);                       // int → String
Integer.parseInt("42");                   // String → int
Integer.parseInt("FF", 16);              // hexadecimal → int
Character.isDigit(c);
Character.isLetter(c);
Character.isLetterOrDigit(c);
Character.toLowerCase(c);

// StringBuilder — cuando concatenas en loop
StringBuilder sb = new StringBuilder();
sb.append("hola");
sb.append(" ").append("mundo");
sb.insert(0, "INICIO: ");
sb.reverse();
sb.toString();
sb.length();
sb.deleteCharAt(i);
sb.delete(from, to);
sb.charAt(i);
sb.setCharAt(i, 'X');
```

---

## 🌊 Stream API

```java
List<Integer> nums = List.of(1, 2, 3, 4, 5, 6);

// Filtrar
nums.stream().filter(n -> n % 2 == 0).collect(Collectors.toList());

// Transformar
nums.stream().map(n -> n * 2).collect(Collectors.toList());

// Aplanar
List<List<Integer>> nested = ...;
nested.stream().flatMap(Collection::stream).collect(Collectors.toList());

// Reducir
int sum = nums.stream().reduce(0, Integer::sum);
int product = nums.stream().reduce(1, (a, b) -> a * b);

// Terminal: encontrar
Optional<Integer> first = nums.stream().filter(n -> n > 3).findFirst();
boolean any = nums.stream().anyMatch(n -> n > 5);
boolean all = nums.stream().allMatch(n -> n > 0);
boolean none = nums.stream().noneMatch(n -> n < 0);

// Terminal: estadísticas
long count = nums.stream().filter(n -> n > 3).count();
Optional<Integer> max = nums.stream().max(Comparator.naturalOrder());
Optional<Integer> min = nums.stream().min(Integer::compareTo);
IntSummaryStatistics stats = nums.stream().mapToInt(Integer::intValue).summaryStatistics();
double avg = nums.stream().mapToInt(Integer::intValue).average().orElse(0.0);

// Collect a mapa
Map<Boolean, List<Integer>> partitioned =
    nums.stream().collect(Collectors.partitioningBy(n -> n % 2 == 0));

Map<Integer, List<String>> grouped =
    words.stream().collect(Collectors.groupingBy(String::length));

Map<String, Long> frequency =
    words.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting()));

// Sorted
nums.stream().sorted().collect(Collectors.toList());
nums.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

// Distinct, limit, skip
nums.stream().distinct().limit(3).skip(1).collect(Collectors.toList());

// String joining
String result = words.stream().collect(Collectors.joining(", ", "[", "]"));

// Desde array de primitivos
IntStream.range(0, 10)           // [0, 10)
IntStream.rangeClosed(1, 10)     // [1, 10]
Arrays.stream(intArray).sum()
```

---

## 🧩 Optional

```java
Optional<String> opt = Optional.of("value");         // ⚠️ NPE si null
Optional<String> opt = Optional.ofNullable(maybeNull); // seguro
Optional<String> opt = Optional.empty();

opt.isPresent();          // true si tiene valor
opt.isEmpty();            // Java 11+
opt.get();                // ⚠️ lanza excepción si empty
opt.orElse("default");
opt.orElseGet(() -> computeDefault());
opt.orElseThrow(() -> new IllegalStateException("No value"));
opt.map(String::toUpperCase);
opt.filter(s -> s.length() > 3);
opt.ifPresent(System.out::println);
opt.flatMap(s -> Optional.of(s.trim())); // cuando map devolvería Optional<Optional<T>>
```

---

## 🔢 Math y Números

```java
Math.abs(-5);               // 5
Math.max(a, b);
Math.min(a, b);
Math.pow(base, exp);        // double
Math.sqrt(n);               // double
Math.floor(3.7);            // 3.0
Math.ceil(3.2);             // 4.0
Math.round(3.5);            // 4 (long)
Math.log(n);                // ln
Math.log10(n);
Integer.MAX_VALUE;          // 2_147_483_647
Integer.MIN_VALUE;          // -2_147_483_648
Long.MAX_VALUE;             // 9_223_372_036_854_775_807

// ⚠️ FINANZAS: NUNCA usar double para dinero
// ✅ SIEMPRE BigDecimal
BigDecimal price = new BigDecimal("19.99");           // ✅ from String
BigDecimal tax = new BigDecimal("0.16");
BigDecimal total = price.multiply(tax).add(price);
total.setScale(2, RoundingMode.HALF_UP);              // redondeo financiero
price.compareTo(other);   // -1, 0, 1 — NO usar .equals() con BigDecimal (escala distinta)

// Módulo / paridad
n % 2 == 0  // par
n % 2 != 0  // impar
Math.floorMod(n, m)  // módulo siempre positivo (útil con negativos)
```

---

## 📊 Comparators

```java
// Desde Java 8+
Comparator<Person> byAge = Comparator.comparing(Person::getAge);
Comparator<Person> byAgeDesc = Comparator.comparing(Person::getAge).reversed();
Comparator<Person> byLastThenFirst = Comparator
    .comparing(Person::getLastName)
    .thenComparing(Person::getFirstName);

// Con nulls
Comparator<Person> nullFirst = Comparator.nullsFirst(Comparator.comparing(Person::getName));

// Ordenar lista de objetos
list.sort(Comparator.comparing(Person::getAge));
list.sort((a, b) -> Integer.compare(a.getAge(), b.getAge()));

// ⚠️ NUNCA: (a, b) -> a.getAge() - b.getAge() → overflow con negativos
// ✅ SIEMPRE: Integer.compare(a.getAge(), b.getAge())
```

---

## 🔍 Patrones de Código Frecuentes

### Frecuencia de elementos

```java
Map<Character, Integer> freq = new HashMap<>();
for (char c : s.toCharArray()) freq.merge(c, 1, Integer::sum);

// Con Streams
Map<String, Long> freq = list.stream()
    .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
```

### Sliding Window (ventana fija)

```java
int windowSum = 0;
for (int i = 0; i < k; i++) windowSum += arr[i];
int maxSum = windowSum;
for (int i = k; i < arr.length; i++) {
    windowSum += arr[i] - arr[i - k];
    maxSum = Math.max(maxSum, windowSum);
}
```

### Two Pointers

```java
int left = 0, right = arr.length - 1;
while (left < right) {
    int sum = arr[left] + arr[right];
    if (sum == target) { /* found */ break; }
    else if (sum < target) left++;
    else right--;
}
```

### Binary Search manual

```java
int left = 0, right = arr.length - 1;
while (left <= right) {
    int mid = left + (right - left) / 2;  // ⚠️ no (left+right)/2 → overflow
    if (arr[mid] == target) return mid;
    else if (arr[mid] < target) left = mid + 1;
    else right = mid - 1;
}
return -1;
```

---

## 🧪 JUnit 5 Quick Reference

```java
// Assertions básicas
assertEquals(expected, actual);
assertEquals(expected, actual, "mensaje de error");
assertNotEquals(a, b);
assertTrue(condition);
assertFalse(condition);
assertNull(obj);
assertNotNull(obj);
assertArrayEquals(expected, actual);          // arrays primitivos
assertIterableEquals(expected, actual);        // colecciones

// Excepciones
assertThrows(IllegalArgumentException.class, () -> method(badInput));
Exception ex = assertThrows(RuntimeException.class, () -> ...);
assertEquals("mensaje esperado", ex.getMessage());

// Múltiples assertions sin parar en la primera falla
assertAll(
    () -> assertEquals(1, result.getA()),
    () -> assertEquals(2, result.getB()),
    () -> assertTrue(result.isValid())
);

// Tests parametrizados
@ParameterizedTest
@ValueSource(ints = {1, 2, 3, 10, -1})
void test(int input) { ... }

@ParameterizedTest
@CsvSource({"1, true", "2, false", "-1, false"})
void test(int input, boolean expected) { ... }
```

---

## 🔑 Java 17 Sintaxis Moderna

```java
// Records (DTO inmutable)
record Point(int x, int y) {}
Point p = new Point(3, 4);
p.x(); p.y(); // getters automáticos

// Text blocks
String json = """
    {
        "name": "Brian",
        "age": 25
    }
    """;

// Switch expression
String result = switch (day) {
    case MONDAY, FRIDAY -> "Working";
    case SATURDAY, SUNDAY -> "Weekend";
    default -> "Midweek";
};

// Pattern matching instanceof
if (obj instanceof String s) {
    System.out.println(s.toUpperCase()); // s ya es String, sin casting
}

// var (inferencia de tipo local)
var list = new ArrayList<String>();  // type inferred
```

---

_Última actualización: Feb 2026 — Java 17 LTS_
