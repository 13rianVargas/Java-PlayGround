import java.util.*;

/** SOLUCIONES — Two Pointers */
public class Builder {

    static int[] twoSum(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if      (sum == target) return new int[]{left + 1, right + 1};
            else if (sum < target)  left++;
            else                    right--;
        }
        return new int[]{0, 0};
    }

    static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (Character.toLowerCase(s.charAt(left)) !=
                Character.toLowerCase(s.charAt(right))) return false;
            left++; right--;
        }
        return true;
    }

    static int removeDuplicates(int[] arr) {
        if (arr.length == 0) return 0;
        int slow = 0;
        for (int fast = 1; fast < arr.length; fast++) {
            if (arr[fast] != arr[slow]) {
                slow++;
                arr[slow] = arr[fast];
            }
        }
        return slow + 1;
    }
    // slow = posición del último único escrito
    // fast = explorador, avanza siempre
    // Cuando fast encuentra un nuevo valor, slow avanza y lo copia

    static int maxWater(int[] height) {
        int left = 0, right = height.length - 1;
        int maxArea = 0;
        while (left < right) {
            int area = Math.min(height[left], height[right]) * (right - left);
            maxArea = Math.max(maxArea, area);
            // Mueve el puntero del lado más bajo — preserva la esperanza de más agua
            if (height[left] <= height[right]) left++;
            else                               right--;
        }
        return maxArea;
    }
    // ⚠️ La intuición: el área está limitada por el lado más bajo.
    // Mover el lado más alto siempre reduciría área (el ancho baja, la altura no puede mejorar).
    // Mover el lado más bajo da chance de encontrar una altura mayor.

    static String reverseWords(String s) {
        String[] words = s.trim().split("\\s+");  // split en cualquier cantidad de espacios
        int left = 0, right = words.length - 1;
        while (left < right) {
            String tmp = words[left]; words[left] = words[right]; words[right] = tmp;
            left++; right--;
        }
        return String.join(" ", words);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum(new int[]{2,7,11,15}, 9)));  // [1, 2]
        System.out.println(isPalindrome("racecar"));  // true
        System.out.println(isPalindrome("hello"));    // false
        int[] a = {1,1,2}; System.out.println(removeDuplicates(a));  // 2
        System.out.println(maxWater(new int[]{1,8,6,2,5,4,8,3,7})); // 49
        System.out.println(reverseWords("the sky is blue"));    // "blue is sky the"
        System.out.println(reverseWords("  hello world  "));   // "world hello"
    }
}
