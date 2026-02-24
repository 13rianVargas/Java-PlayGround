/**
 * ============================================================
 *  06 — DYNAMIC PROGRAMMING (DP)
 * ============================================================
 *
 *  CONCEPTO: Recursión + Memoización. Cuando un problema recursivo
 *  tiene SUBPROBLEMAS REPETIDOS, guardamos resultados en una tabla
 *  para no recalcularlos.
 *
 *  CUÁNDO USARLO:
 *  - "Mínimo número de operaciones para..."
 *  - "Máximo valor/beneficio posible"
 *  - "¿De cuántas formas puedo...?"
 *  - "¿Es posible llegar a X?"
 *
 *  DOS ENFOQUES:
 *  1. Top-Down (recursión + memo)  — más intuitivo
 *  2. Bottom-Up (tabla iterativa)  — más eficiente
 *
 *  ============================================================
 */

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== DYNAMIC PROGRAMMING ===\n");

        // ─────────────────────────────────
        // 1) Fibonacci — DP vs Recursión
        // ─────────────────────────────────
        System.out.println("--- 1) Fibonacci con DP ---");

        // Top-Down con memoización
        Map<Integer, Long> memo = new HashMap<>();
        System.out.println("fib(50) Top-Down: " + fibMemo(50, memo)); // instantáneo

        // Bottom-Up iterativo
        System.out.println("fib(50) Bottom-Up: " + fibBottomUp(50));

        // Sin DP: fib(50) tomaría 2^50 ≈ 10^15 operaciones → nunca termina


        // ─────────────────────────────────
        // 2) Coin Change — Clásico de DP
        // ─────────────────────────────────
        System.out.println("\n--- 2) Coin Change ---");

        int[] coins = {1, 5, 10, 25};
        int amount = 36;
        System.out.println("Mínimas monedas para " + amount + " con " +
                Arrays.toString(coins) + ": " + coinChange(coins, amount));
        // 25 + 10 + 1 = 3 monedas


        // ─────────────────────────────────
        // 3) Longest Common Subsequence (LCS)
        // ─────────────────────────────────
        System.out.println("\n--- 3) Longest Common Subsequence ---");

        String a = "ABCBDAB";
        String b = "BDCAB";
        System.out.println("LCS(\"" + a + "\", \"" + b + "\"): " + lcs(a, b));
        // BCAB → 4


        // ─────────────────────────────────
        // 4) Maximum Subarray Sum (Kadane's)
        // ─────────────────────────────────
        System.out.println("\n--- 4) Maximum Subarray Sum (Kadane's) ---");

        int[] profits = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Array: " + Arrays.toString(profits));
        System.out.println("Max subarray sum: " + kadane(profits)); // 6 → [4,-1,2,1]


        // ─────────────────────────────────
                Knapsack (Mochila)
        // ─────────────────────────────────
        System.out.println("\n--- 5) 0/1 Knapsack ---");

        int[] weights = {2, 3, 4, 5};
        int[] values  = {3, 4, 5, 6};
        int capacity = 8;

        System.out.println("Pesos:    " + Arrays.toString(weights));
        System.out.println("Valores:  " + Arrays.toString(values));
        System.out.println("Capacidad: " + capacity);
        System.out.println("Máximo valor: " + knapsack(weights, values, capacity));
        // Tomar items 1 y 3 (peso=3+5=8, val
            r=4+6=10) → 10
        

        
            
        

        // ─────────────────────────────────
        // RESUMEN
        // ─────────────────────────────────
        System.out.println("\n=== RESUMEN DP ===");
        System.out.println("• Detectar: subproblemas repetidos + solución óptima");
        System.out.println("• Top-Down:  recursión + HashMap/array para memorizar");
        System.out.println("• Bottom-Up: llenar tabla iterativamente dp[i] = f(dp[i-1], ...)");
        System.out.println("• Clásicos:  Fibonacci, Coin Change, LCS, Knapsack, Kadane");
        System.out.println("• En pruebas Senior: Coin Change y Kadane son los más frecuentes");
    }

    // ─── Helpers ───

    static long fibMemo(int n, Map<Integer, Long> memo) {
        if (n <= 1) return n;
        if (memo.containsKey(n)) return memo.get(n);
        long result = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
        memo.put(n, result);
        return result;
    }

    static long fibBottomUp(int n) {
        if (n <= 1) return n;
        long[] dp = new long[n + 1];
        dp[0] = 0; dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    static int coinChange(int[] coins, int amount) {
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

        return dp[amount] > amount ? -1 : dp[amount];
    }

    static int lcs(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[m][n];
    }

    static int kadane(int[] arr) {int maxEndingHere = arr[0];
        int maxSoFar = arr[0];

        for (int i = 1; i < arr.length; i++) {
            maxEndingHere = Math.max(arr[i], maxEndingHere + arr[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }

        return maxSoFar;
    }

    static int knapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                // No tomar item i
                dp[i][w] = dp[i - 1][w];

        // 
                // Tomar item i (si cabe)
                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(dp[i][w],
                            dp[i - 1][w - weights[i - 1]] + values[i - 1]);
                }
            }
        }

        return dp[n][capacity];
    }
}  
           