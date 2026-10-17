import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    private static final String URL = "jdbc:sqlite:universidad_ej1.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }
    public static void crearTabla() {
        String sql = """
                CREATE TABLE IF NOT EXISTS estudiantes (
                    id INTEGER PRIMARY KEY,
                    nombre TEXT NOT NULL,
                    edad INTEGER,
                    carrera TEXT
                )
                """;
        try (Connection con = conectar();
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Tabla verificada correctamente");
        } catch (SQLException e) {
            System.out.println("Error: "+e.getMessage());
        }
    }
}
