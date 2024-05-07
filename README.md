# Java Array Wrapper

Este repositorio contiene un conjunto de clases útiles para trabajar con arrays y realizar transformaciones y operaciones con ellos.

**Autor**: Sergio Martí Torregrosa

**Fecha**: 16/02/2024

**Versión**: 2.0

**Dependencias**

- JOML (Java Open Math Library): para poder trabajar con vectores y matrices.
- LWJGL (Light Weight Java Game Library): para poder trabajar con las clases que representan imágenes y texturas con OpenGL.

## Modulo java-array-wrapper

Este módulo contiene clases que actúan como "*wrappers*" (envoltorios) de matrices de valores. Contienen métodos para 
realizar ciertas operaciones con ellos. 

- Array2d: clase abstracta que representa un array de dos dimensiones.
- Array2di: clase que representa un array bidimensional de enteros.
- Array2df: clase que representa un array bidimensional de números de coma flotante de precisión simple (*float*).
- Array2do: clase abstracta que representa un array bidimensional de objetos.

**Paquete operations**

Este paquete contiene la clase *ShapeArrayOperations*, que permite trabajar con los wrappers para rasterizar formas 
simples: líneas, cuadrados, círculos y triángulos.

**Paquete transforms**

Este paquete contiene la clase *Array2dTransformer*, la cual permite realizar transformaciones geométricas (homotecias) 
de una matriz de valores. Estas transformaciones son: translación, rotación y escalado.


Los dos paquetes cuentan con implementaciones para trabajar con cada tipo de matriz diferente.

## Modulo java-array-texture

Este módulo contiene clases para almacenar en memoria la información de una imagen o textura.

Una imagen o una textura se pueden considerar como una matriz de píxeles. Un pixel es una estructura que guarda 
información de cuatro valores: rojo, verde, azul y la transparencia (*alpha*). Actualmente, hay varias formas de 
codificar esta información, entre las cuales destaca la codificación ARGB. Con esta forma, cada canal de color se 
almacena en un byte de información (0 - 255). Estos cuatro bytes se combinan en un integer y se suele trabajar con él 
en forma hexadecimal.

La clase *Texture* representa una matriz de integers donde cada integer es un pixel en formato ARGB. 
Hereda de la clase *Array2di*, por lo tanto, se pueden realizar operaciones y transformaciones sobre la imagen.

La clase *GLTexture* representa una textura de OpenGL.

En ciertas ocasiones una imagen o textura es una matriz de sub-imágenes o sub-texturas. Se conocen como 
"*sprite sheets*" y son muy utilizadas. La clase *TextureTile* representa este tipo de texturas y contiene métodos para 
obtener las imágenes que la forman.

