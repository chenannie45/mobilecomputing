package edu.asu.mc29.mywardrobe.commons;

import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

/*
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
   /* static {
        if (!OpenCVLoader.initDebug()) {
            // Handle initialization error
        }
    }*/

    /**
     * Creates unique file path for writing photo to storage*
     *
     * @return File
     */
    public File createImageFile() throws IOException {
        // Create an image file name

        String imageFileName = "temp_file";
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
    public void moveImagetoCategoryImage(File originalFile, String type) {
        File imgDirectory = new File(String.valueOf(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES)) + "/" + type);
        //Create directory if doesn not exists
        Log.e("inImageOperation", "Before mkdirs");

        if (imgDirectory.mkdirs() || imgDirectory.isDirectory()) {
            Log.e("inImageOperation", "inside Directory exists");
            File newFile = new File(imgDirectory.getAbsolutePath(), uniqueFileName() + ".png");
            originalFile.renameTo(newFile);
        }

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

    /**
     * Method to remove background and extract foreground from the image using
     * grabCutImageSegmentation(OpenCV library function)
     */
    public String removeBackground(String bitmapImgPath) throws IOException {
        return null;
     /*   BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmapImg = BitmapFactory.decodeFile(bitmapImgPath, bmOptions);
        //GrabCut part
        Mat matImg = new Mat();
        if(bitmapImg != null){
            Utils.bitmapToMat(bitmapImg, matImg);
            Log.e("inRemoval","Not null bitmap");
        }
        else
        {
            return null;
        }
        Log.e("removeBackgorund", "img: " + matImg);

        int r = matImg.rows();
        int c = matImg.cols();
        Point p1 = new Point(c / 10, r / 10);
        Point p2 = new Point(c - c / 10, r - r / 10);
        Rect rect = new Rect(p1, p2);
        Log.e("removeBackground", "rect: " + rect);

        Mat mask = new Mat();
        Mat fgdModel = new Mat();
        Mat bgdModel = new Mat();


        Mat imgC3 = new Mat();
        Imgproc.cvtColor(matImg, imgC3, Imgproc.COLOR_RGBA2RGB);
        Log.e("removeBackground", "imgC3: " + imgC3);

        Log.e("removeBackground", "Grabcut begins");
        Imgproc.grabCut(imgC3, mask, rect, bgdModel, fgdModel, 2, Imgproc.
                GC_INIT_WITH_RECT);
        Log.e("removeBackground", "Grabcut ends");

        Log.e("removeBackground", "mask: " + mask);
        Log.e("removeBackground", "bgdModel: " + bgdModel);
        Log.e("removeBackground", "fgdModel: " + fgdModel);

        Core.convertScaleAbs(mask, mask, 100, 0);
        Imgproc.cvtColor(mask, mask, Imgproc.COLOR_GRAY2RGBA);
        Log.e("removeBackground", "maskC4: " + mask);

        //convert to Bitmap
        Log.e("removeBackground", "Convert to Bitmap");
        //Utils.matToBitmap(imgC3, bitmap);
        Utils.matToBitmap(mask, bitmapImg);

        //release MAT part
        matImg.release();
        imgC3.release();
        mask.release();
        fgdModel.release();
        bgdModel.release();

        //Write image to File for further reading
        File imgDirectory = new File(String.valueOf(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES)) + "/" + "afterRemoval");
        File newFile = new File(imgDirectory.getAbsolutePath(), uniqueFileName() + ".png");
        if (imgDirectory.mkdirs() || imgDirectory.isDirectory()) {
            Log.e("BackGrndRemoveOperation", "inside Directory exists");

            FileOutputStream fOut = new FileOutputStream(newFile);
            if (fOut != null) {
                fOut.flush();
                fOut.close();
            }
        }

        return newFile.getAbsolutePath();
*/
    }

}



