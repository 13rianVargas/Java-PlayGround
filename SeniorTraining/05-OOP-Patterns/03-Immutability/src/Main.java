import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================
 * MÓDULO 05.03 — Immutability: Guía completa
 * ============================================================
 *
 * En el sector financiero, la inmutabilidad NO es opcional.
 * - Los datos de transacciones NO deben cambiar después de crearse (auditoría).
 * - Los objetos compartidos entre hilos deben ser thread-safe.
 * - Los DTOs deben ser predecibles y libres de side effects.
 */
public class Main {

    // ══════════════════════════════════════════════════════════════════════════
    // 1. RECORDS — Inmutabilidad automática (Java 16+)
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Un record es inmutable por diseño:
     * - Campos son final
     * - No tiene setters
     * - equals/hashCode basados en valores
     */
    record Money(BigDecimal amount, String currency) {
        // Compact constructor para validación
        Money {
            Objects.requireNonNull(amount, "amount cannot be null");
            Objects.requireNonNull(currency, "currency cannot be null");
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("amount cannot be negative: " + amount);
            }
            currency = currency.toUpperCase();
        }

        // Métodos que retornan NUEVAS instancias (no mutan)
        Money add(Money other) {
            if (!this.currency.equals(other.currency)) {
                throw new IllegalArgumentException("Cannot add different currencies");
            }
            return new Money(this.amount.add(other.amount), this.currency);
        }

