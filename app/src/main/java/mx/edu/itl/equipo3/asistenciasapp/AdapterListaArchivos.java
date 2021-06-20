package mx.edu.itl.equipo3.asistenciasapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

class AdapterListaArchivos extends RecyclerView.Adapter<AdapterListaArchivos.ViewHolder> {

    ArrayList<InfoArchivo> infoArchivoArrayList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewNombre;
        private final TextView textViewPeso;
        private final TextView textViewPath;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textViewNombre = (TextView) view.findViewById(R.id.txtCargaAsisListaArchivoNombre);
            textViewPeso = (TextView) view.findViewById(R.id.txtCargaAsisListaArchivoPeso);
            textViewPath = (TextView) view.findViewById(R.id.txtCargaAsisListaArchivoPath);
        }

        public TextView getTextViewNombre() {
            return textViewNombre;
        }

        public TextView getTextViewPeso() {
            return textViewPeso;
        }

        public TextView getTextViewPath() {
            return textViewPath;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet ArrayList<InfoArchivo> containing the data to populate views to be used
     * by RecyclerView.
     */
    public AdapterListaArchivos(ArrayList<InfoArchivo> dataSet) {
        infoArchivoArrayList = dataSet;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.carga_asistencia_lista_archivos, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextViewNombre().setText(infoArchivoArrayList.get ( position).getNombre() );
        viewHolder.getTextViewPeso().setText(infoArchivoArrayList.get ( position).getPesoKB() );
        viewHolder.getTextViewPath().setText(infoArchivoArrayList.get ( position).getPath() );
    }

    @Override
    public int getItemCount() {
        return infoArchivoArrayList.size();
    }
}
