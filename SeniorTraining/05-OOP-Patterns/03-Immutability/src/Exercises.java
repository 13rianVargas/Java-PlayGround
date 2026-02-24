import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * EJERCICIOS — Immutability
 *
 * Diseña objetos inmutables con copias defensivas y operaciones funcionales.
 */
public class Exercises {

    // ═══════════════════════════════════════════════════════════════════════════
    // E1 — Record con validación y operaciones inmutables
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Implementa un record Currency con:
     * - code (String, exactamente 3 caracteres, uppercase)
     * - exchangeRateToUSD (BigDecimal, > 0)
     *
     * Validación en compact constructor:
     * - code null o != 3 chars → IllegalArgumentException
     * - exchangeRateToUSD null o <= 0 → IllegalArgumentException
     * - code se convierte a uppercase automáticamente
     *
     * Método convert(BigDecimal amountInThisCurrency, Currency target):
     * Convierte el monto de esta moneda a la moneda target.
     * Fórmula: amount * (thisRate / targetRate), scale 2 HALF_UP.
     * Retorna un nuevo BigDecimal (inmutable).
     */

    // TODO: Implementa el record Currency

    // ═══════════════════════════════════════════════════════════════════════════
    // E2 — Clase inmutable con copia defensiva
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Implementa AccountStatement como una clase inmutable con:
     * - accountId (String)
     * - period (String, ej: "2024-01")
     * - entries (List<Entry>)
     * - closingBalance (BigDecimal)
     *
     * Entry es un record: (String description, BigDecimal amount, String date)
     *
     * Requisitos:
     * - Constructor hace copia defensiva de la lista de entries
     * - getEntries() retorna una lista inmutable
     * - addEntry(Entry e) retorna un NUEVO AccountStatement con el entry adicional
     *   y el closingBalance actualizado (closingBalance + entry.amount)
     * - getTotalDebits(): suma de entries con amount < 0
     * - getTotalCredits(): suma de entries con amount >= 0
     */
    record Entry(String description, BigDecimal amount, String date) {}

    // TODO: Implementa AccountStatement como clase inmutable

    // ═══════════════════════════════════════════════════════════════════════════
    // E3 — Inmutable con Map defensivo
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Implementa ExchangeRateSnapshot como clase inmutable.
     *
     * Campos:
     * - timestamp (long)
     * - rates (Map<String, BigDecimal>): código de moneda → tasa vs USD
     *
     * Requisitos:
     * - Constructor hace copia defensiva del Map
     * - getRates() retorna un Map inmutable
     * - getRate(String currency) retorna Optional<BigDecimal>
     * - withUpdatedRate(String currency, BigDecimal rate) retorna un NUEVO snapshot
     *   con la tasa actualizada y el timestamp actual
     * - getCurrencies() retorna un Set<String> inmutable con los códigos de moneda
     */

    // TODO: Implementa ExchangeRateSnapshot

    // ═══════════════════════════════════════════════════════════════════════════
    // E4 — Transformaciones funcionales sobre inmutables
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Dado un record Ledger que contiene transacciones inmutables:
     */
    record LedgerEntry(String id, BigDecimal amount, String category, boolean reconciled) {}

    /**
     * Implementa:
     *
     * a) reconcileAll: recibe List<LedgerEntry> y retorna una NUEVA lista
     *    con todos los entries marcados como reconciled = true.
     *    (No muta nada — crea nuevos records)
     *
     * b) filterByCategory: recibe List<LedgerEntry> y String category,
     *    retorna una nueva lista inmutable (List.copyOf) con solo los entries
     *    de esa categoría.
     *
     * c) summarizeByCategory: recibe List<LedgerEntry>, retorna
     *    Map<String, BigDecimal> inmutable con la suma de amounts por categoría.
     */
    public static List<LedgerEntry> reconcileAll(List<LedgerEntry> entries) {
        throw new UnsupportedOperationException("Implementa este método.");
    }

    public static List<LedgerEntry> filterByCategory(List<LedgerEntry> entries, String category) {
        throw new UnsupportedOperationException("Implementa este método.");
    }

    public static Map<String, BigDecimal> summarizeByCategory(List<LedgerEntry> entries) {
        throw new UnsupportedOperationException("Implementa este método.");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // E5 — Builder que produce objetos inmutables
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Implementa un InvestmentPortfolio inmutable con Builder.
     *
     * Campos:
     * - name (String, obligatorio)
     * - holdings (Map<String, Integer>: símbolo → cantidad, inmutable)
     * - totalValue (BigDecimal, calculado)
     *
     * Builder methods:
     * - name(String n)
     * - addHolding(String symbol, int quantity): agrega al map interno
     * - totalValue(BigDecimal v)
     * - build(): retorna InvestmentPortfolio inmutable
     *
     * Validaciones en build():
     * - name es obligatorio → IllegalStateException si null/blank
     * - holdings no puede estar vacío → IllegalStateException
     *
     * Métodos de InvestmentPortfolio:
     * - getHoldings() retorna Map inmutable
     * - getTotalShares() retorna la suma de todas las cantidades
     */

    // TODO: Implementa InvestmentPortfolio con Builder

    // ═══════════════════════════════════════════════════════════════════════════

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════");
        System.out.println("  Immutability Exercises — Self Test");
        System.out.println("═══════════════════════════════════════\n");

        // E4 Test
        List<LedgerEntry> ledger = List.of(
                new LedgerEntry("L1", new BigDecimal("100"), "INCOME", false),
                new LedgerEntry("L2", new BigDecimal("-50"), "EXPENSE", false),
                new LedgerEntry("L3", new BigDecimal("200"), "INCOME", false),
                new LedgerEntry("L4", new BigDecimal("-30"), "EXPENSE", true)
        );

        List<LedgerEntry> reconciled = reconcileAll(ledger);
        System.out.println("E4a — All reconciled: " +
                reconciled.stream().allMatch(LedgerEntry::reconciled) + " (expected true)");
        System.out.println("      Original unchanged: " +
                ledger.stream().noneMatch(e -> e.id().equals("L1") && e.reconciled()) + " (expected true)");

        List<LedgerEntry> income = filterByCategory(ledger, "INCOME");
        System.out.println("E4b — Income entries: " + income.size() + " (expected 2)");

        Map<String, BigDecimal> summary = summarizeByCategory(ledger);
        System.out.println("E4c — Summary: " + summary);
        System.out.println("      INCOME total: " + summary.get("INCOME") + " (expected 300)");
        System.out.println("      EXPENSE total: " + summary.get("EXPENSE") + " (expected -80)");
    }
}
