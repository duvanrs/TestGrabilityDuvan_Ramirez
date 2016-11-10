package duvanrodrigo.com.testgrability;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by DuvanRS on 11/09/2016.
 */

public class Applications {
    private String Aplicacion;
    private String UrlImagen;
    private String BitMapImagen;
    private String Resumen;
    private String Titulo;
    private int idDrawable;
    public static Applications[] ITEMS;

    public Applications(String Aplicacion, String UrlImagen,String Resumen,String Titulo) {
        this.Aplicacion = Aplicacion;
        this.UrlImagen = UrlImagen;
        this.BitMapImagen = BitMapImagen;
        this.Resumen = Resumen;
        this.Titulo = Titulo;
        this.idDrawable = idDrawable;
    }

    public  static  void addItems(Applications ap){
           Applications bk[] =ITEMS;
        if(ITEMS==null){
            ITEMS=new Applications[1];
            ITEMS[0]=ap;
        }else {
            ITEMS=new Applications[ITEMS.length+1];
            for(int i=0;i<bk.length;i++){
                ITEMS[i]=bk[i];
            }
            ITEMS[bk.length]=ap;
        }
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public String getAplicacion() {
        return Aplicacion;
    }
    public String getUrlImagen() {
        return UrlImagen;
    }
    public Bitmap getBitMapImagen() {
        return StringToBitMap(BitMapImagen) ;
    }
    public String getResumen() {
        return Resumen;
    }
    public String getTitulo() {
        return Titulo;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    public int getId() {
        return Aplicacion.hashCode();
    }




    public static Applications getItem(int id) {
        for (Applications item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
