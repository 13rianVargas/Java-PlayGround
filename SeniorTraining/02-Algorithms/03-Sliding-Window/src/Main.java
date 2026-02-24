/**
 * ============================================================
 *  03 — SLIDING WINDOW (Ventana Deslizante)
 * ============================================================
 *
 *  PATRÓN: Mantener una "ventana" de tamaño fijo o variable
 *  sobre un array/string y deslizarla de izquierda a derecha.
 *
 *  CUÁNDO USARLO:
 *  - "Subarray de tamaño K con mayor suma"
 *  - "Substring más larga sin caracteres repetidos"
 *  - "Ventana mínima que contiene todos los elementos"
 *
 *  COMPLEJIDAD: O(n) — cada elemento entra y sale de la ventana
 *  exactamente una vez.
 *
 *  ============================================================
 */

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== SLIDING WINDOW ===\n");

        // ───────────────────────────────────
        // 1) Ventana Fija — Suma máxima de K consecutivos
        // ───────────────────────────────────
        System.out.println("--- 1) Ventana Fija: Suma máxima de K consecutivos ---");

        int[] prices = {100, 200, 300, 400, 500, 150, 250};
        int k = 3;

        // Paso 1: calcular suma de la primera ventana
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += prices[i];
        }

        int maxSum = windowSum;

        // Paso 2: deslizar la ventana
        // Sumamos el nuevo elemento, restamos el que sale
        for (int i = k; i < prices.length; i++) {
            windowSum += prices[i] - prices[i - k]; // entra prices[i], sale prices[i-k]
            maxSum = Math.max(maxSum, windowSum);
        }

        System.out.println("Array: " + Arrays.toString(prices));
        System.out.println("K = " + k);
        System.out.println("Suma máxima de " + k + " consecutivos: " + maxSum);
        // Esperado: 300+400+500 = 1200


        // ─────────────────────────────────
        // 2) Ventana Variable — Substring más larga sin repetidos
        // ─────────────────────────────────
        System.out.println("\n--- 2) Ventana Variable: Longest Substring Without Repeating ---");

        String s = "ABCBDAEF";
        Set<Character> window = new HashSet<>();
        int left = 0;
        int maxLength = 0;
        String bestSubstring = "";

        for (int right = 0; right < s.length(); right++) {
            // Si el carácter ya está en la ventana, contraer desde la izquierda
            while (window.contains(s.charAt(right))) {
                window.remove(s.charAt(left));
                left++;
            }
            window.add(s.charAt(right));

            if (right - left + 1 > maxLength) {
                maxLength = right - left + 1;
                bestSubstring = s.substring(left, right + 1);
            }
        }
                

        System.out.println("String: \"" + s + "\"");
        System.out.println("Longest without repeating: \"" + bestSubstring + "\" (length " + maxLength + ")");
        // Esperado: "CBDAEF" → 6


        // ─────────────────────────────────────────────────────────────────────
        // 3) Ventana Fija — Promedio Móvil
        // ─────────────────────────────────────────────────────────────────────
        System.out.println("\n--- 3) Promedio Móvil de K días (Moving Average) ---");

        double[] stockPrices = {120.5, 121.3, 119.8, 122.0, 123.5, 121.0};

        int windowSize = 3;

        double sum = 0;
        System.out.println("Precios diarios: " + Arrays.toString(stockPrices));
        System.out.println("Moving Average (" + windowSize + " días):");

        for (int i = 0; i < stockPrices.length; i++) {
            sum += stockPrices[i];

            if (i >= windowSize) {
                sum -= stockPrices[i - windowSize]; // sale el más antiguo
            }

            if (i >= windowSize - 1) {
                double avg = sum / windowSize;
                System.out.printf("  Día %d: %.2f%n", i + 1, avg);
            }
        }


        // ─────────────────────────────────
        // 4) Concepto: Ventana con HashMap (conteo de frecuencias)
        // ─────────────────────────────────
        System.out.println("\n--- 4) Ventana con HashMap: Menor ventana con K distintos ---");

        int[] data = {1, 2, 1, 3, 4, 2, 3};
        int distinctTarget = 3;

        Map<Integer, Integer> freq = new HashMap<>();
        int minLen = Integer.MAX_VALUE;
        int l = 0;

        for (int r = 0; r < data.length; r++) {
            freq.merge(data[r], 1, Integer::sum);

            while (freq.size() >= distinctTarget) {
                minLen = Math.min(minLen, r - l + 1);
                int leftVal = data[l];
                freq.merge(leftVal, -1, Integer::sum);
                if (freq.get(leftVal) == 0) freq.remove(leftVal);
                l++;
            }
        }

        System.out.println("Array: " + Arrays.toString(data));
        System.out.println("Menor ventana con " + distinctTarget + " distintos: " + minLen);


        // ─────────────────────────────────
        // RESUMEN
        // ─────────────────────────────────
        System.out.println("\n=== RESUMEN SLIDING WINDOW ===");
        System.out.println("• Ventana FIJA:     tamaño K constante, desliza sumando/restando");
        System.out.println("• Ventana VARIABLE: left avanza cuando la condición se viola");
        System.out.println("• Estructura:       HashSet para unicidad, HashMap para frecuencias");
        System.out.println("• Complejidad:      O(n) siempre — cada elemento entra/sale 1 vez");
        System.out.println("• Señal:            'subarray', 'substring', 'consecutivos', 'ventana'");
    }
}
