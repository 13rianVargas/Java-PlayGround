import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import TransactionValidator.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TransactionValidator — Validación de Secuencias")
class TransactionValidatorTest {

    // Helpers
    static Transaction tx(String id, String userId, long amount) {
        return new Transaction(id, userId, amount);
    }

    // ── Sin errores ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("T01 — Transacciones válidas → lista vacía")
    void t01_allValid() {
        List<Transaction> txns = List.of(
                tx("T1", "userA", 100),
                tx("T2", "userB", 200),
                tx("T3", "userA", 50));
        assertTrue(TransactionValidator.validate(txns, 1000).isEmpty());
    }

    // ── Regla 1: INVALID_AMOUNT ────────────────────────────────────────────────

    @Test
    @DisplayName("T02 — amount=0 → INVALID_AMOUNT")
    void t02_amountZero() {
        List<Transaction> txns = List.of(tx("T1", "userA", 0));
        List<ValidationError> errors = TransactionValidator.validate(txns, 1000);
        assertEquals(1, errors.size());
        assertEquals("T1", errors.get(0).transactionId());
        assertEquals(ErrorType.INVALID_AMOUNT, errors.get(0).errorType());
    }

    @Test
    @DisplayName("T03 — amount negativo → INVALID_AMOUNT")
    void t03_negativeAmount() {
        List<ValidationError> errors = TransactionValidator.validate(
                List.of(tx("T1", "userA", -500)), 1000);
        assertEquals(1, errors.size());
        assertEquals(ErrorType.INVALID_AMOUNT, errors.get(0).errorType());
    }

    @Test
    @DisplayName("T04 — Múltiples amounts inválidos")
    void t04_multipleInvalidAmounts() {
        List<Transaction> txns = List.of(
                tx("T1", "userA", -100),
                tx("T2", "userB", 200),
                tx("T3", "userC", 0));
        List<ValidationError> errors = TransactionValidator.validate(txns, 1000);
        long invalidAmountErrors = errors.stream()
                .filter(e -> e.errorType() == ErrorType.INVALID_AMOUNT).count();
        assertEquals(2, invalidAmountErrors);
    }

    // ── Regla 2: CONSECUTIVE_SAME_USER ────────────────────────────────────────

    @Test
    @DisplayName("T05 — Dos transacciones seguidas del mismo user → CONSECUTIVE_SAME_USER en la segunda")
    void t05_consecutiveSameUser() {
        List<Transaction> txns = List.of(
                tx("T1", "userA", 100),
                tx("T2", "userA", 200) // consecutiva
        );
        List<ValidationError> errors = TransactionValidator.validate(txns, 5000);
        assertEquals(1, errors.size());
        assertEquals("T2", errors.get(0).transactionId());
        assertEquals(ErrorType.CONSECUTIVE_SAME_USER, errors.get(0).errorType());
    }

    @Test
    @DisplayName("T06 — Tres consecutivas del mismo user → 2 errores")
    void t06_threeConsecutive() {
        List<Transaction> txns = List.of(
                tx("T1", "A", 10),
                tx("T2", "A", 10),
                tx("T3", "A", 10));
        long consecutive = TransactionValidator.validate(txns, 9999).stream()
                .filter(e -> e.errorType() == ErrorType.CONSECUTIVE_SAME_USER).count();
        assertEquals(2, consecutive);
    }

    @Test
    @DisplayName("T07 — Misma user no consecutiva → no error")
    void t07_sameuserNotConsecutive() {
        List<Transaction> txns = List.of(
                tx("T1", "A", 10),
                tx("T2", "B", 10),
                tx("T3", "A", 10));
        assertTrue(TransactionValidator.validate(txns, 9999).isEmpty());
    }

    // ── Regla 3: BALANCE_EXCEEDED ──────────────────────────────────────────────

    @Test
    @DisplayName("T08 — Balance excedido → BALANCE_EXCEEDED en la transacción que lo rompe")
    void t08_balanceExceeded() {
        List<Transaction> txns = List.of(
                tx("T1", "A", 500),
                tx("T2", "B", 400), // acumula 900
                tx("T3", "A", 200) // acumula 1100 > 1000 → error
        );
        List<ValidationError> errors = TransactionValidator.validate(txns, 1000);
        assertEquals(1, errors.size());
        assertEquals("T3", errors.get(0).transactionId());
        assertEquals(ErrorType.BALANCE_EXCEEDED, errors.get(0).errorType());
    }

    @Test
    @DisplayName("T09 — Balance exactamente igual al máximo → no error")
    void t09_balanceExactMax() {
        List<Transaction> txns = List.of(
                tx("T1", "A", 500),
                tx("T2", "B", 500) // 1000 == 1000, no > 1000
        );
        assertTrue(TransactionValidator.validate(txns, 1000).isEmpty());
    }

    // ── Múltiples errores ──────────────────────────────────────────────────────

