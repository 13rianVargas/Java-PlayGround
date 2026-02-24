/**
 * ============================================================
 *  03 — SLIDING WINDOW — Ejercicios
 * ============================================================
 *
 *  Completa cada método. Todos deben resolverse con el patrón
 *  de ventana deslizante para lograr O(n).
 *
 *  Compila: javac src/*.java -d bin/
 *  Ejecuta: java -cp bin/ Exercises
 * ============================================================
 */

import java.util.*;

public class Exercises {

    /**
     * EJERCICIO 1: Suma máxima de K elementos consecutivos.
     *
     * Dado un array de enteros y un tamaño de ventana K,
     * retorna la suma máxima de K elementos consecutivos.
     *
     * Ejemplo: arr=[2, 1, 5, 1, 3, 2], k=3 → 9 (5+1+3)
     *
     * @param arr array de enteros
     * @param k   tamaño de la ventana
     * @return suma máxima de K consecutivos
     */
    public static int maxSumSubarray(int[] arr, int k) {
        // TODO: Implementa este método con sliding window O(n)
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 2: Longitud de la substring más larga sin caracteres repetidos.
     *
     * Dado un string, encuentra la longitud de la substring más larga
     * que no contiene caracteres duplicados.
     *
     * Ejemplo: "abcabcbb" → 3 ("abc")
     * Ejemplo: "bbbbb"    → 1 ("b")
     * Ejemplo: "pwwkew"   → 3 ("wke")
     *
     * @param s el string de entrada
     * @return longitud de la substring más larga sin repetidos
     */
    public static int longestUniqueSubstring(String s) {
        // TODO: Implementa con ventana variable + HashSet
        throw new UnsupportedOperationException("Implementa este método.");
    }
 
    /**
     * EJERCICIO 3: Promedio móvil (Moving Average).
     *
     * Dado un array de precios diarios y un tamaño de ventana K,
    
     * retorna un array con el promedio móvil.
     * El array resultante tiene longitud (prices.length - k + 1).
     *
     * Ejemplo: prices=[10, 20, 30, 40, 50], k=3
     * → [20.0, 30.0, 40.0]
     *
     * @param prices precios diarios
     * @param k      tamaño de ventana
     * @return array de promedios móviles
     */
    public static double[] movingAverage(double[] prices, int k) {
        // TODO: Implementa con sliding window O(n)
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 4: ¿Contiene duplicados dentro de distancia K?
     *
     * Dado un array de enteros y un entero K, determina si existen
     * s índices i, j tales que arr[i] == arr[j] y |i - j| <= k.
     *
     * Ejemplo: arr=[1,2,3,1], k=3    → true  (índices 0 y 3)
     * Ejemplo: arr=[1,2,3,1,2,3], k=2 → false
     *
     * @param arr array de enteros
     * @param k   distancia máxima
     * @return true si hay duplicado dentro de distancia K
     */
    public static boolean containsNearbyDuplicate(int[] arr, int k) {
        // TODO: Implementa con ventana de tamaño K + HashSet
        throw new UnsupportedOperationException("Implementa este método.");
    }

    
    /**
     * EJERCICIO 5: Número de subarrays con suma exacta K.
     *
     * Dado un array de enteros positivos y un target K,
     * cuenta cuántos subarrays contiguos suman exactamente K.
     * 
     * mplo: arr=[1, 1, 1], k=2 → 2 ([1,1] en posiciones 0-1 y 1-2)
     * Ejemplo: arr=[1, 2, 3], k=3 → 2 ([1,2] y [3])
     *
     * a: usa prefix sum + HashMap para O(n).
     * 
     * @param arr array de enteros
     * @param k   suma objetivo
     * turn número de subarrays con suma K
     */
    public static int subarraySum(int[] arr, int k) {
        // TODO: Implementa con prefix sum + HashMap
        throw new UnsupportedOperationException("Implementa este método.");
    }


    // ─── Runner de verificación ───
    public static void main(String[] args) {
        System.out.println("=== SLIDING WINDOW — Ejercicios ===\n");

        // Test Ejercicio 1
        try {
            int r1 = maxSumSubarray(new int[]{2, 1, 5, 1, 3, 2}, 3);
            System.out.println("Ej1 maxSumSubarray: " + r1 + (r1 == 9 ? " ✅" : " ❌ esperado 9"));
        } catch (Exception e) {
            System.out.println("Ej1 maxSumSubarray: ⬜ No implementado");
        }

        // Test Ejercicio 2
        try {
            int r2 = longestUniqueSubstring("abcabcbb");
            System.out.println("Ej2 longestUniqueSubstring: " + r2 + (r2 == 3 ? " ✅" : " ❌ esperado 3"));
        } catch (Exception e) {
            System.out.println("Ej2 longestUniqueSubstring: ⬜ No implementado");
        }

        // Test Ejercicio 3
        try {
            double[] r3 = movingAverage(new double[]{10, 20, 30, 40, 50}, 3);
            System.out.println("Ej3 movingAverage: " + Arrays.toString(r3)
                + (r3.length == 3 && Math.abs(r3[0] - 20.0) < 0.01 ? " ✅" : " ❌"));
        } catch (Exception e) {
            System.out.println("Ej3 movingAverage: ⬜ No implementado");
        }

        // Test Ejercicio 4
        try {
            boolean r4 = containsNearbyDuplicate(new int[]{1, 2, 3, 1}, 3);
            System.out.println("Ej4 containsNearbyDuplicate: " + r4 + (r4 ? " ✅" : " ❌ esperado true"));
        } catch (Exception e) {
            System.out.println("Ej4 containsNearbyDuplicate: ⬜ No implementado");
        }

        // Test Ejercicio 5
        try {
            int r5 = subarraySum(new int[]{1, 1, 1}, 2);
            System.out.println("Ej5 subarraySum: " + r5 + (r5 == 2 ? " ✅" : " ❌ esperado 2"));
        } catch (Exception e) {
            System.out.println("Ej5 subarraySum: ⬜ No implementado");
        }
    }
}
