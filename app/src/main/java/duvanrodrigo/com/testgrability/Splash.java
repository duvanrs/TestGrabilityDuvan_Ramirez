package duvanrodrigo.com.testgrability;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import imageutils.ImageLoader;

public class Splash extends AppCompatActivity {

    Functions f;
    private String UrlServices;
    ArrayList<HashMap<String, String>> ApplicationsList;
    private ImageLoader imgLoader;
    AnimationDrawable DrawableframeAnimation;
    private Button btnAplicaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        f=new Functions(this);
        UrlServices=getString(R.string.servicios);

        ImageView frameAnimation = (ImageView) findViewById(R.id.imageView);
        frameAnimation.setBackgroundResource(R.drawable.frame_animation);
        DrawableframeAnimation = (AnimationDrawable) frameAnimation.getBackground();

        btnAplicaciones=(Button)findViewById(R.id.button);
        btnAplicaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationsList=new ArrayList<HashMap<String, String>>();
                if(!f.isOnline()){
                    Toast toast = Toast.makeText(Splash.this,"La aplicacion se ejecutara en  modo offline ya que no se encuentra conexion activa a internet", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    getInformationServices _getInformationServices=new getInformationServices();
                    _getInformationServices.execute();
                }
            }
        });



    }
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if(DrawableframeAnimation.isRunning()) {
            DrawableframeAnimation.stop();
            }else {
                DrawableframeAnimation.start();
            }
            return true;
        }
        return super.onTouchEvent(event);
    }


    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };
    private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        } };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class getInformationServices extends AsyncTask<Void,Void,Void>{

        private final ProgressDialog dialog = new ProgressDialog(Splash.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog.setMessage("Obteniendo información, por favor espere");
            this.dialog.show();
            this.dialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... strings) {
            int length = 0;
            StringBuilder result = new StringBuilder();
            HttpsURLConnection _HttpsURLConnection=null;

            try {
                URL _URL=new URL(UrlServices);
                trustAllHosts();
                _HttpsURLConnection= (HttpsURLConnection)_URL.openConnection();
                _HttpsURLConnection.setHostnameVerifier(DO_NOT_VERIFY);
                InputStream in = new BufferedInputStream(_HttpsURLConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JSONObject json_data = new JSONObject(result.toString());
                Object  ObjectArray= json_data.get("feed");
                ObjectArray=((JSONObject) ObjectArray).get("entry");
                JSONArray jsonArray = ((JSONArray) ObjectArray);
                length=jsonArray.length();
                for(int i=0;i<length;i++){
                    HashMap<String, String> application = new HashMap<String, String>();

                    JSONObject json_data2 = jsonArray.getJSONObject(i);
                    Iterator iterator = json_data2.keys();

                    while(iterator.hasNext()){
                        String key = (String)iterator.next();
                        if(key.equalsIgnoreCase("im:name")){
                            JSONObject issue = json_data2.getJSONObject(key);
                            application.put("Aplicacion",issue.optString("label"));
                        }
                        if(key.equalsIgnoreCase("im:image")){
                            JSONArray issue = json_data2.getJSONArray(key);
                            application.put("UrlImagen",issue.getJSONObject(0).optString("label"));
                            //application.put("BitMapImagen",drawable_from_url(issue.getJSONObject(0).optString("label")));
                        }
                        if(key.equalsIgnoreCase("summary")){
                            JSONObject issue = json_data2.getJSONObject(key);
                            application.put("Resumen",issue.optString("label"));
                        }
                        if(key.equalsIgnoreCase("title")){
                            JSONObject issue = json_data2.getJSONObject(key);
                            application.put("Titulo",issue.optString("label"));
                        }

                    }
                    ApplicationsList.add(application);
                    //imgLoader.DisplayImage(URL,ImageView);
                }
                JSONArray jsonAplications= new JSONArray(ApplicationsList);
                f.saveSettings("Aplicaciones",jsonAplications.toString());
            }catch (Exception ex){
                Log.i("doInBackground-Splash",ex.getMessage());
            }
            finally {
                _HttpsURLConnection.disconnect();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            //almacenamos en cache la información

            Intent intent = new Intent(Splash.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private String drawable_from_url(String url) throws java.net.MalformedURLException, java.io.IOException {
        StringBuilder result=new StringBuilder();

        HttpURLConnection connection = (HttpURLConnection)new URL(url) .openConnection();
        connection.setRequestProperty("User-agent","Mozilla/4.0");

        connection.connect();
        InputStream input = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }
}
