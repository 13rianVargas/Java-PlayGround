import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import Problem3.ValidationResult;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Problema 3 — Validador de Transacciones")
class Problem3Test {

    // ── Tests básicos ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("T01 — Una transacción viola dailyLimit → isValid=false, violationCount=1")
    void t01_oneViolation() {
        ValidationResult r = Problem3.validate(new int[] { 100, 200, 50 }, 150, 500);
        assertAll(
                () -> assertFalse(r.isValid()),
                () -> assertEquals(350L, r.totalAmount()),
                () -> assertEquals(1, r.violationCount()));
    }

    @Test
    @DisplayName("T02 — Ninguna violación individual pero suma > maxDaily → isValid=false")
    void t02_sumExceedsMaxDaily() {
        ValidationResult r = Problem3.validate(new int[] { 50, 50, 50 }, 100, 100);
        assertAll(
                () -> assertFalse(r.isValid()),
                () -> assertEquals(150L, r.totalAmount()),
                () -> assertEquals(0, r.violationCount()));
    }

    @Test
    @DisplayName("T03 — Todo válido → isValid=true, violationCount=0")
    void t03_allValid() {
        ValidationResult r = Problem3.validate(new int[] { 30, 40, 20 }, 100, 200);
        assertAll(
                () -> assertTrue(r.isValid()),
                () -> assertEquals(90L, r.totalAmount()),
                () -> assertEquals(0, r.violationCount()));
    }

    @Test
    @DisplayName("T04 — Múltiples violaciones individuales")
    void t04_multipleViolations() {
        ValidationResult r = Problem3.validate(new int[] { 200, 300, 400 }, 150, 1000);
        assertAll(
                () -> assertFalse(r.isValid()),
                () -> assertEquals(900L, r.totalAmount()),
                () -> assertEquals(3, r.violationCount()));
    }

    @Test
    @DisplayName("T05 — amount exactamente igual a dailyLimit → NO viola")
    void t05_exactDailyLimit_noViolation() {
        // 150 > 150 es false → no viola
        ValidationResult r = Problem3.validate(new int[] { 150 }, 150, 200);
        assertEquals(0, r.violationCount());
        assertTrue(r.isValid());
    }

    // ── Edge cases de entrada ──────────────────────────────────────────────────

    @Test
    @DisplayName("T06 — amounts null → ValidationResult(true, 0, 0)")
    void t06_nullAmounts() {
        ValidationResult r = Problem3.validate(null, 100, 500);
        assertAll(
                () -> assertTrue(r.isValid()),
                () -> assertEquals(0L, r.totalAmount()),
                () -> assertEquals(0, r.violationCount()));
    }

    @Test
    @DisplayName("T07 — amounts vacío → ValidationResult(true, 0, 0)")
    void t07_emptyAmounts() {
        ValidationResult r = Problem3.validate(new int[] {}, 100, 500);
        assertAll(
                () -> assertTrue(r.isValid()),
                () -> assertEquals(0L, r.totalAmount()),
                () -> assertEquals(0, r.violationCount()));
    }

    @Test
    @DisplayName("T08 — dailyLimit <= 0 lanza IllegalArgumentException")
    void t08_invalidDailyLimit_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Problem3.validate(new int[] { 100 }, 0, 500));
        assertThrows(IllegalArgumentException.class,
                () -> Problem3.validate(new int[] { 100 }, -1, 500));
    }

    @Test
    @DisplayName("T09 — maxDaily <= 0 lanza IllegalArgumentException")
    void t09_invalidMaxDaily_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> Problem3.validate(new int[] { 100 }, 100, 0));
    }

    // ── Overflow protection ────────────────────────────────────────────────────

    @Test
    @DisplayName("T10 — totalAmount usa long (evita int overflow)")
    void t10_longTotalAmount() {
        // 10 transacciones de Integer.MAX_VALUE → sum overflow si se usa int
        int[] amounts = new int[10];
        java.util.Arrays.fill(amounts, Integer.MAX_VALUE);
        ValidationResult r = Problem3.validate(amounts, Integer.MAX_VALUE, Integer.MAX_VALUE);
        long expected = (long) Integer.MAX_VALUE * 10;
        assertEquals(expected, r.totalAmount());
    }

    // ── Validación de totalAmount con suma exacta a maxDaily ──────────────────

    @Test
    @DisplayName("T11 — suma == maxDaily → isValid=true (bordes)")
    void t11_sumEqualsMaxDaily_valid() {
        ValidationResult r = Problem3.validate(new int[] { 50, 50 }, 100, 100);
        assertTrue(r.isValid()); // 100 <= 100 → válido
    }

    @Test
    @DisplayName("T12 — suma == maxDaily + 1 → isValid=false")
    void t12_sumExceedsMaxDailyByOne() {
        ValidationResult r = Problem3.validate(new int[] { 50, 51 }, 100, 100);
        assertFalse(r.isValid()); // 101 > 100
    }
}
