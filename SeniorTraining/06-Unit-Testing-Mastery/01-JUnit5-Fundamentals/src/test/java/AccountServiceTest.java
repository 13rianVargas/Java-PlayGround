import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ============================================================
 * MÓDULO 06.01 — JUnit 5 Fundamentals: Tests demostrativos
 * ============================================================
 *
 * Este archivo demuestra las features esenciales de JUnit 5:
 * - @Test, @DisplayName
 * - assertEquals, assertTrue, assertFalse, assertNull
 * - assertThrows (verificar excepciones)
 * - assertAll (agrupar assertions)
 * - @BeforeEach, @AfterEach (lifecycle)
 * - @Nested (tests agrupados por contexto)
 * - @Disabled (saltar tests temporalmente)
 */
@TestMethodOrder(OrderAnnotation.class)
class AccountServiceTest {

    private AccountService service;

    // ═══════════════════════════════════════════════════════════════════════════
    // LIFECYCLE — Se ejecuta antes/después de cada test
    // ═══════════════════════════════════════════════════════════════════════════

    @BeforeEach
    void setUp() {
        service = new AccountService();
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // 1. BASIC ASSERTIONS
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @Order(1)
    @DisplayName("calculateInterest — caso normal con valores positivos")
    void calculateInterest_normalCase() {
        BigDecimal result = service.calculateInterest(
                new BigDecimal("10000"), new BigDecimal("0.05"));

        assertEquals(new BigDecimal("500.00"), result,
                "10,000 × 0.05 debe ser 500.00");
    }

    @Test
    @Order(2)
    @DisplayName("calculateInterest — balance cero retorna cero")
    void calculateInterest_zeroBalance() {
        BigDecimal result = service.calculateInterest(
                BigDecimal.ZERO, new BigDecimal("0.05"));

        assertEquals(new BigDecimal("0.00"), result);
    }

    @Test
    @Order(3)
    @DisplayName("calculateInterest — redondeo HALF_UP correcto")
    void calculateInterest_roundingHalfUp() {
        // 1000 × 0.033 = 33.0 (pero probemos con un caso que requiera redondeo)
        BigDecimal result = service.calculateInterest(
                new BigDecimal("999"), new BigDecimal("0.033"));

        // 999 × 0.033 = 32.967 → HALF_UP → 32.97
        assertEquals(new BigDecimal("32.97"), result);
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // 2. assertThrows — Verificar excepciones esperadas
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @Order(4)
    @DisplayName("calculateInterest — null balance lanza IllegalArgumentException")
    void calculateInterest_nullBalance_throwsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.calculateInterest(null, new BigDecimal("0.05")));

        // Verificar el MENSAJE de la excepción (Senior check)
        assertTrue(ex.getMessage().contains("null"),
                "El mensaje debe indicar que el valor es null");
    }

