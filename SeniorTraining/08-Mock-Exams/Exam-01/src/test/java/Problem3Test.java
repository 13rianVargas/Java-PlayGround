import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Problema 3 — Detector de Anomalías (18 tests)")
class Problem3Test {

    // ── Tests básicos del enunciado ────────────────────────────────────────────

    @Test
    @DisplayName("T01 — Ejemplo del enunciado")
    void t01_enunciado_example() {
        int[] readings = { 10, 12, 8, 15, 14 };
        List<String> expected = List.of("NORMAL", "NORMAL", "LOW", "HIGH", "NORMAL");
        assertEquals(expected, Problem3.classifyReadings(readings, 3));
    }

    @Test
    @DisplayName("T02 — Primera lectura siempre NORMAL")
    void t02_firstReadingAlwaysNormal() {
        int[] readings = { 42 };
        assertEquals(List.of("NORMAL"), Problem3.classifyReadings(readings, 0));
    }

    @Test
    @DisplayName("T03 — Lecturas iguales → todas NORMAL")
    void t03_allSameReadings() {
        int[] readings = { 5, 5, 5, 5 };
        List<String> result = Problem3.classifyReadings(readings, 0);
        assertEquals(List.of("NORMAL", "NORMAL", "NORMAL", "NORMAL"), result);
    }

    @Test
    @DisplayName("T04 — Diferencia exactamente 'threshold' → NORMAL")
    void t04_exactThresholdIsNormal() {
        // diff = 3, threshold = 3 → 3 > 3 es false → NORMAL
        int[] readings = { 0, 3 };
        assertEquals(List.of("NORMAL", "NORMAL"), Problem3.classifyReadings(readings, 3));
    }

    @Test
    @DisplayName("T05 — Diferencia exactamente 'threshold + 1' → HIGH")
    void t05_thresholdPlusOneIsHigh() {
        // diff = 4, threshold = 3 → 4 > 3 → HIGH
        int[] readings = { 0, 4 };
        assertEquals(List.of("NORMAL", "HIGH"), Problem3.classifyReadings(readings, 3));
    }

    @Test
    @DisplayName("T06 — Bajada exactamente 'threshold + 1' → LOW")
    void t06_thresholdPlusOneIsLow() {
        // diff anterior - actual = 4, threshold = 3 → LOW
        int[] readings = { 10, 6 };
        assertEquals(List.of("NORMAL", "LOW"), Problem3.classifyReadings(readings, 3));
    }

    // ── Edge cases de entrada ──────────────────────────────────────────────────

    @Test
    @DisplayName("T07 — readings null → lista vacía")
    void t07_nullReadings() {
        assertEquals(List.of(), Problem3.classifyReadings(null, 5));
    }

    @Test
    @DisplayName("T08 — readings vacío → lista vacía")
    void t08_emptyReadings() {
        assertEquals(List.of(), Problem3.classifyReadings(new int[] {}, 5));
    }

    @Test
    @DisplayName("T09 — threshold negativo → lista vacía")
    void t09_negativeThreshold() {
        int[] readings = { 1, 2, 3 };
        assertEquals(List.of(), Problem3.classifyReadings(readings, -1));
    }

    @Test
    @DisplayName("T10 — threshold = 0: cualquier cambio es HIGH o LOW")
    void t10_thresholdZero() {
        int[] readings = { 5, 6, 5 };
        assertEquals(List.of("NORMAL", "HIGH", "LOW"), Problem3.classifyReadings(readings, 0));
    }

    // ── Lecturas con valores negativos ────────────────────────────────────────

    @Test
    @DisplayName("T11 — Lecturas negativas: de negativo a positivo")
    void t11_negativeToPositive() {
        // diff = 5 - (-10) = 15, threshold = 10 → HIGH
        int[] readings = { -10, 5 };
        assertEquals(List.of("NORMAL", "HIGH"), Problem3.classifyReadings(readings, 10));
    }

