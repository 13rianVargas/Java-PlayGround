/**
 * ============================================================
 *  01 — LAMBDAS — Ejercicios
 * ============================================================
 */

import java.util.*;
import java.util.function.*;
import java.math.BigDecimal;

public class Exercises {

    record Account(String id, String type, BigDecimal balance, boolean active) {}

    /**
     * EJERCICIO 1: Crear un Predicate que filtre cuentas activas
     * con balance mayor a un mínimo dado.
     *
     * Ejemplo: mínimo=1000 → filtra cuentas con balance > 1000 Y activas
     */
    public static Predicate<Account> activeWithMinBalance(BigDecimal minimum) {
        // TODO: Retorna un Predicate compuesto con .and()
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 2: Crear una Function que transforme un Account en un String
     * con formato: "ID: {id} | Type: {type} | Balance: ${balance}"
     */
    public static Function<Account, String> accountSummary() {
        // TODO: Retorna una Function<Account, String>
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 3: Aplicar una operación a todos los balances de una lista.
     *
     * Recibe una lista de BigDecimal y un UnaryOperator<BigDecimal>.
     * Retorna una nueva lista con el operador aplicado a cada balance.
     *
     * Ejemplo: balances=[100, 200], operator=(b -> b.multiply(1.05))
     * → [105.00, 210.00]
     */
    public static List<BigDecimal> applyToAll(List<BigDecimal> balances,
                                               UnaryOperator<BigDecimal> operator) {
        // TODO: Aplica el operador a cada elemento
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 4: Implementar un "pipeline" de validaciones.
     * 
     * Recibe una lista de Predicate<Account> y una cuenta.
     * Retorna true solo si TODOS los predicados se cumplen.
     *
     * Ejemplo:
    
     *   validations = [isActive, hasMinBalance(500), isType("SAVINGS")]
     *   account = Account("A1", "SAVINGS", 1000, true)
     *   → true (todas se cumplen)
     */
    public static boolean validateAll(Account account,
                                       List<Predicate<Account>> validations) {
        // TODO: Encadena todos los predicados con stream().allMatch() o reduce(.and())
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 5: Ordenar cuentas usando Comparator con lambdas.
     *
     * Ordena por: type ASC → balance DESC → id ASC
     *
     * Retorna una nueva lista ordenada (no modifica la original).
     */
    public static List<Account> sortAccounts(List<Account> accounts) {
        // TODO: Comparator.comparing().thenComparing().thenComparing()
        throw new UnsupportedOperationException("Implementa este método.");
    }


    // ─── Runner ───
    public static void main(String[] args) {
        System.out.println("=== LAMBDAS — Ejercicios ===\n");

        var accounts = List.of(
            new Account("A1", "SAVINGS", new BigDecimal("5000"), true),
            new Account("A2", "CHECKING", new BigDecimal("200"), true),
            new Account("A3", "SAVINGS", new BigDecimal("10000"), false),
            new Account("A4", "CHECKING", new BigDecimal("3500"), true)
        );
    

        // Test 1
        try {
            var pred = activeWithMinBalance(new BigDecimal("1000"));
            long count = accounts.stream().filter(pred).count();
         System.out.println("Ej1: " + count + (count == 2 ? " ✅" : " ❌ esperado 2"));
        } catch (Exception e) { System.out.println("Ej1: ⬜ No implementado"); }

        // Test 2
        try {
         var fn = accountSummary();
            String s = fn.apply(accounts.get(0));
            System.out.println("Ej2: " + s + (s.contains("A1") ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej2: ⬜ No implementado"); }

        // Test 3
        try {
            var balances = List.of(new BigDecimal("100"), new BigDecimal("200"));
            var result = applyToAll(balances, b -> b.multiply(new BigDecimal("1.05")));
            System.out.println("Ej3: " + result
                + (result.get(0).compareTo(new BigDecimal("105.00")) == 0 ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej3: ⬜ No implementado"); }

        // Test 4
        try {
            List<Predicate<Account>> rules = List.of(
                a -> a.active(),
                a -> a.balance().compareTo(new BigDecimal("500")) > 0
            );
            boolean r = validateAll(accounts.get(0), rules);
            System.out.println("Ej4: " + r + (r ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej4: ⬜ No implementado"); }

        // Test 5
        try {
            var sorted = sortAccounts(accounts);
            System.out.println("Ej5: " + sorted.get(0).id()
                + (sorted.get(0).id().equals("A4") ? " ✅" : " ❌ esperado A4 (CHECKING, $3500)"));
        } catch (Exception e) { System.out.println("Ej5: ⬜ No implementado"); }
    }
}
