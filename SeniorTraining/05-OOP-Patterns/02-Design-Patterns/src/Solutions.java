import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * SOLUCIONES — Design Patterns (Strategy, Builder, Factory)
 *
 * Soluciones anotadas con explicación del patrón aplicado.
 */
public class Solutions {

    // ═══════════════════════════════════════════════════════════════════════════
    // E1 — STRATEGY: Calculadora de comisiones
    // Patrón: Encapsula cada algoritmo de comisión en su propia clase.
    // ═══════════════════════════════════════════════════════════════════════════

    interface CommissionStrategy {
        BigDecimal calculate(BigDecimal transactionAmount);
    }

    static class FlatCommission implements CommissionStrategy {
        private final BigDecimal flatFee;
        FlatCommission(BigDecimal flatFee) { this.flatFee = flatFee; }

        public BigDecimal calculate(BigDecimal transactionAmount) {
            return flatFee;
        }
    }

    static class PercentageCommission implements CommissionStrategy {
        private final BigDecimal rate;
        PercentageCommission(BigDecimal rate) { this.rate = rate; }

        public BigDecimal calculate(BigDecimal transactionAmount) {
            return transactionAmount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
        }
    }

    /**
     * TieredCommission — escalonado por tramos:
     * - Hasta $1,000: 1%
     * - $1,001 – $10,000: 0.5%
     * - Más de $10,000: 0.1%
     *
     * Para $15,000:
     *   Tramo 1: 1000 × 0.01 = 10.00
     *   Tramo 2: 9000 × 0.005 = 45.00
     *   Tramo 3: 5000 × 0.001 = 5.00
     *   Total: $60.00
     */
    static class TieredCommission implements CommissionStrategy {
        public BigDecimal calculate(BigDecimal amount) {
            BigDecimal commission = BigDecimal.ZERO;
            BigDecimal remaining = amount;

            // Tramo 1: hasta 1000 al 1%
            BigDecimal tier1Limit = new BigDecimal("1000");
            if (remaining.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal tier1Amount = remaining.min(tier1Limit);
                commission = commission.add(tier1Amount.multiply(new BigDecimal("0.01")));
                remaining = remaining.subtract(tier1Amount);
            }

            // Tramo 2: de 1001 a 10000 al 0.5%
            BigDecimal tier2Limit = new BigDecimal("9000");
            if (remaining.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal tier2Amount = remaining.min(tier2Limit);
                commission = commission.add(tier2Amount.multiply(new BigDecimal("0.005")));
                remaining = remaining.subtract(tier2Amount);
            }

            // Tramo 3: más de 10000 al 0.1%
            if (remaining.compareTo(BigDecimal.ZERO) > 0) {
                commission = commission.add(remaining.multiply(new BigDecimal("0.001")));
            }

            return commission.setScale(2, RoundingMode.HALF_UP);
        }
    }

