/**
 * ============================================================
 *  04 — METHOD REFERENCES — Soluciones Anotadas
 * ============================================================
 */

import java.util.*;
import java.util.stream.*;

public class Solutions {

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
     * EJERCICIO 1: Static method reference.
     * Employee::fromCsv equivale a csv -> Employee.fromCsv(csv)
     */
    public static List<Employee> parseEmployees(List<String> csvLines) {
        return csvLines.stream()
                .map(Employee::fromCsv)           // static method reference
                .collect(Collectors.toList());
    }

    /** 
     * EJERCICIO 2: Type method references encadenadas.
     * Employee::name y String::toUpperCase
     */
    public static List<String> upperCaseNames(List<Employee> employees) {
        return employees.stream()
                .map(Employee::name)              // type method ref
                .map(String::toUpperCase)         // type method ref
                .collect(Collectors.toList());
    }

    /**
     * EJERCICIO 3: map + forEach con method references.
     *
     * No se puede hacer en un solo method reference porque
     * necesitamos transformar Y luego imprimir.
     */
    public static void printAll(List<Employee> employees) {
        employees.stream()
                .map(Employee::toDisplayString)    // type method ref
                .forEach(System.out::println);     // instance method ref
    }


    /**
     * EJERCICIO 4: Comparator.comparing con method ref.
     */
    public static List<Employee> sortByName(List<Employee> employees) {
        return employees.stream()
                .sorted(Comparator.comparing(Employee::name))
                .collect(Collectors.toList());
    }

    /**
     * EJERCICIO 5: Constructor reference en toCollection.
     * TreeSet::new equivale a () -> new TreeSet<>()
     */
    public static TreeSet<String> uniqueDepartments(List<Employee> employees) {
        return employees.stream()
                .map(Employee::department)
                .collect(Collectors.toCollection(TreeSet::new)); // constructor ref
    }


    // ─── Verificación ───
    public static void main(String[] args) {
        System.out.println("=== METHOD REFERENCES — Soluciones ===\n");

        var csvData = List.of("Alice,TECH,85000", "Bob,HR,72000", "Charlie,TECH,92000");
        var employees = parseEmployees(csvData);

        System.out.println("Ej1 parsed: " + employees.size());                  // 3
        System.out.println("Ej2 names: " + upperCaseNames(employees));           // [ALICE, BOB, CHARLIE]
        System.out.print("Ej3: "); printAll(employees);                          // 3 lines
        System.out.println("Ej4 sorted: " + sortByName(employees).get(0).name()); // Alice
        System.out.println("Ej5 depts: " + uniqueDepartments(employees));        // [HR, TECH]
    }
}
