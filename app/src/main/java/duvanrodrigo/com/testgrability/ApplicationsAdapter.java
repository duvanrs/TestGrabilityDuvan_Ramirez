package duvanrodrigo.com.testgrability;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import imageutils.ImageLoader;

/**
 * Created by DuvanRS on 11/09/2016.
 */

public class ApplicationsAdapter extends BaseAdapter {
    private Context context;

    public ApplicationsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Applications.ITEMS.length;
    }

    @Override
    public Applications getItem(int position) {
        return Applications.ITEMS[position];
    }


    public void addItem(Applications _applicatios) {
        Applications.addItems(_applicatios);
    }

    public void clearItemS() {
        Applications.ITEMS=null;
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item, viewGroup, false);
        }

        ImageView imagenAplicacion = (ImageView) view.findViewById(R.id.ImagenAplicacion);
        TextView nombreAplicacion = (TextView) view.findViewById(R.id.tituloAplicacion);

        final Applications item = getItem(position);
       /* Glide.with(imagenAplicacion.getContext())
                .load(item.getIdDrawable())
                .into(imagenAplicacion);
        */

            new ImageLoader(context).DisplayImage(item.getUrlImagen(), imagenAplicacion);
            nombreAplicacion.setText(item.getTitulo());
        return view;
    }
}