    public static BigDecimal calculateNetAmount(BigDecimal amount, CommissionStrategy strategy) {
        BigDecimal commission = strategy.calculate(amount);
        return amount.subtract(commission).setScale(2, RoundingMode.HALF_UP);
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // E2 — BUILDER: Constructor de transacciones
    // Patrón: Fluent API para objetos con muchos campos opcionales.
    // ═══════════════════════════════════════════════════════════════════════════

    static class TransactionRecord {
        private final String id;
        private final BigDecimal amount;
        private final String type;
        private final String description;
        private final String currency;
        private final long timestamp;
        private final Map<String, String> metadata;

        private TransactionRecord(Builder b) {
            this.id = b.id;
            this.amount = b.amount;
            this.type = b.type;
            this.description = b.description;
            this.currency = b.currency;
            this.timestamp = b.timestamp;
            this.metadata = Map.copyOf(b.metadata); // Copia defensiva
        }

        // Getters
        String getId() { return id; }
        BigDecimal getAmount() { return amount; }
        String getType() { return type; }
        String getDescription() { return description; }
        String getCurrency() { return currency; }
        long getTimestamp() { return timestamp; }
        Map<String, String> getMetadata() { return metadata; }

        @Override
        public String toString() {
            return String.format("Txn{id='%s', amount=%s, type='%s', desc='%s', " +
                    "currency='%s', meta=%s}", id, amount, type, description, currency, metadata);
        }

        static class Builder {
            private String id;
            private BigDecimal amount;
            private String type;
            private String description = "";
            private String currency = "USD";
            private long timestamp = System.currentTimeMillis();
            private Map<String, String> metadata = new HashMap<>();

            Builder(String id) { this.id = Objects.requireNonNull(id); }

            Builder amount(BigDecimal a) { this.amount = a; return this; }
            Builder type(String t) { this.type = t; return this; }
            Builder description(String d) { this.description = d; return this; }
            Builder currency(String c) { this.currency = c; return this; }
            Builder timestamp(long ts) { this.timestamp = ts; return this; }
            Builder addMetadata(String key, String value) { this.metadata.put(key, value); return this; }

            TransactionRecord build() {
                if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
                    throw new IllegalStateException("Amount must be non-null and non-negative");
                }
                if (type == null || type.isBlank()) {
                    throw new IllegalStateException("Type must not be null or empty");
                }
                return new TransactionRecord(this);
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // E3 — FACTORY: Creador de productos financieros
    // Patrón: Sealed interface + Factory method para creación controlada.
    // ═══════════════════════════════════════════════════════════════════════════

    sealed interface FinancialProduct permits SavingsProduct, CDTProduct, CreditProduct {
        String getName();
        BigDecimal getAnnualRate();
    }

    record SavingsProduct(String name, BigDecimal annualRate) implements FinancialProduct {
        public String getName() { return name; }
        public BigDecimal getAnnualRate() { return annualRate; }
    }

    record CDTProduct(String name, BigDecimal annualRate, int termMonths) implements FinancialProduct {
        public String getName() { return name; }        public BigDecimal getAnnualRate() { return annualRate; }
    }

    record CreditProduct(String name, BigDecimal annualRate, BigDecimal creditLimit) implements FinancialProduct {
        public String getName() { return name; }
        public BigDecimal getAnnualRate() { return annualRate; }
    }

    public static FinancialProduct createProduct(String type, Map<String, String> params) {
        return switch (type.toUpperCase()) {
            case "SAVINGS" -> new SavingsProduct(
                    params.get("name"),
                    new BigDecimal(params.get("rate"))
            );
            case "CDT" -> new CDTProduct(
                    params.get("name"),
                    new BigDecimal(params.get("rate")),
                    Integer.parseInt(params.get("termMonths"))
            );
            case "CREDIT" -> new CreditProduct(
                    params.get("name"),
                    new BigDecimal(params.get("rate")),
                    new BigDecimal(params.get("creditLimit"))
            );
            default -> throw new IllegalArgumentException("Unknown product type: " + type);
        };
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // E4 — STRATEGY + BUILDER combinados
    // Patrón: Builder para configurar la simulación, Strategy para el cálculo.
    // ═══════════════════════════════════════════════════════════════════════════

    interface InterestStrategy {
        BigDecimal calculate(BigDecimal principal, int months);
    }

    record SimulationResult(BigDecimal grossReturn, BigDecimal tax,
                            BigDecimal netReturn, BigDecimal finalBalance) {}

    static class InvestmentSimulator {
        private final BigDecimal principal;
        private final int months;
        private final InterestStrategy strategy;
        private final BigDecimal taxRate;

        private InvestmentSimulator(Builder b) {
            this.principal = b.principal;
            this.months = b.months;
            this.strategy = b.strategy;
            this.taxRate = b.taxRate;
        }

        SimulationResult simulate() {
            BigDecimal gross = strategy.calculate(principal, months);
            BigDecimal tax = gross.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
            BigDecimal net = gross.subtract(tax).setScale(2, RoundingMode.HALF_UP);
            BigDecimal finalBal = principal.add(net);
            return new SimulationResult(gross, tax, net, finalBal);
        }

        static class Builder {
            private BigDecimal principal;
            private int months;
            private InterestStrategy strategy;
            private BigDecimal taxRate = BigDecimal.ZERO;

            Builder principal(BigDecimal p) { this.principal = p; return this; }
            Builder months(int m) { this.months = m; return this; }
            Builder strategy(InterestStrategy s) { this.strategy = s; return this; }
            Builder taxRate(BigDecimal t) { this.taxRate = t; return this; }

            InvestmentSimulator build() {
                Objects.requireNonNull(principal, "principal is required");
                Objects.requireNonNull(strategy, "strategy is required");
                if (months <= 0) throw new IllegalStateException("months must be > 0");
                return new InvestmentSimulator(this);
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // E5 — FACTORY con registro dinámico
    // Patrón: Registry + Functional Interface para validadores dinámicos.
    // ═══════════════════════════════════════════════════════════════════════════

    @FunctionalInterface
    interface Validator {
        boolean isValid(String value);
    }

    static class ValidatorRegistry {
        private final Map<String, Validator> validators = new HashMap<>();

        public void register(String name, Validator validator) {
            validators.put(name, validator);
        }

        public boolean validate(String name, String value) {
            Validator validator = validators.get(name);
            if (validator == null) {
                throw new IllegalArgumentException("Unknown validator: " + name);
            }
            return validator.isValid(value);
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════");
        System.out.println("  Design Patterns Solutions — Verify");
        System.out.println("═══════════════════════════════════════\n");
        // E1
        System.out.println("E1 — Commission Strategies:");
        CommissionStrategy flat = new FlatCommission(new BigDecimal("5.00"));
        CommissionStrategy pct = new PercentageCommission(new BigDecimal("0.02"));
        CommissionStrategy tiered = new TieredCommission();
        System.out.println("   Flat $100: net $" + calculateNetAmount(new BigDecimal("100"), flat) + " ✓ (expected 95.00)");
        System.out.println("   Pct $1000: net $" + calculateNetAmount(new BigDecimal("1000"), pct) + " ✓ (expected 980.00)");
        System.out.println("   Tiered $15000: net $" + calculateNetAmount(new BigDecimal("15000"), tiered) + " ✓ (expected 14940.00)");

        // E2
        System.out.println("\nE2 — Transaction Builder:");
        TransactionRecord txn = new TransactionRecord.Builder("TXN-001")
                .amount(new BigDecimal("5000.00"))
                .type("WIRE")
                .description("International transfer")
                .currency("EUR")
                .addMetadata("beneficiary", "Acme Corp")
                .addMetadata("swift", "DEUTDEFF")
                .build();
        System.out.println("   " + txn);

        // E3
        System.out.println("\nE3 — Financial Product Factory:");
        FinancialProduct savings = createProduct("SAVINGS", Map.of("name", "Ahorro Premium", "rate", "0.06"));
        FinancialProduct cdt = createProduct("CDT", Map.of("name", "CDT 90D", "rate", "0.08", "termMonths", "3"));
        FinancialProduct credit = createProduct("CREDIT", Map.of("name", "Visa Gold", "rate", "0.24", "creditLimit", "50000"));
        System.out.println("   " + savings);
        System.out.println("   " + cdt);
        System.out.println("   " + credit);

        // E4
        System.out.println("\nE4 — Investment Simulator (Strategy + Builder):");
        InterestStrategy simple = (p, m) -> p.multiply(new BigDecimal("0.08"))
                .multiply(BigDecimal.valueOf(m).divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP))
                .setScale(2, RoundingMode.HALF_UP);

        SimulationResult result = new InvestmentSimulator.Builder()
                .principal(new BigDecimal("100000"))
                .months(12)
                .strategy(simple)
                .taxRate(new BigDecimal("0.15")) // 15% impuesto
                .build()
                .simulate();
        System.out.println("   Gross: $" + result.grossReturn());
        System.out.println("   Tax (15%): $" + result.tax());
        System.out.println("   Net: $" + result.netReturn());
        System.out.println("   Final balance: $" + result.finalBalance());

        // E5
        System.out.println("\nE5 — Validator Registry:");
        ValidatorRegistry registry = new ValidatorRegistry();
        registry.register("email", v -> v.contains("@") && v.contains("."));
        registry.register("positive", v -> {
            try { return new BigDecimal(v).compareTo(BigDecimal.ZERO) > 0; }
            catch (NumberFormatException e) { return false; }
        });
        registry.register("accountId", v -> v.matches("ACC-\\d{3,}"));

        System.out.println("   email 'user@bank.com': " + registry.validate("email", "user@bank.com") + " ✓ (expected true)");
        System.out.println("   positive '100': " + registry.validate("positive", "100") + " ✓ (expected true)");
        System.out.println("   positive '-5': " + registry.validate("positive", "-5") + " ✓ (expected false)");
        System.out.println("   accountId 'ACC-001': " + registry.validate("accountId", "ACC-001") + " ✓ (expected true)");
        System.out.println("   accountId 'BAD': " + registry.validate("accountId", "BAD") + " ✓ (expected false)");
    }
}
