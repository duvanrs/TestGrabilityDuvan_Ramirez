package duvanrodrigo.com.testgrability;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import imageutils.ImageLoader;

public class ActividadDetalle extends AppCompatActivity {

    public static final String EXTRA_PARAM_ID = "duvanrodrigo.com.testgrability.ID";
    public static final String EXTRA_PARAM_Resumen = "duvanrodrigo.com.testgrability.Resumen";
    public static final String VIEW_NAME_HEADER_IMAGE = "imagen_compartida";
    private Applications itemDetallado;
    private ImageView imagenExtendida;
    private TextView resumenApplication;
    String urlImagen,Resumen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_detalle);

        //usarToolbar();

        Bundle extras = getIntent().getExtras();
        imagenExtendida = (ImageView) findViewById(R.id.imagen_extendida);
        resumenApplication=(TextView)findViewById(R.id.ResumenApplication);

        if (extras != null) {
            urlImagen=extras.getString("UrlImagen");
            Resumen=extras.getString("Resumen");
        }
        resumenApplication.setText(Resumen);
        cargarImagenExtendida();
    }
    private void cargarImagenExtendida() {
        new ImageLoader(this).DisplayImage(urlImagen,imagenExtendida);;
    }

    private void usarToolbar() {
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }
}
