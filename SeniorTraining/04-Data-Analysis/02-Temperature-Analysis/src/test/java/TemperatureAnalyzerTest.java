import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TemperatureAnalyzer — Closest to Zero")
class TemperatureAnalyzerTest {

    // ── Tests básicos ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("T01 — Ejemplo básico: varios positivos y negativos")
    void t01_basicMixed() {
        // El más cercano es 1
        assertEquals(1, TemperatureAnalyzer.closestToZero(new int[] { 1, -2, -8, 4, 5 }));
    }

    @Test
    @DisplayName("T02 — Un elemento de cero exacto → retorna 0")
    void t02_exactlyZero() {
        assertEquals(0, TemperatureAnalyzer.closestToZero(new int[] { -5, 0, 10 }));
    }

    @Test
    @DisplayName("T03 — Un solo elemento")
    void t03_singleElement() {
        assertEquals(42, TemperatureAnalyzer.closestToZero(new int[] { 42 }));
        assertEquals(-7, TemperatureAnalyzer.closestToZero(new int[] { -7 }));
    }

    // ── Regla de preferencia positiva en empate ────────────────────────────────

    @Test
    @DisplayName("T04 — Empate entre -5 y +5 → retorna 5 (positivo gana)")
    void t04_tiePositiveWins() {
        assertEquals(5, TemperatureAnalyzer.closestToZero(new int[] { -5, 5 }));
    }

    @Test
    @DisplayName("T05 — Empate entre +3 y -3 (orden invertido) → retorna 3")
    void t05_tieReverseOrder() {
        assertEquals(3, TemperatureAnalyzer.closestToZero(new int[] { 3, -3 }));
    }

    @Test
    @DisplayName("T06 — Empate múltiple: hay varios con misma distancia → positivo gana")
    void t06_multipleTie() {
        assertEquals(2, TemperatureAnalyzer.closestToZero(new int[] { -4, -2, 2, 4 }));
    }

    // ── Edge cases de entrada ──────────────────────────────────────────────────

    @Test
    @DisplayName("T07 — Array null → retorna 0")
    void t07_nullArray() {
        assertEquals(0, TemperatureAnalyzer.closestToZero(null));
    }

    @Test
    @DisplayName("T08 — Array vacío → retorna 0")
    void t08_emptyArray() {
        assertEquals(0, TemperatureAnalyzer.closestToZero(new int[] {}));
    }

    // ── Valores extremos ──────────────────────────────────────────────────────

    @Test
    @DisplayName("T09 — Todos positivos: retorna el mínimo")
    void t09_allPositive() {
        assertEquals(1, TemperatureAnalyzer.closestToZero(new int[] { 100, 50, 1, 5 }));
    }

    @Test
    @DisplayName("T10 — Todos negativos: retorna el máximo (más cercano a 0)")
    void t10_allNegative() {
        assertEquals(-1, TemperatureAnalyzer.closestToZero(new int[] { -100, -50, -1, -5 }));
    }

    @Test
    @DisplayName("T11 — Valores grandes: Integer.MAX_VALUE vs -1")
    void t11_largeValues() {
        assertEquals(-1, TemperatureAnalyzer.closestToZero(
                new int[] { Integer.MAX_VALUE, -1, Integer.MIN_VALUE }));
    }

    @Test
    @DisplayName("T12 — Integer.MIN_VALUE y Integer.MAX_VALUE: MAX_VALUE gana (positivo y |MIN| > |MAX|)")
    void t12_minMaxValues() {
        // |MAX_VALUE| < |MIN_VALUE| → MAX_VALUE es más cercano a 0
        assertEquals(Integer.MAX_VALUE, TemperatureAnalyzer.closestToZero(
                new int[] { Integer.MIN_VALUE, Integer.MAX_VALUE }));
    }

    // ── Tests parametrizados ──────────────────────────────────────────────────

    record Case(int[] temps, int expected) {
    }

    static Stream<Case> cases() {
        return Stream.of(
                new Case(new int[] { -273, 10, 5, -5 }, 5),
                new Case(new int[] { 15, -3, 6, -9, 12 }, -3),
                new Case(new int[] { 0 }, 0),
                new Case(new int[] { -1, 1 }, 1),
                new Case(new int[] { 2, -2, 3, -3 }, 2));
    }

    @ParameterizedTest(name = "T13 — caso {index}")
    @MethodSource("cases")
    @DisplayName("T13 — Tests parametrizados variados")
    void t13_parameterized(Case c) {
        assertEquals(c.expected(), TemperatureAnalyzer.closestToZero(c.temps()));
    }

    @Test
    @DisplayName("T14 — Array con un solo cero → retorna 0")
    void t14_singleZero() {
        assertEquals(0, TemperatureAnalyzer.closestToZero(new int[] { 0 }));
    }

    @Test
    @DisplayName("T15 — Múltiples ceros → retorna 0")
    void t15_multipleZeros() {
        assertEquals(0, TemperatureAnalyzer.closestToZero(new int[] { 0, -5, 0, 3 }));
    }
}
