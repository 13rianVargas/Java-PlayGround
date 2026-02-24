import java.util.*;

/**
 * PROBLEMA 2 — Procesamiento de Texto con Streams
 */
public class Problem2 {

    public record Comment(String userId, String text, int rating) {
    }

    /**
     * 2a. Cuenta cuántos comentarios hay por cada rating.
     * 
     * @return Map<rating, cantidad>
     */
    public static Map<Integer, Long> countByRating(List<Comment> comments) {
        // TODO
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * 2b. Retorna las n palabras más frecuentes, ignorando stopWords y mayúsculas.
     * En caso de empate en frecuencia, orden alfabético.
     * 
     * @return List de palabras, tamaño máximo n
     */
    public static List<String> topWords(List<Comment> comments, int n, Set<String> stopWords) {
        // TODO
        // Pista: comments.stream()
        // .flatMap(c -> Arrays.stream(c.text().toLowerCase().split("\\s+")))
        // .filter(w -> !stopWords.contains(w) && !w.isBlank())
        // .collect(groupingBy(..., counting()))
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * 2c. Promedio de ratings del usuario.
     * OptionalDouble.empty() si no tiene comentarios.
     */
    public static OptionalDouble averageRatingForUser(List<Comment> comments, String userId) {
        // TODO
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * 2d. userId → promedio de rating.
     * Solo incluye usuarios con >= 2 comentarios.
     */
    public static Map<String, Double> avgRatingPerUser(List<Comment> comments) {
        // TODO
        throw new UnsupportedOperationException("Implementa este método.");
    }
}
