import java.util.*;
import java.util.stream.Collectors;

/**
 * Collections Deep \u2014 Demostraci\u00f3n de las estructuras de datos avanzadas.
 * La elecci\u00f3n correcta de colecci\u00f3n puede cambiar O(n) \u2192 O(log n) o O(1).
 */
public class Main {

    public static void main(String[] args) {
        demo_hashMap_patterns();
        demo_treeMap();
        demo_priorityQueue();
        demo_deque();
        demo_linkedHashMap();
    }

    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    // DEMO 1: HashMap \u2014 patrones de uso avanzado
    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    static void demo_hashMap_patterns() {
        System.out.println("\u2500\u2500\u2500 HashMap \u2500\u2500\u2500");

        // Patr\u00f3n 1: Contar frecuencias
        String[] words = {"apple", "banana", "apple", "cherry", "banana", "apple"};
        Map<String, Integer> freq = new HashMap<>();
        for (String w : words) {
            freq.merge(w, 1, Integer::sum);
            // equivalente a:
            // freq.put(w, freq.getOrDefault(w, 0) + 1);
        }
        System.out.println("Frecuencias: " + freq);

        // Patr\u00f3n 2: Agrupar elementos
        Map<Integer, List<String>> byLength = new HashMap<>();
        for (String w : words) {
            byLength.computeIfAbsent(w.length(), k -> new ArrayList<>()).add(w);
        }
        System.out.println("Por longitud: " + byLength);

        // Patr\u00f3n 3: getOrDefault vs get
        System.out.println("apple: " + freq.getOrDefault("apple", 0));
        System.out.println("mango: " + freq.getOrDefault("mango", 0));

        // Patr\u00f3n 4: Iterar sobre entradas ordenadas por valor
        freq.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(e -> System.out.println("  " + e.getKey() + ": " + e.getValue()));

        System.out.println();
    }

    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    // DEMO 2: TreeMap \u2014 ordenado por clave, ideal para rangos
    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    static void demo_treeMap() {
        System.out.println("\u2500\u2500\u2500 TreeMap \u2500\u2500\u2500");
        TreeMap<Integer, String> scores = new TreeMap<>();
        scores.put(92, "Alice");
        scores.put(85, "Bob");
        scores.put(78, "Charlie");
        scores.put(95, "Diana");
        scores.put(68, "Eve");

        System.out.println("Ordenado: " + scores);
        System.out.println("Mayor puntaje: " + scores.lastKey() + " \u2192 " + scores.get(scores.lastKey()));
        System.out.println("Menor puntaje: " + scores.firstKey() + " \u2192 " + scores.get(scores.firstKey()));

        // Puntajes >= 85
        System.out.println("Puntajes >= 85: " + scores.tailMap(85));

        // Rango [78, 92]
        System.out.println("Rango [78,92]: " + scores.subMap(78, true, 92, true));

        System.out.println();
    }

    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    // DEMO 3: PriorityQueue \u2014 heap, ideal para top-K y scheduling
    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    static void demo_priorityQueue() {
        System.out.println("\u2500\u2500\u2500 PriorityQueue \u2500\u2500\u2500");

        // Min-heap (por defecto)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int[] nums = {5, 1, 8, 3, 7, 2};
        for (int n : nums) minHeap.offer(n);

        System.out.print("Extrayendo en orden: ");
        while (!minHeap.isEmpty()) System.out.print(minHeap.poll() + " ");
        System.out.println();

        // Max-heap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int n : nums) maxHeap.offer(n);
        System.out.println("M\u00e1ximo: " + maxHeap.peek());  // 8

        // Top-3 m\u00e1s grandes (memory-efficient)
        int k = 3;
        PriorityQueue<Integer> topK = new PriorityQueue<>();
        for (int n : nums) {
            topK.offer(n);
            if (topK.size() > k) topK.poll();  // elimina el menor para mantener solo top-k
        }
        System.out.println("Top-3: " + topK);  // [5, 8, 7] (min-heap de los top 3)

        System.out.println();
    }

    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    // DEMO 4: Deque \u2014 como stack Y como queue
    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    static void demo_deque() {
        System.out.println("\u2500\u2500\u2500 Deque \u2500\u2500\u2500");
        Deque<Integer> deque = new ArrayDeque<>();

        // Como Stack (LIFO): historial de acciones, parseo de par\u00e9ntesis
        deque.push(1);
        deque.push(2);
        deque.push(3);
        System.out.println("Stack peek: " + deque.peek());  // 3
        System.out.println("Stack pop:  " + deque.pop());   // 3
        System.out.println("Stack pop:  " + deque.pop());   // 2

        deque.clear();
        // Como Queue (FIFO): BFS, procesamiento ordenado
        deque.offerLast(1);
        deque.offerLast(2);
        deque.offerLast(3);
        System.out.println("Queue poll: " + deque.pollFirst());  // 1
        System.out.println("Queue poll: " + deque.pollFirst());  // 2

        // Ventana deslizante m\u00e1xima (patr\u00f3n avanzado con Deque)
        System.out.println("  \u2192 Sliding window max demo:");
        int[] arr = {1, 3, -1, -3, 5, 3, 6, 7};
        int window = 3;
        Deque<Integer> windowDeque = new ArrayDeque<>();  // guarda \u00edndices
        for (int i = 0; i < arr.length; i++) {
            while (!windowDeque.isEmpty() && windowDeque.peekFirst() < i - window + 1)
                windowDeque.pollFirst();
            while (!windowDeque.isEmpty() && arr[windowDeque.peekLast()] < arr[i])
                windowDeque.pollLast();
            windowDeque.offerLast(i);
            if (i >= window - 1)
                System.out.print(arr[windowDeque.peekFirst()] + " ");
        }
        System.out.println();  // Expected: 3 3 5 5 6 7

        System.out.println();
    }

    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    // DEMO 5: LinkedHashMap \u2014 orden de inserci\u00f3n + entries recientes
    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    static void demo_linkedHashMap() {
        System.out.println("\u2500\u2500\u2500 LinkedHashMap \u2500\u2500\u2500");

        // Mantiene orden de inserci\u00f3n \u2014 \u00fatil para preservar el orden original
        Map<String, Integer> lhm = new LinkedHashMap<>();
        lhm.put("banana", 2);
        lhm.put("apple", 1);
        lhm.put("cherry", 3);
        System.out.println("Orden de inserci\u00f3n: " + lhm.keySet());
        // vs HashMap que no garantiza orden

        // LRU Cache con accessOrder=true
        Map<Integer, String> lru = new LinkedHashMap<>(16, 0.75f, true) {
            @Override protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
                return size() > 3;  // capacidad m\u00e1xima: 3 entradas
            }
        };
        lru.put(1, "A"); lru.put(2, "B"); lru.put(3, "C");
        lru.get(1);      // accede a 1 \u2192 lo mueve al frente
        lru.put(4, "D"); // expulsa el menos-recientemente-usado (2)
        System.out.println("LRU Cache (capacidad 3): " + lru);  // {3=C, 1=A, 4=D}

        System.out.println();
    }
}
