import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * SOLUCIONES — SOLID Principles
 *
 * Soluciones anotadas con explicación del principio aplicado.
 */
public class Builder {

    record Transaction(String id, BigDecimal amount, String type, String status) {}
    record Account(String id, String owner, BigDecimal balance, String type) {}

    // ═══════════════════════════════════════════════════════════════════════════
    // E1 — SRP: Separar responsabilidades
    // Principio: Cada método tiene UNA responsabilidad.
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Filtrar por status — responsabilidad: SELECCIÓN
     * Complejidad: O(n) donde n = tamaño de la lista
     */
    public static List<Transaction> filterByStatus(List<Transaction> txns, String status) {
        return txns.stream()
                .filter(t -> t.status().equals(status))
                .collect(Collectors.toList());
    }

    /**
     * Calcular total — responsabilidad: AGREGACIÓN
     * Complejidad: O(n)
     */
    public static BigDecimal calculateTotal(List<Transaction> txns) {
        return txns.stream()
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // E2 — OCP: Diseñar con extensibilidad
    // Principio: Nuevos tipos de fee = nueva clase, sin modificar las existentes.
    // ═══════════════════════════════════════════════════════════════════════════

    interface FeeCalculator {
    
        BigDecimal calculateFee(BigDecimal amount);
    }

    static class StandardFee implements FeeCalculator {
        public BigDecimal calculateFee(BigDecimal amount) {
            return amount.multiply(new BigDecimal("0.015"))
                    .setScale(2, RoundingMode.HALF_UP);
        }
    }

    static class PremiumFee implements FeeCalculator {
        public BigDecimal calculateFee(BigDecimal amount) {
            return amount.multiply(new BigDecimal("0.005"))
                    .setScale(2, RoundingMode.HALF_UP);
        }
    }

    /**
     * applyFee es genérico — funciona con cualquier FeeCalculator.
         * Para agregar "EnterpriseFee" solo creas una nueva clase.
     */
    public static BigDecimal applyFee(BigDecimal amount, FeeCalculator calculator) {
        BigDecimal fee = calculator.calculateFee(amount);
        return amount.subtract(fee).setScale(2, RoundingMode.HALF_UP);
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // E3 — LSP: Verificar sustitución segura
    // Principio: Ambos subtipos cumplen el contrato de BankAccountOps.
    //            withdraw() retorna boolean — nunca lanza excepción inesperada.
    // ═══════════════════════════════════════════════════════════════════════════

    interface BankAccountOps {
        void deposit(BigDecimal amount);
        boolean withdraw(BigDecimal amount);
        BigDecimal getBalance();
    }

    static class CreditAccount implements BankAccountOps {
        private BigDecimal balance;
        private final BigDecimal creditLimit;

        CreditAccount(BigDecimal initialBalance, BigDecimal creditLimit) {
            this.balance = initialBalance;
            this.creditLimit = creditLimit;
        }

        public void deposit(BigDecimal amount) {
            balance = balance.add(amount);
        }

        /**
         * Permite balance negativo hasta -creditLimit.
         * Retorna true si se pudo, false si excede el límite.
         */
        public boolean withdraw(BigDecimal amount) {
            BigDecimal newBalance = balance.subtract(amount);
            if (newBalance.compareTo(creditLimit.negate()) < 0) {
                return false; // Excede límite de crédito
            }
            balance = newBalance;
            return true;
        }

        public BigDecimal getBalance() { return balance; }
    }

    static class SavingsAccountStrict implements BankAccountOps {
        private BigDecimal balance;

        SavingsAccountStrict(BigDecimal initialBalance) {
            this.balance = initialBalance;
        }

        public void deposit(BigDecimal amount) {
            balance = balance.add(amount);
        }

        /**
         * Balance nunca negativo. Retorna false si fondos insuficientes.
         * NO lanza excepción → cumple el contrato de la interfaz.
         */
        public boolean withdraw(BigDecimal amount) {
            if (balance.compareTo(amount) < 0) {
                return false; // Sin fondos suficientes
            }
            balance = balance.subtract(amount);
            return true;
        }

        public BigDecimal getBalance() { return balance; }
    }
    

    // ═══════════════════════════════════════════════════════════════════════════
    // E4 — ISP: Filtros composables
    // Principio: Predicados pequeños y componibles, no un mega-filtro monolítico.
    // Complejidad: O(n × m) donde n = transacciones, m = número de filtros
    // ═══════════════════════════════════════════════════════════════════════════

    public static List<Transaction> filterTransactions(List<Transaction> txns,
                                                       List<Predicate<Transaction>> filters) {
        // Combina todos los predicados en uno solo con AND
        Predicate<Transaction> combined = filters.stream()
                .reduce(Predicate::and)
                .orElse(t -> true); // Si no hay filtros, deja pasar todo

        return txns.stream()
                .filter(combined)        .collect(Collectors.toList());
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // E5 — DIP: Inversión de dependencias
    // Principio: NotificationDispatcher depende de la abstracción NotificationChannel,
    //            no de EmailChannel o SMSChannel directamente.
    // ═══════════════════════════════════════════════════════════════════════════

    interface NotificationChannel {
        void send(String recipient, String message);
        String getLastMessage();
    }

    static class EmailChannel implements NotificationChannel {
        private String lastMessage = "";

        public void send(String recipient, String message) {
            lastMessage = "EMAIL to " + recipient + ": " + message;
            System.out.println("   → " + lastMessage);
        }

        public String getLastMessage() { return lastMessage; }
    }

    static class SMSChannel implements NotificationChannel {
        private String lastMessage = "";

        public void send(String recipient, String message) {
            lastMessage = "SMS to " + recipient + ": " + message;
            System.out.println("   → " + lastMessage);
        }

        public String getLastMessage() { return lastMessage; }
    }

    static class NotificationDispatcher {
        private final List<NotificationChannel> channels;

        NotificationDispatcher(List<NotificationChannel> channels) {
            this.channels = channels;
        }

        void notifyAll(String recipient, String message) {
            for (NotificationChannel channel : channels) {
                channel.send(recipient, message);
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════");
        System.out.println("  SOLID Solutions — Verification");
        System.out.println("═══════════════════════════════════════\n");

        List<Transaction> txns = List.of(
                new Transaction("T1", new BigDecimal("500"), "WIRE", "COMPLETED"),
                new Transaction("T2", new BigDecimal("200"), "WIRE", "PENDING"),
                new Transaction("T3", new BigDecimal("300"), "ACH", "COMPLETED")
        );

        // E1
        List<Transaction> completed = filterByStatus(txns, "COMPLETED");
        System.out.println("E1a — Filtered COMPLETED: " + completed.size() + " ✓ (expected 2)");
        System.out.println("E1b — Total: $" + calculateTotal(completed) + " ✓ (expected 800)");

        // E2
        BigDecimal amount = new BigDecimal("1000.00");
        System.out.println("\nE2 — Standard fee: $" + applyFee(amount, new StandardFee()) +
                " ✓ (expected 985.00)");
        System.out.println("E2 — Premium fee: $" + applyFee(amount, new PremiumFee()) +
                " ✓ (expected 995.00)");

        // E3
        System.out.println("\nE3 — LSP Verification:");
        BankAccountOps credit = new CreditAccount(new BigDecimal("1000"), new BigDecimal("5000"));
        System.out.println("   CreditAccount withdraw $1500: " + credit.withdraw(new BigDecimal("1500")) +
                " ✓ (balance: " + credit.getBalance() + ")");

        BankAccountOps savings = new SavingsAccountStrict(new BigDecimal("1000"));
        System.out.println("   SavingsStrict withdraw $1500: " + savings.withdraw(new BigDecimal("1500")) +
                " ✓ (balance: " + savings.getBalance() + ", unchanged)");

        // E4
        System.out.println("\nE4 — Composable Filters:");
        List<Predicate<Transaction>> filters = List.of(
                t -> t.amount().compareTo(BigDecimal.valueOf(250)) > 0,
                t -> t.status().equals("COMPLETED")
        );
        List<Transaction> filtered = filterTransactions(txns, filters);
        System.out.println("   Filtered (>250 AND COMPLETED): " + filtered.size() +
                " ✓ (expected 2)");
        filtered.forEach(t -> System.out.println("   → " + t.id() + ": $" + t.amount()));

        // E5
        System.out.println("\nE5 — DIP Notification:");
        EmailChannel email = new EmailChannel();
        SMSChannel sms = new SMSChannel();
        NotificationDispatcher dispatcher = new NotificationDispatcher(List.of(email, sms));
        dispatcher.notifyAll("user@bank.com", "Transfer completed: $500.00");
        System.out.println("   Email last: " + email.getLastMessage());
        System.out.println("   SMS last: " + sms.getLastMessage());
    }
}
