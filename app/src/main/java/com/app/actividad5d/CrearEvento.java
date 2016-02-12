package com.app.actividad5d;

import android.app.Activity;
import android.app.usage.UsageEvents;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;

public class CrearEvento extends Activity {
    private TextView tit, desc, fech;
    private EditText titulo, descripcion;
    private Button guardar;
    private int dia, mes, any;

    static final String PROVIDER_NAME = "com.android.calendar/events";
    static final String URL = "content://" + PROVIDER_NAME + "/calendar";
    static final Uri CONTENT_URI = Uri.parse(URL);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);

        tit = (TextView) findViewById(R.id.tvTituloCrear);
        desc = (TextView)findViewById(R.id.tvDescCrear);
        fech = (TextView) findViewById(R.id.tvFechaCrear);

        titulo = (EditText)findViewById(R.id.etTituloCrear);
        descripcion = (EditText) findViewById(R.id.etDescripcionCrear);

        guardar = (Button) findViewById(R.id.bGuardar);

        Bundle b = getIntent().getExtras();
        if (b!=null){
            dia = b.getInt("Dia");
            mes = b.getInt("Mes");
            any = b.getInt("Any");
           int  mesmuestra= mes+1;
            fech.setText(dia+"/"+mesmuestra+"/"+any);
        }




        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentResolver cr = getContentResolver();
                ContentValues values = new ContentValues();

                long startMillis = 0;
                long endMillis = 0;
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(any, mes,dia, 00, 00);
                startMillis = beginTime.getTimeInMillis();
                Calendar endTime = Calendar.getInstance();
                endTime.set(any,mes, dia + 1, 00, 00);
                endMillis = endTime.getTimeInMillis();


                values.put (CalendarContract.Events.CALENDAR_ID,2);
                values.put(CalendarContract.Events.DTSTART, startMillis);
                values.put(CalendarContract.Events.DTEND, endMillis);
                values.put(CalendarContract.Events.TITLE, titulo.getText().toString());
                values.put(CalendarContract.Events.DESCRIPTION, descripcion.getText().toString());
                values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

               cr.insert (CalendarContract.Events.CONTENT_URI, values);

                finish();


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crear_evento, menu);
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
