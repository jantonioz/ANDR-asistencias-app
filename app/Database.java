package mx.edu.itl.equipo3.asistenciasapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper  {

    static final String nombreBD="Asistencias";

    static final String tablaAlumnos = "TAlumnos";
    static final String noControl="noControl";
    static final String nombre="nombreCompleto";

    static final String tablaAsistencias = "TAsistencias";
    static final String noControlAs="noControlAs";
    static final String status = "status";

    static final String viewTotal = "ViewTotal";

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

 /*   @Override
    public void onCreate(SQLiteDatabase db) {

        String[] tables = {"CREATE TABLE alumnos (NoControl string, Nombre string);",
                " CREATE TABLE asistencias (NoControl string Estatus string);",
        };

        for ( String tabla : tables ) {
            db.execSQL(tabla);
        }
    }
*/
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL("CREATE TABLE "+tablaAlumnos+" ("+noControl+ "INTEGER PRIMARY KEY ,"+nombre+"TEXT)");

        db.execSQL("CREATE TABLE "+tablaAsistencias+" ("+noControlAs+" INTEGER PRIMARY KEY AUTOINCREMENT, "+status+"TEXT)");

        /*
        db.execSQL("CREATE VIEW "+viewTotal+
                    " AS SELECT "+tablaAlumnos+"."+noControl+" AS numControl,"+
                    " "+tablaAlumnos+"."+nombre+","+
                    " "+tablaAsistencias+"."+noControlAs+""+
                    " FROM "+tablaAlumnos+" JOIN "+tablaAsistencias+
                    " ON "+tablaAlumnos+"."+noControlAs+"="+tablaAsistencias+"."+noControlAs);
        */

        ///NO ME SALE LA VISTA AAAA
      //  db.execSQL("SELECT COUNT (*) FROM"+tablaAsistencias+"WHERE"+status+">=1");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+tablaAlumnos);
        db.execSQL("DROP TABLE IF EXISTS "+tablaAsistencias);


    }
}
