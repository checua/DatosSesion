package com.example.datossesion;

import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLServerConnection {

    private static final String URL = "jdbc:sqlserver://mapsMarkers.mssql.somee.com:1433;databaseName=mapsMarkers";
    private static final String CORREO = "profesor76_SQLLogin_1";
    private static final String CONTRA = "by69w8lfmv";
    DialogManager cuadroDialogo = new DialogManager();

    public Connection getConnection(){
        Connection connection = null;
        try {
            StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://mapsMarkers.mssql.somee.com:1433/mapsMarkers;user=profesor76_SQLLogin_1;password=by69w8lfmv;packetSize=4096;trustServerCertificate=true;");
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    public boolean checkUserCredentials(String correo, String contra) {
        boolean isValidUser = false;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            if (connection != null) {
                String query = "SELECT * FROM usuario WHERE correo = '" + correo + "' AND contra = '" + contra + "'";
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    isValidUser = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isValidUser;
    }
}

