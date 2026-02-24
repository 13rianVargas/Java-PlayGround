/**
 * ============================================================
 *  03 — OPTIONAL — Ejercicios
 * ============================================================
 */

import java.util.*;
import java.util.stream.*;
import java.math.BigDecimal;

public class Exercises {

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
     * EJERCICIO 1: Buscar cliente por ID.
     *
     * Retorna Optional<Customer>. No usar null.
     */
    public static Optional<Customer> findCustomer(String id) {
        // TODO: stream().filter().findFirst()
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 2: Obtener el email de un cliente dado su ID.
     *
     * Retorna Optional<String>. Si el cliente no existe → empty.
     * Usa findCustomer + map.
     */
    public static Optional<String> getEmail(String customerId) {
        // TODO: findCustomer(id).map(Customer::email)
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 3: Obtener la orden más grande de un cliente.
     * 
     * Dado un customerId, encuentra su orden con el total más alto.
     * Si el cliente no tiene órdenes → Optional.empty().
     */
    public static Optional<Order> largestOrder(String customerId) {
    
        // TODO: Filtra órdenes del cliente, usa max(Comparator)
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 4: Obtener el nombre del cliente con la orden más grande.
     *
     * Combina: buscar orden más grande global → obtener customerId →
     * buscar customer → obtener nombre.
     * Si cualquier paso falla → "Unknown".
     */
    public static String topSpenderName() {
        // TODO: Encadena flatMap/map para navegar la cadena
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 5: Filtrar una lista de IDs y retornar solo los
     * clientes que existen Y tienen al menos una orden.
     * 
     * Ejemplo: ids=["C1", "C2", "C99"] → ["Alice", "Bob"]
     * (C99 no existe, C1 y C2 tienen órdenes)
     */
    public static List<String> activeCustomerNames(List<String> ids) {
        // TODO: stream() + map(findCustomer) + flatMap(Optional::stream) + filter + map
        throw new UnsupportedOperationException("Implementa este método.");
    }

    // ─── Runner ───
    public static void main(String[] args) {
        System.out.println("=== OPTIONAL — Ejercicios ===\n");

        try {
    
            var r = findCustomer("C1");
            System.out.println("Ej1: " + r.map(Customer::name).orElse("?")
                + (r.isPresent() ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej1: ⬜ No implementado"); }

        try {
         var r = getEmail("C2");
            System.out.println("Ej2: " + r.orElse("?")
                + (r.isPresent() ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej2: ⬜ No implementado"); }
 
        try {
            var r = largestOrder("C1");
         System.out.println("Ej3: " + r.map(Order::total).orElse(BigDecimal.ZERO)
                + (r.map(o -> o.total().compareTo(new BigDecimal("250")) == 0).orElse(false) ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej3: ⬜ No implementado"); }

        try {
            String r = topSpenderName();
            System.out.println("Ej4: " + r + (r.equals("Charlie") ? " ✅" : " ❌ esperado Charlie"));
        } catch (Exception e) { System.out.println("Ej4: ⬜ No implementado"); }

        try {
            var r = activeCustomerNames(List.of("C1", "C2", "C99"));
            System.out.println("Ej5: " + r + (r.size() == 2 ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej5: ⬜ No implementado"); }
    }
}
