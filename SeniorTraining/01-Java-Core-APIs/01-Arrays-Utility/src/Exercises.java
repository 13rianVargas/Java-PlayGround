import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * ══════════════════════════════════════════════════════════
 * EJERCICIOS — Arrays Utility
 * ══════════════════════════════════════════════════════════
 *
 * Instrucciones:
 * 1. Implementa cada método con el cuerpo indicado.
 * 2. Usa el main() para probar tu implementación.
 * 3. Para cada ejercicio hay una PISTA marcada con 💡.
 * 4. Solo mira Solutions.java cuando hayas intentado seriamente.
 *
 * Formato idéntico al de CodinGame/HackerRank:
 * - clase con métodos estáticos
 * - firma del método ya dada, tú implementas el cuerpo
 */
public class Exercises {

    // ───────────────────────────────────────────────────────────────────
    // EJERCICIO 1 — Copia de la prueba real
    //
    // Contexto: Este es EXACTAMENTE el tipo de pregunta que viste en la imagen.
    //
    // "The aim of this exercise is to check whether a number exists
    // in an array of 1 to 1 million items."
    //
    // Implementa el método exists() de dos formas:
    // a) existsLinear — fuerza bruta con for loop → O(n)
    // b) existsBinary — usando Arrays.binarySearch → O(log n)
    //
    // Luego responde: ¿Cuál usarías en producción y por qué?
    // ───────────────────────────────────────────────────────────────────

    /**
     * Versión LINEAL — O(n). Fuerza bruta.
     * 💡 Pista: recorre el array con un for loop básico.
     *
     * @param ints array de enteros en ORDEN ASCENDENTE, nunca null
     * @param k    número a buscar
     * @return true si k existe en ints, false en caso contrario
     */
    static boolean existsLinear(int[] ints, int k) {
        for (int i = 0; i < ints.length; i++) {
            if (ints[i] == k) {
                return true;
            }
        }
        return false;
    }

    /**
     * Versión BINARIA — O(log n). La solución Senior.
     * 💡 Pista: Arrays.binarySearch(array, clave) retorna >= 0 si se encontró.
     * El array ya está ordenado (lo garantiza el enunciado).
     *
     * @param ints array de enteros en ORDEN ASCENDENTE, nunca null
     * @param k    número a buscar
     * @return true si k existe en ints, false en caso contrario
     */
    static boolean existsBinary(int[] ints, int k) {
        return Arrays.binarySearch(ints, k) >= 0 ? true : false;
    }

    // ───────────────────────────────────────────────────────────────────
    // EJERCICIO 2 — Top-K elementos más grandes
    //
    // Dado un array de enteros y un número k, retorna los k elementos
    // más grandes del array en ORDEN DESCENDENTE.
    //
    // 💡 Pista: Ordena el array. Usa Arrays.copyOfRange para extraer
    // los últimos k elementos. Luego inviértelos.
    // O usa Arrays.sort + un loop desde el final.
    // ───────────────────────────────────────────────────────────────────

    /**
     * @param arr array de enteros (puede tener duplicados)
     * @param k   cuántos elementos retornar
     * @return array de los k mayores, en orden descendente
     */
    static int[] topK(int[] arr, int k) {
        // 1. Lo ordeno
        Arrays.sort(arr);

        // 2. Creo Array de tamaño k
        int[] newArr = new int[k];

        // 3. Lleno newArr con los últimos k elementos de arr, en orden inverso
        for (int i = 0; i < k; i++) {
            // Ejemplo si arr= { 3, 1, 4, 1, 5, 9, 2, 6 } y k= 3
            // newArry sorted = { 1, 1, 2, 3, 4, 5, 6, 9 }
            newArr[i] = arr[arr.length
                    // Valor: 8 constantemente
                    - 1
                    // Valor: 7 constantemente (index)
                    - i
            // Index: 0, luego 1, luego 2
            ];
            // Resultado: 8-1-0=7 → 9, luego 8-1-1=6 → 6, luego 8-1-2=5 → 5
            // El resultado es el index del último elemento, luego el penúltimo, luego el
            // antepenúltimo del array ordenado.
        }
        return newArr;
    }

    // ───────────────────────────────────────────────────────────────────
    // EJERCICIO 3 — Primera posición de inserción
    //
    // Dado un array ORDENADO y un valor target que NO está en el array,
    // retorna el índice donde se debería insertar para mantener el orden.
    //
    // Ejemplo: [1, 3, 5, 6], target=2 → 1 (va entre 1 y 3)
    // Ejemplo: [1, 3, 5, 6], target=7 → 4 (va al final)
    // Ejemplo: [1], target=0 → 0 (va al inicio)
    //
    // 💡 Pista: Arrays.binarySearch retorna -(insertion_point) - 1 cuando
    // no encuentra el elemento. Despeja insertion_point.
    // insertion_point = -(resultado) - 1 → -(resultado + 1)
    // ───────────────────────────────────────────────────────────────────

