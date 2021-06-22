package mx.edu.itl.equipo3.asistenciasapp.Activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import mx.edu.itl.equipo3.asistenciasapp.R;
import mx.edu.itl.equipo3.asistenciasapp.SQLite.DB;
import teclag.c17130854.androlib.util.permisos.ChecadorDePermisos;
import teclag.c17130854.androlib.util.permisos.PermisoApp;

public class MainActivity extends AppCompatActivity {

    private PermisoApp[] permisosReq = new PermisoApp [] {
            new PermisoApp ( Manifest.permission.READ_EXTERNAL_STORAGE, "Lectura SD Card", true ),
    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ChecadorDePermisos.checarPermisos ( this, permisosReq );

        DB db = new DB(getApplicationContext());
        db.clearDataBase();
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

        /*public void btnEnviarEmailClick ( View v ) {
        SendMail sm = new SendMail(this, "angel.14.98@hotmail.com", "Lista", "Hola buenas tardes");
        sm.execute();
    }*/


        /**/
}