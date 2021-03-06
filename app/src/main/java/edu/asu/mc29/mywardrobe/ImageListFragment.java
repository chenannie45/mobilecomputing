package edu.asu.mc29.mywardrobe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.Environment;
import android.test.suitebuilder.annotation.Suppress;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.BaseAdapter;

import java.io.File;


public class ImageListFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ImageAdapter mAdapter;
    OnImageSelectedListener mCallback;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnImageSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onImageSelected(int position);
    }

    /*// A static dataset to back the GridView adapter
    public final static Integer[] imageResIds = new Integer[] {
            R.drawable.sample_image_1, R.drawable.sample_image_2, R.drawable.sample_image_3,
            R.drawable.sample_image_4, R.drawable.sample_image_5, R.drawable.sample_image_6,
            R.drawable.sample_image_7, R.drawable.sample_image_8, R.drawable.sample_image_9};
            */

    // Empty constructor as per Fragment docs
    public ImageListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/Camera/";
        mAdapter = new ImageAdapter(getActivity(), imagePath);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_image_list, container, false);
        final ListView mListView = (ListView) v.findViewById(R.id.listView);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        return v;
    }

    @Override
    @SuppressWarnings( "deprecation" )
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnImageSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        mCallback.onImageSelected(position);
    }

    private class ImageAdapter extends BaseAdapter {
        private final Context mContext;
        private  File[] files;

        public ImageAdapter(Context context,String imagePath) {
            super();
            mContext = context;
            File targetDirector = new File(imagePath);

            files = targetDirector.listFiles();
        }


        public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {
            Bitmap bm = null;

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(path, options);

            return bm;
        }

        public int calculateInSampleSize(

                BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {
                if (width > height) {
                    inSampleSize = Math.round((float)height / (float)reqHeight);
                } else {
                    inSampleSize = Math.round((float)width / (float)reqWidth);
                }
            }

            return inSampleSize;
        }

        @Override
        public int getCount() {
            return files.length;
        }

        @Override
        public Object getItem(int position) {
            return files[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            ImageView imageView;
            if (convertView == null) { // if it's not recycled, initialize some attributes

                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);



            } else {
                imageView = (ImageView) convertView;
            }
            Bitmap bm = decodeSampledBitmapFromUri(files[position].getAbsolutePath(), 220, 220);
            imageView.setImageBitmap(bm);// Load image into ImageView
            return imageView;
        }
    }

}
