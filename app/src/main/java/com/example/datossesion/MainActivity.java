package com.example.datossesion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DialogManager cuadroDialogo = new DialogManager();
    SessionManager session;
    Button btnLogout;
    TextView lblName;
    TextView lblEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        session = new SessionManager(getApplicationContext());
        lblName = (TextView) findViewById(R.id.lblName);
        lblEmail = (TextView) findViewById(R.id.lblEmail);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        Toast.makeText(getApplicationContext(), "Estado del login " + session.isLogged(), Toast.LENGTH_LONG );
        session.loginStatus();

        HashMap<String, String> user = session.getUserPref();

        String name = user.get(SessionManager.KEY_NAME);
        String email = user.get(SessionManager.KEY_EMAIL);

        lblName.setText(Html.fromHtml("Nombre: <b>" + name + "</b>"));
        lblEmail.setText(Html.fromHtml("Email: <b>" + email + "</b>"));

        btnLogout.setOnClickListener((arg0)  -> {session.logOut();});
    }
}