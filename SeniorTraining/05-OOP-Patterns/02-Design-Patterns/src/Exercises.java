import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * EJERCICIOS — Design Patterns (Strategy, Builder, Factory)
 *
 * Aplica los patrones más evaluados en pruebas Senior del sector financiero.
 */
public class Exercises {

    // ═══════════════════════════════════════════════════════════════════════════
    // E1 — STRATEGY: Calculadora de comisiones
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Define la interfaz CommissionStrategy con:
     * - BigDecimal calculate(BigDecimal transactionAmount)
     *
     * Implementa 3 estrategias:
     *
     * a) FlatCommission: cobra una comisión fija (ej: $5.00) sin importar el monto.
     * b) PercentageCommission: cobra un porcentaje del monto (ej: 2% = 0.02).
     *    Resultado con scale 2, HALF_UP.
     * c) TieredCommission: cobra escalonado:
     *    - Primeros $1000: 1% (0.01)
     *    - De $1001 a $10000: 0.5% (0.005)
     *    - Más de $10000: 0.1% (0.001)
     *    Suma las comisiones de cada tramo. Scale 2, HALF_UP.
     *
     * Implementa calculateNetAmount que devuelve:
     * transactionAmount - comisión calculada con la strategy.
     */
    interface CommissionStrategy {
        BigDecimal calculate(BigDecimal transactionAmount);
    }

    // TODO: Implementa FlatCommission
    // TODO: Implementa PercentageCommission
    // TODO: Implementa TieredCommission

    public static BigDecimal calculateNetAmount(BigDecimal amount, CommissionStrategy strategy) {
        throw new UnsupportedOperationException("Implementa este método.");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // E2 — BUILDER: Constructor de transacciones
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Implementa un TransactionRecord con Builder pattern.
     *
     * Campos obligatorios: id (String), amount (BigDecimal), type (String)
     * Campos opcionales:
     * - description (default: "")
     * - currency (default: "USD")
     * - timestamp (default: System.currentTimeMillis())
     * - metadata (Map<String, String>, default: empty map)
     *
     * Validaciones en build():
     * - amount no puede ser null ni negativo → IllegalStateException
     * - type no puede ser null ni vacío → IllegalStateException
     *
     * El TransactionRecord debe ser inmutable (metadata debe copiar defensivamente).
     */
    static class TransactionRecord {
        // TODO: Implementa los campos y el constructor privado

        @Override
        public String toString() {
            return "TransactionRecord{implement me}";
        }

        static class Builder {
            // TODO: Implementa el Builder completo

            TransactionRecord build() {
                throw new UnsupportedOperationException("Implementa este método.");
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // E3 — FACTORY: Creador de productos financieros
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Define un sealed interface FinancialProduct con:
     * - String getName()
     * - BigDecimal getAnnualRate()
     *
     * Implementa 3 records:
     * - SavingsProduct(String name, BigDecimal annualRate)
     * - CDTProduct(String name, BigDecimal annualRate, int termMonths)
     * - CreditProduct(String name, BigDecimal annualRate, BigDecimal creditLimit)
     *
     * Implementa createProduct(String type, Map<String, String> params):
     * - "SAVINGS" → SavingsProduct con name y rate de params
     * - "CDT" → CDTProduct con name, rate y termMonths de params
     * - "CREDIT" → CreditProduct con name, rate y creditLimit de params
     * - Otro → IllegalArgumentException
     */

    // TODO: Implementa la jerarquía y el factory method

    // ═══════════════════════════════════════════════════════════════════════════
    // E4 — STRATEGY + BUILDER combinados
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Crea un InvestmentSimulator que combina Strategy + Builder.
     *
     * Builder fields:
     * - principal (BigDecimal, obligatorio)
     * - months (int, obligatorio, > 0)
     * - strategy (InterestStrategy, obligatorio)
     * - taxRate (BigDecimal, default 0.00 = sin impuesto)
     *
     * El método simulate() retorna un SimulationResult record con:
     * - grossReturn: el interés bruto (calculado por la strategy)
     * - tax: grossReturn * taxRate, scale 2 HALF_UP
     * - netReturn: grossReturn - tax
     * - finalBalance: principal + netReturn
     */
    interface InterestStrategy {
        BigDecimal calculate(BigDecimal principal, int months);
    }

    record SimulationResult(BigDecimal grossReturn, BigDecimal tax,
                            BigDecimal netReturn, BigDecimal finalBalance) {}

    static class InvestmentSimulator {
        // TODO: Implementa con Builder pattern + Strategy

        SimulationResult simulate() {
            throw new UnsupportedOperationException("Implementa este método.");
        }

        static class Builder {
            Builder principal(BigDecimal p) { throw new UnsupportedOperationException("Implementa este método."); }
            Builder months(int m) { throw new UnsupportedOperationException("Implementa este método."); }
            Builder strategy(InterestStrategy s) { throw new UnsupportedOperationException("Implementa este método."); }
            Builder taxRate(BigDecimal t) { throw new UnsupportedOperationException("Implementa este método."); }
            InvestmentSimulator build() { throw new UnsupportedOperationException("Implementa este método."); }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // E5 — FACTORY con registro dinámico
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Implementa un ValidatorRegistry que permite registrar y ejecutar
     * validadores de forma dinámica.
     *
     * - register(String name, Validator validator): registra un validador
     * - validate(String name, String value): ejecuta el validador registrado
     *   Retorna true si válido, false si inválido.
     *   Si el validador no existe → IllegalArgumentException
     *
     * Validator es una interfaz funcional: boolean isValid(String value)
     */
    @FunctionalInterface
    interface Validator {
        boolean isValid(String value);
    }

    static class ValidatorRegistry {
        // TODO: Implementa el registro dinámico de validadores

        public void register(String name, Validator validator) {
            throw new UnsupportedOperationException("Implementa este método.");
        }

        public boolean validate(String name, String value) {
            throw new UnsupportedOperationException("Implementa este método.");
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════");
        System.out.println("  Design Patterns Exercises — Self Test");
        System.out.println("═══════════════════════════════════════\n");

        // E1 Test (uncomment after implementing)
        // CommissionStrategy flat = new FlatCommission(new BigDecimal("5.00"));
        // System.out.println("E1 — Flat net: $" + calculateNetAmount(new BigDecimal("100"), flat) +
        //         " (expected 95.00)");
        // CommissionStrategy pct = new PercentageCommission(new BigDecimal("0.02"));
        // System.out.println("E1 — Pct net: $" + calculateNetAmount(new BigDecimal("1000"), pct) +
        //         " (expected 980.00)");

        // E5 Test
        System.out.println("E5 — Validator Registry:");
        ValidatorRegistry registry = new ValidatorRegistry();
        // registry.register("email", v -> v.contains("@") && v.contains("."));
        // registry.register("positive", v -> new BigDecimal(v).compareTo(BigDecimal.ZERO) > 0);
        // System.out.println("   email 'test@bank.com': " + registry.validate("email", "test@bank.com") + " (expected true)");
        // System.out.println("   positive '100': " + registry.validate("positive", "100") + " (expected true)");
        // System.out.println("   positive '-5': " + registry.validate("positive", "-5") + " (expected false)");
    }
}
