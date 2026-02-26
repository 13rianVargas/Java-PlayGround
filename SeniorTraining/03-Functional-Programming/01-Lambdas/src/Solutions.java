/**
 * ============================================================
 *  01 — LAMBDAS — Soluciones Anotadas
 * ============================================================
 */

import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.math.BigDecimal;

public class Solutions {

    record Account(String id, String type, BigDecimal balance, boolean active) {}

    /**
     * EJERCICIO 1: Predicate compuesto.
     *
     * Compone dos predicados con .and() — ambos deben cumplirse.
     */
    public static Predicate<Account> activeWithMinBalance(BigDecimal minimum) {
        Predicate<Account> isActive = Account::active;
        Predicate<Account> hasMinBalance = a -> a.balance().compareTo(minimum) > 0;
        return isActive.and(hasMinBalance);
    }

    /**
     * EJERCICIO 2: Function de transformación.
     *
     * Clave: la lambda captura el formato del string.
     */
    public static Function<Account, String> accountSummary() {
        return a -> "ID: " + a.id() + " | Type: " + a.type() + " | Balance: $" + a.balance();
    }

    /**
     * EJERCICIO 3: Aplicar UnaryOperator a lista.
     *
     * Complejidad: O(n)
     * Clave: UnaryOperator<T> extiende Function<T,T>
     */
    public static List<BigDecimal> applyToAll(List<BigDecimal> balances,
                                               UnaryOperator<BigDecimal> operator) {
        return balances.stream()
                .map(operator)
                .collect(Collectors.toList());
    }
    

    /**
     * EJERCICIO 4: Pipeline de validaciones.
     *
     * DOS ENFOQUES:
     * a) Stream: validations.stream().allMatch(pred -> pred.test(account))
     * b) Reduce: validations.stream().reduce(Predicate::and) → combinado.test(account)
     */
    public static boolean validateAll(Account account,
                                       List<Predicate<Account>> validations) {
        // Enfoque a) — más legible
        return validations.stream().allMatch(pred -> pred.test(account));

        // Enfoque b) — más funcional
        // return validations.stream()
        //         .reduce(a -> true, Predicate::and)
        //         .test(account);
    }

    /**
     * EJERCICIO 5: Comparator multicriteria con lambdas.
     *
     * type ASC → balance DESC → id ASC
     */
    public static List<Account> sortAccounts(List<Account> accounts) {
        return accounts.stream()
                .sorted(Comparator.comparing(Account::type)
                        .thenComparing(Comparator.comparing(Account::balance).reversed())
                        .thenComparing(Account::id))
                .collect(Collectors.toList());
    }


    // ─── Verificación ───
    public static void main(String[] args) {
        System.out.println("=== LAMBDAS — Soluciones ===\n");

        var accounts = List.of(
            new Account("A1", "SAVINGS", new BigDecimal("5000"), true),
            new Account("A2", "CHECKING", new BigDecimal("200"), true),
            new Account("A3", "SAVINGS", new BigDecimal("10000"), false),
            new Account("A4", "CHECKING", new BigDecimal("3500"), true)
        );

        var pred = activeWithMinBalance(new BigDecimal("1000"));
        System.out.println("Ej1: " + accounts.stream().filter(pred).count());  // 2
        System.out.println("Ej2: " + accountSummary().apply(accounts.get(0))); // ID: A1 | ...
        System.out.println("Ej3: " + applyToAll(
            List.of(new BigDecimal("100")),
            b -> b.multiply(new BigDecimal("1.05"))));                          // [105.00]
        System.out.println("Ej4: " + validateAll(accounts.get(0), List.of(
            a -> a.active(), a -> a.balance().compareTo(new BigDecimal("500")) > 0))); // true
        System.out.println("Ej5 first: " + sortAccounts(accounts).get(0).id()); // A4
    }
}
