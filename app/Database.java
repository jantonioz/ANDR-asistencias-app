package mx.edu.itl.equipo3.asistenciasapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper  {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String[] tables = {"CREATE TABLE alumnos (NoControl string, Nombre string);",
                " CREATE TABLE asistencias (NoControl string Estatus string);",
        };

        for ( String tabla : tables ) {
            db.execSQL(tabla);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
