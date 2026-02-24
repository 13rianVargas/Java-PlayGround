import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * SOLUCIONES — Java 17 Features
 */
public class Solutions {

    // ── Tipos reutilizados de Exercises ───────────────────────────────────────

    record Account(String id, String owner, double balance, boolean active) {}

    record Transfer(String fromId, String toId, double amount) {}

    sealed interface TransferResult permits TransferResult.Success,
                                            TransferResult.InsufficientFunds,
                                            TransferResult.AccountNotFound,
                                            TransferResult.InactiveAccount {
        record Success(double newBalance) implements TransferResult {}
        record InsufficientFunds(double available, double requested) implements TransferResult {}
        record AccountNotFound(String accountId) implements TransferResult {}
        record InactiveAccount(String accountId) implements TransferResult {}
    }

    // ══════════════════════════════════════════════════════════════════════════════
    // E1 — processTransfer con sealed types
    // ══════════════════════════════════════════════════════════════════════════════

    public static TransferResult processTransfer(Map<String, Account> accounts,
                                                 Transfer transfer) {
        Account from = accounts.get(transfer.fromId());
        if (from == null) return new TransferResult.AccountNotFound(transfer.fromId());

        Account to = accounts.get(transfer.toId());
        if (to == null) return new TransferResult.AccountNotFound(transfer.toId());

        if (!from.active()) return new TransferResult.InactiveAccount(transfer.fromId());

        if (from.balance() < transfer.amount()) {
            return new TransferResult.InsufficientFunds(from.balance(), transfer.amount());
        }

        return new TransferResult.Success(from.balance() - transfer.amount());
    }

    // ══════════════════════════════════════════════════════════════════════════════
    // E2 — describeResult con switch expression sobre sealed type
    // ══════════════════════════════════════════════════════════════════════════════

    public static String describeResult(TransferResult result) {
        return switch (result) {
            case TransferResult.Success s ->
                "Transferencia exitosa. Saldo restante: %.2f".formatted(s.newBalance());
            case TransferResult.InsufficientFunds i ->
                "Saldo insuficiente: disponible %.2f, solicitado %.2f"
                    .formatted(i.available(), i.requested());
            case TransferResult.AccountNotFound a ->
                "Cuenta no encontrada: " + a.accountId();
            case TransferResult.InactiveAccount ia ->
                "Cuenta inactiva: " + ia.accountId();
            // No necesita default: sealed + exhaustivo
        };
    }

    // ══════════════════════════════════════════════════════════════════════════════
    // E3 — computeStats con Records y Streams
    // ══════════════════════════════════════════════════════════════════════════════

    record AccountStats(long count, double totalBalance, double averageBalance) {}

    public static AccountStats computeStats(List<Account> accounts) {
        var active = accounts.stream()
            .filter(Account::active)
            .collect(Collectors.toList());

        if (active.isEmpty()) return new AccountStats(0, 0.0, 0.0);

        double total = active.stream().mapToDouble(Account::balance).sum();
        return new AccountStats(active.size(), total, total / active.size());
    }

    // ══════════════════════════════════════════════════════════════════════════════
    // E4 — categorizeAccounts con pattern matching y streams
    // ══════════════════════════════════════════════════════════════════════════════

    /**
     * Clasifica cuentas activas en categorías según su saldo:
     * "HIGH"   → balance >= 10_000
     * "MEDIUM" → balance >= 1_000
     * "LOW"    → balance < 1_000
     */
    public static Map<String, List<Account>> categorizeAccounts(List<Account> accounts) {
        return accounts.stream()
            .filter(Account::active)
            .collect(Collectors.groupingBy(a -> {
                if (a.balance() >= 10_000) return "HIGH";
                else if (a.balance() >= 1_000) return "MEDIUM";
                else return "LOW";
            }));
    }

    // ══════════════════════════════════════════════════════════════════════════════
    // Main — Verificación
    // ══════════════════════════════════════════════════════════════════════════════

    public static void main(String[] args) {
        Map<String, Account> accounts = Map.of(
            "A1", new Account("A1", "Carlos", 5000.0, true),
            "A2", new Account("A2", "María", 15000.0, true),
            "A3", new Account("A3", "Pedro", 200.0, false)
        );

        // E1 — Transfer processing
        System.out.println("=== E1: processTransfer ===");
        System.out.println(processTransfer(accounts, new Transfer("A1", "A2", 3000.0)));
        System.out.println(processTransfer(accounts, new Transfer("A1", "A2", 9000.0)));
        System.out.println(processTransfer(accounts, new Transfer("A3", "A1", 100.0)));
        System.out.println(processTransfer(accounts, new Transfer("X9", "A1", 50.0)));

        // E2 — Describe results
        System.out.println("\n=== E2: describeResult ===");
        TransferResult ok = processTransfer(accounts, new Transfer("A1", "A2", 1000.0));
        TransferResult fail = processTransfer(accounts, new Transfer("A1", "A2", 99000.0));
        System.out.println(describeResult(ok));
        System.out.println(describeResult(fail));

        // E3 — Stats
        System.out.println("\n=== E3: computeStats ===");
        AccountStats stats = computeStats(List.copyOf(accounts.values()));
        System.out.println("Activas: " + stats.count());
        System.out.println("Total: " + stats.totalBalance());
        System.out.println("Promedio: " + stats.averageBalance());

        // E4 — Categorize
        System.out.println("\n=== E4: categorizeAccounts ===");
        Map<String, List<Account>> cats = categorizeAccounts(List.copyOf(accounts.values()));
        cats.forEach((cat, accs) -> {
            System.out.println(cat + ": " + accs.stream()
                .map(a -> a.id() + "($" + a.balance() + ")")
                .collect(Collectors.joining(", ")));
        });
    }
}
