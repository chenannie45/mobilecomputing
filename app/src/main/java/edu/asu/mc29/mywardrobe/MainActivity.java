package edu.asu.mc29.mywardrobe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

import edu.asu.mc29.mywardrobe.commons.ImageOperations;
import edu.asu.mc29.mywardrobe.data.Constants;

public class MainActivity extends AppCompatActivity {
    private Button cameraButton;
    private ImageOperations mImageOperations;
    private File tempPhotoFileToShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageOperations = new ImageOperations();
        addListenerOnButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void triggerWeather(View view) {
        Intent intent = new Intent(this, LocalWeather.class);
        /*
        EditText editText = (EditText)findViewById(R.id.edit_message);
        String msg = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE,msg);
        */
        startActivity(intent);
    }

    public void addListenerOnButton() {

        cameraButton = (Button) findViewById(R.id.cameraButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                activateCameraToCaptureImage();
            }
        });

    }

    private void activateCameraToCaptureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = mImageOperations.createImageFile();
            } catch (IOException ex) {
                Log.d("Camera", "failed to create image File");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                tempPhotoFileToShare = photoFile;
                startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == Constants.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Intent showCapturedImage = new Intent(this, ImageCaptureResult.class);
            showCapturedImage.putExtra("filePath", tempPhotoFileToShare.getAbsolutePath());
            startActivity(showCapturedImage);
        }

    }

}