    @Test
    @DisplayName("T10 — Una transacción puede tener errores de múltiples reglas")
    void t10_multipleErrorsOnOneTransaction() {
        List<Transaction> txns = List.of(
                tx("T1", "A", 900),
                tx("T2", "A", -1) // consecutive + invalid amount (balance no se suma por amount<=0)
        );
        List<ValidationError> errors = TransactionValidator.validate(txns, 1000);
        assertEquals(2, errors.size());
        assertTrue(errors.stream().anyMatch(e -> e.errorType() == ErrorType.CONSECUTIVE_SAME_USER));
        assertTrue(errors.stream().anyMatch(e -> e.errorType() == ErrorType.INVALID_AMOUNT));
    }

    @Test
    @DisplayName("T11 — Acumula todos los errores (no hace short-circuit)")
    void t11_accumulatesAllErrors() {
        List<Transaction> txns = List.of(
                tx("T1", "A", 0), // INVALID_AMOUNT
                tx("T2", "A", 200), // CONSECUTIVE_SAME_USER
                tx("T3", "B", 150),
                tx("T4", "B", 150) // CONSECUTIVE_SAME_USER
        );
        List<ValidationError> errors = TransactionValidator.validate(txns, 10000);
        assertEquals(3, errors.size());
    }

    // ── Edge cases de entrada ──────────────────────────────────────────────────

    @Test
    @DisplayName("T12 — Lista null → lista vacía")
    void t12_nullList() {
        assertTrue(TransactionValidator.validate(null, 1000).isEmpty());
    }

    @Test
    @DisplayName("T13 — Lista vacía → lista vacía")
    void t13_emptyList() {
        assertTrue(TransactionValidator.validate(List.of(), 1000).isEmpty());
    }

    @Test
    @DisplayName("T14 — Una sola transacción válida → lista vacía")
    void t14_singleValidTransaction() {
        assertTrue(TransactionValidator.validate(List.of(tx("T1", "A", 1)), 100).isEmpty());
    }

    @Test
    @DisplayName("T15 — maxBalance <= 0 lanza IllegalArgumentException")
    void t15_invalidMaxBalance() {
        assertThrows(IllegalArgumentException.class,
                () -> TransactionValidator.validate(List.of(), 0));
        assertThrows(IllegalArgumentException.class,
                () -> TransactionValidator.validate(List.of(), -500));
    }

    // ── Overflow long ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("T16 — long balance evita overflow con montos grandes")
    void t16_longBalance() {
        List<Transaction> txns = List.of(
                tx("T1", "A", Long.MAX_VALUE / 2),
                tx("T2", "B", Long.MAX_VALUE / 2));
        long maxBalance = Long.MAX_VALUE;
        // Suma < MAX → no debe exceder
        assertTrue(TransactionValidator.validate(txns, maxBalance).isEmpty());
    }

    @Test
    @DisplayName("T17 — Error BALANCE_EXCEEDED correcto con long aritmética")
    void t17_balanceExceededLong() {
        List<Transaction> txns = List.of(
                tx("T1", "A", 1_000_000_000L),
                tx("T2", "B", 1_000_000_000L) // total 2B > maxBalance 1.5B
        );
        List<ValidationError> errors = TransactionValidator.validate(txns, 1_500_000_000L);
        assertEquals(1, errors.size());
        assertEquals("T2", errors.get(0).transactionId());
        assertEquals(ErrorType.BALANCE_EXCEEDED, errors.get(0).errorType());
    }

    // ── Resultado nunca null ───────────────────────────────────────────────────

    @Test
    @DisplayName("T18 — validate nunca retorna null")
    void t18_neverNull() {
        assertNotNull(TransactionValidator.validate(null, 1000));
        assertNotNull(TransactionValidator.validate(List.of(), 1000));
        assertNotNull(TransactionValidator.validate(List.of(tx("T1", "A", 100)), 1000));
    }

    @Test
    @DisplayName("T19 — BALANCE_EXCEEDED solo se registra una vez si hay múltiples que exceden")
    void t19_balanceExceededMultipleTimes() {
        // balance: T1=300, T2=600, T3=900>500, T4=1200>500
        List<Transaction> txns = List.of(
                tx("T1", "A", 300),
                tx("T2", "B", 300),
                tx("T3", "A", 300), // acumula 900 > 500 → error
                tx("T4", "B", 300) // acumula 1200 > 500 → error
        );
        long exceeded = TransactionValidator.validate(txns, 500).stream()
                .filter(e -> e.errorType() == ErrorType.BALANCE_EXCEEDED).count();
        assertEquals(2, exceeded); // T3 y T4
    }

    @Test
    @DisplayName("T20 — Primera transacción no tiene error de consecutiva")
    void t20_firstTransactionNoConsecutiveError() {
        List<Transaction> txns = List.of(tx("T1", "A", 100));
        List<ValidationError> errors = TransactionValidator.validate(txns, 1000);
        assertTrue(errors.stream().noneMatch(e -> e.errorType() == ErrorType.CONSECUTIVE_SAME_USER));
    }
}
