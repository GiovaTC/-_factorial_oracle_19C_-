# -_factorial_oracle_19C_- :.
# 🧮 FactorialOracle19c:

<img width="1254" height="1254" alt="image" src="https://github.com/user-attachments/assets/438038be-679d-40a6-adcf-7dd735b97adb" />  

<img width="2552" height="1079" alt="image" src="https://github.com/user-attachments/assets/743478d9-40fd-45bc-a4ff-d2be0e63af6b" />    
```

## Programa Java para calcular el factorial de un número y almacenar la información en Oracle 19c

## 1. Descripción del proyecto

Este proyecto consiste en una aplicación de consola desarrollada en Java 21 utilizando IntelliJ IDEA, Maven, JDBC
y Oracle Database 19c.

El programa permite ingresar un número entero positivo, calcular su factorial, imprimir el procedimiento matemático
paso a paso en la consola y almacenar la información en una base de datos Oracle 19c .

### Funcionalidades

- Solicitar un número entero al usuario.
- Validar el número ingresado.
- Calcular el factorial mediante un ciclo `for`.
- Imprimir el procedimiento matemático en consola.
- Mostrar el resultado final.
- Conectarse a Oracle 19c mediante JDBC.
- Insertar los datos en una tabla de Oracle.
- Consultar posteriormente los registros almacenados.

## 2. Tecnologías utilizadas

| Tecnología | Versión |
|---|---|
| Java | 21 |
| IntelliJ IDEA | 2023 o superior |
| Maven | 3.8 o superior |
| Oracle Database | 19c |
| JDBC | ojdbc11 |

## 3. Estructura del proyecto

```text
FactorialOracle19c/
│
├── pom.xml
│
└── src/
    └── main/
        └── java/
            ├── config/
            │   └── ConexionOracle.java
            │
            └── Main.java
```

## 4. Configuración de Oracle 19c

Antes de ejecutar el programa, debes crear la tabla en Oracle.

### Script SQL

```sql
CREATE TABLE FACTORIAL_REGISTROS (
    ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NUMERO NUMBER(4) NOT NULL,
    PROCEDIMIENTO CLOB,
    RESULTADO VARCHAR2(4000),
    FECHA_REGISTRO TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Consultar los registros

```sql
SELECT
    ID,
    NUMERO,
    PROCEDIMIENTO,
    RESULTADO,
    FECHA_REGISTRO
FROM FACTORIAL_REGISTROS
ORDER BY ID DESC;
```

## 5. Archivo pom.xml

Este archivo contiene la configuración Maven y la dependencia JDBC necesaria para conectarse a Oracle 19c.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.factorial</groupId>
    <artifactId>FactorialOracle19c</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>

        <dependency>
            <groupId>com.oracle.database.jdbc</groupId>
            <artifactId>ojdbc11</artifactId>
            <version>23.6.0.24.10</version>
        </dependency>

    </dependencies>

</project>
```

## 6. Clase ConexionOracle.java

Ruta:

```text
src/main/java/config/ConexionOracle.java
```

Código completo:

```java
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521/orcl";

    private static final String USUARIO = "SYSTEM";

    private static final String CLAVE = "TU_CLAVE";

    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USUARIO,
                CLAVE
        );
    }
}
```

### Configuración de conexión

Debes modificar las siguientes variables:

```java
private static final String URL =
        "jdbc:oracle:thin:@localhost:1521/orcl";

private static final String USUARIO = "SYSTEM";

private static final String CLAVE = "TU_CLAVE";
```

Si tu servicio Oracle es `XEPDB1`, puedes utilizar:

```java
private static final String URL =
        "jdbc:oracle:thin:@localhost:1521/XEPDB1";
