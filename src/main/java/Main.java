import config.ConexionOracle;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Main
{

    public static void main(String[] args) // metodo principal.
    {
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

        } catch (NumberFormatException e)
        {

            System.out.println(
                    "Error: debe ingresar un numero entero."
            );

            scanner.close();
            return;
        }

        // VALIDAR numero ingresado!
        if (numero < 0 || numero > 1000)
        {
            System.out.println(
                    "Error: ingrese un numero entre 0 y 1000."
            );

            scanner.close();
            return;
        }

        // VARIABLE para almacenar el factorial.
        BigInteger factorial = BigInteger.ONE;

        // VARIABLE para construir el procedimiento.
        StringBuilder procedimiento = new StringBuilder();

        System.out.println("\n----------------------------------");
        System.out.println("PROCEDIMIENTO MATEMATICO");
        System.out.println("----------------------------------");

        // CASO especial : factorial de cero!
        if (numero == 0)
        {
            System.out.println("0! = 1");
            procedimiento.append("0! = 1\n");

        } else {
            //MOSTRAR la expresion matematica.
            System.out.print(numero + "! = ");
            procedimiento.append(numero).append("! = ");

            for (int i = numero; i >= 1; i --)
            {
                System.out.print(i);
                procedimiento.append(i);

                if (i > 1) {
                    System.out.print(" x ");
                    procedimiento.append(" x ");
                }
            }

            System.out.println();
            procedimiento.append("/n");

            // CALCULAR FACTORIAL paso a paso!
            System.out.println("\nPasos del calculo: ");

            for (int i = 1; i <= numero; i++)
            {
                BigInteger anterior = factorial;

                factorial = factorial.multiply(BigInteger.valueOf(i));

                String paso = "Paso " + i + ": " + anterior + " x " + i
                                + anterior + " x " + i + " = " + factorial;

                System.out.println(paso);
                procedimiento.append(paso).append("\n");
            }
        }

        // MOSTRAR resultado final.
        System.out.println("\n----------------------------------");
        System.out.println("RESULTADO FINAL");
        System.out.println("----------------------------------");
        
        System.out.println("El factorial de " + numero + " es: " + factorial);
        
        // GUARDAR informacion en oracle!
        guardarEnOracle(numero, procedimiento.toString(), factorial.toString());
        
        scanner.close();
    }

    public static void guardarEnOracle(int numero, String procedimiento, String resultado)
    {
        String sql = """
                INSERT INTO FACTORIAL_REGISTROS (NUMERO, PROCEDIMIENTO, RESULTADO)
                VALUES (?,?,?)
                """;
        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, numero);
            ps.setString(2, procedimiento);
            ps.setString(3, resultado);

            int filas = ps.executeUpdate();

            if (filas > 0) // guardar bd.
            {
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