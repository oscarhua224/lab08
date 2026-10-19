import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

public class GestorConsultas {
    private ArrayList<Estudiante> estudiantes = new ArrayList<>();
    // cargar desde SQLite
    public void cargarEstudiantes() {
        String sql = "SELECT * FROM estudiantes";

        ArrayList<Estudiante> nuevosEstudiantes = new ArrayList<>();

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                String carrera = rs.getString("carrera");

                Estudiante estudiante =
                        new Estudiante(id, nombre, edad, carrera);

                nuevosEstudiantes.add(estudiante);
            }
            estudiantes = nuevosEstudiantes;

            System.out.println("Estudiantes cargados: "+ estudiantes.size());
        } catch (SQLException e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }
    //consultar registros con logica de java
    public void consultar(
            String[] campos,
            String campoFiltro,
            String operador,
            String valorFiltro,
            String campoOrden,
            boolean descendente,
            int limite) {

        if (campos == null || campos.length == 0) {
            System.out.println("Debe seleccionar al menos un campo");
            return;
        }

        for (String campo : campos) {
            if (!esCampoValido(campo)) {
                System.out.println("Campo no valido: "+ campo);
                return;
            }
        }

        if (!esCampoValido(campoFiltro) ||
            !esCampoValido(campoOrden)) {

            System.out.println("Campo de consulta no valido");
            return;
        }

        if (limite < 0) {
            System.out.println("El limite no puede ser negativo: ");
            return;
        }
        // filtrar registros
        ArrayList<Estudiante> resultado = new ArrayList<>();

        for (Estudiante e : estudiantes) {
            if(cumpleCondicion(e, campoFiltro, operador, valorFiltro)) {
                resultado.add(e);
            }
        }

        if (resultado.isEmpty()) {
            System.out.println("No se encontraron estudiantes");
        }
        // ordenar registros
        Comparator<Estudiante> comparador =
                obtenerComparador(campoOrden);

        if (descendente) {
            comparador = comparador.reversed();
        }
        resultado.sort(comparador);
        // limitar cantidad
        int cantidad = Math.max(0, Math.min(limite, resultado.size()));
        if (cantidad == 0) {
            System.out.println("El limite indicado es 0");
            return;
        }
        // mostrar campos seleccionados
        System.out.println("\n==Resultados de consulta==");
        for (int i = 0; i < cantidad; i++) {
            Estudiante e = resultado.get(i);
            for (String campo : campos) {
                switch (campo.toLowerCase()) {
                    case "id":
                        System.out.print(e.getId()+ " ");
                        break;
                    case "nombre":
                        System.out.print(e.getNombre()+ " ");
                        break;
                    case "edad":
                        System.out.print(e.getEdad()+ " ");
                        break;
                    case "carrera":
                        System.out.print(e.getCarrera()+ " ");
                        break;
                }
            }
            System.out.println();
        }
    }
    //validar nombres de campos
    private boolean esCampoValido(String campo) {
        if (campo == null) {
            return false;
        }
        return switch (campo.trim().toLowerCase()) {
            case "id", "nombre", "edad", "carrera" -> true;
            default -> false;
        };
    }
    //obtener comparador segun el campo
    private Comparator<Estudiante> obtenerComparador(String campo) {
        return switch (campo.trim().toLowerCase()) {
            case "id" ->
                    Comparator.comparingInt(Estudiante::getId);
            case "nombre" ->
                    Comparator.comparing(Estudiante::getNombre);
            case "edad" ->
                    Comparator.comparingInt(Estudiante::getEdad);
            case "carrera" ->
                    Comparator.comparing(Estudiante::getCarrera);
            default ->
                    throw new IllegalArgumentException(
                            "Campo de ordenamiento no valido"
                    );
        };
    }
    private boolean cumpleCondicion(
            Estudiante e,
            String campo,
            String operador,
            String valor) {
        if (campo == null || operador == null || valor == null) {
            return false;
        }

        switch (campo.trim().toLowerCase()) {
            case "edad":
                try {
                    int edad = Integer.parseInt(valor);

                    return switch (operador) {
                        case ">" -> e.getEdad() > edad;
                        case "<" -> e.getEdad() < edad;
                        case ">=" -> e.getEdad() >= edad;
                        case "<=" -> e.getEdad() <= edad;
                        case "=" -> e.getEdad() == edad;
                        default -> false;
                    };
                } catch (NumberFormatException ex) {
                    System.out.println("Valor no valido para edad: "+ valor);
                    return false;
                }
            case "id":
                try {
                    int id = Integer.parseInt(valor.trim());
                    return operador.equals("=") && e.getId() == id;
                } catch (NumberFormatException ex) {
                    return false;
                }
            case "nombre":
                return operador.equals("=") &&
                        e.getNombre().equalsIgnoreCase(valor);
            case "carrera":
                return operador.equals("=") &&
                        e.getCarrera().equalsIgnoreCase(valor);
            default:
                return false;
        }
    }
}
