package mx.edu.itl.equipo3.asistenciasapp.Helpers;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarHelper {
    public static void showSnackbar (View view, String mensaje, boolean corto) {
        Snackbar mySnackbar = Snackbar.make ( view, "Alumnos cargados", corto ? Snackbar.LENGTH_SHORT : Snackbar.LENGTH_LONG );
        mySnackbar.show ();
    }
}
