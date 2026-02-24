import java.util.List;
import java.util.Optional;

/**
 * ============================================================
 * MÓDULO 07 — Java 17 Features: Guía completa
 * ============================================================
 *
 * Las 5 características más evaluadas en pruebas Senior con Java 17.
 */
public class Main {

    // ══════════════════════════════════════════════════════════════════════════
    // 1. RECORDS (Java 16+ stable)
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Un record es una clase de datos inmutable.
     * El compilador genera automáticamente:
     * - Constructor canónico
     * - Getters (sin get- prefix: id(), amount(), etc.)
     * - equals(), hashCode(), toString()
     */
    record Transaction(String id, double amount, String currency) {
        // Compact constructor con validación
        Transaction {
            if (id == null || id.isBlank()) throw new IllegalArgumentException("ID requerido");
            if (amount < 0) throw new IllegalArgumentException("Monto no puede ser negativo");
            currency = currency.toUpperCase();
        }

        // Métodos de instancia normales
        boolean isHighValue() {
            return amount > 10_000;
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // 2. SEALED CLASSES (Java 17 stable)
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Sealed = la jerarquía está cerrada. Solo las clases declaradas en
     * 'permits' pueden extender/implementar.
     *
     * Beneficio: el compilador sabe exactamente qué subtipos existen
     * → switch exhaustivo sin 'default'.
     */
    sealed interface PaymentEvent permits Approved, Rejected, Pending {}
    record Approved(String txId, double amount) implements PaymentEvent {}
    record Rejected(String txId, String reason) implements PaymentEvent {}
    record Pending(String txId) implements PaymentEvent {}

    // ══════════════════════════════════════════════════════════════════════════
    // 3. SWITCH EXPRESSIONS con sealed types (Java 17)
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Con sealed interfaces el switch es exhaustivo:
     * el compilador verifica que se cubran todos los subtipos,
     * no necesitas 'default'.
     */
    static String describeEvent(PaymentEvent event) {
        return switch (event) {
            case Approved a  -> "✓ Aprobado TX:" + a.txId() + " $" + a.amount();
            case Rejected r  -> "✗ Rechazado TX:" + r.txId() + " → " + r.reason();
            case Pending p   -> "⏳ Pendiente TX:" + p.txId();
            // No necesita default: sealed + exhaustivo
        };
    }

    // ══════════════════════════════════════════════════════════════════════════
    // 4. PATTERN MATCHING instanceof (Java 16+ stable)
    // ══════════════════════════════════════════════════════════════════════════

    static void processObject(Object obj) {
        // ❌ Antes de Java 16:
        // if (obj instanceof String) { String s = (String) obj; ... }

        // ✓ Java 16+: el cast es automático
        if (obj instanceof String s && s.length() > 5) {
            System.out.println("String largo: " + s.toUpperCase());
        } else if (obj instanceof Integer i && i > 0) {
            System.out.println("Entero positivo: " + i);
        } else if (obj instanceof Transaction t && t.isHighValue()) {
            System.out.println("Transacción de alto valor: " + t.id() + " $" + t.amount());
        } else {
            System.out.println("Tipo desconocido: " + obj.getClass().getSimpleName());
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // 5. TEXT BLOCKS (Java 15+ stable)
    // ══════════════════════════════════════════════════════════════════════════

    static String buildQuery(String userId) {
        // ❌ Antes:
        // String sql = "SELECT t.id, t.amount\n" +
        //              "FROM transactions t\n" +
        //              "WHERE t.user_id = '" + userId + "'";

        // ✓ Con Text Blocks:
        return """
                SELECT t.id, t.amount, t.currency
                FROM transactions t
                WHERE t.user_id = '%s'
                  AND t.status = 'ACTIVE'
                ORDER BY t.amount DESC
                """.formatted(userId);
    }

    // ══════════════════════════════════════════════════════════════════════════
    // MAIN — Demostración
    // ══════════════════════════════════════════════════════════════════════════

    public static void main(String[] args) {

        // ── Records ────────────────────────────────────────────────────────
        Transaction tx = new Transaction("TX001", 15000.0, "usd");
        System.out.println("=== RECORDS ===");
        System.out.println(tx);                   // toString() auto-generado
        System.out.println(tx.id());              // getter sin get-prefix
        System.out.println(tx.currency());        // "USD" (normalizado en compact constructor)
        System.out.println(tx.isHighValue());     // true

        // Records son inmutables: no hay setters
        // tx.amount = 0; ← ¡no compila!

        // equals() basado en contenido, no referencia
        Transaction tx2 = new Transaction("TX001", 15000.0, "USD");
        System.out.println(tx.equals(tx2)); // true

        // ── Sealed + Switch ────────────────────────────────────────────────
        System.out.println("\n=== SEALED + SWITCH ===");
        List<PaymentEvent> events = List.of(
            new Approved("T1", 500.0),
            new Rejected("T2", "Fondos insuficientes"),
            new Pending("T3")
        );

        for (PaymentEvent e : events) {
            System.out.println(describeEvent(e));
        }

        // ── Pattern Matching instanceof ────────────────────────────────────
        System.out.println("\n=== PATTERN MATCHING ===");
        processObject("HelloWorld");                           // String largo
        processObject(42);                                     // Entero positivo
        processObject(new Transaction("T4", 25000.0, "EUR"));  // Alto valor
        processObject(3.14);                                   // Tipo desconocido

        // ── Text Blocks ────────────────────────────────────────────────────
        System.out.println("\n=== TEXT BLOCKS ===");
        System.out.println(buildQuery("user123"));

        // ── Switch expression básico (sin pattern matching) ───────────────
        int day = 3;
        String dayName = switch (day) {
            case 1 -> "Lunes";
            case 2 -> "Martes";
            case 3 -> "Miércoles";
            case 4 -> "Jueves";
            case 5 -> "Viernes";
            default -> "Fin de semana";
        };
        System.out.println("\n=== SWITCH EXPRESSION ===");
        System.out.println(dayName); // Miércoles

        // Switch expression con bloque y yield
        int numLetters = switch (dayName) {
            case "Lunes", "Jueves" -> 5;
            case "Martes" -> 6;
            default -> {
                yield dayName.length(); // yield en bloque
            }
        };
        System.out.println("Letras en '" + dayName + "': " + numLetters); // 10
    }
}
