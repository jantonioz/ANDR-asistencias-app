package mx.edu.itl.equipo3.asistenciasapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import mx.edu.itl.equipo3.asistenciasapp.Objects.Alumno;
import mx.edu.itl.equipo3.asistenciasapp.R;
import mx.edu.itl.equipo3.asistenciasapp.SQLite.DB;

public class ListaTotalAlumnosActivity extends AppCompatActivity {

    private ListView listVTotales;
    private final String [] nosControl = {"17130848", "17130878", "17001231", "17189867"};
    private final String [] nombres = {"Juan Castillo", "Ernesto Perez", "Gustavo Díaz", "Maria García"};
    private final int [] presentes = {50,50,49,48};
    private final int [] justificadas = {0,0,1,2};
    private final int [] totales = {50,50,50,50};
    private final String [] porcentajes = {"100%","100%","100%","100%"};

    ArrayList<Alumno> alumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_total_alumnos);

        listVTotales = findViewById( R.id.listVTotales );
        MiAdaptador adaptador = new MiAdaptador (this, nosControl, nombres, presentes,
                                            justificadas, totales, porcentajes);
        listVTotales.setAdapter( adaptador );

        listVTotales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent ( ListaTotalAlumnosActivity.this, DetallesAlumnoActivity.class );
                intent.putExtra("noControl", nosControl [position]);
                startActivity( intent );
            }
        });
        getAlumnos();
    }

    //PRUEBA PARA VER SI LOS ALUMNOS SE GUARDARON EN BD
    private void getAlumnos (){
        DB db = new DB(getApplicationContext());
        alumnos = db.getAlumnos();

        for (Alumno alu : alumnos ){
            Log.d("alu", alu.getNoControl() + " "+ alu.getNombreCompleto());;
        }


    }

    class  MiAdaptador extends ArrayAdapter {
        private Context context;
        private  String [] nosControl;
        private  String [] nombres;
        private  int    [] presentes;
        private  int    [] justificadas;
        private  int    [] totales;
        private  String [] porcentajes;

        public MiAdaptador(Context context, String[] nosControl, String[] nombres, int[] presentes, int[] justificadas, int[] totales, String[] porcentajes) {
            super(context, R.layout.list_totales, R.id.txtNoControl, nombres);
            this.context = context;
            this.nosControl = nosControl;
            this.nombres = nombres;
            this.presentes = presentes;
            this.justificadas = justificadas;
            this.totales = totales;
            this.porcentajes = porcentajes;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if( convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                convertView = layoutInflater.inflate( R.layout.list_totales, parent, false );
            }

            TextView noControl = convertView.findViewById( R.id.txtNoControl );
            TextView nombre = convertView.findViewById( R.id.txtNombre );
            TextView presente = convertView.findViewById( R.id.txtPresente );
            TextView justificada = convertView.findViewById( R.id.txtJustificada );
            TextView total = convertView.findViewById( R.id.txtTotal );
            TextView porcentaje = convertView.findViewById( R.id.txtPorcentaje );

            noControl.setText( nosControl [ position ]);
            nombre.setText( nombres [ position ]);
            presente.setText( presentes [ position ]+"");
            justificada.setText( justificadas [ position ]+"");
            total.setText( totales [ position ]+"");
            porcentaje.setText( porcentajes [ position ]);

            return convertView;
        }
    }
}