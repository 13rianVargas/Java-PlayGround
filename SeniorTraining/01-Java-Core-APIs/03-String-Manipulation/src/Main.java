/**
 * ============================================================
 * MÓDULO 01 — String Manipulation: Guía completa
 * ============================================================
 *
 * Operaciones de String más evaluadas en pruebas Senior:
 */
public class Main {

    public static void main(String[] args) {

        // ── 1. StringBuilder (SIEMPRE en vez de concatenación en loops) ──────
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(i).append(",");
        }
        sb.deleteCharAt(sb.length() - 1); // elimina última coma
        System.out.println(sb); // 0,1,2,3,4

        // ── 2. String → char array y viceversa ────────────────────────────
        String s = "hello";
        char[] chars = s.toCharArray();          // {'h','e','l','l','o'}
        chars[0] = 'H';
        String modified = new String(chars);      // "Hello"
        System.out.println(modified);

        // ── 3. Frecuencia de caracteres ───────────────────────────────────
        String programming = "programming";
        int[] freq = new int[26];
        for (char c : programming.toCharArray()) {
            freq[c - 'a']++;
        }
        // freq[2] = count of 'c', freq['g'-'a'] = 2, etc.

        // ── 4. Métodos de String más usados en exámenes ───────────────────
        String str = "  Hello World  ";
        System.out.println(str.trim());                    // "Hello World"
        System.out.println(str.strip());                   // igual pero Unicode-aware
        System.out.println("abc".charAt(1));               // 'b'
        System.out.println("abc".indexOf('b'));            // 1
        System.out.println("abc".substring(1));           // "bc"
        System.out.println("abc".substring(1, 2));        // "b"  (end exclusive)
        System.out.println("abc".contains("bc"));         // true
        System.out.println("abc".startsWith("ab"));       // true
        System.out.println("abc".endsWith("bc"));         // true
        System.out.println("ABC".toLowerCase());          // "abc"
        System.out.println("hello".toUpperCase());        // "HELLO"
        System.out.println("a,b,c".split(",").length);    // 3
        System.out.println(String.join("-", "a","b","c")); // "a-b-c"
        System.out.println("haha".replace("ha", "ho"));   // "hoho"
        System.out.println("abc".isEmpty());              // false

        // ── 5. Comparación correcta ─────────────────────────────────────────
        String a = "hello";
        String b = new String("hello");
        System.out.println(a.equals(b));              // true (valor)
        System.out.println(a.equalsIgnoreCase("HELLO")); // true

        // ── 6. Número de ocurrencias sin regex ─────────────────────────────
        String text = "banana";
        String target = "an";
        int count = 0, idx = 0;
        while ((idx = text.indexOf(target, idx)) != -1) {
            count++;
            idx += target.length();
        }
        System.out.println("occurrences of 'an' in 'banana': " + count); // 2

        // ── 7. Palíndromo eficiente ───────────────────────────────────────
        System.out.println(isPalindrome("racecar")); // true
        System.out.println(isPalindrome("hello"));   // false

        // ── 8. Anagrama ────────────────────────────────────────────────
        System.out.println(isAnagram("listen", "silent")); // true

        // ── 9. Reverse ─────────────────────────────────────────────────
        System.out.println(new StringBuilder("hello").reverse()); // olleh

        // ── 10. String.format ───────────────────────────────────────────
        String formatted = String.format("Name: %s, Balance: $%.2f", "Alice", 1234.5);
        System.out.println(formatted); // Name: Alice, Balance: $1234.50
    }

    // ── Helpers ─────────────────────────────────────────────────────

    static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) return false;
        }
        return true;
    }

    static boolean isAnagram(String a, String b) {
        if (a.length() != b.length()) return false;
        int[] count = new int[26];
        for (char c : a.toCharArray()) count[c - 'a']++;
        for (char c : b.toCharArray()) count[c - 'a']--;
        for (int v : count) if (v != 0) return false;
        return true;
    }
}
