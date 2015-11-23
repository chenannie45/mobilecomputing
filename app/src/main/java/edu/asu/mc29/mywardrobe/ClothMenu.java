package edu.asu.mc29.mywardrobe;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import edu.asu.mc29.mywardrobe.data.Constants;

public class ClothMenu extends AppCompatActivity {
    private ImageButton pants;
    private ImageButton formals;
    private ImageButton suits;
    private ImageButton tshirts;
    private ImageButton shoes;
    Map<ImageButton, Integer> buttonToImage;
    private ImageButton glasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_menu);
        pants = (ImageButton) findViewById(R.id.pants_button);
        formals = (ImageButton) findViewById(R.id.formal_button);
        suits = (ImageButton) findViewById(R.id.suit_button);
        tshirts = (ImageButton) findViewById(R.id.tshirts_button);
        shoes = (ImageButton) findViewById(R.id.shoes_button);
        glasses = (ImageButton) findViewById(R.id.glasses_button);
        buttonToImage = new HashMap<>();
        buttonToImage.put(pants, R.drawable.pants);
        buttonToImage.put(formals, R.drawable.formals);
        buttonToImage.put(suits, R.drawable.suit);
        buttonToImage.put(tshirts, R.drawable.tshirts);
        buttonToImage.put(shoes, R.drawable.tshirts);
        buttonToImage.put(glasses, R.drawable.tshirts);
        setImage(pants);
        setImage(formals);
        setImage(suits);
        setImage(shoes);
        setImage(tshirts);
        setImage(glasses);


    }

    private void setImage(ImageButton imgButton) {
        Picasso.with(this)
                .load(buttonToImage.get(imgButton))
                .fit().centerInside()
                .into(imgButton);
        imgButton.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            startDisplayClothActivity(v.getId());
        }
    };

    private void startDisplayClothActivity(int id) {
        Intent displayImage = new Intent(this, DisplayClothes.class);
        String dirPath = String.valueOf(
                Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_PICTURES)
        ) + "/" + Constants.imgToFolderNameMap.get(id);
        File temp = new File(dirPath);
        if (temp.isDirectory() || temp.mkdirs()) {
            displayImage.putExtra("filePath", dirPath);
            startActivity(displayImage);
        } else {
            Toast.makeText(this, "No clothes exist for this category", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cloth_menu, menu);
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


}
