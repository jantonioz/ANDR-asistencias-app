package mx.edu.itl.equipo3.asistenciasapp;

import java.io.File;

public class InfoArchivo {
    private String nombre;
    private String pesoKB;
    private String path;
    private String fullPath;
    private File archivo;

    public InfoArchivo(String nombre, String pesoKB, String path, String fullPath, File archivo) {
        this.nombre = nombre;
        this.pesoKB = pesoKB;
        this.path = path;
        this.fullPath = fullPath;
        this.archivo = archivo;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public InfoArchivo(String nombre, String pesoKB) {
        this.nombre = nombre;
        this.pesoKB = pesoKB;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPesoKB() {
        return pesoKB;
    }

    public void setPesoKB(String pesoKB) {
        this.pesoKB = pesoKB;
    }
}
