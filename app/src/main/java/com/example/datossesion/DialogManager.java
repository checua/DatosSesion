package com.example.datossesion;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogManager {

    public void showAlertDialog(Context context, String title, String message, Boolean status){
        AlertDialog alertDialog = new AlertDialog.Builder ( context ).create ();

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        if(status != null)
            alertDialog.setIcon((status) ? R.drawable.icon : R.drawable.error);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

}
