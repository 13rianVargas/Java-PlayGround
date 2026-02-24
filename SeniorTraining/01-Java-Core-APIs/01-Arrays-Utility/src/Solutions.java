import java.util.Arrays;

/**
 * ════════════════════════════════════════════════════════════
 *  SOLUCIONES — Arrays Utility
 * ════════════════════════════════════════════════════════════
 *
 * ⚠️  MIRA ESTO SOLO DESPUÉS DE HABER INTENTADO LOS EJERCICIOS
 *
 * Cada solución incluye:
 *
 *   - La implementación
 *
 *   - Explicación de la decisión de diseño
 *   - Complejidad temporal y espacial
 */
public class Solutions {

    // ─────────────────────────────────────────────────────────────────────
    //
    // SOLUCIÓN 1a — existsLinear: O(n) — fuerza bruta
    //
    // ─────────────────────────────────────────────────────────────────────
    static boolean existsLinear(int[] ints, int k) {
        for (int n : ints) {
            if (n == k) return true;
        }
        return false;
    }
    // Complejidad: O(n) tiempo, O(1) espacio
    // Correcto ✅ pero NO óptimo para arrays grandes.
    // Un Mid escribiría esto. Un Senior va más allá.

    // ─────────────────────────────────────────────────────────────────────
    // SOLUCIÓN 1b — existsBinary: O(log n) — la respuesta Senior
    // ─────────────────────────────────────────────────────────────────────────────
    static boolean existsBinary(int[] ints, int k) {
        return Arrays.binarySearch(ints, k) >= 0;
    }

    // Complejidad: O(log n) tiempo, O(1) espacio
    //
    // ¿Por qué funciona?
    //   - El enunciado garantiza que ints está en ORDEN ASCENDENTE → precondición de binarySearch ✅
    //   - El enunciado garantiza que ints nunca es null ✅
    //   - binarySearch retorna >= 0 si encontró, negativo si no → convertimos a boolean
    //
    // ¿Es importante el edge case de array vacío?
    //   Arrays.binarySearch(new int[0], 5) → retorna -1 (negativo) → false ✅
    //   No necesitamos chequeo extra.
    //
    // Esta ES la respuesta que diferencia un Mid de un Senior en esta prueba.


    // ─────────────────────────────────────────────────────────────────────
    // SOLUCIÓN 2 — topK: O(n log n)
    // ─────────────────────────────────────────────────────────────────────
    static int[] topK(int[] arr, int k) {
        // 1. Copiar para no modificar el original
        int[] sorted = Arrays.copyOf(arr, arr.length);
        // 2. Ordenar ascendente
        Arrays.sort(sorted);
        // 3. Tomar los últimos k elementos (los más grandes)
        int[] result = Arrays.copyOfRange(sorted, sorted.length - k, sorted.length);
        // 4. Invertir para orden descendente
        for (int i = 0, j = result.length - 1; i < j; i++, j--) {
            int tmp = result[i]; result[i] = result[j]; result[j] = tmp;
        }
        return result;
    }
    // Complejidad: O(n log n) tiempo, O(n) espacio
    //
    // Alternativa con PriorityQueue:
    //   PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    //   for (int n : arr) {
    //       minHeap.offer(n);
    //       if (minHeap.size() > k) minHeap.poll();
    //   }
    //   // El heap contiene los k más grandes, extraer en orden inverso


    // ─────────────────────────────────────────────────────────────────────
    // SOLUCIÓN 3 — insertionPosition: O(log n)
    // ─────────────────────────────────────────────────────────────────────
    static int insertionPosition(int[] arr, int target) {
        int result = Arrays.binarySearch(arr, target);
        // Si result < 0, no se encontró:
        // result = -(insertion_point) - 1
        // insertion_point = -(result) - 1 = -(result + 1)
        return result >= 0 ? result : -(result + 1);
    }
    // Complejidad: O(log n)
    //
    // Desglose matemático:
    //   binarySearch retorna: -(insertionPoint) - 1
    //   Queremos: insertionPoint
    //   Sea r = -(ip) - 1
    //   -r = ip + 1
    //   ip = -r - 1 = -(r + 1)
    //
    // Ejemplo: arr=[1,3,5,6], target=2
    //   binarySearch → -2 (insertion point sería 1)
    //   -((-2) + 1) = -(-1) = 1 ✅


