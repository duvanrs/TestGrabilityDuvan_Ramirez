package duvanrodrigo.com.testgrability;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by DuvanRS on 11/08/2016.
 */

public class Functions {

    private SharedPreferences preferencesSettings;
    private static final int PREFERENCES_MODE_PRIVATE=0;
    private SharedPreferences.Editor preferencesEditor;
    private Context myContex;

    public Functions(Context context){
        myContex=context;
        preferencesSettings = myContex.getSharedPreferences(myContex.getString(R.string.app_name), PREFERENCES_MODE_PRIVATE);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) myContex.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }

        return false;
    }
    public Drawable LoadImageFromWebOperations(String url,String srcName) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, srcName);
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    public  boolean saveSettings(String SettingsName,String SettingsValue){
        boolean SettingsSaved=true;
        preferencesEditor = preferencesSettings.edit();

        try{
            preferencesEditor.putString(SettingsName, SettingsValue);
            preferencesEditor.apply();
            preferencesEditor.commit();
        }catch (Exception e){
            Log.e("saveSettings",e.getMessage());
            SettingsSaved=false;
        }
        return SettingsSaved;
    }
    public ArrayList<HashMap<String,String>> getArrayList() {

        ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
        try{
            preferencesEditor = preferencesSettings.edit();
            if (preferencesEditor != null) {

                    String Json= preferencesSettings.getString("Aplicaciones","");
                    JSONArray array = new JSONArray(Json);
                    HashMap<String, String> item = null;

                    for(int i =0; i<array.length(); i++) {
                        JSONObject ary = array.getJSONObject(i);
                        Iterator<String> it = ary.keys();
                        item = new HashMap<String, String>();
                        while (it.hasNext()) {
                            String key = it.next();
                            item.put(key, (String) ary.get(key));
                        }
                        arrayList.add(item);
                    }
            } else {
                return null;
            }
        }catch(Exception e) {
            Log.e("getLasUserLogin", e.getMessage());
        }
        return arrayList;
    }

}
