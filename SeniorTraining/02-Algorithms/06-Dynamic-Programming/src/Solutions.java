/**
 * ============================================================
 *  06 — DYNAMIC PROGRAMMING — Soluciones Anotadas
 * ============================================================
 */

import java.util.*;

public class Solutions {

    /**
     * EJERCICIO 1: Climbing Stairs.
     *
     * INSIGHT: Es literalmente Fibonacci.
     * dp[1] = 1, dp[2] = 2, dp[n] = dp[n-1] + dp[n-2]
     *
     * Complejidad: O(n) tiempo, O(1) espacio (optimizado)
     */
    public static int climbStairs(int n) {
        if (n <= 2) return n;

        int prev2 = 1; // dp[i-2]
        int prev1 = 2; // dp[i-1]

        for (int i = 3; i <= n; i++) {
            int current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }
 
    /**
     * EJERCICIO 2: Coin Change.
     *
     * dp[i] = mínimas monedas para formar i.
     * dp[0] = 0 (base).
     * dp[i] = min(dp[i - coin] + 1) para cada moneda ≤ i.
     *
     * Complejidad: O(amount * coins) tiempo, O(amount) espacio
     */
    public static int minCoins(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1); // "infinito"
        dp[0] = 0;

    
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

                 > amount ? -1 : dp[amount];
    }

    /**
     * EJERCICIO 3: Maximum Subarray (Kadane's).
     *
     * INSIGHT: En cada posición, decidimos:
     *   - Extender el subarray
            actual (maxEnding
        e

         - Empezar uno nuevo
            (arr[i])
        

        
            
        

        omplejidad: O(n) tiempo, O(1) es
            acio
        
     *
     * NOTA SENIOR: Este es EL algoritmo O(n) más pedido en pruebas.
     */
    public static int maxSubarraySum(int[] arr) {
        if (arr == null || arr.length == 0)
            throw new IllegalArgumentException("Array vacío");

        int maxEndingHere = arr[0];
        int maxSoFar = arr[0];

        for (int i = 1; i < arr.length; i++) {
            maxEndingHere = Math.max(arr[i], maxEndingHere + arr[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }

        return maxSoFar;
    }

    /**
     * EJERCICIO 4: Longest Increasing Subsequence.
     *
     * dp[i] = longitud de la LIS que termina en arr[i].
     * Para cada j < i: si arr[j] < arr[i], dp[i] = max(dp[i], dp[j] + 1)
     *
     * Complejidad: O(n²) — existe O(n log n) con binary search, pero
     * O(n²) es suficiente para pruebas Senior típicas (n ≤ 10^4).
     */
    public static int longestIncreasingSubsequence(int[] arr) {
        if (arr == null || arr.length == 0) return 0;

        int[] dp = new int[arr.length];
        Arrays.fill(dp, 1); // mínimo: cada elemento es una LIS de longitud 1

        int maxLIS = 1;

        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLIS = Math.max(maxLIS, dp[i]);
        }

        return maxLIS;
    }

    /**
            
        

        JERCICIO 5: Can Sum.
            
        
     *
     * dp[i] = true si es posible formar la suma i.
     * dp[0] = true (base).
     * dp[i] = true si existe algún num donde dp[i - num] == true.
     *
     * Complejidad: O(target * numbers.length) tiempo, O(target) espacio
     */
    public static boolean canSum(int target, int[] numbers) {
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        for (int i = 1; i <= target; i++) {
            for (int num : numbers) {
                if (num <= i && dp[i - num]) {
                    dp[i] = true;
                    break;
                }
            }
        }

       rget];
    }


    
    // ─── Verificación ───
    public static void main(String[] args) {
        System.out.println("=== DYNAMIC PROGRAMMING — Soluciones ===\n");

        System.out.println("Ej1: " + climbStairs(5));                                        // 8
        System.out.println("Ej2: " + minCoins(new int[]{1, 5, 10, 25}, 36));                // 3
        System.out.println("Ej3: " + maxSubarraySum(
                new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));                                // 6
        System.out.println("Ej4: " + longestIncreasingSubsequence(
                new int[]{10, 9, 2, 5, 3, 7, 101, 18}));                                    // 4
        System.out.println("Ej5 (target=7, [2,3]): " + canSum(7, new int[]{2, 3}));         // true
        System.out.println("Ej5 (target=7, [2,4]): " + canSum(7, new int[]{2, 4}));         // false
    }
}

            
        

        
            
        

        
            
        
                
                
            

            

            
                
                
            