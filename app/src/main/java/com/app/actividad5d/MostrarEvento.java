package com.app.actividad5d;

import android.app.Activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MostrarEvento extends Activity {
    private TextView fecha;
    private ListView lista;
    long startMillis = 0;
    long endMillis = 0;
    private int dia, mes, any;
    ArrayList<String> eventos = new ArrayList<>();


    static final String PROVIDER_NAME = "com.android.calendar/events";
    static final String URL = "content://" + PROVIDER_NAME + "/calendar";
    static final Uri CONTENT_URI = Uri.parse(URL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_evento);

        fecha= (TextView) findViewById(R.id.tvFechaMostrar);
        lista = (ListView) findViewById(R.id.lvMostrar);


        //Recogemos parámetros de la fecha seleccionada en el calendario

        Bundle b = getIntent().getExtras();
        if (b!=null){
            dia = b.getInt("Dia");
            mes = b.getInt("Mes");
            any = b.getInt("Any");
            int  mesmuestra= mes+1;
            fecha.setText("Eventos para el día: "+dia+"/"+mesmuestra+"/"+any);
        }

        //Establecemos los parámetros recogidos
        //a los argumentos necesarios para la consulta

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(any, mes,dia, 00, 00);
        startMillis = beginTime.getTimeInMillis();

        Calendar endTime = Calendar.getInstance();
        endTime.set(any,mes, dia + 1, 00, 00);
        endMillis = endTime.getTimeInMillis();

        //Creamos array con los datos de las columnas que queremos recuperar

        String[] projection = new String[] {CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION};

        //Establecemos cláusulas de la búsqueda(id_calendario y fecha evento)

        String selection = "((" + CalendarContract.Events.CALENDAR_ID + " = ?) AND ("
                + CalendarContract.Events.DTSTART + " >= ?) AND ("
                + CalendarContract.Events.DTSTART + " < ?))";

        //Creamos array con los argumentos de la búsqueda

        String[] selArgs = new String[]{String.valueOf(2),String.valueOf(startMillis),String.valueOf(endMillis)};

        //Creamos cursor para guardar la consulta

        Cursor cursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI, projection, selection, selArgs, null);

        //Recorremos cursor y almacenamos los datos obtenidos en el arraylist de eventos

        if (cursor != null && cursor.moveToFirst()){
            do{
                String titulo = cursor.getString(0);
                String descripcion = cursor.getString(1);
                String evento= "Título: "+titulo+"\nDescripción: "+descripcion;
                eventos.add(evento);

            }while (cursor.moveToNext());
            //Cerramos el cursor
            cursor.close();
        }

        //Añadimos a la lista los eventos recuperados para la fecha seleccionada

        lista.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, eventos));


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
