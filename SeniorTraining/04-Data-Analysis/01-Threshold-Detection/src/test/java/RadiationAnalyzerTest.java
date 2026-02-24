import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ════════════════════════════════════════════════════════════
 * RadiationAnalyzerTest — 22 Tests Exhaustivos
 * ════════════════════════════════════════════════════════════
 *
 * Para correr: cd 04-Data-Analysis && mvn test
 *
 * Estructura del test suite:
 * - Tests básicos con ejemplos del enunciado
 * - Tests de edge cases (array vacío, null, un elemento, threshold negativo)
 * - Tests de valores en el límite exacto (boundary values)
 * - Tests parametrizados para cobertura múltiple
 * - Tests de casos complejos (zig-zag, negativos, valores grandes)
 */
@DisplayName("RadiationAnalyzer — Comprehensive Test Suite")
class RadiationAnalyzerTest {

    // ─────────────────────────────────────────────────────────────────────
    // GRUPO 1: Tests del ejemplo del enunciado
    // ─────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("T01: Ejemplo del enunciado — lecturas mixtas con threshold=3")
    void exampleFromEnunciado() {
        int[] readings = { 10, 12, 8, 15, 14 };
        List<String> result = RadiationAnalyzer.analyze(readings, 3);
        assertEquals(List.of("NORMAL", "NORMAL", "LOW", "HIGH", "NORMAL"), result);
    }

    @Test
    @DisplayName("T02: Todas las lecturas normales")
    void allNormal() {
        int[] readings = { 10, 11, 12, 11, 10 };
        List<String> result = RadiationAnalyzer.analyze(readings, 3);
        assertEquals(List.of("NORMAL", "NORMAL", "NORMAL", "NORMAL", "NORMAL"), result);
    }

    @Test
    @DisplayName("T03: Salto exactamente igual al threshold → NORMAL (no es HIGH)")
    void exactlyAtThresholdIsNormal() {
        int[] readings = { 10, 13 }; // diferencia = 3, threshold = 3
        List<String> result = RadiationAnalyzer.analyze(readings, 3);
        assertEquals(List.of("NORMAL", "NORMAL"), result,
                "Con diferencia == threshold debe ser NORMAL (el operador es >, no >=)");
    }

    @Test
    @DisplayName("T04: Salto de threshold+1 → HIGH")
    void oneAboveThresholdIsHigh() {
        int[] readings = { 10, 14 }; // diferencia = 4, threshold = 3
        List<String> result = RadiationAnalyzer.analyze(readings, 3);
        assertEquals(List.of("NORMAL", "HIGH"), result,
                "Con diferencia == threshold + 1 debe ser HIGH");
    }

    @Test
    @DisplayName("T05: Caída de threshold+1 → LOW")
    void oneAboveThresholdIsLow() {
        int[] readings = { 14, 10 }; // diferencia = 4, threshold = 3
        List<String> result = RadiationAnalyzer.analyze(readings, 3);
        assertEquals(List.of("NORMAL", "LOW"), result);
    }

    // ─────────────────────────────────────────────────────────────────────
    // GRUPO 2: Edge Cases — los que eliminan candidatos Mid
    // ─────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("T06: Array null → lista vacía")
    void nullArrayReturnsEmpty() {
        List<String> result = RadiationAnalyzer.analyze(null, 5);
        assertNotNull(result, "No debe retornar null, sino lista vacía");
        assertTrue(result.isEmpty(), "Array null debe retornar lista vacía");
    }

