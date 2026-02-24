import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import Problem2.Comment;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Problema 2 — Procesamiento de Texto")
class Problem2Test {

    private List<Comment> comments;

    @BeforeEach
    void setUp() {
        comments = List.of(
                new Comment("u1", "great service very fast", 5),
                new Comment("u1", "fast delivery great packaging", 4),
                new Comment("u2", "very slow and bad service", 2),
                new Comment("u3", "great great great", 5),
                new Comment("u2", "fast and reliable", 4),
                new Comment("u4", "bad quality very bad", 1));
    }

    // ── 2a. countByRating ─────────────────────────────────────────────────────

    @Test
    @DisplayName("T01 — countByRating cuenta correctamente")
    void t01_countByRating() {
        Map<Integer, Long> result = Problem2.countByRating(comments);
        assertEquals(2L, result.get(5));
        assertEquals(2L, result.get(4));
        assertEquals(1L, result.get(2));
        assertEquals(1L, result.get(1));
        assertNull(result.get(3), "Rating 3 no tiene comentarios");
    }

    @Test
    @DisplayName("T02 — countByRating con lista vacía → mapa vacío")
    void t02_countByRating_empty() {
        assertTrue(Problem2.countByRating(List.of()).isEmpty());
    }

    // ── 2b. topWords ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("T03 — topWords retorna las n más frecuentes")
    void t03_topWords_top3() {
        Set<String> stopWords = Set.of("and", "very", "and");
        List<String> result = Problem2.topWords(comments, 3, stopWords);
        assertEquals(3, result.size());
        // "great" aparece 5 veces, "fast" 3 veces, "bad" 3 veces, "service" 2 veces
        assertEquals("great", result.get(0));
        // "bad" y "fast" tienen 3 apariciones → orden alfabético: bad antes que fast
        assertEquals("bad", result.get(1));
        assertEquals("fast", result.get(2));
    }

    @Test
    @DisplayName("T04 — topWords ignora mayúsculas/minúsculas")
    void t04_topWords_caseInsensitive() {
        List<Comment> cs = List.of(
                new Comment("u1", "Hello hello HELLO", 5));
        List<String> result = Problem2.topWords(cs, 1, Set.of());
        assertEquals(List.of("hello"), result);
    }

    @Test
    @DisplayName("T05 — topWords n mayor que vocabulario retorna todas las palabras disponibles")
    void t05_topWords_nLargerThanVocab() {
        List<Comment> cs = List.of(new Comment("u1", "one two", 5));
        List<String> result = Problem2.topWords(cs, 100, Set.of());
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("T06 — topWords excluye stopWords")
    void t06_topWords_excludesStopWords() {
        Set<String> stopWords = Set.of("great", "fast", "service", "very", "and");
        List<String> result = Problem2.topWords(comments, 2, stopWords);
        assertFalse(result.contains("great"));
        assertFalse(result.contains("fast"));
    }

    // ── 2c. averageRatingForUser ──────────────────────────────────────────────

    @Test
    @DisplayName("T07 — averageRatingForUser calcula correctamente")
    void t07_avgForUser_correct() {
        OptionalDouble result = Problem2.averageRatingForUser(comments, "u1");
        assertTrue(result.isPresent());
        assertEquals(4.5, result.getAsDouble(), 0.001); // (5+4)/2
    }

    @Test
    @DisplayName("T08 — averageRatingForUser con userId inexistente → empty")
    void t08_avgForUser_unknown() {
        assertTrue(Problem2.averageRatingForUser(comments, "unknown").isEmpty());
    }

    // ── 2d. avgRatingPerUser ─────────────────────────────────────────────────

    @Test
    @DisplayName("T09 — avgRatingPerUser solo incluye usuarios con >= 2 comentarios")
    void t09_avgRatingPerUser_onlyTwoOrMore() {
        Map<String, Double> result = Problem2.avgRatingPerUser(comments);
        assertTrue(result.containsKey("u1"), "u1 tiene 2 comentarios");
        assertTrue(result.containsKey("u2"), "u2 tiene 2 comentarios");
        assertFalse(result.containsKey("u3"), "u3 tiene solo 1 comentario");
        assertFalse(result.containsKey("u4"), "u4 tiene solo 1 comentario");
    }

    @Test
    @DisplayName("T10 — avgRatingPerUser calcula promedios correctamente")
    void t10_avgRatingPerUser_correctValues() {
        Map<String, Double> result = Problem2.avgRatingPerUser(comments);
        assertEquals(4.5, result.get("u1"), 0.001); // (5+4)/2
        assertEquals(3.0, result.get("u2"), 0.001); // (2+4)/2
    }
}
