/**
 * ============================================================
 *  05 — RECURSIÓN y BACKTRACKING
 * ============================================================
 *
 *  CONCEPTO: Dividir un problema en subproblemas IDÉNTICOS más
 *  pequeños. Cada llamada recursiva resuelve un caso más pequeño
 *  hasta llegar al caso base.
 *
 *  CUÁNDO USARLO:
 *  - "Todas las combinaciones/permutaciones"
 *  - "Generar todos los subconjuntos"
 *  - Estructuras de árbol/grafo
 *  - "Divide y vencerás"
 *
 *  CUÁNDO NO:
 *  - Si hay subproblemas repetidos → usa DP (Módulo 06)
 *
 *  ============================================================
 */

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== RECURSIÓN y BACKTRACKING ===\n");

        // ───────────────────────────────────
        // 1) Recursión básica — Factorial
        // ───────────────────────────────────
        System.out.println("--- 1) Factorial ---");

        System.out.println("5! = " + factorial(5));      // 120
        System.out.println("10! = " + factorial(10));    // 3628800

        // La estructura es siempre:
        // 1. Caso base (condición de parada)
        // 2. Caso recursivo (reducir el problema)


        // ───────────────────────────────────
        // 2) Fibonacci (recursivo, ineficiente)
        // ───────────────────────────────────
        System.out.println("\n--- 2) Fibonacci (naive) ---");
        System.out.println("fib(10) = " + fibonacci(10)); // 55

        // ⚠️ Este es O(2^n) — lo correcto es DP o iterativo
        // Lo mostramos para entender por qué DP existe


        // ─────────────────────────────────
        // 3) Generar todas las permutaciones
        // ─────────────────────────────────
        System.out.println("\n--- 3) Permutaciones de [1, 2, 3] ---");
        List<List<Integer>> perms = new ArrayList<>();
        permute(new int[]{1, 2, 3}, 0, perms);
        perms.forEach(System.out::println);
        // [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,2,1], [3,1,2]


        // ─────────────────────────────────
        // 4) Subconjuntos (Power Set)
        // ─────────────────────────────────
        System.out.println("\n--- 4) Subconjuntos de [1, 2, 3] ---");
        List<List<Integer>> subsets = new ArrayList<>();
        generateSubsets(new int[]{1, 2, 3}, 0, new ArrayList<>(), subsets);
        subsets.forEach(System.out::println);
        // [], [1], [1,2], [1,2,3], [1,3], [2], [2,3], [3]


        // ─────────────────────────────────
        // 5) Backtracking — Combinaciones que suman K
        // ─────────────────────────────────
        System.out.println("\n--- 5) Combinaciones que suman 7 con [2,3,6,7] ---");
        List<List<Integer>> combos = new ArrayList<>();
        combinationSum(new int[]{2, 3, 6, 7}, 7, 0, new ArrayList<>(), combos);
        combos.forEach(System.out::println);
        // [2,2,3], [7]

                
        // ─────────────────────────────────
        // RESUMEN
        // ─────────────────────────────────
        System.out.println("\n=== RESUMEN RECURSIÓN ===");
        System.out.println("• Siempre define CASO BASE primero (evita stack overflow)");
        System.out.println("• Permutaciones: swap + recurse + swap back");
        System.out.println("• Subconjuntos: incluir/excluir cada elemento");
        System.out.println("• Backtracking: podar ramas inválidas early");
        System.out.println("• Si hay subproblemas repetidos → pasar a DP");
        System.out.println("• Complejidad típica: O(2^n) subconjuntos, O(n!) permutaciones");
    }

    // ─── Helpers ───

    static long factorial(int n) {
        if (n <= 1) return 1;           // caso base
        return n * factorial(n - 1);    // caso recursivo
    }

    static int fibonacci(int n) {
        if (n <= 1) return n;           // caso base
        return fibonacci(n - 1) + fibonacci(n - 2); // ⚠️ O(2^n)
    }

    static void permute(int[] arr, int start, List<List<Integer>> result) {
        if (start == arr.length) {
            List<Integer> perm = new ArrayList<>();
            for (int v : arr) perm.add(v);
            result.add(perm);
            return;
        }

        for (int i = start; i < arr.length; i++) {
            swap(arr, start, i);         // elegir
            permute(arr, start + 1, result); // explorar
            swap(arr, start, i);         // deshacer (backtrack)
        }
    }

    static void swap(int[] arr, int i, int j) {
        int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
    }

    static void generateSubsets(int[] arr, int idx, List<Integer> current,
                                List<List<Integer>> result) {
        result.add(new ArrayList<>(current)); // agregar copia actual

        for (int i = idx; i < arr.length; i++) {
            current.add(arr[i]);                        // incluir
            generateSubsets(arr, i + 1, current, result); // explorar
            current.remove(current.size() - 1);         // excluir (backtrack)
        }
    }

    static void combinationSum(int[] candidates, int target, int start,
                                List<Integer> current, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        if (target < 0) return; // poda

        for (int i = start; i < candidates.length; i++) {
            current.add(candidates[i]);
            // i (no i+1) porque se puede reutilizar el mismo número
            combinationSum(candidates, target - candidates[i], i, current, result);
            current.remove(current.size() - 1); // backtrack
        }
    }
}
