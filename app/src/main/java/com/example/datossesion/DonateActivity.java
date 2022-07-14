package com.example.datossesion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public class DonateActivity extends AppCompatActivity {

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_donate );

        session = new SessionManager(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater ();
        inflater.inflate ( R.menu.menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId ()){
            case R.id.home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.ajustes:
                Toast.makeText ( getApplicationContext (), "Has pulsado Ajustes", Toast.LENGTH_LONG ).show ();
                return true;
            case R.id.ayuda:
                Toast.makeText ( getApplicationContext (), "Has pulsado Ayuda", Toast.LENGTH_LONG ).show ();
                return true;
            case R.id.extra:
                Toast.makeText ( getApplicationContext (), "Has pulsado Extra", Toast.LENGTH_LONG ).show ();
                return true;
            case R.id.cerrarsesion:
                session.logOut();
                return true;
            default:
                return super.onOptionsItemSelected ( item );
        }

    }

}