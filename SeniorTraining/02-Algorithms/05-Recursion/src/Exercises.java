/**
 * ============================================================
 *  05 — RECURSIÓN — Ejercicios
 * ============================================================
 */

import java.util.*;

public class Exercises {

    /**
     * EJERCICIO 1: Potencia recursiva.
     *
     * Calcula base^exponent usando recursión.
     * Usa el método de exponenciación rápida:
     *   - base^0 = 1
    
     *   - base^n = base^(n/2) * base^(n/2)       si n par
     *   - base^n = base * base^(n/2) * base^(n/2) si n impar
            
            
            
     * Ejemplo: power(2, 10) → 1024
        

        
        

        ic static long power(long base, int exponent) {
        

        // TODO: Recursión con exponenciación rápida O(log n)
        
        throw new UnsupportedOperationException("Implementa este método.");
    }

    /**
     * EJERCICIO 2: Generar todos los paréntesis válidos.
     *
     * Dado n pares de paréntesis, genera todas las combinaciones
     * de paréntesis bien formados.
     *
     * Ejemplo: n=3 → ["((()))", "(()())", "(())()", "()(())", "()()()"]
     * Ejemplo: n=1 → ["()"]
     */
    public static List<String> generateParentheses(int n) {
        // TODO: Backtracking con contadores de op
      
     * 

    /**
            
     *
     * Dada una lista que puede contener enteros o listas anidadas,
     * retorna todos los enteros en una lista plana.
     *
     * Ejemplo: [1, [2, 3], [4, [5]]] → [1, 2, 3, 4, 5]
     *
     * Tip: Usa instanceof para verificar el tipo.
     */
    public static List en(List<Object> nested) {
        // TODO: Recursión — si es teger, agregar; si es List, recursión
     * 
        throw new UnsupportedOpe nException("Implementa este método.");
    } 
    

    /**
     * EJERCICIO 4: Palíndromo recursivo.
     *
     * Determina si un string es palíndromo usando recursión (no loops).
     * Ignora mayúsculas y caracteres no alfanuméricos.
     *
     * Ejemplo: "A man, a plan, a canal: Panama" → true
     * Ejemplo: "race a car"                     → false
     */
    public static boolean isPalindromeRecursive(String s) {
    

    
    /**
     

    * Dadoun 

     * de letras posibles (como un teléfono T9).
     *
     * Mapeo: 2→abc, 3→def, 4→ghi, 5→jkl, 6→mno, 7→pqrs, 8→tuv, 9→wxyz
     *
     * Ejemplo: digits="23" → ["ad","ae","af","bd","be","bf","cd","ce","cf"]
     * Ejemplo: digits=""   → []
     */
    public static List<String> letterCombinations(String digits) {
        // TODO: Backtracking con mapa de dígito → letras
        throw new UnsupportedOperationException("Implementa este método.");
    }

    // ─── Runner ───
    public static void main(String[] args) {
    
        System.out.println("=== RECURSIÓN — Ejercicios ===\n");

        try {
            System.out.println("Ej1: " + power(2, 10) + (power(2, 10) == 1024 ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej1: ⬜ No implementado"); }

        try {
            var r2 = generateParentheses(3);
            System.out.println("Ej2: " + r2.size() + " combinaciones" + (r2.size() == 5 ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej2: ⬜ No implementado"); }
 
        try {
            List<Object> nested = List.of(1, List.of(2, 3), List.of(4, List.of(5)));
         var r3 = flatten(nested);
        

       System.out.println("Ej3: " + r3 + (r3.equals(List.of(1, 2, 3, 4, 5)) ? " ✅" : " ❌"));
        

     catch (Exception e) { System.out.println("Ej3: ⬜ No implementado"); }


    
        try {
            boolean r4 = isPalindromeRecursive("A man, a plan, a canal: Panama");
            System.out.println("Ej4: " + r4 + (r4 ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej4: ⬜ No implementado"); }

        try {
            var r5 = letterCombinations("23");
            System.out.println("Ej5: " + r5.size() + " combos" + (r5.size() == 9 ? " ✅" : " ❌"));
        } catch (Exception e) { System.out.println("Ej5: ⬜ No implementado"); }
    }
}
