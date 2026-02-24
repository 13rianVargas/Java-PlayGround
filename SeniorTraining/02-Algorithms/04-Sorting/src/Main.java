/**
 * ============================================================
 *  04 — SORTING + COMPARATORS
 * ============================================================
 *
 *  CONCEPTO: No se trata de implementar QuickSort — se trata de
 *  saber usar Arrays.sort(), Collections.sort() y Comparator
 *  para resolver problemas complejos en una línea.
 *
 *  CUÁNDO USARLO:
 *  - "Ordena por múltiples criterios" (salario DESC, luego nombre ASC)
 *  - Preprocessing antes de Binary Search o Two Pointers
 *  - "Top K", "mediana", "K-th más grande"
 *
 *  COMPLEJIDAD: O(n log n) para sort
 *
 *  ============================================================
 */

import java.util.*;
import java.util.stream.*;

public class Main {

    // Records para ejemplos (Java 17)
    record Transaction(String id, String category, double amount, int priority) {}
    record Employee(String name, String department, double salary) {}

    public static void main(String[] args) {
        System.out.println("=== SORTING + COMPARATORS ===\n");

        // ───────────────────────────────────
        // 1) Arrays.sort básico
        // ───────────────────────────────────
        System.out.println("--- 1) Arrays.sort básico ---");
        int[] numbers = {5, 2, 8, 1, 9, 3};
        Arrays.sort(numbers);  // In-place, Dual-Pivot Quicksort O(n log n)
        System.out.println("Sorted primitives: " + Arrays.toString(numbers));

        // Para ordenar solo un rango:
        int[] partial = {5, 2, 8, 1, 9, 3};
        Arrays.sort(partial, 1, 4); // ordena índices [1,4)
        System.out.println("Partial sort [1,4): " + Arrays.toString(partial));

        // 2) Comparator básico y encadenado
        // ─────────────────────────────────
        System.out.println("\n--- 2) Comparator encadenado ---");

        List<Employee> employees = List.of(
            new Employee("Ana", "TECH", 85000),
            new Employee("Carlos", "TECH", 92000),
            new Employee("Diana", "HR", 78000),
            new Employee("Bruno", "HR", 78000),
            new Employee("Elena", "TECH", 85000)
        );

        // Ordenar por salario DESC, luego por nombre ASC
        List<Employee> sorted = employees.stream()
            .sorted(Comparator.comparingDouble(Employee::salary).reversed()
                    .thenComparing(Employee::name))
            .collect(Collectors.toList());

        System.out.println("Por salario DESC, nombre ASC:");
        sorted.forEach(e -> System.out.printf("  %-10s %-5s $%.0f%n",
                e.name(), e.department(), e.salary()));


        // ─────────────────────────────────
        // 3) Comparator.comparing con extractores
        // ─────────────────────────────────
        System.out.println("\n--- 3) Comparator.comparing ---");

        List<Transaction> txns = List.of(
            new Transaction("T1", "FOOD", 50.0, 3),
            new Transaction("T2", "TECH", 200.0, 1),
            new Transaction("T3", "FOOD", 30.0, 1),
            new Transaction("T4", "TECH", 200.0, 2)
        );

        // Ordenar por categoría ASC, amount DESC, priority ASC
        List<Transaction> sortedTxns = txns.stream()
            .sorted(Comparator.comparing(Transaction::category)
                    .thenComparing(Comparator.comparingDouble(Transaction::amount).reversed())
                    .thenComparingInt(Transaction::priority))
            .collect(Collectors.toList());

        System.out.println("Multi-criterio (cat ASC, amount DESC, priority ASC):");
        sortedTxns.forEach(t -> System.out.printf("  %s %-5s $%.0f p%d%n",
                t.id(), t.category(), t.amount(), t.priority()));


        // ─────────────────────────────────
        // 4) K-th más grande con PriorityQueue
        // ─────────────────────────────────
        System.out.println("\n--- 4) K-th elemento más grande ---");

        int[] data = {3, 2, 1, 5, 6, 4};
        int kth = 2;

        // Min-heap de tamaño K
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int val : data) {
            minHeap.offer(val);
            if (minHeap.size() > kth) minHeap.poll();
        }

        System.out.println("Array: " + Arrays.toString(data));
        System.out.println("K=" + kth + " más grande: " + minHeap.peek()); // 5


        // ─────────────────────────────────
        // 5) Custom sort: orden por frecuencia
        // ─────────────────────────────────
        System.out.println("\n--- 5) Ordenar por frecuencia ---");

        int[] freq = {1, 1, 2, 2, 2, 3};

        // Contar frecuencias
        Map<Integer, Long> freqMap = Arrays.stream(freq)
                .boxed()
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()));

        // Ordenar: más frecuente primero, desempate por valor ASC
        List<Integer> byFreq = Arrays.stream(freq)
                .boxed()
                .sorted(Comparator.<Integer, Long>comparing(freqMap::get).reversed()
                        .thenComparing(Comparator.naturalOrder()))
                .collect(Collectors.toList());

        System.out.println("Original: " + Arrays.toString(freq));
        System.out.println("Por frecuencia DESC: " + byFreq);


        // ─────────────────────────────────
        // RESUMEN
        // ─────────────────────────────────
        System.out.println("\n=== RESUMEN ===");
        System.out.println("• Arrays.sort()       → primitivos, Dual-Pivot Quicksort O(n log n)");
        System.out.println("• Collections.sort()   → objetos, TimSort O(n log n), estable");
        System.out.println("• Comparator.comparing → extractor de clave, encadenable con .thenComparing");
        System.out.println("• .reversed()          → invertir orden de cualquier Comparator");
        System.out.println("• PriorityQueue        → Top-K en O(n log K)");
        System.out.println("• Señales: 'ordena por', 'top K', 'K-th', 'múltiples criterios'");
    }
}