    // ─────────────────────────────────────────────────────────────────────
    // SOLUCIÓN 4 — median: O(n log n)
    // ─────────────────────────────────────────────────────────────────────
    static double median(int[] arr) {
        // COPIAR antes de sort — no modificar el original
        int[] sorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(sorted);
        int n = sorted.length;
        if (n % 2 == 1) {
            return sorted[n / 2];                                // impar → elemento central
        } else {
            return (sorted[n / 2 - 1] + (double) sorted[n / 2]) / 2.0;  // par → promedio
        }
    }
    // Complejidad: O(n log n) tiempo, O(n) espacio (la copia)
    //
    // ⚠️ Error común: modificar arr directamente.
    //    Arrays.sort(arr) modifica IN-PLACE. Siempre copia antes.
    //
    // ⚠️ Error de casting:
    //    (sorted[n/2 - 1] + sorted[n/2]) / 2    → si ambos son int, división entera
    //    (sorted[n/2 - 1] + (double) sorted[n/2]) / 2.0  → resultado double ✅


    // ─────────────────────────────────────────────────────────────────────
    // SOLUCIÓN 5a — mergeSorted SIMPLE: O((n+m) log(n+m))
    // ─────────────────────────────────────────────────────────────────────
    static int[] mergeSortedSimple(int[] a, int[] b) {
        int[] result = Arrays.copyOf(a, a.length + b.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        Arrays.sort(result);
        return result;
    }
    // Correcto. Un Mid lo resolvería así.

    // ─────────────────────────────────────────────────────────────────────
    // SOLUCIÓN 5b — mergeSorted ÓPTIMO con Two Pointers: O(n+m)
    // ─────────────────────────────────────────────────────────────────────
    static int[] mergeSorted(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;

        // Comparar elemento a elemento de ambos arrays
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) result[k++] = a[i++];
            else               result[k++] = b[j++];
        }
        // Copiar los sobrantes (solo uno de los dos while ejecutará)
        while (i < a.length) result[k++] = a[i++];
        while (j < b.length) result[k++] = b[j++];

        return result;
    }
    // Complejidad: O(n+m) tiempo, O(n+m) espacio
    // Esta es la solución Senior. Aprovecha que AMBOS arrays ya están ordenados.


    // ─────────────────────────────────────────────────────────────────────
    // MAIN — Verificar todas las soluciones
    // ─────────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("═══ Ejercicio 1 ═══");
        int[] sorted = {-9, 14, 37, 102};
        System.out.println(existsBinary(sorted, 102));          // true
        System.out.println(existsBinary(sorted, 36));           // false
        System.out.println(existsBinary(new int[0], 5));        // false

        System.out.println("\n═══ Ejercicio 2 ═══");
        System.out.println(Arrays.toString(topK(new int[]{3, 1, 4, 1, 5, 9, 2, 6}, 3))); // [9, 6, 5]

        System.out.println("\n═══ Ejercicio 3 ═══");
        System.out.println(insertionPosition(new int[]{1, 3, 5, 6}, 2));  // 1
        System.out.println(insertionPosition(new int[]{1, 3, 5, 6}, 7));  // 4
        System.out.println(insertionPosition(new int[]{1, 3, 5, 6}, 0));  // 0

        System.out.println("\n═══ Ejercicio 4 ═══");
        System.out.println(median(new int[]{3, 1, 4, 1, 5}));   // 3.0
        System.out.println(median(new int[]{3, 1, 4, 2}));       // 2.5

        System.out.println("\n═══ Ejercicio 5 ═══");
        System.out.println(Arrays.toString(mergeSorted(new int[]{1,3,5}, new int[]{2,4,6})));  // [1,2,3,4,5,6]
        System.out.println(Arrays.toString(mergeSorted(new int[0], new int[]{1,2})));           // [1,2]
    }
}
