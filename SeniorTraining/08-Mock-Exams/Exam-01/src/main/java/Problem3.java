import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PROBLEMA 3 — Detector de Anomalías
 *
 * Clasifica cada lectura como "NORMAL", "HIGH" o "LOW"
 * según su variación respecto a la lectura anterior.
     *                       
 */
public class Problem3 {

    /**
    
     * @param readings  lecturas del sensor (puede ser null o vacío)
     * @param threshold variación máxima permitida entre lecturas consecutivas
     * @return List de "NORMAL" / "HIGH" / "LOW", una por cada lectura
     *         - Primera lectura → siempre "NORMAL"
     *         - readings[i] - readings[i-1] > threshold  → "HIGH"
     *         [i-1] - readings[i] > threshold  → "LOW"
     *         - diferencia ≤ threshold                   → "NORMAL"
     *         - Si readings null/vacío o threshold < 0   → lista vacía
     */
    public static List<String> classifyReadings(int[] readings, int threshold) {
        // TODO: Implementa este método
        // ⚠️ Cuidado con Integer overflow cuando calcules la diferencia
        throw new UnsupportedOperationException("Implementa este método.");
    }
}
