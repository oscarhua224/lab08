import java.util.Locale;
import java.util.Scanner;

public class MenuConsultas {
    public static void ejecutar(Scanner sc) {
        GestorConsultas gestor = new GestorConsultas();
        gestor.cargarEstudiantes();

        boolean valido;
        //seleccionar campos a mostrar
        String[] campos;
        do {
            System.out.print("Campos a mostrar (separados por coma): ");
            campos = sc.nextLine().split(",");
            valido = true;

            for (int i = 0; i < campos.length; i++) {
                campos[i] = campos[i].trim();

                if (!esCampoValido(campos[i])) {
                    valido = false;
                }
            }
            if (!valido) {
                System.out.println("Campos no validos, intente nuevamente");
            }
        } while (!valido);
        // seleccionar campo para filtrar
        String campoFiltro;
        do {
            System.out.print("Campo para filtrar: ");
            campoFiltro = sc.nextLine().trim();

            valido = esCampoValido(campoFiltro);

            if (!valido) {
                System.out.println("Campo no valido, intente nuevamente");
            }
        } while (!valido);

        String operador;
        do {
            System.out.print("Operador (=, >, <, >=, <=): ");
            operador = sc.nextLine().trim();

            valido = esOperadorValido(campoFiltro, operador);
            if (!valido) {
                System.out.println("Operador no valido para este campo");
            }
        } while (!valido);
        // validar valor del filtro
        String valorFiltro;
        do {
            System.out.print("Valor a comparar: ");
            valorFiltro = sc.nextLine().trim();

            valido = esValorValido(campoFiltro, valorFiltro);
            if (!valido) {
                System.out.println("Valor no valido, intente nuevamente");
            }
        } while (!valido);
        // validar campo para ordenar
        String campoOrden;
        do {
            System.out.print("Campo para ordenar: ");
            campoOrden = sc.nextLine().trim();

            valido = esCampoValido(campoOrden);
            if (!valido) {
                System.out.println("Campo no valido. Opciones: id, nombre, edad, carrera");
            }
        } while (!valido);
        //validar orden ascendete o descendente
        boolean descendente;
        while (true) {
            System.out.print("Orden descendente (true/false): ");
            String respuesta = sc.nextLine().trim();

            if (respuesta.equalsIgnoreCase("true")) {
                descendente = true;
                break;
            } else if (respuesta.equalsIgnoreCase("false")) {
                descendente = false;
                break;
            } else {
                System.out.println("Ingrese true o false");
            }
        }
        //validar limite de registros
        int limite;
        while (true) {
            System.out.print("Cantidad maxima de resultados: ");
            try {
                limite = Integer.parseInt(sc.nextLine());

                if (limite < 0) {
                    System.out.println("El limite no puede ser negativo");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error, ingrese un numero valido");
            }
        }
        // ejecutar consulta
        try {
            gestor.consultar(
                    campos,
                    campoFiltro,
                    operador,
                    valorFiltro,
                    campoOrden,
                    descendente,
                    limite
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Error en la consulta: "+ e.getMessage());
        }
    }
    // validar nombres de campos
    private static boolean esCampoValido(String campo) {
        return campo.equalsIgnoreCase("id") ||
                campo.equalsIgnoreCase("nombre") ||
                campo.equalsIgnoreCase("edad") ||
                campo.equalsIgnoreCase("carrera");
    }
    //validar operaciones segun el campo
    private static boolean esOperadorValido(String campo, String operador) {
        switch (campo.toLowerCase()) {
            case "edad":
                return operador.equals("=") ||
                        operador.equals(">") ||
                        operador.equals("<") ||
                        operador.equals(">=") ||
                        operador.equals("<=");

            case "nombre":
            case "carrera":
            case "id":
                return operador.equals("=");

            default:
                return false;
        }
    }
    //validar valores introducidos por el usuario
    private static boolean esValorValido(String campo, String valor) {
        if (valor.isEmpty()) {
            return false;
        }
        switch (campo.toLowerCase()) {
            case "edad":
            case "id":
                try {
                    Integer.parseInt(valor);
                    return true;
                } catch (NumberFormatException ex) {
                    return false;
                }
            case "nombre":
            case "carrera":
                return true;
            default:
                return false;
        }
    }
}
