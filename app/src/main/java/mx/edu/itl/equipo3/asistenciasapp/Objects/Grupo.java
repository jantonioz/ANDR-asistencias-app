package mx.edu.itl.equipo3.asistenciasapp.Objects;

public class Grupo {
    private int id;
    private String nombre;
    private String profe;
    private int clases;

    public Grupo(int id, String nombre, String profe, int clases) {
        this.id = id;
        this.nombre = nombre;
        this.profe = profe;
        this.clases = clases;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProfe() {
        return profe;
    }

    public void setProfe(String profe) {
        this.profe = profe;
    }

    public int getClases() {
        return clases;
    }

    public void setClases(int clases) {
        this.clases = clases;
    }
}
