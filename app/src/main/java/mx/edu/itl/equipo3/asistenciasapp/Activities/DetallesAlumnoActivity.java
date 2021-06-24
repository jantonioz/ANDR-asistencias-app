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

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

import mx.edu.itl.equipo3.asistenciasapp.Adapters.AdapterDetalles;
import mx.edu.itl.equipo3.asistenciasapp.Objects.Asistencia;
import mx.edu.itl.equipo3.asistenciasapp.Objects.Grupo;
import mx.edu.itl.equipo3.asistenciasapp.R;
import mx.edu.itl.equipo3.asistenciasapp.SQLite.DB;

public class DetallesAlumnoActivity extends AppCompatActivity {

    private TextView noControlView;
    private ArrayList<Asistencia> listaDetalles;
    private RecyclerView recyclerViewDetalles;
    private String nombre, noControl;
    private int idGrupo, presentes, justificados;
    private DB db;
    private ArrayList<Grupo> grupos;
    private int clases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_alumno);
        noControlView = (TextView)findViewById(R.id.txtTitulo);

        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();

        nombre = extra.getString("nombre");
        noControl = extra.getString("noControl");
        idGrupo = extra.getInt("idGrupo");
        presentes = extra.getInt("presentes");
        justificados = extra.getInt("justificados");


        noControlView.setText(nombre);

        db = new DB(getApplicationContext());

        getGrupos();

        listaDetalles = new ArrayList<>();
        recyclerViewDetalles = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerViewDetalles.setLayoutManager(new LinearLayoutManager(this));
        llenarDetalles();

        AdapterDetalles adapter = new AdapterDetalles(listaDetalles);
        recyclerViewDetalles.setAdapter(adapter);

        contruirGrafica(presentes, justificados, clases);

    }

    private void llenarDetalles(){
        listaDetalles = db.getAsistencias(noControl, idGrupo);
    }

    private void getGrupos () {;
        grupos = db.getGrupos();

        for( Grupo grupo : grupos ) {
            if(grupo.getId() == idGrupo ) {
                clases = grupo.getClases();
            }
        }
    }

    private void contruirGrafica(int presentes, int justificados, int clases){
        Pie pie = AnyChart.pie();
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Presentes", presentes));
        data.add(new ValueDataEntry("Justificados", justificados));
        data.add(new ValueDataEntry("Faltas", clases-presentes-justificados));
        pie.data(data);
        pie.title("Detalles Asistencias");
        AnyChartView anyChartView = (AnyChartView) findViewById(R.id.anyChartView);
        anyChartView.setChart(pie);
    }


}