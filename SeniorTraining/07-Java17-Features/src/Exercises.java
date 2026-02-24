import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * EJERCICIOS — Java 17 Features
 *
 * Aplica Records, Sealed Classes y Switch Expressions en contexto financiero.
 */
public class Exercises {

    // ── Tipos de datos (Records) ──────────────────────────────────────────────

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

    /**
     * E1 — Procesa una transferencia entre dos cuentas.
     *
     * Reglas:
     * - Si la cuenta origen no existe → AccountNotFound(fromId)
     * - Si la cuenta destino no existe → AccountNotFound(toId)
     * - Si la cuenta origen no está activa → InactiveAccount(fromId)
     * - Si saldo insuficiente → InsufficientFunds(balance, amount)
     * - Si todo ok → Success(nuevoSaldo del origen)
     *
     * @param accounts mapa de id → Account
     * @param transfer la transferencia a procesar

         * @return TransferResult correspondiente
     */
    public static TransferResult processTransfer(Map<String, Account> accounts,
                                                 Transfer transfer) {
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * E2 — Genera un mensaje descriptivo para un TransferResult.
     * Usa switch expression con el sealed type.
     *
     * Ejemplos:
     * Success(4500.0)               → "Transferencia exitosa. Saldo restante: 4500.00"
     * InsufficientFunds(100, 500)   → "Saldo insuficiente: disponible 100.00, solicitado 500.00"
     * AccountNotFound("ACC123")     → "Cuenta no encontrada: ACC123"
     * InactiveAccount("ACC456")     → "Cuenta inactiva: ACC456"
     */
    public static String describeResult(TransferResult result) {
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * E3 — Calcula estadísticas de una lista de cuentas activas.
     * Retorna un record con: count, totalBalance, averageBalance.
     *
     * Si no hay cuentas activas: count=0, totalBalance=0.0, averageBalance=0.0.
     */
    record AccountStats(long count, double totalBalance, double averageBalance) {}


    
    public static AccountStats computeStats(List<Account> accounts) {
        throw new UnsupportedOperationException("Implementa este método.");
    }
}
