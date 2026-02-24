import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ============================================================
 * MÓDULO 06.03 — TDD Practice: Tests pre-escritos
 * ============================================================
 *
 * INSTRUCCIONES:
 * Estos tests ya están escritos. Tu trabajo es implementar
 * BankLedger.java hasta que TODOS estos tests pasen (🟢).
 *
 * Ejecuta: mvn test -pl 03-TDD-Practice
 *
 * WORKFLOW TDD:
 * 1. Ejecuta los tests → todos fallan (🔴)
 * 2. Implementa UN método a la vez
 * 3. Ejecuta tests → algunos pasan (🟢)
 * 4. Repite hasta que todos estén verdes
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("BankLedger — TDD Practice")
class BankLedgerTest {

    private BankLedger ledger;

    @BeforeEach
    void setUp() {
        ledger = new BankLedger();
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TESTS PARA addEntry
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @Order(1)
    @DisplayName("addEntry — agrega entry y retorna tamaño")
    void addEntry_returnsSize() {
        var entry = new BankLedger.Entry("E1", new BigDecimal("100"), "INCOME", false);

        assertEquals(1, ledger.addEntry(entry));
        assertEquals(1, ledger.size());
    }

    @Test
    @Order(2)
    @DisplayName("addEntry — múltiples entries incrementan tamaño")
    void addEntry_multipleEntries() {
        ledger.addEntry(new BankLedger.Entry("E1", new BigDecimal("100"), "INCOME", false));
        ledger.addEntry(new BankLedger.Entry("E2", new BigDecimal("200"), "INCOME", false));

        assertEquals(3, ledger.addEntry(
                new BankLedger.Entry("E3", new BigDecimal("300"), "EXPENSE", false)));
    }

    @Test
    @Order(3)
    @DisplayName("addEntry — null lanza NullPointerException")
    void addEntry_null_throwsNPE() {
        var ex = assertThrows(NullPointerException.class,
                () -> ledger.addEntry(null));
        assertEquals("entry cannot be null", ex.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("addEntry — id duplicado lanza IllegalArgumentException")
    void addEntry_duplicateId_throws() {
        ledger.addEntry(new BankLedger.Entry("E1", new BigDecimal("100"), "INCOME", false));

        var ex = assertThrows(IllegalArgumentException.class,
                () -> ledger.addEntry(
                        new BankLedger.Entry("E1", new BigDecimal("999"), "EXPENSE", false)));
        assertTrue(ex.getMessage().contains("duplicate id: E1"));
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TESTS PARA getBalance
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @Order(5)
    @DisplayName("getBalance — ledger vacío retorna 0.00")
    void getBalance_empty() {
        assertEquals(new BigDecimal("0.00"), ledger.getBalance());
    }

    @Test
    @Order(6)
    @DisplayName("getBalance — suma créditos y débitos")
    void getBalance_mixedEntries() {
        ledger.addEntry(new BankLedger.Entry("E1", new BigDecimal("1000.50"), "INCOME", false));
        ledger.addEntry(new BankLedger.Entry("E2", new BigDecimal("-300.25"), "EXPENSE", false));
        ledger.addEntry(new BankLedger.Entry("E3", new BigDecimal("200.75"), "INCOME", false));

        assertEquals(new BigDecimal("901.00"), ledger.getBalance());
    }

    @Test
    @Order(7)
    @DisplayName("getBalance — resultado con scale 2")
    void getBalance_scale2() {
        ledger.addEntry(new BankLedger.Entry("E1", new BigDecimal("100.333"), "INCOME", false));
        ledger.addEntry(new BankLedger.Entry("E2", new BigDecimal("200.666"), "INCOME", false));

        BigDecimal balance = ledger.getBalance();
        assertEquals(2, balance.scale());
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TESTS PARA getBalanceByCategory
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @Order(8)
    @DisplayName("getBalanceByCategory — ledger vacío retorna map vacío")
    void getBalanceByCategory_empty() {
        assertTrue(ledger.getBalanceByCategory().isEmpty());
    }

    @Test
    @Order(9)
    @DisplayName("getBalanceByCategory — agrupa correctamente")
    void getBalanceByCategory_grouped() {
        ledger.addEntry(new BankLedger.Entry("E1", new BigDecimal("500"), "INCOME", false));
        ledger.addEntry(new BankLedger.Entry("E2", new BigDecimal("300"), "INCOME", false));
        ledger.addEntry(new BankLedger.Entry("E3", new BigDecimal("-200"), "EXPENSE", false));
        ledger.addEntry(new BankLedger.Entry("E4", new BigDecimal("-100"), "EXPENSE", false));

        Map<String, BigDecimal> result = ledger.getBalanceByCategory();

        assertAll(
                () -> assertEquals(new BigDecimal("800.00"), result.get("INCOME")),
                () -> assertEquals(new BigDecimal("-300.00"), result.get("EXPENSE")),
                () -> assertEquals(2, result.size()));
    }

    @Test
    @Order(10)
    @DisplayName("getBalanceByCategory — mapa es inmodificable")
    void getBalanceByCategory_unmodifiable() {
        ledger.addEntry(new BankLedger.Entry("E1", new BigDecimal("100"), "INCOME", false));

        Map<String, BigDecimal> result = ledger.getBalanceByCategory();

        assertThrows(UnsupportedOperationException.class,
                () -> result.put("HACK", BigDecimal.ONE));
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TESTS PARA getUnreconciledEntries
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @Order(11)
    @DisplayName("getUnreconciledEntries — retorna solo no reconciliadas")
    void getUnreconciledEntries_filtersCorrectly() {
        ledger.addEntry(new BankLedger.Entry("E1", new BigDecimal("100"), "INCOME", false));
        ledger.addEntry(new BankLedger.Entry("E2", new BigDecimal("200"), "INCOME", true));
        ledger.addEntry(new BankLedger.Entry("E3", new BigDecimal("300"), "INCOME", false));

        List<BankLedger.Entry> unreconciled = ledger.getUnreconciledEntries();

        assertEquals(2, unreconciled.size());
        assertTrue(unreconciled.stream().noneMatch(BankLedger.Entry::reconciled));
    }

    @Test
    @Order(12)
    @DisplayName("getUnreconciledEntries — ordenadas por amount desc")
    void getUnreconciledEntries_sortedDescending() {
        ledger.addEntry(new BankLedger.Entry("E1", new BigDecimal("100"), "A", false));
        ledger.addEntry(new BankLedger.Entry("E2", new BigDecimal("500"), "B", false));
        ledger.addEntry(new BankLedger.Entry("E3", new BigDecimal("300"), "C", false));

        List<BankLedger.Entry> result = ledger.getUnreconciledEntries();

        assertEquals("E2", result.get(0).id());
        assertEquals("E3", result.get(1).id());
        assertEquals("E1", result.get(2).id());
    }

    @Test
    @Order(13)
    @DisplayName("getUnreconciledEntries — lista es inmutable")
    void getUnreconciledEntries_immutable() {
        ledger.addEntry(new BankLedger.Entry("E1", new BigDecimal("100"), "A", false));

        List<BankLedger.Entry> result = ledger.getUnreconciledEntries();

        assertThrows(UnsupportedOperationException.class,
                () -> result.add(new BankLedger.Entry("HACK", BigDecimal.ONE, "X", false)));
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TESTS PARA reconcileById
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @Order(14)
    @DisplayName("reconcileById — marca entry como reconciled")
    void reconcileById_marksReconciled() {
        ledger.addEntry(new BankLedger.Entry("E1", new BigDecimal("100"), "INCOME", false));

        assertTrue(ledger.reconcileById("E1"));
        assertTrue(ledger.getUnreconciledEntries().isEmpty());
    }

    @Test
    @Order(15)
    @DisplayName("reconcileById — id no encontrado retorna false")
    void reconcileById_notFound() {
        assertFalse(ledger.reconcileById("NOPE"));
    }

    @Test
    @Order(16)
    @DisplayName("reconcileById — ya reconciled retorna false")
    void reconcileById_alreadyReconciled() {
        ledger.addEntry(new BankLedger.Entry("E1", new BigDecimal("100"), "INCOME", true));

        assertFalse(ledger.reconcileById("E1"));
    }

    @Test
    @Order(17)
    @DisplayName("reconcileById — null lanza NullPointerException")
    void reconcileById_null_throws() {
        var ex = assertThrows(NullPointerException.class,
                () -> ledger.reconcileById(null));
        assertEquals("id cannot be null", ex.getMessage());
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TESTS PARA findLargestDebit
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @Order(18)
    @DisplayName("findLargestDebit — retorna el débito más grande")
    void findLargestDebit_found() {
        ledger.addEntry(new BankLedger.Entry("E1", new BigDecimal("-100"), "EXPENSE", false));
        ledger.addEntry(new BankLedger.Entry("E2", new BigDecimal("-500"), "EXPENSE", false));
        ledger.addEntry(new BankLedger.Entry("E3", new BigDecimal("-200"), "EXPENSE", false));
        ledger.addEntry(new BankLedger.Entry("E4", new BigDecimal("1000"), "INCOME", false));

        Optional<BankLedger.Entry> result = ledger.findLargestDebit();

        assertTrue(result.isPresent());
        assertEquals("E2", result.get().id());
    }

    @Test
    @Order(19)
    @DisplayName("findLargestDebit — sin débitos retorna empty")
    void findLargestDebit_noDebits() {
        ledger.addEntry(new BankLedger.Entry("E1", new BigDecimal("100"), "INCOME", false));

        assertTrue(ledger.findLargestDebit().isEmpty());
    }

    @Test
    @Order(20)
    @DisplayName("findLargestDebit — ledger vacío retorna empty")
    void findLargestDebit_emptyLedger() {
        assertTrue(ledger.findLargestDebit().isEmpty());
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TESTS PARA removeById
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @Order(21)
    @DisplayName("removeById — elimina entry existente")
    void removeById_existing() {
        ledger.addEntry(new BankLedger.Entry("E1", new BigDecimal("100"), "A", false));
        ledger.addEntry(new BankLedger.Entry("E2", new BigDecimal("200"), "B", false));

        assertTrue(ledger.removeById("E1"));
        assertEquals(1, ledger.size());
    }

    @Test
    @Order(22)
    @DisplayName("removeById — id no existe retorna false")
    void removeById_notFound() {
        assertFalse(ledger.removeById("NOPE"));
    }

    @Test
    @Order(23)
    @DisplayName("removeById — null lanza NullPointerException")
    void removeById_null_throws() {
        var ex = assertThrows(NullPointerException.class,
                () -> ledger.removeById(null));
        assertEquals("id cannot be null", ex.getMessage());
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST DE INTEGRACIÓN — Flujo completo
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @Order(99)
    @DisplayName("Flujo completo: add → balance → reconcile → remove")
    void fullWorkflow() {
        // Agregar entries
        ledger.addEntry(new BankLedger.Entry("SALARY", new BigDecimal("5000"), "INCOME", false));
        ledger.addEntry(new BankLedger.Entry("RENT", new BigDecimal("-1500"), "HOUSING", false));
        ledger.addEntry(new BankLedger.Entry("FOOD", new BigDecimal("-300"), "GROCERIES", false));
        ledger.addEntry(new BankLedger.Entry("BONUS", new BigDecimal("1000"), "INCOME", false));

        // Verificar balance
        assertEquals(new BigDecimal("4200.00"), ledger.getBalance());

        // Verificar categorías
        Map<String, BigDecimal> byCategory = ledger.getBalanceByCategory();
        assertEquals(new BigDecimal("6000.00"), byCategory.get("INCOME"));
        assertEquals(new BigDecimal("-1500.00"), byCategory.get("HOUSING"));

        // Reconciliar
        assertTrue(ledger.reconcileById("SALARY"));
        assertEquals(3, ledger.getUnreconciledEntries().size());

        // Encontrar mayor débito
        assertEquals("RENT", ledger.findLargestDebit().orElseThrow().id());

        // Eliminar entry
        assertTrue(ledger.removeById("FOOD"));
        assertEquals(3, ledger.size());
        assertEquals(new BigDecimal("4500.00"), ledger.getBalance());
    }
}
