import java.util.*;
import java.util.stream.Collectors;

/**
 * PROBLEMA 2 — Payroll con Records y Streams
 */
public class Problem2 {

    public record Employee(String id, String name, String department,
            double baseSalary, double bonusPercent, boolean active) {
        /** Salario efectivo = baseSalary * (1 + bonusPercent / 100.0) */
        public double effectiveSalary() {
            return baseSalary * (1.0 + bonusPercent / 100.0);
        }
    }

    /**
     * 2a. Agrupa empleados ACTIVOS por departamento.
     */
    public static Map<String, List<Employee>> byDepartment(List<Employee> employees) {
        // TODO
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * 2b. Suma de salarios efectivos por departamento (solo activos).
     */
    public static Map<String, Double> totalPayrollByDept(List<Employee> employees) {
        // TODO
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * 2c. Los n activos con mayor salario efectivo, orden descendente.
     * Si hay menos de n activos, retorna todos.
     */
    public static List<Employee> topEarners(List<Employee> employees, int n) {
        // TODO
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * 2d. Departamento con mayor payroll total (activos).
     * Optional.empty() si no hay activos.
     */
    public static Optional<String> mostExpensiveDepartment(List<Employee> employees) {
        // TODO
        throw new UnsupportedOperationException("Implementa este método.");
    }
}
