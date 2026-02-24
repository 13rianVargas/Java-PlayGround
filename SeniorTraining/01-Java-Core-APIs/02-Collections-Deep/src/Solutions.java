import java.util.*;
import java.util.stream.Collectors;

/** SOLUCIONES — Collections Deep */
public class Solutions {

    // ─── Sol 1 ───────────────────────────────────────────────────────────
    static char firstUniqueChar(String s) {
        // LinkedHashMap preserva orden de inserción → iteración = orden del string
        Map<Character, Integer> freq = new LinkedHashMap<>();
    

        for (char c : s.toCharArray()) freq.merge(c, 1, Integer::sum);
        for (Map.Entry<Character, Integer> e : freq.entrySet())
            if (e.getValue() == 1) return e.getKey();
        throw new IllegalArgumentException("No unique character found");
    }
    // O(n) pero con constante baja (solo 26 letras minúsculas del alfabeto)
    // LinkedHashMap clave: sin él, habría que recorrer el string de
    // nuevo para preservar el orden original.

    // ─── Sol 2 ───────────────────────────────────────────────────────────
    static List<String> topKFrequent(List<String> words, int k) {
        Map<String, Long> freq = words.stream()
            .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
        return freq.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                .thenComparing(Map.Entry.comparingByKey()))
            .limit(k)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
    // O(n log n) por el sort

    // ─── Sol 3 ───────────────────────────────────────────────────────────
    static List<String> studentsInRange(Map<String, Integer> scores, int min, int max) {
        // Invertir map: puntaje → nombre. Usar TreeMap para orden automático.
        // Para manejar empates de puntaje: TreeMap<Integer, List<String>>
        TreeMap<Integer, List<String>> byScore = new TreeMap<>();
        for (Map.Entry<String, Integer> e : scores.entrySet()) {
            byScore.computeIfAbsent(e.getValue(), v -> new ArrayList<>()).add(e.getKey());
        }
        // subMap inclusive en ambos extremos
        List<String> result = new ArrayList<>();
        byScore.subMap(min, true, max, true)
               .descendingMap()
               .forEach((score, names) -> result.addAll(names));
        return result;
    }
    // O(n log n) por TreeMap

    // ─── Sol 4 ───────────────────────────────────────────────────────────
    static boolean isBalanced(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if (c == ')' && top != '(') return false;
                if (c == ']' && top != '[') return false;
                if (c == '}' && top != '{') return false;
            }
        }
        return stack.isEmpty();  // debe quedar vacía si todo está balanceado
    }
    // O(n) tiempo, O(n) espacio

    // ─── Sol 5 ───────────────────────────────────────────────────────────
    static class MedianFinder {
        private final PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        private final PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        void addNumber(int n) {
            // 1. Siempre agregar al maxHeap primero
            maxHeap.offer(n);
            // 2. Balancear: el máximo del maxHeap debe ser <= mínimo del minHeap
            minHeap.offer(maxHeap.poll());
            // 3. Mantener maxHeap con tamaño igual o +1 que minHeap
            if (minHeap.size() > maxHeap.size()) maxHeap.offer(minHeap.poll());
        }

        double getMedian() {
            if (maxHeap.size() > minHeap.size()) return maxHeap.peek();
            return (maxHeap.peek() + (double) minHeap.peek()) / 2.0;
        }
    }
    // addNumber: O(log n), getMedian: O(1)
    // invariante: maxHeap.size() == minHeap.size() O maxHeap.size() == minHeap.size() + 1

    public static void main(String[] args) {
        System.out.println(firstUniqueChar("aabbcdeef")); // c
        System.out.println(topKFrequent(Arrays.asList("apple","banana","apple","cherry","banana","apple"), 2));
        Map<String, Integer> s = Map.of("Alice",85,"Bob",92,"Charlie",78,"Diana",95,"Eve",68);
        System.out.println(studentsInRange(s, 80, 93)); // [Bob, Alice]
        System.out.println(isBalanced("()[]{}")); // true
        System.out.println(isBalanced("([)]")); // false
        MedianFinder mf = new MedianFinder();
        mf.addNumber(1); System.out.println(mf.getMedian()); // 1.0
        mf.addNumber(2); System.out.println(mf.getMedian()); // 1.5
        mf.addNumber(3); System.out.println(mf.getMedian()); // 2.0
    }
}
