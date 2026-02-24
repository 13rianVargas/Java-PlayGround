import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Supplier;

/**
 * ============================================================
 * MÓDULO 05.02 — Design Patterns: Guía completa
 * ============================================================
 *
 * Los 3 patrones más evaluados en pruebas técnicas Senior para el
 * sector financiero: Strategy, Builder y Factory.
 */
public class Main {

    // ══════════════════════════════════════════════════════════════════════════
    // 1. STRATEGY PATTERN
    //    "Define una familia de algoritmos, encapsula cada uno y hazlos
    //     intercambiables." — GoF
    // ══════════════════════════════════════════════════════════════════════════════

    /**
     * Caso: Diferentes estrategias de cálculo de interés.
     * Sin Strategy → if/else largo. Con Strategy → interfaz + implementaciones.
     */
    interface InterestStrategy {
        BigDecimal calculate(BigDecimal principal, int months);
        String getName();
    }

    static class SimpleInterest implements InterestStrategy {
        private final BigDecimal annualRate;

        SimpleInterest(BigDecimal annualRate) { this.annualRate = annualRate; }

        public BigDecimal calculate(BigDecimal principal, int months) {
            BigDecimal years = BigDecimal.valueOf(months).divide(
                    BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
            return principal.multiply(annualRate).multiply(years)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        public String getName() { return "Simple Interest (" + annualRate + ")"; }
    }
    // Interés compuesto: P × (1 + r/n)^(nt) - P
    static class CompoundInterest implements InterestStrategy {
        private final BigDecimal annualRate;
        private final int compoundsPerYear;

        CompoundInterest(BigDecimal annualRate, int compoundsPerYear) {
            this.annualRate = annualRate;
            this.compoundsPerYear = compoundsPerYear;
        }

        public BigDecimal calculate(BigDecimal principal, int months) {
            double P = principal.doubleValue();
            double r = annualRate.doubleValue();
            double n = compoundsPerYear;
            double t = months / 12.0;
            double amount = P * Math.pow(1 + r / n, n * t);
            return BigDecimal.valueOf(amount - P).setScale(2, RoundingMode.HALF_UP);
        }

        public String getName() { return "Compound Interest (" + annualRate + ", " + compoundsPerYear + "x/yr)"; }
    }

    // Interés fijo (para CDTs, bonos)
    static class FixedInterest implements InterestStrategy {
        private final BigDecimal fixedAmount;

        FixedInterest(BigDecimal fixedAmount) { this.fixedAmount = fixedAmount; }

        public BigDecimal calculate(BigDecimal principal, int months) {
            return fixedAmount.multiply(BigDecimal.valueOf(months))
                    .setScale(2, RoundingMode.HALF_UP);
        }

        public String getName() { return "Fixed Interest ($" + fixedAmount + "/month)"; }
    }

    // El CLIENTE usa la Strategy sin saber cuál es:
    static BigDecimal calculateReturn(BigDecimal principal, int months, InterestStrategy strategy) {
        return strategy.calculate(principal, months);
    }

    // ══════════════════════════════════════════════════════════════════
    // 2. BUILDER PATTERN
    //    "Separa la construcción de un objeto complejo de su representación."
    //    Ideal cuando un constructor tiene 4+ parámetros.
    // ══════════════════════════════════════════════════════════════════
    /**
     * Caso: Crear una Account tiene muchos campos opcionales.
     * Sin Builder → constructor con 8 params. Con Builder → fluent API.
     */
    static class Account {
        private final String id;
        private final String owner;
        private final String type;
        private final BigDecimal balance;
        private final BigDecimal creditLimit;
        private final String currency;
        private final boolean active;
        private final List<String> tags;

        private Account(Builder builder) {
            this.id = builder.id;
            this.owner = builder.owner;
            this.type = builder.type;
            this.balance = builder.balance;
            this.creditLimit = builder.creditLimit;
            this.currency = builder.currency;
            this.active = builder.active;
            this.tags = List.copyOf(builder.tags); // Copia defensiva → inmutable
        }

        // Getters
        String getId() { return id; }
        String getOwner() { return owner; }
        String getType() { return type; }
        BigDecimal getBalance() { return balance; }
        BigDecimal getCreditLimit() { return creditLimit; }
        String getCurrency() { return currency; }
        boolean isActive() { return active; }
        List<String> getTags() { return tags; }

        @Override
        public String toString() {
            return String.format("Account{id='%s', owner='%s', type='%s', balance=%s, " +
                    "currency='%s', active=%s, tags=%s}", id, owner, type, balance, currency, active, tags);
        }

        // ── BUILDER ──────────────────────────────────────────────────────────
        static class Builder {
            // Campos obligatorios
            private final String id;
            private final String owner;

            // Campos opcionales con defaults
            private String type = "SAVINGS";
            private BigDecimal balance = BigDecimal.ZERO;
            private BigDecimal creditLimit = BigDecimal.ZERO;
            private String currency = "USD";
            private boolean active = true;
            private List<String> tags = new ArrayList<>();

            Builder(String id, String owner) {
                this.id = Objects.requireNonNull(id, "id is required");
                this.owner = Objects.requireNonNull(owner, "owner is required");
            }

            Builder type(String type) { this.type = type; return this; }
            Builder balance(BigDecimal balance) { this.balance = balance; return this; }
            Builder creditLimit(BigDecimal limit) { this.creditLimit = limit; return this; }
            Builder currency(String currency) { this.currency = currency; return this; }
            Builder active(boolean active) { this.active = active; return this; }
            Builder addTag(String tag) { this.tags.add(tag); return this; }
            Account build() {
                // Validaciones al construir
                if (balance.compareTo(BigDecimal.ZERO) < 0 && creditLimit.equals(BigDecimal.ZERO)) {
                    throw new IllegalStateException("Negative balance requires credit limit");
                }
                return new Account(this);
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // 3. FACTORY PATTERN
    //    "Define una interfaz para crear objetos, pero deja que subclases/métodos decidan qué clase instanciar."
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Caso: Diferentes tipos de notificaciones según el evento.
     * Sin Factory → new ConcreteClass() directo. Con Factory → encapsula creación.
     */
    sealed interface Notification permits EmailNotification, SMSNotification, PushNotification {}
    record EmailNotification(String to, String subject, String body) implements Notification {}
    record SMSNotification(String phone, String text) implements Notification {}
    record PushNotification(String deviceId, String title, String message) implements Notification {}

    // Factory Method
    static Notification createNotification(String type, Map<String, String> params) {
        return switch (type.toUpperCase()) {
            case "EMAIL" -> new EmailNotification(
                    params.get("to"),
                    params.get("subject"),
                    params.get("body")
            );
            case "SMS" -> new SMSNotification(
                    params.get("phone"),
                    params.get("text")
            );
            case "PUSH" -> new PushNotification(
                    params.get("deviceId"),
                    params.get("title"),
                    params.get("message")
            );
            default -> throw new IllegalArgumentException("Unknown notification type: " + type);
        };
    }

    /**
     * VARIANTE: Factory con Supplier (usando lambdas)
     * Registro dinámico de factories.
     */
    static class NotificationFactory {
        private final Map<String, Supplier<Notification>> registry = new HashMap<>();

        void register(String type, Supplier<Notification> creator) {
            registry.put(type.toUpperCase(), creator);
        }

        Notification create(String type) {
            Supplier<Notification> creator = registry.get(type.toUpperCase());
            if (creator == null) throw new IllegalArgumentException("Unknown type: " + type);
            return creator.get();
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // RESUMEN Y DEMO
    // ══════════════════════════════════════════════════════════════════════════

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("  MÓDULO 05.02 — Design Patterns");
        System.out.println("═══════════════════════════════════════════════\n");

        // --- Strategy Demo ---
        System.out.println("1. STRATEGY PATTERN — Cálculo de interés");
        BigDecimal principal = new BigDecimal("50000.00");
        int months = 12;

        List<InterestStrategy> strategies = List.of(
                new SimpleInterest(new BigDecimal("0.08")),
                new CompoundInterest(new BigDecimal("0.08"), 12),
                new FixedInterest(new BigDecimal("200.00"))
        );

        for (InterestStrategy s : strategies) {
            BigDecimal interest = calculateReturn(principal, months, s);
            System.out.println("   " + s.getName() + " → $" + interest);
        }

        // --- Builder Demo ---
        System.out.println("\n2. BUILDER PATTERN — Creación de cuentas");
        Account savings = new Account.Builder("ACC-001", "Carlos Pérez")
                .type("SAVINGS")
                .balance(new BigDecimal("15000"))
                .currency("COP")
                .addTag("premium")
                .addTag("digital")
                .build();
        System.out.println("   " + savings);

        Account credit = new Account.Builder("ACC-002", "María López")
                .type("CREDIT")
                .balance(new BigDecimal("-2000"))
                .creditLimit(new BigDecimal("10000"))
                .currency("USD")
                .addTag("corporate")
                .build();
        System.out.println("   " + credit);

        // Sin builder sería: new Account("ACC-001", "Carlos", "SAVINGS", 15000, 0, "COP", true, List.of(...))
        // Ilegible y propenso a errores de orden de parámetros.

        // --- Factory Demo ---
        System.out.println("\n3. FACTORY PATTERN — Creación de notificaciones");
        Notification email = createNotification("EMAIL", Map.of(
                "to", "carlos@bank.com",
                "subject", "Transferencia exitosa",
                "body", "Se han transferido $500.00 a la cuenta destino."
        ));
        System.out.println("   " + email);

        Notification sms = createNotification("SMS", Map.of(
                "phone", "+57300123456",
                "text", "Retiro de $200.00 de tu cuenta. Saldo: $4800.00"
        ));
        System.out.println("   " + sms);

        // Factory con Supplier
        System.out.println("\n   Factory con Supplier:");
        NotificationFactory factory = new NotificationFactory();
        factory.register("ALERT", () -> new PushNotification("device-123", "⚠️ Alerta", "Transacción sospechosa detectada"));
        Notification alert = factory.create("ALERT");
        System.out.println("   " + alert);

        System.out.println("\n═══════════════════════════════════════════════");
        System.out.println("  RESUMEN Design Patterns");
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("  Strategy → Algoritmos intercambiables (tasas, fees)");
        System.out.println("  Builder  → Objetos complejos paso a paso");
        System.out.println("  Factory  → Desacopla la creación de objetos");
    }
}
