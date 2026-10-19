import java.sql.*;
import java.util.Scanner;

public class GestorEstudiantes {
    public static void insertarEstudiante(Scanner sc) {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Edad: ");
        int edad = sc.nextInt();
        sc.nextLine();

        System.out.print("Carrera: ");
        String carrera = sc.nextLine();

        String sql = """
                INSERT INTO estudiantes
                (id, nombre, edad, carrera)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection con = Conexion.conectar()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);
                ps.setString(2, nombre);
                ps.setInt(3, edad);
                ps.setString(4, carrera);

                ps.executeUpdate();
                confirmarOperacion(con, sc);
            } catch (SQLException e) {
                con.rollback();
                System.out.println("Error en la operacion: "+ e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }
    public static void mostrarEstudiantes() {
        String sql = "SELECT * FROM estudiantes";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n==Lista de estudiantes==");
            boolean hayRegistros = false;

            while (rs.next()) {
                hayRegistros = true;

                System.out.println("ID: "+ rs.getInt("id"));
                System.out.println("Nombre: "+ rs.getString("nombre"));
                System.out.println("Edad: "+ rs.getInt("edad"));
                System.out.println("Carrera: "+ rs.getString("carrera"));
                System.out.println("-----------------------");
            }
            if (!hayRegistros) {
                System.out.println("No hay estudiantes registrados");
            }
        } catch (SQLException e) {
            System.out.println("Error: "+e.getMessage());
        }
    }
    public static void actualizarEstudiante(Scanner sc) {
        System.out.print("ID del estudiante a actualizar: ");
        int id =sc.nextInt();
        sc.nextLine();

        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Nueva carrera: ");
        String carrera = sc.nextLine();

        System.out.print("Nueva edad: ");
        int edad = sc.nextInt();
        sc.nextLine();

        String sql = """
                UPDATE estudiantes
                SET nombre = ?, carrera = ?, edad = ?
                WHERE id = ?
                """;
        try (Connection con = Conexion.conectar()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)){

                ps.setString(1, nombre);
                ps.setString(2, carrera);
                ps.setInt(3, edad);
                ps.setInt(4, id);

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    confirmarOperacion(con, sc);
                } else {
                    con.rollback();
                    System.out.println("Estudiante no encontrado");
                }
            } catch (SQLException e) {
                con.rollback();
                System.out.println("Error: "+ e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error de conexion: "+ e.getMessage());
        }
    }
    public static void eliminarEstudiante(Scanner sc) {
        System.out.print("ID del estudiante a eliminar: ");
        int id = sc.nextInt();
        sc.nextLine();

        String sql = "DELETE FROM estudiantes WHERE id = ?";

        try (Connection con = Conexion.conectar()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    confirmarOperacion(con, sc);
                } else {
                    con.rollback();
                    System.out.println("No se encontro al estudiante");
                }
            } catch (SQLException e) {
                con.rollback();
                System.out.println("Error en la operacion: "+ e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error de conexion o transaccion: "+ e.getMessage());
        }
    }
    private static final String CLAVE = "1234";

    private static void confirmarOperacion(
            Connection con, Scanner sc) throws SQLException {
        System.out.print("Ingrese la contraseña: ");
        String claveIngresada = sc.nextLine();

        if (claveIngresada.equals(CLAVE)) {
            con.commit();
            System.out.println("Operacion confirmada");
        } else {
            con.rollback();
            System.out.println("Contraseña incorrecta, cambios revertidos");
        }
    }
}
