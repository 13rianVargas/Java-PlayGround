/**
 * ============================================================
 *  04 — METHOD REFERENCES (Referencias a Métodos)
 * ============================================================
 *
 *  Un method reference es un atajo de lambda cuando la lambda
 *  solamente llama a un método existente.
 *
 *  4 TIPOS:
 *  1. Referencia estática:    ClassName::staticMethod
 *  2. Referencia a instancia: instance::instanceMethod
 *  3. Referencia a tipo:      ClassName::instanceMethod
 *  4. Referencia a constructor: ClassName::new
 *
 *  ============================================================
 */


import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {

    record Transaction(String id, String type, BigDecimal amount) {
        String summary() {
            return id + " [" + type + "] $" + amount;
        }

        static Transaction parse(String csv) {
            String[] parts = csv.split(",");
            return new Transaction(parts[0].trim(), parts[1].trim(),
                    new BigDecimal(parts[2].trim()));
        }
    }

    public static void main(String[] args) {
        System.out.println("=== METHOD REFERENCES ===\n");

        List<Transaction> txns = List.of(
            new Transaction("T1", "deposit",    new BigDecimal("5000")),
            new Transaction("T2", "withdrawal", new BigDecimal("200")),
            new Transaction("T3", "deposit",    new BigDecimal("3000"))
        );

        // ─────────────────────────────────
        // 1) Referencia estática — ClassName::staticMethod
        // ─────────────────────────────────
        System.out.println("--- 1) Static method reference ---");

        // Lambda:   s -> Integer.parseInt(s)
        // Ref:      Integer::parseInt
        List<String> numStrings = List.of("10", "20", "30");
        List<Integer> nums = numStrings.stream()
                .map(Integer::parseInt)    // Integer::parseInt en lugar de s -> Integer.parseInt(s)
                .collect(Collectors.toList());
        System.out.println("Parsed: " + nums);

        // Transaction::parse es un método estático
        List<String> csvLines = List.of("T10,deposit,1000", "T11,withdrawal,500");
        List<Transaction> parsed = csvLines.stream()
                .map(Transaction::parse)
                .collect(Collectors.toList());
        System.out.println("Parsed from CSV: " + parsed.stream()
                .map(Transaction::summary).collect(Collectors.joining("; ")));


        // ─────────────────────────────────
        // 2) Referencia a instancia — instance::method
        // ─────────────────────────────────

        :   txn -> txn.summary()
        // En un caso donde ya tienes la instancia:
        TransactionFormatter formatter = new TransactionFormatter("USD");

        List<String> formatted = txns.stream()
                .map(formatter::format)    // la instancia 'formatter' ya existe
                .collect(Collectors.toList());
        formatted.forEach(System.out::println);

        // System.out::println es instance method ref de System.out
        System.out.println("\nUsando System.out::println:");
        txns.stream().map(Transaction::summary).forEach(System.out::println);


            
        

        // ─────────────────────────────────────────────────────────────────────
        // 3) Referencia a tipo — ClassName::instanceMethod
        // ─────────────────────────────────────────────────────────────────────
        System.out.println("\n--- 3) Type method reference ---");

        // Lambda:   (s) -> s.toUpperCase()
        // Ref:      String::toUpperCase
        List<String> types = txns.stream()
                .map(Transaction::type)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("Types uppercase: " + types);

        // Transaction::summary — llama summary() en cada Transaction
        List<String> summaries = txns.stream()
                .map(Transaction::summary)
                .collect(Collectors.toList());
        System.out.println("Summaries: " + summaries);


        // ─────────────────────────────────
        // 4) Referencia a constructor — ClassName::new
        // ─────────────────────────────────
        System.out.println("\n--- 4) Constructor reference ---");

        // Lambda:   () -> new ArrayList<>()
        // Ref:      ArrayList::new
        Supplier<List<String>> listFactory = ArrayList::new;
        List<String> newList = listFactory.get();
        System.out.println("New list type: " + newList.getClass().getSimpleName());

        // También funciona con Collectors.toCollection
        TreeSet<String> sortedTypes = txns.stream()
                .map(Transaction::type)
                .collect(Collectors.toCollection(TreeSet::new)); // TreeSet::new
        System.out.println("Sorted unique types: " + sortedTypes);


        // ─────────────────────────────────
        // 5) Comparando Lambda vs Method Reference
        // ─────────────────────────────────
        System.out.println("\n--- 5) Lambda vs Method Reference ---");

        // Lambda                              Method Reference
        // s -> s.length()                     String::length
        // s -> System.out.println(s)          System.out::println
        // s -> Integer.parseInt(s)            Integer::parseInt
        // () -> new ArrayList<>()             ArrayList::new
        // s -> s.toUpperCase()                String::toUpperCase
        // (a, b) -> a.compareTo(b)            Comparable::compareTo

        BigDecimal total = txns.stream()
                .map(Transaction::amount)      // Type::instanceMethod
                .reduce(BigDecimal.ZERO, BigDecimal::add); // instanceRef en reduce
        System.out.println("Total: $" + total);


        // ─────────────────────────────────
        // RESUMEN
        // ─────────────────────────────────
        System.out.println("\n=== RESUMEN ===");
        System.out.println("• Static:      Integer::parseInt     ← ClassName::staticMethod");
        System.out.println("• Instance:    formatter::format     ← instance::method");
        System.out.println("• Type:        String::toUpperCase   ← ClassName::instanceMethod");
        System.out.println("• Constructor: ArrayList::new        ← ClassName::new");
        System.out.println("• Regla: si la lambda SOLO llama a un método → usar method reference");
    }

    static class TransactionFormatter {
        private final String currency;
        TransactionFormatter(String currency) { this.currency = currency; }
        String format(Transaction tx) {
            return String.format("[%s] %s: %s %s",
                    tx.id(), tx.type(), currency, tx.amount());
        }
    }
}
