# invernadero-ii-java

<img width="1013" alt="image" src="https://github.com/user-attachments/assets/4b02edcb-bd5b-4139-a687-040e6e08a785">

# Descripción

Invernadero II es la segunda versión del proyecto del módulo de Programación I. Programa en Java que simula la gestión de una serie de huertos con capacidad para plantas y árboles con características especiales.

# Uso

El programa está pensado para su uso en una terminal de comandos. Es necesario tener instalador Java Development Kit versión 17 en adelante.

# Instalación

Puede ser compilado usando IDEs como Apache NetBeans o Visual Studio Code, con sus respectivas extensiones.

Se requiere instalar las dependencias [External Lib](./external-lib/). En Visual Studio Code se puede importar mediante **Referenced Libraries**.

## Compilación y ejecución desde la terminal

Si prefieres no usar un IDE, puedes compilar y ejecutar el proyecto directamente desde la terminal.

### Linux/macOS

1.  **Compilar:**
    Este comando compila todos los archivos `.java` del directorio `src` y guarda los `.class` en `bin`.
    ```sh
    javac -d bin -cp "external-lib/InvernaderoLib.jar" $(find src -name "*.java")
    ```

2.  **Ejecutar:**
    Este comando ejecuta el programa, especificando la ruta a las clases compiladas y a la librería externa.
    ```sh
    java -cp "bin:external-lib/InvernaderoLib.jar" invernadero.Simulador
    ```

### Windows

1.  **Compilar (usando PowerShell):**
    ```powershell
    javac -d bin -cp "external-lib/InvernaderoLib.jar" (Get-ChildItem -Recurse -Filter *.java src).FullName
    ```

2.  **Ejecutar:**
    En Windows, el separador del classpath es `;` en lugar de `:`.
    ```powershell
    java -cp "bin;external-lib/InvernaderoLib.jar" invernadero.Simulador
    ```

# Documentación

En el [Manual de usuario](./documentation/Manual_Usuario_Proyecto_Invernadero_II.pdf) se describe el funcionamiento de la interfaz de usuario del programa, así como las opciones y reglas del juego.

Por otro lado, en el [Manual técnico](./documentation/Manual_Tecnico_Proyecto_Invernadero_II.pdf) se trata de explicar en profundidad cada clase hecha.
