import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Stream API — Demostración de operaciones críticas para pruebas técnicas.
 * Este módulo es el más importante de programación funcional.
 */
public class Main {

    // ─── Datos de ejemplo: Transacciones financieras ─────────────────────
    record Transaction(String id, String type, double amount, String currency, boolean success) {}

    static List<Transaction> transactions = List.of(
        new Transaction("T001", "CREDIT",  1500.00, "USD", true),
        new Transaction("T002", "DEBIT",    200.50, "USD", true),
        new Transaction("T003", "CREDIT",  3000.00, "EUR", true),
        new Transaction("T004", "DEBIT",   5500.00, "USD", false),
        new Transaction("T005", "CREDIT",   800.00, "COP", true),
        new Transaction("T006", "TRANSFER",1200.00, "EUR", true),
        new Transaction("T007", "DEBIT",    450.00, "USD", true),
        new Transaction("T008", "CREDIT",  2000.00, "COP", true),
        new Transaction("T009", "DEBIT",    150.00, "EUR", false),
        new Transaction("T010", "CREDIT",  7500.00, "EUR", true)
    );

    public static void main(String[] args) {
        demo_filter_map();
        demo_collect();
        demo_groupingBy();
        demo_flatMap();
        demo_reduce();
        demo_statistics();
        demo_optional();
    }

    // ─────────────────────────────────────────────────────────────────────
    // DEMO 1: filter + map + sorted + collect
    // ─────────────────────────────────────────────────────────────────────
    static void demo_filter_map() {
        System.out.println("─── filter + map + sorted ───\n");

        // Montos de transacciones exitosas en USD mayores a $500
        List<Double> bigSuccessful = transactions.stream()
            .filter(Transaction::success)
            .filter(t -> "USD".equals(t.currency()))
            .filter(t -> t.amount() > 500)
            .map(Transaction::amount)
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toList());

        System.out.println("Montos USD exitosos > $500: " + bigSuccessful);

