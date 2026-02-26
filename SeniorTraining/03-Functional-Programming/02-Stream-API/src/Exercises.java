import java.util.*;
import java.util.stream.*;

/**
 * EJERCICIOS — Stream API
 * Todos los ejercicios usan la misma clase Transaction del Main.java.
 * Copia las declaraciones necesarias o exécuta desde el mismo directorio.
 */
public class Exercises {

    // ─── Modelo de datos (mismo que Main.java) ────────────────────────────
    record Transaction(String id, String type, double amount, String currency, boolean success) {}
    record Employee(String name, String dept, double salary, int yearsExp) {}

    
    static List<Transaction> transactions = List.of(
        new Transaction("T001", "CREDIT",  1500.00, "USD", true),
        new Transaction("T002", "DEBIT",    200.50, "USD", true),
        new Transaction("T003", "CREDIT",  3000.00, "EUR", true),
        new Transaction("T004", "DEBIT",    500.00, "USD", false),
        new Transaction("T005", "CREDIT",   800.00, "USD", true),
        new Transaction("T006", "DEBIT",    200.00, "EUR", true),
        new Transaction("T007", "DEBIT",   9000.00, "USD", false),
        new Transaction("T008", "CREDIT",   450.00, "COP", true),
        new Transaction("T009", "TRANSFER", 300.00, "USD", true),
        new Transaction("T010", "CREDIT",  7500.00, "EUR", true)
    );

    static List<Employee> employees = List.of(
        new Employee("Alice",   "Engineering", 95000, 5),
        new Employee("Bob",     "Engineering", 82000, 3),
        new Employee("Charlie", "Finance",     110000, 8),
        new Employee("Diana",   "Finance",     90000, 4),
        new Employee("Eve",     "Marketing",   75000, 2),
        new Employee("Frank",   "Engineering", 105000, 7),
        new Employee("Grace",   "Marketing",   68000, 1),
        new Employee("Henry",   "Finance",     125000, 12)
    );


    // ─────────────────────────────────────────────────────────────────────
    // E1 — Suma de transacciones exitosas en USD
    //
    // Retorna la suma de las transacciones que son:
    //   - exitosas (success = true)
    //   - en USD
    //
    // Resultado esperado: 2800.50
    //
    // 💡 filter + mapToDouble + sum
    // ─────────────────────────────────────────────────────────────────────
    static double totalSuccessfulUSD() {
        // TODO 
        return 0.0;
    }
 
    // ─────────────────────────────────────────────────────────────────────
    // E2 — IDs de transacciones fallidas, ordenados
    //
    // Retorna la lista de IDs de transacciones no exitosas, en orden alfabético.
    //
    // Resultado esperado: ["T004", "T007"]
    //
    // 💡 filter + map + sorted + collect
    // ─────────────────────────────────────────────────────────────────────
    static List<String> failedTransactionIds() {
        // TODO
        return Collections.emptyList();
    }

    // ─────────────────────────────────────────────────────────────────────
    // E3 — Monto promedio por tipo de transacción
    //
    // Retorna un Map<String, Double> donde la clave es el tipo de transacción
    // y el valor es el monto promedio de ese tipo.
    //
    // Resultado esperado (aprox.):
    //   CREDIT: 2650.0, DEBIT: 4900.16..., TRANSFER: 750.0
    //
    // 💡 groupingBy + averagingDouble
    // ─────────────────────────────────────────────────────────────────────
    static Map<String, Double> averageAmountByType() {
        // TODO
        return Collections.emptyMap();
    }

    // ─────────────────────────────────────────────────────────────────────
    // E4 — La transacción exitosa de mayor monto
    //
    // Retorna el ID de la transacción exitosa con mayor monto.
    // Si no hay transacciones exitosas, retorna "N/A".
    //
    // Resultado esperado: "T010" (EUR, 7500.00)
    //
    // 💡 filter + max(Comparator) + map + orElse
    // ─────────────────────────────────────────────────────────────────────
    static String highestSuccessfulTransactionId() {
        // TODO
        return "N/A";
    }

    // ─────────────────────────────────────────────────────────────────────
    // E5 — Empleados Senior agrupados por departamento
    //
    // Retorna un Map<String, List<String>> con el nombre de los empleados
    // que tienen >= 5 años de experiencia, agrupados por departamento.
    //
    // Resultado esperado:
    //   Engineering: [Alice, Frank]
    //   Finance: [Charlie, Henry]
    //
    // 💡 filter + collect(groupingBy + mapping)
    // ─────────────────────────────────────────────────────────────────────
    static Map<String, List<String>> seniorsByDepartment() {
        // TODO
        return Collections.emptyMap();
    }

    // ─────────────────────────────────────────────────────────────────────
    // E6 — Reporte de salarios por departamento
    //
    // Retorna un Map<String, DoubleSummaryStatistics> donde cada clave es
    // un departamento y el valor contiene min/max/avg/sum/count de salarios.
    //
    // 💡 groupingBy + summarizingDouble
    // ─────────────────────────────────────────────────────────────────────
    static Map<String, DoubleSummaryStatistics> salaryStatsByDept() {
        // TODO
        return Collections.emptyMap();
    }

    // ─────────────────────────────────────────────────────────────────────
    // E7 — ¿Hay alguna transacción de más de $10,000? (Fraude detector)
    //
    // Retorna true si existe al menos una transacción con amount > 10000.
    // Resultado esperado: false
    //
    // 💡 anyMatch — es EFICIENTE: cortocircuita al primer match
    // ─────────────────────────────────────────────────────────────────────
    static boolean hasSuspiciousTransaction() {
        // TODO
        return false;
    }

    // ─────────────────────────────────────────────────────────────────────
    // E8 — Monedas distintas en uso (sin duplicados, ordenadas)
    //
    // Retorna una lista de las monedas únicas que aparecen en las
    // transacciones, en orden alfabético.
    //
    // Resultado esperado: ["COP", "EUR", "USD"]
    //
    // 💡 map + distinct + sorted + collect
    // ─────────────────────────────────────────────────────────────────────
    static List<String> uniqueCurrencies() {
        // TODO
        return Collections.emptyList();
    }

    // ─────────────────────────────────────────────────────────────────────
    // MAIN
    // ─────────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("E1 totalSuccessfulUSD:          " + totalSuccessfulUSD());           // 2800.50
        System.out.println("E2 failedTransactionIds:        " + failedTransactionIds());         // [T004, T007]
        System.out.println("E3 averageAmountByType:         " + averageAmountByType());
        System.out.println("E4 highestSuccessful:           " + highestSuccessfulTransactionId()); // T010
        System.out.println("E5 seniorsByDept:               " + seniorsByDepartment());
        System.out.println("E6 salaryStats (Engineering):");
        salaryStatsByDept().forEach((dept, stats) ->
            System.out.printf("   %s → avg=$%.0f, min=$%.0f, max=$%.0f%n",
                dept, stats.getAverage(), stats.getMin(), stats.getMax()));
        System.out.println("E7 hasSuspicious:               " + hasSuspiciousTransaction());     // false
        System.out.println("E8 uniqueCurrencies:            " + uniqueCurrencies());             // [COP, EUR, USD]
    }
}