    /**
     * @param arr    array de enteros ordenados, sin duplicados
     * @param target valor a insertar (NO está en arr)
     * @return índice donde insertar para mantener orden ascendente
     */
    static int insertionPosition(int[] arr, int target) {
        int ans = Arrays.binarySearch(arr, target);
        return ans < 0 ? -(ans + 1) : ans;
    }

    // ───────────────────────────────────────────────────────────────────
    // EJERCICIO 4 — Mediana de un array
    //
    // Dado un array de enteros (puede estar desordenado), retorna la mediana.
    // - Si el array tiene tamaño impar, retorna el elemento del medio.
    // - Si el tamaño es par, retorna el promedio de los dos del medio.
    //
    // Ejemplo: [3, 1, 4, 1, 5] → ordena → [1, 1, 3, 4, 5] → mediana = 3.0
    // Ejemplo: [3, 1, 4, 2] → ordena → [1, 2, 3, 4] → mediana = 2.5
    //
    // 💡 Pista: Ordena una COPIA del array (no el original con copyOf).
    // Luego accede a los elementos del medio.
    // IMPORTANTE: No modifiques el array original.
    // ───────────────────────────────────────────────────────────────────

    /**
     * @param arr array de enteros, no null, no vacío
     * @return la mediana como double
     */
    static double median(int[] arr) {
        // TODO: implementar
        return 0.0;
    }

    // ───────────────────────────────────────────────────────────────────
    // EJERCICIO 5 — Combinar dos arrays ordenados (Merge)
    //
    // Dados dos arrays ORDENADOS a[] y b[], retorna un nuevo array que
    // contiene todos los elementos de ambos, también ordenado.
    //
    // Ejemplo: [1, 3, 5] y [2, 4, 6] → [1, 2, 3, 4, 5, 6]
    // Ejemplo: [1, 1, 3] y [2, 3, 5] → [1, 1, 2, 3, 3, 5]
    //
    // 💡 Pista SIMPLE: concatena ambos arrays en uno nuevo, luego sort.
    // ¿Conoces System.arraycopy?
    // Pista AVANZADA (Senior): Two pointers O(n+m) sin sort adicional.
    // Intenta ambas. Compara su complejidad.
    // ───────────────────────────────────────────────────────────────────

    /**
     * @param a primer array ordenado
     * @param b segundo array ordenado
     * @return nuevo array con todos los elementos de a y b, ordenado
     */
    static int[] mergeSorted(int[] a, int[] b) {
        // TODO: implementar
        return new int[0];
    }

    // ───────────────────────────────────────────────────────────────────
    // MAIN — Para probar tus implementaciones
    // ───────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("═══ EJERCICIO 1: exists ═══");
        int[] sorted = { -9, 14, 37, 102 };
        System.out.println("existsLinear(102):  " + existsLinear(sorted, 102)); // true
        System.out.println("existsLinear(36):   " + existsLinear(sorted, 36)); // false
        System.out.println("existsBinary(102):  " + existsBinary(sorted, 102)); // true
        System.out.println("existsBinary(36):   " + existsBinary(sorted, 36)); // false
        System.out.println("existsBinary([]):   " + existsBinary(new int[0], 5)); // false

        System.out.println("\n═══ EJERCICIO 2: topK ═══");
        System.out.println(Arrays.toString(topK(new int[] { 3, 1, 4, 1, 5, 9, 2, 6 }, 3))); // [9, 6, 5]
        System.out.println(Arrays.toString(topK(new int[] { 1 }, 1))); // [1]

        System.out.println("\n═══ EJERCICIO 3: insertionPosition ═══");
        System.out.println(insertionPosition(new int[] { 1, 3, 5, 6 }, 2)); // 1
        System.out.println(insertionPosition(new int[] { 1, 3, 5, 6 }, 7)); // 4
        System.out.println(insertionPosition(new int[] { 1, 3, 5, 6 }, 0)); // 0

        System.out.println("\n═══ EJERCICIO 4: median ═══");
        System.out.println(median(new int[] { 3, 1, 4, 1, 5 })); // 3.0
        System.out.println(median(new int[] { 3, 1, 4, 2 })); // 2.5
        System.out.println(median(new int[] { 7 })); // 7.0

        System.out.println("\n═══ EJERCICIO 5: mergeSorted ═══");
        System.out.println(Arrays.toString(mergeSorted(new int[] { 1, 3, 5 }, new int[] { 2, 4, 6 }))); // [1, 2, 3, 4,
                                                                                                        // 5, 6]
        System.out.println(Arrays.toString(mergeSorted(new int[] { 1, 1, 3 }, new int[] { 2, 3, 5 }))); // [1, 1, 2, 3,
                                                                                                        // 3, 5]
        System.out.println(Arrays.toString(mergeSorted(new int[0], new int[] { 1, 2 }))); // [1, 2]
    }
}
