import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * AccountService — Clase a testear en 01-JUnit5-Fundamentals.
 *
 * Servicio básico de operaciones de cuenta bancaria.
 * Los tests en test/java/ verifican todas las funcionalidades.
 */
public class AccountService {

    public record Account(String id, String owner, BigDecimal balance, boolean active) {
        public Account {
            Objects.requireNonNull(id, "id cannot be null");
            Objects.requireNonNull(owner, "owner cannot be null");
            Objects.requireNonNull(balance, "balance cannot be null");
        }
    }

    public record TransferResult(boolean success, String message, BigDecimal newSourceBalance) {
    }

    /**
     * Calcula el interés sobre un balance.
     *
     * @param balance    el saldo actual (no puede ser null ni negativo)
     * @param annualRate tasa anual (no puede ser null ni negativa)
     * @return interés con scale 2 y HALF_UP
     * @throws IllegalArgumentException si balance o rate son null o negativos
     */
    public BigDecimal calculateInterest(BigDecimal balance, BigDecimal annualRate) {
        if (balance == null || annualRate == null) {
            throw new IllegalArgumentException("Balance and rate cannot be null");
        }
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Balance cannot be negative: " + balance);
        }
        if (annualRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Rate cannot be negative: " + annualRate);
        }
        return balance.multiply(annualRate).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Transfiere dinero entre dos cuentas.
     *
     * @return TransferResult con el resultado de la operación
     */
    public TransferResult transfer(Account source, Account destination, BigDecimal amount) {
        if (source == null || destination == null) {
            return new TransferResult(false, "Source and destination cannot be null", BigDecimal.ZERO);
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return new TransferResult(false, "Amount must be positive", source.balance());
        }
        if (!source.active()) {
            return new TransferResult(false, "Source account is inactive", source.balance());
        }
        if (!destination.active()) {
            return new TransferResult(false, "Destination account is inactive", source.balance());
        }
        if (source.balance().compareTo(amount) < 0) {
            return new TransferResult(false, "Insufficient funds", source.balance());
        }
        BigDecimal newBalance = source.balance().subtract(amount);
        return new TransferResult(true, "Transfer successful", newBalance);
    }

    /**
     * Filtra cuentas activas con balance mayor al mínimo dado.
     */
    public List<Account> filterActiveAccounts(List<Account> accounts, BigDecimal minBalance) {
        if (accounts == null)
            return List.of();
        return accounts.stream()
                .filter(Account::active)
                .filter(a -> a.balance().compareTo(minBalance) >= 0)
                .sorted(Comparator.comparing(Account::balance).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Calcula el balance total de una lista de cuentas.
     */
    public BigDecimal totalBalance(List<Account> accounts) {
        if (accounts == null || accounts.isEmpty())
            return BigDecimal.ZERO;
        return accounts.stream()
                .map(Account::balance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Agrupa las cuentas por estado activo/inactivo y calcula el total de cada
     * grupo.
     */
    public Map<Boolean, BigDecimal> balanceByStatus(List<Account> accounts) {
        if (accounts == null || accounts.isEmpty())
            return Map.of();
        return accounts.stream()
                .collect(Collectors.groupingBy(
                        Account::active,
                        Collectors.reducing(BigDecimal.ZERO, Account::balance, BigDecimal::add)));
    }
}
