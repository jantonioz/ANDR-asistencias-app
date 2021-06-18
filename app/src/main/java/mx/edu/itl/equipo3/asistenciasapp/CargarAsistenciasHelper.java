package mx.edu.itl.equipo3.asistenciasapp;

import java.io.File;
import java.util.ArrayList;

public class CargarAsistenciasHelper {

    public static ArrayList<InfoArchivo> getFiles(String path) {
        // gets the files in the directory
        File fileDirectory = new File( path );
        // lists all the files into an array
        File[] dirFiles = fileDirectory.listFiles();

        ArrayList<InfoArchivo> infoArchivos = new ArrayList<>();

        assert dirFiles != null;
        if (dirFiles.length != 0) {
            for (File dirFile : dirFiles) {
                if ( !dirFile.isFile() && dirFile.isDirectory() ) {
                    ArrayList<InfoArchivo> infoArchivosSub = getFiles( dirFile.getAbsolutePath() );
                    infoArchivos.addAll(infoArchivosSub);
                }

                else {
                    infoArchivos.add ( new InfoArchivo (
                            dirFile.getName(),
                            String.valueOf(dirFile.length()/1024) + "KB",
                            path,
                            path + File.separator + dirFile.getName(),
                            dirFile )
                    );
                    String fileOutput = dirFile.toString();
                    System.out.println(fileOutput);
                }
            }
        }
        return infoArchivos;
    }

    public static ArrayList<Alumno> obtenerAsistenciasPorAlumno ( ArrayList<InfoArchivo> archivos ) {
        /*
        TODO:
          1. Leer todos los archivos
          2. Por cada archivo leer linea por linea y testear contra los formatos (regex)
          3. Obtener el numero de control y nombre -> crear alumno si no existe -> mantener arreglo de alumnos
          4. Agregar asistencia al alumno
          5. Regresar listado de alumnos
         */
        return new ArrayList<Alumno> ();
    }
}
