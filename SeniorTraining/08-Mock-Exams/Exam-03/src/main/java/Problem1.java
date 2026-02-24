/**
 * PROBLEMA 1 — Combinations (DP / Recursión con Memoización)
 *
 * Cuenta las formas de combinar denominaciones para alcanzar 'amount'.
 * Cada denominación se puede usar múltiples veces.
 * El orden NO importa: [200, 100] == [100, 200].
 */
public class Problem1 {

    /**
     * @param        monto objetivo (0 <= amount <= 10_000)
     * @param denominations denominaciones, nunca null, todos > 0, sin duplicados
     * @return              número de combinaciones únicas, 0 si amount = 0
     */
    public static int countCombinations(int amount, int[] denominations) {
        // TODO: Implementa con programación dinámica (DP bottom-up)
        //
        // Pista — tabla DP:
        //   int[] dp = new int[amount + 1];
        //   dp[0] = 1; // hay 1 forma de alcanzar 0 (no usar nada)
        //   for (int denom : denominations)
        //     for (int i = denom; i <= amount; i++)
        //       dp[i] += dp[i - denom];
        //
        // Respuesta: dp[amount] - corrige si amount == 0 según el enunciado
        throw new UnsupportedOperationException("Implementa este método.");
    }
}
