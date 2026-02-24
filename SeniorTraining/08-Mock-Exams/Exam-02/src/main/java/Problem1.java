/**
 * PROBLEMA 1 — Ventana Deslizante (Sliding Window)
 *
 * Encuentra la suma máxima de k elementos consecutivos en O(n).
 */
public class Problem1 {

    /**
     * @param amounts array de montos, nunca null, length >= k
     * @param k       tamaño de la ventana, 1 <= k <= amounts.length
     * @return suma máxima de k elementos consecutivos
     */
    public static long maxWindowSum(int[] amounts, int k) {
        // TODO: Implementa con Sliding Window (O(n))
        // Pista 1: calcula la suma de la primera ventana
        // Pista 2: desliza la ventana: suma += amounts[i] - amounts[i-k]
        throw new UnsupportedOperationException("Implementa este método.");
    }
}
