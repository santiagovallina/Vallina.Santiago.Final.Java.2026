# Sistema de Gestión de Vehiculos

### Sobre mi
Soy Santiago Julián Vallina y tengo 25 años.
##### Datos de Alumno: 
- DNI: 42.375.241
- Legajo: 115717

# Resumen del proyecto
Se basa en un sistema de gestión de vehículos con interfaz gráfica con **JavaFX**
Cuenta con una base principal llamada **Vehiculo**, de la cual surgen tres hijos: **Auto, Moto y Camión**. 
El **GestorVehiculo** administra una lista de vehículos y ofrece funcionalidades de tipo CRUD:
- Crear
- Eliminar
- Actualizar
- Leer
- Ordenar y filtrar
- Persistencia de datos


# Interfaz gráfica con JavaFX
La interfaz gráfica utiliza los métodos del Vehículo y del gestor para mostrar, modificar, eliminar y exportar los datos.

## Página de Bienvenida
Al abrir la app, muestra un mensaje de bienvenida y un boton que al presionarlo, lleva a la página principal.

![Inicio](https://github.com/santiagovallina/Vallina.Santiago.Final.Java.2026/blob/main/img/Captura%20de%20pantalla%202026-02-11%20092837.png)

## Página principal
Al presionar el botón de la página de bienvenida, lleva a la página principal, donde se encuentra la tabla y todas las funcionalidades que se pueden hacer con ella.

### Funcionalidades disponibles
- Buscar por patente
- Agregar 
- Eliminar
- Actualizar
- Leer
- Ordenar por patente, precio y año de manera ascendente y descendente
- Modificar el precio de todas la lista en un porccentaje a elegir, ya sea un aumento o un descuento
- Exportar lista a archivo CSV y binario

![](https://github.com/santiagovallina/Vallina.Santiago.Final.Java.2026/blob/main/img/Captura%20de%20pantalla%202026-02-11%20095424.png)

### Formularios

### Formulario Agregar
Al presionar "Agregar vehículo", nos lleva a un formulario donde pide los datos básicos de cualquier vehículo y los específicos según sea auto, moto o camión

![](https://github.com/santiagovallina/Vallina.Santiago.Final.Java.2026/blob/main/img/Captura%20de%20pantalla%202026-02-11%20095459.png)

### Formulario actualizar 
Al presionar "actualizar seleccionado", nos muestra el formulario donde muestra los datos ya cargados de este vehículo para modificar lo necesario.

![](https://github.com/santiagovallina/Vallina.Santiago.Final.Java.2026/blob/main/img/Captura%20de%20pantalla%202026-02-11%20095543.png)

## Diagrama UML

![](https://github.com/santiagovallina/Vallina.Santiago.Final.Java.2026/blob/main/img/Captura%20de%20pantalla%202026-02-11%20164529.png)

## Archivos generados
Se guardan en distintos archivos de las siguientes maneras:

#### JSON
```json
{
    "tipo": "Auto",
    "puertas": 5,
    "motor": 1.6,
    "patente": "ZAE321",
    "modelo": 2020,
    "marca": "Chevrolet",
    "color": "AZUL",
    "precio": 6600000.000000001
  },
```


#### CSV 
    patente,modelo,marca,color,precio
    AUTO, ZAE321, 2020, Chevrolet, AZUL, 6600000.0
    AUTO, ABC123, 2020, Toyota, ROJO, 5500000.0
    AUTO, DEF456, 2019, Ford, AZUL, 4950000.0
    AUTO, GHI789, 2021, Fiat, VERDE, 4180000.00
    MOTO, RTE123, 2021, Honda, VERDE, 1980000.02

#### TXT


    Listado de Vehículos filtrados
    Tipo seleccionado: Auto
    ====================================
    Tipo: Auto
    Patente: ZAE321
    Modelo: 2020
    Marca: Chevrolet
    Color: AZUL
    Precio: 6600000.0
    Puertas: 5
    Motor: 1.6
    ------------------------------
    Tipo: Auto
    Patente: ABC123
    Modelo: 2020
    Marca: Toyota
    Color: ROJO
    Precio: 5500000.0
    Puertas: 5
    Motor: 1.6
    
  
    



