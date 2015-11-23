package edu.asu.mc29.mywardrobe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import com.squareup.picasso.Picasso;

import java.io.File;

public class ClothMatchNew extends AppCompatActivity implements ImageListFragment.OnImageSelectedListener, AdapterView.OnItemSelectedListener {

    private String category;
    private String filePath;
    ImageListFragment imageListFragment;
    private ArrayAdapter<CharSequence> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_match_new);

        if (savedInstanceState != null) {
            return;
        }

        // Create an instance of ExampleFragment
        imageListFragment = new ImageListFragment();
        //MatchCloth matchFragment = new MatchCloth();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
        Bundle args = new Bundle();
        //    args.putString(CLOTHPATH, filepath+category+"/");
        //filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/Camera/";
        filePath = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        category = "tops";
        args.putString("clothpath", filePath);
        args.putString("category", category);

        imageListFragment.setArguments(args);
        //matchFragment.setArguments(args);

        // Add the fragment to the 'fragment_container' FrameLayout
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft1 = fm.beginTransaction();
        ft1.add(R.id.listFrame, imageListFragment).commit();

        adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        Spinner spinner = (Spinner) findViewById(R.id.categorySpinner);


// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }

    public void showCloth(String filePath, String category, int position) {
        File targetDirector = new File(filePath + "/" + category + "/");

        File[] files = targetDirector.listFiles();

        if (category.equals("tops")) {
            ImageView imageView = (ImageView) findViewById(R.id.topMiddle);
            //Bitmap bm = decodeSampledBitmapFromUri(files[position].getAbsolutePath(), 300, 300);
            Picasso.with(this).load("file://" + files[position].getAbsolutePath())
                    .fit()
                    .centerInside()
                    .into(imageView);


        }

        if (category.equals("bottoms")) {
            Log.e("bottom", Integer.toString(position));
            ImageView imageView = (ImageView) findViewById(R.id.imageBottom);
            Picasso.with(this).load("file://" + files[position].getAbsolutePath())
                    .fit()
                    .centerInside()
                    .into(imageView);

        }

        if (category.equals("shoes")) {
            ImageView imageView = (ImageView) findViewById(R.id.imageShoes);
            Picasso.with(this).load("file://" + files[position].getAbsolutePath())
                    .fit()
                    .centerInside()
                    .into(imageView);

        }
        if (category.equals("hats")) {
            ImageView imageView = (ImageView) findViewById(R.id.imageHat);
            Picasso.with(this).load("file://" + files[position].getAbsolutePath())
                    .fit()
                    .centerInside()
                    .into(imageView);

        }
        if (category.equals("glasses")) {
            ImageView imageView = (ImageView) findViewById(R.id.imageGlass);
            Picasso.with(this).load("file://" + files[position].getAbsolutePath())
                    .fit()
                    .centerInside()
                    .into(imageView);

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cloth_match_new, menu);
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
    public void onImageSelected(int position) {
        String pos = "position is " + position;
        Log.e("Click", pos);
        //Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG).show();
        //MatchCloth matchFrag = (MatchCloth) getSupportFragmentManager().findFragmentById(R.id.detailFrame);

        Log.e("Initialize", pos);
        showCloth(filePath, category, position);

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        String[] categories = getResources().getStringArray(R.array.category_array);
        category = categories[pos].toLowerCase();

        //Bundle args = new Bundle();
        //Log.e("omItemSelected","before Update");
        imageListFragment.updateList(filePath, category);
       // Log.e("omItemSelected", "After Update");
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void removeHat(View view) {
        ImageView imageView = (ImageView) findViewById(R.id.imageHat);
        imageView.setImageResource(0);
    }

    public void removeTop(View view) {
        ImageView imageView = (ImageView) findViewById(R.id.topMiddle);
        imageView.setImageResource(0);
    }

    public void removeGlass(View view) {
        ImageView imageView = (ImageView) findViewById(R.id.imageGlass);
        imageView.setImageResource(0);
    }

    public void removeBottom(View view) {
        ImageView imageView = (ImageView) findViewById(R.id.imageBottom);
        imageView.setImageResource(0);
    }

    public void removeShoes(View view) {
        ImageView imageView = (ImageView) findViewById(R.id.imageShoes);
        imageView.setImageResource(0);
    }
}