    @Test
    @Order(5)
    @DisplayName("calculateInterest — balance negativo lanza IllegalArgumentException")
    void calculateInterest_negativeBalance_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculateInterest(new BigDecimal("-100"), new BigDecimal("0.05")));
    }

    @Test
    @Order(6)
    @DisplayName("calculateInterest — rate negativa lanza IllegalArgumentException")
    void calculateInterest_negativeRate_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculateInterest(new BigDecimal("1000"), new BigDecimal("-0.01")));
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // 3. assertAll — Agrupar múltiples assertions
    // Si una falla, las demás SIGUEN ejecutándose (a diferencia de
    // assertions separadas donde el test para en la primera falla).
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @Order(7)
    @DisplayName("transfer — exitoso verifica todos los campos del resultado")
    void transfer_successful_allFieldsCorrect() {
        var source = new AccountService.Account("S1", "Carlos", new BigDecimal("5000"), true);
        var dest = new AccountService.Account("D1", "María", new BigDecimal("1000"), true);

        var result = service.transfer(source, dest, new BigDecimal("1500"));

        // assertAll ejecuta TODAS las assertions y reporta TODAS las fallas
        assertAll("Transfer result",
                () -> assertTrue(result.success(), "should be successful"),
                () -> assertEquals("Transfer successful", result.message()),
                () -> assertEquals(new BigDecimal("3500"), result.newSourceBalance(),
                        "new balance should be 5000 - 1500 = 3500"));
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // 4. @Nested — Tests agrupados por contexto/escenario
    // ═══════════════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("Transfer — Edge Cases")
    class TransferEdgeCases {

        @Test
        @DisplayName("fondos insuficientes retorna failure")
        void insufficientFunds() {
            var source = new AccountService.Account("S1", "Carlos", new BigDecimal("100"), true);
            var dest = new AccountService.Account("D1", "María", new BigDecimal("1000"), true);

            var result = service.transfer(source, dest, new BigDecimal("500"));

            assertAll(
                    () -> assertFalse(result.success()),
                    () -> assertTrue(result.message().contains("Insufficient")),
                    () -> assertEquals(new BigDecimal("100"), result.newSourceBalance()));
        }

        @Test
        @DisplayName("cuenta origen inactiva retorna failure")
        void sourceInactive() {
            var source = new AccountService.Account("S1", "Carlos", new BigDecimal("5000"), false);
            var dest = new AccountService.Account("D1", "María", new BigDecimal("1000"), true);

            var result = service.transfer(source, dest, new BigDecimal("500"));

            assertFalse(result.success());
            assertTrue(result.message().contains("inactive"));
        }

        @Test
        @DisplayName("monto negativo retorna failure")
        void negativeAmount() {
            var source = new AccountService.Account("S1", "Carlos", new BigDecimal("5000"), true);
            var dest = new AccountService.Account("D1", "María", new BigDecimal("1000"), true);

            var result = service.transfer(source, dest, new BigDecimal("-100"));

            assertFalse(result.success());
            assertTrue(result.message().contains("positive"));
        }

        @Test
        @DisplayName("monto null retorna failure")
        void nullAmount() {
            var source = new AccountService.Account("S1", "Carlos", new BigDecimal("5000"), true);
            var dest = new AccountService.Account("D1", "María", new BigDecimal("1000"), true);

            var result = service.transfer(source, dest, null);

            assertFalse(result.success());
        }

        @Test
        @DisplayName("cuentas null retorna failure")
        void nullAccounts() {
            var result = service.transfer(null, null, new BigDecimal("100"));
            assertFalse(result.success());
        }

        @Test
        @DisplayName("transferencia del monto exacto del balance")
        void exactBalance() {
            var source = new AccountService.Account("S1", "Carlos", new BigDecimal("500"), true);
            var dest = new AccountService.Account("D1", "María", new BigDecimal("1000"), true);

            var result = service.transfer(source, dest, new BigDecimal("500"));

            assertAll(
                    () -> assertTrue(result.success()),
                    () -> assertEquals(BigDecimal.ZERO, result.newSourceBalance()));
        }
    }

    @Nested
    @DisplayName("Filter Active Accounts")
    class FilterActiveTests {

        private List<AccountService.Account> accounts;

        @BeforeEach
        void setUpAccounts() {
            accounts = List.of(
                    new AccountService.Account("A1", "Carlos", new BigDecimal("10000"), true),
                    new AccountService.Account("A2", "María", new BigDecimal("500"), true),
                    new AccountService.Account("A3", "Juan", new BigDecimal("50000"), false),
                    new AccountService.Account("A4", "Ana", new BigDecimal("8000"), true),
                    new AccountService.Account("A5", "Pedro", new BigDecimal("200"), true));
        }

        @Test
        @DisplayName("filtra cuentas activas con balance mínimo")
        void filterWithMinBalance() {
            var result = service.filterActiveAccounts(accounts, new BigDecimal("1000"));

            assertEquals(2, result.size());
            assertEquals("A1", result.get(0).id(), "Primero: mayor balance");
            assertEquals("A4", result.get(1).id());
        }

        @Test
        @DisplayName("lista null retorna lista vacía")
        void nullList() {
            var result = service.filterActiveAccounts(null, BigDecimal.ZERO);
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("resultado ordenado por balance descendente")
        void orderedByBalanceDesc() {
            var result = service.filterActiveAccounts(accounts, BigDecimal.ZERO);

            for (int i = 0; i < result.size() - 1; i++) {
                assertTrue(result.get(i).balance().compareTo(result.get(i + 1).balance()) >= 0,
                        "Should be sorted descending by balance");
            }
        }
    }

    @Nested
    @DisplayName("Balance Calculations")
    class BalanceTests {

        @Test
        @DisplayName("totalBalance suma correctamente")
        void totalBalance() {
            var accounts = List.of(
                    new AccountService.Account("A1", "Carlos", new BigDecimal("100"), true),
                    new AccountService.Account("A2", "María", new BigDecimal("200"), true),
                    new AccountService.Account("A3", "Juan", new BigDecimal("300"), false));

            assertEquals(new BigDecimal("600"), service.totalBalance(accounts));
        }

        @Test
        @DisplayName("totalBalance con lista vacía retorna zero")
        void totalBalance_empty() {
            assertEquals(BigDecimal.ZERO, service.totalBalance(List.of()));
        }

        @Test
        @DisplayName("totalBalance con null retorna zero")
        void totalBalance_null() {
            assertEquals(BigDecimal.ZERO, service.totalBalance(null));
        }

        @Test
        @DisplayName("balanceByStatus agrupa correctamente")
        void balanceByStatus() {
            var accounts = List.of(
                    new AccountService.Account("A1", "Carlos", new BigDecimal("1000"), true),
                    new AccountService.Account("A2", "María", new BigDecimal("2000"), true),
                    new AccountService.Account("A3", "Juan", new BigDecimal("500"), false));

            Map<Boolean, BigDecimal> result = service.balanceByStatus(accounts);

            assertAll(
                    () -> assertEquals(new BigDecimal("3000"), result.get(true)),
                    () -> assertEquals(new BigDecimal("500"), result.get(false)));
        }
    }
}
