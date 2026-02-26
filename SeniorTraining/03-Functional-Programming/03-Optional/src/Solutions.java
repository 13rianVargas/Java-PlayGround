/**
 * ============================================================
 *  03 — OPTIONAL — Soluciones Anotadas
 * ============================================================
 */

import java.util.*;
import java.util.stream.*;
import java.math.BigDecimal;

public class Solutions {

    record Customer(String id, String name, String email) {}
    record Order(String orderId, String customerId, BigDecimal total) {}

    static final List<Customer> CUSTOMERS = List.of(
        new Customer("C1", "Alice",   "alice@email.com"),
        new Customer("C2", "Bob",     "bob@email.com"),
        new Customer("C3", "Charlie", "charlie@email.com")
    );

    static final List<Order> ORDERS = List.of(
        new Order("O1", "C1", new BigDecimal("150.00")),
        new Order("O2", "C1", new BigDecimal("250.00")),
        new Order("O3", "C2", new BigDecimal("80.00")),
        new Order("O4", "C3", new BigDecimal("500.00"))
    );

    /**
     * EJERCICIO 1: Buscar por ID.
     * Patrón estándar: stream + filter + findFirst → Optional
     */
    public static Optional<Customer> findCustomer(String id) {
        return CUSTOMERS.stream()
                .filter(c -> c.id().equals(id))
                .findFirst();
    }

    /**
     * EJERCICIO 2: Encadenar con map.
     * findCustomer ya retorna Optional → encadenar con .map()
     */
    public static Optional<String> getEmail(String customerId) {
        return findCustomer(customerId)
                .map(Customer::email);
    }

    
    /**
     * EJERCICIO 3: Max con Comparator dentro de Optional.
     */
    public static Optional<Order> largestOrder(String customerId) {
        return ORDERS.stream()
                .filter(o -> o.customerId().equals(customerId))
                .max(Comparator.comparing(Order::total));
    }
                
    /**
     * EJERCICIO 4: Cadena de flatMap/map.
     *
     * La clave es flatMap cuando la función ya retorna Optional.
     * map cuando retorna un valor directo.
     */
    public static String topSpenderName() {
        return ORDERS.stream()
                .max(Comparator.comparing(Order::total))      // Optional<Order>
                .flatMap(o -> findCustomer(o.customerId()))   // Optional<Customer>
                .map(Customer::name)                           // Optional<String>
                .orElse("Unknown");                            // String
    }

    /**
     * EJERCICIO 5: Combinar Optional con Stream.
     *
     * flatMap(Optional::stream) convierte Optional<T> → Stream<T>
     * (0 o 1 elementos). Los empty se eliminan automáticamente.
     */
    public static List<String> activeCustomerNames(List<String> ids) {
        return ids.stream()
                .map(Solutions::findCustomer)               // Stream<Optional<Customer>>
                .flatMap(Optional::stream)                  // Stream<Customer> (solo existentes)
                .filter(c -> ORDERS.stream()                // filtrar los que tienen órdenes
                        .anyMatch(o -> o.customerId().equals(c.id())))
                .map(Customer::name)
                .collect(Collectors.toList());
    }


    // ─── Verificación ───
    public static void main(String[] args) {
        System.out.println("=== OPTIONAL — Soluciones ===\n");

        System.out.println("Ej1: " + findCustomer("C1").map(Customer::name).orElse("?")); // Alice
        System.out.println("Ej2: " + getEmail("C2").orElse("?"));                         // bob@email.com
        System.out.println("Ej3: " + largestOrder("C1").map(Order::total).orElse(BigDecimal.ZERO)); // 250.00
        System.out.println("Ej4: " + topSpenderName());                                   // Charlie
        System.out.println("Ej5: " + activeCustomerNames(List.of("C1", "C2", "C99")));    // [Alice, Bob]
    }
}
