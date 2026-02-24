import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * TransactionValidator — Clase a testear con Parameterized Tests.
 *
 * Valida transacciones financieras con reglas de negocio del sector bancario.
 */
public class TransactionValidator {

    public enum ValidationResult {
        VALID,
        NULL_TRANSACTION,
        INVALID_AMOUNT,
        INVALID_CURRENCY,
        AMOUNT_TOO_LARGE,
        AMOUNT_BELOW_MINIMUM
    }

    public record Transaction(String id, BigDecimal amount, String currency, String type) {
    }

    private static final BigDecimal MAX_AMOUNT = new BigDecimal("1000000.00");
    private static final BigDecimal MIN_AMOUNT = new BigDecimal("0.01");

    /**
     * Valida una transacción según las reglas del banco.
     *
     * Reglas:
     * - Transaction no puede ser null → NULL_TRANSACTION
     * - amount no puede ser null ni <= 0 → INVALID_AMOUNT
     * - amount > 1,000,000 → AMOUNT_TOO_LARGE
     * - amount < 0.01 → AMOUNT_BELOW_MINIMUM
     * - currency debe ser uno de: USD, EUR, COP, GBP → INVALID_CURRENCY
     * - Si todo ok → VALID
     */
    public ValidationResult validate(Transaction txn) {
        if (txn == null)
            return ValidationResult.NULL_TRANSACTION;

        if (txn.amount() == null || txn.amount().compareTo(BigDecimal.ZERO) <= 0) {
            return ValidationResult.INVALID_AMOUNT;
        }

        if (txn.amount().compareTo(MAX_AMOUNT) > 0) {
            return ValidationResult.AMOUNT_TOO_LARGE;
        }

        if (txn.amount().compareTo(MIN_AMOUNT) < 0) {
            return ValidationResult.AMOUNT_BELOW_MINIMUM;
        }

        if (txn.currency() == null || !isValidCurrency(txn.currency())) {
            return ValidationResult.INVALID_CURRENCY;
        }

        return ValidationResult.VALID;
    }

    private boolean isValidCurrency(String currency) {
        return switch (currency.toUpperCase()) {
            case "USD", "EUR", "COP", "GBP" -> true;
            default -> false;
        };
    }

    /**
     * Calcula la comisión basada en el tipo de transacción.
     *
     * - WIRE: 0.5% del monto (mínimo $5.00)
     * - ACH: 0.1% del monto (mínimo $1.00)
     * - INTERNAL: $0.00 (sin comisión)
     * - Otro tipo: 1% del monto
     *
     * Resultado con scale 2, HALF_UP.
     */
    public BigDecimal calculateCommission(String type, BigDecimal amount) {
        Objects.requireNonNull(type, "type cannot be null");
        Objects.requireNonNull(amount, "amount cannot be null");

        return switch (type.toUpperCase()) {
            case "WIRE" -> {
                BigDecimal commission = amount.multiply(new BigDecimal("0.005"));
                yield commission.max(new BigDecimal("5.00")).setScale(2, RoundingMode.HALF_UP);
            }
            case "ACH" -> {
                BigDecimal commission = amount.multiply(new BigDecimal("0.001"));
                yield commission.max(new BigDecimal("1.00")).setScale(2, RoundingMode.HALF_UP);
            }
            case "INTERNAL" -> BigDecimal.ZERO.setScale(2);
            default -> amount.multiply(new BigDecimal("0.01"))
                    .setScale(2, RoundingMode.HALF_UP);
        };
    }

    /**
     * Categoriza una transacción por su monto.
     *
     * - amount < 100 → "MICRO"
     * - 100 <= amount < 1000 → "SMALL"
     * - 1000 <= amount < 10000 → "MEDIUM"
     * - 10000 <= amount < 100000 → "LARGE"
     * - amount >= 100000 → "CORPORATE"
     */
    public String categorizeByAmount(BigDecimal amount) {
        Objects.requireNonNull(amount, "amount cannot be null");
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount cannot be negative");
        }

        if (amount.compareTo(new BigDecimal("100")) < 0)
            return "MICRO";
        if (amount.compareTo(new BigDecimal("1000")) < 0)
            return "SMALL";
        if (amount.compareTo(new BigDecimal("10000")) < 0)
            return "MEDIUM";
        if (amount.compareTo(new BigDecimal("100000")) < 0)
            return "LARGE";
        return "CORPORATE";
    }
}
