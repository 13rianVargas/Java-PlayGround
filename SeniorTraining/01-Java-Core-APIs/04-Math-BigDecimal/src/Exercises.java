import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

/**
 * EJERCICIOS — Math y BigDecimal
 * Todos los ejercicios monetarios requieren BigDecimal. Nunca double.
 */
public class Exercises {

    // ─────────────────────────────────────────────────────────────────────
    // EJERCICIO 1 — Suma de transacciones financieras
    //
    // Dado un array de montos como Strings, retorna la suma total
    // redondeada a 2 decimales (HALF_UP).
    //
    // Ejemplo: ["100.50", "200.25", "50.33"] → "351.08"
    // Ejemplo: ["0.1", "0.2"]               → "0.30"
    //
    // 💡 Pista: convierte cada monto a BigDecimal con new BigDecimal(String), súmalos, aplica setScale(2, HALF_UP)
    // ─────────────────────────────────────────────────────────────────────
    static String sumTransactions(String[] amounts) {
        // TODO
        return "0.00";
    }

    // ─────────────────────────────────────────────────────────────────────
    // EJERCICIO 2 — Cálculo de IVA
    //
    // Dado un precio base (String) y un porcentaje de IVA (int, ej: 16 para 16%),
    // retorna el precio final con IVA, redondeado a 2 decimales (HALF_UP).
    //
    // Ejemplo: "100.00", 16 → "116.00"
    // Ejemplo: "19.99", 16  → "23.19"
    // Ejemplo: "1.00", 7    → "1.07"
    //
    // 💡 Pista: iva = precio.multiply(new BigDecimal(pct)).divide(BigDecimal.TEN.pow(2), ...)
    //           O más limpio: precio.multiply(BigDecimal.valueOf(1 + pct/100.0))
    //           Recuerda: divide requiere escala y RoundingMode
    // ─────────────────────────────────────────────────────────────────────
    static String applyTax(String price, int taxPercent) {
        // TODO
        return "0.00";
    }

    // ─────────────────────────────────────────────────────────────────────
    // EJERCICIO 3 — Comparador de precios
    //
    // Dados dos precios como Strings, retorna:
    //   -1 si price1 < price2
    //    0 si son iguales (aunque tengan diferente escala: "2.0" == "2.00")
    //    1 si price1 > price2
    //
    // Ejemplo: "10.5" vs "10.50" → 0
    // Ejemplo: "9.99" vs "10.00"   → -1
    // Ejemplo: "100.01" vs "100"   → 1
    //
    // 💡 Pista: BigDecimal.compareTo() — NO usar .equals()
    // ─────────────────────────────────────────────────────────────────────
    static int comparePrices(String price1, String price2) {
        // TODO
        return 0;
    }

    // ─────────────────────────────────────────────────────────────────────
    // EJERCICIO 4 — Distribución de dividendos
    //
    // Una empresa distribuye un monto total (String) entre N accionistas
    // de forma equitativa. Retorna cuánto recibe cada uno, redondeado
    // hacia abajo (FLOOR) con 2 decimales. El excedente se pierde.
    //
    // Ejemplo: "100.00" ÷ 3 → "33.33" (cada uno)
    // Ejemplo: "10.00"  ÷ 3 → "3.33"
    // Ejemplo: "1.00"   ÷ 4 → "0.25"
    //
    // 💡 Pista: divide(new BigDecimal(n), 2, RoundingMode.FLOOR)
    // ─────────────────────────────────────────────────────────────────────
    static String dividendPerShare(String totalAmount, int shareholders) {
        // TODO
        return "0.00";
    }

    // ─────────────────────────────────────────────────────────────────────
    // EJERCICIO 5 — Temperatura más cercana a cero
    //
    // Dado un array de enteros (pueden ser negativos) que representan
    // temperaturas, retorna la temperatura más cercana a 0.
    // Si hay empate (e.g. 5 y -5), retorna el positivo.
    // Si el array está vacío, retorna 0.
    //
    // Ejemplo: [1, -2, -8, 4, 5]  → 1
    // Ejemplo: [-2, -8, 4, 5]     → -2
    // Ejemplo: [5, -5]            → 5
    // Ejemplo: []                 → 0
    //
    // 💡 Pista: Math.abs(). Itera comparando distancias absolutas.
    //           Para el desempate: si |a| == |b|, prefiere el positivo.
    //           Es un ejercicio clásico de CodinGame (Temperature puzzle).
    // ─────────────────────────────────────────────────────────────────────
    static int closestToZero(int[] temperatures) {
        // TODO
        return 0;
    }

    // ─────────────────────────────────────────────────────────────────────
    // MAIN
    // ─────────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("═══ Ejercicio 1: sumTransactions ═══");
        System.out.println(sumTransactions(new String[]{"100.50", "200.25", "50.33"})); // 351.08
        System.out.println(sumTransactions(new String[]{"0.1", "0.2"}));                // 0.30

        System.out.println("\n═══ Ejercicio 2: applyTax ═══");
        System.out.println(applyTax("100.00", 16));  // 116.00
        System.out.println(applyTax("19.99", 16));   // 23.19

        System.out.println("\n═══ Ejercicio 3: comparePrices ═══");
        System.out.println(comparePrices("10.5", "10.50")); // 0
        System.out.println(comparePrices("9.99", "10.00")); // -1
        System.out.println(comparePrices("100.01", "100")); // 1

        System.out.println("\n═══ Ejercicio 4: dividendPerShare ═══");
        System.out.println(dividendPerShare("100.00", 3)); // 33.33
        System.out.println(dividendPerShare("10.00", 3));  // 3.33

        System.out.println("\n═══ Ejercicio 5: closestToZero ═══");
        System.out.println(closestToZero(new int[]{1, -2, -8, 4, 5})); // 1
        System.out.println(closestToZero(new int[]{5, -5}));            // 5
        System.out.println(closestToZero(new int[]{}));                  // 0
    }
}
