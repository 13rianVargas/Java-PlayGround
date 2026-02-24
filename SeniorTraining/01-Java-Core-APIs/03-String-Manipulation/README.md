# 🔤 03 — String Manipulation

> **"El 30% de problemas en CodinGame involucran strings. StringBuilder no es opcional."**

---

## 🎯 Objetivo

Manipulación eficiente de cadenas con las APIs estándar de Java:

- **StringBuilder** vs concatenación — por qué importa en O(n²) vs O(n)
- **Palindromos** — técnica de dos punteros
- **Anagramas** — frequency map de caracteres
- **Parsing** — split, substring, charAt, toCharArray
- **Patrones comunes** — compresión, primer carácter único, prefijo común

---

## 📁 Archivos

| Archivo              | Descripción                                                                                    |
| -------------------- | ---------------------------------------------------------------------------------------------- |
| `src/Main.java`      | Guía teórica con demos de StringBuilder, palindromo, anagrama                                  |
| `src/Exercises.java` | 5 ejercicios: firstNonRepeating, compress, countUniqueWords, isPalindrome, longestCommonPrefix |
| `src/Solutions.java` | Soluciones anotadas con análisis de complejidad                                                |

---

## 🔑 Reglas de Oro

1. **Nunca concatenes strings en un loop** — usa `StringBuilder`
2. **`toCharArray()`** es tu mejor amigo para iterar
3. **Anagrama = mismos caracteres, distinto orden** → compara frequency maps
4. **Palindromo = se lee igual al derecho y al revés** → dos punteros desde los extremos

---

## 🛠️ Cómo ejecutar

```bash
javac src/*.java -d bin/
java -cp bin/ Main
```
