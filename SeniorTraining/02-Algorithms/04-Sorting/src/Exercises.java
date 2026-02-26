/**
 * ============================================================
 *  04 — SORTING + COMPARATORS — Ejercicios
 * ============================================================
 */

import java.util.*;
import java.util.stream.*;

public class Exercises {

    record Product(String name, String category, double price, int stock) {}

    /**
     * EJERCICIO 1: Ordenar productos por precio DESC, luego nombre ASC.
     *
     * Ejemplo:
     *   [("B", "X", 10, 5), ("A", "X", 10, 3), ("C", "Y", 20, 1)]
     *   → [("C", "Y", 20, 1), ("A", "X", 10, 3), ("B", "X", 10, 5)]
     */
    public static List<Product> sortByPriceThenName(List<Product> products) {
        // TODO: Usa Comparator.comparing con .reversed() y .thenComparing
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 2: K-th elemento más grande de un array.
     *
     * Ejemplo: arr=[3,2,1,5,6,4], k=2 → 5
     * Ejemplo: arr=[3,2,3,1,2,4,5,5,6], k=4 → 4
     *
     * Usa PriorityQueue (min-heap) de tamaño K para O(n log K).
     */
    public static int kthLargest(int[] arr, int k) {
        // TODO: Min-heap de tamaño K
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 3: Merge de dos arrays ordenados en uno solo ordenado.
     *
     * Ejemplo: a=[1,3,5], b=[2,4,6] → [1,2,3,4,5,6]
     * Ejemplo: a=[1,1], b=[2]       → [1,1,2]
     *
     * Complejidad objetivo: O(n+m) con two pointers, NO con sort.
     */
    public static int[] mergeSortedArrays(int[] a, int[] b) {
        // TODO: Two pointers merge
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /* 
     * EJERCICIO 4: Ordenar strings por longitud ASC, desempate alfabético.
     *
     * Ejemplo: ["banana", "kiwi", "fig", "apple", "pear"]
     * → ["fig", "kiwi", "pear", "apple", "banana"]
    
     */
    public static List<String> sortByLength(List<String> words) {
        // TODO: Comparator.comparingInt(String::length).thenComparing(...)
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 5: Organizar intervalos y encontrar conflictos.
     *
     * Dado un array de intervalos [inicio, fin], ordénalos por inicio
     * y retorna true si algún par de intervalos se solapa.
     *
     * Ejemplo: [[1,3],[2,6],[8,10],[15,18]] → true (1-3 y 2-6 se solapan)
     * Ejemplo: [[1,2],[3,4],[5,6]]          → false
     */
    public static boolean hasOverlap(int[][] intervals) {
        // TODO: Ordena por inicio, compara fin del anterior con inicio del siguiente
        throw new UnsupportedOperationException("Implementa este método.");
    }
 

    // ─── Runner ───
    public static void main(String[] args) {
        System.out.println("=== SORTING — Ejercicios ===\n");

        // Test 1
        try {
            var products = List.of(
                new Product("B", "X", 10, 5),
                new Product("A", "X", 10, 3),
                new Product("C", "Y", 20, 1)
            );
            var r1 = sortByPriceThenName(products);
    
            System.out.println("Ej1: " + r1.get(0).name()
                + (r1.get(0).name().equals("C") ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej1: ⬜ No implementado"); }

        // Test 2
        try {
            int r2 = kthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2);
            System.out.println("Ej2: " + r2 + (r2 == 5 ? " ✅" : " ❌ esperado 5"));
        } catch (Exception e) { System.out.println("Ej2: ⬜ No implementado"); }

        // Test 3
        try {
            int[] r3 = mergeSortedArrays(new int[]{1, 3, 5}, new int[]{2, 4, 6});
            System.out.println("Ej3: " + Arrays.toString(r3)
                + (Arrays.equals(r3, new int[]{1, 2, 3, 4, 5, 6}) ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej3: ⬜ No implementado"); }

        // Test 4
        try {
            var r4 = sortByLength(List.of("banana", "kiwi", "fig", "apple", "pear"));
            System.out.println("Ej4: " + r4.get(0)
                + (r4.get(0).equals("fig") ? " ✅" : " ❌ esperado fig"));
        } catch (Exception e) { System.out.println("Ej4: ⬜ No implementado"); }

        // Test 5
        try {
            boolean r5 = hasOverlap(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}});
            System.out.println("Ej5: " + r5 + (r5 ? " ✅" : " ❌ esperado true"));
        } catch (Exception e) { System.out.println("Ej5: ⬜ No implementado"); }
    }
}
