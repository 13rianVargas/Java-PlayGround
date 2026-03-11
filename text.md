# Guión y Plan de Ruta — Video Explicativo del Gestor de Proyectos

> **Duración objetivo:** máximo 10 minutos  
> **Formato sugerido:** compartir pantalla mostrando el código y el navegador

---

## Plan de Ruta (orden de presentación)

| #  | Tema                        | Archivo(s)            | Duración aprox. |
|----|-----------------------------|-----------------------|-----------------|
| 1  | Introducción                | Navegador             | 0:30            |
| 2  | HTML: DOCTYPE y estructura  | `index.html`          | 1:30            |
| 3  | HTML: Etiquetas y atributos | `index.html`          | 1:30            |
| 4  | HTML: DOM y Templates       | `index.html`          | 1:00            |
| 5  | CSS: Reglas y selectores    | `css/styles.css`      | 1:30            |
| 6  | CSS: Propiedades de estilo  | `css/styles.css`      | 1:00            |
| 7  | JS: Funciones y eventos     | `funciones.js`        | 1:30            |
| 8  | JS: LocalStorage y CRUD     | `funciones.js`        | 1:00            |
| 9  | Demo en vivo                | Navegador             | 0:30            |

---

## 1. Introducción (0:30)

**Qué decir:**

"Hola, en este video voy a explicar el sitio web que construí: un **Gestor de Proyectos** que permite crear proyectos, asignarles miembros y gestionar tareas con responsables. Está hecho únicamente con **HTML, CSS y JavaScript puro**, sin frameworks, y usa **LocalStorage** para guardar los datos en el navegador."

- Mostrar rápidamente la app funcionando en el navegador (crear un proyecto, agregar miembro, agregar tarea).

---

## 2. HTML: DOCTYPE y Estructura del Documento (1:30)

**Archivo:** `index.html` — líneas 1–9

**Qué decir:**

- **`<!doctype html>`**: "Esta declaración le indica al navegador que el documento usa **HTML5**. No es una etiqueta, es una instrucción que debe ir siempre en la primera línea."

- **`<html lang="es">`**: "La etiqueta raíz que envuelve todo el documento. El **atributo `lang="es"`** indica que el contenido está en español — esto ayuda a motores de búsqueda y lectores de pantalla."

- **`<head>`**: "El `head` contiene **metadatos** del documento, cosas que el usuario no ve directamente:"
  - **`<meta charset="UTF-8">`**: "Define la codificación de caracteres. UTF-8 permite usar tildes, ñ y caracteres especiales."
  - **`<meta name="viewport" ...>`**: "Hace que la página sea **responsive**, se adapte al ancho del dispositivo."
  - **`<title>`**: "El texto que aparece en la pestaña del navegador."
  - **`<link rel="stylesheet" href="css/styles.css">`**: "Vincula la **hoja de estilos CSS** externa. El atributo `rel` indica la relación (stylesheet) y `href` la ruta del archivo."

- **`<body>`**: "Contiene todo el contenido visible de la página."

---

## 3. HTML: Etiquetas y Atributos (1:30)

**Archivo:** `index.html` — líneas 11–29

**Qué decir:**

- **Etiquetas**: "Una etiqueta HTML define un elemento en la página. Tienen apertura `<tag>` y cierre `</tag>`. Algunas son auto-cerradas como `<meta />` e `<input />`."

- Señalar estas etiquetas en el código:
  - **`<header>`**: "Etiqueta **semántica** que define el encabezado de la página."
  - **`<h1>`**: "Encabezado de nivel 1, el título principal."
  - **`<main>`**: "Etiqueta semántica para el contenido principal."
  - **`<div>`**: "Contenedor genérico, muy usado para agrupar elementos."
  - **`<input>`**: "Campo de entrada de texto. Es auto-cerrada."
  - **`<button>`**: "Botón interactivo."

- **Atributos**: "Los atributos dan información extra a las etiquetas. Van dentro de la etiqueta de apertura."
  - **`type="text"`**: "Define que el input es de tipo texto."
  - **`id="nombreProyecto"`**: "Identificador **único** para acceder desde JS."
  - **`placeholder="..."`**: "Texto de ayuda que aparece cuando el campo está vacío."
  - **`class="form-crear"`**: "Asigna una **clase CSS** para aplicar estilos. A diferencia del `id`, las clases se pueden repetir."

- **Comentarios HTML**: "Se escriben `<!-- texto -->`. Sirven para documentar el código y no se muestran en la página."

---

## 4. HTML: El DOM y Templates (1:00)

