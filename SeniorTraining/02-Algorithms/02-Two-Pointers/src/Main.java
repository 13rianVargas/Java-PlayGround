import java.util.Arrays;

/**
 * ============================================================
 * MÓDULO 02 — Dos Punteros (Two Pointers): Guía completa
 * ============================================================
 *
 * Patrón: dos índices (left, right) que se mueven desde los extremos
 * o en la misma dirección para resolver problemas en O(n) que
 * ingenuamente serían O(n²).
 *
 * CUÁNDO USARLO:
 * - Array ordenado + buscar par con suma k
 * - Detectar palíndromo
 * - Eliminar duplicados in-place
 * - Problema del área máxima
 * - Invertir elementos
 */
public class Main {

    public static void main(String[] args) {

        // ── 1. Ejemplo clásico: encontrar par con suma k ───────────────────────
        int[] arr = {1, 2, 3, 4, 6};
        int target = 6;
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int sum = arr[left] + arr[right];
            if (sum == target) {
                System.out.println("Par: [" + arr[left] + ", " + arr[right] + "]"); // [2, 4]
                break;
            } else if (sum < target) left++;
            else right--;
        }

        // ── 2. Palíndromo con dos punteros ──────────────────────────────────
        System.out.println(isPalindrome("racecar")); // true
        System.out.println(isPalindrome("hello"));   // false

        // ── 3. Eliminar duplicados in-place (slow/fast) ─────────────────
        int[] nums = {1, 1, 2, 2, 3, 4, 4};
        int uniqueLen = removeDuplicates(nums);
        System.out.println("Unique length: " + uniqueLen); // 4
        System.out.println(Arrays.toString(Arrays.copyOf(nums, uniqueLen))); // [1,2,3,4]

        // ── 4. Problema del agua: contenedor de mayor área ─────────────
        int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println("Max water: " + maxWater(heights)); // 49

        // ── 5. Invertir array in-place ──────────────────────────────────────────
        int[] toReverse = {1, 2, 3, 4, 5};
        reverseArray(toReverse);
        System.out.println("Reversed: " + Arrays.toString(toReverse)); // [5,4,3,2,1]
    }

    /*
     * ══════════════ CUADRO DE REFERENCIA ═══════════════
     * Patrón                   | Movimiento punteros
     * ─────────────────────────+─────────────────────────
     * Par con suma             | ambos hacia el centro
     * Palíndromo                | ambos hacia el centro
     * Remove duplicates        | fast siempre avanza
     *   (slow/fast)            | slow avanza si es distinto
     * Max water                | mover el más bajo
     * Reverse                  | swap y cerrar
     */

    // ── Implementaciones de demostración ───────────────────────────────────────

    static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) return false;
        }
        return true;
    }

    static int removeDuplicates(int[] nums) {
        int slow = 0;
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow]) {
                nums[++slow] = nums[fast];
            }
        }
        return slow + 1;
    }

    static int maxWater(int[] heights) {
        int left = 0, right = heights.length - 1;
        int maxArea = 0;
        while (left < right) {
            int area = Math.min(heights[left], heights[right]) * (right - left);
            maxArea = Math.max(maxArea, area);
            if (heights[left] < heights[right]) left++;
            else right--;
        }
        return maxArea;
    }

    static void reverseArray(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left++] = arr[right];
            arr[right--] = temp;
        }
    }
}
