# 📦 Módulo 01 — Java Core APIs

> **"Lo que no sabías que Java ya tenía resuelto"**

Las pruebas Senior asumen que conoces la JDK. No te piden reinventar la rueda — te piden saber cuándo **ya existe una rueda que gira en O(log n) en lugar de O(n)**.

---

## 🎯 Objetivo del Módulo

Al finalizar este módulo conocerás y podrás aplicar correctamente:

- `Arrays` utility class: sort, binarySearch, copyOf, stream
- Collections avanzadas: `TreeMap`, `PriorityQueue`, `Deque`, `LinkedHashMap`
- `String` y `StringBuilder`: manipulación eficiente
- `BigDecimal`: manejo preciso de dinero (crítico en finanzas)

**Tiempo estimado:** 3–4 horas

---

## 📁 Estructura

```
01-Java-Core-APIs/
├── README.md                ← este archivo
├── 01-Arrays-Utility/       ← Arrays.binarySearch y más
│   ├── README.md
│   └── src/
│       ├── Main.java        ← ejemplos teóricos corribles
│       ├── Exercises.java   ← ejercicios que debes completar
│       └── Solutions.java   ← soluciones (mira DESPUÉS de intentarlo)
├── 02-Collections-Deep/     ← HashMap, TreeMap, PriorityQueue, Deque
│   ├── README.md
│   └── src/
│       ├── Main.java
│       ├── Exercises.java
│       └── Solutions.java
├── 03-String-Manipulation/  ← StringBuilder, regex, parsing
│   ├── README.md
│   └── src/
│       ├── Main.java
│       ├── Exercises.java
│       └── Solutions.java
└── 04-Math-BigDecimal/      ← precisión financiera
    ├── README.md
    └── src/
        ├── Main.java
        ├── Exercises.java
        └── Solutions.java
```

---

## 🔑 Concepto Clave del Módulo

> El candidato **Mid** escribe un `for` loop para buscar un elemento en un array.  
> El candidato **Senior** mira si el array está ordenado y usa `Arrays.binarySearch` → O(log n) vs O(n).  
> La diferencia en un array de 1 millón de elementos: ~500,000 operaciones vs ~20.

---

## 📋 Orden de Estudio

1. Lee el `README.md` de cada sub-módulo
2. Estudia el `Main.java` (ejecútalo y observa la salida)
3. Intenta completar los ejercicios en `Exercises.java`
4. Solo cuando estés satisfecho, compara con `Solutions.java`

---

## 🛠️ Cómo ejecutar

```bash
cd 01-Arrays-Utility/
javac src/*.java -d bin/
java -cp bin/ Main
```