```

Recuerda que la tabla debe existir en el esquema al que te conectas.

## 7. Clase Main.java

Ruta:

```text
src/main/java/Main.java
```

Código completo:

```java
import config.ConexionOracle;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("==================================");
        System.out.println("     CALCULO DEL FACTORIAL");
        System.out.println("==================================");

        System.out.print(
                "Ingrese un numero entero entre 0 y 1000: "
        );

        int numero;

        try {

            numero = Integer.parseInt(scanner.nextLine());

        } catch (NumberFormatException e) {

            System.out.println(
                    "Error: debe ingresar un numero entero."
            );

            scanner.close();
            return;
        }

        // Validar el numero ingresado
        if (numero < 0 || numero > 1000) {

            System.out.println(
                    "Error: ingrese un numero entre 0 y 1000."
            );

            scanner.close();
            return;
        }

        // Variable para almacenar el factorial
        BigInteger factorial = BigInteger.ONE;

        // Variable para construir el procedimiento
        StringBuilder procedimiento = new StringBuilder();

        System.out.println("\n----------------------------------");
        System.out.println("PROCEDIMIENTO MATEMATICO");
        System.out.println("----------------------------------");

        // Caso especial: factorial de cero
        if (numero == 0) {

            System.out.println("0! = 1");

            procedimiento.append("0! = 1\n");

        } else {

            // Mostrar la expresion matematica
            System.out.print(numero + "! = ");

            procedimiento.append(numero).append("! = ");

            for (int i = numero; i >= 1; i--) {

                System.out.print(i);

                procedimiento.append(i);

                if (i > 1) {

                    System.out.print(" x ");

                    procedimiento.append(" x ");
                }
            }

            System.out.println();

            procedimiento.append("\n");

            // Calcular factorial paso a paso
            System.out.println("\nPasos del calculo:");

            for (int i = 1; i <= numero; i++) {

                BigInteger anterior = factorial;

                factorial = factorial.multiply(
                        BigInteger.valueOf(i)
                );

                String paso = "Paso " + i + ": "
                        + anterior + " x " + i
                        + " = " + factorial;

                System.out.println(paso);

                procedimiento.append(paso).append("\n");
            }
        }

        // Mostrar resultado final
        System.out.println("\n----------------------------------");
        System.out.println("RESULTADO FINAL");
        System.out.println("----------------------------------");

        System.out.println(
                "El factorial de " + numero
                + " es: " + factorial
        );

        // Guardar informacion en Oracle
        guardarEnOracle(
                numero,
                procedimiento.toString(),
                factorial.toString()
        );

        scanner.close();
    }

    public static void guardarEnOracle(
            int numero,
            String procedimiento,
            String resultado) {

        String sql = """
                INSERT INTO FACTORIAL_REGISTROS
                (NUMERO, PROCEDIMIENTO, RESULTADO)
                VALUES (?, ?, ?)
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setInt(1, numero);
            ps.setString(2, procedimiento);
            ps.setString(3, resultado);

            int filas = ps.executeUpdate();

            if (filas > 0) {

                System.out.println("\n----------------------------------");
                System.out.println("REGISTRO EN ORACLE 19c");
                System.out.println("----------------------------------");

                System.out.println(
                        "Informacion guardada correctamente."
                );

                System.out.println(
                        "Numero de registros insertados: " + filas
                );

            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al guardar en Oracle 19c: "
                    + e.getMessage()
            );
        }
    }
}
```

## 8. Ejemplo de ejecución

Si ingresamos el número `5`, la aplicación mostrará:

```text
==================================
     CALCULO DEL FACTORIAL
==================================
Ingrese un numero entero entre 0 y 1000: 5

----------------------------------
PROCEDIMIENTO MATEMATICO
----------------------------------
5! = 5 x 4 x 3 x 2 x 1

Pasos del calculo:
Paso 1: 1 x 1 = 1
Paso 2: 1 x 2 = 2
Paso 3: 2 x 3 = 6
Paso 4: 6 x 4 = 24
Paso 5: 24 x 5 = 120

----------------------------------
RESULTADO FINAL
----------------------------------
El factorial de 5 es: 120

----------------------------------
REGISTRO EN ORACLE 19c
----------------------------------
Informacion guardada correctamente.
Numero de registros insertados: 1
```

## 9. Ejemplos matemáticos

### Factorial de 3

```text
3! = 3 x 2 x 1

Paso 1: 1 x 1 = 1
Paso 2: 1 x 2 = 2
Paso 3: 2 x 3 = 6

Resultado: 6
```

### Factorial de 6

```text
6! = 6 x 5 x 4 x 3 x 2 x 1

Paso 1: 1 x 1 = 1
Paso 2: 1 x 2 = 2
Paso 3: 2 x 3 = 6
Paso 4: 6 x 4 = 24
Paso 5: 24 x 5 = 120
Paso 6: 120 x 6 = 720

Resultado: 720
```

### Factorial de 0

```text
0! = 1

Resultado: 1
```

## 10. Consultar información en Oracle 19c

Después de ejecutar el programa, puedes consultar los datos almacenados.

```sql
SELECT
    ID,
    NUMERO,
    PROCEDIMIENTO,
    RESULTADO,
    FECHA_REGISTRO
FROM FACTORIAL_REGISTROS
ORDER BY ID DESC;
```

Ejemplo de resultado:

| ID | NUMERO | RESULTADO |
|---:|---:|---:|
| 1 | 5 | 120 |
| 2 | 6 | 720 |
| 3 | 3 | 6 |
| 4 | 0 | 1 |

## 11. Fórmula matemática

El factorial de un número entero no negativo se define como:

\[
n!=n\times(n-1)\times(n-2)\times\dots\times 1
\]

Para el caso especial de cero:

\[
0!=1
\]

Ejemplo:

\[
5!=5\times4\times3\times2\times1=120
\]

El programa utiliza `BigInteger` para permitir cálculos de factoriales grandes sin desbordamiento de los tipos numéricos 
tradicionales de Java . 

## 12. Instalación y ejecución

### Paso 1. Crear el proyecto

En IntelliJ IDEA:

1. Seleccionar `New Project`.
2. Elegir `Maven`.
3. Seleccionar Java 21.
4. Asignar el nombre `FactorialOracle19c`.

### Paso 2. Configurar Maven

Copiar el contenido del archivo `pom.xml`.

Esperar a que IntelliJ descargue la dependencia JDBC.

### Paso 3. Crear la tabla

Ejecutar el script SQL de creación de la tabla en Oracle 19c.

### Paso 4. Configurar la conexión

Modificar la clase `ConexionOracle.java` con el usuario, contraseña y servicio Oracle correspondiente.

### Paso 5. Ejecutar el programa

Ejecutar la clase `Main.java`.

### Paso 6. Consultar los registros

Ejecutar el SELECT de la tabla `FACTORIAL_REGISTROS` desde SQL Developer.

## 13. Conceptos de programación aplicados

| Concepto | Aplicación |
|---|---|
| Variables | Almacenar número y resultado |
| Scanner | Leer información desde teclado |
| if | Validar el número ingresado |
| for | Calcular el factorial |
| BigInteger | Operaciones con números grandes |
| StringBuilder | Construir el procedimiento |
| JDBC | Conexión con Oracle |
| PreparedStatement | Ejecutar INSERT |
| SQL | Almacenar y consultar registros |

## 14. Autor

Proyecto educativo desarrollado en Java 21 con IntelliJ IDEA y Oracle Database 19c.

**Nombre del proyecto:** FactorialOracle19c

**Lenguaje:** Java

**Base de datos:** Oracle 19c

**IDE:** IntelliJ IDEA

**Objetivo:** Calcular el factorial de un número, imprimir el procedimiento matemático en consola y almacenar la información en Oracle 19c.
:. . / .
