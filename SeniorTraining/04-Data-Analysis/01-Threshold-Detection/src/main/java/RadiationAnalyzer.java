import java.util.Collections;
import java.util.List;

/**
 * ════════════════════════════════════════════════════════════
 * RadiationAnalyzer — EJERCICIO (esqueleto a completar)
 * ════════════════════════════════════════════════════════════
 *
 * CONTEXTO (exactamente el tipo de ejercicio que viste en la prueba):
 *
 * Se reciben lecturas secuenciales de un sensor de radioactividad.
 * Debes analizar la variación entre lecturas consecutivas para
 * detectar anomalías: saltos abruptos hacia arriba (HIGH) o
 * hacia abajo (LOW).
 *
 * REGLAS:
 * - La PRIMERA lectura siempre es "NORMAL" (no tiene lectura anterior).
 * - Para cada lectura i (i >= 1):
 * * Si readings[i] - readings[i-1] > threshold → "HIGH"
 * * Si readings[i-1] - readings[i] > threshold → "LOW"
 * * En caso contrario → "NORMAL"
 * - La comparación usa > (estricto), no >=.
 * Es decir: exactamente el umbral = NORMAL, excederlo = HIGH/LOW.
 *
 * PARÁMETROS:
 * 
 * @param radioactivity array de lecturas enteras (puede ser null o vacío)
 * @param threshold     máxima variación permitida entre lecturas (>= 0)
 *
 *                      RETORNO:
 *                      List<String> con un String por cada lectura: "NORMAL",
 *                      "HIGH" o "LOW"
 *                      Si radioactivity es null o vacío: retorna lista vacía.
 *                      Si threshold < 0: retorna lista vacía.
 *
 *                      EJEMPLO:
 *                      readings = [10, 12, 8, 15, 14]
 *                      threshold = 3
 *                      resultados:
 *                      i=0: 10 → NORMAL (primera lectura)
 *                      i=1: 12 - 10 = 2 → NORMAL (2 <= 3)
 *                      i=2: 12 - 8 = 4 → LOW (4 > 3, baja más de 3)
 *                      i=3: 15 - 8 = 7 → HIGH (7 > 3, sube más de 3)
 *                      i=4: 15 - 14 = 1 → NORMAL (1 <= 3)
 *                      → ["NORMAL", "NORMAL", "LOW", "HIGH", "NORMAL"]
 *
 *                      CORRE LOS TESTS: mvn test (desde el directorio
 *                      04-Data-Analysis/)
 */
public class RadiationAnalyzer {

    // ─────────────────────────────────────────────────────────────────────
    // TU IMPLEMENTACIÓN AQUÍ
    // ─────────────────────────────────────────────────────────────────────
    public static List<String> analyze(int[] radioactivity, int threshold) {
        // TODO: implementar según las reglas descritas arriba
        return Collections.emptyList();
    }
}
