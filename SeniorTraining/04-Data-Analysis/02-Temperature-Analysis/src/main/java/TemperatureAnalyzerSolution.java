/**
 * SOLUCIÓN — TemperatureAnalyzer
 *
 * Decisiones Senior:
 * 1. Inicializar con el primer elemento (no con 0 o Integer.MAX_VALUE) — evita
 * confusión
 * 2. La comparación "prefiere positivo en empate" se resuelve con:
 * Math.abs(t) < Math.abs(closest) || (Math.abs(t) == Math.abs(closest) && t >
 * 0)
 * 3. null/empty → 0 (especificación)
 */
public class TemperatureAnalyzerSolution {

    public static int closestToZero(int[] temperatures) {
        if (temperatures == null || temperatures.length == 0) {
            return 0;
        }

        int closest = temperatures[0];

        for (int i = 1; i < temperatures.length; i++) {
            int t = temperatures[i];
            int absT = Math.abs(t);
            int absCurrent = Math.abs(closest);

            if (absT < absCurrent || (absT == absCurrent && t > 0)) {
                closest = t;
            }
        }

        return closest;
    }
}