    @Test
    @DisplayName("T12 — Lecturas negativas: ambas negativas")
    void t12_bothNegative() {
        int[] readings = { -100, -103, -97 };
        // -103 - (-100) = -3, abs → reverso: -100 - (-103) = 3 → threshold=2 → LOW
        // -97 - (-103) = 6 → threshold=2 → HIGH
        assertEquals(List.of("NORMAL", "LOW", "HIGH"), Problem3.classifyReadings(readings, 2));
    }

    // ── Integer overflow ──────────────────────────────────────────────────────

    @Test
    @DisplayName("T13 — Integer overflow: MIN_VALUE a MAX_VALUE → HIGH (requiere long)")
    void t13_overflowMinToMax() {
        // Con int: MAX_VALUE - MIN_VALUE overflows. Con long: es correcto → HIGH
        int[] readings = { Integer.MIN_VALUE, Integer.MAX_VALUE };
        List<String> result = Problem3.classifyReadings(readings, Integer.MAX_VALUE);
        assertEquals("NORMAL", result.get(0));
        assertEquals("HIGH", result.get(1),
                "Requiere aritmética long para evitar overflow");
    }

    @Test
    @DisplayName("T14 — Integer overflow: MAX_VALUE a MIN_VALUE → LOW (requiere long)")
    void t14_overflowMaxToMin() {
        int[] readings = { Integer.MAX_VALUE, Integer.MIN_VALUE };
        List<String> result = Problem3.classifyReadings(readings, Integer.MAX_VALUE);
        assertEquals("LOW", result.get(1),
                "Requiere aritmética long para evitar overflow");
    }

    // ── Validación del tamaño del resultado ───────────────────────────────────

    @Test
    @DisplayName("T15 — El resultado tiene exactamente readings.length elementos")
    void t15_resultSizeEqualsInputLength() {
        int[] readings = { 1, 3, 2, 8, 7, 15 };
        List<String> result = Problem3.classifyReadings(readings, 5);
        assertEquals(readings.length, result.size());
    }

    @Test
    @DisplayName("T16 — Todos los valores son HIGH/NORMAL/LOW (no otros strings)")
    void t16_valuesAreValidStrings() {
        int[] readings = { 10, 20, 5, 15, 14, 50 };
        List<String> valid = List.of("HIGH", "LOW", "NORMAL");
        List<String> result = Problem3.classifyReadings(readings, 3);
        for (String s : result) {
            assertTrue(valid.contains(s), "Valor inesperado: " + s);
        }
    }

    // ── Tests parametrizados ──────────────────────────────────────────────────

    record Case(int[] readings, int threshold, List<String> expected) {
    }

    static Stream<Case> cases() {
        return Stream.of(
                new Case(new int[] { 1, 2 }, 0, List.of("NORMAL", "HIGH")),
                new Case(new int[] { 2, 1 }, 0, List.of("NORMAL", "LOW")),
                new Case(new int[] { 100, 100 }, 0, List.of("NORMAL", "NORMAL")),
                new Case(new int[] { 0, 10, 0 }, 5, List.of("NORMAL", "HIGH", "LOW")),
                new Case(new int[] { 10, 10, 10 }, 0, List.of("NORMAL", "NORMAL", "NORMAL")));
    }

    @ParameterizedTest(name = "T17 — caso {index}")
    @MethodSource("cases")
    @DisplayName("T17 — Tests parametrizados variados")
    void t17_parameterized(Case c) {
        assertEquals(c.expected(), Problem3.classifyReadings(c.readings(), c.threshold()));
    }

    @Test
    @DisplayName("T18 — threshold muy grande → todo NORMAL (excepto primer elemento)")
    void t18_veryLargeThreshold() {
        int[] readings = { 1, 1000, -1000, 500 };
        List<String> result = Problem3.classifyReadings(readings, Integer.MAX_VALUE);
        for (String s : result) {
            assertEquals("NORMAL", s);
        }
    }
}
