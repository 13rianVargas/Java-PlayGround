import java.util.List;

/**
 * EJERCICIO — Validador de Secuencia de Transacciones
 *
 * Un sistema bancario necesita validar que una secuencia de transacciones
 * no viola ninguna regla de negocio. Se deben clasificar los errores
 * encontrados.
 *
 * Reglas:
 * 1. El monto de cada transacción debe ser > 0
 * 2. No puede haber dos transacciones consecutivas del mismo userId
 * 3. La suma acumulada nunca puede superar el maxBalance
 *
 * Implementa el método validate() que retorna una lista de errores encontrados.
 * Si no hay errores, retorna una lista vacía (no null).
 */
public class TransactionValidator {

    public record Transaction(String id, String userId, long amount) {
    }

    public enum ErrorType {
        INVALID_AMOUNT, // amount <= 0
        CONSECUTIVE_SAME_USER, // dos seguidas del mismo userId
        BALANCE_EXCEEDED // suma acumulada > maxBalance
    }

    public record ValidationError(String transactionId, ErrorType errorType) {
    }

    /**
     * @param transactions lista de transacciones (puede ser null o vacía)
     * @param maxBalance   balance máximo permitido (> 0)
     * @return lista de errores encontrados (vacía si es válido, nunca null)
     * @throws IllegalArgumentException si maxBalance <= 0
     */
    public static List<ValidationError> validate(List<Transaction> transactions, long maxBalance) {
        // TODO: Implementa este método
        // - Lanza IllegalArgumentException si maxBalance <= 0
        // - Si transactions es null o vacío, retorna lista vacía
        // - Revisa las 3 reglas en orden, acumulando errors
        // - Nota: aplica todas las reglas (no solo la primera que falla)
        throw new UnsupportedOperationException("Implementa este método.");
    }
}
