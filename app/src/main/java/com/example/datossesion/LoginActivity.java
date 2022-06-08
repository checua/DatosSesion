package com.example.datossesion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUser, txtPwd;
    private Button btnLogin, btnRegister;
    SessionManager session;
    DialogManager cuadroDialogo = new DialogManager();

    Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        txtUser = findViewById(R.id.txtUserName);
        txtPwd =  findViewById(R.id.txtPassword);
        btnLogin = findViewById ( R.id.btnLogin );
        btnRegister = findViewById ( R.id.btnRegister );

        if(session.isLogged()){
            txtUser.setVisibility ( View.INVISIBLE );
            txtPwd.setVisibility ( View.INVISIBLE );
            btnLogin.setVisibility ( View.INVISIBLE );
        }
    }

    @Override
    protected void onStart() {
        super.onStart ( );

        if(!session.isLogged()){

            session.loginStatus();


            //Toast.makeText(getApplicationContext(), "Estado de identificación: " + session.isLogged(), Toast.LENGTH_LONG).show();
            btnLogin = (Button) findViewById(R.id.btnLogin);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username = txtUser.getText().toString();
                    String password = txtPwd.getText().toString();

                    if(username.trim().length() > 0 && password.trim().length() > 0){
                        if(username.equals("David") && password.equals("pixelpro")){
                            session.createLoginSession("Pixelpro", "info@pixelpro.es");
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            cuadroDialogo.showAlertDialog(LoginActivity.this, "Fallo", "David", false);
                        }
                    }else{
                        cuadroDialogo.showAlertDialog(LoginActivity.this, "Fallo", "Debe introducir datos", false);
                    }
                }
            });

        }else
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        btnRegister.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        } );

    }
}