        Money multiply(int factor) {
            return new Money(this.amount.multiply(BigDecimal.valueOf(factor)), this.currency);
        }
    }

    record Transaction(String id, Money amount, String type, long timestamp) {
        Transaction {
            Objects.requireNonNull(id);
            Objects.requireNonNull(amount);
            Objects.requireNonNull(type);
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // 2. CLASES INMUTABLES MANUALES — Cuando necesitas más control
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Reglas para una clase inmutable:
     * 1. Declarar la clase como final (no se puede extender)
     * 2. Todos los campos private y final
     * 3. No proveer setters
     * 4. Copias defensivas de colecciones mutables
     * 5. Copias defensivas al retornar colecciones
     */
    static final class Portfolio {
        private final String id;
        private final String owner;
        private final List<Transaction> transactions; // ← Colección mutable
        private final Map<String, BigDecimal> balances;

        Portfolio(String id, String owner, List<Transaction> transactions,
                Map<String, BigDecimal> balances) {
            this.id = Objects.requireNonNull(id);
            this.owner = Objects.requireNonNull(owner);
            // ✅ Copia defensiva en el constructor
            this.transactions = List.copyOf(transactions);
            this.balances = Map.copyOf(balances);
        }

        String getId() {
            return id;
        }

        String getOwner() {
            return owner;
        }

        // ✅ Retornar vista inmutable (List.copyOf ya lo es, pero como ejemplo)
        List<Transaction> getTransactions() {
            return transactions;
        }

        Map<String, BigDecimal> getBalances() {
            return balances;
        }

        // Para "modificar", se retorna un NUEVO Portfolio
        Portfolio addTransaction(Transaction txn) {
            List<Transaction> newTxns = new ArrayList<>(transactions);
            newTxns.add(txn);
            return new Portfolio(id, owner, newTxns, balances);
        }

        Portfolio updateBalance(String currency, BigDecimal newBalance) {
            Map<String, BigDecimal> newBalances = new HashMap<>(balances);
            newBalances.put(currency, newBalance);
            return new Portfolio(id, owner, transactions, newBalances);
        }

        @Override
        public String toString() {
            return String.format("Portfolio{id='%s', owner='%s', txns=%d, balances=%s}",
                    id, owner, transactions.size(), balances);
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // 3. ERRORES COMUNES CON MUTABILIDAD
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * ❌ PROBLEMA: Retornar la referencia interna de una lista mutable.
     */
    static class AccountBAD {
        private final List<String> tags;

        AccountBAD(List<String> tags) {
            this.tags = tags; // ⚠️ NO copia defensiva → el caller puede mutar
        }

        List<String> getTags() {
            return tags; // ⚠️ El caller puede hacer getTags().add("hacked")
        }
    }

    /**
     * ✅ CORRECTO: Copias defensivas entrada y salida.
     */
    static class AccountGOOD {
        private final List<String> tags;

        AccountGOOD(List<String> tags) {
            this.tags = List.copyOf(tags); // ✅ Copia defensiva
        }

        List<String> getTags() {
            return tags; // ✅ List.copyOf retorna una lista inmutable
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // 4. INMUTABILIDAD CON COLECCIONES — Factories de Java 9+
    // ══════════════════════════════════════════════════════════════════════════

    static void collectionFactories() {
        // Inmutables desde la creación:
        List<String> currencies = List.of("USD", "EUR", "COP");
        Set<String> types = Set.of("SAVINGS", "CHECKING", "CREDIT");
        Map<String, BigDecimal> rates = Map.of(
                "SAVINGS", new BigDecimal("0.04"),
                "CHECKING", new BigDecimal("0.01"));

        // Intentar modificar lanza UnsupportedOperationException:
        try {
            currencies.add("GBP"); // ← UnsupportedOperationException
        } catch (UnsupportedOperationException e) {
            System.out.println("   List.of() es inmutable: " + e.getClass().getSimpleName());
        }

        // Para más de 10 entradas en Map:
        Map<String, BigDecimal> moreRates = Map.ofEntries(
                Map.entry("SAVINGS", new BigDecimal("0.04")),
                Map.entry("CHECKING", new BigDecimal("0.01")),
                Map.entry("PREMIUM", new BigDecimal("0.06")));

        // Copiar una colección existente como inmutable:
        List<String> mutable = new ArrayList<>(List.of("A", "B", "C"));
        List<String> immutableCopy = Collections.unmodifiableList(mutable);
        // CUIDADO: unmodifiableList es una VISTA. Si mutable cambia, immutableCopy
        // también.
        // Mejor: List.copyOf(mutable) — crea una copia real inmutable.
        List<String> safeCopy = List.copyOf(mutable);
    }

    // ══════════════════════════════════════════════════════════════════════════
    // 5. CUÁNDO USAR MUTABILIDAD VS INMUTABILIDAD
    // ══════════════════════════════════════════════════════════════════════════

    /*
     * ┌──────────────────────────────┬────────────────────────────────────────┐
     * │ INMUTABLE (preferir) │ MUTABLE (cuando necesario) │
     * ├──────────────────────────────┼────────────────────────────────────────┤
     * │ DTOs / Value Objects │ Builders (antes de build()) │
     * │ Records de transacciones │ StringBuilder en loops │
     * │ Datos entre hilos │ Acumuladores locales en algoritmos │
     * │ Claves de Map / Set │ Colecciones temporales en streams │
     * │ Mensajes de eventos │ Objetos de sesión/estado │
     * └──────────────────────────────┴────────────────────────────────────────┘
     *
     * REGLA DE ORO: Si el objeto sale de tu método → hazlo inmutable.
     * Si es interno y temporal → puede ser mutable.
     */

    // ══════════════════════════════════════════════════════════════════════════
    // RESUMEN Y DEMO
    // ══════════════════════════════════════════════════════════════════════════

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("  MÓDULO 05.03 — Immutability");
        System.out.println("═══════════════════════════════════════════════\n");

        // --- Records Demo ---
        System.out.println("1. RECORDS — Inmutabilidad automática");
        Money m1 = new Money(new BigDecimal("100.00"), "usd");
        Money m2 = new Money(new BigDecimal("50.00"), "USD");
        Money sum = m1.add(m2);
        System.out.println("   $" + m1.amount() + " + $" + m2.amount() + " = $" + sum.amount() + " " + sum.currency());
        System.out.println("   m1 sigue siendo $" + m1.amount() + " (inmutable)");

        // --- Portfolio Demo ---
        System.out.println("\n2. CLASE INMUTABLE — Portfolio");
        Transaction t1 = new Transaction("T1",
                new Money(new BigDecimal("500.00"), "USD"), "WIRE", System.currentTimeMillis());
        Transaction t2 = new Transaction("T2",
                new Money(new BigDecimal("200.00"), "USD"), "ACH", System.currentTimeMillis());

        Portfolio p1 = new Portfolio("PF-001", "Carlos",
                List.of(t1), Map.of("USD", new BigDecimal("10000")));
        System.out.println("   Original: " + p1);

        Portfolio p2 = p1.addTransaction(t2);
        System.out.println("   Con nueva txn: " + p2);
        System.out.println("   Original sin cambios: " + p1);
        System.out.println("   ¿Son el mismo objeto? " + (p1 == p2)); // false

        // --- Error de mutabilidad ---
        System.out.println("\n3. ERRORES DE MUTABILIDAD");
        List<String> tags = new ArrayList<>(List.of("premium", "digital"));
        AccountBAD bad = new AccountBAD(tags);
        tags.add("HACKED"); // Muta la lista interna
        System.out.println("   AccountBAD tags (mutada externamente): " + bad.getTags());

        List<String> tags2 = new ArrayList<>(List.of("premium", "digital"));
        AccountGOOD good = new AccountGOOD(tags2);
        tags2.add("HACKED"); // No afecta la copia interna
        System.out.println("   AccountGOOD tags (protegida): " + good.getTags());

        // --- Collection Factories ---
        System.out.println("\n4. COLECCIONES INMUTABLES");
        collectionFactories();

        System.out.println("\n═══════════════════════════════════════════════");
        System.out.println("  RESUMEN Immutability");
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("  • Records = inmutables por diseño (preferir para DTOs)");
        System.out.println("  • Copia defensiva: List.copyOf(), Map.copyOf()");
        System.out.println("  • Nunca retornar referencias internas mutables");
        System.out.println("  • Factories inmutables: List.of(), Set.of(), Map.of()");
        System.out.println("  • Para 'modificar' → crear nuevo objeto");
    }
}
