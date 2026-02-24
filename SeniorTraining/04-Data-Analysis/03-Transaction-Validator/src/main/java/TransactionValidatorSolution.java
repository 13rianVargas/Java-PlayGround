import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SOLUCIÓN — TransactionValidator
 *
 * Decisiones Senior:
 * 1. Acumular TODOS los errores en una pasada (no fail-fast) — requirement
 * 2. Usar long para balance acumulado (evita overflow con muchas transacciones)
 * 3. Rastrear el userId previo con una variable simple (no necesita estructura
 * compleja)
 * 4. IllegalArgumentException por contrato del método — documentado en Javadoc
 * 5. Collections.emptyList() vs new ArrayList<>() → emptyList es inmutable y
 * más eficiente
 */
public class TransactionValidatorSolution {

    public record Transaction(String id, String userId, long amount) {
    }

    public enum ErrorType {
        INVALID_AMOUNT,
        CONSECUTIVE_SAME_USER,
        BALANCE_EXCEEDED
    }

    public record ValidationError(String transactionId, ErrorType errorType) {
    }

    public static List<ValidationError> validate(List<Transaction> transactions, long maxBalance) {
        if (maxBalance <= 0) {
            throw new IllegalArgumentException("maxBalance debe ser > 0, fue: " + maxBalance);
        }
        if (transactions == null || transactions.isEmpty()) {
            return Collections.emptyList();
        }

        List<ValidationError> errors = new ArrayList<>();
        long runningBalance = 0;
        String previousUserId = null;

        for (Transaction tx : transactions) {
            // Regla 1: monto inválido
            if (tx.amount() <= 0) {
                errors.add(new ValidationError(tx.id(), ErrorType.INVALID_AMOUNT));
            }

            // Regla 2: userId consecutivo
            if (tx.userId().equals(previousUserId)) {
                errors.add(new ValidationError(tx.id(), ErrorType.CONSECUTIVE_SAME_USER));
            }

            // Regla 3: balance excedido
            // Solo acumulamos amounts válidos para el balance
            if (tx.amount() > 0) {
                runningBalance += tx.amount();
                if (runningBalance > maxBalance) {
                    errors.add(new ValidationError(tx.id(), ErrorType.BALANCE_EXCEEDED));
                }
            }

            previousUserId = tx.userId();
        }

        return errors;
    }
}
