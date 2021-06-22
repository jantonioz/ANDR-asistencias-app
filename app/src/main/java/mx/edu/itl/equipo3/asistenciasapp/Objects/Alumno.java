package mx.edu.itl.equipo3.asistenciasapp.Objects;

import java.util.ArrayList;

public class Alumno {
    private String noControl;
    private String nombreCompleto;
    private String[] nombres;

    private ArrayList<Asistencia> asistencias = new ArrayList<>();

    public Alumno(String noControl, String nombreCompleto, String[] nombres) {
        this.noControl = noControl;
        this.nombreCompleto = nombreCompleto;
        this.nombres = nombres;
    }

    public ArrayList<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(ArrayList<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }

    public String getNoControl() {
        return noControl;
    }

    public void setNoControl(String noControl) {
        this.noControl = noControl;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String[] getNombres() {
        return nombres;
    }

    public void setNombres(String[] nombres) {
        this.nombres = nombres;
    }
}
