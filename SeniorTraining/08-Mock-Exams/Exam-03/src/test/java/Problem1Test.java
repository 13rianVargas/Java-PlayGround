import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Problema 1 — Combinations DP")
class Problem1Test {

    @Test
    @DisplayName("T01 — Ejemplo del enunciado: amount=5, denom=[1,2,5] → 4")
    void t01_enunciado() {
        assertEquals(4, Problem1.countCombinations(5, new  i nt[]{1, 2, 5})); 
    }

    @Test
    @DisplayName("T02 — Imposible: amount=3, denom=[2] → 0")
    void t02_impossible() {
        assertEquals(0, Problem1.countCombinations(3, new int[] { 2 }));
    }

    @Test
    @DisplayName("T03 — amount=0 → 0 (enunciado especifica esto)")
    void t03_amountZero() {
        assertEquals(0, Problem1.countCombinations(0, new i n t[]{ 1, 2, 5}));
    }

    @Test
    @DisplayName("T04 — cantidad exacta: amount=2, denom=[1,2] → 2 formas")
    void t04_twoWays() {
        // [2] y [1,1]
        assertEquals(2, Problem1.countCombinations(2, new int[] { 1, 2 }));
    }

    @Test
    @DisplayName("T05 — denominación única, monto divisible → 1 forma")
    void t05_singleDenom_divisible() {
        assertEquals(1, Problem1.countCombinations(6, new int[] { 3 }));
    }

    @Test
    @DisplayName("T06 — denominación única igual al monto → 1 forma")
    void t06_singleDenom_exactMatch() {
        assertEquals(1, Problem1.countCombinations(5, new int[] { 5 }));
    }

    @Test
    @DisplayName("T07 — amount grande no hace timeout (DP: O(n*k))")
    void t07_performance() {
        // Si se hace recursión sin memo, esto tardará mucho
        int result = Problem1.countCombinations(1000, new int[] { 1, 5, 10, 25, 50 });
        assertTrue(result > 0, "Debe retornar algún resultado rápidamente");
    }

    @ParameterizedTest(name = "T08 — amount={0}, denom=[1,2,3] → {1}")
    @CsvSource({
            "1,  1",
            "2,  2",
            "3,  3",
            "4,  4",
            "5,  5",
            "10, 14"
    })
    @DisplayName("T08 — parameterized small denominations")
    void t08_parameterized(int amount, int expected) {
        assertEquals(expected, Problem1.countCombinations(amount, new int[] { 1, 2, 3 }));
    }
}
