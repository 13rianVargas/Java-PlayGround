import java.util.*;
import java.util.stream.Collectors;

/**
 * EJERCICIOS — Collections Deep
 * Formato CodinGame/HackerRank: métodos estáticos, tú implementas el cuerpo.
 */
public class Exercises {

    // ─────────────────────────────────────────────────────────────────────
    // EJERCICIO 1 — Primer carácter no repetido
    //
    // Dado un String, retorna el PRIMER carácter que aparece exactamente
    // una vez. Si no existe tal carácter, retorna '\0'.
    //
    // Ejemplo: "aabbcdeef" → 'c'
    // Ejemplo: "aabb"       → '\0' (no existe carácter único)
    //
    // 💡 Pista: Cuenta frecuencias, luego recorre el string para
    //    encontrar el primero con frecuencia 1.
    //    ¿Qué estructura preserva el orden de inserción?
    // ─────────────────────────────────────────────────────────────────────
    public static char firstUniqueChar(String s) {
        // TODO
        return '\0';
    }

    // ─────────────────────────────────────────────────────────────────────
    // EJERCICIO 2 — Top-K palabras más frecuentes
    //
    // Dada una lista de palabras, retorna las k palabras más frecuentes
    // en orden de mayor a menor frecuencia.
    // En caso de empate en frecuencia, ordena alfabéticamente.
    //
    // Ejemplo: ["apple","banana","apple","cherry","banana","apple"], k=2
    //   → ["apple", "banana"]
    //
    // 💡 Pista: Cuenta con HashMap, luego ordena con
    //    Comparator.comparingByValue().reversed().thenComparing()
    // ─────────────────────────────────────────────────────────────────────
    static List<String> topKFrequent(List<String> words, int k) {
        // TODO
        return Collections.emptyList();
    }

    // ─────────────────────────────────────────────────────────────────────
    // EJERCICIO 3 — Rango de puntajes
    //
    // Dado un conjunto de nombre→puntaje y dos enteros min y max (inclusive),
    // retorna los nombres que tienen puntaje dentro de [min, max],
    // ordenados por puntaje descendente.
    //
    // Ejemplo: {"Alice":85, "Bob":92, "Charlie":78, "Diana":95, "Eve":68}, [80, 93]
    //   → ["Bob", "Alice"]
    //
    // 💡 Pista: TreeMap tiene tailMap(min) y subMap(min, max) para rangos eficientes.
    //    Construye TreeMap<Integer, String> para ordenar por puntaje.
    //    ¿Qué pasa si dos personas tienen el mismo puntaje?
    // ─────────────────────────────────────────────────────────────────────
    static List<String> studentsInRange(Map<String, Integer> scores, int min, int max) {
        // TODO
        return Collections.emptyList();
    }

    // ─────────────────────────────────────────────────────────────────────
    // EJERCICIO 4 — Paréntesis balanceados
    //
    // Dado un String con paréntesis, corchetes y llaves, retorna true si
    // están correctamente balanceados, false en caso contrario.
    //
    // Ejemplo: "()[]{}" → true
    // Ejemplo: "([)]"     → false
    // Ejemplo: "{[]"      → false
    // Ejemplo: ""         → true
    //
    // 💡 Pista: usa Deque como Stack. Cada vez que veas apertura, push.
    //           Cada vez que veas cierre, pop y verifica que coincida.
    // ─────────────────────────────────────────────────────────────────────
    static boolean isBalanced(String s) {
        // TODO
        return false;
    }

    // ─────────────────────────────────────────────────────────────────────
    // EJERCICIO 5 — Mediana mantenida con dos heaps
    //
    // Implementa una clase MedianFinder que mantiene la mediana de un
    // stream de números al agregar uno por uno.
    //
    // Operaciones:
    //   addNumber(int n): agrega un número al stream
    //   getMedian():      retorna la mediana actual como double
    //
    // Ejemplo:
    //   addNumber(1) → mediana = 1.0
    //   addNumber(2) → mediana = 1.5
    //   addNumber(3) → mediana = 2.0
    //   addNumber(4) → mediana = 2.5
    //
    // ⏱️ addNumber debe ser O(log n), getMedian debe ser O(1)
    //
    // 💡 Pista: divide el stream en dos heaps:
    //   - maxHeap (mitad inferior): PriorityQueue reverseOrder
    //   - minHeap (mitad superior): PriorityQueue natural
    //   Mantén |maxHeap.size() - minHeap.size()| <= 1
    //   La mediana es la raíz del heap mayor (si impares) o el promedio (si pares).
    // ─────────────────────────────────────────────────────────────────────
    static class MedianFinder {
        // TODO: declarar las dos PriorityQueue

        void addNumber(int n) {
            // TODO
        }

        double getMedian() {
            // TODO
            return 0.0;
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // MAIN
    // ─────────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("═══ Ejercicio 1: firstUniqueChar ═══");
        System.out.println(firstUniqueChar("aabbcdeef"));  // c
        System.out.println(firstUniqueChar("aabb"));       // \0
        System.out.println(firstUniqueChar("z"));          // z

        System.out.println("\n═══ Ejercicio 2: topKFrequent ═══");
        System.out.println(topKFrequent(Arrays.asList("apple","banana","apple","cherry","banana","apple"), 2));
        // [apple, banana]

        System.out.println("\n═══ Ejercicio 3: studentsInRange ═══");
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice", 85); scores.put("Bob", 92);
        scores.put("Charlie", 78); scores.put("Diana", 95); scores.put("Eve", 68);
        System.out.println(studentsInRange(scores, 80, 93));  // [Bob, Alice]

        System.out.println("\n═══ Ejercicio 4: isBalanced ═══");
        System.out.println(isBalanced("()[]{}")); // true
        System.out.println(isBalanced("([{]})"));  // false
        System.out.println(isBalanced(""));        // true

        System.out.println("\n═══ Ejercicio 5: MedianFinder ═══");
        MedianFinder mf = new MedianFinder();
        mf.addNumber(1); System.out.println(mf.getMedian()); // 1.0
        mf.addNumber(2); System.out.println(mf.getMedian()); // 1.5
        mf.addNumber(3); System.out.println(mf.getMedian()); // 2.0
        mf.addNumber(4); System.out.println(mf.getMedian()); // 2.5
    }
}
