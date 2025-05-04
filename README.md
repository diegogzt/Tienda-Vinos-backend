# 🍷 Sistema de Gestión de Bodega "Celler La Bona Estrella" 🍷

[![Estado: En Desarrollo](https://img.shields.io/badge/Estado-En%20Desarrollo-brightgreen)](https://github.com/diegogzt/Tienda-Vinos-backend)
[![Java](https://img.shields.io/badge/Java-11%2B-orange)](https://www.java.com/)

## 📝 Descripción del Proyecto

Este sistema ha sido desarrollado para gestionar el inventario de vinos de la bodega "Celler La Bona Estrella". La propietaria necesitaba una solución que le permitiera catalogar, buscar y gestionar su creciente colección de vinos de manera eficiente y sencilla.

El sistema implementa una interfaz por consola que permite realizar operaciones básicas sobre la colección de vinos, incluyendo:

- Búsqueda avanzada por múltiples criterios
- Catálogo completo con detalles técnicos
- Persistencia de datos en formato CSV
- Gestión de inventario

## 🏗️ Arquitectura del Sistema

El sistema se basa en una arquitectura orientada a objetos simple pero robusta, con tres componentes principales:

### 📊 Diagrama de Clases

```
+-------------+       +----------------+       +--------------+
|    Entorn   | ----> |     Botiga     | ----> |      Vi      |
| (Interfaz)  |       | (Repositorio)  |       |   (Modelo)   |
+-------------+       +----------------+       +--------------+
```

### 🧩 Componentes Principales

#### 🍷 Clase `Vi`
Representa un vino individual con todas sus propiedades:
- **Datos básicos**: nombre, precio, stock
- **Identificación**: referencia única
- **Características**: lugar, denominación de origen, tipo, cosecha
- **Validación**: verifica integridad de los datos
- **Serialización**: conversión desde/hacia formato CSV

#### 🏪 Clase `Botiga`
Gestiona la colección de vinos y las operaciones sobre ella:
- Almacenamiento de vinos en memoria
- Búsqueda por referencia o criterios múltiples
- Navegación secuencial por el catálogo
- Operaciones CRUD (Crear, Leer, Actualizar, Eliminar)

#### 💻 Clase `Entorn`
Implementa la interfaz de usuario y el ciclo principal:
- Menú interactivo por consola
- Procesamiento de comandos
- Carga/guardado de datos desde/hacia CSV
- Gestión de entrada/salida

## 🛠️ Funcionalidades Implementadas

### 📋 Gestión de Vinos
- ✅ Creación de nuevos registros de vinos
- ✅ Búsqueda por referencia exacta
- ✅ Búsqueda avanzada por múltiples criterios
- ✅ Visualización detallada de información
- ⏸️ Modificación de datos (implementado pero desactivado temporalmente)
- ⏸️ Eliminación de vinos (implementado pero desactivado temporalmente)

### 💾 Persistencia de Datos
- ✅ Carga automática desde archivo CSV al iniciar
- ✅ Normalización de datos para garantizar consistencia
- ✅ Serialización/deserialización de objetos a formato CSV
- ✅ Manejo de errores en datos corruptos o incompletos

### 🔍 Sistema de Búsqueda Avanzada
- ✅ Buscar por referencia exacta
- ✅ Filtrado por nombre
- ✅ Filtrado por precio máximo
- ✅ Filtrado por stock mínimo
- ✅ Filtrado por lugar de almacenamiento
- ✅ Filtrado por denominación de origen
- ✅ Filtrado por tipo de vino
- ✅ Filtrado por año de cosecha
- ✅ Combinación de criterios múltiples

## 💡 Características Técnicas Destacables

### 🧰 Normalización de Datos
El sistema implementa un procesamiento robusto para normalizar cadenas:
- Elimina espacios redundantes al inicio/fin
- Reduce múltiples espacios a uno solo
- Maneja correctamente casos nulos o vacíos

### 🔐 Validación Integral
Cada objeto `Vi` implementa validación completa:
- Verifica que todos los campos tengan valores válidos
- Comprueba que los valores numéricos estén en rangos aceptables
- Detecta datos malformados o inconsistentes

### 📊 Modelo de Datos Flexible
La clase `Vi` ha evolucionado para almacenar datos completos:
- De simples 3 atributos iniciales (nombre, precio, stock)
- A 8 atributos detallados con la versión ampliada
- Manteniendo compatibilidad hacia atrás

### 🔄 Recorrido de Colección
Implementa un mecanismo simple pero efectivo:
```java
botiga.iniciaRecorregut();
while (true) {
    Vi vi = botiga.getSeguent();
    if (vi == null) break;
    System.out.println(vi);
}
```

## 📁 Estructura del Proyecto

```
tienda-vinos-backend/
│
├── README.md            # Esta documentación
├── Botiga.java          # Gestión de la colección de vinos
├── Entorn.java          # Interfaz de usuario y ciclo principal
├── EsEnter.java         # Utilidad para validar números enteros
├── Vi.java              # Modelo de datos para un vino
│
└── botiga.csv           # Archivo de persistencia (generado en ejecución)
```

## 🚀 Guía de Uso

### 🔧 Requisitos
- Java JDK 11 o superior
- Compilador de Java (javac)

### ⚙️ Compilación
Para compilar el proyecto, ejecute:
```bash
javac *.java
```

### ▶️ Ejecución
Para iniciar la aplicación:
```bash
java Entorn
```

### 📖 Comandos Disponibles
Una vez ejecutando, el sistema mostrará un prompt `botiga>` y aceptará los siguientes comandos:

| Comando | Descripción | Estado |
|---------|-------------|--------|
| `ajuda` | Muestra la lista de comandos disponibles | ✅ Activo |
| `cerca` | Busca vinos por referencia o criterios múltiples | ✅ Activo |
| `afegeix` | Añade un nuevo vino (temporalmente desactivado) | ⏸️ Desactivado |
| `modifica` | Modifica un vino existente (temporalmente desactivado) | ⏸️ Desactivado |
| `elimina` | Elimina un vino (temporalmente desactivado) | ⏸️ Desactivado |
| `surt` | Sale de la aplicación | ✅ Activo |

### 🔍 Ejemplo de Búsqueda
Para buscar un vino, puede hacerlo por referencia directa:
```
botiga> cerca
ref> ROURABLA20200232
```

O mediante búsqueda por criterios múltiples:
```
botiga> cerca
ref> 
nom> roura blanc
preu max.> 2000
estoc min.> 10
lloc> 
D.O.> alella
tipus> blanc
collita> 2020
```

## 📝 Detalles de Implementación

### 📋 Formato del CSV

El archivo CSV utiliza punto y coma (`;`) como separador y no incluye cabeceras:
```
ROURABLA20200232;Roura blanc;1012;42;P21E45N55E;Alella;blanc;2020
CERCIUMX20170002;Cercium;565;30;P23E01N55D;Empordà;negre;2017
MASIASER20200001;Masia Serra;1350;12;P02E02N55E;Empordà;blanc;2020
```

### 🧠 Lógica de Búsqueda Avanzada

La implementación de búsqueda utiliza una "plantilla" (objeto `Vi` parcialmente completo):
- Propiedades con valor `null` o vacío actúan como comodines
- Precio se interpreta como precio máximo
- Stock se interpreta como stock mínimo
- Los campos textuales se comparan ignorando mayúsculas/minúsculas

```java
public Vi cerca(Vi plantilla) {
    for (Vi vi : vins) {
        if (vi == null) continue;
        
        // Comparar cada propiedad según las reglas específicas...
        // (Ver implementación completa en Botiga.java)
        
        return vi; // Vino que cumple todos los criterios
    }
    return null; // No hay coincidencias
}
```

### 🧪 Validación de Vinos

La validación de un vino se implementa en el método `esValid()`:

```java
public boolean esValid() {
    // Verificar que ningún campo textual sea nulo
    if (ref == null || nom == null || lloc == null || origen == null || 
        tipus == null || collita == null) {
        return false;
    }
    
    // Verificar que ningún campo textual esté vacío
    if (ref.isBlank() || nom.isBlank() || lloc.isBlank() ||
        origen.isBlank() || tipus.isBlank() || collita.isBlank()) {
        return false;
    }
    
    // Verificar valores numéricos válidos
    if (preu < 0 || estoc < 0) {
        return false;
    }
    
    return true;
}
```

## 📋 Historial de Desarrollo

El sistema ha evolucionado a través de varias etapas:

1. **Fase 1**: Implementación básica de la clase `Vi` con atributos nombre, precio y stock
2. **Fase 2**: Desarrollo de la clase `Botiga` para gestionar la colección
3. **Fase 3**: Creación de la interfaz de usuario `Entorn`
4. **Fase 4**: Implementación de persistencia en CSV
5. **Fase 5**: Ampliación del modelo con nuevos atributos y mejora de búsqueda

Cada fase ha añadido nuevas funcionalidades manteniendo la compatibilidad con las anteriores.

## 🚀 Planes Futuros

Algunas mejoras previstas para próximas versiones:

- [ ] Reactivar las funciones de modificación y eliminación
- [ ] Mejorar el sistema de visualización con opciones de formato
- [ ] Implementar filtros de búsqueda más avanzados
- [ ] Añadir estadísticas y reportes sobre el inventario
- [ ] Mejorar la interfaz de usuario con colores y formatos

## 👨‍💻 Autor

Este proyecto ha sido desarrollado por Diego Gabriel Zaldivar Tovar (@diegogzt) como parte de los ejercicios del curso "Introducció a la programació amb Java" (2024-2025).

---

## 📚 Glosario

- **Botiga**: Tienda/Bodega en catalán
- **Vi**: Vino en catalán
- **Entorn**: Entorno/Ambiente en catalán
- **D.O.**: Denominación de Origen

---

💡 **Nota**: Este sistema ha sido diseñado con un enfoque educativo, centrándose en la aplicación de conceptos de programación orientada a objetos y gestión de datos.