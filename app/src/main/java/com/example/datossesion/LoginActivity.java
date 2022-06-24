package com.example.datossesion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.sql.Connection;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUser, txtPwd;
    private Button btnLogin, btnRegister;
    SessionManager session;
    DialogManager cuadroDialogo = new DialogManager();
    ProgressBar progressBar;
    //private TextView tvResult;

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

        progressBar = findViewById(R.id.pbbar);
        progressBar.setVisibility(View.INVISIBLE);

        //tvResult = findViewById ( R.id.tvResult );
        //tvResult.setVisibility ( View.INVISIBLE );

        if(session.isLogged()){
            txtUser.setVisibility ( View.INVISIBLE );
            txtPwd.setVisibility ( View.INVISIBLE );
            btnLogin.setVisibility ( View.INVISIBLE );
            btnRegister.setVisibility ( View.INVISIBLE );
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

                        /*
                        if(username.equals("David") && password.equals("pixelpro")){
                            session.createLoginSession("Pixelpro", "info@pixelpro.es");

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            cuadroDialogo.showAlertDialog(LoginActivity.this, "Fallo", "David", false);
                        }

                         */

                        progressBar.setVisibility(View.VISIBLE);

                        TareaWSConsulta tarea = new TareaWSConsulta();
                        tarea.execute();
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

    private class TareaWSConsulta extends AsyncTask<String, Integer, Integer> {

        protected Integer doInBackground(String... params) {



            int resul = 0;
            final String NAMESPACE = "http://microsoft.com/webservices/";
            final String URL = "https://webservicex.azurewebsites.net/WebServices/WebService1.asmx";
            final String METHOD_NAME = "LogUsuReturnId";
            final String SOAP_ACTION = "http://microsoft.com/webservices/LogUsuReturnId";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("usuario", txtUser.getText ().toString ().trim ());
            request.addProperty("contra", txtPwd.getText ().toString ().trim ());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(SOAP_ACTION, envelope);
                /*SoapObject resSoap = (SoapObject) envelope.getResponse();*/
                SoapPrimitive resSoap = (SoapPrimitive)envelope.getResponse();
                resul = Integer.parseInt(resSoap.toString());
            } catch (Exception e) {
                resul = 0;
            }


            return resul;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);



            if (progressBar != null) {
                progressBar.setProgress(values[0]);
                progressBar.setMax(100); // 100 maximum value for the progress value
                progressBar.setProgress(50); // 50 default progress value for the progress bar
            }
        }


        protected void onPostExecute(Integer result) {


            if (result != 0) {


                //tvRegistrar.setText ( "Acceso concedido" );

                session.createLoginSession(txtUser.getText ().toString ().trim (), txtPwd.getText ().toString ().trim ());
                Intent intentReg = new Intent ( LoginActivity.this, MainActivity.class );
                LoginActivity.this.startActivity ( intentReg );
            } else {
                /*
                tvResult.setVisibility ( View.VISIBLE );
                tvResult.setText ( "Acceso denegado" );

                 */
                cuadroDialogo.showAlertDialog(LoginActivity.this, "Fallo", "Correo o contraseña incorrecta", false);
                progressBar.setVisibility(View.INVISIBLE);
                txtUser.setText ( "" );
                txtPwd.setText ( "" );
            }



        }
    }
}