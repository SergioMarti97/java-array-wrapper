# Java Array Wrapper

Este repositorio contiene un conjunto de clases útiles para trabajar con arrays y realizar transformaciones y operaciones con ellos.

**Autor**: Sergio Martí Torregrosa

**Fecha**: 16/02/2024

**Versión**: 2.0

**Dependencias**

- JOML (Java Open Math Library) 1.10.5v: para poder trabajar con vectores y matrices.
- LWJGL (Light Weight Java Game Library) 3.3.2v: para poder trabajar con las clases que representan imágenes y texturas con OpenGL.

## Módulos

En esta sección se detallan los diferentes módulos del proyecto. 

### Modulo java-array-wrapper

Este módulo contiene clases que actúan como "*wrappers*" (envoltorios) de matrices de valores. Contienen métodos para 
realizar ciertas operaciones con ellos.

Hay tres tipos de *arrays*: de 1 dimensión, 2 dimensiones y 3 dimensiones. Cada uno cuenta con 3 implementaciones para 
almacenar tres tipos de datos: enteros (*int*), coma flotante de precisión simple (*float*) y objetos.

Los tres tipos de *arrays* tienen la siguiente estructura de clases (ejemplo de las matrices 2d):
- Array2d: clase abstracta que representa un array de dos dimensiones.
    - Array2di: clase que representa un array bidimensional de enteros.
    - Array2df: clase que representa un array bidimensional de números decimales.
    - Array2do: clase abstracta que representa un array bidimensional de objetos.

**Paquete operations**

Este paquete contiene la clase *ShapeArrayOperations*, que permite trabajar con los wrappers para rasterizar formas 
simples: líneas, cuadrados, círculos y triángulos.

**Paquete transforms**

Este paquete contiene la clase *Array2dTransformer*, la cual permite realizar transformaciones geométricas (homotecias) 
de una matriz de valores. Estas transformaciones son: translación, rotación y escalado.


Los dos paquetes cuentan con implementaciones para trabajar con cada tipo de matriz diferente.

### Modulo java-array-texture

Este módulo contiene clases para almacenar en memoria la información de una imagen o textura.

Una imagen o una textura se pueden considerar como una matriz de píxeles. Un pixel es una estructura que guarda 
información de cuatro valores: rojo, verde, azul y la transparencia (*alpha*). Actualmente, hay varias formas de 
codificar esta información, entre las cuales destaca la codificación ARGB. Con esta forma, cada canal de color se 
almacena en un byte de información (0 - 255). Estos cuatro bytes se combinan en un integer y se suele trabajar con él 
en forma hexadecimal.

La clase *Texture* representa una matriz de integers donde cada integer es un pixel en formato ARGB. 
Hereda de la clase *Array2di* y, por lo tanto, se pueden realizar operaciones y transformaciones sobre la imagen.

En ciertas ocasiones una imagen o textura es una matriz de sub-imágenes o sub-texturas. Se conocen como
"*sprite sheets*" y son muy utilizadas. La clase *TextureTile* representa este tipo de texturas y contiene métodos para
obtener y manipular las imágenes que la forman.

### Modulo java-array-gltexture

Este módulo contiene clases para trabajar con imágenes o texturas de forma similar al módulo *java-array-texture* pero 
utiliza la librería *LWJGL*. La clase *GLTexture* representa una textura de OpenGL.

## Historial de versiones

- 16/02/2024: inicio de proyecto **version 1.0**. El proyecto está limitado a matrices de 2 dimensiones.
- 07/05/2024: **version 2.0**. Ahora el proyecto se ha ampliado y cuenta con clases de matrices de 1, 2 y 3 dimensiones.

## Planes a futuro

Implementar una clase de muestreador "*ArraySampler*" para poder tomar muestras de las texturas de una manera más precisa.
