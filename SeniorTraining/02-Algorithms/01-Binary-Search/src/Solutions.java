import java.util.Arrays;

/** SOLUCIONES — Binary Search */
public class Solutions {

    // ─── E1: mySqrt ──────────────────────────────────────────────────────
    static int mySqrt(int n) {
        if (n == 0) return 0;
        long left = 1, right = n;
        while (left < right) {
            long mid = left + (right - left + 1) / 2;  // upper mid
            if (mid * mid <= n) left = mid;
            else                right = mid - 1;
        }
        return (int) left;
    }
    // Usamos long para evitar overflow: 46341² > Integer.MAX_VALUE
        

    // right = n (no n/2), importante para mySqrt(1)
        

    // "Upper mid" garantiza convergencia cuando left + 1 == right
        

        
        
    // ─── E2: countOccurrences ────────────────────────────────────────────
    static int countOccurrences(int[] arr, int target) {
        int left = leftBound(arr, target);
        if (left == -1) return 0;
        int right = rightBound(arr, target);
        return right - left + 1;
    }

    static int leftBound(int[] arr, int target) {
        int lo = 0, hi = arr.length - 1, result = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if      (arr[mid] == target) { result = mid; hi = mid - 1; }
            else if (arr[mid] < target)  lo = mid + 1;
            else                         hi = mid - 1;
        }
            
        return result;
    }

    static int rightBound(int[] arr, int target) {
        int lo = 0, hi = arr.length - 1, result = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if      (arr[mid] == target) { result = mid; lo = mid + 1; }
            else if (arr[mid] < target)  lo = mid + 1;
            else                         hi = mid - 1;
        }
        return result;
    }

    // ─── E3: existsInRange ─────────────────────────────────────────
    static boolean existsInRange(int[] arr, int target, int from, int to) {
        return Arrays.binarySearch(arr, from, to, target) >= 0;
    }
    // Una sola línea. El conocimiento del API hace la diferencia.

    // ─── E4: searchMatrix ────────────────────────────────────────────────
    static boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length, cols = matrix[0].length;
        int lo = 0, hi = rows * cols - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int val = matrix[mid / cols][mid % cols];  // mapeo 1D → 2D
            if      (val == target) return true;
            else if (val < target)  lo = mid + 1;
            else                    hi = mid - 1;
        }
        return false;
    }
    // Elegante: trata una matriz m×n como array de m*n elementos.
    // Índice i → fila = i/cols, columna = i%cols

    // ─── E5: shipWithinDays ──────────────────────────────────────────────
    static int shipWithinDays(int[] weights, int D) {
        int lo = 0, hi = 0;
        for (int w : weights) { lo = Math.max(lo, w); hi += w; }

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (canShip(weights, D, mid)) hi = mid;
            else                          lo = mid + 1;
        }
        return lo;
    }

    static boolean canShip(int[] weights, int days, int capacity) {
        int daysNeeded = 1, load = 0;
        for (int w : weights) {
            if (load + w > capacity) { daysNeeded++; load = 0; }
            load += w;
        }
        return daysNeeded <= days;
    }

    public static void main(String[] args) {
        System.out.println(mySqrt(8));   // 2
        System.out.println(mySqrt(2147395600));  // 46340
        System.out.println(countOccurrences(new int[]{1,2,3,3,3,4,5}, 3)); // 3
        System.out.println(countOccurrences(new int[]{1,2,3,3,3,4,5}, 6)); // 0
        System.out.println(existsInRange(new int[]{1,2,3,4,5}, 3, 0, 3)); // true
        System.out.println(existsInRange(new int[]{1,2,3,4,5}, 3, 3, 5)); // false
        int[][] m = {{1,3,5,7},{10,11,16,20},{23,30,34,60}};
        System.out.println(searchMatrix(m, 3));   // true
        System.out.println(searchMatrix(m, 13));  // false
        System.out.println(shipWithinDays(new int[]{1,2,3,4,5,6,7,8,9,10}, 5)); // 15
    }
}
