/**
 * ============================================================
 *  04 — SORTING + COMPARATORS — Soluciones Anotadas
 * ============================================================
 */

import java.util.*;
import java.util.stream.*;

public class Solutions {

    record Product(String name, String category, double price, int stock) {}

    /**
     * EJERCICIO 1: Ordenar por precio DESC, nombre ASC.
     *
     * Complejidad: O(n log n)
     * Clave: Comparator.comparingDouble().reversed().thenComparing()
     */
    public static List<Product> sortByPriceThenName(List<Product> products) {
        return products.stream()
                .sorted(Comparator.comparingDouble(Product::price).reversed()
                        .thenComparing(Product::name))
                .collect(Collectors.toList());
    }

    /**
     * EJERCICIO 2: K-th más grande.
     *
     * Complejidad: O(n log K) — más eficiente que O(n log n) sort
     * Clave: Min-heap de tamaño K. Al final, peek() es el K-th más grande.
     */
    public atic int kthLargest(int[] arr, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int val : arr) {
            minHeap.offer(val);
            if (minHeap.size() > k) {
                minHeap.poll(); // eliminar el menor → solo quedan los K mayores
            }
        }
        return minHeap.peek(); // el menor de los K mayores = K-th más grande
    }

    /**
     * EJERCICIO 3: Merge de arrays ordenados.
     *
     * Complejidad: O(n+m) tiempo, O(n+m) espacio
    
     * Clave: Two pointers, uno en cada array. Comparar y avanzar el menor.
     *
     * NOTA SENIOR: Este es el mismo algoritmo que usa MergeSort internamente.
     */
    public static int[] mergeSortedArrays(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        int i = 0, j = 0, idx = 0;

        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                result[idx++] = a[i++];
            } else {
                result[idx++] = b[j++];
            }
        }

        // Copiar los restantes
        while (i < a.length) result[idx++] = a[i++];
        while (j < b.length) result[idx++] = b[j++];

        return result;
    }

    /**
     * EJERCICIO 4: Sort por longitud, desempate alfabético.
     *
     * Complejidad: O(n log n)
     */
    public static List<String> sortByLength(List<String> words) {
        return words.stream()
                .sorted(Comparator.comparingInt(String::length)
                        .thenComparing(Comparator.naturalOrder()))
                .collect(Collectors.toList());
    }

    /**
     * EJERCICIO 5: Detectar solapamiento de intervalos.
     *
     * Complejidad: O(n log n) por el sort + O(n) recorrido = O(n log n)
     * Clave: Ordenar por inicio. Si interval[i].start < interval[i-1].end → overlap.
     */
    public static boolean hasOverlap(int[][] intervals) {
        if (intervals == null || intervals.length < 2) return false;

        // Ordenar por inicio
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][1]) {
                return true; // el inicio del actual cae dentro del anterior
            }
        }

        return false;
    }


    // ─── Verificación ───
    public static void main(String[] args) {
        System.out.println("=== SORTING — Soluciones ===\n");

        var products = List.of(
            new Product("B", "X", 10, 5),
            new Product("A", "X", 10, 3),
            new Product("C", "Y", 20, 1)
        );
        System.out.println("Ej1: " + sortByPriceAndName(products));

        System.out.println("Ej2: " + kthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
        System.out.println("Ej3: " + Arrays.toString(
                mergeSortedArrays(new int[]{1, 3, 5}, new int[]{2, 4, 6})));
        System.out.println("Ej4: " + sortByLength(
                List.of("banana", "kiwi", "fig", "apple", "pear")));
        System.out.println("Ej5: " + hasOverlap(
                new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}}));
    }
}
            
        

        
            
        
                
                
            

            

            
                
                
            