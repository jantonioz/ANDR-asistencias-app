package mx.edu.itl.equipo3.asistenciasapp;

import java.util.Date;

public class Asistencia {
    private String fecha;
    private String noControl;
    private Date fechaDate;
    private String status; // "PRESENTE" | "JUSTIFICADO"

    public Asistencia(String fecha, String noControl, Date fechaDate, String status) {
        this.fecha = fecha;
        this.noControl = noControl;
        this.fechaDate = fechaDate;
        this.status = status;
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
