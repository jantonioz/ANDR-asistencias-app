package mx.edu.itl.equipo3.asistenciasapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mx.edu.itl.equipo3.asistenciasapp.Adapters.AdapterAsistencias;
import mx.edu.itl.equipo3.asistenciasapp.Objects.Alumno;
import mx.edu.itl.equipo3.asistenciasapp.Objects.Asistencia;
import mx.edu.itl.equipo3.asistenciasapp.Objects.AsistenciaStatus;
import mx.edu.itl.equipo3.asistenciasapp.Objects.Grupo;
import mx.edu.itl.equipo3.asistenciasapp.Objects.Total;
import mx.edu.itl.equipo3.asistenciasapp.R;
import mx.edu.itl.equipo3.asistenciasapp.SQLite.DB;

public class ListaTotalAlumnosActivity extends AppCompatActivity {

    private RecyclerView listVTotales;
    private Spinner spinner;

    ArrayList<Total> totales;
    ArrayList<Alumno> alumnos;
    ArrayList<Asistencia> asistencias;

    ArrayList<Grupo> grupos;
    ArrayList<String> gruposNombres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_total_alumnos);
        getAlumnos();
        getGrupos();

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, gruposNombres));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                armarTotales(position+1);
                itemClick();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                armarTotales(1);
                itemClick();
            }
        });
        
    }

    private void itemClick(){
        listVTotales = findViewById( R.id.recyclerViewAsistencias );
        listVTotales.setLayoutManager(new LinearLayoutManager( getApplicationContext()));
        listVTotales.setAdapter(new AdapterAsistencias(totales, new AdapterAsistencias.OnItemClickListener() {
            @Override
            public void onItemClick(Total asistencia) {
                Toast.makeText(getApplicationContext(), asistencia.getNombre(), Toast.LENGTH_LONG).show();
            }
        }));
    }

    private void armarTotales(int idGrupo){
        totales = new ArrayList<>();
        int totalPresente = 0;
        int totalJustificado = 0;
        boolean exist;
        int clases = 0;

        getAsistencias(idGrupo);

        for(Alumno alu: alumnos){
            exist = false;
            for(Asistencia asis: asistencias){
                if(asis.getNoControl().equals(alu.getNoControl())){
                    exist = true;

                    if(asis.getStatus().equals(AsistenciaStatus.PRESENTE)){
                        totalPresente++;

                    } else if (asis.getStatus().equals(AsistenciaStatus.JUSTIFICADO)){
                        totalJustificado++;
                    }
                    for (Grupo grupo : grupos ){
                        if(grupo.getNombre().equals(asis.getGrupo()+"")){
                            clases = grupo.getClases();
                            Log.d("grupos", grupo.getClases()+"");
                        }
                    }
                }
            }
            if(exist){
                totales.add(new Total(alu.getNoControl(), alu.getNombreCompleto(), totalPresente, totalJustificado, totalPresente+totalJustificado,
                                ( ( ( totalPresente + totalJustificado) * 100 ) / clases )+"%"));
                totalPresente = 0;
                totalJustificado = 0;
                clases = 0;
            }
        }
    }

    private void getAlumnos (){
        DB db = new DB(getApplicationContext());
        alumnos = db.getAlumnos();
    }

    private void getAsistencias (int idGrupo){
        DB db = new DB(getApplicationContext());
        asistencias = new ArrayList<>();
        asistencias = db.getAsistencias("17130850", idGrupo);
    }

    private void getGrupos () {
        DB db = new DB(getApplicationContext());
        gruposNombres = new ArrayList<>();
        grupos = db.getGrupos();
        for( Grupo grupo : grupos ) {
            gruposNombres.add(grupo.getNombre());
        }
    }
}