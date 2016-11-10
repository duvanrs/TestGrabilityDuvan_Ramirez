package duvanrodrigo.com.testgrability;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private GridView gridView;
    private ApplicationsAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // usarToolbar();

        gridView = (GridView) findViewById(R.id.grid);
        adaptador = new ApplicationsAdapter(this);
        adaptador.clearItemS();

        ArrayList<HashMap<String,String>>  aplicaciones= new Functions(this).getArrayList();
        String Aplicacion="", UrlImagen="",Resumen="",Titulo="";

        for (HashMap<String, String> map : aplicaciones) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("Aplicacion")) {
                    Aplicacion = entry.getValue();
                }
                if (entry.getKey().equalsIgnoreCase("UrlImagen")) {
                    UrlImagen = entry.getValue();
                }
                if (entry.getKey().equalsIgnoreCase("Resumen")) {
                    Resumen = entry.getValue();
                }
                if (entry.getKey().equalsIgnoreCase("Titulo")) {
                    Titulo = entry.getValue();
                }
            }
            Applications ap=new Applications(Aplicacion,UrlImagen,Resumen,Titulo);
            adaptador.addItem(ap);
        }
        adaptador.notifyDataSetChanged();
        //Applications ap=new Applications("Messenger - Facebook, Inc.","http://is5.mzstatic.com/image/thumb/Purple71/v4/a5/dd/23/a5dd2389-9267-ed35-57c9-9a4306ca3d96/mzl.zaqgosfq.png/53x53bb-85.png","RESUMEN","Messenger - Facebook, Inc.");
        //adaptador.addItem(ap);
        gridView.setAdapter(adaptador);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Applications item = (Applications) adapterView.getItemAtPosition(i);

        Intent intent = new Intent(this, ActividadDetalle.class);
        intent.putExtra("UrlImagen", item.getUrlImagen());
        intent.putExtra("Resumen", item.getResumen());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityOptionsCompat activityOptions =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                            this,
                            new Pair<View, String>(view.findViewById(R.id.ImagenAplicacion),
                                    ActividadDetalle.VIEW_NAME_HEADER_IMAGE)
                    );

            ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
        } else
            startActivity(intent);
    }
    private void usarToolbar() {
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
