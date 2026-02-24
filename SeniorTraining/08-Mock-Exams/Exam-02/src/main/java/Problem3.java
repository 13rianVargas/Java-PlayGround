/**
 * PROBLEMA 3 — Validador de Transacciones
 */
public class Problem3 {

    /**
     * Resultado de la validación.
     *
     * @param isValid        true si no hay violaciones individuales Y sum <=
     *                       maxDaily
     * @param totalAmount    suma total de todos los amounts
     * @param violationCount número de transacciones que superan dailyLimit
     */
    public record ValidationResult(boolean isValid, long totalAmount, int violationCount) {
    }

    /**
     * @param amounts    montos de las transacciones (puede ser null o vacío)
     * @param dailyLimit límite máximo por transacción individual
     * @param maxDaily   límite de la suma total diaria
     * @return ValidationResult
     * @throws IllegalArgumentException si dailyLimit <= 0 o maxDaily <= 0
     */
    public static ValidationResult validate(int[] amounts, int dailyLimit, int maxDaily) {
        // TODO
        // Recuerda:
        // - Primero valida que dailyLimit > 0 && maxDaily > 0 (lanza excepción si no)
        // - Si amounts es null o vacío: retorna ValidationResult(true, 0, 0)
        // - Cuenta las transacciones donde amount > dailyLimit
        // - Suma todos los amounts
        // - isValid = violationCount == 0 && totalAmount <= maxDaily
        throw new UnsupportedOperationException("Implementa este método.");
    }
}