**Archivo:** `index.html` — líneas 32–96

**Qué decir:**

- **DOM (Document Object Model)**: "Cuando el navegador lee el HTML, crea una estructura en forma de **árbol** llamada DOM. Cada etiqueta se convierte en un **nodo** que JavaScript puede leer y modificar. Por ejemplo, cuando creamos un proyecto, JS accede al DOM para agregar nuevos elementos a la página sin recargarla."

- **`<template>`**: "Es una etiqueta especial de HTML5. Su contenido **no se muestra** en la página, funciona como un **molde reutilizable**. Desde JS lo clonamos con `cloneNode()` cada vez que necesitamos crear una card de proyecto, un miembro o una tarea."

- Señalar los 3 templates:
  - `template-proyecto`: molde de la card completa.
  - `template-miembro`: molde de un item de miembro.
  - `template-tarea`: molde de un item de tarea.

- **`<script src="funciones.js">`**: "Vincula el archivo JavaScript. Se coloca al final del `body` para que el HTML ya esté cargado cuando se ejecute."

---

## 5. CSS: Reglas de Estilo y Selectores (1:30)

**Archivo:** `css/styles.css`

**Qué decir:**

- **¿Qué es CSS?**: "CSS (Cascading Style Sheets) define la apariencia visual de la página. Se escribe en un archivo externo vinculado al HTML."

- **Regla de estilo**: "Una regla tiene un **selector** y un bloque de **declaraciones** entre llaves `{}`."

- **Tipos de selectores** — señalar cada uno en el archivo:
  - **Selector universal `*`**: "Selecciona **todos** los elementos. Aquí lo usamos para resetear márgenes y padding."
  - **Selector de etiqueta `body`**: "Selecciona todos los elementos `<body>`. Aplica a la etiqueta directamente."
  - **Selector de clase `.form-crear`**: "Selecciona elementos con `class="form-crear"`. Se usa con un punto delante. Es el selector más común."
  - **Selector de ID (mencionarlo)**: "Se escribiría `#nombreProyecto` con un `#`. Selecciona un elemento único por su `id`. No lo usamos en CSS aquí pero sí en JS con `getElementById()`."
  - **Selector de atributo `input[type="checkbox"]`**: "Selecciona inputs que tengan el atributo `type` con valor `checkbox`."
  - **Selector descendiente `.card-titulo h3`**: "Selecciona los `h3` que estén **dentro** de un elemento con clase `card-titulo`."
  - **Pseudo-clase `.btn-eliminar:hover`**: "Aplica estilos cuando el cursor pasa por encima del elemento."

---

## 6. CSS: Propiedades de Estilo Importantes (1:00)

**Archivo:** `css/styles.css`

**Qué decir:**

Recorrer rápidamente las propiedades más relevantes:

- **Layout**:
  - `display: flex` → activa **Flexbox** para distribuir elementos en fila o columna.
  - `gap: 10px` → espacio entre elementos flex.
  - `justify-content`, `align-items` → alineación en ejes horizontal y vertical.
  - `flex: 1` → el elemento crece para ocupar el espacio disponible.

- **Box Model**:
  - `margin` → espacio exterior.
  - `padding` → espacio interior.
  - `border` → borde del elemento.
  - `box-sizing: border-box` → hace que padding y border se incluyan dentro del tamaño total (el `*` al inicio).

- **Visual**:
  - `background` → color de fondo.
  - `color` → color del texto.
  - `border-radius` → esquinas redondeadas.
  - `box-shadow` → sombra del elemento.
  - `font-size`, `font-weight`, `font-family` → tipografía.

- **Interactividad**:
  - `cursor: pointer` → muestra manita al pasar el mouse.
  - `transition: background 0.2s` → anima el cambio de color al hover.
  - `text-decoration: line-through` → tacha el texto (tareas completadas).
  - `accent-color` → cambia el color del checkbox.

---

## 7. JavaScript: Funciones y Eventos (1:30)

**Archivo:** `funciones.js` — líneas 1–20 y función `cargarProyectos()`

**Qué decir:**

- **¿Qué es JavaScript?**: "JS le da **interactividad** a la página. Permite responder a acciones del usuario, modificar el DOM y guardar datos."

- **Funciones**: "Una función es un bloque de código reutilizable. Se declara con `function nombre() {}` y se ejecuta llamandola con `nombre()`."
  - Mostrar las funciones principales: `agregarProyecto()`, `eliminarProyecto()`, `cargarProyectos()`, etc.

