import java.util.Scanner;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int opcion;

            Conexion.crearTabla();
            do {
                System.out.println("\n==Gestion de Estudiantes==");
                System.out.println("1. Insertar estudiante");
                System.out.println("2. Mostrar estudiantes");
                System.out.println("3. Actualizar estudiante");
                System.out.println("4. Eliminar estudiante");
                System.out.println("5. Consultas avanzadas");
                System.out.println("6. Salir");
                opcion = Entrada.leerEntero(sc, "Seleccione una opcion: ");

                switch (opcion) {
                    case 1:
                        GestorEstudiantes.insertarEstudiante(sc);
                        break;
                    case 2:
                        GestorEstudiantes.mostrarEstudiantes();
                        break;
                    case 3:
                        GestorEstudiantes.actualizarEstudiante(sc);
                        break;
                    case 4:
                        GestorEstudiantes.eliminarEstudiante(sc);
                        break;
                    case 5:
                        MenuConsultas.ejecutar(sc);
                        break;
                    case 6:
                        System.out.println("Fin");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }
            } while (opcion !=6);
        } catch (NoSuchElementException e) {
            System.out.println("Entrada finalizada. Saliendo del programa");
        }
    }
}
