/**
 * ============================================================
 *  05 — RECURSIÓN — Soluciones Anotadas
 * ============================================================
 */

import java.util.*;

public class Solutions {

    /**
     * EJERCICIO 1: Exponenciación rápida.
     *
     * Complejidad: O(log n) — dividimos el exponente a la mitad en cada paso
     *
     * La clave es que base^10 = (base^5)^2, y base^5 = base * (base^2)^2
     */
    public static long power(long base, int exponent) {
        if (exponent == 0) return 1;
        if (exponent == 1) return base;

        long half = power(base, exponent / 2);
        if (exponent % 2 == 0) {
            return half * half;
        } else {
            return base * half * half;
        }
    }

    /**
     * EJERCICIO 2: Generar paréntesis válidos.
     *
     * Complejidad: O(4^n / sqrt(n)) — número catalán
     *
     * REGLA: Puedes agregar '(' si open < n.
     *        Puedes agregar ')' si close < open.
     *        Cuando close == n, tienes una combinación válida.
     */
    public static List<String> generateParentheses(int n) {
        List<String> result = new ArrayList<>();
        backtrackParens(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    private static void backtrackParens(List<String> result, StringBuilder sb,
                                         int open, int close, int n) {
        if (sb.length() == 2 * n) {
    
            result.add(sb.toString());
            return;
        }

        if (open < n) {
            sb.append('(');
            backtrackParens(result, sb, open + 1, close, n);
            sb.deleteCharAt(sb.length() - 1); // backtrack
        }
                

        if (close < open) {
            sb.append(')');
            backtrackParens(result, sb, open, close + 1, n);
            sb.deleteCharAt(sb.length() - 1); // backtrack
        }
    }

    /**
     * EJERCICIO 3: Aplanar lista anidada.
     *
     * Complejidad: O(n) donde n es el número total de elementos
     */
    @SuppressWarnings("unchecked")
    public static List<Integer> flatten(List<Object> nested) {
        List<Integer> result = new ArrayList<>();
        for (Object item : nested) {
            if (item instanceof Integer val) {
                result.add(val);
            } else if (item instanceof List<?> list) {
                result.addAll(flatten((List<Object>) list));
            }
        }
        return result;
    }

    /**
     * EJERCICIO 4: Palíndromo recursivo.
     *
     * Complejidad: O(n)
     * 1. Limpiar: solo dejar alfanuméricos en minúscula
     * 2. Comparar extremos recursivamente
     */
    public static boolean isPalindromeRecursive(String s) {
        String clean = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return checkPalin(clean, 0, clean.length() - 1);
    }

    private static boolean checkPalin(String s, int left, int right) {
        if (left >= right) return true;                    // caso base
        if (s.charAt(left) != s.charAt(right)) return false;
        return checkPalin(s, left + 1, right - 1);        // acercar extremos
    }

    /**
     * EJERCICIO 5: Letter Combinations.
     *
     * Complejidad: O(4^n) donde n = longitud del string de dígitos
     *              (máximo 4 letras por dígito: 7→pqrs, 9→wxyz)
     */
    public static List<String> letterCombinations(String digits) {
        if (digits == null || digits.isEmpty()) return Collections.emptyList();

        Map<Character, String> phone = Map.of(
            '2', "abc", '3', "def", '4', "ghi", '5', "jkl",
            '6', "mno", '7', "pqrs", '8', "tuv", '9', "wxyz"
        );

            
        

        List<String> result = new ArrayList<>();
        
        backtrackLetters(result, new StringBuilder(), digits, 0, phone);
        return result;
    }

    private static void backtrackLetters(List<String> result, StringBuilder sb,
                                          String digits, int idx,
                                          Map<Character, String> phone) {
        if (idx == digits.length()) {
            result.add(sb.toString());
            return;
        }

        String letters = phone.get(digits.charAt(idx));
        for (char c : letters.toCharArray()) {
            sb.append(c);
            backtrackLetters(result, sb, digits, idx + 1, phone);
            sb.deleteCharAt(sb.length() - 1); // backtrack
        }
    }

 
    // ─── Verificación ───
    public static void main(String[] args) {
        System.out.println("=== RECURSIÓN — Soluciones ===\n");
    

        System.out.println("Ej1: " + power(2, 10));                          // 1024
        System.out.println("Ej2: " + generateParentheses(3));                // 5 combinaciones
        System.out.println("Ej3: " + flatten(
                List.of(1, List.of(2, 3), List.of(4, List.of(5)))));         // [1,2,3,4,5]
        System.out.println("Ej4: " + isPalindromeRecursive(
                "A man, a plan, a canal: Panama"));                          // true
        System.out.println("Ej5: " + letterCombinations("23"));              // 9 combos
    }
}