- **Eventos**: "Los eventos son acciones del usuario (clic, tecla, carga de página). Usamos `addEventListener()` para escucharlos."
  - `DOMContentLoaded` → se dispara cuando el HTML termina de cargar.
  - `click` → cuando se hace clic en un botón.
  - `keydown` → cuando se presiona una tecla (usamos Enter).
  - `onchange` → cuando cambia el estado del checkbox.
  - `onclick` → forma directa de asignar evento de clic.

- **Métodos del DOM usados**:
  - `document.getElementById()` → busca por ID.
  - `document.querySelector()` → busca por selector CSS.
  - `document.createElement()` → crea un nuevo elemento.
  - `.appendChild()` → agrega un hijo al final.
  - `.cloneNode(true)` → clona un template.
  - `.textContent` → cambia el texto de un elemento.
  - `.classList.add()` → agrega una clase CSS.
  - `.innerHTML` → reemplaza todo el contenido HTML.

---

## 8. JavaScript: LocalStorage y CRUD (1:00)

**Archivo:** `funciones.js` — funciones de LocalStorage y CRUD

**Qué decir:**

- **LocalStorage**: "Es un almacenamiento del navegador que persiste incluso al cerrar la pestaña. Solo guarda texto (strings), por eso usamos:"
  - `JSON.stringify()` → convierte objetos JS a texto para guardar.
  - `JSON.parse()` → convierte texto de vuelta a objetos JS para leer.
  - `localStorage.setItem("clave", valor)` → guardar.
  - `localStorage.getItem("clave")` → leer.

- **CRUD**: "Es el patrón de operaciones básicas sobre datos:"
  - **Create (Crear)**: `agregarProyecto()`, `agregarMiembro()`, `agregarTarea()` — usan `.push()` para añadir al arreglo.
  - **Read (Leer)**: `obtenerProyectos()`, `cargarProyectos()` — leen y muestran los datos.
  - **Update (Actualizar)**: `toggleTarea()` — usa `!` (negación) para invertir el estado completada/no completada.
  - **Delete (Eliminar)**: `eliminarProyecto()`, `eliminarMiembro()`, `eliminarTarea()` — usan `.splice(posición, 1)` para remover del arreglo.

- **Métodos de arreglo usados**:
  - `.push()` → agrega al final.
  - `.splice()` → elimina en una posición.
  - `.forEach()` → recorre cada elemento.

---

## 9. Demo en Vivo (0:30)

**Qué hacer:**

1. Crear un proyecto "Tarea de Nuevas Tecnologías".
2. Agregar 2 miembros.
3. Agregar 2 tareas asignadas a los miembros.
4. Marcar una tarea como completada (checkbox).
5. Recargar la página para demostrar que **los datos persisten** gracias a LocalStorage.
6. Eliminar un miembro y mostrar que su tarea queda "Sin asignar".

"Y así se demuestra que el sitio es completamente funcional usando solo HTML, CSS y JavaScript puro."

---

## Glosario Rápido (referencia)

| Concepto              | Definición breve                                                    |
|-----------------------|---------------------------------------------------------------------|
| **HTML**              | Lenguaje de marcado que estructura el contenido de la página        |
| **DOCTYPE**           | Declaración que indica al navegador que use HTML5                   |
| **Etiqueta**          | Elemento HTML (`<div>`, `<h1>`, `<input>`, etc.)                    |
| **Atributo**          | Info adicional en una etiqueta (`id`, `class`, `type`, `src`, etc.) |
| **Comentario HTML**   | `<!-- texto -->` — no se muestra en la página                       |
| **DOM**               | Representación en árbol del HTML que JS puede manipular             |
| **CSS**               | Lenguaje que define la apariencia visual de la página               |
| **Regla de estilo**   | Selector + bloque de declaraciones `{ propiedad: valor; }`         |
| **Selector**          | Patrón que indica a qué elementos aplicar estilos                   |
| **Propiedad CSS**     | Aspecto visual a modificar (`color`, `padding`, `display`, etc.)    |
| **JavaScript**        | Lenguaje de programación que da interactividad a la página          |
| **Función**           | Bloque de código reutilizable declarado con `function`              |
| **Evento**            | Acción del usuario o del navegador (click, keydown, load, etc.)     |
| **LocalStorage**      | Almacenamiento local del navegador, persiste entre sesiones         |
| **JSON**              | Formato de texto para representar datos (stringify/parse)           |
| **CRUD**              | Create, Read, Update, Delete — operaciones básicas sobre datos      |
| **Template HTML**     | Molde reutilizable cuyo contenido no se renderiza hasta clonarlo    |
