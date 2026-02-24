/**
 * ============================================================
 *  06 — DYNAMIC PROGRAMMING — Ejercicios
 * ============================================================
 */

import java.util.*;

public class Exercises {

    /**
     * EJERCICIO 1: Climbing Stairs.
     *
     * Estás subiendo una escalera de n escalones.
     * Puedes subir 1 o 2 escalones a la vez.
     *
     * Ejemplo: n=2 → 2 (1+1, 2) 
     * Ejemplo: n=5 → 8
     */
    public static int climbStairs(int n) {
        // TODO: DP bottom-up — dp[i] = dp[i-1] + dp[i-2]
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 2: Coin Change — mínimo de monedas.
     *
     * Dadas denominaciones de monedas y un monto objetivo,
     * retorna el mínimo número de monedas necesarias.
     * Si no es posible, retorna -1.
     *
     * Ejemplo: coins=[1,5,10,25], amount=36 → 3 (25+10+1)
     * Ejemplo: coins=[2], amount=3           → -1
     */
    public static int minCoins(int[] coins, int amount) {
        // TODO: DP — dp[i] = min(dp[i - coin] + 1) para cada moneda
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 3: Maximum Subarray Sum (Kadane's Algorithm).
     *
     * Dado un array de enteros (positivos y negativos),
     * encuentra la suma máxima de un subarray contiguo.
     *
     * Ejemplo: [-2, 1, -3, 4, -1, 2, 1, -5, 4] → 6 ([4,-1,2,1])
     * Ejemplo: [1]                               → 1
     * Ejemplo: [-1, -2, -3]                      → -1
     */
    public static int maxSubarraySum(int[] arr) {
        // TODO: Kadane's — mantener maxEndingHere y maxSoFar
        throw new UnsupportedOperationException("Implementa este método.");
    }

    
    /**
     * EJERCICIO 4: Longest Increasing Subsequence (LIS).
     *
     * Dada una secuencia de enteros, encuentra la longitud
     * de la subsecuencia creciente más larga (no necesariamente contigua).
     *
     * Ejemplo: [10, 9, 2, 5, 3, 7, 101, 18] → 4 (2, 3, 7, 101)
     * Ejemplo: [0, 1, 0, 3, 2, 3]           → 4 (0, 1, 2, 3)
     */
    public static int longestIncreasingSubsequence(int[] arr) {
        // TODO: DP O(n²) — dp[i] = max(dp[j] + 1) donde arr[j] < arr[i]
         throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 5: Can Sum (¿Es posible sumar al target?).
     *
     * Dados números positivos y un target, determina si es posible
     * formar el target sumando cualquier combinación de los números
     * (se puede repetir).
     *
     * Ejemplo: target=7, numbers=[2,3]   → true  (2+2+3)
     * Ejemplo: target=7, numbers=[5,3,4] → true  (3+4)
     * Ejemplo: target=7, numbers=[2,4]   → false
     */
    public static boolean canSum(int target, int[] numbers) {
        // TODO: DP — dp[i] = true si se puede formar i
        throw new UnsupportedOperationException("Implementa este método.");
    }

    // ─── Runner ───
    public static void main(String[] args) {
        System.out.println("=== DYNAMIC PROGRAMMING — Ejercicios ===\n");
    

        try {
         System.out.println("Ej1: " + climbStairs(5) + (climbStairs(5) == 8 ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej1: ⬜ No implementado"); }

        try {
            int r = minCoins(new int[]{1, 5, 10, 25}, 36);
            System.out.println("Ej2: " + r + (r == 3 ? " ✅" : " ❌ esperado 3"));
        } catch (Exception e) { System.out.println("Ej2: ⬜ No implementado"); }
 
        try {
            int r = maxSubarraySum(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
            System.out.println("Ej3: " + r + (r == 6 ? " ✅" : " ❌ esperado 6"));
        } catch (Exception e) { System.out.println("Ej3: ⬜ No implementado"); }

        try {
            int r = longestIncreasingSubsequence(new int[]{10, 9, 2, 5, 3, 7, 101, 18});
            System.out.println("Ej4: " + r + (r == 4 ? " ✅" : " ❌ esperado 4"));
        } catch (Exception e) { System.out.println("Ej4: ⬜ No implementado"); }

        try {
            System.out.println("Ej5: " + canSum(7, new int[]{2, 3})
                + (canSum(7, new int[]{2, 3}) ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej5: ⬜ No implementado"); }
    }
}
