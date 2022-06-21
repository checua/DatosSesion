package com.example.datossesion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DialogManager cuadroDialogo = new DialogManager();
    SessionManager session;
    Button btnLogout;
    TextView lblName;
    TextView lblEmail;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        session = new SessionManager(getApplicationContext());
        lblName = (TextView) findViewById(R.id.lblName);
        //lblEmail = (TextView) findViewById(R.id.lblEmail);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        Toast.makeText(getApplicationContext(), "Estado del login " + session.isLogged(), Toast.LENGTH_LONG );
        session.loginStatus();

        HashMap<String, String> user = session.getUserPref();

        String name = user.get(SessionManager.KEY_NAME);
        String email = user.get(SessionManager.KEY_EMAIL);

        lblName.setText(Html.fromHtml("Correo: <b>" + name + "</b>"));
        //lblEmail.setText(Html.fromHtml("contraseña: <b>" + email + "</b>"));

        btnLogout.setOnClickListener((arg0)  -> {
            session.logOut();
            //finish ();
        });
    }
/*
    @Override
    protected void onStart() {
        super.onStart ( );

        SharedPreferences sharedpreferences = getSharedPreferences ( "MisPreferencias", Context.MODE_PRIVATE );
        //get your string with default string in case referred key is not found
        String str1 = sharedpreferences.getString ( "user1", null );
        String str2 = sharedpreferences.getString ( "user2", null );

        if (str1 != null && str2 != null) {
            etUsuario.setVisibility ( View.INVISIBLE );
            etContra.setVisibility ( View.INVISIBLE );
            btnLogin.setVisibility ( View.INVISIBLE );
            tvRegistrar.setVisibility ( View.INVISIBLE );
            imagen1.setVisibility ( View.INVISIBLE );
            progressBar.setVisibility ( View.VISIBLE );

            Intent intentReg = new Intent ( MainActivity.this, Main2Activity.class );
            MainActivity.this.startActivity ( intentReg );
        } else {
            progressBar.setVisibility ( View.INVISIBLE );


            //Toast.makeText(getApplicationContext(),"Now onStart() calls", Toast.LENGTH_LONG).show(); //onStart Called
            tvRegistrar.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public void onClick(View view) {
                    Intent intentReg = new Intent ( MainActivity.this, Register.class );
                    MainActivity.this.startActivity ( intentReg );
                }
            } );
        }
    }

 */
}