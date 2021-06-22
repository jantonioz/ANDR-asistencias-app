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

import mx.edu.itl.equipo3.asistenciasapp.Objects.Asistencia;
import mx.edu.itl.equipo3.asistenciasapp.R;
import mx.edu.itl.equipo3.asistenciasapp.SQLite.DB;

public class DetallesAlumnoActivity extends AppCompatActivity {

    TextView noControlView;
    ArrayList<Asistencia> listaDetalles;
    RecyclerView recyclerViewDetalles;
    private String noControl;
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_alumno);
        noControlView = (TextView)findViewById(R.id.txtTitulo);

        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();

        noControl = extra.getString("noControl");
        noControlView.setText(noControl);

        db = new DB(getApplicationContext());

        listaDetalles = new ArrayList<>();
        recyclerViewDetalles = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerViewDetalles.setLayoutManager(new LinearLayoutManager(this));
        llenarDetalles();

        AdaptadorDetalles adapter = new AdaptadorDetalles(listaDetalles);
        recyclerViewDetalles.setAdapter(adapter);
    }

    private void llenarDetalles(){
        listaDetalles = db.getAsistencias(noControl, 1);
    }

    class AdaptadorDetalles extends RecyclerView.Adapter<AdaptadorDetalles.ViewHolderDetalles>{

        ArrayList<Asistencia> listaDetalles;

        public AdaptadorDetalles(ArrayList<Asistencia> listaDetalles) {
            this.listaDetalles = listaDetalles;
        }

        @NonNull
        @Override
        public ViewHolderDetalles onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detalles, null, false);
            return new ViewHolderDetalles(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderDetalles holder, int position) {
            holder.fecha.setText(listaDetalles.get(position).getFecha());
            holder.estatus.setText(listaDetalles.get(position).getStatus().toString());
        }

        @Override
        public int getItemCount() {
            return listaDetalles.size();
        }

        public class ViewHolderDetalles extends RecyclerView.ViewHolder {
            TextView fecha, estatus;

            public ViewHolderDetalles(@NonNull View itemView) {
                super(itemView);
                fecha = (TextView) itemView.findViewById(R.id.txtFecha);
                estatus = (TextView) itemView.findViewById(R.id.txtEstatus);
            }
        }
    }
}