import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * PROBLEMA 2 — Análisis de Transacciones
 *
 * Datos: record Transaction(String id, String category, double amount, boolean
 * flagged)
 *
 * Implementa los 4 métodos con Stream API.
 */
public class Problem2 {

    public record Transaction(String id, String category, double amount, boolean flagged) {
    }

    /**
     * 2a. IDs de transacciones flagged=true, ordenados alfabéticamente.
     */
    public static List<String> flaggedIds(List<Transaction> txns) {
        // TODO
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * 2b. Suma de amount por categoría, solo transacciones NO flagged.
     */
    public static Map<String, Double> totalByCategory(List<Transaction> txns) {
        // TODO
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * 2c. Transacción flagged con mayor monto. Optional.empty() si no hay ninguna.
     */
    public static Optional<Transaction> highestRisk(List<Transaction> txns) {
        // TODO
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * 2d. ¿Existe alguna transacción con monto > threshold?
     */
    public static boolean hasExcessiveAmount(List<Transaction> txns, double threshold) {
        // TODO
        throw new UnsupportedOperationException("Implementa este método.");
    }
}
