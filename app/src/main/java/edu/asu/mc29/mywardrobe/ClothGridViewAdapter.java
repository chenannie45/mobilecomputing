package edu.asu.mc29.mywardrobe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by rrshah9 on 11/19/15.
 */
public class ClothGridViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private File[] files;

    public ClothGridViewAdapter(Context context, String dirPath) {
        super();
        this.context = context;
        files = new File(dirPath).listFiles();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return files.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item_layout, parent, false);
            //imageView = (ImageView) convertView.findViewById(R.id.grid_image);
        }
        imageView = (ImageView) convertView.findViewById(R.id.grid_image);
        Picasso.with(context)
                .load("file://" + files[position])
                .fit().centerInside()
                .into((ImageView) imageView);

        return convertView;

    }
}