    @Test
    @DisplayName("T07: Array vacío → lista vacía")
    void emptyArrayReturnsEmpty() {
        List<String> result = RadiationAnalyzer.analyze(new int[0], 5);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("T08: Un solo elemento → [NORMAL]")
    void singleElement() {
        List<String> result = RadiationAnalyzer.analyze(new int[] { 42 }, 5);
        assertEquals(List.of("NORMAL"), result,
                "Un solo elemento siempre es NORMAL (no tiene anterior)");
    }

    @Test
    @DisplayName("T09: Threshold = 0 → cualquier cambio es HIGH o LOW")
    void zeroThresholdMakesEverythingAnomaly() {
        int[] readings = { 5, 5, 6, 5 }; // 5→5: diff=0 (no > 0), 5→6: HIGH, 6→5: LOW
        List<String> result = RadiationAnalyzer.analyze(readings, 0);
        assertEquals(List.of("NORMAL", "NORMAL", "HIGH", "LOW"), result,
                "Con threshold=0: diff > 0 es anomalía, diff == 0 es NORMAL");
    }

    @Test
    @DisplayName("T10: Threshold negativo → lista vacía")
    void negativeThresholdReturnsEmpty() {
        List<String> result = RadiationAnalyzer.analyze(new int[] { 1, 2, 3 }, -1);
        assertNotNull(result);
        assertTrue(result.isEmpty(), "Threshold negativo es inválido → lista vacía");
    }

    // ─────────────────────────────────────────────────────────────────────
    // GRUPO 3: Tests con valores especiales
    // ─────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("T11: Lecturas con valores negativos")
    void negativeReadings() {
        int[] readings = { -10, -5, -12, -3 };
        // -10 → NORMAL
        // -5 - (-10) = 5 > 3 → HIGH
        // -5 - (-12) = 7 > 3 → LOW
        // -3 - (-12) = 9 > 3 → HIGH
        List<String> result = RadiationAnalyzer.analyze(readings, 3);
        assertEquals(List.of("NORMAL", "HIGH", "LOW", "HIGH"), result);
    }

    @Test
    @DisplayName("T12: Lecturas todas iguales")
    void allSameValues() {
        int[] readings = { 7, 7, 7, 7, 7 };
        List<String> result = RadiationAnalyzer.analyze(readings, 2);
        assertEquals(5, result.size());
        assertTrue(result.stream().allMatch("NORMAL"::equals));
    }

    @Test
    @DisplayName("T13: Lecturas en escalera ascendente — todos HIGH")
    void stairUpAllHigh() {
        int[] readings = { 0, 10, 20, 30 }; // saltos de 10 > threshold=5
        List<String> result = RadiationAnalyzer.analyze(readings, 5);
        assertEquals(List.of("NORMAL", "HIGH", "HIGH", "HIGH"), result);
    }

    @Test
    @DisplayName("T14: Lecturas en escalera descendente — todos LOW")
    void stairDownAllLow() {
        int[] readings = { 30, 20, 10, 0 };
        List<String> result = RadiationAnalyzer.analyze(readings, 5);
        assertEquals(List.of("NORMAL", "LOW", "LOW", "LOW"), result);
    }

    @Test
    @DisplayName("T15: Patrón zig-zag HIGH/LOW alternados")
    void zigZagPattern() {
        int[] readings = { 10, 50, 5, 45, 0 }; // threshold = 10
        // 50 - 10 = 40 > 10 → HIGH
        // 50 - 5 = 45 > 10 → LOW
        // 45 - 5 = 40 > 10 → HIGH
        // 45 - 0 = 45 > 10 → LOW
        List<String> result = RadiationAnalyzer.analyze(readings, 10);
        assertEquals(List.of("NORMAL", "HIGH", "LOW", "HIGH", "LOW"), result);
    }

    @Test
    @DisplayName("T16: Dos lecturas — solo HIGH")
    void twoReadingsHigh() {
        assertAll(
                () -> assertEquals(List.of("NORMAL", "HIGH"),
                        RadiationAnalyzer.analyze(new int[] { 1, 100 }, 5)),
                () -> assertEquals(List.of("NORMAL", "NORMAL"),
                        RadiationAnalyzer.analyze(new int[] { 1, 6 }, 5)),
                () -> assertEquals(List.of("NORMAL", "NORMAL"),
                        RadiationAnalyzer.analyze(new int[] { 1, 6 }, 5)) // boundary exacta
        );
    }

    @Test
    @DisplayName("T17: Pico aislado alto en medio de normales")
    void isolatedPeak() {
        int[] readings = { 10, 10, 50, 10, 10 }; // threshold = 5
        List<String> result = RadiationAnalyzer.analyze(readings, 5);
        assertEquals(List.of("NORMAL", "NORMAL", "HIGH", "LOW", "NORMAL"), result);
    }

    @Test
    @DisplayName("T18: Lista retornada tiene exactamente el mismo tamaño que el input")
    void resultSameSize() {
        int[] readings = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        List<String> result = RadiationAnalyzer.analyze(readings, 2);
        assertEquals(readings.length, result.size());
    }

    @Test
    @DisplayName("T19: Threshold muy grande → todo NORMAL")
    void veryLargeThreshold() {
        int[] readings = { 1, 1000, -1000, 999 };
        List<String> result = RadiationAnalyzer.analyze(readings, Integer.MAX_VALUE);
        assertTrue(result.stream().allMatch("NORMAL"::equals),
                "Con threshold = MAX_VALUE ninguna diferencia puede superarlo");
    }

    @Test
    @DisplayName("T20: Valores Integer.MAX y MIN no causan overflow")
    void noIntegerOverflow() {
        // Si se hace MIN - MAX en int → overflow silencioso → resultado incorrecto
        // El código debería usar long para la diferencia
        int[] readings = { Integer.MIN_VALUE, Integer.MAX_VALUE };
        // La diferencia real es 2^32 - 1, que supera cualquier int threshold
        List<String> result = RadiationAnalyzer.analyze(readings, 100);
        assertNotNull(result);
        assertEquals(2, result.size());
        // El primero SIEMPRE es NORMAL
        assertEquals("NORMAL", result.get(0));
        // El segundo debe ser HIGH (la diferencia es enorme)
        assertEquals("HIGH", result.get(1),
                "ADVERTENCIA: ¿está calculando la diferencia con long para evitar overflow?");
    }

    // ─────────────────────────────────────────────────────────────────────
    // GRUPO 4: Tests Parametrizados — múltiples casos en uno
    // ─────────────────────────────────────────────────────────────────────

    @ParameterizedTest(name = "T21.{index}: readings={0}, threshold={1} → {2}")
    @MethodSource("provideSimpleCases")
    @DisplayName("T21: Tests parametrizados — casos simples")
    void parametrizedSimpleCases(int[] readings, int threshold, List<String> expected) {
        assertEquals(expected, RadiationAnalyzer.analyze(readings, threshold));
    }

    static Stream<Arguments> provideSimpleCases() {
        return Stream.of(
                // Lecturas de 2 elementos, varios thresholds
                Arguments.of(new int[] { 5, 5 }, 0, List.of("NORMAL", "NORMAL")), // diff=0, NOT > 0
                Arguments.of(new int[] { 5, 6 }, 0, List.of("NORMAL", "HIGH")), // diff=1 > 0
                Arguments.of(new int[] { 6, 5 }, 0, List.of("NORMAL", "LOW")), // diff=1 > 0
                Arguments.of(new int[] { 5, 10 }, 5, List.of("NORMAL", "NORMAL")), // diff=5, NOT > 5
                Arguments.of(new int[] { 5, 11 }, 5, List.of("NORMAL", "HIGH")), // diff=6 > 5
                Arguments.of(new int[] { 11, 5 }, 5, List.of("NORMAL", "LOW")), // diff=6 > 5

                // Array de un elemento
                Arguments.of(new int[] { 0 }, 0, List.of("NORMAL")),
                Arguments.of(new int[] { 99 }, 1, List.of("NORMAL")),

                // Tres elementos
                Arguments.of(new int[] { 1, 2, 3 }, 1, List.of("NORMAL", "NORMAL", "NORMAL")),
                Arguments.of(new int[] { 1, 3, 5 }, 1, List.of("NORMAL", "HIGH", "HIGH")));
    }

    @Test
    @DisplayName("T22: El resultado no contiene strings distintos de NORMAL, HIGH, LOW")
    void resultOnlyContainsValidValues() {
        int[] readings = { 10, 20, 5, 15, 12, 18, 8 };
        List<String> result = RadiationAnalyzer.analyze(readings, 4);
        List<String> validValues = List.of("NORMAL", "HIGH", "LOW");
        assertTrue(result.stream().allMatch(validValues::contains),
                "Todos los resultados deben ser NORMAL, HIGH o LOW. Encontrado: " + result);
    }
}
