/**
 * ============================================================
 *  03 — SLIDING WINDOW — Soluciones Anotadas
 * ============================================================
 */

import java.util.*;

public class Solutions {

    /**
     * EJERCICIO 1: Suma máxima de K elementos consecutivos.
     *
     * ESTRATEGIA: Ventana fija de tamaño K.
     * - Calcular suma inicial de los primeros K elementos.
     * - Deslizar: sumar el nuevo, restar el que sale.
     *
     * Complejidad: O(n) tiempo, O(1) espacio
     */
    public static int maxSumSubarray(int[] arr, int k) {
        if (arr == null || arr.length < k || k <= 0) return 0;

        // Suma de la primera ventana
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }

        int maxSum = windowSum;

        // Deslizar: entra arr[i], sale arr[i-k]
        for (int i = k; i < arr.length; i++) {
           windowSum += arr[i] - arr[i - k];
            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum;
    }


    /**
     * EJERCICIO 2: Longest Substring Without Repeating Characters.
     *
     * ESTRATEGIA: Ventana variable con HashSet.
     * - right avanza siempre.
     * - Si hay duplicado, left avanza hasta eliminar el duplicado.
     *
     * Complejidad: O(n) tiempo, O(min(n, charset)) espacio
     */
    public static int longestUniqueSubstring(String s) {
        if (s == null || s.isEmpty()) return 0;

        Set<Character> window = new HashSet<>();
        int left = 0;
        int maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            while (window.contains(s.charAt(right))) {
                window.remove(s.charAt(left));
                left++;
            }
            window.add(s.charAt(right));
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }


    /**
     * EJERCICIO 3: Moving Average.
     *
     * ESTRATEGIA: Ventana fija, mantener suma acumulada.
     *
     * Complejidad: O(n) tiempo, O(n-k+1) espacio (resultado)
     */
    public static double[] movingAverage(double[] prices, int k) {
        if (prices == null || prices.length < k || k <= 0) return new double[0];

        double[] result = new double[prices.length - k + 1];
        double windowSum = 0;

        for (int i = 0; i < k; i++) {
            windowSum += prices[i];
        }
        result[0] = windowSum / k;

        for (int i = k; i < prices.length; i++) {
            windowSum += prices[i] - prices[i - k];
            result[i - k + 1] = windowSum / k;
        }

        return result;
    }


    /**
     * EJERCICIO 4: Contains Nearby Duplicate.
     *
     * ESTRATEGIA: Mantener un HashSet como ventana de tamaño K.
     * - Si el nuevo elemento ya está en el set → true.
     * - Si el set crece más de K, eliminar el más antiguo.
     *
     * Complejidad: O(n) tiempo, O(k) espacio
     */
    public static boolean containsNearbyDuplicate(int[] arr, int k) {
        if (arr == null || k <= 0) return false;

        Set<Integer> window = new HashSet<>();

        for (int i = 0; i < arr.length; i++) {
            if (window.contains(arr[i])) return true;
            window.add(arr[i]);
            if (window.size() > k) {
                window.remove(arr[i - k]); // sale el más antiguo
            }
        }

        return false;
    }


    /**
     * EJERCICIO 5: Subarray Sum Equals K.
     *
     * ESTRATEGIA: Prefix Sum + HashMap.
     * - prefixSum[i] = sum(arr[0..i])
     * - Si prefixSum[j] - prefixSum[i] == k → subarray [i+1..j] suma K
     * - Buscar en el HashMap cuántos prefix sums previos son (currentSum - k)
     *
     * Complejidad: O(n) tiempo, O(n) espacio
     *
     * NOTA SENIOR: Este NO es sliding window puro, pero usa la misma
     * filosofía de "mantener estado mientras recorremos". Se incluye
     * porque aparece frecuentemente junto a problemas de ventana.
     */
    public static int subarraySum(int[] arr, int k) {
        if (arr == null) return 0;

        Map<Integer, Integer> prefixCount = new HashMap<>();
        prefixCount.put(0, 1); // base case: suma vacía
        int currentSum = 0;
        int count = 0;

        for (int num : arr) {
            currentSum += num;
            // ¿Cuántos prefix sums previos hacen que currentSum - prefix == k?
            count += prefixCount.getOrDefault(currentSum - k, 0);
            prefixCount.merge(currentSum, 1, Integer::sum);
        }

        return count;
    }

    // ─── Verificación ───
    public static void main(String[] args) {
        System.out.println("=== SLIDING WINDOW — Soluciones ===\n");

        System.out.println("Ej1: " + maxSumSubarray(new int[]{2, 1, 5, 1, 3, 2}, 3));    // 9
        System.out.println("Ej2: " + longestUniqueSubstring("abcabcbb"));                  // 3
        System.out.println("Ej3: " + Arrays.toString(movingAverage(
                new double[]{10, 20, 30, 40, 50}, 3)));                                    // [20.0, 30.0, 40.0]
        System.out.println("Ej4: " + containsNearbyDuplicate(new int[]{1, 2, 3, 1}, 3));  // true
        System.out.println("Ej5: " + subarraySum(new int[]{1, 1, 1}, 2));                  // 2
    }
}