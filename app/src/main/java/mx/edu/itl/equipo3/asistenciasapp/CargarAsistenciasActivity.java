package mx.edu.itl.equipo3.asistenciasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codekidlabs.storagechooser.StorageChooser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;


public class CargarAsistenciasActivity extends AppCompatActivity {
    public final static int CARGAR_ASISTENCIAS_CODE = 101;

    TextView textViewPath;
    TextView textViewTotalArchivos;

    Button btnLimpiar;
    Button btnCargar;

    RecyclerView cargaAsisRecyclerView;

    ArrayList<InfoArchivo> infoArchivoArrayList;

    AdapterListaArchivos adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_asistencias);
        Objects.requireNonNull(getSupportActionBar()).hide();

        textViewPath = findViewById ( R.id.txtvCargarAsisPath);
        textViewTotalArchivos = findViewById ( R.id.txtvCargarAsisCount);

        btnLimpiar = findViewById ( R.id.btnCargarAsisLimpiar);
        btnCargar = findViewById ( R.id.btnCargarAsisCargar);

        cargaAsisRecyclerView = findViewById ( R.id.recyclerViewCargarAsis);
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
        desactivarControles ();
    }

    public void onClickCargar ( View v ) {
        ArrayList<Alumno> alumnos =
            CargarAsistenciasHelper.obtenerAsistenciasPorAlumno ( infoArchivoArrayList );

        Log.d("ALUMNOS", String.valueOf(alumnos.size()));
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

