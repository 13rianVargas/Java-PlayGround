import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Problema 3 — Caché LRU")
class LRUCacheTest {

    @Test
    @DisplayName("T01 — Ejemplo del enunciado completo")
    void t01_enunciado() {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 10);
        cache.put(2, 20);
        assertEquals(10, cache.get(1)); // LRU = 2
        cache.put(3, 30); // expulsa 2
        assertEquals(-1, cache.get(2)); // ya no existe
        assertEquals(30, cache.get(3));
    }

    @Test
    @DisplayName("T02 — get de clave inexistente → -1")
    void t02_missingKey() {
        LRUCache cache = new LRUCache(3);
        assertEquals(-1, cache.get(99));
    }

    @Test
    @DisplayName("T03 — put actualiza valor de clave existente")
    void t03_updateExisting() {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 100);
        cache.put(1, 200); // update
        assertEquals(200, cache.get(1));
    }

    @Test
    @DisplayName("T04 — Capacidad 1: el segundo put siempre expulsa el primero")
    void t04_capacityOne() {
        LRUCache cache = new LRUCache(1);
        cache.put(1, 10);
        cache.put(2, 20); // expulsa 1
        assertEquals(-1, cache.get(1));
        assertEquals(20, cache.get(2));
    }

    @Test
    @DisplayName("T05 — get actualiza orden LRU (evita que sea expulsado)")
    void t05_getUpdatesOrder() {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 10);
        cache.put(2, 20);
        cache.get(1); // 1 es ahora el más reciente, LRU = 2
        cache.put(3, 30); // expulsa 2 (no 1)
        assertEquals(10, cache.get(1)); // 1 sigue vivo
        assertEquals(-1, cache.get(2)); // 2 fue expulsado
        assertEquals(30, cache.get(3));
    }

    @Test
    @DisplayName("T06 — put de clave existente actualiza LRU order")
    void t06_putExistingUpdatesOrder() {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 10);
        cache.put(2, 20);
        cache.put(1, 15); // re-insert 1 → 1 es el más reciente, LRU = 2
        cache.put(3, 30); // expulsa 2
        assertEquals(15, cache.get(1));
        assertEquals(-1, cache.get(2));
    }

    @Test
    @DisplayName("T07 — No expulsa cuando hay capacidad disponible")
    void t07_noEvictionWhenCapacityAvailable() {
        LRUCache cache = new LRUCache(5);
        for (int i = 1; i <= 5; i++) {
            cache.put(i, i * 10);
        }
        for (int i = 1; i <= 5; i++) {
            assertEquals(i * 10, cache.get(i), "Todos deben existir");
        }
    }

    @Test
    @DisplayName("T08 — Expulsa el correcto en secuencia de accesos variada")
    void t08_correctEvictionOrder() {
        LRUCache cache = new LRUCache(3);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.get(1); // orden: 3(LRU) → 2 → 1(MRU)
        cache.get(2); // orden: 3(LRU) → 1 → 2(MRU)
        cache.put(4, 4); // expulsa 3
        assertEquals(-1, cache.get(3));
        assertEquals(1, cache.get(1));
        assertEquals(2, cache.get(2));
        assertEquals(4, cache.get(4));
    }
}
