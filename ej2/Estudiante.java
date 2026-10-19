public class Estudiante {
    private int id;
    private String nombre;
    private int edad;
    private String carrera;

    public Estudiante(int id, String nombre, int edad, String carrera) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.carrera = carrera;
    }
    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public int getEdad() {
        return edad;
    }
    public String getCarrera() {
        return carrera;
    }
}
