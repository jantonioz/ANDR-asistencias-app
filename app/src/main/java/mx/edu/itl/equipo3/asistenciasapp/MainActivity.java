package mx.edu.itl.equipo3.asistenciasapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Date;

import mx.edu.itl.equipo3.asistenciasapp.SQLite.DB;
import mx.edu.itl.equipo3.asistenciasapp.SendEmail.SendMail;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*final DB db = new DB(getApplicationContext());
        db.clearDataBase();*/
    }

    public void btnListaTotalAlumnosClick ( View v ) {
        Intent intent = new Intent ( this, ListaTotalAlumnosActivity.class );
        startActivity( intent );
    }

    /*public void btnEnviarEmailClick ( View v ) {
        SendMail sm = new SendMail(this, "angel.14.98@hotmail.com", "Lista", "Hola buenas tardes");
        sm.execute();
    }*/
}