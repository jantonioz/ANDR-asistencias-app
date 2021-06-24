package mx.edu.itl.equipo3.asistenciasapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mx.edu.itl.equipo3.asistenciasapp.Adapters.AdapterDetalles;
import mx.edu.itl.equipo3.asistenciasapp.Objects.Asistencia;
import mx.edu.itl.equipo3.asistenciasapp.R;
import mx.edu.itl.equipo3.asistenciasapp.SQLite.DB;

public class DetallesAlumnoActivity extends AppCompatActivity {

    private TextView noControlView;
    private ArrayList<Asistencia> listaDetalles;
    private RecyclerView recyclerViewDetalles;
    private String noControl;
    private int idGrupo;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_alumno);
        noControlView = (TextView)findViewById(R.id.txtTitulo);

        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();

        noControl = extra.getString("noControl");
        idGrupo = extra.getInt("idGrupo");

        noControlView.setText(noControl);

        db = new DB(getApplicationContext());

        listaDetalles = new ArrayList<>();
        recyclerViewDetalles = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerViewDetalles.setLayoutManager(new LinearLayoutManager(this));
        llenarDetalles();

        AdapterDetalles adapter = new AdapterDetalles(listaDetalles);
        recyclerViewDetalles.setAdapter(adapter);
    }

    private void llenarDetalles(){
        listaDetalles = db.getAsistencias(noControl, idGrupo);
    }


}