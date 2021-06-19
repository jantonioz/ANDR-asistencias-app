package mx.edu.itl.equipo3.asistenciasapp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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

    }

    public void btnOnClickLanzarCargarAsistencias ( View v ) {
        Intent intent = new Intent ( this, CargarAsistenciasActivity.class );
        startActivity ( intent );
    }
}