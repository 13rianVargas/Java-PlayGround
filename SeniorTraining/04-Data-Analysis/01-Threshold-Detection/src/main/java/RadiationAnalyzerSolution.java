import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ════════════════════════════════════════════════════════════
 * RadiationAnalyzerSolution — SOLUCIÓN COMPLETA
 * ════════════════════════════════════════════════════════════
 *
 * ⚠️ MIRAR SOLO DESPUÉS DE HABER INTENTADO LA IMPLEMENTACIÓN
 *
 * Puntos clave de esta solución:
 * 1. Validación defensiva de inputs (null, vacío, threshold negativo)
 * 2. Uso de long para la diferencia (evita overflow con Integer.MIN/MAX)
 * 3. Comparación ESTRICTA (>) según el enunciado
 * 4. Primera lectura siempre NORMAL
 * 5. Retorno de lista vacía (no null) para inputs inválidos
 */
public class RadiationAnalyzerSolution {

    private static final String NORMAL = "NORMAL";
    private static final String HIGH = "HIGH";
    private static final String LOW = "LOW";

    public static List<String> analyze(int[] radioactivity, int threshold) {
        // ── Validación de inputs ─────────────────────────────────────────
        if (radioactivity == null || radioactivity.length == 0) {
            return Collections.emptyList();
        }
        if (threshold < 0) {
            return Collections.emptyList();
        }

        // ── Procesamiento principal ──────────────────────────────────────
        List<String> results = new ArrayList<>(radioactivity.length);
        results.add(NORMAL); // La primera lectura siempre es NORMAL

        for (int i = 1; i < radioactivity.length; i++) {
            // ⚠️ CRÍTICO: usar long para evitar overflow con Integer.MIN/MAX
            // Si usamos int: Integer.MIN_VALUE - Integer.MAX_VALUE → overflow silencioso
            long diff = (long) radioactivity[i] - (long) radioactivity[i - 1];

            if (diff > threshold) {
                results.add(HIGH);
            } else if (-diff > threshold) { // equivalente a: diff < -threshold
                results.add(LOW);
            } else {
                results.add(NORMAL);
            }
        }

        return results;
    }
}

/*
 * ────────────────────────────────────────────────────────────
 * LECCIONES DE DISEÑO SENIOR
 * ────────────────────────────────────────────────────────────
 *
 * 1. OVERFLOW (el error que elimina al 30% de candidatos en T20)
 * Si radioactivity[i] = Integer.MAX_VALUE y radioactivity[i-1] =
 * Integer.MIN_VALUE:
 * int diff = MAX - MIN = 2^32 - 1 = OVERFLOW → resultado incorrecto
 * long diff = MAX - MIN = 2_147_483_647 - (-2_147_483_648) = 4_294_967_295L →
 * correcto
 *
 * 2. COMPARACIÓN ESTRICTA (>) vs NO ESTRICTA (>=)
 * El enunciado dice "mayor o menor en 2 puntos" o "more than threshold".
 * Siempre leer con cuidado: "more than" = >, "at least" = >=
 * El test T03 valida exactamente este límite.
 *
 * 3. PRIMERA LECTURA
 * No tiene referencia anterior → siempre NORMAL.
 * Puede parecer obvio pero candidatos lo olvidan en pruebas bajo presión.
 *
 * 4. RETORNO DEFENSIVO
 * Nunca retornar null. Retornar lista vacía.
 * Collections.emptyList() es más eficiente que new ArrayList<>() para listas
 * vacías.
 *
 * 5. CONSTANTES DE STRING
 * Definir "NORMAL", "HIGH", "LOW" como constantes evita typos ("HIGHH", "high")
 * que son difíciles de debuggear bajo presión.
 *
 * 6. CAPACIDAD INICIAL DEL ArrayList
 * new ArrayList<>(radioactivity.length) → pre-allocate para evitar rehashing
 * Señal de pensamiento en rendimiento que un Senior muestra naturalmente.
 */
