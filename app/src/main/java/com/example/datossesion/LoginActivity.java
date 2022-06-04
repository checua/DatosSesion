package com.example.datossesion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText txtUser, txtPwd;
    Button btnLogin;
    SessionManager session;
    DialogManager cuadroDialogo = new DialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        txtUser = (EditText) findViewById(R.id.txtUserName);
        txtPwd =  (EditText) findViewById(R.id.txtPassword);

        Toast.makeText(getApplicationContext(), "Estado de identificación" + session.isLogged(), Toast.LENGTH_LONG).show();
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

    }
}