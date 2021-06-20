package mx.edu.itl.equipo3.asistenciasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codekidlabs.storagechooser.StorageChooser;

import java.util.ArrayList;
import java.util.Objects;

public class CargarAlumnosActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_carga_alumnos);

        Objects.requireNonNull(getSupportActionBar()).hide();

        textViewPath = findViewById ( R.id.txtvCargarAluPath);
        textViewTotalArchivos = findViewById ( R.id.txtvCargarAluCount);

        btnLimpiar = findViewById ( R.id.btnCargarAluLimpiar);
        btnCargar = findViewById ( R.id.btnCargarAluCargar);

        cargaAsisRecyclerView = findViewById ( R.id.recyclerViewCargarAlu);
    }

    public void onClickAtras ( View v ) {
        finish ( );
    }

    public void onClickSelectFolder ( View v ) {
        String path = this.getApplicationContext().getExternalFilesDir( Environment.DIRECTORY_DOCUMENTS ).getAbsolutePath();
        final StorageChooser chooser = new StorageChooser.Builder()
                .withActivity(CargarAlumnosActivity.this)
                .withFragmentManager(getFragmentManager())
                .withMemoryBar(true)
                .allowCustomPath(true)
                .withPredefinedPath(path)
                .setType(StorageChooser.DIRECTORY_CHOOSER)
                .build();

        chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
            @Override
            public void onSelect(String path) {
                textViewPath.setText(path);
                infoArchivoArrayList = CargarAsistenciasHelper.getFiles(path);

                if (infoArchivoArrayList.size() <= 0) return;

                activarControles();

                adapter = new AdapterListaArchivos(infoArchivoArrayList);

                cargaAsisRecyclerView.setAdapter(adapter);
                cargaAsisRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                String label = infoArchivoArrayList.size() + " Archivos";
                textViewTotalArchivos.setText(label);
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