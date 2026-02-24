import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * ============================================================
 * MÓDULO 06.03 — TDD Practice: ¡TÚ implementas el código!
 * ============================================================
 *
 * INSTRUCCIONES:
 * 1. NO mires los tests aún (src/test/java/BankLedgerTest.java).
 * Bueno, en TDD los tests se escriben primero, aquí ya están.
 * 2. Lee la documentación de cada método.
 * 3. Implementa cada método para que los tests pasen.
 * 4. Ejecuta: mvn test -pl 03-TDD-Practice
 * 5. ¡Todos los tests deben ser verdes!
 *
 * CONCEPTO SENIOR:
 * En TDD real, el ciclo es:
 * 🔴 Red → Escribe un test que falle
 * 🟢 Green → Escribe el código MÍNIMO para que pase
 * 🔵 Refactor → Limpia el código sin romper tests
 *
 * Aquí simulamos la fase 🟢: los tests ya existen, tú haces Green.
 */
public class BankLedger {

    public record Entry(String id, BigDecimal amount, String category, boolean reconciled) {

        public Entry {
            Objects.requireNonNull(id, "id cannot be null");
            Objects.requireNonNull(amount, "amount cannot be null");
            Objects.requireNonNull(category, "category cannot be null");
        }
    }

    private final List<Entry> entries;

    public BankLedger() {
        this.entries = new ArrayList<>();
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // MÉTODO 1: addEntry
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Agrega una entrada al ledger.
     *
     * Reglas:
     * - Si entry es null → lanza NullPointerException("entry cannot be null")
     * - Si ya existe una entry con el mismo id → lanza
     * IllegalArgumentException("duplicate id: " + id)
     * - Si todo ok → agrega y retorna el tamaño actual del ledger
     */
    public int addEntry(Entry entry) {
        // TODO: Implementa este método
        throw new UnsupportedOperationException("Implementa addEntry");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // MÉTODO 2: getBalance
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Retorna la suma de todos los amounts en el ledger.
     * (Los amounts negativos representan débitos, positivos créditos.)
     *
     * Resultado con scale 2, HALF_UP.
     * Si el ledger está vacío → retorna BigDecimal.ZERO con scale 2.
     */
    public BigDecimal getBalance() {
        // TODO: Implementa este método
        throw new UnsupportedOperationException("Implementa getBalance");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // MÉTODO 3: getBalanceByCategory
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Retorna un Map<String, BigDecimal> con la suma de amounts por categoría.
     *
     * - Las claves son las categorías existentes.
     * - Los valores son la suma de amounts de esa categoría, scale 2, HALF_UP.
     * - Si el ledger está vacío → retorna un Map vacío.
     * - El mapa retornado debe ser inmodificable.
     */
    public Map<String, BigDecimal> getBalanceByCategory() {
        // TODO: Implementa este método
        throw new UnsupportedOperationException("Implementa getBalanceByCategory");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // MÉTODO 4: getUnreconciledEntries
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Retorna una lista inmutable de entries donde reconciled == false,
     * ordenadas por amount descendente (mayor primero).
     *
     * Si no hay entries no reconciliadas → retorna lista vacía.
     */
    public List<Entry> getUnreconciledEntries() {
        // TODO: Implementa este método
        throw new UnsupportedOperationException("Implementa getUnreconciledEntries");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // MÉTODO 5: reconcileById
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Marca una entry como reconciled.
     *
     * Como Entry es un record (inmutable), debes:
     * 1. Encontrar la entry con el id dado.
     * 2. Reemplazarla por una nueva Entry con reconciled = true.
     * 3. Retornar true si se encontró y actualizó.
     *
     * - Si id es null → lanza NullPointerException("id cannot be null")
     * - Si no se encuentra → retorna false
     * - Si ya estaba reconciled → retorna false (no cambió nada)
     */
    public boolean reconcileById(String id) {
        // TODO: Implementa este método
        throw new UnsupportedOperationException("Implementa reconcileById");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // MÉTODO 6: findLargestDebit
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Retorna la entry con el débito más grande (amount negativo más alejado de 0).
     *
     * - Solo considera entries con amount < 0.
     * - Si no hay débitos → retorna Optional.empty()
     * - Si hay empate → retorna cualquiera de los empatados.
     */
    public Optional<Entry> findLargestDebit() {
        // TODO: Implementa este método
        throw new UnsupportedOperationException("Implementa findLargestDebit");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // MÉTODO 7: removeById
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Elimina una entry por su id.
     *
     * - Si id es null → lanza NullPointerException("id cannot be null")
     * - Si se encontró y eliminó → retorna true
     * - Si no se encontró → retorna false
     */
    public boolean removeById(String id) {
        // TODO: Implementa este método
        throw new UnsupportedOperationException("Implementa removeById");
    }

    /**
     * Retorna el número de entries en el ledger.
     */
    public int size() {
        return entries.size();
    }

    /**
     * Acceso interno para uso en implementaciones.
     */
    protected List<Entry> getEntries() {
        return entries;
    }
}
