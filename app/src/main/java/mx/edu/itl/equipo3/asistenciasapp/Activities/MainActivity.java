package mx.edu.itl.equipo3.asistenciasapp.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import mx.edu.itl.equipo3.asistenciasapp.Helpers.SnackbarHelper;
import mx.edu.itl.equipo3.asistenciasapp.Objects.Alumno;
import mx.edu.itl.equipo3.asistenciasapp.Objects.Asistencia;
import mx.edu.itl.equipo3.asistenciasapp.R;
import mx.edu.itl.equipo3.asistenciasapp.SQLite.DB;
import teclag.c17130854.androlib.util.permisos.ChecadorDePermisos;
import teclag.c17130854.androlib.util.permisos.PermisoApp;

public class MainActivity extends AppCompatActivity {

    TextView txtvCountAsis;
    TextView txtvCountAlu;

    private PermisoApp[] permisosReq = new PermisoApp [] {
            new PermisoApp ( Manifest.permission.READ_EXTERNAL_STORAGE, "Lectura SD Card", true ),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ChecadorDePermisos.checarPermisos ( this, permisosReq );

        txtvCountAsis = findViewById ( R.id.txtvCountAsis );
        txtvCountAlu = findViewById ( R.id.txtvCountAlumnos );

        DB db = new DB(getApplicationContext());
        cargarDatos ( db );
        //db.clearDataBase();
    }

    public void btnOnClickLanzarCargarAsistencias ( View v ) {
        Intent intent = new Intent ( this, CargarAsistenciasActivity.class );
        startActivity ( intent );
    }

    public void btnOnClickLanzarCargarAlumnos ( View v ) {
        Intent intent = new Intent ( this, CargarAlumnosActivity.class );
        startActivity ( intent );
    }

    public void btnListaTotalAlumnosClick ( View v ) {
        Intent intent = new Intent ( this, ListaTotalAlumnosActivity.class );
        startActivity( intent );
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        DB db = new DB(getApplicationContext());
        cargarDatos ( db );
    }

    public void btnClickLimpiarDB (View v ) {
        ProgressDialog progress;
        progress = ProgressDialog.show(this, "Actualizando db",
                "Se están eliminando los registros de la base de datos", true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DB db = new DB(getApplicationContext());
                db.clearDataBase();
                cargarDatos ( db );

                progress.dismiss();
                SnackbarHelper.showSnackbar ( v, "Datos eliminados correctamente", true);
            }
        }, 0);
    }

    public void cargarDatos ( DB db ) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<Asistencia> asistencias = db.getAllAsistencias ( );
                ArrayList<Alumno> alumnos = db.getAlumnos ( );

                txtvCountAlu.setText ( alumnos.size()+ " Alumnos" );
                txtvCountAsis.setText ( asistencias.size()+ " Asistencias" );
            }
        }, 0);
    }
}