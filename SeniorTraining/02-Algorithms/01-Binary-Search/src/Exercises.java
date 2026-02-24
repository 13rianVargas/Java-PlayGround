import java.util.Arrays;

/**
 * EJERCICIOS — Binary Search
 * 5 ejercicios graduados del básico al avanzado.
 */
public class Exercises {

    // ─────────────────────────────────────────────────────────────────────
    // E1 — Encontrar la raíz cuadrada entera (√n)
    //
    // Dado un entero no negativo n, retorna la parte entera de su raíz.
    // Ejemplo: n=8  → 2  (porque 2² = 4 ≤ 8 < 9 = 3²)
    // Ejemplo: n=4  → 2
    
    // Ejemplo: n=0  → 0
    // Ejemplo: n=1  → 1
    
    //
    // ⚠️ NO usar Math.sqrt(). Impleméntalo con binary search.
             el rango [0, n].
            // ───────────────────────────────
            
        // TODO
        

        return 0;
        

        
        

        
        
    // ─────────────────────────────────────────────────────────────────────
    // E2 — Contar ocurrencias en array ordenado
    //
    // Dado un array ORDENADO y un target, retorna cuántas veces aparece.
    // Ejemplo: [1,2,3,3,3,4,5], target=3 → 3
    // Ejemplo: [1,2,3,3,3,4,5], target=6 → 0
    //
    // ⏱️ Debe ser O(log n) — usar variantes leftBound y rightBound.
    // 💡 count = rightBound(arr, target) - leftBound(arr, target) + 1
    //    (cuando el target existe)
    // ─────────────────────────────────────────────────────────────────────
    static int countOccurrences(int[] arr, int target) {
        // TODO
        return 0;
    }

    // ─────────────────────────────────────────────────────────────────────
            
    //
    // Dado un array ORDENADO, un target, y un rango [from, to) (exclusivo),
    // retorna true si target existe dentro del rango especificado.
    //
    // Ejemplo: arr=[1,2,3,4,5], target=3, from=0, to=3 → true  (índice 2 está en [0,3))
    // Ejemplo: arr=[1,2,3,4,5], target=3, from=3, to=5 → false (índice 2 NO está en [3,5))
    //
    // 💡 Arrays.binarySearch tiene una sobrecarga: binarySearch(arr, from, to, key)
    // ─────────────── ───────────────────────────────────────
    static boolean existsInRange(i ] arr, int target, int from, int to) {
     * 
        // TODO 
        return false; 
    }

    // ─────────────────────────────────────────────────────────────────────
    // E4 — Buscar en matriz ordenada por filas
    //
    // Dada una matriz donde cada fila está ordenada ascendentemente
    // y el primer elemento de cada fila es mayor que el último de la anterior,
    // retorna true si target existe en la matriz.
    //
    // Ejemplo:
    //   [[1,  3,  5,  7 ],
    //    [10, 11, 16, 20],
    
    //    [23, 30, 34, 60]]
    //   target=3  → true
    //   target=13 → false
    //
    // ⏱️ Debe ser O(log(m*n))
    // 💡 Trata la matriz como un array 1D de m*n elementos.
    //    Índice 1D i → fila = i/cols, col = i%cols
    // ─────────────────────────────────────────────────────────────────────
    static boolean searchMatrix(int[][] matrix, int target) {
        // TODO
        return false;
    }

    // ─────────────────────────────────────────────────────────────────────
    // E5 — Capacidad mínima de camión
    //
    // Tienes weights[] (pesos de paquetes en orden) y D días para entregarlos.
    // Las entregas son consecutivas: no puedes reordenarlas.
    // Cada día puedes cargar paquetes consecutivos mientras no excedan la capacidad.
    // Retorna la MÍNIMA capacidad necesaria para entregar todo en D días.
    //
    // Ejemplo: weights=[1,2,3,4,5,6,7,8,9,10], D=5 → 15
    // Ejemplo: weights=[3,2,2,4,1,4], D=3 → 6
    //
    // 💡 Este es "Binary Search en la respuesta" (más difícil).
    //    Rango: [max(weights), sum(weights)]
    //    Pregunta: ¿Puedo entregar todo en D días si la capacidad es C?
    //    → simula la entrega, cuenta días necesarios.
    // ─────────────────────────────────────────────────────────────────────
    static int shipWithinDays(int[] weights, int D) {
        // TODO
        return 0;
    }

    // ─────────────────────────────────────────────────────────────────────
    // MAIN
    // ─────────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("═══ E1: mySqrt ═══");
        System.out.println(mySqrt(8));   // 2
        System.out.println(mySqrt(4));   // 2
        System.out.println(mySqrt(0));   // 0
        System.out.println(mySqrt(1));   // 1
        System.out.println(mySqrt(2147395600));  // edge: near Integer.MAX_VALUE

        System.out.println("\n═══ E2: countOccurrences ═══");
        System.out.println(countOccurrences(new int[]{1,2,3,3,3,4,5}, 3));  // 3
        System.out.println(countOccurrences(new int[]{1,2,3,3,3,4,5}, 6));  // 0
        System.out.println(countOccurrences(new int[]{5,5,5,5,5}, 5));      // 5

        System.out.println("\n═══ E3: existsInRange ═══");
        System.out.println(existsInRange(new int[]{1,2,3,4,5}, 3, 0, 3));  // true
        System.out.println(existsInRange(new int[]{1,2,3,4,5}, 3, 3, 5));  // false

        System.out.println("\n═══ E4: searchMatrix ═══");
        int[][] m = {{1,3,5,7},{10,11,16,20},{23,30,34,60}};
        System.out.println(searchMatrix(m, 3));   // true
        System.out.println(searchMatrix(m, 13));  // false

        System.out.println("\n═══ E5: shipWithinDays ═══");
        System.out.println(shipWithinDays(new int[]{1,2,3,4,5,6,7,8,9,10}, 5)); // 15
        System.out.println(shipWithinDays(new int[]{3,2,2,4,1,4}, 3));           // 6
    }
}
