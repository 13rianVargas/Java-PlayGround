import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Problema 1 — Ventana Deslizante")
class Problem1Test {

    @Test
    @DisplayName("T01 — Ejemplo del enunciado")
    void t01_enunciado() {
        assertEquals(550L, Problem1.maxWindowSum(new int[] { 100, 200, 150, 300, 250 }, 2));
    }

    @Test
    @DisplayName("T02 — Todos iguales")
    void t02_allEqual() {
        assertEquals(15L, Problem1.maxWindowSum(new int[] { 5, 5, 5, 5, 5 }, 3));
    }

    @Test
    @DisplayName("T03 — k = 1 retorna el máximo elemento")
    void t03_windowOne() {
        assertEquals(1000L, Problem1.maxWindowSum(new int[] { 1000 }, 1));
    }

    @Test
    @DisplayName("T04 — k == length, retorna suma de todo el array")
    void t04_windowEqualsLength() {
        assertEquals(600L, Problem1.maxWindowSum(new int[] { 100, 200, 300 }, 3));
    }

    @Test
    @DisplayName("T05 — Ventana máxima al principio")
    void t05_maxAtBeginning() {
        assertEquals(900L, Problem1.maxWindowSum(new int[] { 500, 400, 100, 50 }, 2));
    }

    @Test
    @DisplayName("T06 — Valores negativos")
    void t06_negativeValues() {
        assertEquals(-1L, Problem1.maxWindowSum(new int[] { -3, -1, -4, -2 }, 2));
    }

    @Test
    @DisplayName("T07 — Valores grandes (evita int overflow con long)")
    void t07_largeValues() {
        int[] amounts = { Integer.MAX_VALUE, Integer.MAX_VALUE };
        long expected = (long) Integer.MAX_VALUE * 2;
        assertEquals(expected, Problem1.maxWindowSum(amounts, 2));
    }

    // amounts = {10, 3, 9, 7, 6}
    // k=1: max(10,3,9,7,6) = 10
    // k=2: max(13,12,16,13) = 16
    // k=3: max(22,19,22) = 22
    // k=4: max(29,25) = 29
    // k=5: (35) = 35
    @ParameterizedTest(name = "T08 — k={0} → {1}")
    @CsvSource({
            "1, 10",
            "2, 16",
            "3, 22",
            "4, 29",
            "5, 35"
    })
    @DisplayName("T08 — Diferentes tamaños de ventana en [10, 3, 9, 7, 6]")
    void t08_parameterized(int k, long expected) {
        int[] amounts = { 10, 3, 9, 7, 6 };
        assertEquals(expected, Problem1.maxWindowSum(amounts, k));
    }
}
