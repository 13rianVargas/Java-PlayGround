import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import Problem2.Employee;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Problema 2 — Payroll con Streams")
class Problem2Test {

    private List<Employee> employees;

    @BeforeEach
    void setUp() {
        employees = List.of(
                new Employee("E1", "Alice", "TECH", 5000, 20, true), // 6000
                new Employee("E2", "Bob", "TECH", 4000, 10, true), // 4400
                new Employee("E3", "Carlos", "SALES", 3000, 30, true), // 3900
                new Employee("E4", "Diana", "SALES", 6000, 0, false),
                new Employee("E5", "Eve", "HR", 3500, 10, true), // 3850
                new Employee("E6", "Frank", "TECH", 7000, 15, false),
                new Employee("E7", "Grace", "HR", 4000, 25, true), // 5000
                new Employee("E8", "Hector", "SALES", 2500, 10, true) // 2750
        );
    }

    // ── 2a. byDepartment ──────────────────────────────────────────────────────

    @Test
    @DisplayName("T01 — byDepartment incluye solo activos")
    void t01_byDepartment_onlyActive() {
        Map<String, List<Employee>> result = Problem2.byDepartment(employees);
        // TECH activos: E1, E2 (E6 inactivo)
        assertEquals(2, result.get("TECH").size());
        // SALES activos: E3, E8 (E4 inactiva)
        assertEquals(2, result.get("SALES").size());
        // HR activos: E5, E7
        assertEquals(2, result.get("HR").size());
    }

    @Test
    @DisplayName("T02 — byDepartment no incluye inactivos en ningún grupo")
    void t02_byDepartment_noInactive() {
        Map<String, List<Employee>> result = Problem2.byDepartment(employees);
        result.values().forEach(list -> list.forEach(e -> assertTrue(e.active(), "No debe incluir inactivos")));
    }

    // ── 2b. totalPayrollByDept ────────────────────────────────────────────────

    @Test
    @DisplayName("T03 — totalPayrollByDept calcula correctamente TECH")
    void t03_payroll_tech() {
        Map<String, Double> result = Problem2.totalPayrollByDept(employees);
        // TECH activos: E1=6000 + E2=4400 = 10400
        assertEquals(10400.0, result.get("TECH"), 0.001);
    }

    @Test
    @DisplayName("T04 — totalPayrollByDept no incluye inactivos")
    void t04_payroll_excludesInactive() {
        Map<String, Double> result = Problem2.totalPayrollByDept(employees);
        // SALES activos: E3=3900 + E8=2750 = 6650 (E4 inactiva)
        assertEquals(6650.0, result.get("SALES"), 0.001);
    }

    // ── 2c. topEarners ────────────────────────────────────────────────────────

    @Test
    @DisplayName("T05 — topEarners retorna los n con mayor salario efectivo")
    void t05_topEarners_correct() {
        List<Employee> result = Problem2.topEarners(employees, 3);
        assertEquals(3, result.size());
        // Salarios efectivos activos: E1=6000, E2=4400, E3=3900, E5=3850, E7=5000,
        // E8=2750
        // Top 3: E1(6000), E7(5000), E2(4400)
        assertEquals("E1", result.get(0).id());
        assertEquals("E7", result.get(1).id());
        assertEquals("E2", result.get(2).id());
    }

    @Test
    @DisplayName("T06 — topEarners con n > activos retorna todos los activos")
    void t06_topEarners_lessThanN() {
        List<Employee> result = Problem2.topEarners(employees, 100);
        long activeCount = employees.stream().filter(Employee::active).count();
        assertEquals(activeCount, result.size());
    }

    @Test
    @DisplayName("T07 — topEarners solo incluye activos")
    void t07_topEarners_onlyActive() {
        List<Employee> result = Problem2.topEarners(employees, 99);
        result.forEach(e -> assertTrue(e.active()));
    }

    // ── 2d. mostExpensiveDepartment ───────────────────────────────────────────

    @Test
    @DisplayName("T08 — mostExpensiveDepartment retorna el más caro")
    void t08_mostExpensive() {
        Optional<String> result = Problem2.mostExpensiveDepartment(employees);
        assertTrue(result.isPresent());
        // TECH=10400, SALES=6650, HR=8850
        assertEquals("TECH", result.get());
    }

    @Test
    @DisplayName("T09 — mostExpensiveDepartment con lista vacía → empty")
    void t09_mostExpensive_empty() {
        assertTrue(Problem2.mostExpensiveDepartment(List.of()).isEmpty());
    }

    @Test
    @DisplayName("T10 — mostExpensiveDepartment con todos inactivos → empty")
    void t10_mostExpensive_allInactive() {
        List<Employee> allInactive = List.of(
                new Employee("X1", "Z", "DEPT", 9999, 100, false));
        assertTrue(Problem2.mostExpensiveDepartment(allInactive).isEmpty());
    }
}
