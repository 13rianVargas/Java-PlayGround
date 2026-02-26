
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================
 * MÓDULO 05.01 — SOLID Principles: Guía completa
 * ============================================================
 *
 * Los 5 principios SOLID son la base del diseño de software Senior.
 * Aquí los aplicamos al dominio financiero con ejemplos reales.
 */
public class Main {

 

/**
 * ❌ VIOLACIÓN SRP — Esta clase hace TODO:
 * Calcula interés
 * Valida transferencias
 * Formatea reportes
 */
static class AccountServiceBAD {
    BigDecimal calculateInterest(BigDecimal balance, BigDecimal rate) {
        return balance.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }

    boolean validateTransfer(BigDecimal balance, BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }

    String formatReport(String accountId, BigDecimal balance) {
        return String.format("Account %s: $%s", accountId, balance);
    }
}

/**
 * ✅ SRP CORRECTO — Cada clase tiene UNA responsabilidad:
 */
static class InterestCalculator {
    BigDecimal calculate(BigDecimal balance, BigDecimal rate) {
        return balance.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }
}

static class TransferValidator {
    boolean hasSufficientFunds(BigDecimal balance, BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }
}

static class AccountReportFormatter {
    String format(String accountId, BigDecimal balance) {
        return String.format("Account %s: $%s", accountId, balance);
    }
}

// ══════════════════════════════════════════════════════════════════════════
// 2. OCP — Open/Closed Principle
//    "Abierto para extensión, cerrado para modificación."
// ══════════════════════════════════════════════════════════════════════════

/**
 * ❌ VIOLACIÓN OCP — Cada nuevo tipo requiere modificar el switch.
 */
// static BigDecimal calculateInterestBAD(String type, BigDecimal balance) {
//     return switch (type) {
//         case "SAVINGS" -> balance.multiply(new BigDecimal("0.04"));
//         default -> BigDecimal.ZERO;
//     };
// }

/**
 * ✅ OCP CORRECTO — Nuevos tipos = nuevas clases, sin modificar existentes.
 */
interface InterestPolicy {
    BigDecimal calculateInterest(BigDecimal balance);
}

static class SavingsInterest implements InterestPolicy {
    public BigDecimal calculateInterest(BigDecimal balance) {
        return balance.multiply(new BigDecimal("0.04")).setScale(2, RoundingMode.HALF_UP);
    }
}

static class CheckingInterest implements InterestPolicy {
    public BigDecimal calculateInterest(BigDecimal balance) {
        return balance.multiply(new BigDecimal("0.01")).setScale(2, RoundingMode.HALF_UP);
    }
}

// Nuevo tipo: solo agrega una nueva clase.
static class PremiumInterest implements InterestPolicy {
    public BigDecimal calculateInterest(BigDecimal balance) {
        BigDecimal base = balance.multiply(new BigDecimal("0.05"));
        if (balance.compareTo(new BigDecimal("10000")) > 0) {
            base = base.add(new BigDecimal("100"));
        }
        return base.setScale(2, RoundingMode.HALF_UP);
    }
}

// ══════════════════════════════════════════════════════════════════════════
// 3. LSP — Liskov Substitution Principle
//    "Si S es subtipo de T, entonces objetos de T pueden
//     ser reemplazados por objetos de S sin alterar el comportamiento."
// ══════════════════════════════════════════════════════════════════════════

static class BankAccount {
    protected BigDecimal balance;

    BankAccount(BigDecimal balance) { this.balance = balance; }
    void deposit(BigDecimal amount) { balance = balance.add(amount); }
    void withdraw(BigDecimal amount) { balance = balance.subtract(amount); }
    BigDecimal getBalance() { return balance; }
}

/**
 * ✅ LSP CORRECTO — Separar interfaces para que subtipos cumplan contratos.
 */
interface Depositable {
    void deposit(BigDecimal amount);
    BigDecimal getBalance();
}

interface Withdrawable extends Depositable {
    void withdraw(BigDecimal amount);
}

static class FullAccount implements Withdrawable {
    private BigDecimal balance;

    FullAccount(BigDecimal balance) { this.balance = balance; }
    public void deposit(BigDecimal amount) { balance = balance.add(amount); }
    public void withdraw(BigDecimal amount) { balance = balance.subtract(amount); }
    public BigDecimal getBalance() { return balance; }
}

static class DepositOnlyAccount implements Depositable {
    private BigDecimal balance;

    DepositOnlyAccount(BigDecimal balance) { this.balance = balance; }
    public void deposit(BigDecimal amount) { balance = balance.add(amount); }
    public BigDecimal getBalance() { return balance; }
    // No withdraw → no puede romper ningún contrato.
}

// ══════════════════════════════════════════════════════════════════════════
// 4. ISP — Interface Segregation Principle
//    "Los clientes no deben verse forzados a depender de interfaces
//     que no utilizan."
// ══════════════════════════════════════════════════════════════════════════

/**
 * ✅ ISP CORRECTO — Interfaces pequeñas y específicas.
 */
interface TransactionCapable {
    void deposit(BigDecimal amount);
    void withdraw(BigDecimal amount);
}

interface InterestBearing {
    void applyInterest();
}

interface Reportable {
    String generateReport();
}

interface Notifiable {
    void sendNotification(String message);
}

    // Cada Account implementa lo que NECESITA:
    static class SavingsAccount implements TransactionCapable, InterestBearing, Reportable {
        private BigDecimal balance = BigDecimal.ZERO;
        private final BigDecimal rate = new BigDecimal("0.04");