        // IDs de transacciones fallidas
        List<String> failedIds = transactions.stream()
            .filter(t -> !t.success())
            .map(Transaction::id)
            .collect(Collectors.toList());
        System.out.println("IDs fallidos: " + failedIds);
        System.out.println();
    }

    // ─────────────────────────────────────────────────────────────────────
    // DEMO 2: collect con Collectors avanzados
    // ─────────────────────────────────────────────────────────────────────
    static void demo_collect() {
        System.out.println("─── collect avanzado ───");

        // Unir IDs en String
        String allIds = transactions.stream()
            .map(Transaction::id)
            .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("Todos los IDs: " + allIds);

        // Sumar montos con Collectors.summingDouble
        double totalAmount = transactions.stream()
            .filter(Transaction::success)
            .collect(Collectors.summingDouble(Transaction::amount));
        System.out.printf("Total exitosas: $%.2f%n", totalAmount);

        // Contar por tipo
        Map<String, Long> countByType = transactions.stream()
            .collect(Collectors.groupingBy(Transaction::type, Collectors.counting()));
        System.out.println("Conteo por tipo: " + countByType);
        System.out.println();
    }

    // ─────────────────────────────────────────────────────────────────────
    // DEMO 3: groupingBy — CLAVE en pruebas de análisis de datos
    // ─────────────────────────────────────────────────────────────────────
    static void demo_groupingBy() {
        System.out.println("─── groupingBy ───");
 
        // acciones por moneda
        Map<String, List<Transaction>> byCurrency = transactions.stream()
            .collect(Collectors.groupingBy(Transaction::currency));
        byCurrency.forEach((currency, txns) ->
            System.out.println(currency + ": " + txns.size() + " transacciones"));

        // Monto promedio por tipo
        Map<String, Double> avgByType = transactions.stream()
            .collect(Collectors.groupingBy(Transaction::type,
                     Collectors.averagingDouble(Transaction::amount)));
        System.out.println("Promedio por tipo: " + avgByType);

        // Monto total por moneda (solo exitosas)
        Map<String, Double> totalByCurrency = transactions.stream()
            .filter(Transaction::success)
            .collect(Collectors.groupingBy(Transaction::currency,
                     Collectors.summingDouble(Transaction::amount)));
        System.out.println("Total por moneda: " + totalByCurrency);

        // partitioningBy: éxito vs fallo
        Map<Boolean, List<Transaction>> partition = transactions.stream()
            .collect(Collectors.partitioningBy(Transaction::success));
        System.out.println("Exitosas: "  + partition.get(true).size());
        System.out.println("Fallidas:  " + partition.get(false).size());
        System.out.println();
    }

    // ─────────────────────────────────────────────────────────────────────
    // DEMO 4: reduce — cuando necesitas un valor acumulado custom
    // ─────────────────────────────────────────────────────────────────────
    static void demo_reduce() {
        System.out.println("─── reduce ───");
        List<Integer> nums = List.of(1, 2, 3, 4, 5);

        // Suma
        int sum = nums.stream().reduce(0, Integer::sum);
        System.out.println("Suma: " + sum);

        // Producto
        int product = nums.stream().reduce(1, (a, b) -> a * b);
        System.out.println("Producto: " + product);

        // Máximo (alternativa a max())
        Optional<Integer> max = nums.stream().reduce(Integer::max);
        System.out.println("Máximo: " + max.orElse(0));

        // Concatenar strings
        List<String> words = List.of("Java", "is", "powerful");
        String sentence = words.stream().reduce("", (a, b) -> a.isEmpty() ? b : a + " " + b);
        System.out.println("Oración: " + sentence);
        System.out.println();
    }

    // ─────────────────────────────────────────────────────────────────────
    // DEMO 5: flatMap — aplanar colecciones de colecciones
    // ─────────────────────────────────────────────────────────────────────
    static void demo_flatMap() {
        System.out.println("─── flatMap ───");

        // Lista de listas → lista plana
        List<List<Integer>> nested = List.of(
            List.of(1, 2, 3),
            List.of(4, 5),
            List.of(6, 7, 8, 9)
        );
        List<Integer> flat = nested.stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        System.out.println("Aplanado: " + flat);

        // Todas las palabras únicas de múltiples frases
        List<String> sentences = List.of("java is great", "java is fast", "streams are great");
        Set<String> uniqueWords = sentences.stream()
            .flatMap(s -> Arrays.stream(s.split(" ")))
            .collect(Collectors.toSet());
        System.out.println("Palabras únicas: " + new TreeSet<>(uniqueWords));
        System.out.println();
    }

    // ─────────────────────────────────────────────────────────────────────
    // DEMO 6: Statistics — IntSummaryStatistics
    // ─────────────────────────────────────────────────────────────────────
    static void demo_statistics() {
        System.out.println("─── Statistics ───");
        DoubleSummaryStatistics stats = transactions.stream()
            .mapToDouble(Transaction::amount)
            .summaryStatistics();

        System.out.println("Mínimo:   $" + String.format("%.2f", stats.getMin()));
        System.out.println("Máximo:   $" + String.format("%.2f", stats.getMax()));
        System.out.println("Promedio: $" + String.format("%.2f", stats.getAverage()));
        System.out.println("Total:    $" + String.format("%.2f", stats.getSum()));
        System.out.println("Conteo:   " + stats.getCount());
        System.out.println();
    }

    // ─────────────────────────────────────────────────────────────────────
    // DEMO 7: Optional — manejo correcto de ausencia de valor
    // ─────────────────────────────────────────────────────────────────────
    static void demo_optional() {
        System.out.println("─── Optional ───");

        // findFirst con filter
        Optional<Transaction> bigEur = transactions.stream()
            .filter(t -> "EUR".equals(t.currency()))
            .filter(t -> t.amount() > 5000)
            .findFirst();

        // Manejo idiomático de Optional
        String result = bigEur
            .map(t -> t.id() + ": $" + t.amount())
            .orElse("No se encontró transacción EUR > $5000");
        System.out.println(result);

        // anyMatch / allMatch / noneMatch  (terminales booleanas — no retornan Optional)
        boolean hasFailures = transactions.stream().anyMatch(t -> !t.success());
        System.out.println("¿Hay fallos? " + hasFailures);

        boolean allPositive = transactions.stream().allMatch(t -> t.amount() > 0);
        System.out.println("¿Todos positivos? " + allPositive);
        System.out.println();
    }
}
