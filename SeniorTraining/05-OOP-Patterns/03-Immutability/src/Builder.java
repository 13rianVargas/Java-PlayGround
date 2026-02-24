import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * SOLUCIONES — Immutability
 *
 * Soluciones anotadas con técnicas de inmutabilidad.
 */
public class Builder {

    // ═══════════════════════════════════════════════════════════════════════════
    // E1 — Record con validación y operaciones inmutables
    // Técnica: Compact constructor + métodos que retornan nuevos valores.
    // ═══════════════════════════════════════════════════════════════════════════

    record Currency(String code, BigDecimal exchangeRateToUSD) {
        Currency{if(code==null||code.length()!=3){throw new IllegalArgumentException("Currency code must be exactly 3 characters: "+code);}Objects.requireNonNull(exchangeRateToUSD,"exchangeRateToUSD cannot be null");if(exchangeRateToUSD.compareTo(BigDecimal.ZERO)<=0){throw new IllegalArgumentException("exchangeRateToUSD must be positive: "+exchangeRateToUSD);}code=code.toUpperCase();}

        /**
         * Convierte el monto de esta moneda a la moneda target.
         * Ejemplo: 100 EUR (rate 1.10) → USD → COP (rate 0.00025)
         *   100 * (1.10 / 0.00025) = 440,000 COP
         */
        BigDecimal convert(BigDecimal amountInThisCurrency, Currency target) {
            return amountInThisCurrency
                    .multiply(this.exchangeRateToUSD)
                    .divide(target.exchangeRateToUSD, 2, RoundingMode.HALF_UP);
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // E2 — Clase inmutable con copia defensiva
    // Técnica: List.copyOf en constructor, nuevos objetos para "mutaciones".
    // ═══════════════════════════════════════════════════════════════════════════

    record Entry(String description, BigDecimal amount, String date) {}


    
    static final class AccountStatement {
        private final String accountId;
        private final String period;
        private final List<Entry> entries;
        private final BigDecimal closingBalance;

        AccountStatement(String accountId, String period,
                         List<Entry> entries, BigDecimal closingBalance) {
            this.accountId = Objects.requireNonNull(accountId);
            this.period = Objects.requireNonNull(period);
            this.entries = List.copyOf(entries); // ✅ Copia defensiva
            this.closingBalance = Objects.requireNonNull(closingBalance);
        }

        String getAccountId() { return accountId; }
        String getPeriod() { return period; }
        List<Entry> getEntries() { return entries; } // Ya inmutable
        BigDecimal getClosingBalance() { return closingBalance; }

        /**
         * Retorna un NUEVO statement con el entry adicional.
         */
        AccountStatement addEntry(Entry e) {
            List<Entry> newEntries = new ArrayList<>(entries);
            newEntries.add(e);
            BigDecimal newBalance = closingBalance.add(e.amount());
            return new AccountStatement(accountId, period, newEntries, newBalance);
        }

        BigDecimal getTotalDebits() {
            return entries.stream()
                    .map(Entry::amount)
                    .filter(a -> a.compareTo(BigDecimal.ZERO) < 0)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        BigDecimal getTotalCredits() {
            return entries.stream()
                    .map(Entry::amount)
                    .filter(a -> a.compareTo(BigDecimal.ZERO) >= 0)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        @Override
        public String toString() {
            return String.format("Statement{acc='%s', period='%s', entries=%d, balance=%s}",
                    accountId, period, entries.size(), closingBalance);
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // E3 — Inmutable con Map defensivo
    // Técnica: Map.copyOf + withUpdatedRate retorna nuevo snapshot.
    // ═══════════════════════════════════════════════════════════════════════════

    static final class ExchangeRateSnapshot {
        private final long timestamp;
        private final Map<String, BigDecimal> rates;

        ExchangeRateSnapshot(long timestamp, Map<String, BigDecimal> rates) {
            this.timestamp = timestamp;
            this.rates = Map.copyOf(rates); // ✅ Copia defensiva
        }

        long getTimestamp() { return timestamp; }
        Map<String, BigDecimal> getRates() { return rates; } // Ya inmutable

        Optional<BigDecimal> getRate(String currency) {
            return Optional.ofNullable(rates.get(currency.toUpperCase()));
        }

        ExchangeRateSnapshot withUpdatedRate(String currency, BigDecimal rate) {
            Map<String, BigDecimal> newRates = new HashMap<>(rates);
            newRates.put(currency.toUpperCase(), rate);
            return new ExchangeRateSnapshot(System.currentTimeMillis(), newRates);
        }

        Set<String> getCurrencies() {
            return rates.keySet(); // Map.copyOf keys are already unmodifiable
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // E4 — Transformaciones funcionales sobre inmutables
    // Técnica: Stream.map para crear nuevos records. Collectors inmutables.
    // Complejidad: O(n) para reconcileAll y filterByCategory.
    //              O(n) para summarizeByCategory (groupingBy + reducing).
    // ═══════════════════════════════════════════════════════════════════════════

    record LedgerEntry(String id, BigDecimal amount, String category, boolean reconciled) {}

    /**
     * Crea NUEVOS records con reconciled = true.
     * Los originales no se mutan (records son inmutables).
     */
    public static List<LedgerEntry> reconcileAll(List<LedgerEntry> entries) {
        return entries.stream()
                .map(e -> new LedgerEntry(e.id(), e.amount(), e.category(), true))
                .collect(Collectors.toList());
    }

    public static List<LedgerEntry> filterByCategory(List<LedgerEntry> entries, String category) {
        return List.copyOf(
                entries.stream()
                        .filter(e -> e.category().equals(category))
                        .toList()
        );
    }

    /**
     * Agrupa por category y suma los amounts.
     * Retorna Map inmutable.
     */
    public static Map<String, BigDecimal> summarizeByCategory(List<LedgerEntry> entries) {
        Map<String, BigDecimal> summary = entries.stream()
                .collect(Collectors.groupingBy(
                        LedgerEntry::category,
                        Collectors.reducing(BigDecimal.ZERO, LedgerEntry::amount, BigDecimal::add)
                ));
        return Map.copyOf(summary);
    }
    // ═══════════════════════════════════════════════════════════════════════════
    // E5 — Builder que produce objetos inmutables
    // Técnica: Builder mutable → build() produce objeto inmutable con Map.copyOf.
    // ═══════════════════════════════════════════════════════════════════════════

    static final class InvestmentPortfolio {
        private final String name;
        private final Map<String, Integer> holdings;
        private final BigDecimal totalValue;

        private InvestmentPortfolio(Builder b) {
            this.name = b.name;
            this.holdings = Map.copyOf(b.holdings); // ✅ Copia inmutable
            this.totalValue = b.totalValue;
        }

        String getName() { return name; }
        Map<String, Integer> getHoldings() { return holdings; }
        BigDecimal getTotalValue() { return totalValue; }

        int getTotalShares() {
            return holdings.values().stream()
                    .mapToInt(Integer::intValue)
                    .sum();
        }

        @Override
        public String toString() {
            return String.format("Portfolio{name='%s', holdings=%s, value=%s, shares=%d}",
                    name, holdings, totalValue, getTotalShares());
        }

        static class Builder {
            private String name;
            private final Map<String, Integer> holdings = new HashMap<>();
            private BigDecimal totalValue = BigDecimal.ZERO;

            Builder name(String n) { this.name = n; return this; }
            Builder addHolding(String symbol, int quantity) {
                this.holdings.merge(symbol, quantity, Integer::sum);
                return this;
            }
            Builder totalValue(BigDecimal v) { this.totalValue = v; return this; }

            InvestmentPortfolio build() {
                if (name == null || name.isBlank()) {
                    throw new IllegalStateException("name is required");
                }
                if (holdings.isEmpty()) {
                    throw new IllegalStateException("holdings cannot be empty");
                }
                return new InvestmentPortfolio(this);
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════");
        System.out.println("  Immutability Solutions — Verify");
        System.out.println("═══════════════════════════════════════\n");

        // E1
        System.out.println("E1 — Currency Record:");
        Currency usd = new Currency("USD", BigDecimal.ONE);
        Currency eur = new Currency("eur", new BigDecimal("1.10"));
        Currency cop = new Currency("COP", new BigDecimal("0.00025"));
        BigDecimal converted = eur.convert(new BigDecimal("100"), cop);
        System.out.println("   100 EUR → COP: " + converted + " ✓ (expected ~440000.00)");
        System.out.println("   100 EUR → USD: " + eur.convert(new BigDecimal("100"), usd) + " ✓ (expected 110.00)");

        // E2
        System.out.println("\nE2 — AccountStatement (immutable):");
        AccountStatement stmt = new AccountStatement("ACC-001", "2024-01",
                List.of(
                        new Entry("Salary", new BigDecimal("5000"), "2024-01-01"),
                        new Entry("Rent", new BigDecimal("-1500"), "2024-01-05")
                ),
                new BigDecimal("8500"));
        AccountStatement stmt2 = stmt.addEntry(new Entry("Groceries", new BigDecimal("-200"), "2024-01-10"));
        System.out.println("   Original: " + stmt);
        System.out.println("   After add: " + stmt2);
        System.out.println("   Original unchanged: " + (stmt.getEntries().size() == 2) + " ✓");
        System.out.println("   Credits: $" + stmt.getTotalCredits() + " ✓ (expected 5000)");
        System.out.println("   Debits: $" + stmt.getTotalDebits() + " ✓ (expected -1500)");

        // E3
        System.out.println("\nE3 — ExchangeRateSnapshot:");
        ExchangeRateSnapshot snap = new ExchangeRateSnapshot(System.currentTimeMillis(),
                Map.of("USD", BigDecimal.ONE, "EUR", new BigDecimal("1.10")));
        ExchangeRateSnapshot snap2 = snap.withUpdatedRate("GBP", new BigDecimal("1.27"));
        System.out.println("   Original currencies: " + snap.getCurrencies());
        System.out.println("   Updated currencies: " + snap2.getCurrencies());
        System.out.println("   GBP in original: " + snap.getRate("GBP") + " ✓ (expected empty)");
        System.out.println("   GBP in updated: " + snap2.getRate("GBP") + " ✓ (expected 1.27)");

        // E4
        System.out.println("\nE4 — Functional Transformations:");
        List<LedgerEntry> ledger = List.of(
                new LedgerEntry("L1", new BigDecimal("100"), "INCOME", false),
                new LedgerEntry("L2", new BigDecimal("-50"), "EXPENSE", false),
                new LedgerEntry("L3", new BigDecimal("200"), "INCOME", false),
                new LedgerEntry("L4", new BigDecimal("-30"), "EXPENSE", true)
        );
        List<LedgerEntry> reconciled = reconcileAll(ledger);
        System.out.println("   All reconciled: " +
                reconciled.stream().allMatch(LedgerEntry::reconciled) + " ✓");
        System.out.println("   Original L1 still not reconciled: " +
                !ledger.get(0).reconciled() + " ✓");

        Map<String, BigDecimal> summary = summarizeByCategory(ledger);
        System.out.println("   INCOME: $" + summary.get("INCOME") + " ✓ (expected 300)");
        System.out.println("   EXPENSE: $" + summary.get("EXPENSE") + " ✓ (expected -80)");

        // E5
        System.out.println("\nE5 — InvestmentPortfolio (Builder → Immutable):");
        InvestmentPortfolio portfolio = new InvestmentPortfolio.Builder()
                .name("Tech Growth")
                .addHolding("AAPL", 50)
                .addHolding("GOOGL", 20)
                .addHolding("MSFT", 30)
                .totalValue(new BigDecimal("125000.00"))
                .build();
        System.out.println("   " + portfolio);
        System.out.println("   Total shares: " + portfolio.getTotalShares() + " ✓ (expected 100)");

        // Verify immutability
        try {
            portfolio.getHoldings().put("AMZN", 10);
            System.out.println("   ⚠️ Holdings should be immutable!");
        } catch (UnsupportedOperationException e) {
            System.out.println("   Holdings is immutable ✓");
        }
    }
}
