import java.util.*;

/**
 * EJERCICIOS — Two Pointers
 * Patrón para arrays/strings con dos índices que se mueven.
 */
public class Exercises {

    // ─────────────────────────────────────────────────────────────────────
    // E1 — Par con suma objetivo (array ordenado)
    //
    // Dado un array ORDENADO y un target, retorna los ÍNDICES (1-based) de
    // los dos números que suman target. Se garantiza solución única.
    //
    
    // Ejemplo: [2, 7, 11, 15], target=9  → [1, 2]
    // Ejemplo: [2, 3, 4],     target=6  → [1, 3]
    
    //
    // ⏱️ O(n) tiempo, O(1) espacio — NO usar HashMap
    //    sum == target: return el par.
    //    sum < target: left++.
    //    sum > target: right--.
            // ────────────────────────────────
    static int[] twoSum(int[] numbers, int target) {
        

        // TODO
        

        return new int[]{-1, -1};
    }

    // ─────────────────────────────────────────────────────────────────────
    // E2 — ¿Es un palíndromo?
    //
    // Dado un String con solo caracteres alfanuméricos, retorna true si es
    // palíndromo (case-insensitive).
    //
    // Ejemplo: "racecar"     → true
    // Ejemplo: "A man a plan a canal Panama" → false (tiene espacios — simplificado)
    // Ejemplo: "hello"       → false
    // Ejemplo: ""            → true
    // Ejemplo: "a"           → true
    //
    // 💡 left y right desde los extremos, comparar char a char.
    // ─────────────────────────────────────────────────────────────────────
    static boolean isPalindrome(String s) {
        // TODO
            
    }

    // ─────────────────────────────────────────────────────────────────────
    // E3 — Eliminar duplicados de array ordenado (in-place)
    //
    // Dado un array ORDENADO, elimina los duplicados IN-PLACE y retorna
    // la cantidad de elementos únicos. No importa lo que quede después.
    //
    // Ejemplo: [1,1,2 rray queda [1,2,...])
    // Ejemplo: [0,0,1,1,1,2,2,3,3  → 5
     * 
    // 
    // 💡 slow pointer para posi a escribir,
    //    fast pointer para recorer. Cuando fast != arr[slow], avanza slow y copia.
    // ─────────────────────────────────────────────────────────────────────
    static int removeDuplicates(int[] arr) {
        // TODO
        return 0;
    }

    // ─────────────────────────────────────────────────────────────────────
    // E4 — Contenedor con más agua
    //
    // Dado un array height[] donde height[i] es la altura de una línea,
    // encuentra el par de líneas que junto con el eje X forma el contenedor
    
    // con mayor área de agua.
    //
    // Área = min(height[l], height[r]) * (r - l)
    //
    // Ejemplo: [1,8,6,2,5,4,8,3,7] → 49
    // Ejemplo: [1,1]               → 1
    //
    // 💡 Mueve el puntero del lado con MENOR altura (pierdes ancho de 1,
    //    pero puedes ganar en altura). Este es un clásico de greedy + 2 pointers.
    // ─────────────────────────────────────────────────────────────────────
    static int maxWater(int[] height) {
        // TODO
        return 0;
    }

    // ─────────────────────────────────────────────────────────────────────
    // E5 — Revertir palabras en un String
    //
    // Dado un String de palabras separadas por espacios, retorna las
    // palabras en orden inverso. Espacios múltiples entre palabras deben
    // reducirse a uno. Sin espacios iniciales/finales.
    //
    // Ejemplo: "the sky is blue"  → "blue is sky the"
    // Ejemplo: "  hello world  " → "world hello"
    // Ejemplo: "a good example"  → "example good a"
    //
    // 💡 Haz split con regex "\\s+" para manejar espacios múltiples,
    //    luego usa dos punteros o Collections.reverse, o StringBuilder.
    // ─────────────────────────────────────────────────────────────────────
    static String reverseWords(String s) {
        // TODO
        return "";
    }

    public static void main(String[] args) {
        System.out.println("═══ E1: twoSum ═══");
        System.out.println(Arrays.toString(twoSum(new int[]{2,7,11,15}, 9)));  // [1, 2]
        System.out.println(Arrays.toString(twoSum(new int[]{2,3,4}, 6)));      // [1, 3]

        System.out.println("\n═══ E2: isPalindrome ═══");
        System.out.println(isPalindrome("racecar"));  // true
        System.out.println(isPalindrome("hello"));    // false
        System.out.println(isPalindrome(""));         // true

        System.out.println("\n═══ E3: removeDuplicates ═══");
        int[] a = {1,1,2}; System.out.println(removeDuplicates(a) + " → " + Arrays.toString(a));
        int[] b = {0,0,1,1,1,2,2,3,3,4}; System.out.println(removeDuplicates(b));

        System.out.println("\n═══ E4: maxWater ═══");
        System.out.println(maxWater(new int[]{1,8,6,2,5,4,8,3,7})); // 49
        System.out.println(maxWater(new int[]{1,1}));                // 1

        System.out.println("\n═══ E5: reverseWords ═══");
        System.out.println(reverseWords("the sky is blue"));    // "blue is sky the"
        System.out.println(reverseWords("  hello world  "));   // "world hello"
    }
}
