import java.util.*;

/**
 * SOLUCIONES — String Manipulation
 *
 * Complejidades y decisiones Senior documentadas.
 */
public class Solutions {

    // ══════════════════════════════════════════════════════════════════════════
    // E1 — Primer carácter no repetido
    // Tiempo: O(n), Espacio: O(1) — solo 26 letras
    // ══════════════════════════════════════════════════════════════════════════
    public static char firstUniqueChar(String s) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;

        // Segunda pasada: mantiene el ORDEN de aparición
        for (char c : s.toCharArray()) {
            if (freq[c - 'a'] == 1) return c;
        }
        return '\0';
        // ❌ TRAMPA FRECUENTE — usar HashMap pierde el orden de inserción.
        //    LinkedHashMap sí lo preserva, pero int[] es más eficiente.
    }

    // ══════════════════════════════════════════════════════════════════════════
    // E2 — Compresión "Run-Length Encoding"
    // Tiempo: O(n), Espacio: O(n) — el StringBuilder
    // ══════════════════════════════════════════════════════════════════════════
    public static String compress(String s) {
        if (s == null || s.isEmpty()) return s;

        StringBuilder sb = new StringBuilder();
        int count = 1;

        for (int i = 1; i <= s.length(); i++) {
            if (i < s.length() && s.charAt(i) == s.charAt(i - 1)) {
                count++;
            } else {
                sb.append(s.charAt(i - 1)).append(count);
                count = 1;
            }
        }

        String compressed = sb.toString();
        return compressed.length() < s.length() ? compressed : s;

        // ✓ Senior: verifica ANTES de retornar si la compresión mejora el tamaño
    }

    // ══════════════════════════════════════════════════════════════════════════
    // E3 — Palabras únicas
    //
    // Tiempo: O(n), Espacio: O(k) — k palabras únicas
    // ══════════════════════════════════════════════════════════════════════════
    public static int countUniqueWords(String text) {
        if (text == null || text.isBlank()) return 0;

        // Elimina puntuación
        String[] words = text.toLowerCase()
                        .replaceAll("[^a-z0-9\\s]", "")
                        .trim()
                        .split("\\s+");

        Set<String> unique = new HashSet<>(Arrays.asList(words));
        unique.remove(""); // por si hay espacios múltiples
        return unique.size();
    }

    // ══════════════════════════════════════════════════════════════════════════
    // E4 — Palíndromo ignorando no-alfanuméricos
    //
    // Tiempo: O(n), Espacio: O(1) — dos punteros, sin nuevo String
    // ══════════════════════════════════════════════════════════════════════════
    public static boolean isPalindromeClean(String s) {
        int left = 0, right = s.length() - 1;

        while (left < right) {
            // Avanza saltando no-alfanuméricos
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;

            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                    return false;
            }
            left++;
            right--;
        }

        return true;

        // ❌ Mid haría: s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase() y compararía con reverse
        // → O(n) espacio extra
    }

    // ══════════════════════════════════════════════════════════════════════════
    // E5 — Longest Common Prefix
    //
    // Tiempo: O(n*m) donde n=palabras, m=longitud del primero
    // Espacio: O(1)
    // ══════════════════════════════════════════════════════════════════════════
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        String prefix = strs[0]; // arranca tomando el primero como prefijo

        for (int i = 1; i < strs.length; i++) {
            // Reduce el prefijo hasta que strs[i] empiece con él
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        }
        return prefix;

        // ✓ Senior: no usa sorting ni regex — solución directa O(n*m)
        // La solución con sorting es tramposa: O(n log n) pero funciona porque
        // el prefijo del array ordenado = prefijo del primer y el último elemento
    }
}
