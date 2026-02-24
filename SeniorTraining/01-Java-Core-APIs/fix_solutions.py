#!/usr/bin/env python3
"""Fix corruption in Solutions.java files."""
import sys
import os

def fix_arrays_solutions():
    path = "01-Arrays-Utility/src/Solutions.java"
    with open(path, 'r') as f:
        lines = f.readlines()

    # Find key markers
    cada_line = None
    for_int_line = None
    for i, line in enumerate(lines):
        if 'Cada solución incluye:' in line:
            cada_line = i
        if cada_line is not None and 'for (int n : ints)' in line:
            for_int_line = i
            break

    if cada_line is not None and for_int_line is not None:
        new_block = [
            ' * Cada solución incluye:\n',
            ' *\n',
            ' *   - La implementación\n',
            ' *\n',
            ' *   - Explicación de la decisión de diseño\n',
            ' *   - Complejidad temporal y espacial\n',
            ' */\n',
            'public class Solutions {\n',
            '\n',
            '    // ─────────────────────────────────────────────────────────────────────\n',
            '    //\n',
            '    // SOLUCIÓN 1a — existsLinear: O(n) — fuerza bruta\n',
            '    //\n',
            '    // ─────────────────────────────────────────────────────────────────────\n',
            '    static boolean existsLinear(int[] ints, int k) {\n',
            '        for (int n : ints) {\n',
        ]
        lines[cada_line:for_int_line+1] = new_block
        print(f"  Fix 1: Replaced lines {cada_line}-{for_int_line} (javadoc + class + solution 1a header)")
    else:
        print(f"  Fix 1 SKIPPED: markers not found (cada={cada_line}, for_int={for_int_line})")

    with open(path, 'w') as f:
        f.writelines(lines)
    print(f"  Saved {path}")


def fix_string_solutions():
    path = "03-String-Manipulation/src/Solutions.java"
    with open(path, 'r') as f:
        content = f.read()

    correct = '''import java.util.*;

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
        return '\\0';
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
                        .replaceAll("[^a-z0-9\\\\s]", "")
                        .trim()
                        .split("\\\\s+");

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
'''

    with open(path, 'w') as f:
        f.write(correct)
    print(f"  Rewrote {path} completely")


def fix_math_main():
    path = "04-Math-BigDecimal/src/Main.java"
    with open(path, 'r') as f:
        lines = f.readlines()

    changes = 0

    for i, line in enumerate(lines):
        stripped = line.rstrip('\n')

        # Fix split separators: line with just ─ after a separator
        if stripped.strip() == '─' and i > 0 and '────' in lines[i-1]:
            # Merge this dash into previous line
            lines[i-1] = lines[i-1].rstrip('\n') + '─────────\n'
            lines[i] = ''  # Remove orphan line
            changes += 1
            continue

        # Fix orphan blank lines after separators (4 spaces)
        if stripped == '    ' and i > 0 and '────' in lines[i-1]:
            lines[i] = ''
            changes += 1
            continue

        # Fix "valu f(double)" -> "valueOf(double)"
        if 'valu f(double)' in stripped:
            lines[i] = line.replace('valu f(double)', 'valueOf(double)')
            changes += 1

        # Fix "Bi cimal" -> "BigDecimal"
        if 'Bi cimal' in stripped:
            lines[i] = line.replace('Bi cimal', 'BigDecimal')
            changes += 1

        # Fix "Bi  = new BigDecimal" -> "BigDecimal exact = new BigDecimal"
        if 'Bi  = new BigDecimal' in stripped:
            lines[i] = line.replace('Bi  = new BigDecimal', 'BigDecimal exact = new BigDecimal')
            changes += 1

        # Fix "CEI cale(2" in rounding demo
        if '"CEI' in stripped and 'cale(2' in stripped:
            lines[i] = '        System.out.println("CEILING:   " + val.setScale(2, RoundingMode.CEILING));   // 2.35\n'
            changes += 1

        # Fix "FLOOR:   cale(2"
        if '"FLOOR:' in stripped and 'cale(2' in stripped:
            lines[i] = '        System.out.println("FLOOR:     " + val.setScale(2, RoundingMode.FLOOR));     // 2.34\n'
            changes += 1

        # Fix 'UP:' line with 'cale(2'
        if '"UP:' in stripped and 'cale(2' in stripped:
            lines[i] = '        System.out.println("UP:        " + val.setScale(2, RoundingMode.UP));        // 2.35 (alejarse de 0)\n'
            changes += 1

        # Fix 'DOWN:' line with 'va cale'
        if '"DOWN:' in stripped and 'va cale' in stripped:
            lines[i] = '        System.out.println("DOWN:      " + val.setScale(2, RoundingMode.DOWN));      // 2.34 (hacia 0)\n'
            changes += 1

        # Fix comparison section: "em.out.println" -> "System.out.println"
        if 'em.out.println("─── Compar' in stripped:
            lines[i] = '        System.out.println("─── Comparación ───");\n'
            changes += 1

        # Fix "ecimal a = new BigDecimal" -> "BigDecimal a = new BigDecimal"
        if stripped.strip().startswith('ecimal a = new BigDecimal'):
            lines[i] = '        BigDecimal a = new BigDecimal("2.0");\n'
            changes += 1

        # Fix "ecimal b = new Bi" -> "BigDecimal b = new BigDecimal"
        if stripped.strip().startswith('ecimal b = new Bi'):
            lines[i] = '        BigDecimal b = new BigDecimal("2.00");\n'
            changes += 1

        # Fix "comp .00" -> "compareTo 2.00"
        if 'comp .00' in stripped:
            lines[i] = line.replace('comp .00', 'compareTo 2.00')
            changes += 1

    # Remove empty strings left by merged lines
    lines = [l for l in lines if l != '']

    with open(path, 'w') as f:
        f.writelines(lines)
    print(f"  Fixed {changes} issues in {path}")


