package com.itca.crud_sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.itca.crud_sqlite.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this ,"administracion.db",null,1);
          //  SQLiteDatabase.bd = admin.getWritableDatabase();

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private EditText et_codigo, et_descripcion, et_precio;
    private Button btnguardar, btnconsultar1, btnconsultar2, btneliminar, btnmodificar, btnnuevo, btnsalir;

    String codigo, descripcion, precio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       // setSupportActionBar(binding.toolbar);

       /* NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration); */


        et_codigo = (EditText) findViewById(R.id.et_codigo);
        et_descripcion = (EditText) findViewById(R.id.et_descripcion);
        et_precio = (EditText) findViewById(R.id.et_precio);

        btnguardar = (Button) findViewById(R.id.btnguardar);
        btnconsultar1 = (Button) findViewById(R.id.btnconsultar1);
        btnconsultar2 = (Button) findViewById(R.id.btnconsultar2);
        btneliminar = (Button) findViewById(R.id.btneliminar);
        btnmodificar = (Button) findViewById(R.id.btnmodificar);
        btnnuevo = (Button) findViewById(R.id.btnnuevo);
        btnsalir = (Button) findViewById(R.id.btnsalir);




      /*  binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View view) {
        SQLiteDatabase bd = admin.getWritableDatabase();
        switch (view.getId()){

            case R.id.btnguardar:
                // Toast.makeText(this, "Has hecho click en el boton guardar", Toast.LENGTH_SHORT).show();
                String codigo = et_codigo.getText().toString();
                String descripcion = et_descripcion.getText().toString();
                String precio = et_precio.getText().toString();
                ContentValues registro = new ContentValues();
                registro.put("codigo",codigo);
                registro.put("descripcion",descripcion);
                registro.put("precio",precio);


                if (codigo.isEmpty()){
                    et_codigo.setError("Campo Obligatorio");
                }
                else if (descripcion.isEmpty()){
                    et_descripcion.setError("Campo Obligatorio");
                }
                else if (precio.isEmpty()){
                    et_precio.setError("Campo Obligatorio");
                } else {
                    bd.insert("articulos", null, registro);
                    bd.close();
                    et_codigo.setText(null);
                    et_descripcion.setText(null);
                    et_precio.setText(null);
                    Toast.makeText(this, "Registro guardado correctamente", Toast.LENGTH_SHORT).show();
                } break;







            case R.id.btnconsultar1:


                codigo = et_codigo.getText().toString();
                if (codigo.isEmpty()){
                    et_codigo.setError("Campo Obligatorio");
                } else {
                    Cursor file = bd.rawQuery("select descripcion, precio from articulos where codigo=" + codigo, null);
                    if (file.moveToFirst()){
                        et_descripcion.setText(file.getString(0));
                        et_precio.setText(file.getString(1));
                    } else {
                        Toast.makeText(this, "No existe un articulo con dicho codigo", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.btnconsultar2:


               descripcion = et_descripcion.getText().toString();
                if (descripcion.isEmpty()){
                    et_descripcion.setError("Campo Obligatorio");
                } else {
                    Cursor file = bd.rawQuery("select codigo, precio from articulos where descripcion= '" + descripcion + "'" , null);
                    if (file.moveToFirst()){
                        et_codigo.setText(file.getString(0));
                        et_precio.setText(file.getString(1));
                    } else {
                        Toast.makeText(this, "No existe un articulo con dicha descripcion", Toast.LENGTH_SHORT).show();
                    } bd.close();
                }



                break;

            case R.id.btneliminar:

             String cod2 = et_codigo.getText().toString();
             if (cod2.isEmpty()){
                 et_codigo.setError("Campo Obligatorio");
             } else {
                 int cant2 = bd.delete("articulos", "codigo=" + cod2, null);
                 bd.close();
                 et_codigo.setText("");
                 et_descripcion.setText("");
                 et_precio.setText("");
                 if (cant2 == 1)
                     Toast.makeText(this, "Se borro el articulo con dicho codigo", Toast.LENGTH_SHORT).show();
                 else
                     Toast.makeText(this, "No existe un articulo con dicho codigo", Toast.LENGTH_SHORT).show();


             }



                break;
            case R.id.btnmodificar:


                String cod = et_codigo.getText().toString();
                String descri = et_descripcion.getText().toString();
                if (cod.isEmpty()) {
                    et_codigo.setError("Campo Obligatorio");
                }else {

                    String pre = et_precio.getText().toString();
                    ContentValues registro1 = new ContentValues();
                    registro1.put("codigo",cod);
                    registro1.put("descripcion",descri);
                    registro1.put("precio",pre);
                    int cant = bd.update("articulos", registro1, "codigo=" + cod, null);
                    if (cant == 1)
                        Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "No existe un articulos con el codigo ingresado", Toast.LENGTH_SHORT).show();


                }


                break;
            case R.id.btnnuevo:
                et_codigo.setText(null);
                et_descripcion.setText(null);
                et_precio.setText(null);
                break;
            case R.id.btnsalir:

                finish();
                break;
        }

    }


}

   /* @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}*/