import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:universidad_actividad.db";

        try (Connection con = DriverManager.getConnection(url);
             Statement stmt = con.createStatement()) {
            // CREAR TABLA
            String sql = """
                    CREATE TABLE IF NOT EXISTS estudiantes (
                        id INTEGER PRIMARY KEY,
                        nombre TEXT NOT NULL,
                        edad INTEGER
                    )
                    """;
            stmt.executeUpdate(sql);
            System.out.println("Tabla verificada correctamente.");
            // INSERT
            System.out.println("\n-- INSERT --");
            String sqlInsert =
                    "INSERT INTO estudiantes (id, nombre, edad) VALUES (?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {

                ps.setInt(1, 1);
                ps.setString(2, "Carlos");
                ps.setInt(3, 21);
                ps.executeUpdate();

                ps.setInt(1, 2);
                ps.setString(2, "Lucia");
                ps.setInt(3, 19);
                ps.executeUpdate();

                System.out.println("Estudiantes insertados correctamente.");
            }

            // SELECT
            System.out.println("\n-- SELECT --");

            String sqlSelect = "SELECT * FROM estudiantes";

            try (PreparedStatement ps = con.prepareStatement(sqlSelect);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println(
                            rs.getInt("id") + " - " +
                                    rs.getString("nombre") + " - " +
                                    rs.getInt("edad")
                    );
                }
            }

            // UPDATE
            System.out.println("\n-- UPDATE --");

            String sqlUpdate =
                    "UPDATE estudiantes SET nombre = ? WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlUpdate)) {

                ps.setString(1, "Luciana");
                ps.setInt(2, 2);

                int filasActualizadas = ps.executeUpdate();

                System.out.println(
                        "Filas actualizadas: " + filasActualizadas
                );
            }

            // DELETE
            System.out.println("\n-- DELETE --");

            String sqlDelete =
                    "DELETE FROM estudiantes WHERE edad < ?";
            try (PreparedStatement ps = con.prepareStatement(sqlDelete)) {

                ps.setInt(1, 20);

                int filasEliminadas = ps.executeUpdate();

                System.out.println(
                        "Registros eliminados: " + filasEliminadas
                );
            }

            // TRANSACCIONES
            System.out.println("\n-- TRANSACCIONES JDBC --");
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {

                // Operacion confirmada
                ps.setInt(1, 3);
                ps.setString(2, "Pedro");
                ps.setInt(3, 22);
                ps.executeUpdate();

                con.commit();
                System.out.println("INSERT de Pedro confirmado con commit.");

                // Operacion revertida
                ps.setInt(1, 4);
                ps.setString(2, "Ana");
                ps.setInt(3, 20);
                ps.executeUpdate();

                con.rollback();
                System.out.println("INSERT de Ana revertido con rollback.");
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }

            // RESULTADO FINAL
            System.out.println("\n-- REGISTROS FINALES --");

            try (PreparedStatement ps = con.prepareStatement(sqlSelect);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    System.out.println(
                            rs.getInt("id") + " - " +
                                    rs.getString("nombre") + " - " +
                                    rs.getInt("edad")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
