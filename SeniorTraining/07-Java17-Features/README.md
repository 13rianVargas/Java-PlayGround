# Módulo 07 — Java 17 Features

> Java 17 (LTS, 2021) introduce características que los Senior deben conocer bien.
> Son evaluadas directamente en CodinGame/HackerRank y en entrevistas.

## Características principales

| Feature                       | Cuándo usarla                              |
| ----------------------------- | ------------------------------------------ |
| Records                       | DTOs inmutables, resultados de métodos     |
| Sealed Classes                | Jerarquías de tipos cerradas y exhaustivas |
| Text Blocks                   | SQL, JSON, XML embebidos en código         |
| Switch Expressions            | switch como expresión, sin fall-through    |
| Pattern Matching `instanceof` | Elimina casteos redundantes                |

## Estructura

```
07-Java17-Features/
├── 01-Records/src/Main.java
├── 02-SealedClasses/src/Main.java
├── 03-TextBlocks/src/Main.java
└── 04-SwitchExpressions/src/Main.java
```

## Por qué importa en el sector financiero

- **Records**: Representar transacciones, cuentas, eventos de dominio como objetos inmutables
- **Sealed Classes**: Tipos de transacción fijos (CREDIT, DEBIT, TRANSFER) con exhaustividad garantizada en switch
- **Text Blocks**: SQL embebido sin escapados manuales
- **Switch Expressions**: Reemplazar if-else chains complejos en procesadores de eventos
