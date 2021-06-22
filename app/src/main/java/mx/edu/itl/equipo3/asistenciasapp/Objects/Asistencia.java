package mx.edu.itl.equipo3.asistenciasapp.Objects;

import java.util.Date;

public class Asistencia {
    private String fecha;
    private String nombre;
    private String noControl;
    private Date fechaDate;
    private String status; // "PRESENTE" | "JUSTIFICADO"

    public Asistencia(String fecha, String nombre, String noControl, Date fechaDate, String status) {
        this.fecha = fecha;
        this.nombre = nombre;
        this.noControl = noControl;
        this.fechaDate = fechaDate;
        this.status = status;
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

    public Date getFechaDate() {
        return fechaDate;
    }

    public void setFechaDate(Date fechaDate) {
        this.fechaDate = fechaDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
