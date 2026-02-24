import java.util.*;
import java.util.stream.*;

/** SOLUCIONES — Stream API */
public class Solutions {

    record Transaction(String id, String type, double amount, String currency, boolean success) {}
    record Employee(String name, String dept, double salary, int yearsExp) {}

    static List<Transaction> transactions = List.of(
    

        new Transaction("T001", "CREDIT",  1500.00, "USD", true)
    ,
        new Transaction("T002", "DEBIT",    200.50, "USD", true),
        new Transaction("T003", "CREDIT",  3000.00, "EUR", true),
            500.00, "USD", false),
            800.00, "USD", true),
            200.00, "EUR", true),
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
            000, 1),
        new Employee("Henry",   "Finance",     125000, 12)
    ); 

    // ─── Sol E1 ───────
            // ──────────────────────────────────────────────────
    static double total
            cessfulUSD() {
        return transactions
            stream()
            .filter(Transaction::success)
            .filter(t -> "USD".equals(t.currency()))
            .mapToDouble(Transaction::amount)
            .sum();
    }
    // 1500 + 200.50 + 800 + 300 = 2800.50

    // ─── Sol E2 ──────────────────────────────────────────────────────────
    static List<String> failedTransactionIds() {
        return transactions.stream()
            .filter(t -> !t.success())
            .map(Transaction::id)
            .sorted()
            .collect(Collectors.toList());
    }
                        
    // ─── Sol E3 ──────────────────────────────────────────────────────────
    static Map<String, Double> averageAmountByType() {
        return transactions.stream()
            .collect(Collectors.groupingBy(Transaction::type,
                     Collectors.averagingDouble(Transaction::amount)));
    }

    // ─── Sol E4 ──────────────────────────────────────────────────────────
    static String highestSuccessfulTransactionId() {
        return transactions.stream()
            .filter(Transaction::success)
            .max(Comparator.comparingDouble(Transaction::amount))
    
            .map(Transaction::id)
            .orElse("N/A");
    }
                 retorna Optional<Transa
                Transaction::id) transforma Optional<Transaction> → Optional<String>
    // .orElse("N/A") desenvuelve con default

            
    // ─── Sol E5 ──────────────────────────────────────────────────────────
    static Map<String, List<String>> seniorsByDepartment() {
        return employees.stream()
            .filter(e -> e.yearsExp() >= 5)
            .collect(Collectors.groupingBy(Employee::dept,
                     Collectors.mapping(Employee::name, Collectors.toList())));
    }
    // groupingBy con downstream collector: mapping() proyecta Employee → name

    // ─── Sol E6 ──────────────────────────────────────────────────────────
    static Map<String, DoubleSummaryStatistics> salaryStatsByDept() {
        return employees.stream()
            .collect(Collectors.groupingBy(Employee::dept,
                     Collectors.summarizingDouble(Employee::salary)));
    }

    // ─── Sol E7 ──────────────────────────────────────────────────────────
    static boolean hasSuspiciousTransaction() {
        return transactions.stream().anyMatch(t -> t.amount() > 10_000);
    }
    // anyMatch es SHORT-CIRCUIT: para en el primer match. O(n) worst case pero mejor en práctica.

    // ─── Sol E8 ──────────────────────────────────────────────────────────
    static List<String> uniqueCurrencies() {
        return transactions.stream()
            .map(Transaction::currency)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.printf("E1: %.2f%n", totalSuccessfulUSD());        // 2800.50
        System.out.println("E2: " + failedTransactionIds());           // [T004, T007]
        System.out.println("E3: " + averageAmountByType());
        System.out.println("E4: " + highestSuccessfulTransactionId()); // T010
        System.out.println("E5: " + seniorsByDepartment());
        System.out.println("E6: " + salaryStatsByDept());
        System.out.println("E7: " + hasSuspiciousTransaction());       // false
        System.out.println("E8: " + uniqueCurrencies());               // [COP, EUR, USD]
    }
}
