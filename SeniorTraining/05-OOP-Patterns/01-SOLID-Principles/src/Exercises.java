import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Predicate;

/**
 * EJERCICIOS — SOLID Principles
 *
 * Aplica los 5 principios SOLID en contexto financiero.
 * Cada ejercicio te pide diseñar o refactorizar código siguiendo un principio.
 */
public class Exercises {

    // ── Tipos base ────────────────────────────────────────────────────────────

    record Transaction(String id, BigDecimal amount, String type, String status) {}
    record Account(String id, String owner, BigDecimal balance, String type) {}

    // ═══════════════════════════════════════════════════════════════════════════
    // E1 — SRP: Separar responsabilidades
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Tienes una lista de transacciones.
     * Implementa DOS métodos separados (SRP):
     *
     * a) filterByStatus: filtra transacciones por status dado.
     * b) calculateTotal: suma el amount de una lista de transacciones.
     *
     * En vez de UNA función que haga ambas cosas.
     */
    public static List<Transaction> filterByStatus(List<Transaction> txns, String status) {
        throw new UnsupportedOperationException("Implementa este método.");
    }

    public static BigDecimal calculateTotal(List<Transaction> txns) {
        throw new UnsupportedOperationException("Implementa este método.");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // E2 — OCP: Diseñar con extensibilidad
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Implementa la interfaz FeeCalculator y sus dos implementaciones:
     * - StandardFee: cobra 1.5% del monto (0.015)
     * - PremiumFee: cobra 0.5% del monto (0.005)
     *
     * Luego implementa applyFee que usa un FeeCalculator genérico.
     * Resultado = amount - fee, con scale 2 y HALF_UP.
     */
    interface FeeCalculator {
        BigDecimal calculateFee(BigDecimal amount);
    }

    // TODO: Implementa StandardFee
    // TODO: Implementa PremiumFee

    public static BigDecimal applyFee(BigDecimal amount, FeeCalculator calculator) {
        throw new UnsupportedOperationException("Implementa este método.");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // E3 — LSP: Verificar sustitución segura
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Diseña una jerarquía de cuentas donde:
     * - CreditAccount puede tener balance negativo (hasta un límite)
     * - SavingsAccountStrict NO puede tener balance negativo
     *
     * Implementa la interfaz BankAccountOps y ambas clases.
     * withdraw() retorna true si se pudo retirar, false si no.
     * Ambos subtipos deben ser sustituibles sin romper el contrato.
     */
    interface BankAccountOps {
        void deposit(BigDecimal amount);
        boolean withdraw(BigDecimal amount);  // retorna true si exitoso
        BigDecimal getBalance();
    }

    // TODO: Implementa CreditAccount (con creditLimit en constructor)
    // TODO: Implementa SavingsAccountStrict (balance nunca negativo)

    // ═══════════════════════════════════════════════════════════════════════════
    // E4 — ISP: Filtros composables
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Implementa un sistema de filtrado de transacciones usando
     * interfaces funcionales segregadas (no una interfaz monolítica).
     *
     * filterTransactions: recibe una lista de transacciones y una lista de
     * Predicate<Transaction>. Retorna solo las transacciones que cumplen
     * TODOS los predicados (AND lógico).
     *
     * Ejemplo: filterTransactions(txns, List.of(
     *     t -> t.amount().compareTo(BigDecimal.valueOf(100)) > 0,
     *     t -> t.status().equals("COMPLETED")
     * ))
     * → solo transacciones completadas con amount > 100
     */
    public static List<Transaction> filterTransactions(List<Transaction> txns,
                                                       List<Predicate<Transaction>> filters) {
        throw new UnsupportedOperationException("Implementa este método.");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // E5 — DIP: Inversión de dependencias
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Implementa un NotificationService que depende de una abstracción
     * NotificationChannel, no de implementaciones concretas.
     *
     * NotificationChannel tiene: void send(String recipient, String message)
     *
     * Implementa:
     * - EmailChannel: retorna "EMAIL to {recipient}: {message}"
     *   (guarda el último mensaje enviado en lastMessage)
     * - SMSChannel: retorna "SMS to {recipient}: {message}"
     *   (guarda el último mensaje enviado en lastMessage)
     *
     * NotificationDispatcher recibe un List<NotificationChannel> y tiene:
     * - notifyAll(String recipient, String message): envía por todos los canales
     */
    interface NotificationChannel {
        void send(String recipient, String message);
        String getLastMessage();
    }

    // TODO: Implementa EmailChannel
    // TODO: Implementa SMSChannel
    // TODO: Implementa NotificationDispatcher

    // ═══════════════════════════════════════════════════════════════════════════

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════");
        System.out.println("  SOLID Exercises — Self Test");
        System.out.println("═══════════════════════════════════════\n");

        // E1 Test
        List<Transaction> txns = List.of(
                new Transaction("T1", new BigDecimal("500"), "WIRE", "COMPLETED"),
                new Transaction("T2", new BigDecimal("200"), "WIRE", "PENDING"),
                new Transaction("T3", new BigDecimal("300"), "ACH", "COMPLETED")
        );

        List<Transaction> completed = filterByStatus(txns, "COMPLETED");
        System.out.println("E1a — Filtered COMPLETED: " + completed.size() + " (expected 2)");
        System.out.println("E1b — Total of completed: $" +
                calculateTotal(completed) + " (expected 800)");

        // E2 Test
        BigDecimal amount = new BigDecimal("1000.00");
        // Uncomment after implementing:
        // FeeCalculator standard = new StandardFee();
        // FeeCalculator premium = new PremiumFee();
        // System.out.println("E2 — Standard fee result: $" + applyFee(amount, standard) + " (expected 985.00)");
        // System.out.println("E2 — Premium fee result: $" + applyFee(amount, premium) + " (expected 995.00)");

        // E4 Test
        List<Predicate<Transaction>> filters = List.of(
                t -> t.amount().compareTo(BigDecimal.valueOf(250)) > 0,
                t -> t.status().equals("COMPLETED")
        );
        List<Transaction> filtered = filterTransactions(txns, filters);
        System.out.println("E4 — Filtered (>250 AND COMPLETED): " +
                filtered.size() + " (expected 2: T1, T3)");
    }
}
