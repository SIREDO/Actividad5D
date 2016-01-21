package com.app.actividad5d;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MostrarEvento extends Activity {
    private TextView fecha, titulo, descripcion;
    private String f,t,d;

    static final String PROVIDER_NAME = "com.android.calendar/events";
    static final String URL = "content://" + PROVIDER_NAME + "/calendar";
    static final Uri CONTENT_URI = Uri.parse(URL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_evento);
        fecha= (TextView) findViewById(R.id.tvFechaMostrar);
        titulo = (TextView) findViewById(R.id.tvTituloMostrar);
        descripcion = (TextView) findViewById(R.id.tvDescmostrar);
        ArrayList<String> eventos = new ArrayList<String>();

        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(CONTENT_URI, null, null, null, null);

       /* if(cursor!=null && cursor.moveToFirst()) {
           do{titulo.setText(cursor.getString(0));
               descripcion.setText(cursor.getString(1));
           }while(cursor.moveToNext());

        }*/
        /*if(cursor!=null) {
            while(cursor.moveToNext()) {
                int t = cursor.getColumnIndex("TITTLE");
                Log.e("TITULO:", ""+t);
            }
        }*/
        /*if (cursor !=null && cursor.moveToFirst()){
            do{
               int t = cursor.getColumnIndex("TITTLE");
                Log.e("TITULO:", ""+t);

                eventos.add(cursor.getString(0)+"     "+cursor.getString(1));
            }while(cursor.moveToNext());
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mostrar_evento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
