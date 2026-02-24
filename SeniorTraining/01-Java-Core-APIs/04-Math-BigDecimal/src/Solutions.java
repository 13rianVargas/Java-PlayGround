import java.math.BigDecimal;
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
