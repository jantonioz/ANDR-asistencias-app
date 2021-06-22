package mx.edu.itl.equipo3.asistenciasapp.Objects;

import java.util.Date;

public class Asistencia {
    private String fecha;
    private String nombre;
    private String noControl;
    private AsistenciaStatus status; // "PRESENTE" | "JUSTIFICADO"
    private GrupoEnum grupo;

    public Asistencia(String fecha, String nombre, String noControl, AsistenciaStatus status, GrupoEnum grupo) {
        this.fecha = fecha;
        this.nombre = nombre;
        this.noControl = noControl;
        this.status = status;
        this.grupo = grupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNoControl() {
        return noControl;
    }

    public void setNoControl(String noControl) {
        this.noControl = noControl;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public AsistenciaStatus getStatus() {
        return status;
    }

    public void setStatus(AsistenciaStatus status) {
        this.status = status;
    }

    public GrupoEnum getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoEnum grupo) {
        this.grupo = grupo;
    }
}
