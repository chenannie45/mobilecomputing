package edu.asu.mc29.mywardrobe;

import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import edu.asu.mc29.mywardrobe.commons.ImageOperations;
import edu.asu.mc29.mywardrobe.data.Constants;

public class ImageCaptureResult extends AppCompatActivity {

    protected ImageOperations mImageOperations;
    private ImageView imageView;
    private Button recaptureButton;
    private Button doneButton;

    /**
     * Starts the activity for taking picture from the existing camera application installed on
     * the device.
     */
    private void takeCaptureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);
        mImageOperations = new ImageOperations();
        imageView = (ImageView) findViewById(R.id.show_image);
        recaptureButton = (Button) findViewById(R.id.recapture_button);
        doneButton = (Button) findViewById(R.id.done_button);
        addDoneButtonListener(doneButton);
        addRecaptureButtonListener(recaptureButton);
        Log.d("debug",getIntent().getStringExtra("filePath"));
        Picasso.with(this).load("file://"+getIntent().getStringExtra("filePath"))
                .fit()
                .into(imageView);
        /*Bitmap imageToShow = mImageOperations.scaleImage(getIntent().getStringExtra("filePath"),
                imageView.getHeight(), imageView.getWidth());
        imageView.setImageBitmap(imageToShow);*/
    }

    private void addDoneButtonListener(Button doneButton) {
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //Succesfully completing the Activity
                finish();
            }
        });
    }

    private void addRecaptureButtonListener(Button recaptureButton) {
        recaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_capture, menu);
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
