/**
 * ============================================================
 *  03 — OPTIONAL<T>
 * ============================================================
 *
 *  Optional es el reemplazo de Java para null checks.
 *  NO es un contenedor genérico — es una forma de expresar
 *  "este valor puede o no existir" en la firma del método.
 *
 *  REGLAS DE ORO:
 *  1. NUNCA usar Optional como parámetro de método
 *  2. NUNCA usar Optional como campo de clase
 *  3. USAR Optional como tipo de retorno cuando el resultado puede no existir
 *  4. NUNCA llamar .get() sin .isPresent() — usar .orElse() o .orElseThrow()
 *
 *  ============================================================
 */

import java.util.*;
import java.util.stream.*;
import java.math.BigDecimal;

public class Main {

    record Account(String id, String owner, BigDecimal balance) {}

    static final List<Account> ACCOUNTS = List.of(
        new Account("A001", "Alice", new BigDecimal("5000.00")),
        new Account("A002", "Bob", new BigDecimal("2500.00")),
        new Account("A003", "Charlie", new BigDecimal("15000.00"))
    );

    public static void main(String[] args) {
        System.out.println("=== OPTIONAL ===\n");

        // ─────────────────────────────────
        // 1) Crear Optional
        // ─────────────────────────────────
        System.out.println("--- 1) Crear Optional ---");

        Optional<String> present = Optional.of("value");           // valor presente
        Optional<String> empty   = Optional.empty();               // valor ausente
        Optional<String> nullable = Optional.ofNullable(null);
        Optional<String> nullable2 = Optional.ofNullable("hello");

        System.out.println("present: " + present);
        System.out.println("empty: " + empty);
        System.out.println("ofNullable(null): " + nullable);
        System.out.println("ofNullable('hello'): " + nullable2);

        // ⚠️ NUNCA: Optional.of(null) → NullPointerException


        // ─────────────────────────────────
        // 2) Extraer valores de forma segura
        // ─────────────────────────────────
        System.out.println("\n--- 2) Extraer valores ---");

        // ❌ MAL: .get() puede lanzar NoSuchElementException
        // String val = empty.get(); // BOOM

        // ✅ orElse — valor por defecto
        String val1 = empty.orElse("default");
        System.out.println("orElse: " + val1);

        // ✅ orElseGet — lazy, calcula solo si vacío
        String val2 = empty.orElseGet(() -> "computed_" + System.currentTimeMillis());
        System.out.println("orElseGet: " + val2);

        // ✅ orElseThrow — lanzar excepción específica
        try {
            String val3 = empty.orElseThrow(() ->
                    new IllegalArgumentException("Valor requerido"));
        } catch (IllegalArgumentException e) {
            System.out.println("orElseThrow: " + e.getMessage());
        }

        // ✅ Java 11+ — orElseThrow() sin argumento (NoSuchElementException)
        try {
            String val4 = empty.orElseThrow();
        } catch (java.util.NoSuchElementException e) {
            System.out.println("orElseThrow(): " + e.getClass().getSimpleName());
        }


        // ─────────────────────────────────
        // 3) Transformar con map y flatMap
        // ─────────────────────────────────
        System.out.println("\n--- 3) map / flatMap ---");

        Optional<Account> account = findById("A001");

        // map: transformar el contenido si existe
        Optional<String> ownerName = account.map(Account::owner);
        System.out.println("Owner: " + ownerName.orElse("Not found"));
        // map encadenado
        Optional<Integer> nameLength = account
                .map(Account::owner)
                .map(String::length);
        System.out.println("Name length: " + nameLength.orElse(0));

        // flatMap: cuando la función ya retorna Optional
        Optional<BigDecimal> balance = findById("A001")
                .flatMap(Main::getVerifiedBalance);
        System.out.println("Verified balance: " + balance.orElse(BigDecimal.ZERO));


        // ─────────────────────────────────
        // 4) filter — condicionar
        // ─────────────────────────────────
        System.out.println("\n--- 4) filter ---");

        Optional<Account> highBalance = findById("A003")
                .filter(a -> a.balance().compareTo(new BigDecimal("10000")) > 0);
        System.out.println("High balance account: " + highBalance.map(Account::id).orElse("None"));

        Optional<Account> lowBalance = findById("A002")
                .filter(a -> a.balance().compareTo(new BigDecimal("10000")) > 0);
        System.out.println("Low balance filtered: " + lowBalance.map(Account::id).orElse("None"));


        // ─────────────────────────────────
        // 5) ifPresent / ifPresentOrElse (Java 11)
        // ─────────────────────────────────
        System.out.println("\n--- 5) ifPresent ---");

        findById("A001").ifPresent(a ->
                System.out.println("Found: " + a.owner() + " with $" + a.balance()));

        findById("XXXX").ifPresentOrElse(
                a -> System.out.println("Found: " + a.id()),
                () -> System.out.println("Account not found")
        );


        // ─────────────────────────────────
        // 6) Optional con Streams
        // ─────────────────────────────────
        System.out.println("\n--- 6) Optional + Streams ---");

        // stream() en Optional (Java 9)
        List<String> ids = List.of("A001", "XXXX", "A003");
        List<String> foundOwners = ids.stream()
                .map(Main::findById)
                .flatMap(Optional::stream) // solo los presentes
                .map(Account::owner)
                .collect(Collectors.toList());
        System.out.println("Found owners: " + foundOwners); // [Alice, Charlie]


        // ─────────────────────────────────
        // RESUMEN
        // ─────────────────────────────────
        System.out.println("\n=== RESUMEN OPTIONAL ===");
        System.out.println("• Crear:    Optional.of(val), .empty(), .ofNullable(val)");
        System.out.println("• Extraer:  .orElse(), .orElseGet(), .orElseThrow()");
        System.out.println("• Mapear:   .map() para transformar, .flatMap() si retorna Optional");
        System.out.println("• Filtrar:  .filter() para condiciones");
        System.out.println("• Acción:   .ifPresent(), .ifPresentOrElse()");
        System.out.println("• NUNCA:    .get() directo, Optional como campo o parámetro");
    }
    // ─── Helpers ───

    static Optional<Account> findById(String id) {
        return ACCOUNTS.stream()
                .filter(a -> a.id().equals(id))
                .findFirst();
    }

    static Optional<BigDecimal> getVerifiedBalance(Account account) {
        // Simula una verificación que puede fallar
        if (account.balance().compareTo(BigDecimal.ZERO) > 0) {
            return Optional.of(account.balance());
        }
        return Optional.empty();
    }
}
