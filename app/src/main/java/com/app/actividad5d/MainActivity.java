package com.app.actividad5d;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends Activity {
    private CalendarView calen;
    private int dia, mes, any;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calen = (CalendarView)findViewById(R.id.calendarView);

        calen.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                dia= dayOfMonth;
                mes= month;
                any=year;
                registerForContextMenu(view);
                openContextMenu(view);
            }
        });
        //Uri del Cont Prov para recoger calendarios
        Uri uri = CalendarContract.Calendars.CONTENT_URI;

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if(cursor!=null) {
            while(cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndex("_id"));
                String name = cursor.getString(cursor.getColumnIndex("calendar_displayName"));
                Log.e("ID CALENDARIO:", ""+id+" "+name);
            }
        }
        Uri u = CalendarContract.Events.CONTENT_URI;
        Cursor c = getContentResolver().query(u,null,null,null,null);
        if(c!=null) {
            while(c.moveToNext()) {
                int d =c.getColumnIndex("DESCRIPTION");
                int f = c.getColumnIndex("DTSTART");
                int t = c.getColumnIndex("TITTLE");//nº70, id calen 71
               String des = c.getString(69);
                String tit = c.getString(70);
                String all= c.getString(5);
                Long da = Long.parseLong(tit) * 1000;
                Date date = new Date(da);
                Log.e("allday:", ""+all);
                //Log.e("Evento:", all+ "fecha: "+date+ "titulo: "+tit+ "descripcion: "+des );
                //Log.e("fecha:", ""+date);
                Log.e("fecha:", ""+f+ " "+date);
                Log.e("titulo:", ""+t+ " "+tit);
                Log.e("descripcion:", ""+d+ " "+des);
            }
        }
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
    }

    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.crear:
                //Toast.makeText(getApplicationContext(),""+mes, Toast.LENGTH_LONG).show();
                Intent intent= new Intent(getApplicationContext(),CrearEvento.class);
                Bundle b = new Bundle();
                b.putInt("Dia", dia);
                b.putInt("Mes",mes);
                b.putInt("Any",any);
                intent.putExtras(b);
                startActivity(intent);


                break;
            case R.id.mostrar:
                Intent intent1= new Intent(getApplicationContext(),MostrarEvento.class);
                Bundle b1 = new Bundle();
                b1.putInt("Dia", dia);
                b1.putInt("Mes",mes);
                b1.putInt("Any",any);
                intent1.putExtras(b1);
                startActivity(intent1);
                break;

        }
        return true;
    }

}
