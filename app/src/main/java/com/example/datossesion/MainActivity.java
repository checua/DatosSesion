package com.example.datossesion;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Random;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    DialogManager cuadroDialogo = new DialogManager();
    SessionManager session;
    Button btnLogout;
    TextView lblName;
    TextView lblEmail;
    ProgressBar progressBar;

    ImageView ruleta;
    Button girar;
    Random random = new Random (  );
    boolean reestablecer;
    int angulo;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        ruleta = (ImageView) findViewById ( R.id.ruletaImagen );
        girar = (Button) findViewById ( R.id.btnGirar );


        session = new SessionManager(getApplicationContext());
        lblName = (TextView) findViewById(R.id.lblName);
        //lblEmail = (TextView) findViewById(R.id.lblEmail);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        Toast.makeText(getApplicationContext(), "Estado del login " + session.isLogged(), Toast.LENGTH_LONG );
        session.loginStatus();

        HashMap<String, String> user = session.getUserPref();

        String name = user.get(SessionManager.KEY_NAME);
        String email = user.get(SessionManager.KEY_EMAIL);

        lblName.setText(Html.fromHtml(name ));
        //lblName.setText(Html.fromHtml("Correo: <b>" + name + "</b>"));
        //lblEmail.setText(Html.fromHtml("contraseña: <b>" + email + "</b>"));

        btnLogout.setOnClickListener((arg0)  -> {
            session.logOut();
            //finish ();
        });
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
    public void gira(View view){

        if(reestablecer) {
            angulo = angulo % 360;
            RotateAnimation rotar = new RotateAnimation ( angulo, 360,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f );
            rotar.setFillAfter ( true );
            rotar.setDuration ( 360 * 4 );
            rotar.setInterpolator ( new AccelerateDecelerateInterpolator ( ) );
        }else{
            angulo = random.nextInt (3600) + 360;
            RotateAnimation rotar = new RotateAnimation ( 0, angulo,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f );
            rotar.setFillAfter ( true );
            rotar.setDuration ( 3600 );
            rotar.setInterpolator ( new AccelerateDecelerateInterpolator ( ) );
            ruleta.startAnimation ( rotar );
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                //buttons[inew][jnew].setBackgroundColor(Color.BLACK);
                Intent intent = new Intent(getApplicationContext(), DonateActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        }, 3600);

    }
}