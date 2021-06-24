package mx.edu.itl.equipo3.asistenciasapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

import mx.edu.itl.equipo3.asistenciasapp.Adapters.AdapterAsistencias;
import mx.edu.itl.equipo3.asistenciasapp.Objects.Alumno;
import mx.edu.itl.equipo3.asistenciasapp.Objects.Asistencia;
import mx.edu.itl.equipo3.asistenciasapp.Objects.AsistenciaStatus;
import mx.edu.itl.equipo3.asistenciasapp.Objects.Grupo;
import mx.edu.itl.equipo3.asistenciasapp.Objects.Total;
import mx.edu.itl.equipo3.asistenciasapp.R;
import mx.edu.itl.equipo3.asistenciasapp.SQLite.DB;
import mx.edu.itl.equipo3.asistenciasapp.SendEmail.SendMail;

public class ListaTotalAlumnosActivity extends AppCompatActivity {

    private RecyclerView listVTotales;
    private Spinner spinner;

    private ArrayList<Total> totales;
    private ArrayList<Alumno> alumnos;
    private ArrayList<Asistencia> asistencias;

    private ArrayList<Grupo> grupos;
    private ArrayList<String> gruposNombres;

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
                itemClick(position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                armarTotales(1);
                itemClick(1);
            }
        });
        
    }

    private void itemClick(int idGrupo){
        listVTotales = findViewById( R.id.recyclerViewAsistencias );
        listVTotales.setLayoutManager(new LinearLayoutManager( getApplicationContext()));
        listVTotales.setAdapter(new AdapterAsistencias(totales, new AdapterAsistencias.OnItemClickListener() {
            @Override
            public void onItemClick(Total asistencia) {
                Intent intent = new Intent(ListaTotalAlumnosActivity.this, DetallesAlumnoActivity.class);
                intent.putExtra("nombre", asistencia.getNombre());
                intent.putExtra("noControl", asistencia.getNoControl());
                intent.putExtra("idGrupo", idGrupo);
                intent.putExtra("presentes", asistencia.getTotalPresente());
                intent.putExtra("justificados", asistencia.getTotalJustificado());
                startActivity(intent);
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
        asistencias = db.getAsistencias("", idGrupo);
    }

    private void getGrupos () {
        DB db = new DB(getApplicationContext());
        gruposNombres = new ArrayList<>();
        grupos = db.getGrupos();
        for( Grupo grupo : grupos ) {
            gruposNombres.add(grupo.getNombre());
        }
    }

    public void btnEnviarEmailClick ( View v ) {
        String message = "";
        String materia = asistencias.get(0).getGrupo()+"";
        for(Total total : totales){
            message = message + total.getNoControl() + "    " + total.getNombre()  +
                                                                                 "      Total: " + total.getTotal() + "      Porcentaje: " + total.getPorcentaje() + "\n";
        }

        Log.d("Mensaje", materia);
        SendMail sm = new SendMail(this, "angel.14.98@hotmail.com", "ASISTENCIAS "+materia, message);
        sm.execute();
    }

    //Ciadro de dialogo con un layout de login incrustado y botones Aceptar y Cancelar
    private View email_layout;
    private EditText edtEmail;
    public void btnDialogoEmailIncrustadoClick ( View v ) {
        email_layout = getLayoutInflater().inflate( R.layout.email_layout, null );
        edtEmail = email_layout.findViewById( R.id.edtEmail );

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( "Ingresa tu Email" )
                .setIcon(R.drawable.ic_baseline_email_black_24)
                .setView( email_layout )
                .setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!validarEmail(edtEmail.getText().toString())){
                            Toast.makeText(ListaTotalAlumnosActivity.this,"EMAIL NO VÁLIDO",Toast.LENGTH_LONG).show();
                        } else {
                            String message = "";
                            String materia = asistencias.get(0).getGrupo()+"";
                            for(Total total : totales){
                                message = message + total.getNoControl() + "    " + total.getNombre()  +
                                        "      Total: " + total.getTotal() + "      Porcentaje: " + total.getPorcentaje() + "\n";
                            }
                            SendMail sm = new SendMail(ListaTotalAlumnosActivity.this, edtEmail.getText().toString(), "ASISTENCIAS "+materia, message);
                            sm.execute();
                        }
                    }
                })
                .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // cerrar el dialogo sin hacer nada
                    }
                })
                .setCancelable( false )
                .create()
                .show();
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}