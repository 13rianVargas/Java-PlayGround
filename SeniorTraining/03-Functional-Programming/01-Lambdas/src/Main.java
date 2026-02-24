/**
 * ============================================================
 *  01 — LAMBDAS e INTERFACES FUNCIONALES
 * ============================================================
 *
 *  Una LAMBDA es una función anónima que implementa una interfaz
 *  funcional (interfaz con exactamente un método abstracto).
 *
 *  Java provee interfaces funcionales predefinidas en java.util.function:
 *  - Predicate<T>      → T → boolean       (filtrar)
 *  - Function<T,R>     → T → R             (transformar)
 *  - Consumer<T>       → T → void          (ejecutar acción)
 *  - Supplier<T>       → () → T            (crear/proveer)
 *  - BiFunction<T,U,R> → (T, U) → R       (dos inputs)
 *  - UnaryOperator<T>  → T → T             (transformar mismo tipo)
 *  - BinaryOperator<T> → (T, T) → T        (combinar dos del mismo tipo)
 *
 *  ============================================================
 */

import java.util.*;
import java.util.function.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {

    // Record para ejemplos financieros
    record Transaction(String id, String type, BigDecimal amount) {}

    public static void main(String[] args) {
        System.out.println("=== LAMBDAS e INTERFACES FUNCIONALES ===\n");

        // ─────────────────────────────────
        // 1) Sintaxis de Lambdas
        // ─────────────────────────────────
        System.out.println("--- 1) Sintaxis ---");

        // Forma completa
        Comparator<String> byLength1 = (String a, String b) -> {
            return Integer.compare(a.length(), b.length());
        };

        // Forma reducida (tipos inferidos, return implícito)
        Comparator<String> byLength2 = (a, b) -> Integer.compare(a.length(), b.length());

        // Un solo parámetro: sin paréntesis
        Function<String, Integer> len = s -> s.length();

        // Sin parámetros
        Supplier<List<String>> newList = () -> new ArrayList<>();

        System.out.println("Longitud de 'Java': " + len.apply("Java"));


        // ─────────────────────────────────
        // 2) Predicate<T> — filtrar
        // ─────────────────────────────────
        System.out.println("\n--- 2) Predicate ---");

        Predicate<BigDecimal> isPositive = amount -> amount.compareTo(BigDecimal.ZERO) > 0;
        Predicate<BigDecimal> isLarge = amount -> amount.compareTo(new BigDecimal("1000")) > 0;

        // Composición de Predicados
        Predicate<BigDecimal> isLargePositive = isPositive.and(isLarge);
        Predicate<BigDecimal> isSmallOrNegative = isLargePositive.negate();

        BigDecimal val = new BigDecimal("5000.00");
        System.out.println(val + " es positivo y grande: " + isLargePositive.test(val));   // true
        System.out.println(val + " es pequeño o negativo: " + isSmallOrNegative.test(val)); // false


        // ─────────────────────────────────
        // 3) Function<T,R> — transformar
        // ─────────────────────────────────
        System.out.println("\n--- 3) Function ---");

        Function<Transaction, String> getSummary =
                tx -> tx.type() + ": $" + tx.amount();

        Function<String, String> toUpper = String::toUpperCase;

        // Composición: andThen (ejecutar después) y compose (ejecutar antes)
        Function<Transaction, String> upperSummary = getSummary.andThen(toUpper);

        Transaction tx = new Transaction("T1", "deposit", new BigDecimal("2500.00"));
        System.out.println("Summary: " + upperSummary.apply(tx));
        // DEPOSIT: $2500.00


        // ─────────────────────────────────
        // 4) Consumer<T> — ejecutar acción
        // ─────────────────────────────────
        System.out.println("\n--- 4) Consumer ---");

        Consumer<Transaction> logTransaction =
                t -> System.out.println("[LOG] " + t.type() + " → " + t.amount());

        Consumer<Transaction> auditTransaction =
                t -> System.out.println("[AUDIT] " + t.id());

        // andThen encadena consumers
        Consumer<Transaction> fullProcess = logTransaction.andThen(auditTransaction);
        fullProcess.accept(tx);


        // ─────────────────────────────────
        // 5) BiFunction y BinaryOperator
        // ─────────────────────────────────
        System.out.println("\n--- 5) BiFunction / BinaryOperator ---");

        BiFunction<BigDecimal, BigDecimal, BigDecimal> calculateTax =
                (amount2, rate) -> amount2.multiply(rate).setScale(2, RoundingMode.HALF_UP);

        BinaryOperator<BigDecimal> sum = BigDecimal::add;

        BigDecimal tax = calculateTax.apply(new BigDecimal("1000"), new BigDecimal("0.19"));
        System.out.println("Tax on $1000 at 19%: $" + tax);      // $190.00

        BigDecimal total = sum.apply(new BigDecimal("1000"), tax);
        System.out.println("Total: $" + total);                    // $1190.00


        // ─────────────────────────────────
        // 6) Lambdas como parámetros
        // ─────────────────────────────────
        System.out.println("\n--- 6) Lambdas como parámetros ---");

        List<Transaction> transactions = List.of(
            new Transaction("T1", "deposit",    new BigDecimal("5000")),
            new Transaction("T2", "withdrawal", new BigDecimal("200")),
            new Transaction("T3", "deposit",    new BigDecimal("3000")),
            new Transaction("T4", "withdrawal", new BigDecimal("7500"))
        );

        // Filtrar con Predicate
        List<Transaction> deposits = filterTransactions(transactions,
                tx2 -> tx2.type().equals("deposit"));
        System.out.println("Depósitos: " + deposits.size()); // 2

        // Transformar con Function
        List<String> summaries = mapTransactions(transactions,
                tx2 -> tx2.id() + " [" + tx2.type() + "] $" + tx2.amount());
        summaries.forEach(System.out::println);


        // ─────────────────────────────────
        // RESUMEN
        // ─────────────────────────────────
        System.out.println("\n=== RESUMEN LAMBDAS ===");
        System.out.println("• Lambda = función anónima → implementa @FunctionalInterface");
        System.out.println("• Predicate: filtrar (test → boolean)");
        System.out.println("• Function: transformar (apply → R)");
        System.out.println("• Consumer: acción lateral (accept → void)");
        System.out.println("• Supplier: crear/proveer (get → T)");
        System.out.println("• Composición: .and(), .or(), .negate(), .andThen(), .compose()");
        System.out.println("• En pruebas: Lambdas + Streams → 90% del código funcional");
    }
    // Helper: filtrar con Predicate
    static <T> List<T> filterTransactions(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) result.add(item);
        }
        return result;
    }

    // Helper: transformar con Function
    static <T, R> List<R> mapTransactions(List<T> list, Function<T, R> mapper) {
        List<R> result = new ArrayList<>();
        for (T item : list) {    result.add(mapper.apply(item));
        }
        return result;
    }
}
