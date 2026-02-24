import java.util.Arrays;

/**
 * Arrays Utility \u2014 Demostraci\u00f3n pr\u00e1ctica de todos los m\u00e9todos clave.
 *
 * Ejecutar:
 *   javac src/Main.java -d bin/
 *   java -cp bin/ Main
 */
public class Main {

    public static void main(String[] args) {
        demo_binarySearch();
        demo_sort();
        demo_copy();
        demo_fill();
        demo_stream();
        demo_comparisons();
    }

    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    // DEMO 1: binarySearch \u2014 la estrella de pruebas t\u00e9cnicas
    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    static void demo_binarySearch() {
        System.out.println("\u2500\u2500\u2500 Arrays.binarySearch \u2500\u2500\u2500");

        int[] arr = {-9, 14, 37, 102};  // \u26a0\ufe0f DEBE estar ordenado

        int found = Arrays.binarySearch(arr, 37);
        System.out.println("binarySearch(37): " + found);  // \u2192 2

        int notFound = Arrays.binarySearch(arr, 36);
        System.out.println("binarySearch(36): " + notFound);  // \u2192 negativo

        // C\u00f3mo verificar si se encontr\u00f3
        System.out.println("\u00bfEst\u00e1 37? " + (Arrays.binarySearch(arr, 37) >= 0));  // \u2192 true
        System.out.println("\u00bfEst\u00e1 36? " + (Arrays.binarySearch(arr, 36) >= 0));  // \u2192 false

        // Rango parcial: buscar en [1, 3)
        int partial = Arrays.binarySearch(arr, 1, 3, 14);
        System.out.println("Buscar 14 en rango [1,3): " + partial);  // \u2192 1

        System.out.println();
    }

    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    // DEMO 2: sort
    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    static void demo_sort() {
        System.out.println("\u2500\u2500\u2500 Arrays.sort \u2500\u2500\u2500");
        int[] arr = {5, 2, 8, 1, 9, 3};

        System.out.println("Antes:   " + Arrays.toString(arr));
        Arrays.sort(arr);
        System.out.println("Despu\u00e9s: " + Arrays.toString(arr));

        // Sort parcial: solo subarray
        int[] arr2 = {5, 2, 8, 1, 9, 3};
        Arrays.sort(arr2, 1, 4);  // ordena posiciones 1, 2, 3
        System.out.println("Sort parcial [1,4): " + Arrays.toString(arr2));

        System.out.println();
    }

    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    // DEMO 3: copyOf y copyOfRange
    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    static void demo_copy() {
        System.out.println("\u2500\u2500\u2500 Arrays.copyOf / copyOfRange \u2500\u2500\u2500");
        int[] original = {10, 20, 30, 40, 50};

        int[] longer = Arrays.copyOf(original, 8);
        System.out.println("copyOf tama\u00f1o 8: " + Arrays.toString(longer));   // padding con 0

        int[] shorter = Arrays.copyOf(original, 3);
        System.out.println("copyOf tama\u00f1o 3: " + Arrays.toString(shorter));  // truncado

        int[] slice = Arrays.copyOfRange(original, 1, 4);  // [1, 4) \u2192 \u00edndices 1, 2, 3
        System.out.println("copyOfRange [1,4): " + Arrays.toString(slice));

        System.out.println();
    }

    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    // DEMO 4: fill
    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    static void demo_fill() {
        System.out.println("\u2500\u2500\u2500 Arrays.fill \u2500\u2500\u2500");
        int[] arr = new int[6];
        Arrays.fill(arr, -1);
        System.out.println("fill -1: " + Arrays.toString(arr));

        Arrays.fill(arr, 2, 5, 99);  // llena [2, 5)
        System.out.println("fill 99 en [2,5): " + Arrays.toString(arr));

        // Inicializar tabla de DP
        int[] dp = new int[10];
        Arrays.fill(dp, Integer.MAX_VALUE);  // "no calculado"
        dp[0] = 0;
        System.out.println("DP init: " + Arrays.toString(dp));

        System.out.println();
    }

    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    // DEMO 5: stream
    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    static void demo_stream() {
        System.out.println("\u2500\u2500\u2500 Arrays.stream \u2500\u2500\u2500");
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5};

        int sum = Arrays.stream(arr).sum();
        System.out.println("Suma: " + sum);

        int max = Arrays.stream(arr).max().getAsInt();
        System.out.println("M\u00e1ximo: " + max);

        double avg = Arrays.stream(arr).average().orElse(0);
        System.out.println("Promedio: " + avg);

        long count = Arrays.stream(arr).filter(n -> n > 4).count();
        System.out.println("Elementos > 4: " + count);

        int[] sorted = Arrays.stream(arr).distinct().sorted().toArray();
        System.out.println("\u00danicos y ordenados: " + Arrays.toString(sorted));
        System.out.println();
    }

    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    // DEMO 6: equals y mismatch
    // \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
    static void demo_comparisons() {
        System.out.println("\u2500\u2500\u2500 equals / mismatch \u2500\u2500\u2500");
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};
        int[] c = {1, 2, 4};

        System.out.println("a equals b: " + Arrays.equals(a, b));  // true
        System.out.println("a equals c: " + Arrays.equals(a, c));  // false

        // mismatch: \u00edndice del primer elemento diferente (Java 9+)
        System.out.println("mismatch a,c: " + Arrays.mismatch(a, c));  // \u2192 2

        System.out.println();
    }
}
