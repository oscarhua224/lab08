import java.util.Scanner;

public class Entrada {
    public static int leerEntero(Scanner sc, String mensaje) {
        return leerEntero(sc, mensaje, Integer.MIN_VALUE);
    }
    public static int leerEntero(Scanner sc, String mensaje, int minimo) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = Integer.parseInt(sc.nextLine().trim());
                if (valor < minimo) {
                    System.out.println("El valor debe ser mayor o igual a " + minimo);
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido");
            }
        }
    }
    public static String leerTexto(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = sc.nextLine().trim();
            if (!texto.isBlank()) {
                return texto;
            }
            System.out.println("El campo no puede estar vacio");
        }
    }
}
