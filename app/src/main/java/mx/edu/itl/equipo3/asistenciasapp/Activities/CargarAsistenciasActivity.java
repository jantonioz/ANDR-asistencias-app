package mx.edu.itl.equipo3.asistenciasapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codekidlabs.storagechooser.StorageChooser;

import java.util.ArrayList;
import java.util.Objects;

import mx.edu.itl.equipo3.asistenciasapp.Adapters.AdapterListaArchivos;
import mx.edu.itl.equipo3.asistenciasapp.Helpers.SnackbarHelper;
import mx.edu.itl.equipo3.asistenciasapp.Objects.Alumno;
import mx.edu.itl.equipo3.asistenciasapp.Helpers.CargarAsistenciasHelper;
import mx.edu.itl.equipo3.asistenciasapp.Objects.Grupo;
import mx.edu.itl.equipo3.asistenciasapp.Objects.InfoArchivo;
import mx.edu.itl.equipo3.asistenciasapp.R;
import mx.edu.itl.equipo3.asistenciasapp.SQLite.DB;


public class CargarAsistenciasActivity extends AppCompatActivity {
    TextView textViewPath;
    TextView textViewTotalArchivos;

    Button btnLimpiar;
    Button btnCargar;
    Button btnCargarAsisSelectFolder;

    RecyclerView cargaAsisRecyclerView;

    ArrayList<InfoArchivo> infoArchivoArrayList;
    ArrayList<Grupo> grupos;

    AdapterListaArchivos adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_asistencias);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();

        int enableCargarAsisFolder = extra.getInt("totalAsistencias");

        textViewPath = findViewById ( R.id.txtvCargarAsisPath);
        textViewTotalArchivos = findViewById ( R.id.txtvCargarAsisCount);

        btnLimpiar = findViewById ( R.id.btnCargarAsisLimpiar);
        btnCargar = findViewById ( R.id.btnCargarAsisCargar);
        btnCargarAsisSelectFolder = findViewById( R.id.btnCargarAsisSelectFolder );

        cargaAsisRecyclerView = findViewById ( R.id.recyclerViewCargarAsis);
        btnCargarAsisSelectFolder.setEnabled(enableCargarAsisFolder > 0 ? false : true );
    }

    public void onClickAtras ( View v ) {
        finish ( );
    }

    public void onClickSelectFolder ( View v ) {
        String path = this.getApplicationContext().getExternalFilesDir( Environment.DIRECTORY_DOCUMENTS ).getAbsolutePath();
        final StorageChooser chooser = new StorageChooser.Builder()
                // Specify context of the dialog
                .withActivity(CargarAsistenciasActivity.this)
                .withFragmentManager(getFragmentManager())
                .withMemoryBar(true)
                .allowCustomPath(true)
                .withPredefinedPath(path)
                .setType(StorageChooser.DIRECTORY_CHOOSER)
                .build();

        chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
            @Override
            public void onSelect(String path) {
                textViewPath.setText ( path );
                infoArchivoArrayList = CargarAsistenciasHelper.getFiles ( path );
                grupos = CargarAsistenciasHelper.getGruposFromFiles ( infoArchivoArrayList);

                if ( infoArchivoArrayList.size() <= 0) return;

                activarControles ();

                adapter = new AdapterListaArchivos(infoArchivoArrayList);

                cargaAsisRecyclerView.setAdapter ( adapter );
                cargaAsisRecyclerView.setLayoutManager ( new LinearLayoutManager ( getApplicationContext() ) );

                String label = infoArchivoArrayList.size() + " Archivos";
                textViewTotalArchivos.setText ( label );

            }
        });

            chooser.show();
    }

    public void onClickLimpiar ( View v ) {
        infoArchivoArrayList.clear ();
        adapter.notifyDataSetChanged ();
        textViewPath.setText ( "" );
        textViewTotalArchivos.setText ( "0 Archivos" );
        desactivarControles();
        DB db = new DB(getApplicationContext());
        db.clearAsistencias();
    }

    public void onClickCargar ( View v ) {
        ProgressDialog progress;
        progress = ProgressDialog.show(this, "Cargando Asistencias",
                "Se están procesando " + infoArchivoArrayList.size() + " archivos", true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<Alumno> alumnos =
                            CargarAsistenciasHelper.obtenerAsistenciasPorAlumno ( infoArchivoArrayList, grupos );
                    ArrayList<Grupo> dbGrupos = CargarAsistenciasHelper.guardarGrupos ( grupos, getApplicationContext() );

                    CargarAsistenciasHelper.guardarAsistencias ( alumnos, dbGrupos, getApplicationContext() );

                    progress.dismiss();
                    SnackbarHelper.showSnackbar ( v, "Asistencias cargadas correctamente", true);
                    btnCargar.setEnabled ( false );
                } catch (Exception e) {
                    SnackbarHelper.showSnackbar ( v, "Hubo un problema al cargar las asistencias", true);
                }
            }
        }, 0);
    }

    private void activarControles () {
        establecerControles ( true, true );
    }

    private void desactivarControles () {
        establecerControles ( false, false );
    }

    private void establecerControles ( boolean setBtnLimpiarEnabled, boolean setBtnCargarEnabled ) {
        btnLimpiar.setEnabled ( setBtnLimpiarEnabled );
        btnCargar.setEnabled ( setBtnCargarEnabled );
    }
}

