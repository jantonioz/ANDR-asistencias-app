package mx.edu.itl.equipo3.asistenciasapp.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import mx.edu.itl.equipo3.asistenciasapp.Objects.Asistencia;

public class DB extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "asistencias.db";
    private static final int VERSION_BD = 1;
    //Tablas
    private static final String TABLE_ALUMNOS = "CREATE TABLE ALUMNOS " +
                                                "(NOCONTROL VARCHAR PRIMARY KEY, NOMBRE VARCHAR)";
    private static final String TABLE_GRUPOS = "CREATE TABLE GRUPOS " +
                                                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE VARCHAR, MAESTRO VARCHAR)";
    private static final String TABLE_ASISTENCIAS = "CREATE TABLE ASISTENCIAS " +
                                                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, FECHASTR VARCHAR, ESTATUS VARCHAR, ID_GRUPO INTEGER, NOCONTROL_ALUMNO VARCHAR," +
                                                "FOREIGN KEY(NOCONTROL_ALUMNO) REFERENCES ALUMNOS(NOCONTROL), FOREIGN KEY(ID_GRUPO) REFERENCES GRUPOS(ID), " +
                                                "UNIQUE(FECHASTR, ID_GRUPO, NOCONTROL_ALUMNO))";

    public DB (Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_GRUPOS);
        db.execSQL(TABLE_ALUMNOS);
        db.execSQL(TABLE_ASISTENCIAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS GRUPOS");
        db.execSQL("DROP TABLE IF EXISTS ALUMNOS");
        db.execSQL("DROP TABLE IF EXISTS ASISTENCIAS");
        onCreate(db);
    }

    public void clearDataBase(){
        SQLiteDatabase dbWrite = getWritableDatabase();
        dbWrite.execSQL("DROP TABLE IF EXISTS GRUPOS");
        dbWrite.execSQL("DROP TABLE IF EXISTS ALUMNOS");
        dbWrite.execSQL("DROP TABLE IF EXISTS ASISTENCIAS");
        onCreate(dbWrite);
    }

    public void addAlumno(String noControl, String nombre) {
        SQLiteDatabase dbWrite = getWritableDatabase();
        if( dbWrite != null ) {
            dbWrite.execSQL("INSERT INTO ALUMNOS VALUES ('"+noControl+"','"+nombre+"')");
            dbWrite.close();
        }
    }

    public void addGrupo(String nombre, String maestro) {
        SQLiteDatabase dbWrite = getWritableDatabase();
        if( dbWrite != null ) {
            dbWrite.execSQL("INSERT INTO GRUPOS VALUES ('"+nombre+"','"+maestro+"')");
            dbWrite.close();
        }
    }

    public void addAsistencia(String fechaStr, String estatus, int idGrupo, String noControl) {
        SQLiteDatabase dbWrite = getWritableDatabase();
        if( dbWrite != null ) {
            dbWrite.execSQL("INSERT INTO ASISTENCIAS VALUES ('"+fechaStr+"','"+estatus+"','"+idGrupo+"','"+noControl+"')");
            dbWrite.close();
        }
    }

    public ArrayList<Asistencia> getAsistencias (String noControl, int idGrupo) {
        SQLiteDatabase dbRead = getReadableDatabase();
        Cursor cursor = dbRead.rawQuery("SELECT * FROM ASISTENCIAS WHERE NOCONTROL_ALUMNO = "+noControl+" AND ID_GRUPO = "+idGrupo, null);
        ArrayList<Asistencia> asistencias = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                //asistencias.add(new Asistencia(cursor.getString(1), cursor.getString(4), cursor.getString(5), new Date(), cursor.getString(3)));
            }while(cursor.moveToNext());
        }
        return asistencias;
    }
}
