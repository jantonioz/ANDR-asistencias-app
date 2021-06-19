package mx.edu.itl.equipo3.asistenciasapp.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mx.edu.itl.equipo3.asistenciasapp.Asistencia;

public class DB extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "asistencias.db";
    private static final int VERSION_BD = 1;
    //Tablas
    private static final String TABLE_ALUMNOS = "CREATE TABLE ALUMNOS (NOCONTROL VARCHAR PRIMARY KEY, NOMBRE VARCHAR, ID_GRUPO INT, FOREIGN KEY(ID_GRUPO) REFERENCES GRUPOS(ID))";
    private static final String TABLE_GRUPOS = "CREATE TABLE GRUPOS (ID INT PRIMARY KEY, NOMBRE VARCHAR, MAESTRO VARCHAR)";
    private static final String TABLE_ASISTENCIAS = "CREATE TABLE ASISTENCIAS (ID INT PRIMARY KEY, FECHASTR VARCHAR, FECHA DATE, ESTATUS VARCHAR, NOMBRE VARCHAR, NOCONTROL_ALUMNO VARCHAR, FOREIGN KEY(NOCONTROL_ALUMNO) REFERENCES ALUMNOS(NOCONTROL))";

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

    public void addAlumno(String noControl, String nombre, int grupoId) {
        SQLiteDatabase dbWrite = getWritableDatabase();
        if( dbWrite != null ) {
            dbWrite.execSQL("INSERT INTO ALUMNOS VALUES ('"+noControl+"','"+nombre+"','"+grupoId+"')");
            dbWrite.close();
        }
    }

    public void addGrupo(int id, String nombre, String maestro) {
        SQLiteDatabase dbWrite = getWritableDatabase();
        if( dbWrite != null ) {
            dbWrite.execSQL("INSERT INTO GRUPOS VALUES ('"+id+"','"+nombre+"','"+maestro+"')");
            dbWrite.close();
        }
    }

    public void addAsistencia(int id, String noControl, String fechaStr, Date fecha, String estatus, String nombre) {
        SQLiteDatabase dbWrite = getWritableDatabase();
        if( dbWrite != null ) {
            dbWrite.execSQL("INSERT INTO ASISTENCIAS VALUES ('"+id+"','"+fechaStr+"','"+fecha+"','"+estatus+"','"+nombre+"','"+noControl+"')");
            dbWrite.close();
        }
    }

    public ArrayList<Asistencia> getAsistencias (String noControl) {
        SQLiteDatabase dbRead = getReadableDatabase();
        Cursor cursor = dbRead.rawQuery("SELECT * FROM ASISTENCIAS WHERE NOCONTROL_ALUMNO = "+noControl, null);
        ArrayList<Asistencia> asistencias = new ArrayList<>();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        if(cursor.moveToFirst()){
            do{
                asistencias.add(new Asistencia(cursor.getString(1), cursor.getString(4), cursor.getString(5), new Date(), cursor.getString(3)));
            }while(cursor.moveToNext());
        }
        return asistencias;
    }
}