        public void deposit(BigDecimal amount) { balance = balance.add(amount); }
        public void withdraw(BigDecimal amount) { balance = balance.subtract(amount); }
        public void applyInterest() {
            balance = balance.add(balance.multiply(rate)).setScale(2, RoundingMode.HALF_UP);
        }
        public String generateReport() {
            return "Savings Balance: $" + balance;
        }
        // No implementa Notifiable → no está obligada a tener sendNotification
    }

    // ══════════════════════════════════════════════════════════════════════════
    // 5. DIP — Dependency Inversion Principle
    //    "Los módulos de alto nivel no deben depender de los de bajo nivel.
    //     Ambos deben depender de abstracciones."
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * ❌ VIOLACIÓN DIP — TransferService depende directamente de implementaciones concretas.
     */
    static class MySQLAccountRepository {
        BankAccount findById(String id) { return new BankAccount(new BigDecimal("1000")); }
    }

    static class TransferServiceBAD {
        private final MySQLAccountRepository repo = new MySQLAccountRepository(); // ⚠️ Acoplamiento
        void transfer(String fromId, String toId, BigDecimal amount) {
            // Siempre usa MySQL. ¿Qué pasa si cambias a PostgreSQL?
        }
    }

    /**
     * ✅ DIP CORRECTO — Depende de abstracciones, no de implementaciones.
     */
    interface AccountRepository {
        Optional<BankAccount> findById(String id);
        void save(BankAccount account);
    }

    static class TransferService {
        private final AccountRepository repository; // ← Abstracción

        TransferService(AccountRepository repository) { // ← Inyección de dependencia
            this.repository = repository;
        }

        String transfer(String fromId, String toId, BigDecimal amount) {
            Optional<BankAccount> from = repository.findById(fromId);
            Optional<BankAccount> to = repository.findById(toId);
            if (from.isEmpty() || to.isEmpty()) return "Account not found";
            if (from.get().getBalance().compareTo(amount) < 0) return "Insufficient funds";
            from.get().withdraw(amount);
            to.get().deposit(amount);
            repository.save(from.get());
            repository.save(to.get());
            return "Transfer successful";
        }
    }

    // Ahora puedes tener cualquier implementación:
    static class InMemoryAccountRepository implements AccountRepository {
        private final Map<String, BankAccount> store = new HashMap<>();
        public Optional<BankAccount> findById(String id) { return Optional.ofNullable(store.get(id)); }
        public void save(BankAccount account) { /* guardar en memoria */ }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // RESUMEN Y DEMO
    // ══════════════════════════════════════════════════════════════════════════

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("  MÓDULO 05.01 — SOLID Principles");
        System.out.println("═══════════════════════════════════════════════\n");

        // --- SRP Demo ---
        System.out.println("1. SRP — Single Responsibility");
        InterestCalculator calc = new InterestCalculator();
        TransferValidator validator = new TransferValidator();
        AccountReportFormatter formatter = new AccountReportFormatter();

        BigDecimal interest = calc.calculate(new BigDecimal("10000"), new BigDecimal("0.05"));
        System.out.println("   Interés calculado: $" + interest);
        System.out.println("   ¿Fondos suficientes (10k para 5k)? " +
                validator.hasSufficientFunds(new BigDecimal("10000"), new BigDecimal("5000")));
        System.out.println("   Reporte: " + formatter.format("ACC-001", new BigDecimal("10000")));

        // --- OCP Demo ---
        System.out.println("\n2. OCP — Open/Closed");
        List<InterestPolicy> policies = List.of(
                new SavingsInterest(),
                new CheckingInterest(),
                new PremiumInterest()
        );
        BigDecimal balance = new BigDecimal("50000");
        for (InterestPolicy p : policies) {
            System.out.println("   " + p.getClass().getSimpleName() +
                    " sobre $50,000 = $" + p.calculateInterest(balance));
        }

        // --- LSP Demo ---
        System.out.println("\n3. LSP — Liskov Substitution");
        FullAccount full = new FullAccount(new BigDecimal("5000"));
        full.withdraw(new BigDecimal("1000"));
        System.out.println("   FullAccount después de withdraw $1000: $" + full.getBalance());

        DepositOnlyAccount depOnly = new DepositOnlyAccount(new BigDecimal("3000"));
        depOnly.deposit(new BigDecimal("500"));
        System.out.println("   DepositOnlyAccount después de deposit $500: $" + depOnly.getBalance());
        // No hay withdraw → no puede romper el contrato.

        // --- ISP Demo ---
        System.out.println("\n4. ISP — Interface Segregation");
        SavingsAccount savings = new SavingsAccount();
        savings.deposit(new BigDecimal("10000"));
        savings.applyInterest();
        System.out.println("   " + savings.generateReport());

        // --- DIP Demo ---
        System.out.println("\n5. DIP — Dependency Inversion");
        AccountRepository repo = new InMemoryAccountRepository();
        TransferService service = new TransferService(repo);
        System.out.println("   TransferService usa: " + repo.getClass().getSimpleName());
        System.out.println("   (Se puede cambiar a PostgreSQL sin tocar TransferService)");

        System.out.println("\n═══════════════════════════════════════════════");
        System.out.println("  RESUMEN SOLID");
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("  S → Single Responsibility: una razón para cambiar");
        System.out.println("  O → Open/Closed: extensión sin modificación");
        System.out.println("  L → Liskov Substitution: subtipos cumplen contrato");
        System.out.println("  I → Interface Segregation: interfaces pequeñas");
        System.out.println("  D → Dependency Inversion: depender de abstracciones");
    }
}
