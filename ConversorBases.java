import java.util.Scanner;

public class ConversorBases {

    // Caracteres para representar dígitos hasta base 36 (0-9 y A-Z)
    private static final String DIGITOS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean continuar = true;

        // Bucle para permitir múltiples conversiones (Requerimiento del deber)
        while (continuar) {
            try {
                System.out.print("\nIngresa el número a convertir: ");
                String numIngresado = sc.next().trim().toUpperCase(); // Se usa next() en lugar de nextLine() para evitar saltos de línea vacíos

                System.out.print("Ingresa la base de origen (2 - 36): ");
                int baseOrigen = sc.nextInt();

                System.out.print("Ingresa la base de destino (2 - 36): ");
                int baseDestino = sc.nextInt();

                // Validación de límites de base
                if (baseOrigen < 2 || baseOrigen > 36 || baseDestino < 2 || baseDestino > 36) {
                    System.out.println("Error: Las bases deben estar entre 2 y 36.");
                } else {
                    // Paso 1: Convertir la entrada a decimal (base 10) usando nuestra función manual
                    int numDecimal = convertirADecimal(numIngresado, baseOrigen);

                    // Paso 2: Convertir de decimal a la base destino e imprimir
                    String resultado = convertirDesdeDecimal(numDecimal, baseDestino);

                    System.out.println("----------------------------------------");
                    System.out.println("El número " + numIngresado + " (base " + baseOrigen +
                                       ") equivale a: " + resultado + " (base " + baseDestino + ")");
                    System.out.println("----------------------------------------");
                }

            } catch (NumberFormatException e) {
                // Atrapa el error si la función convertirADecimal detecta un símbolo inválido
                System.out.println("Error: El número ingresado no es válido para la base especificada.");
            } catch (Exception e) {
                System.out.println("Error: Entrada inválida. Asegúrate de ingresar números para las bases.");
                sc.nextLine(); // Limpiar el buffer del scanner en caso de error de tipado
            }

            // Opcionalmente realizar otra conversión
            System.out.print("\n¿Deseas realizar otra conversión? (S/N): ");
            String respuesta = sc.next().trim().toUpperCase();
            if (!respuesta.equals("S")) {
                continuar = false;
                System.out.println("Saliendo del programa...");
            }
        }

        sc.close();
    }

    /**
     * Convierte un entero en base 10 a cualquier base mediante divisiones sucesivas.
     */
    public static String convertirDesdeDecimal(int decimal, int baseDestino) {
        if (decimal == 0) {
            return "0";
        }

        StringBuilder resultado = new StringBuilder();
        int valor = Math.abs(decimal);

        while (valor > 0) {
            int residuo = valor % baseDestino;
            resultado.insert(0, DIGITOS.charAt(residuo)); // Agrega el dígito al inicio
            valor = valor / baseDestino;
        }

        if (decimal < 0) {
            resultado.insert(0, "-");
        }

        return resultado.toString();
    }

    /**
     * Convierte una cadena de cualquier base (2-36) a un entero decimal (base 10) de forma manual.
     */
    public static int convertirADecimal(String num, int baseOrigen) throws NumberFormatException {
        int decimal = 0;
        int startIndex = 0;
        boolean esNegativo = false;

        // Validación de cadena vacía
        if (num.isEmpty()) {
            throw new NumberFormatException("Cadena vacía");
        }

        // Si el número es negativo, empezamos a procesar desde el segundo carácter
        if (num.charAt(0) == '-') {
            esNegativo = true;
            startIndex = 1;
        }

        // Recorremos cada dígito del String ingresado
        for (int i = startIndex; i < num.length(); i++) {
            char caracter = num.charAt(i);

            // Buscamos la posición del carácter en nuestra constante DIGITOS (ej. 'A' es 10)
            int valorSimbolo = DIGITOS.indexOf(caracter);

            // Validamos que el símbolo exista y que además sea estrictamente menor que la base de origen
            // Por ejemplo: si base es 8, '9' retornará un valorSimbolo de 9, lo cual activará este error.
            if (valorSimbolo < 0 || valorSimbolo >= baseOrigen) {
                throw new NumberFormatException("Dígito inválido para la base de origen.");
            }

            // Aplicamos el método polinomial: acumulado * base + nuevo dígito
            decimal = (decimal * baseOrigen) + valorSimbolo;
        }

        // Devolvemos el número final, restaurando su signo negativo si lo tenía
        if (esNegativo) {
            return -decimal;
        }

        return decimal;
    }
}
