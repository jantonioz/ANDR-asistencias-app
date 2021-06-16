package mx.edu.itl.equipo3.asistenciasapp;

import java.util.ArrayList;

public class Alumno {
    private String noControl;
    private String nombreCompleto;
    private String[] nombres;

    private ArrayList<Asistencia> asistencias;

    public Alumno(String noControl, String nombreCompleto, String[] nombres) {
        this.noControl = noControl;
        this.nombreCompleto = nombreCompleto;
        this.nombres = nombres;
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
