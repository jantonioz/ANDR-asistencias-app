package mx.edu.itl.equipo3.asistenciasapp.Helpers;

import android.annotation.SuppressLint;
import android.util.ArrayMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.edu.itl.equipo3.asistenciasapp.Alumno;
import mx.edu.itl.equipo3.asistenciasapp.Asistencia;
import mx.edu.itl.equipo3.asistenciasapp.InfoArchivo;

public class CargarAsistenciasHelper {
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );

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
                    String [] grupo_fecha = getFecha_GrupoDeNombreArchivo ( dirFile.getName() );
                    infoArchivos.add ( new InfoArchivo (
                                dirFile.getName(),
                                String.valueOf(dirFile.length()/1024) + "KB",
                                path,
                                path + File.separator + dirFile.getName(),
                                dirFile,
                                grupo_fecha[0],
                                grupo_fecha[1]
                            )
                    );
                }
            }
        }
        return infoArchivos;
    }

    private static String[] getFecha_GrupoDeNombreArchivo ( String nombre ) {
        if ( !esNombreArchivoValido ( nombre ) ) return new String[]{"", ""};
        String [] datos = nombre.split ( " " );
        String fecha = datos [ 0 ];
        String grupo = datos [ 1 ].split ( "-" )[0];

        return new String[] {grupo, fecha};
    }

    private static boolean esNombreArchivoValido ( String nombre ) {
        return nombre.trim().toLowerCase().matches ( "(?i)[0-9]{4}-[0-9]{2}-[0-9]{2}\\s*(andr|tap|la2)-asistencia\\.txt$" );
    }

    public static ArrayList<Alumno> obtenerAsistenciasPorAlumno (ArrayList<InfoArchivo> archivos ) {
        return procesarArchivos ( archivos );
    }

    private static ArrayList<Alumno> procesarArchivos(ArrayList<InfoArchivo> archivos) {
        ArrayMap<String, Alumno> alumnosArrayMap = new ArrayMap<>();
        ArrayList<Alumno> alumnos = new ArrayList<>();


        for (InfoArchivo archivo : archivos) {
            procesarArchivo ( alumnosArrayMap, archivo );
        }

        alumnosArrayMap.values ().forEach(new Consumer<Alumno>() {
            @Override
            public void accept(Alumno alumno) {
                alumnos.add(alumno);
            }
        });


        alumnos.sort(new Comparator<Alumno>() {
            @Override
            public int compare(Alumno alumno, Alumno t1) {
                return alumno.getNoControl().compareTo ( t1.getNoControl() );
            }
        });

        return alumnos;
    }

    private static void procesarArchivo ( ArrayMap<String, Alumno> alumnos, InfoArchivo archivo ) {
        try {
            BufferedReader br = new BufferedReader( new FileReader ( archivo.getArchivo() ) );
            String linea;

            while ( ( linea = br.readLine() ) != null ) {
                Asistencia asistencia = obtenerDatosAsistencia ( linea, archivo.getFecha() );

                if ( asistencia == null ) continue;

                if ( alumnos.containsKey ( asistencia.getNoControl() ) ) {

                    Objects.requireNonNull( alumnos.get( asistencia.getNoControl() ) )
                            .getAsistencias().add ( asistencia );

                } else {
                    Alumno alumnoNuevo =
                        new Alumno (
                                asistencia.getNoControl(),
                                asistencia.getNombre(),
                                asistencia.getNombre().split ( "" )
                        );
                    alumnoNuevo.getAsistencias().add ( asistencia );
                    alumnos.put( alumnoNuevo.getNoControl(), alumnoNuevo );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final static String regexMatch =
            "(?i).*\\s([A-Za-z]?[0-9]{9}[A-Za-z]?|[A-Za-z]?[0-9]{8}[A-Za-z]?)\\s*([\\w\\s]+)(to)?\\s*(everyone)?\\s*:\\s*(PRESENTE|JUSTIFICADO)$";

    private static boolean esAsistenciaValida ( String linea, String regex ) {
        return linea
                .toLowerCase()
                .matches ( regex);
    }

    private static Asistencia obtenerDatosAsistencia ( String linea, String fecha) {
        String posibleAsistencia = sanitizarLinea ( linea );
        if ( !esAsistenciaValida ( posibleAsistencia, regexMatch) ) return null;

        return obtenerAsistenciaAlumno ( posibleAsistencia, fecha );
    }

    private static Asistencia obtenerAsistenciaAlumno ( String linea, String fecha ) {
        try {
            Pattern pattern = Pattern.compile ( regexMatch );
            Matcher partes = pattern.matcher ( linea );

            if ( partes.find () ) {
                String noControl = partes.group ( 1 );
                String nombre =
                        Objects.requireNonNull( partes.group ( 2 ) )
                                .replaceAll("to\\s*Everyone", "")
                                .trim();

                String estado = Objects.requireNonNull( partes.group ( 5 ) ).toUpperCase();


                return new Asistencia ( fecha, nombre, noControl, dateFormat.parse( fecha ), estado );

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String sanitizarLinea ( String linea ) {
        return linea + "".replace('_', ' ').replace('.', ' ').trim();
    }
}