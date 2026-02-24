/**
 * ============================================================
 *  04 — METHOD REFERENCES — Ejercicios
 * ============================================================
 *
 *  En cada ejercicio, reescribe la versión lambda usando
 *  method references donde sea posible.
 * ============================================================
 */

import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.math.BigDecimal;

public class Exercises {

    record Employee(String name, String department, double salary) {
        static Employee fromCsv(String csv) {
            String[] p = csv.split(",");
            return new Employee(p[0].trim(), p[1].trim(), Double.parseDouble(p[2].trim()));
        }

        String toDisplayString() {
            return name + " (" + department + ") $" + String.format("%.0f", salary);
        }
    }

    /**
     * EJERCICIO 1: Parsear líneas CSV a Employee usando method reference.
     *
     * Lambda actual: csv -> Employee.fromCsv(csv)
     * Reescríbelo con method reference.
     */
    public static List<Employee> parseEmployees(List<String> csvLines) {
        // TODO: Usa Employee::fromCsv en lugar de la lambda
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 2: Obtener solo los nombres de empleados, en mayúsculas.
     *
     * Lambda: e -> e.name() seguido de s -> s.toUpperCase()
     * Ambos pueden ser method references.
     */
    public static List<String> upperCaseNames(List<Employee> employees) {
        // TODO: Usa Employee::name y String::toUpperCase
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 3: Imprimir cada empleado usando su toDisplayString.
     *
     * Lambda: e -> System.out.println(e.toDisplayString())
     * Hint: no es un solo method reference — necesitas .map() + forEach
     */
    public static void printAll(List<Employee> employees) {
        // TODO: .map(Employee::toDisplayString).forEach(System.out::println)
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 4: Crear un Comparator usando method reference.
     *
     * Ordena por nombre ASC usando Comparator.comparing con method reference.
     * Retorna la lista ordenada.
     */
    public static List<Employee> sortByName(List<Employee> employees) {
        // TODO: Comparator.comparing(Employee::name)
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 5: Usar constructor reference para crear colección.
     *
     * Recolecta los departamentos únicos en un TreeSet (orden natural).
     * Usa TreeSet::new como constructor ref en Collectors.toCollection().
     */
    public static TreeSet<String> uniqueDepartments(List<Employee> employees) {
        // TODO: .map(...).collect(Collectors.toCollection(TreeSet::new))
        throw new UnsupportedOperationException("Implementa este método.");
    }


    // ─── Runner ───
    public static void main(String[] args) {
        System.out.println("=== METHOD REFERENCES — Ejercicios ===\n");

        var csvData = List.of("Alice,TECH,85000", "Bob,HR,72000", "Charlie,TECH,92000");
        var employees = List.of(
            new Employee("Alice", "TECH", 85000),
            new Employee("Bob", "HR", 72000),
            new Employee("Charlie", "TECH", 92000)
        );

        try {
            var r = parseEmployees(csvData);
            System.out.println("Ej1: " + r.size() + (r.size() == 3 ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej1: ⬜ No implementado"); }

        try {
            var r = upperCaseNames(employees);
            System.out.println("Ej2: " + r + (r.get(0).equals("ALICE") ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej2: ⬜ No implementado"); }

        try {
            System.out.print("Ej3: ");
            printAll(employees);
            System.out.println(" ✅");
        } catch (Exception e) { System.out.println("Ej3: ⬜ No implementado"); }

        try {
            var r = sortByName(employees);
            System.out.println("Ej4: " + r.get(0).name()
                + (r.get(0).name().equals("Alice") ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej4: ⬜ No implementado"); }

        try {
            var r = uniqueDepartments(employees);
            System.out.println("Ej5: " + r + (r instanceof TreeSet && r.size() == 2 ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej5: ⬜ No implementado"); }
    }
}
