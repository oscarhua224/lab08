import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        Conexion.crearTabla();
        do {
            System.out.println("\n==Gestion de Estudiantes==");
            System.out.println("1. Insertar estudiante");
            System.out.println("2. Mostrar estudiantes");
            System.out.println("3. Actualizar estudiante");
            System.out.println("4. Eliminar estudiante");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = sc.nextInt();

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
                    System.out.println("Saliendo del programa");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        } while (opcion !=5);
        sc.close();
    }
}
