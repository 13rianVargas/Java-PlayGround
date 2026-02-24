import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * BigDecimal \u2014 Precisi\u00f3n num\u00e9rica para aplicaciones financieras.
 *
 * En el sector financiero, NUNCA uses double/float para dinero.
 * Este m\u00f3dulo explica por qu\u00e9 y c\u00f3mo usar BigDecimal correctamente.
 */
public class Main {

    public static void main(String[] args) {
        demo_why_not_double();
        demo_creation();
        demo_arithmetic();
        demo_rounding();
        demo_comparison();
        demo_financial_example();
    }

    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    // DEMO 1: Por qu\u00e9 NO usar double para dinero
    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    static void demo_why_not_double() {
        System.out.println("\u2500\u2500\u2500 \u00bfPor qu\u00e9 no double? \u2500\u2500\u2500");

        // \u274c Representaci\u00f3n binaria de punto flotante es INEXACTA
        double a = 0.1 + 0.2;
        System.out.println("0.1 + 0.2 con double: " + a);          // 0.30000000000000004
        System.out.println("0.1 + 0.2 == 0.3: " + (a == 0.3));    // false \u274c

        // \u2705 BigDecimal = precisi\u00f3n exacta
        BigDecimal ba = new BigDecimal("0.1");
        BigDecimal bb = new BigDecimal("0.2");
        BigDecimal bc = ba.add(bb);
        System.out.println("0.1 + 0.2 con BigDecimal: " + bc);    // 0.3

        // El error se acumula en finanzas:
        double balance = 1000.00;
        for (int i = 0; i < 1000; i++) balance += 0.1;
        System.out.printf("1000 + 0.1\u00d71000 con double: %.10f%n", balance);
        System.out.println("1000 + 0.1\u00d71000 con BigDecimal: " +
            new BigDecimal("1000.00").add(new BigDecimal("0.1").multiply(new BigDecimal("1000"))));
        System.out.println();
    }

    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    // DEMO 2: Formas correctas e incorrectas de crear BigDecimal
    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    static void demo_creation() {
        System.out.println("\u2500\u2500\u2500 Crear BigDecimal \u2500\u2500\u2500");

        // \u2705 CORRECTO: desde String
        BigDecimal price = new BigDecimal("19.99");
        System.out.println("Desde String: " + price);

        // \u2705 CORRECTO: desde int/long
        BigDecimal amount = new BigDecimal(100);
        System.out.println("Desde int: " + amount);

        // \u2705 CORRECTO: valueOf(double) \u2014 convierte a String internamente
        BigDecimal ok = BigDecimal.valueOf(19.99);
        System.out.println("valueOf(19.99): " + ok);

        // \u274c INCORRECTO: desde double directo
        BigDecimal bad = new BigDecimal(0.1);
        System.out.println("new BigDecimal(0.1):   " + bad);          // 0.1000000000000000055511...

        // Constantes \u00fatiles
        System.out.println("ZERO: " + BigDecimal.ZERO);    // 0
        System.out.println("ONE:  " + BigDecimal.ONE);     // 1
        System.out.println("TEN:  " + BigDecimal.TEN);     // 10
        System.out.println();
    }

    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    // DEMO 3: Operaciones aritm\u00e9ticas
    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    static void demo_arithmetic() {
        System.out.println("\u2500\u2500\u2500 Aritm\u00e9tica \u2500\u2500\u2500");
        BigDecimal a = new BigDecimal("100.50");
        BigDecimal b = new BigDecimal("30.25");

        System.out.println("Suma:         " + a.add(b));           // 130.75
        System.out.println("Resta:        " + a.subtract(b));      // 70.25
        System.out.println("Multiplicar:  " + a.multiply(b));      // 3040.1250
        System.out.println("Dividir:      " + a.divide(b, 4, RoundingMode.HALF_UP)); // 3.3223
        System.out.println("Potencia:     " + a.pow(2));           // 10100.2500
        System.out.println("Negativo:     " + a.negate());         // -100.50
        System.out.println("Abs:          " + a.negate().abs());   // 100.50

        // resultado exacto (cuando es posible)
        BigDecimal exact = new BigDecimal("10").divide(new BigDecimal("4")); // 2.5
        // \u26a0\ufe0f SIEMPRE especifica escala y RoundingMode para evitar ArithmeticException
        System.out.println();
    }

    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    // DEMO 4: Redondeo \u2014 cr\u00edtico en finanzas
    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    static void demo_rounding() {
        System.out.println("\u2500\u2500\u2500 Redondeo \u2500\u2500\u2500");
        BigDecimal val = new BigDecimal("2.345");

        // RoundingMode.HALF_UP: el m\u00e1s com\u00fan en finanzas (2.345 \u2192 2.35)
        System.out.println("HALF_UP:   " + val.setScale(2, RoundingMode.HALF_UP));   // 2.35
        System.out.println("HALF_DOWN: " + val.setScale(2, RoundingMode.HALF_DOWN)); // 2.34
        System.out.println("HALF_EVEN: " + val.setScale(2, RoundingMode.HALF_EVEN)); // 2.34 (banker's rounding)
        System.out.println("CEILING:   " + val.setScale(2, RoundingMode.CEILING));   // 2.35
        System.out.println("FLOOR:     " + val.setScale(2, RoundingMode.FLOOR));     // 2.34
        System.out.println("UP:        " + val.setScale(2, RoundingMode.UP));        // 2.35 (alejarse de 0)
        System.out.println("DOWN:      " + val.setScale(2, RoundingMode.DOWN));      // 2.34 (hacia 0)

        // stripTrailingZeros: elimina ceros finales
        System.out.println("2.5000 stripped: " + new BigDecimal("2.5000").stripTrailingZeros()); // 2.5
        System.out.println();
    }

    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    // DEMO 5: Comparaci\u00f3n \u2014 la trampa m\u00e1s com\u00fan
    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    static void demo_comparison() {
        System.out.println("\u2500\u2500\u2500 Comparaci\u00f3n \u2500\u2500\u2500");
        BigDecimal a = new BigDecimal("2.0");
        BigDecimal b = new BigDecimal("2.00");
        // \u26a0\ufe0f equals compara VALOR Y ESCALA \u2192 2.0 != 2.00
        System.out.println("2.0 equals 2.00:    " + a.equals(b));          // false \u274c
        // \u2705 compareTo compara solo el VALOR \u2192 2.0 == 2.00
        System.out.println("2.0 compareTo 2.00: " + (a.compareTo(b) == 0)); // true \u2705

        // Operadores de comparaci\u00f3n
        BigDecimal x = new BigDecimal("5.5");
        BigDecimal y = new BigDecimal("3.3");
        System.out.println("x > y:  " + (x.compareTo(y) > 0));   // true
        System.out.println("x < y:  " + (x.compareTo(y) < 0));   // false
        System.out.println("x == y: " + (x.compareTo(y) == 0));  // false
        System.out.println();
    }

    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    // DEMO 6: Ejemplo financiero \u2014 c\u00e1lculo de inter\u00e9s compuesto
    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    static void demo_financial_example() {
        System.out.println("\u2500\u2500\u2500 Inter\u00e9s Compuesto \u2500\u2500\u2500");
        // F\u00f3rmula: A = P * (1 + r/n)^(nt)
        // P = capital inicial, r = tasa anual, n = periodos/a\u00f1o, t = a\u00f1os

        BigDecimal principal = new BigDecimal("10000.00");  // $10,000
        BigDecimal annualRate = new BigDecimal("0.05");     // 5% anual
        int periods = 12;    // mensual
        int years = 5;

        BigDecimal rate = annualRate.divide(new BigDecimal(periods), 10, RoundingMode.HALF_UP);
        BigDecimal base = BigDecimal.ONE.add(rate);
        BigDecimal factor = base.pow(periods * years);
        BigDecimal amount = principal.multiply(factor).setScale(2, RoundingMode.HALF_UP);

        System.out.println("Capital inicial: $" + principal);
        System.out.println("Tasa anual:      " + annualRate.multiply(new BigDecimal("100")) + "%");
        System.out.println("Per\u00edodo:         " + years + " a\u00f1os");
        System.out.println("Monto final:     $" + amount);
        System.out.println("Inter\u00e9s ganado:  $" + amount.subtract(principal).setScale(2, RoundingMode.HALF_UP));
    }
}
