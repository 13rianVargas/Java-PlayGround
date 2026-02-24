import java.util.LinkedHashMap;
import java.util.Map;

/**
 * PROBLEMA 3 — Caché LRU (Least Recently Used)
 *
 * Implementa una caché con capacidad fija.
 * Cuando está llena y se inserta un nuevo elemento,
 * se elimina el elemento menos recientemente usado.
 */
public class LRUCache {

    /**
     * @param capacity capacidad máxima de la caché (>= 1)
     */
    public LRUCache(int capacity) {
        // TODO: Inicializa la estructura de datos
        // Pista: LinkedHashMap(capacity, 0.75f, accessOrder=true)
        // + Override removeEldestEntry() para auto-eliminar LRU
        throw new UnsupportedOperationException("Inicializar en el constructor");
    }

    /**
     * @return el valor asociado a key, o -1 si no existe
     */
    public int get(int key) {
        // TODO
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * Inserta o actualiza (key, value).
     * Si la caché está llena, elimina el menos recientemente usado.
     */
    public void put(int key, int value) {
        // TODO
        throw new UnsupportedOperationException("Implementa este método.");
    }
}
