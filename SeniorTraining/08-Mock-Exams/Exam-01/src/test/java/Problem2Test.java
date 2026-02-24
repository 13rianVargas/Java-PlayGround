import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import Problem2.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Problema 2 — Análisis de Transacciones")
class Problem2Test {

    private List<Transaction> txns;

    @BeforeEach
    void setUp() {
        txns = List.of(
                new Transaction("TX001", "RETAIL", 1500.0, false),
                new Transaction("TX002", "TRAVEL", 8500.0, true),
                new Transaction("TX003", "RETAIL", 300.0, false),
                new Transaction("TX004", "FOOD", 200.0, false),
                new Transaction("TX005", "TRAVEL", 5000.0, true),
                new Transaction("TX006", "FOOD", 9500.0, true),
                new Transaction("TX007", "RETAIL", 4000.0, false));
    }

    // ── 2a. flaggedIds ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("T01 — flaggedIds retorna IDs correctos")
    void t01_flaggedIds_correctIds() {
        List<String> result = Problem2.flaggedIds(txns);
        assertEquals(3, result.size());
        assertTrue(result.containsAll(List.of("TX002", "TX005", "TX006")));
    }

    @Test
    @DisplayName("T02 — flaggedIds retorna IDs en orden alfabético")
    void t02_flaggedIds_sortedAlphabetically() {
        List<String> result = Problem2.flaggedIds(txns);
        assertEquals(List.of("TX002", "TX005", "TX006"), result);
    }

    @Test
    @DisplayName("T03 — flaggedIds con lista vacía retorna lista vacía")
    void t03_flaggedIds_emptyList() {
        List<String> result = Problem2.flaggedIds(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("T04 — flaggedIds sin ninguna flagged retorna lista vacía")
    void t04_flaggedIds_noneFlags() {
        List<Transaction> noFlags = List.of(
                new Transaction("A1", "FOOD", 100.0, false),
                new Transaction("A2", "FOOD", 200.0, false));
        assertTrue(Problem2.flaggedIds(noFlags).isEmpty());
    }

    // ── 2b. totalByCategory ────────────────────────────────────────────────────

    @Test
    @DisplayName("T05 — totalByCategory excluye transacciones flagged")
    void t05_totalByCategory_excludesFlagged() {
        Map<String, Double> result = Problem2.totalByCategory(txns);
        assertFalse(result.containsKey("TRAVEL"), "TRAVEL solo tiene flagged, no debe aparecer en el mapa");
    }

    @Test
    @DisplayName("T06 — totalByCategory suma correctamente por categoría")
    void t06_totalByCategory_correctSums() {
        Map<String, Double> result = Problem2.totalByCategory(txns);
        // RETAIL: TX001(1500) + TX003(300) + TX007(4000) = 5800
        assertEquals(5800.0, result.get("RETAIL"), 0.001);
        // FOOD: TX004(200) — TX006 es flagged
        assertEquals(200.0, result.get("FOOD"), 0.001);
    }

    @Test
    @DisplayName("T07 — totalByCategory con lista vacía retorna mapa vacío")
    void t07_totalByCategory_emptyList() {
        Map<String, Double> result = Problem2.totalByCategory(List.of());
        assertTrue(result.isEmpty());
    }

    // ── 2c. highestRisk ────────────────────────────────────────────────────────

    @Test
    @DisplayName("T08 — highestRisk retorna la flagged con mayor monto")
    void t08_highestRisk_returnsHighestFlagged() {
        Optional<Transaction> result = Problem2.highestRisk(txns);
        assertTrue(result.isPresent());
        assertEquals("TX006", result.get().id()); // TX006 con 9500
    }

    @Test
    @DisplayName("T09 — highestRisk retorna Optional.empty si no hay ninguna flagged")
    void t09_highestRisk_noFlagged() {
        List<Transaction> noFlags = List.of(
                new Transaction("X1", "RETAIL", 100.0, false));
        assertTrue(Problem2.highestRisk(noFlags).isEmpty());
    }

    @Test
    @DisplayName("T10 — highestRisk con lista vacía retorna Optional.empty")
    void t10_highestRisk_emptyList() {
        assertTrue(Problem2.highestRisk(List.of()).isEmpty());
    }

    // ── 2d. hasExcessiveAmount ─────────────────────────────────────────────────

    @Test
    @DisplayName("T11 — hasExcessiveAmount con threshold menor que el máximo → true")
    void t11_hasExcessiveAmount_true() {
        assertTrue(Problem2.hasExcessiveAmount(txns, 9000.0));
    }

    @Test
    @DisplayName("T12 — hasExcessiveAmount con threshold igual al máximo → false")
    void t12_hasExcessiveAmount_equalsMax_false() {
        // El máximo es 9500. 9500 > 9500 es false
        assertFalse(Problem2.hasExcessiveAmount(txns, 9500.0));
    }

    @Test
    @DisplayName("T13 — hasExcessiveAmount con threshold muy alto → false")
    void t13_hasExcessiveAmount_highThreshold_false() {
        assertFalse(Problem2.hasExcessiveAmount(txns, 100_000.0));
    }

    @Test
    @DisplayName("T14 — hasExcessiveAmount con lista vacía → false")
    void t14_hasExcessiveAmount_emptyList() {
        assertFalse(Problem2.hasExcessiveAmount(List.of(), 0));
    }
}
