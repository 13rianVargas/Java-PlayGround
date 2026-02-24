/**
 * ============================================================
 *  01 — BINARY SEARCH
 * ============================================================
 *
 *  CONCEPTO: Dividir el espacio de búsqueda a la mitad en cada paso.
 *  VARIANTES: clásica, left bound, right bound, search-on-answer.
 *
 *  COMPLEJIDAD: O(log n)
 *
 *  ============================================================
 */

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        demo_classic();
        demo_leftBound();
        demo_rightBound();
        demo_insertionPoint();
        demo_searchOnAnswer();
    }

    // ─────────────────────────────────────────────────────────────────────
    // VARIANTE 1: Búsqueda clásica — ¿existe el elemento?
    // ─────────────────────────────────────────────────────────────────────
    static void demo_classic() {
        System.out.println("─── Classic Binary Search ───");
        int[] arr = {-9, 14, 37, 37, 37, 102, 200};

        System.out.println("binarySearch(37): " + Arrays.binarySearch(arr, 37));
        System.out.println("binarySearch(36): " + Arrays.binarySearch(arr, 36));

        // Implementación manual
        System.out.println("binarySearch manual(37): " + binarySearch(arr, 37));
        System.out.println();
    }

    static boolean binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if      (arr[mid] == target) return true;
            else if (arr[mid] < target)  left = mid + 1;
            else                         right = mid - 1;
        }
        return false;
    }

    // ─────────────────────────────────────────────────────────────────────
    // VARIANTE 2: Primera ocurrencia (Left Bound)
    // Cuando hay duplicados, retorna el PRIMER índice donde aparece
    // ─────────────────────────────────────────────────────────────────────
    static void demo_leftBound() {
        System.out.println("─── Left Bound (primera ocurrencia) ───");
        int[] arr = {1, 2, 3, 3, 3, 4, 5};

        System.out.println("Primera ocurrencia de 3: " + leftBound(arr, 3));  // 2
        System.out.println("Primera ocurrencia de 7: " + leftBound(arr, 7));  // -1
        System.out.println();
    }

    static int leftBound(int[] arr, int target) {
        int left = 0, right = arr.length - 1, result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                result = mid;
                right = mid - 1;    // ← sigue buscando hacia la izquierda
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    // VARIANTE 3: Última ocurrencia (Right Bound)
    // ─────────────────────────────────────────────────────────────────────
    static void demo_rightBound() {
        System.out.println("─── Right Bound (última ocurrencia) ───");
        int[] arr = {1, 2, 3, 3, 3, 4, 5};

        System.out.println("Última ocurrencia de 3: " + rightBound(arr, 3));  // 4
        System.out.println("Cuenta de 3: " + (rightBound(arr, 3) - leftBound(arr, 3) + 1)); // 3
        System.out.println();
    }

    static int rightBound(int[] arr, int target) {
        int left = 0, right = arr.length - 1, result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                result = mid;
                left = mid + 1;    // ← sigue buscando hacia la derecha
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    // ─────────────────────────────────────────────────────────────────────
    // VARIANTE 4: Posición de inserción
    // ─────────────────────────────────────────────────────────────────────
    static void demo_insertionPoint() {
        System.out.println("─── Insertion Point ───");
        int[] arr = {1, 3, 5, 6};
        System.out.println("Insertar 2: posición " + insertionPoint(arr, 2)); // 1
        System.out.println("Insertar 7: posición " + insertionPoint(arr, 7)); // 4
        System.out.println("Insertar 0: posición " + insertionPoint(arr, 0)); // 0
        System.out.println("Insertar 5: posición " + insertionPoint(arr, 5)); // 2 (o lugar del existente)
        System.out.println(); 
    }

    static int insertionPoint(int[] arr, int target) {
        // Si el target no existe: la posición correcta de inserción es `left` al final del loop
        int left = 0, right = arr.length;
        while (left < right) {             // ← nota: right = arr.length (no -1)
            int mid = left + (right - left) / 2;
            if (arr[mid] < target) left = mid + 1;
            else                   right = mid;
        }
        return left;
    }

    // ────────────────────────────────────────────────────────────────────
    // VARIANTE 5: "Binary Search en la Respuesta" (avanzado)
    // No buscas en el array, buscas el VALOR mínimo que satisface condición.
    //
    // Ejemplo: ¿Cuál es el número mínimo de páginas para leer todos los libros
    // en K días, si cada día lees máximo X páginas?
    // ─────────────────────────────────────────────────────────────────────
    static void demo_searchOnAnswer() {
        System.out.println("─── Binary Search en la Respuesta ───");
        // Problema: Tienes [10, 20, 30, 40] libros con esas páginas.
        // K=2 días. ¿Cuántas páginas mínimas necesitas leer por día para terminarlos?
        int[] books = {10, 20, 30, 40};
        int days = 2;
        System.out.println("Mínimo de páginas/día: " + minPagesPerDay(books, days)); // 60
        System.out.println();
    }

    static int minPagesPerDay(int[] books, int days) {
        // El rango de respuesta posible: [max(books), sum(books)]
        int lo = 0, hi = 0;
        for (int b : books) { lo = Math.max(lo, b); hi += b; }

        // Binary search en ese rango
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (canFinish(books, days, mid)) hi = mid;   // puede → intenta menos
            else                              lo = mid + 1; // no puede → necesita más
        }
        return lo;
    }

    static boolean canFinish(int[] books, int days, int maxPerDay) {
        int daysNeeded = 1, pagesRead = 0;
        for (int b : books) {
            if (pagesRead + b > maxPerDay) { daysNeeded++; pagesRead = 0; }
            pagesRead += b;
        }
        return daysNeeded <= days;
    }
}
