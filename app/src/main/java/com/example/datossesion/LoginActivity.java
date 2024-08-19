package com.example.datossesion;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {

    private EditText txtCorreo, txtContra;
    private Button btnLogin, btnRegister;
    SessionManager session;
    DialogManager cuadroDialogo = new DialogManager();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        txtCorreo = findViewById(R.id.txtUserName); // Asumiendo que el campo de correo electrónico usa este ID
        txtContra = findViewById(R.id.txtPassword); // Asumiendo que el campo de contraseña usa este ID
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        progressBar = findViewById(R.id.pbbar);
        progressBar.setVisibility(View.INVISIBLE);

        if (session.isLogged()) {
            txtCorreo.setVisibility(View.INVISIBLE);
            txtContra.setVisibility(View.INVISIBLE);
            btnLogin.setVisibility(View.INVISIBLE);
            btnRegister.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!session.isLogged()) {
            session.loginStatus();

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String correo = txtCorreo.getText().toString();
                    String contra = txtContra.getText().toString();

                    if (correo.trim().length() > 0 && contra.trim().length() > 0) {
                        progressBar.setVisibility(View.VISIBLE);
                        TareaWSConsulta tarea = new TareaWSConsulta();
                        tarea.execute();
                    } else {
                        cuadroDialogo.showAlertDialog(LoginActivity.this, "Fallo", "Debe introducir datos", false);
                    }
                }
            });
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class TareaWSConsulta extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            SQLServerConnection dbConnection = new SQLServerConnection();
            String correo = txtCorreo.getText().toString().trim();
            String contra = txtContra.getText().toString().trim();
            return dbConnection.checkUserCredentials(correo, contra);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (progressBar != null) {
                progressBar.setProgress(values[0]);
                progressBar.setMax(100);
                progressBar.setProgress(50);
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progressBar.setVisibility(View.INVISIBLE);
            if (result) {
                session.createLoginSession(txtCorreo.getText().toString().trim(), txtContra.getText().toString().trim());
                Intent intentReg = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intentReg);
                finish();
            } else {
                cuadroDialogo.showAlertDialog(LoginActivity.this, "Fallo", "Correo o contraseña incorrecta", false);
                txtCorreo.setText("");
                txtContra.setText("");
            }
        }
    }

}
