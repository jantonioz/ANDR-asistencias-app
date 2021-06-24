package mx.edu.itl.equipo3.asistenciasapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mx.edu.itl.equipo3.asistenciasapp.Objects.Asistencia;
import mx.edu.itl.equipo3.asistenciasapp.R;

public class AdapterDetalles extends RecyclerView.Adapter<AdapterDetalles.ViewHolderDetalles>{

    ArrayList<Asistencia> listaDetalles;

    public AdapterDetalles(ArrayList<Asistencia> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    @NonNull
    @Override
    public ViewHolderDetalles onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detalles, parent, false);
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