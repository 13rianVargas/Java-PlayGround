import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Problema 1 — Búsqueda Eficiente")
class Problem1Test {

    // ── Tests básicos ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("T01 — Encuentra elemento en posición final")   
    void t01_findsLastElement() {
        int[] ids = {-9, 14, 37, 102};
        assertTrue(Problem1.exists(ids, 102));
    }

    @Test   
    @DisplayName("T02 — Elemento no existe")
    void t02_elementNotFound() {
        int[] ids = {-9, 14, 37, 102};
        assertFalse(Problem1.exists(ids, 36));
    }
   
    @Test
    @DisplayName("T03 — Encuentra elemento negativo al inicio")
    void t03_findsNegativeAtStart() {
        int[] ids = {-9, 14, 37, 102};
        assertTrue(Problem1.exists(ids, -9));
    }
   
    @Test
    @DisplayName("T04 — Cero no está en el array")
    void t04_zeroNotFound() {
        int[] ids = {-9, 14, 37, 102};
        assertFalse(Problem1.exists(ids, 0));
    }   

    @Test
    @DisplayName("T05 — Array de un solo elemento (existe)")
    void t05_singleElementFound() {
        assertTrue(Problem1.exists(new int[]{42}, 42));
    }   

    @Test
    @DisplayName("T06 — Array de un solo elemento (no existe)")
    void t06_singleElementNotFound() {
        assertFalse(Problem1.exists(new int[]{42}, 99));
    }

    @Test
    @DisplayName("T07 — Array vacío siempre retorna false")
    void t07_emptyArray() {
        assertFalse(Problem1.exists(new int[]{}, 5));
    }

    // ── Tests de rendimiento y valores extremos ────────────────────────────────

    @Test
    @DisplayName("T08 — Encuentra elemento en array de 1M elementos (debe ser O(log n))")
    void t08_largeArrayPerformance() {
        int[] largeArray = new int[1_000_000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i * 2; // pares: 0, 2, 4, ..., 1999998
        }
        assertTrue(Problem1.exists(largeArray, 999_998));
        assertFalse(Problem1.exists(largeArray, 999_999)); // impar, no existe
    }

    @Test
    @DisplayName("T09 — Valores negativos y positivos mezclados")
    void t09_negativeAndPositive() {
        int[] ids = {-1000, -50, -1, 0, 1, 50, 1000};
        assertTrue(Problem1.exists(ids, -1000));
        assertTrue(Problem1.exists(ids, 0));
        assertTrue(Problem1.exists(ids, 1000));
        assertFalse(Problem1.exists(ids, -999));
    }

    @Test
    @DisplayName("T10 — Valores extremos: Integer.MIN_VALUE y MAX_VALUE")
    void t10_integerBoundaries() {
        int[] ids = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
        assertTrue(Problem1.exists(ids, Integer.MIN_VALUE));
        assertTrue(Problem1.exists(ids, Integer.MAX_VALUE));
        assertFalse(Problem1.exists(ids, 2));
    }

    // ── Tests parametrizados ───────────────────────────────────────────────────

    @ParameterizedTest(name = "T11 — searchId={1} → {2}")
    @CsvSource({
        "5,  true",
        "10, true",
        "15, true",
        "3,  false",
        "6,  false",
        "20, false"
    })
    @DisplayName("T11 — Búsqueda parametrizada en [5, 10, 15]")
    void t11_parameterized(int searchId, boolean expected) {
        int[] ids = {5, 10, 15};
        assertEquals(expected, Problem1.exists(ids, searchId));
    }
}
