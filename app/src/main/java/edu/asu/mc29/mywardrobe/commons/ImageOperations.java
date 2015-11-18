package edu.asu.mc29.mywardrobe.commons;

import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.asu.mc29.mywardrobe.data.Constants;

/**
 * Created by rrshah9 on 11/13/2015.
 */
public class ImageOperations {

    private String mCurrentPhotoPath;
    private File mStorageDir;

    /**
     * Creates unique file path for writing photo to storage*
     *
     * @return File
     */
    public File createImageFile() throws IOException {
        // Create an image file name

        String imageFileName = uniqueFileName();
        File mStorageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageF = File.createTempFile(
                imageFileName,
                Constants.IMG_FILE_SUFFIX,
                mStorageDir
        );
        //Saving file path for future reference
        mCurrentPhotoPath = "file:" + imageF.getAbsolutePath();
        return imageF;
    }

    /**
     * @return Unique FileName creator using TimeStamp
     */
    public String uniqueFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return Constants.IMG_FILE_PREFIX + timeStamp + "_";
    }

    /**
     * @return Moves the temp Image File to a type Folder
     */
    public File moveImagetoCategoryImage(File originalFile, String type) {
        File imgDirectory = new File(String.valueOf(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES)) + "/" + type);
        //Create directory if doesn not exists
        boolean isDirExists = imgDirectory.mkdirs();
        if (isDirExists) {
            originalFile.renameTo(imgDirectory);
        }
        return originalFile;
    }

    public Bitmap scaleImage(String imagePath, int targetH, int targetW) {
        // Get the size of the image
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = 4;

		/* Decode the JPEG file into a Bitmap */
        return BitmapFactory.decodeFile(imagePath, bmOptions);

    }

}