def fix_math_solutions():
    path = "04-Math-BigDecimal/src/Solutions.java"
    with open(path, 'r') as f:
        content = f.read()

    correct = '''import java.math.BigDecimal;
import java.math.RoundingMode;

/** SOLUCIONES — Math y BigDecimal */
public class Solutions {

    static String sumTransactions(String[] amounts) {
        BigDecimal total = BigDecimal.ZERO;
        for (String a : amounts) total = total.add(new BigDecimal(a));
        return total.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    static String applyTax(String price, int taxPercent) {
        BigDecimal p = new BigDecimal(price);
        BigDecimal multiplier = BigDecimal.ONE.add(
            new BigDecimal(taxPercent).divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP)
        );
        return p.multiply(multiplier).setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    static int comparePrices(String price1, String price2) {
        return new BigDecimal(price1).compareTo(new BigDecimal(price2));
    }
    // ⚠️ La trampa: "2.0".equals("2.00") → false. compareTo → 0 ✅

    static String dividendPerShare(String totalAmount, int shareholders) {
        return new BigDecimal(totalAmount)
            .divide(new BigDecimal(shareholders), 2, RoundingMode.FLOOR)
            .toPlainString();
    }

    static int closestToZero(int[] temperatures) {
        if (temperatures.length == 0) return 0;
        int closest = temperatures[0];
        for (int t : temperatures) {
            int absT = Math.abs(t);
            int absClosest = Math.abs(closest);
            if (absT < absClosest || (absT == absClosest && t > 0)) {
                closest = t;
            }
        }
        return closest;
    }
    // Clave del ejercicio 5: el desempate. Si |5| == |-5|, retornamos 5 (el positivo).
    // Condición: absT == absClosest && t > 0 → el nuevo candidato es positivo → gana.

    public static void main(String[] args) {
        System.out.println(sumTransactions(new String[]{"0.1","0.2"})); // 0.30
        System.out.println(applyTax("19.99", 16));   // 23.19
        System.out.println(comparePrices("10.5","10.50")); // 0
        System.out.println(dividendPerShare("100.00", 3)); // 33.33
        System.out.println(closestToZero(new int[]{5,-5})); // 5
        System.out.println(closestToZero(new int[]{}));     // 0
    }
}
'''

    with open(path, 'w') as f:
        f.write(correct)
    print(f"  Rewrote {path} completely")


if __name__ == '__main__':
    base = "/Users/13rianvargas/Documents/GitHub/PlayGrounds/Java-PlayGround/SeniorTraining/01-Java-Core-APIs"
    os.chdir(base)

    print("=== Fixing 01-Arrays-Utility/src/Solutions.java ===")
    fix_arrays_solutions()

    print("\n=== Fixing 03-String-Manipulation/src/Solutions.java ===")
    fix_string_solutions()

    print("\n=== Fixing 04-Math-BigDecimal/src/Main.java ===")
    fix_math_main()

    print("\n=== Fixing 04-Math-BigDecimal/src/Solutions.java ===")
    fix_math_solutions()

    print("\nAll fixes applied!")
